/*
 * Copyright (C) 2013 UCWeb Inc. All rights reserved
 * 本代码版权归UC优视科技所有。
 * UC游戏交易平台为优视科技（UC）旗下的手机游戏交易平台产品
 *
 *
 */

package com.geli.m.coustomview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.geli.m.R;
import java.lang.reflect.Field;

/**
 * 验证码控件
 */
public class VerificationCodeInput extends ViewGroup {

    private static final String TAG = "VerificationCodeInput";

    // 文本格格式(数字、密码、文本、手机号码)
    private final static String TYPE_NUMBER = "number";
    private final static String TYPE_TEXT = "text";
    private final static String TYPE_PASSWORD = "password";
    private final static String TYPE_PHONE = "phone";

    /** 格子个数 */
    private int box = 4;
    private int boxWidth = 120;
    private int boxHeight = 120;
    private int childHPadding = 14;
    private int childVPadding = 14;
    private String inputType = TYPE_PASSWORD;

    /** 每个格子的背景 -- 点击时的背景 */
    private Drawable boxBgFocus = null;
    /** 每个格子的背景 -- 无点击时的背景 */
    private Drawable boxBgNormal = null;
    //
    private int editTextColor = 0xb4b4b4;

    private Listener mListener;

    public VerificationCodeInput(Context context) {
        super(context);
        initViews();
    }

