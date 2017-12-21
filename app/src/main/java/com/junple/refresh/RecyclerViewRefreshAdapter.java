package com.junple.refresh;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by junple on 2017/12/20.
 */

public class RecyclerViewRefreshAdapter extends RefreshAdapter {

    private RecyclerView m_recyclerView;
    private boolean m_canSwipeDown = false;

    public RecyclerViewRefreshAdapter() {
        super();
    }

    @Override
    boolean canSwipeDown() {


        return m_canSwipeDown;
    }

    @Override
    boolean canSwipeUp() {
        return false;
    }

    @Override
    void onBindRefreshLayout(JunpleRefreshLayout layout) {

        m_recyclerView = (RecyclerView)layout.getContentView();
        m_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!recyclerView.canScrollVertically(-1)) {
                    //onScrolledToTop();
                    m_canSwipeDown = true;
                } else if (!recyclerView.canScrollVertically(1)) {
                    //onScrolledToBottom();
                } else if (dy < 0) {
                    //onScrolledUp();
                } else if (dy > 0) {
                    m_canSwipeDown = false;
                    //onScrolledDown();
                }
            }
        });
    }
}
