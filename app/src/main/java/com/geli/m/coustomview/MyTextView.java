package com.geli.m.coustomview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * author:  shen
 * date:    2018/5/25
 */

public class MyTextView extends TextView implements View.OnTouchListener {
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        view.getParent().requestDisallowInterceptTouchEvent(true);
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_UP:
                view.getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return false;
    }

//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//
//        if(motionEvent.getAction()== MotionEvent.ACTION_DOWN){
//            //通知父控件不要干扰
//            view.getParent().requestDisallowInterceptTouchEvent(true);
//        }
//        if(motionEvent.getAction()== MotionEvent.ACTION_MOVE){
//            view.getParent().requestDisallowInterceptTouchEvent(true);
//        }
//        if(motionEvent.getAction()== MotionEvent.ACTION_UP){
//            view.getParent().requestDisallowInterceptTouchEvent(false);
//        }
//        return false;
//    }


////    在手指触摸可以滚动的textview的触摸监听事件里面做一下拦截就OK了。
////    当文字的总高度小于控件的高度的时候，当触摸文字区域是整个scrollView在滚动，所以需要在onTou事件加个判断，直接上代码：
//    protected boolean canVerticalScroll(TextView editText) {
//        //滚动的距离
//        int scrollY = editText.getScrollY();
//        //控件内容的总高度
//        int scrollRange = editText.getLayout().getHeight();
//        //控件实际显示的高度
//        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom();
//        //控件内容总高度与实际显示高度的差值
//        int scrollDifference = scrollRange - scrollExtent;
//
//        Log.i("shen", "---------------scrollDifference:" + scrollDifference);
//
//        if (scrollDifference == 0) {
//            return false;
//        }
//        return (scrollY > 0) || (scrollY < scrollDifference - 1);
//    }


}
