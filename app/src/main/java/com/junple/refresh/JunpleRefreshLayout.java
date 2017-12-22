package com.junple.refresh;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by junple on 2017/12/20.
 */

public class JunpleRefreshLayout extends LinearLayout implements JunpleRefreshLayoutExecutor{

    private RefreshLayoutInitiliazer m_refreshLayoutIntiliazer;
    private int m_status = RefreshLayoutEvent.NORMAL;
    private View m_contentView;
    private int m_refreshStatus = RefreshLayoutEvent.NORMAL;
    private RefreshAdapter m_refreshAdapter;
    private OnUpdateListener m_onUpdateListener;
    private List<OnUpdateListener> m_onUpdateListenerList = new ArrayList<>();
    private SwipeController m_swipeController;
    private OnRefreshListener m_onRefreshListener;
    private float m_previousMovedValue = 0.0f;
    private float m_previousPostion = 0.0f;

    public JunpleRefreshLayout(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);

        TypedArray ta = context.obtainStyledAttributes( attrs, R.styleable.JunpleRefreshLayout);
        int footerLayoutId = ta.getResourceId( R.styleable.JunpleRefreshLayout_footer_layout, R.layout.footer);
        int headerLayoutId = ta.getResourceId( R.styleable.JunpleRefreshLayout_header_layout, R.layout.layout_header_default);
        Drawable headerDrawable = ta.getDrawable( R.styleable.JunpleRefreshLayout_header_background);
        int header_extend_height = ta.getDimensionPixelSize( R.styleable.JunpleRefreshLayout_header_extend_height, 200);
        int footer_extend_height = ta.getDimensionPixelSize( R.styleable.JunpleRefreshLayout_header_extend_height, 200);
        String refreshUiControllerName = ta.getString( R.styleable.JunpleRefreshLayout_refresh_ui_contoller);

