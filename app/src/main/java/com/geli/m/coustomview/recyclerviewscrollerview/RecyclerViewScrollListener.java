package com.geli.m.coustomview.recyclerviewscrollerview;

import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * 负责在"用户"滚动RecyclerView时 -- 更新"快速滚动条" -- 的监听器
 */
public class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    /** 快速滚动条 */
    private final FastScroller mScroller;

    List<ScrollerListener> mListenerList = new ArrayList<>();

    /** 之前 -- 滑动状态 -- 默认"没滑动" */
    int mOldScrollState = RecyclerView.SCROLL_STATE_IDLE;


    public RecyclerViewScrollListener(FastScroller scroller) {
        mScroller = scroller;
    }

    public void addScrollerListener(ScrollerListener listener){
        mListenerList.add(listener);
    }


    /*-----------------------  RecyclerView 滑动监听 start  --------------------------------*/
    /** 滑动状态改变了 */
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newScrollState) {
        super.onScrollStateChanged(recyclerView, newScrollState);


        if(newScrollState == RecyclerView.SCROLL_STATE_IDLE &&       /* 旧状态--滚动 ; 新状态--不滚动 */
                mOldScrollState != RecyclerView.SCROLL_STATE_IDLE){
            mScroller.getViewProvider().onScrollFinished();     // "快速滚动条" -- 结束滚动

        } else if(newScrollState!= RecyclerView.SCROLL_STATE_IDLE &&   /* 旧状态--不滚动 ; 新状态--滚动 */
                mOldScrollState == RecyclerView.SCROLL_STATE_IDLE){
            mScroller.getViewProvider().onScrollStarted();      // "快速滚动条" -- 开始滚动
        }

        mOldScrollState = newScrollState;
    }

    /** 正在滚动中 */
    @Override
    public void onScrolled(RecyclerView rv, int dx, int dy) {
        if(mScroller.shouldUpdateHandlePosition()) {
            updateHandlePosition(rv);
        }
    }
    /*-----------------------  RecyclerView 滑动监听 end  --------------------------------*/

    /**
     * 根据 RecyclerView 的滚动情况来设置"快速滚动条"的滑动位置
     * @param rv
     */
    void updateHandlePosition(RecyclerView rv) {
        float relativePos;

        if(mScroller.isVertical()) {
            int offset = rv.computeVerticalScrollOffset();      // 上部偏移(已偏移)的长度
            int extent = rv.computeVerticalScrollExtent();      // 可见长度
            int range = rv.computeVerticalScrollRange();        // 全部长度
            relativePos = offset / (float)(range - extent);     // 已偏移长度 / 全部不可见的长度

        } else {
            int offset = rv.computeHorizontalScrollOffset();
            int extent = rv.computeHorizontalScrollExtent();
            int range = rv.computeHorizontalScrollRange();
            relativePos = offset / (float)(range - extent);
        }

        mScroller.setScrollerPosition(relativePos);
        notifyListeners(relativePos);
    }

    /**
     * 循环监听器列表 -- 设置滑动距离
     * @param relativePos   RecyclerView已偏移长度 / RecyclerView全部不可见的长度
     */
    public void notifyListeners(float relativePos){
        for(ScrollerListener listener : mListenerList)
            listener.onScroll(relativePos);
    }

    public interface ScrollerListener {
        /**
         * 设置滑动距离
         * @param relativePos RecyclerView已偏移长度 / RecyclerView全部不可见的长度
         */
        void onScroll(float relativePos);
    }

}
