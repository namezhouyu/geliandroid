package com.geli.m.mvp.base;

import com.geli.m.api.Api;
import com.geli.m.api.ApiEngine;
import com.geli.m.utils.RxUtils;
import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/9/20.
 */

public abstract class BaseModel {

    protected final Api mApiService;
    public static long delayTime = 500;
    public static long delayTime200 = 200;
    public BaseModel() {
        mApiService = ApiEngine.getInstance().getApiService();
    }

    public static void universal(Observable observable, BaseObserver observer) {
        universal(observable, observer, true);
    }

    public static void universal(Observable observable, BaseObserver observer, boolean isDelay) {
        Observable obser = observable;
        if (isDelay) {
            obser = observable
                    .delay(delayTime, TimeUnit.MILLISECONDS);
        }
        obser.compose(RxUtils.rxSchedulerHelper())
                .subscribe(observer);
    }
}
