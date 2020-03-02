package com.geli.m.mvp.home.other.login_register_activity;


import com.geli.m.mvp.home.mine_fragment.setting_activity.getcode_activity.GetCodeView;

/**
 * Created by Administrator on 2017/11/2.
 */

public interface RegistView extends GetCodeView {

    //修改密码或者注册
    void submitSuccess();

    void gotoProtocol(String url);
}
