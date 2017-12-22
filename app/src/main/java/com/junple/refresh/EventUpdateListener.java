package com.junple.refresh;

import android.util.Log;

/**
 * Created by junple on 2017/12/21.
 */

public class EventUpdateListener implements JunpleRefreshLayout.OnUpdateListener {

    @Override
    public void onSettingToView(JunpleRefreshLayout view) {

    }

    @Override
    public void onUpdate(JunpleRefreshLayout view, RefreshLayoutEvent event) {

            switch ( event.getFingerStatus()) {

                case RefreshLayoutEvent.FINGER_UP: {

                    if( view.getMovedValue()>100.0f) {

                        view.moveToPosition( (float)view.getRefreshLayoutInitiliazer().getHeaderHeight()/(float)view.getHeaderView().getHeight()*100.0f, 200);
                        view.setRefreshStatus( RefreshLayoutEvent.REFRESHING);
                    }else {
                        view.moveToPosition( 0.0f, 200);
                    }
                    break;
                    }

                case RefreshLayoutEvent.FINGER_MOVE: {

                    break;
                }
            }
    }

    @Override
    public void onStatusChanged(JunpleRefreshLayout view, int oldStatus, int newStatus) {

        /*
        if( newStatus==RefreshLayoutEvent.NORMAL) {
            m_animator.end();
            m_img.setRotation( 0.0f);
        }*/
    }
}
