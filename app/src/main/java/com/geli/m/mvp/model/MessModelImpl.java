package com.geli.m.mvp.model;

import com.geli.m.bean.MessBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import java.util.Map;

/**
 * Created by Steam_l on 2018/3/28.
 */

public class MessModelImpl extends BaseModel {
    public void getMess(Map<String, String> data, BaseObserver<MessBean> observer) {
        universal(mApiService.getMess(data), observer);
    }
}
