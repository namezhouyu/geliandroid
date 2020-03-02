package com.geli.m.mvp.home.other.pay_activity;


import com.geli.m.api.Api;
import com.geli.m.api.GeLiPayApiEngine;
import com.geli.m.bean.BalanceBean;
import com.geli.m.bean.FwqTimeBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.utils.RxUtils;
import io.reactivex.Observable;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.ResponseBody;

/**
 * shen
 */
public class PayModelImpl extends BaseModel {
//    public void orderPay(Map data, BaseObserver<ResponseBody> observer) {
//        universal(mApiService.orderPay(data), observer);
//    }

    public void isGeliPay(String user_id, String order_sn, BaseObserver<BalanceBean> observer) {
        universal(mApiService.isGeliPay(user_id, order_sn), observer);
    }

    public void orderPayNew(String user_id, String order_sn, String pay_type, String pay_pwd,
                            BaseObserver<ResponseBody> observer) {
        universal(mApiService.orderPayNew(user_id, order_sn, pay_type, pay_pwd), observer);
    }

    public void logisticsPay(String user_id, String order_sn, String pay_type, String pay_pwd,
                            BaseObserver<ResponseBody> observer) {
        universal(mApiService.logisticsPay(user_id, order_sn, pay_type, pay_pwd), observer);
    }


    public void balancePay(Map data, BaseObserver<ResponseBody> observer) {
        universal(mApiService.balancePay(data), observer);
    }


    public void getFwqTime(BaseObserver<ResponseBody> observer) {
        universal(mApiService.getFwqTime(), observer);
    }


    public void getFwqTime1(BaseObserver<FwqTimeBean> observer) {
        Api apiService = GeLiPayApiEngine.getInstance().getApiService();
        Observable<FwqTimeBean> observable = apiService.getFwqTime();
        observable = observable.delay(delayTime, TimeUnit.MILLISECONDS);
        observable.compose(RxUtils.rxSchedulerHelper()).subscribe(observer);
    }


    public void transfer(String user_id, String order_sn, BaseObserver observer) {
        universal(mApiService.transfer(user_id, order_sn), observer);
    }

}
