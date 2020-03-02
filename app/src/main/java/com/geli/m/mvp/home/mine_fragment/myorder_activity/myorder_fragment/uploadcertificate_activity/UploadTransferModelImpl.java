package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.uploadcertificate_activity;

import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/12/6.
 */

public class UploadTransferModelImpl extends BaseModel {
    public void getBankInfo(String user_id, BaseObserver observer) {
        universal(mApiService.getBankInfo(user_id), observer);
    }

    public void UploadPayProof(RequestBody body, BaseObserver observer) {
        universal(mApiService.uploadPayProof(body), observer);
    }
}
