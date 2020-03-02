package com.geli.m.coustomview.recyclerviewscrollerview.viewprovider;

/**
 *
 * 控件View -- 行为 -- 显示/隐藏(释放)/开始滑动/结束滑动
 *   <p>
 * 扩展类应该使用这个接口来获得关于FastScroller元素（mViewHandle）发生的事件的通知，
 * 并做出相应的反应。
 *
 */
public interface ViewBehavior {

    /** 控件 -- 显示 */
    void onHandleGrabbed();
    /** 控件 -- 隐藏(释放) */
    void onHandleReleased();

    /** 控件 -- 开始滑动 */
    void onScrollStarted();
    /** 控件 -- 结束滑动 */
    void onScrollFinished();
}
