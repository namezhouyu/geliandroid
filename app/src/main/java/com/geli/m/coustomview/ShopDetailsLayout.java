package com.geli.m.coustomview;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.geli.m.R;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.utils.KeyBoardUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/**
 * Created by Steam_l on 2017/12/25.
 */

public class ShopDetailsLayout extends RelativeLayout implements NestedScrollingParent {

    private ViewDragHelper mDragHelper;
    /** 商品详情的底层布局的"头布局" */
    private LinearLayout mLl_shopdetails_top;
    /** 商品详情的上层布局(包含 -- 搜索、左边分类索引 + 右边具体商品) */
    private LinearLayout mLl_goodslayout;
    private int mLayoutHeight;
    /** 商品详情的底层布局的"头布局" 的 "高度" */
    private int mShopdetails_topheight;

    /** 商品详情的底层布局的"根布局" */
    private NestedScrollView mNsv_shopdetailslayout;
    public boolean mGoodsIsOpen = true;
    private NestedScrollingParentHelper mParentHelper;
    /** 商品详情的上层布 -- 右边具体商品 */
    private EasyRecyclerView mErv_content;
    /** 商品详情的上层布 -- 左边分类索引 */
    private EasyRecyclerView mErv_title;
    private boolean mTitleCanScrollTop;
    private boolean mContentCanScrollTop;
    /** 是否拦截nsv的标记 */
    private boolean mNsvIsScrollIng = false;
    /** 让父类拦截本view的操作 */
    private OnTouchListener mFalseScrollListener;

    /** "上层布局"是否在顶部(上层) */
    public boolean isTop = false;

    private float mStartY;
    private float mStartX;
    Context mContext;



    public ShopDetailsLayout(@NonNull Context context) {
        this(context, null);
    }

