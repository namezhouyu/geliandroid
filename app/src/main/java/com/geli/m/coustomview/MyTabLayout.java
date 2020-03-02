package com.geli.m.coustomview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.geli.m.R;

/**
 * Created by Steam_l on 2018/1/3.
 *
 * 使用线性布局 -- 做MyTabLayout
 */
public class MyTabLayout extends LinearLayout {

    Context mContext;

    /** 指示器 -- 这里使用 ImageView  */
    View indicatorView;
    /** 指示器的高 */
    private int mIndiocatorHeight;
    /** 指示器的宽 */
    private int mIndicatorWidth;

    /** 每个标签的 -- 高 */
    private int mChildHeight;
    /** 每个标签的 -- 宽 */
    private int mChildWidth;
    private int mStartX;

    /** 标题个数 */
    private int tabNumber;

    /** 指示器颜色 -- 未选中颜色 */
    private int normalColor;
    /** 指示器颜色 -- 已选中颜色 */
    private int selectColor;

    private ViewGroup mTabViewGroup;

    public MyTabLayout(Context context) {
        this(context, null);
    }

    public MyTabLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = getContext();

        normalColor = 0xFF2e2e2e;
        selectColor = 0xFFe75d5d;

        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.MyTabLayout);
        try {
            normalColor = array.getColor(R.styleable.MyTabLayout_tl_normalColor, normalColor);
            selectColor = array.getColor(R.styleable.MyTabLayout_tl_selectColor, selectColor);
        } finally {
            array.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof ViewGroup) {
                mTabViewGroup = (ViewGroup) childAt;
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);

            if (childAt instanceof ImageView) {                         /* 控件 -- 一般是继承 View */
                indicatorView = childAt;
                mIndicatorWidth = childAt.getMeasuredWidth();
                mIndiocatorHeight = childAt.getMeasuredHeight();

            } else if (childAt instanceof ViewGroup) {                  /* 布局控件 -- 一般是继承ViewGroup */
                mChildHeight = childAt.getMeasuredHeight();
                tabNumber = 0;
                for (int j = 0; j < ((ViewGroup) childAt).getChildCount(); j++) {
                    View view = ((ViewGroup) childAt).getChildAt(j);
                    if (view.getVisibility() != GONE) {         // 只要不是 "隐藏" ，就算是"标签"一个
                        tabNumber++;
                    }
                }
            }
        }
        mChildWidth = getWidth() / tabNumber;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof ImageView) {
                mStartX = (mChildWidth - mIndicatorWidth) / 2;
                childAt.layout(mStartX,
                        mChildHeight,
                        mStartX + mIndicatorWidth,
                        mIndiocatorHeight + mChildHeight);
            }
        }
    }


    /**
     * 设置传入的 ViewPager 的滑动事件和"指示器"关联
     * @param viewPager
     */
    public void setViewPager(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 设置"指示器"跟随，ViewPager的滑动而动
                indicatorView.setX(mStartX + position * mChildWidth + mChildWidth * positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mTabViewGroup.getChildCount(); i++) {
                    TextView childAt = (TextView) mTabViewGroup.getChildAt(i); // 每一个标签都是"文本控件"

                    if (i == position) {                        /* 当前选中的(页)，对应的"标签" */
                        childAt.setTextColor(selectColor);
                    } else {
                        childAt.setTextColor(normalColor);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setCurrItem(0);
    }

    /**
     * 设置"选中项"
     * @param position
     */
    public void setCurrItem(int position) {
        for (int i = 0; i < mTabViewGroup.getChildCount(); i++) {
            TextView childAt = (TextView) mTabViewGroup.getChildAt(i);

            if (i == position) {
                childAt.setTextColor(selectColor);
            } else {
                childAt.setTextColor(normalColor);
            }
        }

    }

}
