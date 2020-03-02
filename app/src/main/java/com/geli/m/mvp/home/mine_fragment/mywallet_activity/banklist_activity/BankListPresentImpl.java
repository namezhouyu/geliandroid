package com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity;

import com.geli.m.bean.BankListBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;

/**
 * Created by Steam_l on 2018/3/24.
 */

public class BankListPresentImpl<V extends BankListView, M extends BankListModelImpl> extends BasePresenter<BankListView, BankListModelImpl> {
    public BankListPresentImpl(BankListView mvpView) {
        super(mvpView);
    }

    public void getList(String user_id) {
        mModel.getBankList(user_id, new BaseObserver<BankListBean>(this, mvpView) {
            @Override
            protected void onSuccess(BankListBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.showList(data.getData());
                } else if (data.getCode() == 200) {
                    mvpView.showList(null);
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

    @Override
    protected BankListModelImpl createModel() {
        return new BankListModelImpl();
    }
}
