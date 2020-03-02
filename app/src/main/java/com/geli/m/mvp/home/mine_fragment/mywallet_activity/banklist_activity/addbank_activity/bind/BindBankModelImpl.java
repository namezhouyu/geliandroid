package com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity.addbank_activity.bind;

import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.home.mine_fragment.setting_activity.getcode_activity.GetCodeModelImpl;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/3/24.
 */

public class BindBankModelImpl extends GetCodeModelImpl {
    public void bindBank(Map data, BaseObserver<ResponseBody> observer) {
        universal(mApiService.binBank(data), observer);
    }

}
