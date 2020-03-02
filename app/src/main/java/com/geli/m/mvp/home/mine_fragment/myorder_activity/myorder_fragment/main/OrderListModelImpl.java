package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.main;

import com.geli.m.bean.OrderListBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/1/27.
 */

public class OrderListModelImpl extends BaseModel {
    public void getOrderList(boolean isDelay, Map data, BaseObserver<OrderListBean> observer) {
        universal(mApiService.getOrderList(data), observer, isDelay);
    }

//    public void balancePay(Map data, BaseObserver<ResponseBody> observer) {
//        universal(mApiService.balancePay(data), observer);
//    }

//    public void orderPay(Map data, BaseObserver<ResponseBody> observer) {
//        universal(mApiService.orderPay(data), observer);
//    }

    public void universal(String url, Map data, BaseObserver<ResponseBody> observer) {
        universal(mApiService.universal(url, data), observer);
    }

}
