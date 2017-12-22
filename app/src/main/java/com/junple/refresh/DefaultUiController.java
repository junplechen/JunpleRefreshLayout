package com.junple.refresh;

import android.animation.ObjectAnimator;
import android.widget.ImageView;

/**
 * Created by junple on 2017/12/22.
 */

public class DefaultUiController implements JunpleRefreshLayout.OnUpdateListener {

    private ImageView imageView;
    private ObjectAnimator m_animator;
    @Override
    public void onSettingToView(JunpleRefreshLayout view) {

        imageView = (ImageView)view.findViewById( R.id.asdfasdfasdfsdfagasd);
    }

    @Override
    public void onUpdate(JunpleRefreshLayout view, RefreshLayoutEvent event) {

        if( imageView==null) return;
        if( view.getRefreshStatus()==RefreshLayoutEvent.NORMAL) {
            if( view.getMovedValue()<=100.0f &&view.getMovedValue()>0) {
                imageView.setScaleY( view.getMovedValue()/100.0f);
                imageView.setScaleX( view.getMovedValue()/100.0f);
            }else if( view.getMovedValue()>100.0f) {
                imageView.setScaleY(1);
                imageView.setScaleX(1);
            }
        }
    }

    @Override
    public void onStatusChanged(JunpleRefreshLayout view, int oldStatus, int newStatus) {

        if( imageView==null) return;
        if( newStatus==RefreshLayoutEvent.REFRESHING) {

            m_animator = ObjectAnimator.ofFloat( imageView, "rotation", 0, -360);
            m_animator.setInterpolator( null);
            m_animator.setDuration(1500);
            m_animator.setRepeatCount( -1);
            m_animator.start();
        }

        if( newStatus==RefreshLayoutEvent.NORMAL) {
            m_animator.end();
            imageView.setRotation( 0.0f);
        }
    }
}
