package com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity.unbinbank_activity;

import com.geli.m.bean.BaseBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.mvp.base.BaseView;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/3/24.
 */

public class UnBinBankPresentImpl extends BasePresenter<BaseView, UnBinBankModelImpl> {
    public UnBinBankPresentImpl(BaseView mvpView) {
        super(mvpView);
    }

    public void unBin(String user_id, String bk_id,String pay_pwd) {
        mModel.unBin(user_id, bk_id,pay_pwd, new BaseObserver<ResponseBody>(this,mvpView) {
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
    protected UnBinBankModelImpl createModel() {
        return new UnBinBankModelImpl();
    }
}
