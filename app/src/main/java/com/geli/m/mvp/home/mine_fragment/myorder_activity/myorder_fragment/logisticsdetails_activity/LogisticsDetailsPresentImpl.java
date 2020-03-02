package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.logisticsdetails_activity;

import com.geli.m.bean.LogisticsDetailsBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;

/**
 * Created by Steam_l on 2018/3/27.
 */

public class LogisticsDetailsPresentImpl extends BasePresenter<LogisticsDetailsView, LogisticsDetailsModelImpl> {
    public LogisticsDetailsPresentImpl(LogisticsDetailsView mvpView) {
        super(mvpView);
    }

    public void getLogistics(String user_id, String order_id) {
        mModel.getList(user_id,order_id, new BaseObserver<LogisticsDetailsBean>(this, mvpView) {
            @Override
            protected void onSuccess(LogisticsDetailsBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.showList(data.getData());
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

    @Override
    protected LogisticsDetailsModelImpl createModel() {
        return new LogisticsDetailsModelImpl();
    }
}
