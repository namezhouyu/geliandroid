package com.geli.m.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by mrCTang on 2017/5/10.
 * 单例吐司的实现
 */
public class ShowSingleToast {
    private static Toast toast = null;

    public static void showToast(Context context, String message) {
        if (toast == null) {
            synchronized (ShowSingleToast.class) {
                if (toast == null) {
                    toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        } else {
            toast.setText(message);
            toast.show();
        }
    }
}
