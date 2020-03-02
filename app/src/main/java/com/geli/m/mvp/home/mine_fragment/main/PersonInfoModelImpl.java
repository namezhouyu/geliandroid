package com.geli.m.mvp.home.mine_fragment.main;

import com.geli.m.api.Api;
import com.geli.m.api.ApiCacheEngine;
import com.geli.m.bean.PersonInfoBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.utils.RxUtils;
import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;

/**
 * Created by Steam_l on 2018/2/5.
 */

public class PersonInfoModelImpl extends BaseModel {
    public void getPersonInfo(String user_id, BaseObserver<PersonInfoBean> observer) {
        universal(mApiService.getUserInfo(user_id), observer);
    }


    public void getPersonInfoCache(String user_id, BaseObserver<PersonInfoBean> observer) {
        Api apiService = ApiCacheEngine.getInstance().getApiService();
        Observable<PersonInfoBean> observable = apiService.getUserInfo(user_id);
        observable = observable.delay(delayTime, TimeUnit.MILLISECONDS);
        observable.compose(RxUtils.rxSchedulerHelper()).subscribe(observer);
    }

}
