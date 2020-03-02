package com.geli.m.mvp.home.mine_fragment.setting_activity.getcode_activity.paypass_activity;

import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/2/6.
 */

public class PayPassSettingModelImpl extends BaseModel {
    public void resetPayPass(Map data, BaseObserver<ResponseBody> observer) {
        universal(mApiService.resetPayPass(data), observer);
    }
}
