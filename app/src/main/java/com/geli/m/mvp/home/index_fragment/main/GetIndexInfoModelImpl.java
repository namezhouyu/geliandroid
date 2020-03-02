package com.geli.m.mvp.home.index_fragment.main;

import com.geli.m.bean.IndexBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import io.reactivex.functions.Function;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2017/12/21.
 */

public class GetIndexInfoModelImpl extends BaseModel {


    public void getIndexInfo(Map<String, String> data,Function<ResponseBody, IndexBean> function, BaseObserver<IndexBean> observer) {
        universal(mApiService.getIndexInfo(data)
                .map(function), observer);
    }

}
