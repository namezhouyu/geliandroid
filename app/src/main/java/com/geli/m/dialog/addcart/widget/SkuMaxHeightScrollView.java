package com.geli.m.dialog.addcart.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;
import com.geli.m.utils.ScreenUtils;

/**
 * Sku Item ScrollView
 * <p>
 * 解决Sku过多时，选择界面铺满全屏的问题
 * <p>
 * Created by wuhenzhizao on 15/12/11.
 */
public class SkuMaxHeightScrollView extends ScrollView {

    public SkuMaxHeightScrollView(Context context) {
        super(context);
    }

    public SkuMaxHeightScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = 0;

        // 获取 -- 最高的子空间的"高"
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height)
                height = h;
        }

        float heightDp = ScreenUtils.px2Dp(getContext(), height);

        // 限定”当前控件”的高度再 --  75dp~250dp
        if (heightDp > 250) {
            int maxHeight = ScreenUtils.dp2PxInt(getContext(), 250);
            setMeasuredDimension(width, maxHeight);

        } else if (heightDp < 75) {
            int minHeight = ScreenUtils.dp2PxInt(getContext(), 75);
            setMeasuredDimension(width, minHeight);

        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
