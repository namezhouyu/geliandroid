package com.geli.m.coustomview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 禁止view pager 滑动事件的响应
 *
 * 子View获得事件后，onTouchEvent返回false 事件就往上传递(如果是true事件就会被消费，无法往上传)，
 * 事件就有可能被本控件获取
 *
 */
public class NoScrollViewpager extends ViewPager {
    public NoScrollViewpager(Context context) {
        super(context);
    }
    public NoScrollViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 屏蔽滑动响应事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;           /* 不拦截事件，将子View获取事件 */
    }

}
