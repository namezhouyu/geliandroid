package com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity.bankdetails_activity;

import com.geli.m.bean.BankDetailsBean;
import com.geli.m.bean.BaseBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/3/24.
 */

public class BankDetailsPresentImpl extends BasePresenter<BankDetailsView, BankDetailsModelImpl> {
    public BankDetailsPresentImpl(BankDetailsView mvpView) {
        super(mvpView);
    }

    public void getDetails(String user_id, String bk_id) {
        mModel.getDetails(user_id, bk_id, new BaseObserver<BankDetailsBean>(this, mvpView) {
            @Override
            protected void onSuccess(BankDetailsBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.showDetails(data.getData());
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

    public void unBin(String user_id, String bk_id, String pay_pwd) {
        mModel.unBin(user_id, bk_id, pay_pwd, new BaseObserver<ResponseBody>(this, mvpView) {
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
    protected BankDetailsModelImpl createModel() {
        return new BankDetailsModelImpl();
    }
}
