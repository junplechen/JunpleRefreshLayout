package com.junple.refresh;

import android.view.View;

/**
 * Created by junple on 2017/12/20.
 */

public interface JunpleRefreshLayoutExecutor {

    void createInitializer();
    View getHeaderView();
    View getFooterView();
    View getContentView();
    void setRefreshAdapter(RefreshAdapter connector);
    void setRefreshStatus( int status);
    int getRefreshStatus();
    void initEvent();
    void addOnUpdateListener(JunpleRefreshLayout.OnUpdateListener listener);
    void moveToPosition( float value, long duration);
    void moveToPosition(float value, long duration, JunpleRefreshLayout.OnMoveEndListener listener);
    float getPosition();
    void setPostion( float value);
    void finishRefresh();
    void setOnRefreshLitener(JunpleRefreshLayout.OnRefreshListener litener);
    float getMovedValue();
    RefreshLayoutInitiliazer getRefreshLayoutInitiliazer();
}
