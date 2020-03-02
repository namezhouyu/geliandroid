package com.geli.m.mvp.home.other.submitorder_activity.main;

import com.geli.m.bean.LogisticsPriceBean;
import com.geli.m.bean.SubmitOrderBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/1/22.
 */

public class SubmitOrderModelImpl extends BaseModel {
    public void getOrderInfo(Map  data, BaseObserver<SubmitOrderBean> observer) {
        universal(mApiService.getOrderInfo(data), observer);
    }

    public void getOrderInfoFormOverseas(Map data, BaseObserver<SubmitOrderBean> observer) {
        universal(mApiService.getOverseasOrderInfo(data), observer);
    }
    public void getTerms(String goods_type, BaseObserver<ResponseBody> observer) {
        universal(mApiService.getTerms(goods_type), observer);
    }

//    public void submitOrder(String user_id, String data, BaseObserver observer) {
//        universal(mApiService.submitOrder(user_id, data), observer);
//    }


    public void submitOrderNew(Map<String, String> data, BaseObserver observer) {
        universal(mApiService.submitOrderNew(data), observer);
    }

    public void submitFuturesOrder(Map<String, String> data, BaseObserver observer) {
        universal(mApiService.submitFuturesOrder(data), observer);
    }

    public void submitGrouponOrder(Map<String, String> data, BaseObserver observer) {
        universal(mApiService.submitGrouponOrder(data), observer);
    }

//    public void submitOverseasOrder(String user_id, String data, BaseObserver observer) {
//        universal(mApiService.submitOverseasOrder(user_id, data), observer);
//    }
//    public void submitOverseasGroupOrder(String user_id, String data, BaseObserver observer) {
//        universal(mApiService.submitOverseasGrouponOrder(user_id, data), observer);
//    }


    public void getLogisticsData(String user_id, String address_id, String json, BaseObserver<LogisticsPriceBean> observer) {
        universal(mApiService.getLogisticsInfo(user_id, address_id, json), observer);
    }

    public void balancePay(Map data, BaseObserver<ResponseBody> observer) {
        universal(mApiService.balancePay(data), observer);
    }


    public void settlement(String user_id, String cart_id, String is_sh, BaseObserver<SubmitOrderBean> observer) {
        universal(mApiService.settlement(user_id, cart_id, is_sh), observer);
    }

    public void getLogisticInfo(String shop_id, BaseObserver observer) {
        universal(mApiService.getLogisticInfo(shop_id), observer);
    }
}
