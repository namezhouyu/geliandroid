package com.geli.m.coustomview;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.geli.m.orther.SwipeDeleteManager;

/**
 * Created by Steam_l on 2017/12/30.
 *
 * 左右移动，显示"删除按钮"
 */
public class SwipeDeleteItem extends FrameLayout {

    private ViewDragHelper viewDragHelper;

    /** 子控件中的 -- 内容布局 */
    private View contentView;
    /** 子控件中的 -- 删除布局 */
    private View deleteView;
    /** 内容布局的"高" */
    private int contentHeight;
    /** 内容布局的"宽" */
    private int contentWidth;
    /** 删除布局的"高" */
    private int deleteHeight;
    /** 删除布局的"宽" */
    private int deleteWidth;

    /** 按下的时间的 xy */
    float downX, downY;//按下的x y
    private float upX;
    private float upY;
    private float moveX;
    private float moveY;
    private float downIX;
    private float downIY;

    final int close = 1 << 0;
    final int open = 1 << 1;

    //默认状态是关闭
    private int state = close;
    private float mLastY;
    private float mLastX;
    private float mStartY;
    private float mStartX;



    public SwipeDeleteItem(Context context) {
        this(context, null);
    }

    public SwipeDeleteItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeDeleteItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        viewDragHelper = ViewDragHelper.create(this, 1.0f, callback);
    }

    /**
     * 此控件的结束标签读取完毕
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);        // 获取子控件
        deleteView = getChildAt(1);
    }

    /**
     * onMeasure 已经执行完毕 可以直接获取孩子宽高
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        contentHeight = contentView.getMeasuredHeight();
        contentWidth = contentView.getMeasuredWidth();
        deleteWidth = deleteView.getMeasuredWidth();
        deleteHeight = deleteView.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e("item", "content" + contentWidth + SwipeDeleteManager.getInstance().haveOpened());
        contentView.layout(0, 0, contentWidth, contentHeight);
        deleteView.layout(contentWidth, 0, contentWidth + deleteWidth, deleteHeight);

        if (SwipeDeleteManager.getInstance().haveOpened()) {        // 有打开的都关闭一下
            SwipeDeleteManager.getInstance().clear();
        }
    }


    ItemOnclickListener mItemOnclickListener;

    public interface ItemOnclickListener {
        void onclick();
    }

    public void setItemOnclickListener(ItemOnclickListener listener) {
        mItemOnclickListener = listener;
    }



    // 这个方法用来拦截TouchEvent
    // 如果 interceptTouchEvent 返回"true"，
    //		也就是拦截掉了，则交给它的 onTouchEvent 来处理，
    // 如果 interceptTouchEvent 返回"false"，
    //      那么就传递给子 view ，由子 view 的 dispatchTouchEvent 再来开始这个事件的分发，依次向下传递。
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        boolean value = viewDragHelper.shouldInterceptTouchEvent(event);
        if (!SwipeDeleteManager.getInstance().haveOpened(this)) {   // d如果打开的不是当前的item 关闭
            SwipeDeleteManager.getInstance().close();
        }

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:           /* 按下 */
                downIX = event.getX();
                downIY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:           /* 滑动 */
                moveX = event.getX();
                moveY = event.getY();
                // 如果"不是点击事件"，那么将事件拦截 -- 这里判断dx dy >1就是判断是否是点击事件
                if (Math.abs(moveX - downIX) > 1 || Math.abs(moveY - downIY) > 1) {
                    value = true;       // 拦截掉了，则交给它的 onTouchEvent 来处理，
                }
                mLastX = event.getX();
                mLastY = event.getY();
                break;
        }
        return value;
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (SwipeDeleteManager.getInstance().haveOpened(this)) {    /* 如果已经有item打开 不允许父控件拦截事件 */
            // 如果触摸的是打开的item 让viewDragHelper处理触摸事件
            requestDisallowInterceptTouchEvent(true);// 请求父控件不要拦截事件

        } else if (SwipeDeleteManager.getInstance().haveOpened()) {   /* 如果触摸的不是当前打开的item 直接消耗事件 不让item滑动 */
            requestDisallowInterceptTouchEvent(true);   // 请求父控件不要拦截事件
            return true;
        }

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:                           /* 按下 */
                downX = event.getX();
                downY = event.getY();
                mStartX = event.getX();
                mStartY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:                           /* 滑动 */
                float moveX = event.getX();
                float moveY = event.getY();
                float dx = Math.abs(moveX - downX);
                float dy = Math.abs(moveY - downY);
                if (dx > dy) {
                    // 如果 x距离大于y 则不允许父控件拦截事件
                    requestDisallowInterceptTouchEvent(true);
                } else {
                    // y>x 关闭
                    SwipeDeleteManager.getInstance().close();
                    requestDisallowInterceptTouchEvent(false);
                    return false;       // 事件传递回上一个控件的"onTouchEvent"
                }
                downX = moveX;
                downY = moveY;
                break;

            case MotionEvent.ACTION_UP:                             /* 抬起手 */
                if (mStartX == event.getX() && mStartY == event.getY()                  // 只是"点击"没有"移动"
                        && !SwipeDeleteManager.getInstance().haveOpened(this)) {
                    if (mItemOnclickListener != null) {
                        mItemOnclickListener.onclick();
                    }
                }
                break;
        }


        viewDragHelper.processTouchEvent(event);
        return true;
    }



    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        // 通过DragHelperCallback的tryCaptureView方法的返回值可以决定一个parentview中哪个子view可以拖动，
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return (child == contentView || child == deleteView) && SwipeDeleteManager.getInstance().getCanScroll();
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            /* 伴随移动 */
            if (changedView == contentView) {                      /* 移动"内容布局"，同时设置"删除布局" */
                deleteView.layout(deleteView.getLeft() + dx, deleteView.getTop() + dy,
                        deleteView.getRight() + dx, deleteView.getBottom() + dy);

            } else if (changedView == deleteView) {                /* 移动"删除布局"，同时设置"内容布局" */
                contentView.layout(contentView.getLeft() + dx, contentView.getTop() + dy,
                        contentView.getRight() + dx, contentView.getBottom() + dy);
            }

            //如果 item getLeft不等于0 就认为item已经打开 不允许其它item滑动
            if (contentView.getLeft() != 0)                                /* 打开"删除布局了" */
                SwipeDeleteManager.getInstance().setSwipeDeleteItem(SwipeDeleteItem.this);  // 将这一项设置到，"删除管理"中
            else                                                           /* 没有打开"删除布局了" */
                SwipeDeleteManager.getInstance().clear();                                   // 删除管理中，删除项置为null


            if (contentView.getLeft() == 0 && state != close)                       /* 没打开 */
                state = close;
            else if (contentView.getLeft() == -deleteWidth && state != open)        /* 已经打开 */
                state = open;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return deleteWidth;
        }


        /* 处理横向的拖动： */
        // 返回一个适当的数值就能实现横向拖动效果，
        // clampViewPositionHorizontal的第二个参数是指当前拖动子view应该到达的x坐标。
        // 所以按照常理这个方法原封返回第二个参数就可以了，
        // 但为了让被拖动的view遇到边界之后就不在拖动，对返回的值做了更多的考虑。
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            // 限定滑动的范围
            if (child == contentView) {                 /* 拖动的控件是 -- 内容布局 -- 超过某些界线，设置下 */
                if (left > 0)
                    left = 0;

                if (left < -deleteWidth)
                    left = -deleteWidth;

            } else if (child == deleteView) {           /* 拖动的控件是 -- 删除布局 */
                if (left > contentWidth)
                    left = contentWidth;

                if (left < contentWidth - deleteWidth)
                    left = contentWidth - deleteWidth;
            }
            return left;
        }

        // 当速度达到一定的值的时候 直接打开或者关闭
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            // 当速度达到一定的值的时候 直接打开或者关闭
            if (xvel < -2000) {
                open();
                return;
            }

            if (xvel > 2000) {
                close();
                return;
            }

            if (contentView.getLeft() > - deleteWidth / 2) {
                close();
            } else {
                open();
            }
        }
    };

    public void open() {
        Log.e("item", "动画打开");
        viewDragHelper.smoothSlideViewTo(contentView, -deleteWidth, contentView.getTop());
        state = open;
        SwipeDeleteManager.getInstance().setSwipeDeleteItem(SwipeDeleteItem.this);
        ViewCompat.postInvalidateOnAnimation(SwipeDeleteItem.this);
    }

    public void close() {
        Log.e("item", "动画关闭");
        viewDragHelper.smoothSlideViewTo(contentView, 0, contentView.getTop());
        ViewCompat.postInvalidateOnAnimation(SwipeDeleteItem.this);
    }

    /**
     * 重写此方法刷新动画
     */
    @Override
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
