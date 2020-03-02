package com.geli.m.mvp.home.other.submitorder_activity.main;

import com.geli.m.bean.ShopLogisticBean;
import com.geli.m.bean.SubmitOrderBean;
import com.geli.m.bean.SubmitOrderNewBean;
import com.geli.m.mvp.base.BaseView;

/**
 * Created by Steam_l on 2018/1/22.
 */

public interface SubmitOrderView extends BaseView {
    void showOrderInfo(SubmitOrderBean.DataEntity entity);

    void finsh();

//    void showLogisticsPrice(LogisticsPriceBean dataEntity);

    void showTerms(String terms);

    void submitOrderSuccess(SubmitOrderNewBean bean);

    void getLogisticInfoSuccess(ShopLogisticBean data);
}
