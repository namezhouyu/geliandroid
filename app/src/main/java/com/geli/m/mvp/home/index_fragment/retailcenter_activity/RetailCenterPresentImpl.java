package com.geli.m.mvp.home.index_fragment.retailcenter_activity;

import com.geli.m.R;
import com.geli.m.bean.RetailCenterBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.utils.Utils;
import java.util.Map;

/**
 * Created by Steam_l on 2018/1/9.
 */

public class RetailCenterPresentImpl extends BasePresenter<RetailCenterView, RetailCenterModelImpl> {

    public RetailCenterPresentImpl(RetailCenterView mvpView) {
        super(mvpView);
    }

    public void getList(String url, Map data) {
        mModel.getList(url, data, new BaseObserver<RetailCenterBean>(this, mvpView) {
            @Override
            protected void onSuccess(RetailCenterBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.onSuccess(Utils.getString(R.string.refresh_success));
                    mvpView.showData(data);
                } else {
                    mvpView.onError(data.getMessage());
                }
            }

            @Override
            protected void onError(String msg) {
                mvpView.onError(msg);
            }
        });
    }

    @Override
    protected RetailCenterModelImpl createModel() {
        return new RetailCenterModelImpl();
    }
}
