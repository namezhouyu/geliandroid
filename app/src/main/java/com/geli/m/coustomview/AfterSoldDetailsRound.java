package com.geli.m.coustomview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.geli.m.R;
import com.geli.m.utils.Utils;

/**
 * Created by Steam_l on 2018/3/2.
 */

public class AfterSoldDetailsRound extends View {
    Context mContext;
    private Paint mGrayPaint;
    private Paint mRedMaxPaint;
    private Paint mRedMinPaint;
    private boolean isRed = true;
    private boolean isBig = true;
    private boolean isFirst = false;
    private int maxRadius = 18;
    private int minRadius = 10;
    private int lineHeight = 55;

    /** 大的那个控件的颜色 */
    private int bigColor = 0xE75D5D;
    /** 小的那个控件的颜色 */
    private int smallColor = 0xB4B4B4;

    public AfterSoldDetailsRound(Context context) {
        this(context, null);
    }

    public AfterSoldDetailsRound(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AfterSoldDetailsRound(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.AfterSoldDetailsRound);
        try {
            maxRadius = typedArray.getDimensionPixelOffset(R.styleable.AfterSoldDetailsRound_asdr_maxradius, maxRadius);
            minRadius = typedArray.getDimensionPixelOffset(R.styleable.AfterSoldDetailsRound_asdr_maxradius, minRadius);
            lineHeight = typedArray.getDimensionPixelOffset(R.styleable.AfterSoldDetailsRound_asdr_lineheight, lineHeight);
            bigColor = typedArray.getColor(R.styleable.AfterSoldDetailsRound_asdr_big_color, bigColor);
            smallColor = typedArray.getColor(R.styleable.AfterSoldDetailsRound_asdr_small_color, smallColor);
        } finally {
            typedArray.recycle();
        }
        maxRadius = Utils.dip2px(mContext, maxRadius);
        minRadius = Utils.dip2px(mContext, minRadius);
        lineHeight = Utils.dip2px(mContext, lineHeight);


        mRedMaxPaint = new Paint();
        mRedMaxPaint.setAntiAlias(true);                               // 消除锯齿
        mRedMaxPaint.setColor(bigColor);
        mRedMaxPaint.setStrokeWidth(Utils.dip2px(mContext, 1));
        mRedMaxPaint.setStyle(Paint.Style.STROKE);                      //绘图为描边模式

        mRedMinPaint = new Paint();
        mRedMinPaint.setAntiAlias(true);                               // 消除锯齿
        mRedMinPaint.setColor(bigColor);
        mRedMinPaint.setStrokeWidth(Utils.dip2px(mContext, 1));

        mGrayPaint = new Paint();
        mGrayPaint.setAntiAlias(true);                                 // 消除锯齿
        mGrayPaint.setColor(smallColor);
        mRedMaxPaint.setStrokeWidth(Utils.dip2px(mContext, 1));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint;
        if (isRed) {
            paint = mRedMaxPaint;
        } else {
            paint = mGrayPaint;
        }

        int size;
        if (isBig) {
            size = maxRadius;
            float strokeWidth = (float) (size * 0.05); //设置线条宽度
            paint.setStrokeWidth(strokeWidth);
            canvas.drawCircle(maxRadius / 2, size/2, size/2 - strokeWidth, paint);

            canvas.drawCircle(maxRadius / 2, maxRadius/2, minRadius/2, mRedMinPaint);
        } else {
            size = minRadius;
            canvas.drawCircle(maxRadius / 2, size/2, size/2, paint);
        }

        if (!isFirst) {
            canvas.drawLine(maxRadius / 2 , size, maxRadius / 2 , size + lineHeight, mGrayPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = 0;
        if (isBig) {
            size += maxRadius;
        } else {
            size += minRadius;
        }
        if (!isFirst) {
            size += lineHeight;
        }
        setMeasuredDimension(maxRadius, size);
    }

    public void setState(boolean isRed, boolean isBig, boolean isFirst) {
        this.isRed = isRed;
        this.isBig = isBig;
        this.isFirst = isFirst;
        requestLayout();
        invalidate();

    }
}
