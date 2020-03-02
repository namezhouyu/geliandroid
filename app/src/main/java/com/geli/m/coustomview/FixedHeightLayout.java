package com.geli.m.coustomview;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.geli.m.utils.Utils;

/**
 * Created by Steam_l on 2017/12/30.
 */

public class FixedHeightLayout extends FrameLayout {

    private int mMaxHeight;

    public FixedHeightLayout(@NonNull Context context) {
        this(context, null);
    }

    public FixedHeightLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FixedHeightLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMaxHeight = Utils.dip2px(context, 296);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (size > mMaxHeight) {
            setMeasuredDimension(widthMeasureSpec, mMaxHeight);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
