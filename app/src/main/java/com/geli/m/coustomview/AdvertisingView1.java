package com.geli.m.coustomview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Steam_l on 2017/12/19.
 * 左边一个右边两个
 *
 */
public class AdvertisingView1 extends BaseAdvertisingView {

    public AdvertisingView1(Context context) {
        this(context, null);
    }

    public AdvertisingView1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdvertisingView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    int imgNumber() {
        return 3;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            RelativeLayout.LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();

            if (i == 0) {               /* 第一个"图片控件" */
                layoutParams.width = (int) (mWinWidth * 0.64 + .5f);
                layoutParams.height = (int) (layoutParams.width * 0.625 + .5f);
                double leftMargin = mWinWidth * 0.04 + .5f;
                childAt.layout((int) leftMargin, 0, (int) (layoutParams.width + leftMargin), layoutParams.height);

            } else if (i == 1) {
                layoutParams.width = (int) (mWinWidth * 0.27 + .5f);
                layoutParams.height = (int) (layoutParams.width * 0.9 + .5f);
                View preView = getChildAt(i - 1);
                childAt.layout(preView.getRight() + (int) (mWinWidth * 0.014 + .5f), 0, preView.getRight() + (int) (mWinWidth * 0.014) + layoutParams.width, layoutParams.height);

            } else {
                layoutParams.width = (int) (mWinWidth * 0.27 + .5f);
                layoutParams.height = (int) (layoutParams.width * 0.55);
                View preView = getChildAt(i - 1);
                childAt.layout(preView.getLeft(), preView.getBottom() + (int) (mWinWidth * 0.014), preView.getRight(), preView.getBottom() + (int) (mWinWidth * 0.014) + layoutParams.height);
            }
            childAt.setLayoutParams(layoutParams);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, (int) ((mWinWidth * 0.64 + .5f) * 0.625 + .5f));
    }
}
