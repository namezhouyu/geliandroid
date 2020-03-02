package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.logisticsdetails_activity;

import com.geli.m.bean.LogisticsDetailsBean;
import com.geli.m.mvp.base.BaseView;

/**
 * Created by Steam_l on 2018/3/27.
 */

public interface LogisticsDetailsView extends BaseView {
    void showList(LogisticsDetailsBean.DataEntity dataEntities);
}
