package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.aftersold_activity;

import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import okhttp3.RequestBody;

/**
 * author:  shen
 * date:    2018/5/22
 */

public class AfterSoldModeImpl extends BaseModel {

    public void getContact(String user_id, String order_id, BaseObserver observer) {
        universal(mApiService.getContact(user_id, order_id), observer);
    }


    public void afterSoldSubmit(RequestBody body, BaseObserver observer) {
        universal(mApiService.afterSoldSubmit(body), observer);
    }
}
