package com.geli.m.coustomview;

/**
 * Created by c on 2017/3/15.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.utils.Utils;
import java.util.Arrays;

/**
 * Created by junweiliu on 16/11/24.
 */
public class SideBar extends View {
    /**
     * 点击回调
     */
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    /**
     * 26字母
     */
    public static String[] letterStrs = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};
    /**
     * 当前是否选中
     */
    private int choose = 0;
    /**
     * 字母画笔
     */
    private Paint paint = new Paint();
    /**
     * 显示的TextView
     */
    private TextView mTextDialog;
    /**
     * 普通时的颜色
     */
    private int normalColor;
    /**
     * 选中的颜色
     */
    private int chooseColor;
    /**
     * 普通时的背景
     */
    private Drawable normalBackground;
    /**
     * 选中时的背景
     */
    private Drawable chooseBackground;
    /**
     * 文字大小
     */
    private float textSize;
    /**
     * 边框
     */
    private Rect mRect;


    public SideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // 获取自定义属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SideBar);
        normalColor = ta.getColor(R.styleable.SideBar_normalColor, Color.GRAY);
        chooseColor = ta.getColor(R.styleable.SideBar_chooseColor, Color.RED);
        normalBackground = ta.getDrawable(R.styleable.SideBar_normalBackground);
        chooseBackground = ta.getDrawable(R.styleable.SideBar_chooseBackground);
        textSize = ta.getDimension(R.styleable.SideBar_sideTextSize, TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_SP, 13,
                        getResources().getDisplayMetrics()));
        ta.recycle();
        init();
    }

    /**
     * 为SideBar设置显示字母的TextView
     *
     * @param mTextDialog
     */
    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    /**
     * 设置
     *
     * @param letter
     */
    public void setLetter(String[] letter) {
        this.letterStrs = letter;
        invalidate();
        requestLayout();
    }


    /**
     * 初始化参数
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void init() {
        paint.setColor(normalColor);
        //        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);
        // 获取单个绘制的rect,用于获取单个绘制项的高度
        mRect = new Rect();
        paint.getTextBounds("M", 0, "M".length(), mRect);
    }


    /**
     * 绘制
     *
     * @param canvas
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取焦点改变背景颜色.
        int height = getHeight() - getPaddingTop() - getPaddingBottom();// 获取对应高度
        int width = getWidth(); // 获取对应宽度
        int singleHeight = height / letterStrs.length;// 获取每一个字母的高度
        for (int i = 0; i < letterStrs.length; i++) {
            float xPos = width / 2 - paint.measureText(letterStrs[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            // 选中的状态
            if (i == choose) {
                paint.setColor(Utils.getColor(R.color.progress));
                //                paint.setFakeBoldText(true);
                int dr = Utils.dip2px(getContext(), 2.5f);
                RectF rectF = new RectF((width - mRect.width()) / 2 - dr, yPos - mRect.height() - dr, (width - mRect.width()) / 2 + mRect.width() + dr, yPos + dr);
                canvas.drawArc(rectF, 0, 360, true, paint);
                //                canvas.drawRoundRect(, radio, radio, paint);
            }
            // x坐标等于中间-字符串宽度的一半.
            paint.setColor(normalColor);
            canvas.drawText(letterStrs[i], xPos, yPos, paint);
            paint.reset();// 重置画笔
            init();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        // 点击的y坐标
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        // 获取当前点击的字母位置,点击位置的y坐标比上总的高度相当于点击的位置比上全部位置(c / b.length = y / getHeight())
        final int currChoose = (int) (y / getHeight() * letterStrs.length);

        switch (action) {
            case MotionEvent.ACTION_UP:
                //                // 重置背景色
                //                if (null != normalBackground) {
                //                    setBackground(normalBackground);
                //                } else {
                //                    setBackgroundColor(Color.argb(0, 0, 0, 0));
                //                }
                //                // 抬起时置为-1
                //                choose = -1;
                //                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                // 设置背景色
                if (null != chooseBackground) {
                    setBackground(chooseBackground);
                }
                if (oldChoose != currChoose) {
                    if (currChoose >= 0 && currChoose < letterStrs.length) {
                        if (null != listener) {
                            listener.onTouchingLetterChanged(letterStrs[currChoose]);
                        }
                        if (null != mTextDialog) {
                            mTextDialog.setText(letterStrs[currChoose]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }
                        // 设置选中的位置为当前位置
                        choose = currChoose;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    public void setSelect(String str) {
        if (!letterStrs[choose].equals(str)) {
            choose = Arrays.binarySearch(letterStrs, str);
            invalidate();
        }
    }

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * 回调接口
     *
     * @author coder
     */
    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }


    /**
     * 测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        // 当高度为自适应时,高度为字母高度*字母数量*2 即间隔为单位高度
        int wrapHeight = letterStrs.length * mRect.height() + (letterStrs.length + 1) * Utils.dip2px(getContext(), 5);
        // 当宽度为自适应使,宽度为字母宽度*2
        int warpWidth = mRect.width() * 2;
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth : warpWidth
                , (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
                        //wrap_content时的高度
                        : wrapHeight);
    }

}

