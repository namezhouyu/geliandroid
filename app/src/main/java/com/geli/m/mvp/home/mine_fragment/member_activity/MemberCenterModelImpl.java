package com.geli.m.mvp.home.mine_fragment.member_activity;

import com.geli.m.bean.MemberBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;

/**
 * Created by Steam_l on 2018/3/10.
 */

public class MemberCenterModelImpl extends BaseModel {
    public void getData(String user_id, BaseObserver<MemberBean> observer) {
        universal(mApiService.MemberCenter(user_id),observer);
    }
}
