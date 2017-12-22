package com.junple.refresh;

import android.view.View;

/**
 * Created by junple on 2017/12/20.
 */

interface RefreshLayoutInitiliazer {

    View getHeaderView();
    View getFooterView();
    int getHeaderHeight();
    int getFooterHeight();
}
