package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.aftersolddetails_activity;

import com.geli.m.bean.AfterSoldDetailsBean;
import com.geli.m.mvp.base.BaseView;

/**
 * author:  shen
 * date:    2018/5/22
 */

public interface AfterSoldDetailsView extends BaseView {
    void getAfterSoldDetailsSuccess(AfterSoldDetailsBean bean);
}
