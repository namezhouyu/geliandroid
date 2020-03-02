package com.geli.m.mvp.home.mine_fragment.helpcenter_activity;

import com.geli.m.bean.HelpCenterBean;
import com.geli.m.bean.HelpCenterBottomBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;

/**
 * Created by Steam_l on 2018/3/8.
 */

public class HelpCenterPresentImpl extends BasePresenter<HelpCenterView, HelpCenterModelImpl> {
    public HelpCenterPresentImpl(HelpCenterView mvpView) {
        super(mvpView);
    }

    public void getAllData() {
        mModel.getAllData(new BaseObserver<HelpCenterBean>(this, mvpView) {
            @Override
            protected void onSuccess(HelpCenterBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.showAllData(data.getData());
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

    public void getDataForCat(String cat_id) {
        mModel.getDataForCat(cat_id, new BaseObserver<HelpCenterBottomBean>(this, mvpView) {
            @Override
            protected void onSuccess(HelpCenterBottomBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.showCatData(data.getData().getArt_list());
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

    @Override
    protected HelpCenterModelImpl createModel() {
        return new HelpCenterModelImpl();
    }
}
