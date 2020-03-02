package com.geli.m.coustomview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Introduce : 暴露滑动监听接口的自定义scrollView
 * Created by CHEN_ on 2018/7/7.
 **/
public class ZLScrollView extends ScrollView {

    private ScrollViewListener scrollViewListener = null;

    public ZLScrollView(Context context) {
        super(context);
    }

    public ZLScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZLScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        // x为当前滑动条的横坐标，y表示当前滑动条的纵坐标，oldx为前一次滑动的横坐标，oldy表示前一次滑动的纵坐标
        if (scrollViewListener != null) {
            // 在这里将方法暴露出去
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    // 是否要其弹性滑动
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                   int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

        // 弹性滑动关键则是maxOverScrollX， 以及maxOverScrollY，
        // 一般默认值都是0，需要弹性时，更改其值即可
        // 即就是，为零则不会发生弹性，不为零（>0,负数未测试）则会滑动到其值的位置
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY,
                scrollRangeX, scrollRangeY, 0, 0, isTouchEvent);
    }


    // 接口
    public interface ScrollViewListener {

        void onScrollChanged(View scrollView, int x, int y, int oldx, int oldy);

    }

    public void setOnScrollViewListener(ScrollViewListener listener) {
        scrollViewListener = listener;
    }
}
