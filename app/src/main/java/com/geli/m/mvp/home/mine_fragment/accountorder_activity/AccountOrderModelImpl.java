package com.geli.m.mvp.home.mine_fragment.accountorder_activity;


import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;

/**
 * shen
 */
public class AccountOrderModelImpl extends BaseModel {
    public void shDetail(String user_id, String shop_id, BaseObserver observer) {
        universal(mApiService.shDetail(user_id, shop_id), observer);
    }
}
