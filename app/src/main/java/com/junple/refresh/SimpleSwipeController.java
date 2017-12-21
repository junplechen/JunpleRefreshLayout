package com.junple.refresh;

/**
 * Created by junple on 2017/12/21.
 */

public class SimpleSwipeController implements SwipeController {
    @Override
    public float onSwipe(float swipedValue, float distanceY) {
        return distanceY*(100.0f-swipedValue)/100.0f;
    }
}
