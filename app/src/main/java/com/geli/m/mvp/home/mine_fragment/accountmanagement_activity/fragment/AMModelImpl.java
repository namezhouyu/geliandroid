package com.geli.m.mvp.home.mine_fragment.accountmanagement_activity.fragment;


import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;

/**
 * shen
 */
public class AMModelImpl extends BaseModel {
    public void userShManage(String user_id, String type, BaseObserver observer) {
        universal(mApiService.userShManage(user_id, type), observer);
    }
}
