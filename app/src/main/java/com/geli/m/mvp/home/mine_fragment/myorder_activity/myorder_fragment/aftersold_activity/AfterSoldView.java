package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.aftersold_activity;

import com.geli.m.bean.OrderContactBean;
import com.geli.m.mvp.base.BaseView;

/**
 * author:  shen
 * date:    2018/5/22
 */

public interface AfterSoldView extends BaseView {
    void getContactSuccess(OrderContactBean bean);
}