    public VerificationCodeInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypedArray(context, attrs);
        initViews();
    }

    public VerificationCodeInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypedArray(context, attrs);
        initViews();
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();


    }

    /**
     * 根据xml 获得属性
     * @param context
     * @param attrs
     */
    private void initTypedArray(Context context, AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.vericationCodeInput);
        box = a.getInt(R.styleable.vericationCodeInput_box, 4);
        childHPadding = (int) a.getDimension(R.styleable.vericationCodeInput_child_h_padding, 0);
        childVPadding = (int) a.getDimension(R.styleable.vericationCodeInput_child_v_padding, 0);
        boxBgFocus = a.getDrawable(R.styleable.vericationCodeInput_box_bg_focus);
        boxBgNormal = a.getDrawable(R.styleable.vericationCodeInput_box_bg_normal);
        inputType = a.getString(R.styleable.vericationCodeInput_inputType);
        boxWidth = (int) a.getDimension(R.styleable.vericationCodeInput_child_width, boxWidth);
        boxHeight = (int) a.getDimension(R.styleable.vericationCodeInput_child_height, boxHeight);
        editTextColor = a.getColor(R.styleable.vericationCodeInput_edit_text_color, editTextColor);
    }

    private void initViews() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {

                } else {
                    focus();
                    checkAndCommit();
                }
            }

        };


        // 键盘的"退格键"监听处理
        OnKeyListener onKeyListener = new OnKeyListener() {
            @Override
            public synchronized boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    backFocus();
                }
                return false;
            }
        };


        // 一个个的生产"格子(编辑框)"
        for (int i = 0; i < box; i++) {
            EditText editText = new EditText(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(boxWidth, boxHeight);
            //            layoutParams.bottomMargin = childVPadding;
            //            layoutParams.topMargin = childVPadding;
            //            layoutParams.leftMargin = childHPadding;
            //            layoutParams.rightMargin = childHPadding;
            layoutParams.gravity = Gravity.CENTER;

            editText.setOnTouchListener(new OnTouchListener() {         // 每个格子的"触摸事件"
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        for (int i = 0; i < getChildCount(); i++) {
                            View childAt = getChildAt(i);
                            if (v == childAt) {
                                setBg((EditText) childAt, true);
                            } else {
                                setBg((EditText) childAt, false);
                            }
                        }
                    }
                    return false;
                }
            });
            editText.setOnKeyListener(onKeyListener);
            if (i == 0) {
                setBg(editText, true);
            } else {
                setBg(editText, false);
            }
            editText.setTextColor(Color.BLACK);
            editText.setTextSize(20);
            editText.setLayoutParams(layoutParams);
            editText.setGravity(Gravity.CENTER);
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});

            if (TYPE_NUMBER.equals(inputType)) {
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else if (TYPE_PASSWORD.equals(inputType)) {
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else if (TYPE_TEXT.equals(inputType)) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
            } else if (TYPE_PHONE.equals(inputType)) {
                editText.setInputType(InputType.TYPE_CLASS_PHONE);
            }
            editText.setId(i);
            editText.setEms(1);                             // 只能输入一个值
            editText.addTextChangedListener(textWatcher);
            //setCursorDrawableColor(editText, Utils.getColor(R.color.specifitext_color));
            setCursorDrawableColor(editText, editTextColor);
            addView(editText, i);
        }
    }

    public static void setCursorDrawableColor(EditText editText, int color) {
        try {
            Field fCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes"); // 获取这个字段
            fCursorDrawableRes.setAccessible(true);                                 // 代表这个字段、方法等等可以被访问
            int mCursorDrawableRes = fCursorDrawableRes.getInt(editText);

            Field fEditor = TextView.class.getDeclaredField("mEditor");
            fEditor.setAccessible(true);
            Object editor = fEditor.get(editText);

            Class<?> clazz = editor.getClass();
            Field fCursorDrawable = clazz.getDeclaredField("mCursorDrawable");
            fCursorDrawable.setAccessible(true);

            Drawable[] drawables = new Drawable[2];
            drawables[0] = editText.getContext().getResources().getDrawable(mCursorDrawableRes);
            drawables[1] = editText.getContext().getResources().getDrawable(mCursorDrawableRes);
            drawables[0].setColorFilter(color, PorterDuff.Mode.SRC_IN);//SRC_IN 上下层都显示。下层居上显示。
            drawables[1].setColorFilter(color, PorterDuff.Mode.SRC_IN);
            fCursorDrawable.set(editor, drawables);
        } catch (Throwable ignored) {
        }
    }


    /**
     * 删除
     */
    private void backFocus() {
        int count = getChildCount();
        EditText editText;
        boolean tag = false;
        for (int i = count - 1; i >= 0; i--) {
            editText = (EditText) getChildAt(i);
            if (!tag && editText.getText().length() == 1) {
                editText.requestFocus();
                editText.setSelection(1);
                tag = !tag;
                setBg(editText, true);
            } else {
                setBg(editText, false);
            }
        }
    }

    /**
     * 如果没有值，就设置"选中背景"
     */
    private void focus() {
        int count = getChildCount();
        EditText editText;
        boolean tag = false;
        for (int i = 0; i < count; i++) {
            editText = (EditText) getChildAt(i);
            if (!tag && editText.getText().length() < 1) {      // 一个个格子循环 -- 没有值，就设置下这个格子的背景
                editText.requestFocus();
                setBg(editText, true);
                tag = !tag;
            } else {
                setBg(editText, false);
            }
        }
    }

    /**
     * 设置背景
     * @param editText
     * @param focus
     */
    private void setBg(EditText editText, boolean focus) {
        if (boxBgNormal != null && !focus) {
            editText.setBackground(boxBgNormal);
        } else if (boxBgFocus != null && focus) {
            editText.setBackground(boxBgFocus);
        }
    }

    /**
     * 只要有个"格子"输入完毕，就执行下这里
     */
    private void checkAndCommit() {
        StringBuilder stringBuilder = new StringBuilder();
        boolean full = true;
        for (int i = 0; i < box; i++) {
            EditText editText = (EditText) getChildAt(i);
            String content = editText.getText().toString();
            if (content.length() == 0) {                // 只要有个格子没有"输入"，就不算完成，不提交
                full = false;
                break;
            } else {
                stringBuilder.append(content);
            }

        }
        Log.d(TAG, "checkAndCommit:" + stringBuilder.toString());
        if (full) {
            if (mListener != null) {
                mListener.onComplete(stringBuilder.toString());
                // setEnabled(false);
            }

        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.setEnabled(enabled);
        }
    }

    /**
     * 将"监听接口实现"并传递到本控件
     * @param listener
     */
    public void setOnCompleteListener(Listener listener) {
        mListener = listener;
    }

    @Override

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LinearLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(getClass().getName(), "onMeasure");
        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            this.measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }

        // 测量
        if (count > 0) {
            View child = getChildAt(0);
            int cHeight = child.getMeasuredHeight();
            int cWidth = child.getMeasuredWidth();
            int maxH = cHeight + 2 * childVPadding;
            int maxW = cWidth * box + childHPadding * (box - 1);
            setMeasuredDimension(resolveSize(maxW, widthMeasureSpec),
                    resolveSize(maxH, heightMeasureSpec));
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(getClass().getName(), "onLayout");
        int childCount = getChildCount();

        // 为每一个"格子"设置在布局中的位置
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            child.setVisibility(View.VISIBLE);
            int cWidth = child.getMeasuredWidth();
            int cHeight = child.getMeasuredHeight();
            int cl = (i) * (cWidth + childHPadding);
            int cr = cl + cWidth;
            int ct = childVPadding;
            int cb = ct + cHeight;
            child.layout(cl, ct, cr, cb);
        }
    }

    public interface Listener {
        /**
         * 完成--让子类实现
         * @param content
         */
        void onComplete(String content);
    }

}

