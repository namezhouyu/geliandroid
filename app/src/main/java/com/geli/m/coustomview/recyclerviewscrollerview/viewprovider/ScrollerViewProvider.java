package com.geli.m.coustomview.recyclerviewscrollerview.viewprovider;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import com.geli.m.coustomview.recyclerviewscrollerview.FastScroller;


/**
 *
 * 为FastScroller的 "滚动条中的滚条"  提供{@link View}及其行为。
 */
public abstract class ScrollerViewProvider {

    /** 快速滚动条 */
    private FastScroller mFastScroller;
    /** 滚动条中的滚条 -- 行为 */
    private ViewBehavior mHandleBehavior;

    public void setFastScroller(FastScroller scroller){
        this.mFastScroller = scroller;
    }

    protected Context getContext(){
        return mFastScroller.getContext();
    }

    protected FastScroller getFastScroller() {
        return mFastScroller;
    }

    /**
     * 返回一个 "快速滚动条的滚条" 控件
     *
     * @param container The container {@link FastScroller} for the mView to inflate properly.
     * @return A mView which will be by the {@link FastScroller} used as a mViewHandle.
     */
    public abstract View provideHandleView(ViewGroup container);

    /**
     * 生成一个 "快速滚动条的滚条"控件 的 "行为"
     * @return  控件的行为类
     */
    @Nullable
    protected abstract ViewBehavior provideHandleBehavior();


    /**
     * 获取"快速滚动条的滚条"控件的 -- 行为
     * @return
     */
    protected ViewBehavior getHandleBehavior(){
        if(mHandleBehavior ==null) mHandleBehavior = provideHandleBehavior();
        return mHandleBehavior;
    }


    /** 控件 -- 显示 */
    public void onHandleGrabbed(){
        if(getHandleBehavior()!=null) getHandleBehavior().onHandleGrabbed();
    }

    /** 控件 -- 隐藏(释放) */
    public void onHandleReleased(){
        if(getHandleBehavior()!=null) getHandleBehavior().onHandleReleased();
    }

    /** 控件 -- 开始滑动 */
    public void onScrollStarted(){
        if(getHandleBehavior()!=null) getHandleBehavior().onScrollStarted();
    }

    /** 控件 -- 结束滑动 */
    public void onScrollFinished(){
        if(getHandleBehavior()!=null) getHandleBehavior().onScrollFinished();
    }

}
