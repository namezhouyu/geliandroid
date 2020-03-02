package com.geli.m.coustomview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Steam_l on 2018/2/1.
 *
 */
public class SelectLayout extends FrameLayout {

    private final ViewDragHelper mDragHelper;
    Context mContext;

    private View mContentView;
    private View mLeftView;

    private int mContentWidth;
    private int mLeftWidth;

    public boolean isOpen = false;

    public SelectLayout(Context context) {
        this(context, null);
    }

    public SelectLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        // 第一个为当前的ViewGroup，
        // 第二个为sensitivity，主要用于设置touchSlop：helper.mTouchSlop = (int) (helper.mTouchSlop * (1 / sensitivity)); 传入越大，touchSlop就越小
        // 参数3:
        //      tryCaptureView：如果返回true表示捕获相关View，你可以根据第一个参数child决定捕获哪个View。
        //      clampViewPositionVertical：计算child垂直方向的位置，top表示y轴坐标（相对于ViewGroup），默认返回0（如果不复写该方法）。这里，你可以控制垂直方向可移动的范围。
        //      clampViewPositionHorizontal：与clampViewPositionVertical类似，只不过是控制水平方向的位置。

        mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }


            // 这个是当changedView的位置发生变化时调用，我们可以在这里面控制View的显示位置和移动。
            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);

                if (changedView == mContentView) {
                    mLeftView.layout(mLeftView.getLeft() + dx, mLeftView.getTop() + dy,
                            mLeftView.getRight() + dx, mLeftView.getBottom() + dy);
                } else {
                    mContentView.layout(mContentView.getLeft() + dx, mContentView.getTop() + dy,
                            mContentView.getRight() + dx, mContentView.getBottom() + dy);
                }
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLeftView = getChildAt(0);              // 第一个控件
        mContentView = getChildAt(1);           // 第二个控件
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mLeftWidth = mLeftView.getMeasuredWidth();
        mContentWidth = mContentView.getMeasuredWidth();
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        // 根据"打开"或是"关闭"设置布局的位置
        if (!isOpen) {
            mLeftView.layout(-mLeftWidth, 0, 0, mLeftView.getMeasuredHeight());
            FrameLayout.LayoutParams layoutParams = (LayoutParams) mContentView.getLayoutParams();
            mContentView.layout(
                    layoutParams.leftMargin,
                    layoutParams.topMargin,
                    mContentWidth + layoutParams.rightMargin,
                    mContentView.getMeasuredHeight() + layoutParams.bottomMargin);

        } else {
            mLeftView.layout(0, 0, mLeftWidth, mLeftView.getMeasuredHeight());
            FrameLayout.LayoutParams layoutParams = (LayoutParams) mContentView.getLayoutParams();
            mContentView.layout(
                    mLeftWidth + layoutParams.leftMargin,
                    layoutParams.topMargin,
                    mLeftWidth + mContentWidth + layoutParams.rightMargin,
                    mContentView.getMeasuredHeight() + layoutParams.bottomMargin);
        }

    }



//    postInvalidate方法是将任务添加到队列中排队后立即执行的，而postInvalidateOnAnimation依赖上一帧动画的的执行时间，因为动画的刷新是存在一个频率的，直到下一帧动画的时间才会真正执行刷新操作。
//    而postIfNeededLocked()干的事情就是把mInvalidateOnAnimationRunnable作为Choreographer.CALLBACK_ANIMATION(这个类型的task会在mesaure/layout/draw之前被运行)的Task 交给 UI线程的Choreographer.
//    View的invalidate会进一步触发ViewRootImpl的invalidateChildInParent()->invalidate()<一种情况(dirty == null 表示全部重绘)，不过另外一种差不多>:
//    不过虽然没有发出去，不代表这次invalidate就不会生效,因为前面的invalidate()里已经设置了mDirty了:

    public void open() {
        isOpen = true;
        mDragHelper.smoothSlideViewTo(mLeftView, 0, 0);     // 左边控件移位
        ViewCompat.postInvalidateOnAnimation(this);         // 执行"右移动动画"再执行"刷新"
    }

    public void close() {
        isOpen = false;
        mDragHelper.smoothSlideViewTo(mLeftView, -mLeftWidth, 0);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
