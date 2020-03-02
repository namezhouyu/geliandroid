package com.geli.m.mvp.base;

/**
 * Created by Administrator on 2017/9/20.
 */

public interface BaseView {
    void onSuccess(String msg);

    void onError(String msg);

    void showLoading();

    void hideLoading();
}
