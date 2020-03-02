package com.geli.m.mvp.home.mine_fragment.mywallet_activity.main;

import com.geli.m.bean.FwqTimeBean;
import com.geli.m.bean.WalletBalanceBean;
import com.geli.m.bean.WalletBean;
import com.geli.m.mvp.base.BaseView;

/**
 * Created by Steam_l on 2018/3/26.
 */

public interface MyWalletView extends BaseView {
    void showWallet(WalletBean.DataEntity dataEntity);
    void showWalletBalance(WalletBalanceBean bean);

    void getFwqTimeSuccess(String jm, FwqTimeBean data);
}
