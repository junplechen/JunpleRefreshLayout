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
    void setUpdateListener(JunpleRefreshLayout.UpdateListener listener);
    void moveToPostion( float value, long duration);
    float getPosition();
    void setPostion( float value);
    void finishRefresh();
    void setOnRefreshLitener(JunpleRefreshLayout.OnRefreshListener litener);
}
