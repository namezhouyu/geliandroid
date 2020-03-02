package com.geli.m.mvp.home.mine_fragment.accountmanagement_activity.fragment;


import com.geli.m.bean.AccountManagementBean;
import com.geli.m.mvp.base.BaseView;

/**
 * shen
 */
public interface AMView extends BaseView {
    void getShListSuccess(AccountManagementBean data);
}
