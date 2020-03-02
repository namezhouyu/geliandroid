package com.geli.m.mvp.home.mine_fragment.setting_activity.getcode_activity.paypass_activity;

import com.geli.m.bean.BaseBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.mvp.base.BaseView;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/2/6.
 */

public class PayPassSettingPresentImpl extends BasePresenter<BaseView, PayPassSettingModelImpl> {
    public PayPassSettingPresentImpl(BaseView mvpView) {
        super(mvpView);
    }

    public void resetPayPass(Map data) {
        mModel.resetPayPass(data, new BaseObserver<ResponseBody>(this, mvpView) {
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
    protected PayPassSettingModelImpl createModel() {
        return new PayPassSettingModelImpl();
    }
}
