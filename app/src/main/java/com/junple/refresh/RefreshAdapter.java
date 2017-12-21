package com.junple.refresh;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by junple on 2017/12/20.
 */

public abstract class RefreshAdapter {

    protected JunpleRefreshLayout m_refreshLayout;
    private float m_y = 0;
    private float m_scrolledY;


    public RefreshAdapter( ){

    }

    public void setRefreshLayout( JunpleRefreshLayout layout) {

        m_refreshLayout = layout;
        onBindRefreshLayout( m_refreshLayout);
        initEvent( m_refreshLayout);
    }

    private void initEvent( final JunpleRefreshLayout layout) {

    }

    abstract boolean canSwipeDown();
    abstract boolean canSwipeUp();
    abstract void onBindRefreshLayout( JunpleRefreshLayout layout);
}