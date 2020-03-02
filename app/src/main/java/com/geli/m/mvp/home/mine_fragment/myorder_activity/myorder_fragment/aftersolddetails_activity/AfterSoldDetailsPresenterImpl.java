package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.aftersolddetails_activity;

import com.geli.m.bean.AfterSoldDetailsBean;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;

/**
 * author:  shen
 * date:    2018/5/22
 */

public class AfterSoldDetailsPresenterImpl extends BasePresenter<AfterSoldDetailsView, AfterSoldDetailsModeImpl> {

    public AfterSoldDetailsPresenterImpl(AfterSoldDetailsView mvpView) {
        super(mvpView);
    }

    @Override
    protected AfterSoldDetailsModeImpl createModel() {
        return new AfterSoldDetailsModeImpl();
    }
    public void getAfterSoldDetails(String user_id, String order_id){
        mModel.getAfterSoldDetails(user_id, order_id, new BaseObserver<AfterSoldDetailsBean>(this, mvpView) {
            @Override
            protected void onSuccess(AfterSoldDetailsBean data) {
                mvpView.getAfterSoldDetailsSuccess(data);
            }
        });
    }

}
