package com.geli.m.mvp.home.mine_fragment.mywallet_activity.withdraw_activity;

import com.geli.m.bean.BaseBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity.BankListPresentImpl;
import com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity.BankListView;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/3/26.
 */

public class WithdrawPresentImpl extends BankListPresentImpl<BankListView, WithdrawModelImpl> {
    public WithdrawPresentImpl(BankListView mvpView) {
        super(mvpView);
    }

    public void withdraw(Map data) {
        ((WithdrawModelImpl)mModel).withdraw(data, new BaseObserver<ResponseBody>(this, mvpView) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    BaseBean baseBean = parseData(data.string());
                    if (baseBean.code == Constant.REQUEST_OK) {
                        mvpView.onSuccess(baseBean.message);
                    } else {
                        mvpView.onError(baseBean.message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mvpView.onError(parseError(e));
                }
            }
        });
    }

    @Override
    protected WithdrawModelImpl createModel() {
        return new WithdrawModelImpl();
    }
}
