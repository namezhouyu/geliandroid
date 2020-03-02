package com.geli.m.coustomview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Steam_l on 2017/12/19.
 * 上面两个(左大右小)下面三个
 * 左边两个小右边大
 */

public class AdvertisingView3 extends BaseAdvertisingView {


    public AdvertisingView3(Context context) {
        this(context, null);
    }

    public AdvertisingView3(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdvertisingView3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    int imgNumber() {
        return 5;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();

            if (i == 0) {
                layoutParams.width = (int) (mWinWidth * 0.587);
                layoutParams.height = (int) (layoutParams.width * 0.545);
                double leftMargin = mWinWidth * 0.04;
                childAt.layout((int) leftMargin, 0, (int) (layoutParams.width + leftMargin), layoutParams.height);

            } else if (i == 1) {
                layoutParams.width = (int) (mWinWidth * 0.32);
                layoutParams.height = layoutParams.width;
                View preView = getChildAt(i - 1);
                childAt.layout(preView.getRight() + (int) (mWinWidth * 0.014), 0, preView.getRight() + (int) (mWinWidth * 0.014) + layoutParams.width, layoutParams.height);

            } else if (i == 2 || i == 3) {
                layoutParams.width = (int) (mWinWidth * 0.18);
                layoutParams.height = (int) (layoutParams.width * 1.48);
                View preView = getChildAt(i - 1);
                if (i == 2) {
                    childAt.layout((int) (mWinWidth * 0.04), preView.getBottom() + (int) (mWinWidth * 0.014), (int) (layoutParams.width + (mWinWidth * 0.04)), preView.getBottom() + (int) (mWinWidth * 0.014) + layoutParams.height);
                } else {
                    childAt.layout((int) (preView.getRight() + (mWinWidth * 0.014)), preView.getTop(), (int) (preView.getRight() + (mWinWidth * 0.014) + layoutParams.width), preView.getBottom());
                }

            } else {
                layoutParams.width = (int) (mWinWidth * 0.534);
                layoutParams.height = (int) (mWinWidth * 0.5);
                View preView = getChildAt(i - 1);
                childAt.layout(preView.getRight() + (int) (mWinWidth * 0.014), preView.getTop(), preView.getRight() + (int) (mWinWidth * 0.014) + layoutParams.width, preView.getBottom());
            }

            childAt.setLayoutParams(layoutParams);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, (int) (((mWinWidth * 0.587) * 0.545) + (mWinWidth * 0.014) + ((mWinWidth * 0.18) * 1.48)));
    }
}
