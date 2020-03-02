package com.geli.m.popup;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

public abstract class BasePopupWindow {
    protected Activity mActivity;
    protected View mContentView;
    protected PopupWindow mInstance;
    protected float mBgColor;

    /**
     *
     * @param a
     * @param layoutRes
     * @param w
     * @param h
     * @param bgColor               0~1
     */
    public BasePopupWindow(Activity a, int layoutRes, int w, int h, float bgColor) {
        mActivity = a;
        mContentView = LayoutInflater.from(a).inflate(layoutRes, null, false);
        mInstance = new PopupWindow(mContentView, w, h, true);
        initView();
        initEvent();
        initWindow();
        mBgColor = bgColor;
        if(bgColor < 1 && bgColor > 0){
            darkenBackground(mActivity, bgColor);       // 0.5f
        }
        setDismissListener();
    }

    public View getContentView() { return mContentView; }
    public PopupWindow getPopupWindow() { return mInstance; }

    protected abstract void initView();
    protected abstract void initEvent();
    protected abstract void setDismissListenerI();

    protected void initWindow() {
        mInstance.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mInstance.setOutsideTouchable(true);
        mInstance.setTouchable(true);
    }

    public void showBashOfAnchor(View anchor, LayoutGravity layoutGravity, int xmerge, int ymerge) {
        int[] offset = layoutGravity.getOffset(anchor, mInstance);
        mInstance.showAsDropDown(anchor, offset[0]+xmerge, offset[1]+ymerge);
    }

    /**
     * 在7.0后适配
     */
    public void showAsDropDown(View anchor, int xoff, int yoff) {

        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);// 以屏幕 左上角 为参考系的
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;  //屏幕高度减去 anchor 的 bottom
            mInstance.setHeight(h);// 重新设置PopupWindow高度
        }
        mInstance.showAsDropDown(anchor, xoff, yoff);
    }


    public void showAtLocation(View parent, int gravity, int x, int y) {
        mInstance.showAtLocation(parent, gravity, x, y);
    }


    /**
     * 改变背景颜色 -- 灰度
     *
     * @param activity
     * @param bgColor           0~1
     */
    public void darkenBackground(Activity activity, Float bgColor){
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgColor;

        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);

    }

    /**
     * 监听关闭的时候将Activity的灰度设为正常
     */
    private void setDismissListener(){
        getPopupWindow().setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(mBgColor < 1 && mBgColor > 0){
                    darkenBackground(mActivity, 1f);
                }
                setDismissListenerI();
            }
        });
    }


    /**
     * popup 的位置(在对应的控件)
     *
     * 使用: BasePopupWindow.LayoutGravity mLayoutGravity = new BasePopupWindow.LayoutGravity(
     *              BasePopupWindow.LayoutGravity.ALIGN_RIGHT |
     *              BasePopupWindow.LayoutGravity.TO_BOTTOM);
     */
    public static class LayoutGravity {
        private int layoutGravity;
        // waring, don't change the order of these constants!
        public static final int ALIGN_LEFT=0x1;
        public static final int ALIGN_ABOVE=0x2;
        public static final int ALIGN_RIGHT=0x4;
        public static final int ALIGN_BOTTOM=0x8;
        public static final int TO_LEFT=0x10;
        public static final int TO_ABOVE=0x20;
        public static final int TO_RIGHT=0x40;
        public static final int TO_BOTTOM=0x80;
        public static final int CENTER_HORI=0x100;
        public static final int CENTER_VERT=0x200;

        public LayoutGravity(int gravity) {
            layoutGravity=gravity;
        }

        public int getLayoutGravity() { return layoutGravity; }
        public void setLayoutGravity(int gravity) { layoutGravity=gravity; }

        public void setHoriGravity(int gravity) {
            layoutGravity&=(0x2+0x8+0x20+0x80+0x200);
            layoutGravity|=gravity;
        }
        public void setVertGravity(int gravity) {
            layoutGravity&=(0x1+0x4+0x10+0x40+0x100);
            layoutGravity|=gravity;
        }

        public boolean isParamFit(int param) {
            return (layoutGravity & param) > 0;
        }

        public int getHoriParam() {
            for(int i=0x1; i<=0x100; i=i<<2)
                if(isParamFit(i))
                    return i;
            return ALIGN_LEFT;
        }

        public int getVertParam() {
            for(int i=0x2; i<=0x200; i=i<<2)
                if(isParamFit(i))
                    return i;
            return TO_BOTTOM;
        }

        public int[] getOffset(View anchor, PopupWindow window) {
            int anchWidth=anchor.getWidth();
            int anchHeight=anchor.getHeight();

            int winWidth=window.getWidth();
            int winHeight=window.getHeight();
            View view=window.getContentView();
            if(winWidth<=0)
                winWidth=view.getWidth();
            if(winHeight<=0)
                winHeight=view.getHeight();

            int xoff=0;
            int yoff=0;

            switch (getHoriParam()) {
                case ALIGN_LEFT:
                    xoff=0; break;
                case ALIGN_RIGHT:
                    xoff=anchWidth-winWidth; break;
                case TO_LEFT:
                    xoff=-winWidth; break;
                case TO_RIGHT:
                    xoff=anchWidth; break;
                case CENTER_HORI:
                    xoff=(anchWidth-winWidth)/2; break;
                default:break;
            }
            switch (getVertParam()) {
                case ALIGN_ABOVE:
                    yoff=-anchHeight; break;
                case ALIGN_BOTTOM:
                    yoff=-winHeight; break;
                case TO_ABOVE:
                    yoff=-anchHeight-winHeight; break;
                case TO_BOTTOM:
                    yoff=0; break;
                case CENTER_VERT:
                    yoff=(-winHeight-anchHeight)/2; break;
                default:break;
            }
            return new int[]{ xoff, yoff };
        }
    }
}