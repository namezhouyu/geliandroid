package com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity;

import com.geli.m.bean.BankListBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;

/**
 * Created by Steam_l on 2018/3/24.
 */

public class BankListModelImpl extends BaseModel {
    public void getBankList(String user_id, BaseObserver<BankListBean> observer) {
        universal(mApiService.bankList(user_id),observer);
    }

}
