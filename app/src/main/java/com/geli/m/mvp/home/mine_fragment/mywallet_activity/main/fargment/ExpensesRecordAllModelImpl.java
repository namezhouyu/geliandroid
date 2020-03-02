package com.geli.m.mvp.home.mine_fragment.mywallet_activity.main.fargment;

import com.geli.m.bean.ExpensesRecordBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;

/**
 * Created by Steam_l on 2018/3/27.
 */

public class ExpensesRecordAllModelImpl extends BaseModel {
    public void getWalletDetailAll(String user_id, String page, BaseObserver<ExpensesRecordBean> observer) {
        universal(mApiService.walletDetailAll(user_id, page), observer);
    }

    public void getWalletDetail(String user_id, String page, String trade_type, BaseObserver<ExpensesRecordBean> observer) {
        universal(mApiService.walletDetail(user_id, page, trade_type), observer);
    }
}
