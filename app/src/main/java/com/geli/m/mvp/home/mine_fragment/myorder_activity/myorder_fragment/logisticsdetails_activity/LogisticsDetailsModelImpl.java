package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.logisticsdetails_activity;

import com.geli.m.bean.LogisticsDetailsBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;

/**
 * Created by Steam_l on 2018/3/27.
 */

public class LogisticsDetailsModelImpl extends BaseModel {
    public void getList(String user_id, String order_id, BaseObserver<LogisticsDetailsBean> observer) {
        universal(mApiService.getLogisticsList(user_id,order_id),observer);
    }

}
