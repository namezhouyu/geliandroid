package com.geli.m.mvp.home.mine_fragment.setting_activity.getcode_activity.loginpass_activity;

import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/2/6.
 */

public class ChangeLoginPassModelImpl extends BaseModel {
    public void changePass(String url, Map data, BaseObserver<ResponseBody> observer) {
        universal(mApiService.regist(url, data), observer);
    }
}
