package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.orderdetails_activity;

import com.geli.m.bean.OrderDetailsBean;
import com.geli.m.mvp.base.BaseView;

/**
 * Created by Steam_l on 2018/1/29.
 */

public interface OrderDetailsView extends BaseView{
    void showOrderDetails(OrderDetailsBean.DataEntity bean);
}
