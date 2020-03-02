package com.geli.m.mvp.home.mine_fragment.coupon_activity.coupon_fragment;

import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import java.util.Map;

/**
 * Created by Steam_l on 2018/1/20.
 */

public class CouponModelImpl extends BaseModel {
    public void collectCoupons(String user_id, String coupon_id, BaseObserver observer) {
        universal(mApiService.collectCoupons(user_id, coupon_id), observer);
    }
    public void getCouponInfo(String url, Map data, BaseObserver observer) {
        universal(mApiService.universal(url, data), observer);
    }
}
