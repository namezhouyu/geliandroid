package com.geli.m.coustomview.recyclerviewscrollerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import com.geli.m.R;
import com.geli.m.coustomview.recyclerviewscrollerview.viewprovider.DefaultScrollerViewProvider;
import com.geli.m.coustomview.recyclerviewscrollerview.viewprovider.ScrollerViewProvider;


/**
 * 快速滚动条
 */
public class FastScroller extends LinearLayout {

    private static final int STYLE_NONE = -1;
    /** 负责在"用户"滚动RecyclerView时 -- 更新"快速滚动条" -- 的监听器 */
    private final RecyclerViewScrollListener mScrollListener = new RecyclerViewScrollListener(this);
    private RecyclerView mRecyclerView;


    /** 快速滚动条 -- 里面的滚条 */
    private View mViewHandle;
    /** 滚动条的滚条 -- 颜色 */
    private int mHandleColor;

    /** 滚动条的方向 -- VERTICAL  HORIZONTAL */
    private int mScrollerOrientation;

    /** 本视图是否可见 -- 视图的可见性状态。 -- TODO名称应该是固定的，还要检查是否有更好的方法来处理可见性，因为这有点复杂 */
    private int mMaxVisibility;
    /** 是否"手动(拖动滚动条)" -- 改变位置 */
    private boolean mManuallyChangingPosition;

    /** FastScroller的 "滚动条中的滚条" 和 "气泡" 提供 -- "视图"及"其行为"。 */
    private ScrollerViewProvider mViewProvider;

    public FastScroller(Context context) {
        this(context, null);
    }