        m_refreshLayoutIntiliazer = new SimpleRefreshLayoutInitiliazer( headerLayoutId, footerLayoutId, this, headerDrawable, null, header_extend_height, footer_extend_height);
        m_swipeController = new SimpleSwipeController();
        if( m_refreshLayoutIntiliazer!=null&&m_refreshLayoutIntiliazer.getHeaderView()!=null) {
            addView( m_refreshLayoutIntiliazer.getHeaderView());
        }
        m_onUpdateListener = new EventUpdateListener();
        addOnUpdateListener( m_onUpdateListener);
        if( refreshUiControllerName==null) {
            addOnUpdateListener( new DefaultUiController());
        }else {
            try {
                Class<?extends OnUpdateListener> uiControllerClass = (Class<? extends OnUpdateListener>) Class.forName( refreshUiControllerName);
                OnUpdateListener uiController = uiControllerClass.newInstance();
                addOnUpdateListener( uiController);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    public JunpleRefreshLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if( m_refreshLayoutIntiliazer!=null&&m_refreshLayoutIntiliazer.getFooterView()!=null) {
            addView( m_refreshLayoutIntiliazer.getFooterView());
        }
        m_contentView = getChildAt( 1);
        initEvent();
        if( getContentView() instanceof RecyclerView) {
            setRefreshAdapter( new RecyclerViewRefreshAdapter());
        }
        addOnUpdateListener( new DefaultUiController());
    }

    @Override
    public void createInitializer() {

    }

    @Override
    public View getHeaderView() {
        return m_refreshLayoutIntiliazer.getHeaderView();
    }

    @Override
    public View getFooterView() {
        return m_refreshLayoutIntiliazer.getFooterView();
    }

    @Override
    public View getContentView() {
        return m_contentView;
    }

    @Override
    public void setRefreshAdapter(RefreshAdapter adapter) {
        m_refreshAdapter = adapter;
        if( m_refreshAdapter!=null) {
            m_refreshAdapter.setRefreshLayout( this);
        }
    }

    @Override
    public void setRefreshStatus(int status) {

        if( status!=m_refreshStatus) {
            doOnStatusChanged( this, m_refreshStatus, status);
            m_refreshStatus = status;
            if( status==RefreshLayoutEvent.REFRESHING&&m_onRefreshListener!=null) {
                m_onRefreshListener.onRefresh( this);
            }
        }else {
            m_refreshStatus = status;
        }
    }

    @Override
    public int getRefreshStatus() {
        return m_refreshStatus;
    }

    @Override
    public void initEvent() {

        getContentView().setOnTouchListener(new View.OnTouchListener() {

            private float m_y;
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //手指按下时记录初始点的位置
                if( event.getAction()==MotionEvent.ACTION_DOWN) {
                    m_y = event.getRawY();
                    if( m_refreshAdapter==null||(!m_refreshAdapter.canSwipeUp()&&!m_refreshAdapter.canSwipeDown())) return false;//没有适配器或者不能上拉也不能下拉就返回
                }
                //手指滑动时
                else if( event.getAction()==MotionEvent.ACTION_MOVE) {

                    float distance = m_y - event.getRawY();//计算离上个点的距离
                    m_y = event.getRawY();//存储当前点位置
                    RefreshLayoutEvent rlEvent = new RefreshLayoutEvent();//创建一个Refresh控件事件
                    rlEvent.setFingerStatus( RefreshLayoutEvent.FINGER_MOVE);
                    //处于可下拉刷新的状态
                    if ( m_refreshAdapter.canSwipeDown()) {
                        //手指在下滑
                        if( distance<0) {

                            rlEvent.setMoveStatus(RefreshLayoutEvent.TO_REFRESH);
                            float generateValue = 100.0f / getHeaderView().getHeight() * (-getScrollY());
                            doOnUpdate( JunpleRefreshLayout.this, rlEvent);

                            if (-getScrollY() > getHeaderView().getHeight()) {
                                scrollTo(0, -getHeaderView().getHeight());
                            } else if (-getScrollY() == getHeaderView().getHeight()) {
                            } else {
                                if( m_swipeController!=null) {
                                    scrollTo(0, (int)m_swipeController.onSwipe( generateValue,distance)+(int)getScrollY());
                                }
                            }
                            return true;
                        }
                        //手指在上滑
                        else if( distance>=0) {
                            if( getScrollY()>=0) {
                                scrollTo(0,0);

                                RecyclerView rv = (RecyclerView) getContentView();
                                rv.getLayoutManager().scrollToPosition(0);
                                return false;
                            }
                            else {
                                rlEvent.setMoveStatus(RefreshLayoutEvent.TO_REFRESH);
                                float generateValue = 100.0f / getHeaderView().getHeight() * (-getScrollY());
                                doOnUpdate( JunpleRefreshLayout.this, rlEvent);
                                if( m_swipeController!=null) {
                                    scrollTo(0, (int) m_swipeController.onSwipe( generateValue,distance) + (int) getScrollY());
                                }
                                return true;
                            }
                        }
                    }
                }
                else if(event.getAction()==MotionEvent.ACTION_UP) {

                        //setRefreshStatus( RefreshLayoutEvent.REFRESHING);
                        RefreshLayoutEvent rlEvent = new RefreshLayoutEvent();
                        rlEvent.setFingerStatus( RefreshLayoutEvent.FINGER_UP);
                        //m_onUpdateListener.onStatusChanged( JunpleRefreshLayout.this, getRefreshStatus());
                    m_onUpdateListener.onUpdate( JunpleRefreshLayout.this, rlEvent);
                }
                return false;
            }});
    }

    @Override
    public void addOnUpdateListener(OnUpdateListener listener) {

        m_onUpdateListenerList.add( listener);
        listener.onSettingToView( this);
    }

    @Override
    public void moveToPosition( float value, long duration) {

        moveToPosition( value,duration,null);
    }

