package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.goodscomment_activity;

import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import java.util.Map;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/1/31.
 */

public class GoodsCommentModelImpl extends BaseModel {
    public void goodsCommentImg(RequestBody body, BaseObserver<ResponseBody> observer) {
        universal(mApiService.goodsCommmentImg(body), observer);
    }

    public void goodsComment(Map data, BaseObserver<ResponseBody> observer) {
        universal(mApiService.goodsComment(data), observer);
    }
}
