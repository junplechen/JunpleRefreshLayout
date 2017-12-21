package com.junple.refresh;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by junple on 2017/12/21.
 */

public class DefaultUpdateListener implements JunpleRefreshLayout.UpdateListener {

    private ImageView m_img;
    private View m_header;
    private View m_content;
    private ObjectAnimator m_animator;
    @Override
    public void onUpdate(JunpleRefreshLayout view, RefreshLayoutEvent event) {

        if( m_img==null) {
            m_img = view.findViewById( R.id.img);
        }
        if( m_header==null) {
            m_header = view.findViewById( R.id.header);
        }
        if( m_content==null) {
            m_content = view.findViewById( R.id.content);
        }
        if( m_header!=null&&m_img!=null&&m_content!=null) {

            if( view.getRefreshStatus()!=RefreshLayoutEvent.REFRESHING) {
                float scale = 1.0f* m_header.getHeight()*view.getPosition()/100.0f/m_content.getHeight();
                if( scale>1.0f) scale=1.0f;
                m_img.setScaleY( scale);
                m_img.setScaleX( scale);
            }
            switch ( event.getFingerStatus()) {

                case RefreshLayoutEvent.FINGER_UP: {

                    if( view.getPosition()>0) {
                        float position = 100.0f*m_content.getHeight()/m_header.getHeight();
                        if( view.getPosition()> position) {
                            view.moveToPostion( position, 200);
                            if( view.getRefreshStatus()!=RefreshLayoutEvent.REFRESHING) {

                                m_animator = ObjectAnimator.ofFloat( m_img, "rotation",0,360);
                                m_animator.setDuration(1000);
                                m_animator.setInterpolator( null);
                                m_animator.setRepeatCount( ObjectAnimator.INFINITE);
                                m_animator.start();
                            }
                            view.setRefreshStatus( RefreshLayoutEvent.REFRESHING);
                        }else {
                            view.moveToPostion( 0.0f, 200);
                        }
                    }
                    break;
                }

                case RefreshLayoutEvent.FINGER_MOVE: {

                    break;
                }
            }
        }
    }

    @Override
    public void onStatusChanged(JunpleRefreshLayout view, int oldStatus, int newStatus) {

        if( newStatus==RefreshLayoutEvent.NORMAL) {
            m_animator.end();
            m_img.setRotation( 0.0f);
        }
    }
}
