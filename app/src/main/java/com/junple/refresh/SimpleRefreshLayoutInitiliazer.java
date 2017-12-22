package com.junple.refresh;

import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by junple on 2017/12/22.
 */

public class SimpleRefreshLayoutInitiliazer implements RefreshLayoutInitiliazer {

    private View m_headerView;
    private View m_footerView;
    private View m_headerContent;

    public SimpleRefreshLayoutInitiliazer(int headerLayoutId, int footerLayoutId, ViewGroup viewGroup, Drawable headerDrawable, Drawable footerDrawable, int headerExtendHeight, int footerExtendHeight) {

        /*
        LayoutInflater inflater = LayoutInflater.from( viewGroup.getContext());
        m_headerView = inflater.inflate( headerLayoutId, viewGroup,false);
        m_footerView = inflater.inflate( footerLayoutId, viewGroup,false);
        LinearLayout.LayoutParams headerLayoutParams = (LinearLayout.LayoutParams)m_headerView.getLayoutParams();
        headerLayoutParams.topMargin = -headerLayoutParams.height;*/

        LayoutInflater inflater = LayoutInflater.from( viewGroup.getContext());

        FrameLayout header = new FrameLayout( viewGroup.getContext());
        View userHeaderView = inflater.inflate( headerLayoutId, header,false);
        FrameLayout.LayoutParams usrHlp = (FrameLayout.LayoutParams) userHeaderView.getLayoutParams();
        usrHlp.gravity = Gravity.BOTTOM|Gravity.CENTER;
        header.addView( userHeaderView);
        header.setBackground( headerDrawable);

        LinearLayout.LayoutParams headerLayoutParams = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, headerExtendHeight+usrHlp.height);
        headerLayoutParams.topMargin = -headerLayoutParams.height;
        header.setLayoutParams( headerLayoutParams);
        m_headerView = header;

        m_footerView = new View( viewGroup.getContext());
        m_headerContent = userHeaderView;
    }

    @Override
    public View getHeaderView() {

        return m_headerView;
    }

    @Override
    public View getFooterView() {

        return m_footerView;
    }

    @Override
    public int getHeaderHeight() {
        return m_headerContent.getHeight();
    }

    @Override
    public int getFooterHeight() {
        return 0;
    }
}
