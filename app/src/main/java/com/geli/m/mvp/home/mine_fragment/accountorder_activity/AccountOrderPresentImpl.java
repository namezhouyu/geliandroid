package com.geli.m.mvp.home.mine_fragment.accountorder_activity;

import com.geli.m.bean.AccountOrderBean;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;

import static com.geli.m.config.Constant.REQUEST_OK;

/**
 * shen
 */
public class AccountOrderPresentImpl extends BasePresenter<AccountOrderView, AccountOrderModelImpl> {


    @Override
    protected AccountOrderModelImpl createModel() {
        return new AccountOrderModelImpl();
    }

    public AccountOrderPresentImpl(AccountOrderView mvpView) {
        super(mvpView);
    }


    public void shDetail(String user_id, String shop_id) {
       mModel.shDetail(user_id, shop_id, new BaseObserver<AccountOrderBean>(this, mvpView, true) {
            @Override
            protected void onSuccess(AccountOrderBean data) {
                if(data.getCode() == REQUEST_OK){
                    mvpView.shDetailSuccess(data);
                }
            }
        });
    }

}