    public FastScroller(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FastScroller(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setClipChildren(false); // 默认情况下，绘制前将子对象剪裁到其边界。这允许视图组覆盖动画等的此行为。

        TypedArray style = context.obtainStyledAttributes(attrs,
                R.styleable.fastscroll__fastScroller,
                R.attr.fastscroll__style, 0);
        try {
            mHandleColor = style.getColor(R.styleable.fastscroll__fastScroller_fastscroll__handleColor, STYLE_NONE);
        } finally {
            style.recycle();
        }

        mMaxVisibility = getVisibility();                   // 返回此视图的可见性状态。
        setViewProvider(new DefaultScrollerViewProvider());
    }


    /**
     * 设置 -- "滚动条中的滚条" 和 "气泡"
     *
     * Enables custom layout for {@link FastScroller}.
     * @param viewProvider A {@link ScrollerViewProvider} for the {@link FastScroller} to use when building layout.
     */
    public void setViewProvider(ScrollerViewProvider viewProvider) {
        removeAllViews();

        mViewProvider = viewProvider;
        viewProvider.setFastScroller(this);
        mViewHandle = viewProvider.provideHandleView(this);
        addView(mViewHandle);
    }


    /**
     * Attach the {@link FastScroller} to {@link RecyclerView}. Should be used after the adapter is set
     * to the {@link RecyclerView}. If the adapter implements SectionTitleProvider, the FastScroller
     * will show a mViewBubble with title.
     * @param recyclerView A {@link RecyclerView} to attach the {@link FastScroller} to.
     */
    public void setRecyclerView(RecyclerView recyclerView) {

        mRecyclerView = recyclerView;

        recyclerView.addOnScrollListener(mScrollListener);      // rv滚动监听

        invalidateVisibility();
        // 注册一个监听，以便在此视图中 -- "添加"或"删除"子项时 -- 调用该回调。
        recyclerView.setOnHierarchyChangeListener(new OnHierarchyChangeListener() {
            /* 添加控件 -- 应该是rv移动时候 屏幕显示的控件 */
            @Override
            public void onChildViewAdded(View parent, View child) {
                invalidateVisibility();
            }

            /* 移除控件 -- 应该是rv移动时候 屏幕不见的控件  */
            @Override
            public void onChildViewRemoved(View parent, View child) {
                invalidateVisibility();
            }
        });
    }


    /**
     * 设置方向 -- VERTICAL : HORIZONTAL <p>
     *
     * Set the orientation of the {@link FastScroller}. The orientation of the {@link FastScroller}
     * should generally match the orientation of connected  {@link RecyclerView} for good UX but it's not enforced.
     * Note: This method is overridden from {@link LinearLayout#setOrientation(int)} but for {@link FastScroller}
     * it has a totally different meaning.
     * @param orientation of the {@link FastScroller}. {@link #VERTICAL} or {@link #HORIZONTAL}
     */
    @Override
    public void setOrientation(int orientation) {
        mScrollerOrientation = orientation;
        super.setOrientation(orientation == HORIZONTAL ? VERTICAL : HORIZONTAL);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        initHandleMovement();

        applyStyling();             // 设置 "气泡/滚动条的滚条"背景  设置 气泡文本

        if (!isInEditMode()) {      // 如果不是 "编辑模式"
            //sometimes recycler starts with a defined scroll (e.g. when coming from saved state)
            mScrollListener.updateHandlePosition(mRecyclerView);
        }
    }



    /**
     * 设置 "滚动条的滚条"控件的"背景颜色"
     * @param color Color in hex notation with alpha channel, e.g. 0xFFFFFFFF
     */
    public void setHandleColor(int color) {
        mHandleColor = color;
        invalidate();
    }


    /**
     * 添加    负责在"用户"滚动RecyclerView时 -- 更新"快速滚动条" -- 的监听器
     * @param listener
     */
    public void addScrollerListener(RecyclerViewScrollListener.ScrollerListener listener){
        mScrollListener.addScrollerListener(listener);
    }


    /**
     * 设置 "气泡/滚动条的滚条"背景  设置 气泡文本
     */
    private void applyStyling() {
        if(mHandleColor != STYLE_NONE) setBackgroundTint(mViewHandle, mHandleColor);
    }

    private void setBackgroundTint(View view, int color) {
        final Drawable background = DrawableCompat.wrap(view.getBackground());
        if(background==null) return;
        DrawableCompat.setTint(background.mutate(), color);
        FastScrollUtils.setBackground(view, background);
    }


    /**
     * 设置 "滚动条的滚条" 的"触动事件"
     */
    private void initHandleMovement() {

        mViewHandle.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!FastScroller.this.isEnabled()){
                    return false;
                }

                requestDisallowInterceptTouchEvent(true);
                if (event.getAction() == MotionEvent.ACTION_DOWN ||             // 按下
                        event.getAction() == MotionEvent.ACTION_MOVE) {         // 移动

                    mManuallyChangingPosition = true;           // 是 -- "手动(拖动滚动条)"改变位置
                    float relativePos = getRelativeTouchPosition(event);   // 移动的距离 / (滚动条高{宽} - 滚条高{宽})
                    setScrollerPosition(relativePos);
                    setRecyclerViewPosition(relativePos);
                    return true;

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mManuallyChangingPosition = false;
                    return true;
                }
                return false;
            }
        });
    }


    /**
     * 获取相对位置 -- 移动的距离 / (滚动条高{宽} - 滚条高{宽})
     *
     * @param event
     * @return
     */
    private float getRelativeTouchPosition(MotionEvent event){
        if(isVertical()){
            // event.getRawY()                              移动到屏幕的 Y
            // FastScrollUtils.getViewRawY(mViewHandle)     获取"本控件"在屏幕的"Y坐标"
            // yInParent                                    移动的距离
            // getHeight() - mViewHandle.getHeight()        滚动条高 - 滚条高
            float yInParent = event.getRawY() - FastScrollUtils.getViewRawY(mViewHandle);
            return yInParent / (getHeight() - mViewHandle.getHeight());
        } else {
            float xInParent = event.getRawX() - FastScrollUtils.getViewRawX(mViewHandle);
            return xInParent / (getWidth() - mViewHandle.getWidth());
        }
    }

    @Override
    public void setVisibility(int visibility) {
        mMaxVisibility = visibility;
        invalidateVisibility();
    }


    private void invalidateVisibility() {
        if(mRecyclerView.getAdapter() == null ||                       // rv 没有数据
                mRecyclerView.getAdapter().getItemCount() == 0 ||      // rv 没有数据
                mRecyclerView.getChildAt(0) == null ||           // rv 第一个数据为空
                isRecyclerViewNotScrollable() ||                       // 有"滑动条"
                mMaxVisibility != View.VISIBLE){                       // 本视图不可见
            // super.setVisibility(INVISIBLE);
            super.setVisibility(GONE);

        } else {
            super.setVisibility(VISIBLE);
        }
    }

    /**
     * 有没有"滑动条"  -- FALSE: 没有(数据太少，所有项高度/宽度 小于 Rv本身)
     * @return
     */
    private boolean isRecyclerViewNotScrollable() {
        if(isVertical()) {                                          /* 垂直 */
            return mRecyclerView.getChildAt(0).getHeight()
                    * mRecyclerView.getAdapter().getItemCount()
                    //<= mRecyclerView.getHeight() + mRecyclerView.getPaddingLeft() + mRecyclerView.getPaddingRight();
                    <= mRecyclerView.getMeasuredHeight();
        } else {                                                    /* 水平 */
            return mRecyclerView.getChildAt(0).getWidth()
                    * mRecyclerView.getAdapter().getItemCount()
                    //<= mRecyclerView.getWidth() + mRecyclerView.getPaddingLeft() + mRecyclerView.getPaddingRight();;
                    <= mRecyclerView.getMeasuredWidth();
        }
    }


    /**
     * 设置 "Rv" 的位置
     *
     * @param relativePos 移动的距离 / (滚动条高{宽} - 滚条高{宽})
     */
    private void setRecyclerViewPosition(float relativePos) {
        if (mRecyclerView == null)
            return;

        int itemCount = mRecyclerView.getAdapter().getItemCount();
        int targetPos = (int) FastScrollUtils.getValueInRange(      // 哪一项
                0,
                itemCount - 1,
                (int) (relativePos * (float) itemCount));

        mRecyclerView.scrollToPosition(targetPos);
    }


    /**
     * 设置 "气泡" 和 "滚动条的滚条" 的位置
     *
     * @param relativePos   移动的距离 / (滚动条高{宽} - 滚条高{宽})
     * @param relativePos   RecyclerView已偏移长度 / RecyclerView全部不可见的长度
     */
    void setScrollerPosition(float relativePos) {
        if(isVertical()) {                                          /* 垂直 */
            mViewHandle.setY(FastScrollUtils.getValueInRange(
                    0,
                    getHeight() - mViewHandle.getHeight(),
                    relativePos * (getHeight() - mViewHandle.getHeight()))
            );
        } else {                                                    /* 水平 */
            mViewHandle.setX(FastScrollUtils.getValueInRange(
                    0,
                    getWidth() - mViewHandle.getWidth(),
                    relativePos * (getWidth() - mViewHandle.getWidth()))
            );
        }
    }


    /**
     * 是不是垂直
     * @return
     */
    public boolean isVertical(){
        return mScrollerOrientation == VERTICAL;
    }


    /**
     * 是否应该改变 -- "滚动条中的滚条" 的 "位置" <p>
     * @return
     */
    boolean shouldUpdateHandlePosition() {
        return mViewHandle != null &&               // "滚动条中的滚条" 不为空
                !mManuallyChangingPosition &&       // 不是"手动"改变
                mRecyclerView.getChildCount() > 0;  // rv 有数据
    }


    /** 获取 -- FastScroller的 "滚动条中的滚条" 提供 -- "视图"及"其行为"。 */
    ScrollerViewProvider getViewProvider() {
        return mViewProvider;
    }
}
