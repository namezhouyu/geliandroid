package com.geli.m.mvp.home.mine_fragment.personinfo_activity;

import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/2/6.
 */

public class ModifyPersonInfoModelImpl extends BaseModel {
    public void modify(Map data, BaseObserver<ResponseBody> observer) {
        universal(mApiService.modifyUserInfo(data), observer);
    }

    public void doAvatar(MultipartBody buiBody, BaseObserver<ResponseBody> observer) {
        universal(mApiService.doAvatar(buiBody), observer);
    }
}
