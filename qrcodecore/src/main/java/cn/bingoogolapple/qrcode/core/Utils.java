package cn.bingoogolapple.qrcode.core;

import android.content.Context;

/**
 * Created by Steam_l on 2017/12/15.
 */

public class Utils {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
