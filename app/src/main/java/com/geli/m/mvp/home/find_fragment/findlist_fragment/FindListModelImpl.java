package com.geli.m.mvp.home.find_fragment.findlist_fragment;

import com.geli.m.bean.FindListBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/3/21.
 */

public class FindListModelImpl extends BaseModel {
    public void getList(Map<String, String> data, BaseObserver<FindListBean> observer) {
        universal(mApiService.findList(data), observer);
    }

    public void like(String user_id, String find_id, BaseObserver<ResponseBody> observer) {
        universal(mApiService.findLike(user_id, find_id), observer);
    }

}
