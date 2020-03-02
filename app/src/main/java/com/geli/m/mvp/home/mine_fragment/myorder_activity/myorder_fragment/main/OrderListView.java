package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.main;

import com.geli.m.bean.OrderListBean;
import com.geli.m.mvp.base.BaseView;
import java.util.List;

/**
 * Created by Steam_l on 2018/1/27.
 */

public interface OrderListView extends BaseView {
    void showOrderList(List<OrderListBean.DataEntity> entityList);
//    void walletPaySuccess();
//    void payResult(String res,String pay_type);
}
