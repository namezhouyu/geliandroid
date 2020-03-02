package com.geli.m.mvp.home.find_fragment.findlist_fragment.details;

import com.geli.m.bean.FindDetailsBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/3/21.
 */

public class FindDetailsModelImpl extends BaseModel {
    public void getDetails(String user_id,String find_id, BaseObserver<FindDetailsBean> observer) {
        universal(mApiService.findDetails(user_id,find_id), observer);
    }

    public void collection(Map<String, String> data, BaseObserver<ResponseBody> observer) {
        universal(mApiService.collection(data), observer);
    }

    public void like(String user_id, String find_id, BaseObserver<ResponseBody> observer) {
        universal(mApiService.findLike(user_id, find_id), observer);
    }


}
