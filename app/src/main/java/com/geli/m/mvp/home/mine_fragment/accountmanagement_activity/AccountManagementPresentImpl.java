package com.geli.m.mvp.home.mine_fragment.accountmanagement_activity;

import com.geli.m.mvp.base.BasePresenter;

/**
 * shen
 */
public class AccountManagementPresentImpl
        extends BasePresenter<AccountManagementView, AccountManagementModelImpl> {


    @Override
    protected AccountManagementModelImpl createModel() {
        return new AccountManagementModelImpl();
    }

    public AccountManagementPresentImpl(AccountManagementView mvpView) {
        super(mvpView);
    }
}
