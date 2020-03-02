package com.geli.m.coustomview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import cn.bingoogolapple.qrcode.core.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;

/**
 * Created by Steam_l on 2017/12/18.
 *
 * 解决纵向rv嵌套横向rv滑动冲突
 * 解决横向rv嵌套纵向rv滑动冲突
 */

public class MyEasyRecyclerView extends EasyRecyclerView {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private float mLastY;
    private float mLastX;
    private float mDownY;
    Context mContext;

    /**
     * 是否做拦截
     */
    private boolean isIntercept = true;

    private int orientation = HORIZONTAL;

    public MyEasyRecyclerView(Context context) {
        this(context, null);
    }

    public MyEasyRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyEasyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                if(!isIntercept){
                    break;
                }
                if(orientation == HORIZONTAL){
                    if (mLastY != 0 && Math.abs(ev.getY() - mLastY) > (Math.abs(ev.getX() - mLastX) * 2)) {
                        if (Math.abs(ev.getY() - mDownY) > Utils.dip2px(mContext, 30)) {
                            getParent().requestDisallowInterceptTouchEvent(false);  // 父控件拦截事件
                            return false;
                        }
                    }
                }else {
                    if (mLastY != 0 && (Math.abs(ev.getY() - mLastY)*2) < Math.abs(ev.getX() - mLastX)) {
                        if (Math.abs(ev.getY() - mDownY) < Utils.dip2px(mContext, 30)) {
                            getParent().requestDisallowInterceptTouchEvent(false);  // 父控件拦截事件
                            return false;
                        }
                    }
                }

                getParent().requestDisallowInterceptTouchEvent(true);           // 父控件不拦截事件
                mLastY = ev.getY();
                mLastX = ev.getX();
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public boolean isIntercept() {
        return isIntercept;
    }

    public void setIntercept(boolean intercept) {
        isIntercept = intercept;
    }
}
