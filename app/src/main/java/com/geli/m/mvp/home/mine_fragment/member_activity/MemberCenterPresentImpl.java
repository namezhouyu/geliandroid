package com.geli.m.mvp.home.mine_fragment.member_activity;

import com.geli.m.bean.MemberBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;

/**
 * Created by Steam_l on 2018/3/10.
 */

public class MemberCenterPresentImpl extends BasePresenter<MemberView, MemberCenterModelImpl> {
    public MemberCenterPresentImpl(MemberView mvpView) {
        super(mvpView);
    }

    public void getData(String user_id) {
        mModel.getData(user_id, new BaseObserver<MemberBean>(this,mvpView) {
            @Override
            protected void onSuccess(MemberBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.showData(data.getData());
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

    @Override
    protected MemberCenterModelImpl createModel() {
        return new MemberCenterModelImpl();
    }
}