    @Override
    public void moveToPosition(float value, long duration, final OnMoveEndListener listener) {

        ValueAnimator animator = ValueAnimator.ofFloat( getPosition(), value);
        animator.setDuration( duration);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if( listener!=null) listener.onEnd( JunpleRefreshLayout.this);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value  = (float)animation.getAnimatedValue();
                setPostion( value);
            }
        });
        animator.start();
    }

    @Override
    public float getPosition() {

        float postion = 0.0f;
        float scrollY = getScrollY();
        if( scrollY<0.0f) {
            postion = 100.0f/(float)getHeaderView().getHeight()*-scrollY;
        }
        else if (scrollY>0.0f) {
            postion = 100.0f/(float)getFooterView().getHeight()*scrollY;
        }
        return postion;
    }

    @Override
    public void setPostion(float value) {

        if( value==0.0f) {
            scrollTo(0,0);
        }else if( value>0.0f) {
            scrollTo( 0, (int)-(getHeaderView().getHeight()*value/100.0f));
        }else {
            scrollTo( 0, (int)(getFooterView().getHeight()*value/100.0f));
        }
        RefreshLayoutEvent rlEvent = new RefreshLayoutEvent();
        doOnUpdate( this, rlEvent);
    }

    @Override
    public void finishRefresh() {


        if( getRefreshStatus()==RefreshLayoutEvent.REFRESHING) {
            setRefreshStatus( RefreshLayoutEvent.REFRESHING_SUCCESS);
            moveToPosition(0.0f, 200, new OnMoveEndListener() {
                @Override
                public void onEnd(JunpleRefreshLayout view) {
                    setRefreshStatus( RefreshLayoutEvent.NORMAL);
                }
            });
        }
    }

    public void finishRefresh( long duration) {

        if( getRefreshStatus()==RefreshLayoutEvent.REFRESHING) {
            setRefreshStatus( RefreshLayoutEvent.REFRESHING_SUCCESS);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    AppCompatActivity activity = (AppCompatActivity) getContext();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            moveToPosition(0.0f, 200, new OnMoveEndListener() {
                                @Override
                                public void onEnd(JunpleRefreshLayout view) {
                                    setRefreshStatus( RefreshLayoutEvent.NORMAL);
                                }
                            });
                        }
                    });
                }
            },duration);

        }
    }

    @Override
    public void setOnRefreshLitener(OnRefreshListener litener) {

        m_onRefreshListener = litener;
    }

    @Override
    public float getMovedValue() {

        float value = 0.0f;
        if( getScrollY()<0) {
            value = -getScrollY()/(float)m_refreshLayoutIntiliazer.getHeaderHeight()*100.0f;
        }
        else {
            value = -getScrollY()/(float)m_refreshLayoutIntiliazer.getFooterHeight()*100.0f;
        }
        return value;
    }

    @Override
    public RefreshLayoutInitiliazer getRefreshLayoutInitiliazer() {
        return m_refreshLayoutIntiliazer;
    }

    private void doOnUpdate(JunpleRefreshLayout view, RefreshLayoutEvent event) {

        event.setPeviousMovedValue( m_previousMovedValue);
        event.setPreviousPostion( m_previousPostion);
        m_previousMovedValue = getMovedValue();
        m_previousPostion = getPosition();
        if( m_onUpdateListenerList==null) return;
        for( int i=0; i<m_onUpdateListenerList.size(); i++) {
            m_onUpdateListenerList.get( i).onUpdate( view, event);
        }
    }

    private void doOnStatusChanged(  JunpleRefreshLayout view, int oldStatus,  int newStatus) {

        if( m_onUpdateListenerList==null) return;
        for( int i=0; i<m_onUpdateListenerList.size(); i++) {

            m_onUpdateListenerList.get( i).onStatusChanged( view, oldStatus, newStatus);
        }
    }

    public interface OnRefreshListener {

        void onRefresh( JunpleRefreshLayout view);
    }

    public interface OnUpdateListener {

        void onSettingToView( JunpleRefreshLayout view);
        void onUpdate( JunpleRefreshLayout view, RefreshLayoutEvent event);
        void onStatusChanged( JunpleRefreshLayout view, int oldStatus,  int newStatus);
    }

    public interface OnMoveEndListener {

        void onEnd( JunpleRefreshLayout view);
    }
}