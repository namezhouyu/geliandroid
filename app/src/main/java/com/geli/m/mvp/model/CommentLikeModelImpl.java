package com.geli.m.mvp.model;

import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/2/1.
 */

public class CommentLikeModelImpl extends BaseModel {
    public void toLike(String user_id, String com_id, BaseObserver<ResponseBody> observer) {
        universal(mApiService.commentLike(user_id, com_id), observer);
    }
}
