package com.geli.m.coustomview.recyclerviewscrollerview.viewprovider;

import android.graphics.drawable.InsetDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.geli.m.R;
import com.geli.m.coustomview.recyclerviewscrollerview.FastScrollUtils;
import com.geli.m.coustomview.recyclerviewscrollerview.FastScroller;

/**
 * 生成 --  "快速滚动条的滚条"控件 <br>
 * 和他们的行为(移动动画)
 */
public class DefaultScrollerViewProvider extends ScrollerViewProvider {

    /** 快速滚动条 -- 里面的滚条 */
    protected View mViewHandle;



    /**
     *
     * 返回一个 "快速滚动条的滚条" 控件
     *
     * @param container The container {@link FastScroller} for the mView to inflate properly.
     * @return
     */
    @Override
    public View provideHandleView(ViewGroup container) {
        mViewHandle = new View(getContext());

        int verticalInset = getFastScroller().isVertical() ?
                0 :                      // 垂直 : 上下不设置边距
                0; // 水平 : 左右设置边距
        int horizontalInset = !getFastScroller().isVertical() ?
                0 :                      // 水平 : 左右不设置
                0; // 垂直 : 上下设置边距

        // 生成一个 Drawable  左上右下 边距距离(插入像素)
        InsetDrawable handleBg = new InsetDrawable(
                ContextCompat.getDrawable(getContext(), R.drawable.shape_bg_red_r40),
                horizontalInset, verticalInset,
                horizontalInset, verticalInset);

        FastScrollUtils.setBackground(mViewHandle, handleBg);     // 设置 滚动条的滚条 的 背景

        int handleWidth = getContext().getResources().getDimensionPixelSize(getFastScroller().isVertical() ?
                R.dimen.fastscroll__handle_width :        // 垂直 : 滚动条的滚条--宽度
                R.dimen.fastscroll__handle_height);                 // 水平 : 滚动条的滚条--宽度

        int handleHeight = getContext().getResources().getDimensionPixelSize(getFastScroller().isVertical() ?
                R.dimen.fastscroll__handle_height :                 // 垂直 : 滚动条的滚条--高度
                R.dimen.fastscroll__handle_width);        // 水平 : 滚动条的滚条--高度

        LinearLayout.LayoutParams params;
        if(getFastScroller().isVertical()){
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, handleHeight);
        }else {
            params = new LinearLayout.LayoutParams(handleWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        }

        mViewHandle.setLayoutParams(params);
        return mViewHandle;
    }

    @Override
    protected ViewBehavior provideHandleBehavior() {
        return null;
    }

}
