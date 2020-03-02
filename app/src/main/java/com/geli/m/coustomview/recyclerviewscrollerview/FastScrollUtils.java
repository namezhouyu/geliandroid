package com.geli.m.coustomview.recyclerviewscrollerview;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

/**
 * Created by mklimczak on 31/07/15.
 */
public class FastScrollUtils {

    /**
     * 获取"本控件"在屏幕的"Y坐标"
     *
     * getLocationInWindow -- 计算此视图在其窗口中的坐标。参数必须是两个整数的数组。方法返回后，数组按顺序包含X和Y位置。
     * @param view
     * @return
     */
    public static float getViewRawY(View view) {
        int[] location = new int[2];
        location[0] = 0;
        location[1] = (int) view.getY();
        ((View)view.getParent()).getLocationInWindow(location);
        return location[1];
    }

    /**
     * 获取"本控件"在屏幕的"X坐标"
     * getLocationInWindow -- 计算此视图在其窗口中的坐标。参数必须是两个整数的数组。方法返回后，数组按顺序包含X和Y位置。
     * @param view
     * @return
     */
    public static float getViewRawX(View view) {
        int[] location = new int[2];
        location[0] = (int) view.getX();
        location[1] = 0;
        ((View)view.getParent()).getLocationInWindow(location);
        return location[0];
    }

    /**
     * 边界内取数
     *
     * 小于"最小边界" 返回 min          <br>
     * 大于"最大边界" 返回 max          <br>
     * 在"边界"内的 ， 返回 Value
     *
     * @param min
     * @param max
     * @param value
     * @return
     */
    public static float getValueInRange(float min, float max, float value) {
        float minimum = Math.max(min, value);
        return Math.min(minimum, max);
    }

    /**
     * 为"控件" 设置背景
     * @param view          控件
     * @param drawable      资源文件
     */
    public static void setBackground(View view, Drawable drawable){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

}
