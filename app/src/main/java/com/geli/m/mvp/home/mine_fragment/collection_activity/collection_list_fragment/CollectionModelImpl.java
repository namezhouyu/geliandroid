package com.geli.m.mvp.home.mine_fragment.collection_activity.collection_list_fragment;

import com.geli.m.bean.CollectionBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/4/2.
 */

public class CollectionModelImpl extends BaseModel {
    public void getList(String user_id, String col_type, BaseObserver<CollectionBean> observer) {
        universal(mApiService.collList(user_id, col_type), observer);
    }

    public void collection(Map<String, String> data, BaseObserver<ResponseBody> observer) {
        universal(mApiService.collection(data), observer);
    }

}
