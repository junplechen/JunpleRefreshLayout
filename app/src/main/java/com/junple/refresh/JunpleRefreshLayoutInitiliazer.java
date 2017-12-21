package com.junple.refresh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by junple on 2017/12/20.
 */

class JunpleRefreshLayoutInitiliazer implements RefreshLayoutInitiliazer {

    private View m_headerView;
    private View m_footerView;

    public JunpleRefreshLayoutInitiliazer(int headerLayoutId, int footerLayoutId, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from( viewGroup.getContext());
        m_headerView = inflater.inflate( headerLayoutId, viewGroup,false);
        m_footerView = inflater.inflate( footerLayoutId, viewGroup,false);
        LinearLayout.LayoutParams headerLayoutParams = (LinearLayout.LayoutParams)m_headerView.getLayoutParams();
        headerLayoutParams.topMargin = -headerLayoutParams.height;
    }

    @Override
    public View getHeaderView() {

        return m_headerView;
    }

    @Override
    public View getFooterView() {

        return m_footerView;
    }
}
