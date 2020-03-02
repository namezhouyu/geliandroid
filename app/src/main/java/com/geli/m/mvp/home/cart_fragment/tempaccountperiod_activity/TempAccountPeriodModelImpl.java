package com.geli.m.mvp.home.cart_fragment.tempaccountperiod_activity;

import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import okhttp3.ResponseBody;


/**
 * shen
 */
public class TempAccountPeriodModelImpl extends BaseModel {
    public void tempShApply(String user_id, String amount, String shop_id, BaseObserver<ResponseBody> observer) {
        universal(mApiService.tempShApply(user_id, amount, shop_id), observer);
    }
}
