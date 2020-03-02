package com.geli.m.coustomview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.geli.m.R;
import com.geli.m.utils.Utils;

/**
 * Created by Steam_l on 2018/3/2.
 */

public class LogisticsDetailsRound extends View {
    Context mContext;
    private Paint mGrayPaint;
    private Paint mRedPaint;
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

    public LogisticsDetailsRound(Context context) {
        this(context, null);
    }

    public LogisticsDetailsRound(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LogisticsDetailsRound(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.LogisticsDetailsRound);
        try {
            maxRadius = typedArray.getDimensionPixelOffset(R.styleable.LogisticsDetailsRound_ldr_maxradius, maxRadius);
            minRadius = typedArray.getDimensionPixelOffset(R.styleable.LogisticsDetailsRound_ldr_maxradius, minRadius);
            lineHeight = typedArray.getDimensionPixelOffset(R.styleable.LogisticsDetailsRound_ldr_lineheight, lineHeight);
            bigColor = typedArray.getColor(R.styleable.LogisticsDetailsRound_ldr_big_color, bigColor);
            smallColor = typedArray.getColor(R.styleable.LogisticsDetailsRound_ldr_small_color, smallColor);
        } finally {
            typedArray.recycle();
        }
        maxRadius = Utils.dip2px(mContext, maxRadius);
        minRadius = Utils.dip2px(mContext, minRadius);
        lineHeight = Utils.dip2px(mContext, lineHeight);
        mRedPaint = new Paint();
        // mRedPaint.setColor(Utils.getColor(R.color.zhusediao));
        mRedPaint.setColor(bigColor);
        mRedPaint.setStrokeWidth(Utils.dip2px(mContext, 1));

        mGrayPaint = new Paint();
        // mGrayPaint.setColor(Utils.getColor(R.color.specifitext_color));
        mGrayPaint.setColor(smallColor);
        mRedPaint.setStrokeWidth(Utils.dip2px(mContext, 1));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint;
        if (isRed) {
            paint = mRedPaint;
        } else {
            paint = mGrayPaint;
        }
        int size;
        if (isBig) {
            size = maxRadius;
        } else {
            size = minRadius;
        }
        RectF rectF = new RectF((maxRadius - size) / 2, 0, maxRadius - (maxRadius - size) / 2, size);
        canvas.drawRoundRect(rectF, size / 2, size / 2, paint);


        if (!isFirst) {
            canvas.drawLine(maxRadius / 2, size, maxRadius / 2, size + lineHeight, paint);
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
