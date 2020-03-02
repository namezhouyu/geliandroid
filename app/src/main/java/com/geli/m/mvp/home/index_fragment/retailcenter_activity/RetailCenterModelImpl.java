package com.geli.m.mvp.home.index_fragment.retailcenter_activity;

import com.geli.m.bean.RetailCenterBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import java.util.Map;

/**
 * Created by Steam_l on 2018/1/9.
 */

public class RetailCenterModelImpl extends BaseModel {
    public void getList(String url,Map data, BaseObserver<RetailCenterBean> observer) {
        universal(mApiService.getRetailCenter(url,data), observer);
    }
}
