package com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity.bankdetails_activity;

import com.geli.m.bean.BankDetailsBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/3/24.
 */

public class BankDetailsModelImpl extends BaseModel {
    public void getDetails(String user_id, String bk_id, BaseObserver<BankDetailsBean> observer) {
        universal(mApiService.bankDetails(user_id, bk_id), observer);
    }

    public void unBin(String user_id, String bk_id, String pay_pwd, BaseObserver<ResponseBody> observable) {
        universal(mApiService.unBank(user_id, bk_id, pay_pwd), observable);
    }
}
