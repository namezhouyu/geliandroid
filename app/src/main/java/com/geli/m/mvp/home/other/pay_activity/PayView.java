package com.geli.m.mvp.home.other.pay_activity;


import com.geli.m.bean.BalanceBean;
import com.geli.m.bean.FwqTimeBean;
import com.geli.m.bean.TransferBean;
import com.geli.m.mvp.base.BaseView;

/**
 * shen
 */
public interface PayView extends BaseView {
    void payResult(String res,String pay_type);

    void walletPaySuccess();

    void isGeliPay(BalanceBean bean);

    void getFwqTimeSuccess(int code, FwqTimeBean data);

    void getTransferData(TransferBean data);
}
