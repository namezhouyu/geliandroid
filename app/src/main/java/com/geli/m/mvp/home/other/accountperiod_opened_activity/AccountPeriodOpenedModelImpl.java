package com.geli.m.mvp.home.other.accountperiod_opened_activity;

import com.geli.m.bean.UserAPDetailBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;

/**
 * author:  shen
 * date:    2018/11/3
 */
public class AccountPeriodOpenedModelImpl extends BaseModel {

    public void getUserShDetail(String user_id, String shop_id, BaseObserver<UserAPDetailBean> observer) {
        universal(mApiService.getUserShDetail(user_id, shop_id), observer);
    }

}
