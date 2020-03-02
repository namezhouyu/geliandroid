package com.geli.m.mvp.home.mine_fragment.setting_activity.getcode_activity;

import com.geli.m.R;
import com.geli.m.bean.GetCodeBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.utils.Utils;

/**
 * Created by Steam_l on 2018/2/6.
 */

public class GetCodePresentImpl<v extends GetCodeView, m extends GetCodeModelImpl> extends BasePresenter<GetCodeView, GetCodeModelImpl> {
    public GetCodePresentImpl(GetCodeView mvpView) {
        super(mvpView);
    }

    /** 这个是 从后台获取的验证码  --  用于验证手机接收到的验证码 */
    public String mCurrCode = "";

    /**
     * 获取"验证码"
     *
     * @param phone
     * @param authtype  1:注册 2:忘记密码 3:支付密码 4绑定银行卡
     * @param auth_user
     */
    public void getCode(String phone, String authtype, String auth_user) {
        mModel.getCode(phone, authtype, auth_user, new BaseObserver<GetCodeBean>(this, mvpView) {
            @Override
            protected void onSuccess(GetCodeBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.waitGetCode();
                    mCurrCode = data.getData();
                    mvpView.onSuccess(Utils.getString(R.string.message_waitgetcode));
                    // LogUtils.i("传说中的验证码 -- mCurrCode:" + mCurrCode);
                } else {
                    mvpView.onError(data.getMessage() + "");
                }
            }

        });
    }


    /**
     * 验证"验证码"
     * @param code 验证码
     * @return
     */
    public boolean verifyCode(String code) {
        if (!code.equals(mCurrCode)) {
            mvpView.onError(Utils.getString(R.string.incorrect_verification_code));
        }
        return code.equals(mCurrCode);
    }

    @Override
    protected GetCodeModelImpl createModel() {
        return new GetCodeModelImpl();
    }
}
