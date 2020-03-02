package com.geli.m.mvp.home.mine_fragment.accountmanagement_activity.fragment;

import com.geli.m.bean.AccountManagementBean;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;

/**
 * shen
 */
public class AMPresentImpl extends BasePresenter<AMView, AMModelImpl> {


    @Override
    protected AMModelImpl createModel() {
        return new AMModelImpl();
    }

    public AMPresentImpl(AMView mvpView) {
        super(mvpView);
    }


    /**
     *
     * @param user_id
     * @param type		类型  1已开通2未开通
     */
    public void getShList(String user_id, String type) {
       mModel.userShManage(user_id, type, new BaseObserver<AccountManagementBean>(this, mvpView, true) {
           @Override
           protected void onSuccess(AccountManagementBean data) {
            mvpView.onSuccess(data.getMessage());
            mvpView.getShListSuccess(data);
           }
        });
    }


}
