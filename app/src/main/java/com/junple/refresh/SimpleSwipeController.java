package com.junple.refresh;

/**
 * Created by junple on 2017/12/21.
 */

public class SimpleSwipeController implements SwipeController {
    @Override
    public float onSwipe(float swipedValue, float distanceY) {

        if( swipedValue>0 &&distanceY<0) {
            return distanceY*(100.0f-swipedValue)/100.0f;
        }
        return distanceY;
    }
}
