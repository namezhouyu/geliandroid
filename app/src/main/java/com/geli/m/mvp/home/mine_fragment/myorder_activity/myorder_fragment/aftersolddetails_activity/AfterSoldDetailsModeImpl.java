package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.aftersolddetails_activity;

import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;

/**
 * author:  shen
 * date:    2018/5/22
 */

public class AfterSoldDetailsModeImpl extends BaseModel {

    public void getAfterSoldDetails(String user_id, String order_id, BaseObserver observer) {
        universal(mApiService.getAfterSoldDetails(user_id, order_id), observer);
    }

}
