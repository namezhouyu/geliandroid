package com.geli.m.coustomview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import com.geli.m.R;
import com.geli.m.utils.Utils;

/**
 * Created by l on 2018/4/12.
 */

public class StrokeTextView extends android.support.v7.widget.AppCompatTextView {

    private TextPaint mStrokePaint;
    private int mStrokeColor;
    private float mStrokeWidth;

    public StrokeTextView(Context context) {
        this(context, null);
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mStrokeColor = Utils.getColor(R.color.zhusediao);
        mStrokeWidth = Utils.dip2px(context, 3);
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (mStrokePaint == null) {
            mStrokePaint = new TextPaint();
        }
        // 复制原来TextViewg画笔中的一些参数
        TextPaint paint = getPaint();
        mStrokePaint.setTextSize(paint.getTextSize());
        mStrokePaint.setTypeface(paint.getTypeface());
        mStrokePaint.setFlags(paint.getFlags());
        mStrokePaint.setAlpha(paint.getAlpha());

        // 自定义描边效果
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setColor(getStrokeColor());
        mStrokePaint.setStrokeWidth(getStrokeWidth());
        String text = getText().toString();
        //在文本底层画出带描边的文本
        canvas.drawText(text, (getWidth() - mStrokePaint.measureText(text)) / 2,
                getBaseline(), mStrokePaint);
        mStrokePaint.setStrokeWidth(Utils.dip2px(getContext(), 1));
        RectF leftRectf = new RectF((getWidth() - mStrokePaint.measureText(text)) / 2 - Utils.dip2px(getContext(), 31), getHeight() / 2
                , (getWidth() - mStrokePaint.measureText(text)) / 2 - Utils.dip2px(getContext(), 6), (getHeight() / 2 + mStrokePaint.getStrokeWidth() + Utils.dip2px(getContext(), 1)));
        canvas.drawRoundRect(leftRectf, Utils.dip2px(getContext(), 1), Utils.dip2px(getContext(), 1), mStrokePaint);
        RectF rightRectf = new RectF((getWidth() + mStrokePaint.measureText(text)) / 2 + Utils.dip2px(getContext(), 6), getHeight() / 2
                , (getWidth() + mStrokePaint.measureText(text)) / 2 + Utils.dip2px(getContext(), 31), getHeight() / 2 + mStrokePaint.getStrokeWidth() + Utils.dip2px(getContext(), 1));
        canvas.drawRoundRect(rightRectf, Utils.dip2px(getContext(), 1), Utils.dip2px(getContext(), 1), mStrokePaint);
        super.onDraw(canvas);
    }

    public int getStrokeColor() {
        return mStrokeColor;
    }

    public float getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setStrokeColor(int strokeColor) {
        mStrokeColor = strokeColor;
    }

    public void setStrokeWidth(float strokeWidth) {
        mStrokeWidth = strokeWidth;
    }
}
