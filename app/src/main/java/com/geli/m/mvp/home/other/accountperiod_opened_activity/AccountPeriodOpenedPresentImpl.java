package com.geli.m.mvp.home.other.accountperiod_opened_activity;

import com.geli.m.bean.UserAPDetailBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;

/**
 * author:  shen
 * date:    2018/11/3
 */
public class AccountPeriodOpenedPresentImpl extends BasePresenter<AccountPeriodOpenedView, AccountPeriodOpenedModelImpl> {
    public AccountPeriodOpenedPresentImpl(AccountPeriodOpenedView mvpView) {
        super(mvpView);
    }

    @Override
    protected AccountPeriodOpenedModelImpl createModel() {
        return new AccountPeriodOpenedModelImpl();
    }


    /* 用户账期详情 */
    public void getUserShDetail(String user_id, String shop_id) {
        mModel.getUserShDetail(user_id, shop_id, new BaseObserver<UserAPDetailBean>(this, mvpView) {
            @Override
            protected void onSuccess(UserAPDetailBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.showUserShDetail(data.getData());
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

}
