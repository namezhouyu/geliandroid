package com.geli.m.mvp.home.other.accountperiod_activity;

import com.geli.m.bean.ShopAPDetailBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * author:  shen
 * date:    2018/11/3
 */
public class AccountPeriodModelImpl extends BaseModel {

    public void getShopShDetail(String shop_id, BaseObserver<ShopAPDetailBean> observer) {
        universal(mApiService.getShopShDetail(shop_id), observer);
    }

    public void shApply(RequestBody body, BaseObserver<ResponseBody> observer) {
        universal(mApiService.shApply(body), observer);
    }
}
