package com.geli.m.mvp.home.mine_fragment.setting_activity.getcode_activity;

import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;

/**
 * Created by Steam_l on 2018/2/6.
 */

public class GetCodeModelImpl extends BaseModel {
    public void getCode(String phone, String type, String auth_user, BaseObserver observer) {
        universal(mApiService.getCode(phone, type, auth_user), observer);
    }
}
