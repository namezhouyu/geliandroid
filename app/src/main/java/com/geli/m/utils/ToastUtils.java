package com.geli.m.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.geli.m.app.GlobalData;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mrCTang on 2017/5/10.
 * 单例吐司的实现
 */
public class ToastUtils {
    private static Toast toast = null;

    public static void showToast(Context context, String message) {
        if(context == null){
            showToast(message);
        }else {
            if (toast == null) {
                synchronized (ToastUtils.class) {
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

    public static void showToast(String message) {
        if (toast == null) {
            synchronized (ToastUtils.class) {
                if (toast == null) {
                    toast = Toast.makeText(GlobalData.getInstance(), message, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        } else {
            toast.setText(message);
            toast.show();
        }
    }

    public static void showToast(String message, int gravity) {
        if (toast == null) {
            synchronized (ToastUtils.class) {
                if (toast == null) {
                    toast = Toast.makeText(GlobalData.getInstance(), message, Toast.LENGTH_SHORT);
                    toast.setGravity(gravity, 0, 0);
                    toast.show();
                }
            }
        } else {
            toast.setText(message);
            toast.setGravity(gravity, 0, 0);
            toast.show();
        }
    }


    public static void showToast(final String string, final Activity activity) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            Toast.makeText(activity, string, Toast.LENGTH_SHORT).show();
        } else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, string, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public static void showToastByMainThread(String s){
        Observable.just(s)
                .subscribeOn(Schedulers.io())                 // 上游
                .observeOn(AndroidSchedulers.mainThread())      // 下游
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        showToast(s);
                    }


                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
