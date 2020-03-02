package com.geli.m.coustomview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import com.geli.m.R;
import com.geli.m.utils.Utils;

/**
 * Created by Steam_l on 2018/1/20.
 */

public class CouponImagView extends AppCompatImageView {
    private Paint paint;
    public float text_size = 40;
    private float txtBaseY;
    private String text = "";
    private float rotate = 0;
    private int text_color;

    public CouponImagView(Context context) {
        this(context, null);
    }

    public CouponImagView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CouponImagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        text_color = Utils.getColor(R.color.text_color);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CouponImagView);
        try {
            text = typedArray.getText(R.styleable.CouponImagView_text) + "";
            rotate = typedArray.getFloat(R.styleable.CouponImagView_rotate, rotate);
            text_size = typedArray.getDimension(R.styleable.CouponImagView_text_size, text_size);
            text_color = typedArray.getColor(R.styleable.CouponImagView_text_color, text_color);
        } finally {
            typedArray.recycle();
        }
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setFilterBitmap(false);
        paint.setAntiAlias(true);
        paint.setTextSize(text_size);
        paint.setColor(text_color);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        //        // ascent:单个字符基线以上的推荐间距，为负数
        //        System.out.println("ascent:" + fontMetrics.ascent//
        //                // descent:单个字符基线以下的推荐间距，为正数
        //                + " descent:" + fontMetrics.descent //
        //                // 单个字符基线以上的最大间距，为负数
        //                + " top:" + fontMetrics.top //
        //                // 单个字符基线以下的最大间距，为正数
        //                + " bottom:" + fontMetrics.bottom//
        //                // 文本行与行之间的推荐间距
        //                + " leading:" + fontMetrics.leading);
        // 在此处直接计算出来，避免了在onDraw()处的重复计算
        txtBaseY = (fontMetrics.bottom - fontMetrics.ascent) / 2 - fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float tmpWidth = paint.measureText(text);
        canvas.rotate(rotate, getWidth() / 2, getHeight() / 2);
        canvas.drawText(text, (getWidth() - tmpWidth) / 2, getHeight() / 2 + txtBaseY, paint);
        canvas.rotate(-rotate, getWidth() / 2, getHeight() / 2);
    }
}
