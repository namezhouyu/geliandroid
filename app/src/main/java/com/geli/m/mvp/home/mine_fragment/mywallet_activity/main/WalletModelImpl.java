package com.geli.m.mvp.home.mine_fragment.mywallet_activity.main;

import com.geli.m.api.Api;
import com.geli.m.api.GeLiPayApiEngine;
import com.geli.m.bean.FwqTimeBean;
import com.geli.m.bean.WalletBalanceBean;
import com.geli.m.bean.WalletBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.utils.RxUtils;
import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;

/**
 * Created by Steam_l on 2018/3/26.
 */

public class WalletModelImpl extends BaseModel {
    public void wallet(String user_id, BaseObserver<WalletBean> observer) {
        universal(mApiService.wallet(user_id), observer);
    }

    public void walletBalance(String user_id, BaseObserver<WalletBalanceBean> observer) {
        universal(mApiService.walletBalance(user_id), observer);
    }


    public void getFwqTime1(BaseObserver<FwqTimeBean> observer) {
        Api apiService = GeLiPayApiEngine.getInstance().getApiService();
        Observable<FwqTimeBean> observable = apiService.getFwqTime();
        observable = observable.delay(delayTime, TimeUnit.MILLISECONDS);
        observable.compose(RxUtils.rxSchedulerHelper()).subscribe(observer);
    }
}
