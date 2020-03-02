package com.geli.m.mvp.home.other.login_register_activity.regist_fragment;

import com.geli.m.R;
import com.geli.m.api.UrlSet;
import com.geli.m.bean.RegistrationProtocolBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.home.mine_fragment.setting_activity.getcode_activity.GetCodePresentImpl;
import com.geli.m.mvp.home.other.login_register_activity.RegistView;
import com.geli.m.utils.Utils;
import java.util.HashMap;
import java.util.Map;
import okhttp3.ResponseBody;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/11/2.
 */

public class RegistPresentImpl extends GetCodePresentImpl<RegistView, RegistModelImpl> {
    public RegistPresentImpl(RegistView mvpView) {
        super(mvpView);
    }


    public void getProtocol() {
        ((RegistModelImpl) mModel).getProtocol(new BaseObserver<RegistrationProtocolBean>(this, mvpView, false) {
            @Override
            protected void onSuccess(RegistrationProtocolBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    String url = data.getData().getArt_url();
                    if (!(url.startsWith(UrlSet.HTTP)||url.startsWith(UrlSet.HTTPS))) {
                        url = UrlSet.HTTP + url;
                    }
                    ((RegistView) mvpView).gotoProtocol(url);
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

    public void submit(final String type, String phone, String code, final String pass) {
        if (!verifyCode(code)) {
            return;
        }
        String url;
        if (type.equals("1")) {
            url = UrlSet.Sign_url;
        } else {
            url = UrlSet.doForgetPwd;
        }
        Map data = new HashMap();
        data.put("phone", phone);
        data.put("password", pass);
        data.put("code", code);
        ((RegistModelImpl) mModel).regist(url, data, new BaseObserver<ResponseBody>(this, mvpView) {

            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    JSONObject jsonObject = new JSONObject(data.string());
                    if (jsonObject.getInt("code") == Constant.REQUEST_OK) {
                        if (type.equals("1")) {
                            mvpView.onSuccess(Utils.getString(R.string.message_registsuccess));
                        } else {
                            mvpView.onSuccess(Utils.getString(R.string.message_modifysuccess));
                        }
                        ((RegistView) mvpView).submitSuccess();
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
    protected RegistModelImpl createModel() {
        return new RegistModelImpl();
    }
}
