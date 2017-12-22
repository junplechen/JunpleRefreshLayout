package com.junple.refresh;

/**
 * Created by junple on 2017/12/20.
 */

public class RefreshLayoutEvent {

    public static final int NORMAL = 1000;//正常状态
    public static final int TO_REFRESH = 1001;//下拉过程中
    public static final int TO_LOAD = 1002;//上拉过程中
    public static final int REFRESHING = 2000;//正在刷新
    public static final int REFRESHING_SUCCESS = 2002;//刷新成功
    public static final int LOADING = 2001;//正在加载
    public static final int FINGER_DOWN = 3000;//手指按下
    public static final int FINGER_UP = 3001;//手指抬起
    public static final int FINGER_MOVE = 3002;//手指滑动

    private int m_moveStatus;
    private int m_fingerStatus;
    private float m_previousMovedValue;
    private float m_previousPostion;

    public RefreshLayoutEvent() {

        m_moveStatus = NORMAL;
    }

    public int getMoveStatus() {
        return m_moveStatus;
    }

    public void setMoveStatus(int moveStatus) {
        this.m_moveStatus = moveStatus;
    }

    public void setFingerStatus( int status) {
        m_fingerStatus = status;
    }

    public int getFingerStatus() {
        return m_fingerStatus;
    }

    public float getPreviousMovedValue() {
        return m_previousMovedValue;
    }

    public void setPeviousMovedValue(float previousMovedValue) {
        this.m_previousMovedValue = previousMovedValue;
    }

    public float getPreviousPostion() {
        return m_previousPostion;
    }

    public void setPreviousPostion(float previousPostion) {
        this.m_previousPostion = previousPostion;
    }
}
