package com.geli.m.mvp.view;

import android.content.Context;
import android.util.AttributeSet;

import com.jude.easyrecyclerview.EasyRecyclerView;

/**
 * author:  shen
 * date:    2018/12/29
 */
public class MyTestEasyRecyclerView extends EasyRecyclerView {
    public MyTestEasyRecyclerView(Context context) {
        super(context);
    }

    public MyTestEasyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTestEasyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 重写该方法 适应ScrollView的效果
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
