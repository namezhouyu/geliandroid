package com.geli.m.mvp.home.mine_fragment.mywallet_activity.withdraw_activity;

import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity.BankListModelImpl;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/3/26.
 */

public class WithdrawModelImpl extends BankListModelImpl {
    public void withdraw(Map data, BaseObserver<ResponseBody> observer) {
        universal(mApiService.withdraw(data), observer);
    }
}
