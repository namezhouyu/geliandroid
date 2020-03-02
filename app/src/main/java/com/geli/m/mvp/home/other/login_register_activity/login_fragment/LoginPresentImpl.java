package com.geli.m.mvp.home.other.login_register_activity.login_fragment;

import com.geli.m.R;
import com.geli.m.bean.LoginBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.mvp.base.BaseView;
import com.geli.m.utils.LoginInformtaionSpUtils;
import com.geli.m.utils.Utils;
import com.google.gson.Gson;
import org.json.JSONObject;

import static com.geli.m.app.GlobalData.mContext;

/**
 * Created by Administrator on 2017/11/2.
 */

public class LoginPresentImpl extends BasePresenter<BaseView, LoginModelImpl> {
    public LoginPresentImpl(BaseView mvpView) {
        super(mvpView);
    }

    public void doLogin(final String phone, final String password) {
        mModel.doLogin(phone, password, new BaseObserver<String>(this, mvpView) {

            @Override
            protected void onSuccess(String string) {
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    if (jsonObject.getInt("code") == Constant.REQUEST_OK) {
                        LoginBean data = new Gson().fromJson(string, LoginBean.class);
                        //将token保存到本地
                        LoginInformtaionSpUtils.setLoginInfString(mContext, LoginInformtaionSpUtils.login_password, password);
                        LoginInformtaionSpUtils.setLoginInfString(mContext, LoginInformtaionSpUtils.login_phone, phone);
                        LoginInformtaionSpUtils.setLoginInfString(mContext, LoginInformtaionSpUtils.login_user_id, data.getData().getUser_id().trim());
                        LoginInformtaionSpUtils.setLoginInfBoolean(mContext, LoginInformtaionSpUtils.login_pay_pwd, data.getData().isPay_pwd());
                        LoginInformtaionSpUtils.setLoginInfString(mContext, LoginInformtaionSpUtils.login_login, "1");
                        mvpView.onSuccess(Utils.getString(R.string.message_loginsuccess));
                    } else if (jsonObject.getInt("code") == Constant.PHONE_NOREGIST) {
                        mvpView.onError(Utils.getString(R.string.message_phonenoregist));
                    } else if (jsonObject.getInt("code") == Constant.PASS_OR_PHONE_FAILED) {
                        mvpView.onError(Utils.getString(R.string.login_incorrectusernameorpass));
                    } else {
                        mvpView.onError(jsonObject.getString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mvpView.onError(parseError(e));
                }
            }

            @Override
            protected void onError(String msg) {
                mvpView.onError(msg);
            }
        });
    }

    @Override
    protected LoginModelImpl createModel() {
        return new LoginModelImpl();
    }
}
