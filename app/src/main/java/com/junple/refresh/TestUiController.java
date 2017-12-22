package com.junple.refresh;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by junple on 2017/12/22.
 */

public class TestUiController implements JunpleRefreshLayout.OnUpdateListener {

    private TextView m_text;
    private ImageView m_img;

    @Override
    public void onSettingToView(JunpleRefreshLayout view) {

        m_text = view.findViewById( R.id.text);
        m_img = view.findViewById( R.id.down);
    }

    @Override
    public void onUpdate(JunpleRefreshLayout view, RefreshLayoutEvent event) {


        if( view.getRefreshStatus()==RefreshLayoutEvent.NORMAL) {

            if( event.getPreviousMovedValue()<100.0f&&view.getMovedValue()>=100.0f) {
                m_img.setRotation( 180.0f);
                m_text.setText("释放刷新");
            }
            if( event.getPreviousMovedValue()>100.0f&&view.getMovedValue()<100.0f) {
                m_img.setRotation( 0.0f);
                m_text.setText("下拉刷新");
            }
        }
    }

    @Override
    public void onStatusChanged(JunpleRefreshLayout view, int oldStatus, int newStatus) {

        if( newStatus==RefreshLayoutEvent.REFRESHING) {
            m_text.setText("正在刷新");
        }
        if( newStatus==RefreshLayoutEvent.NORMAL) {
            m_text.setText("下拉刷新");
            m_img.setRotation( 0.0f);
        }
        if( newStatus==RefreshLayoutEvent.REFRESHING_SUCCESS) {
            m_text.setText("刷新成功");
            m_img.setRotation( 0.0f);
        }
    }
}