    public ShopDetailsLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShopDetailsLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }



    private void init() {
        mParentHelper = new NestedScrollingParentHelper(this);

        // 第一个为当前的ViewGroup，
        // 第二个为sensitivity，主要用于设置touchSlop：helper.mTouchSlop = (int) (helper.mTouchSlop * (1 / sensitivity)); 传入越大，touchSlop就越小
        // 参数3:
        //      tryCaptureView：如果返回true表示捕获相关View，你可以根据第一个参数child决定捕获哪个View。
        mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return false;
            }
        });

        mFalseScrollListener = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(false); // 父控件拦截事件
                return false;
            }
        };
    }


    /**
     * "上层布局" -- 滑动到"顶部"
     */
    public void moveTop() {
        // System.out.println("=============moveTop===============");
        isTop = true;
        if (mChangeListener != null) {
            mChangeListener.open();
        }

        mGoodsIsOpen = true;
        if (mDragHelper.smoothSlideViewTo(mLl_goodslayout, 0, 0)) { // 滑动上层布局 -- 去顶部
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


    /**
     * "上层布局" -- 滑动到离"顶部"相差 -- "底层"中的"头布局的高度" -- 的"距离"
     */
    public void moveInit() {
        // System.out.println("=============moveInit===============");
        isTop = false;
        if (mChangeListener != null) {
            mChangeListener.open();
        }
        mGoodsIsOpen = true;
        if (mDragHelper.smoothSlideViewTo(mLl_goodslayout, 0, mShopdetails_topheight)) { // 差不多滑动到顶部
            ViewCompat.postInvalidateOnAnimation(ShopDetailsLayout.this);
        }
        mLl_goodslayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mLl_goodslayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                // System.out.println("=============" + mLl_goodslayout.getTop() + "===============");
            }
        });
        //        ObjectAnimator animator = ObjectAnimator.ofInt(mNsv_shopdetailslayout, "scrollY", mNsv_shopdetailslayout.getScrollY(), 0);
        //        animator.setDuration(1000);
        //        animator.start();
        if (mNsv_shopdetailslayout.getScrollY() == 0) {
            return;
        }
        mNsvIsScrollIng = true;
        mNsv_shopdetailslayout.setOnTouchListener(mFalseScrollListener);
        Observable.timer(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mNsvIsScrollIng = false;
                        mNsv_shopdetailslayout.setOnTouchListener(null);
                        mNsv_shopdetailslayout.fullScroll(NestedScrollView.FOCUS_UP);
                        // mNsv_shopdetailslayout.smoothScrollTo(0, 0);
                    }
                });
    }


    /**
     * "上层布局" -- 滑动到"底部"
     */
    public void moveBottom() {
        // System.out.println("==============moveBottom==============");
        isTop = false;
        if (mChangeListener != null) {
            mChangeListener.close();
        }

        mGoodsIsOpen = false;
        if (mDragHelper.smoothSlideViewTo(mLl_goodslayout, 0, mLayoutHeight)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }



    public boolean isTouchView(View view, float x, float y) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        if (location[0] < x && x < location[0] + view.getWidth()
                && location[1] < y && y < location[1] + view.getHeight()) {
            return true;
        }
        return false;
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mNsvIsScrollIng || super.onInterceptTouchEvent(ev);
    }

    // onFinishInflate方法是在setContentView之后、onMeasure之前
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mLl_goodslayout = (LinearLayout) findViewById(R.id.ll_shopdetails_goodslayout);
        mLl_shopdetails_top = (LinearLayout) findViewById(R.id.ll_shopdetails_toplayout);
        mNsv_shopdetailslayout = (NestedScrollView) findViewById(R.id.nsv_shopdetails_details);
        mErv_content = (EasyRecyclerView) findViewById(R.id.erv_shopdetails_goods_content);
        mErv_title = (EasyRecyclerView) findViewById(R.id.erv_shopdetails_goods_title);
        ViewCompat.setNestedScrollingEnabled(mNsv_shopdetailslayout, true);

        // "上层" 是不会盖住"下层的头布局的--所以可以点击到"
        mLl_shopdetails_top.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mGoodsIsOpen) {
                    moveBottom();
                } else {
                    moveInit();
                }
                return false;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mStartX = ev.getX();
            mStartY = ev.getY();

        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            // canScrollVertically -- 检查这个视图是否可以垂直地向某个方向滚动。
            mContentCanScrollTop = mErv_content.getRecyclerView().canScrollVertically(-1);
            mTitleCanScrollTop = mErv_title.getRecyclerView().canScrollVertically(-1);

        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            if ((mStartX != ev.getX() || ev.getY() != mStartY)//单击
                    && isTouchView(mLl_goodslayout, ev.getX(), ev.getY())//触摸范围
                    //                    && mNsv_shopdetailslayout.getScrollY() <= 5//是否到顶部
                    && mGoodsIsOpen && mLl_goodslayout.getTop() != 0) {
                if (0 < mLl_goodslayout.getTop() && mLl_goodslayout.getTop() < mShopdetails_topheight / 2) {
                    moveTop();
                } else if (mLayoutHeight - (mLayoutHeight - mShopdetails_topheight) * 2 / 3 < mLl_goodslayout.getTop() && mLl_goodslayout.getTop() < mLayoutHeight) {
                    moveBottom();
                } else {
                    moveInit();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // System.out.println("=============onSizeChanged===============");
        mLayoutHeight = getMeasuredHeight();
        mShopdetails_topheight = mLl_shopdetails_top.getMeasuredHeight();
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) mLl_goodslayout.getLayoutParams();
        layoutParams.height = mLayoutHeight;
        mLl_goodslayout.setLayoutParams(layoutParams);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!isTop) {//由于recyclerview加载会触发该viewonlayout导致重新布局,只要到了顶部就不再重新布局
            // if (changed) {
            //避免用户关屏再开屏导致回调onlayout导致重新布局,
            if (mGoodsIsOpen) {                                 /* 打开状态才布局 */
                mLl_goodslayout.layout(0, mShopdetails_topheight, r, mLayoutHeight + mShopdetails_topheight);
            } else {                                            /* 关闭状态布局到最底部 */
                mLl_goodslayout.layout(0, mLayoutHeight, r, mLayoutHeight + mLayoutHeight);
            }
            // }
        }
    }

    /**
     * 有嵌套滑动到来了，问下该父View是否接受嵌套滑动
     *
     * @param child            嵌套滑动对应的父类的子类(因为嵌套滑动对于的父View不一定是一级就能找到的，可能挑了两级父View的父View，child的辈分>=target)
     * @param target           具体嵌套滑动的那个子类
     * @param nestedScrollAxes 支持嵌套滚动轴。水平方向，垂直方向，或者不指定
     * @return 是否接受该嵌套滑动
     */
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }


    /**
     * 该父View接受了嵌套滑动的请求该函数调用。onStartNestedScroll返回true该函数会被调用。
     * 参数和onStartNestedScroll一样
     */
    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        mParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    /**
     * 在嵌套滑动的子View未滑动之前告诉过来的准备滑动的情况
     *
     * @param target   具体嵌套滑动的那个子类
     * @param dx       水平方向嵌套滑动的子View想要变化的距离
     * @param dy       垂直方向嵌套滑动的子View想要变化的距离 +上滑 -下滑
     * @param consumed 这个参数要我们在实现这个函数的时候指定，回头告诉子View当前父View消耗的距离
     *                 consumed[0] 水平消耗的距离，consumed[1] 垂直消耗的距离 好让子view做出相应的调整
     */
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (mGoodsIsOpen) {
            if (target instanceof NestedScrollView) {
                if (target.getScrollY() <= 0) {
                    consumed[1] += dy;
                }
            } else if (target instanceof RecyclerView) {
                KeyBoardUtils.hide_keyboard((BaseActivity) mContext);
                if (mLl_goodslayout.getTop() > 0) {
                    mLl_goodslayout.offsetTopAndBottom(mLl_goodslayout.getTop() - dy < 0 ? -mLl_goodslayout.getTop() : -dy);
                    consumed[1] += dy;
                } else {
                    isTop = true;
                }
            }
        }
    }

    /**
     * 嵌套滑动的子View在滑动之后报告过来的滑动情况
     *
     * @param target       具体嵌套滑动的那个子类
     * @param dxConsumed   水平方向嵌套滑动的子View滑动的距离(消耗的距离)
     * @param dyConsumed   垂直方向嵌套滑动的子View滑动的距离(消耗的距离)
     * @param dxUnconsumed 水平方向嵌套滑动的子View未滑动的距离(未消耗的距离)
     * @param dyUnconsumed 垂直方向嵌套滑动的子View未滑动的距离(未消耗的距离)
     */
    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (target instanceof RecyclerView) {
            //是否滑动到顶部
            if ((!mContentCanScrollTop || !mTitleCanScrollTop) && dyUnconsumed < 0) {
                mLl_goodslayout.offsetTopAndBottom(-dyUnconsumed);
            }
        } else if (target instanceof NestedScrollView) {
            if (!mGoodsIsOpen && dyConsumed == 0 && dyUnconsumed >= Utils.dip2px(getContext(), 30)) {
                moveInit();
            }
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public void onStopNestedScroll(View child) {
        mParentHelper.onStopNestedScroll(child);
    }

    /**
     * 在嵌套滑动的子View未fling之前告诉过来的准备fling的情况
     *
     * @param target    具体嵌套滑动的那个子类
     * @param velocityX 水平方向速度
     * @param velocityY 垂直方向速度
     * @return true 父View是否消耗了fling
     */
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }


    /**
     * 改变监听
     */
    LayoutChangeListener mChangeListener;

    public interface LayoutChangeListener {
        /**
         * 打开
         */
        void open();

        /**
         * 关闭
         */
        void close();
    }

    public void setLayoutChangeListener(LayoutChangeListener listener) {
        mChangeListener = listener;
    }

}
