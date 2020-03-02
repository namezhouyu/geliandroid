package com.geli.m.mvp.model;

import com.geli.m.mvp.base.BaseObserver;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/2/1.
 */

public class CommentListModelImpl extends CommentLikeModelImpl {
    public void getCommentList(String url, Map data, BaseObserver<ResponseBody> observer) {
        universal(mApiService.universal(url, data), observer);
    }

}
