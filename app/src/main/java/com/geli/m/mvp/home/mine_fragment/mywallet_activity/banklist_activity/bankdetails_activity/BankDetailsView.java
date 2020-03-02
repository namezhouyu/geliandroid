package com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity.bankdetails_activity;

import com.geli.m.bean.BankDetailsBean;
import com.geli.m.mvp.base.BaseView;

/**
 * Created by Steam_l on 2018/3/24.
 */

public interface BankDetailsView extends BaseView {
    void showDetails(BankDetailsBean.DataEntity dataEntity);
}
