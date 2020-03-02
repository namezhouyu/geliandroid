package com.geli.m.mvp.home.mine_fragment.main;

import com.geli.m.bean.PersonInfoBean;
import com.geli.m.mvp.base.BaseView;

/**
 * Created by Steam_l on 2018/2/5.
 */

public interface MineView extends BaseView {
    void showPersonInfo(PersonInfoBean.DataEntity infoBean);

    void getPersonInfoError();
}
