package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.orderdetails_activity;

import com.geli.m.bean.OrderDetailsBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/1/29.
 */

public class OrderDetailsModelImpl extends BaseModel {
    public void getOrderDetails(String user_id, String order_id, BaseObserver<OrderDetailsBean> observer) {
        universal(mApiService.getOrderDetails(user_id, order_id), observer);
    }

    public void balancePay(Map data, BaseObserver<ResponseBody> observer) {
        universal(mApiService.balancePay(data), observer);
    }

    public void orderPay(Map data, BaseObserver<ResponseBody> observer) {
        universal(mApiService.orderPay(data), observer);
    }

    public void universal(String url, Map data, BaseObserver<ResponseBody> observer) {
        universal(mApiService.universal(url, data), observer);
    }
}
