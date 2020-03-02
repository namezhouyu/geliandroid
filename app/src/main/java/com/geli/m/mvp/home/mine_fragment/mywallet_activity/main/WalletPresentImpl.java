package com.geli.m.mvp.home.mine_fragment.mywallet_activity.main;

import com.geli.m.bean.FwqTimeBean;
import com.geli.m.bean.WalletBalanceBean;
import com.geli.m.bean.WalletBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;

/**
 * Created by Steam_l on 2018/3/26.
 */

public class WalletPresentImpl extends BasePresenter<MyWalletView, WalletModelImpl> {
    public WalletPresentImpl(MyWalletView mvpView) {
        super(mvpView);
    }

    public void getWallet(String user_id) {
        mModel.wallet(user_id, new BaseObserver<WalletBean>(this, mvpView) {
            @Override
            protected void onSuccess(WalletBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.showWallet(data.getData());
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }


    public void getWalletBalance(String user_id) {
        mModel.walletBalance(user_id, new BaseObserver<WalletBalanceBean>(this, mvpView) {
            @Override
            protected void onSuccess(WalletBalanceBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.showWalletBalance(data);
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }


    public void getFwqTime(final String jm) {
        mModel.getFwqTime1(new BaseObserver<FwqTimeBean>(this, mvpView) {
            @Override
            protected void onSuccess(FwqTimeBean data) {
                if(data.getCode() == 0) {
                    mvpView.getFwqTimeSuccess(jm, data);
                }else {
                    onError(data.getCode() + ":" + data.getRep_msg());
                }
            }


        });
    }

    @Override
    protected WalletModelImpl createModel() {
        return new WalletModelImpl();
    }
}
