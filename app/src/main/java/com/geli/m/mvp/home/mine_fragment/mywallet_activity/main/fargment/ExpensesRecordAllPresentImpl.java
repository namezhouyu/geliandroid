package com.geli.m.mvp.home.mine_fragment.mywallet_activity.main.fargment;

import com.geli.m.bean.ExpensesRecordBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;

/**
 * Created by Steam_l on 2018/3/27.
 */

public class ExpensesRecordAllPresentImpl extends BasePresenter<ExpensesRecordAllView, ExpensesRecordAllModelImpl> {
    public ExpensesRecordAllPresentImpl(ExpensesRecordAllView mvpView) {
        super(mvpView);
    }

    public void getWalletDetailAll(String user_id, String page) {
        mModel.getWalletDetailAll(user_id, page, new BaseObserver<ExpensesRecordBean>(this, mvpView) {
            @Override
            protected void onSuccess(ExpensesRecordBean data) {
                if(data.getCode() == Constant.REQUEST_OK){
                    mvpView.showData(data.getData());
                }else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

    public void getWalletDetail(String user_id, String page, String trade_type) {
        mModel.getWalletDetail(user_id, page, trade_type, new BaseObserver<ExpensesRecordBean>(this, mvpView) {
            @Override
            protected void onSuccess(ExpensesRecordBean data) {
                if(data.getCode() == Constant.REQUEST_OK){
                    mvpView.showData(data.getData());
                }else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }


    @Override
    protected ExpensesRecordAllModelImpl createModel() {
        return new ExpensesRecordAllModelImpl();
    }
}
