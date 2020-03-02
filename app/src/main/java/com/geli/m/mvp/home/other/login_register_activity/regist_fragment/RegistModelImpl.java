package com.geli.m.mvp.home.other.login_register_activity.regist_fragment;

import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.home.mine_fragment.setting_activity.getcode_activity.GetCodeModelImpl;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/2.
 */

public class RegistModelImpl extends GetCodeModelImpl {

    public void getProtocol(BaseObserver observer) {
        universal(mApiService.getProtocol(), observer);
    }

    public void regist(String url, Map data, BaseObserver observer) {
        universal(mApiService.regist(url, data), observer);
    }
}
