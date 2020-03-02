package com.geli.m.mvp.home.other.login_register_activity.login_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.mvp.base.BaseView;
import com.geli.m.mvp.base.MVPFragment;
import com.geli.m.utils.KeyBoardUtils;
import com.geli.m.utils.MatcherUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;

import static com.geli.m.config.Constant.ACTION_REQUEST;
import static com.geli.m.config.Constant.INTENT_INTERFACE;

/**
 * Created by Administrator on 2017/12/15.
 */

public class LoginFragment extends MVPFragment<LoginPresentImpl> implements OnClickListener, BaseView {

    /** 密码编辑框 */
    @BindView(R.id.et_login_password)
    EditText mEtPass;
    /** 手机号编辑框 */
    @BindView(R.id.et_login_phone)
    EditText mEtPhone;
    /** 是否显示密码 */
    @BindView(R.id.cb_login_viewpass)
    CheckBox mCbViewPass;

    LoginInterface mLoginInterface;

    public LoginFragment(){

    }

    public static LoginFragment newInstance(LoginInterface loginInterface) {

        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putParcelable(INTENT_INTERFACE, loginInterface);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected LoginPresentImpl createPresent() {
        return new LoginPresentImpl(this);
    }

    @Override
    public int getResId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void init() {
        Bundle arguments = getArguments();
        mLoginInterface = arguments.getParcelable(INTENT_INTERFACE);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.tv_login_gotoresetpass, R.id.bt_login, R.id.cb_login_viewpass})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_login_gotoresetpass:               /* 忘记密码 */
                replaceResetPass();
                break;

            case R.id.bt_login:                             /* 登录 */
                doLogin();
                break;

            case R.id.cb_login_viewpass:                     /* 是否显示密码 */
                KeyBoardUtils.changePassState(mCbViewPass, mEtPass);
                break;

            default:
                break;
        }
    }


    /**
     * 忘记密码
     */
    public void replaceResetPass() {
        if(mLoginInterface != null){
            mLoginInterface.resetPass();
        }
    }

    /**
     * 登录
     */
    public void doLogin() {
        String phone = mEtPhone.getText().toString().trim();
        String pass = mEtPass.getText().toString().trim();

        if (phone.isEmpty() || pass.isEmpty()) {
            ToastUtils.showToast(Utils.getString(R.string.info_no_null));
            return;
        }
        if (!MatcherUtils.isPhoneRegularlyNew(phone)) {
            ToastUtils.showToast( Utils.getString(R.string.phone_matcher_failed));
            return;
        }
        KeyBoardUtils.closeKeybord(mEtPass, mContext);
        mPresenter.doLogin(phone, pass);
    }


    /* 登录成功 */
    @Override
    public void onSuccess(String msg) {
        ToastUtils.showToast(msg);

        Intent intent = new Intent(ACTION_REQUEST);     // 在MineFragment 重新获取用户数据
        mContext.sendBroadcast(intent);

        if(mLoginInterface != null){
            mLoginInterface.goHome();
        }
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void showLoading() {
        Utils.uProgressDialog(mContext);
    }

    @Override
    public void hideLoading() {
        Utils.dismissProgress();
    }




    public interface LoginInterface extends Parcelable {
        /**
         * 忘记密码 -- 显示忘记密码界面
         */
        void resetPass();

        /**
         * 去主界面
         */
        void goHome();


        @Override
        int describeContents();

        @Override
        void writeToParcel(Parcel dest, int flags);
    }

}
