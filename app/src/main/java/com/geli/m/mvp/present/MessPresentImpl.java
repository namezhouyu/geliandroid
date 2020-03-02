package com.geli.m.mvp.present;

import com.geli.m.bean.MessBean;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.mvp.model.MessModelImpl;
import com.geli.m.mvp.view.MessView;
import com.geli.m.config.Constant;

import java.util.Map;

/**
 * Created by Steam_l on 2018/3/28.
 */

public class MessPresentImpl extends BasePresenter<MessView, MessModelImpl> {

    public MessPresentImpl(MessView mvpView) {
        super(mvpView);
    }

    public void getMess(Map data) {
        mModel.getMess(data, new BaseObserver<MessBean>(this, mvpView) {
            @Override
            protected void onSuccess(MessBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.showList(data.getData());
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

    @Override
    protected MessModelImpl createModel() {
        return new MessModelImpl();
    }
}
