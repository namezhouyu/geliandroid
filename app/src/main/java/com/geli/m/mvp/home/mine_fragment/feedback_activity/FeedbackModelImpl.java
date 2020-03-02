package com.geli.m.mvp.home.mine_fragment.feedback_activity;

import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/3/30.
 */

public class FeedbackModelImpl extends BaseModel {
    public void feedback(RequestBody body, BaseObserver<ResponseBody> observer) {
        universal(mApiService.feedBack(body), observer);
    }
}
