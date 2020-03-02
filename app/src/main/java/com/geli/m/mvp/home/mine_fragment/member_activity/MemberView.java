package com.geli.m.mvp.home.mine_fragment.member_activity;

import com.geli.m.bean.MemberBean;
import com.geli.m.mvp.base.BaseView;

/**
 * Created by Steam_l on 2018/3/10.
 */

public interface MemberView extends BaseView {
    void showData(MemberBean.DataEntity dataEntity);
}
