package com.geli.m.mvp.home.other.login_register_activity.regist_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.mvp.base.MVPFragment;
import com.geli.m.mvp.home.other.WebViewActivity;
import com.geli.m.mvp.home.other.login_register_activity.RegistView;
import com.geli.m.utils.KeyBoardUtils;
import com.geli.m.utils.MatcherUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;

import static com.geli.m.config.Constant.INTENT_INTERFACE;
import static com.geli.m.config.Constant.INTENT_LINKS;

/**
 * Created by Administrator on 2017/12/15.
 *
 * 1注册, 2忘记密码
 */
public class RegistFragment extends MVPFragment<RegistPresentImpl> implements View.OnClickListener, RegistView {


    /** 电话 */
    @BindView(R.id.et_regist_phone)
    EditText mEtPhone;
    /** 密码 */
    @BindView(R.id.et_regist_newpassword)
    EditText mEtPass;
    /** 验证码 */
    @BindView(R.id.et_regist_code)
    EditText mEtCode;
    /** 获取验证码按钮 */
    @BindView(R.id.bt_regist_getcode)
    Button mBtGetCode;
    /** 注册按钮 */
    @BindView(R.id.bt_regist)
    Button mBtRegister;
    /** 是否同意协议  */
    @BindView(R.id.cb_regist_xieyi)
    CheckBox mCbXieyi;
    /** 密码是否可见 */
    @BindView(R.id.cb_regist_viewpass)
    CheckBox mCbViewpass;

    /** 再次获取验证码倒计时 */
    private CountDownTimer mCountDownTimer;

    /** 1注册, 2忘记密码 */
    private int mType = 1;

    RegisterInterface mRegisterInterface;

    public static RegistFragment newInstance(RegistFragment.RegisterInterface registerInterface) {

        RegistFragment fragment = new RegistFragment();
        Bundle args = new Bundle();
        args.putParcelable(INTENT_INTERFACE, registerInterface);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getResId() {
        return R.layout.fragment_regist;
    }

    @Override
    protected void init() {
        Bundle arguments = getArguments();
        mRegisterInterface = arguments.getParcelable(INTENT_INTERFACE);
    }

    @Override
    protected void initData() {
        initView();
    }

    @Override
    protected void initEvent() {

    }

    private void initView() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }

        mEtCode.setText("");
        mEtPass.setText("");
        mEtPhone.setText("");
        mBtGetCode.setEnabled(true);
        mBtGetCode.setText(Utils.getString(R.string.getcode));

        mCbXieyi.setChecked(false);
        mCbViewpass.setChecked(false);

        KeyBoardUtils.changePassState(mCbViewpass, mEtPass);
    }

    @OnClick({R.id.cb_regist_viewpass, R.id.bt_regist,
            R.id.bt_regist_getcode, R.id.tv_regist_gotoxieyi})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_regist_viewpass:
                KeyBoardUtils.changePassState(mCbViewpass, mEtPass);
                break;

            case R.id.bt_regist:
                doRegister();
                break;

            case R.id.bt_regist_getcode:
                getCode();
                break;

            case R.id.tv_regist_gotoxieyi:
                mPresenter.getProtocol();
                break;

            default:
                break;
        }
    }

    private void doRegister() {
        if (mPresenter.mCurrCode.isEmpty()) {
            ToastUtils.showToast(Utils.getString(R.string.message_pleasegetverificationcode));
            return;
        }
        String phone = mEtPhone.getText().toString().trim();
        String pass = mEtPass.getText().toString().trim();
        if (phone.isEmpty() || pass.isEmpty()) {
            ToastUtils.showToast(Utils.getString(R.string.info_no_null));
            return;
        }
        if (!MatcherUtils.isPasswordRegularly(pass)) {
            ToastUtils.showToast(Utils.getString(R.string.pass_matcher_failed));
            return;
        }
        if (!mCbXieyi.isChecked()) {
            ToastUtils.showToast(Utils.getString(R.string.plase_agree_the_agreement));
            return;
        }
        KeyBoardUtils.closeKeybord(mEtCode, mContext);
        mPresenter.submit(mType + "", phone, mEtCode.getText().toString().trim(), pass);
    }

    private void getCode() {
        String phone = mEtPhone.getText().toString().trim();
        if (phone.isEmpty()) {
            ToastUtils.showToast(Utils.getString(R.string.phone_no_null));
            return;
        }
        if (!MatcherUtils.isPhoneRegularlyNew(phone)) {
            ToastUtils.showToast(Utils.getString(R.string.phone_matcher_failed));
            return;
        }
        mPresenter.getCode(phone, mType + "", Utils.md5(phone + Utils.md5(GlobalData.Md5Str)));
    }

    @Override
    protected RegistPresentImpl createPresent() {
        return new RegistPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
    }

    @Override
    public void onError(String msg) {
        ShowSingleToast.showToast(mContext, msg);
    }

    @Override
    public void showLoading() {
        Utils.uProgressDialog(mContext);
    }

    @Override
    public void hideLoading() {
        Utils.dismissProgress();
    }

    @Override
    public void waitGetCode() {
        mBtGetCode.setEnabled(false);
        if (mCountDownTimer == null) {
            //第一个参数表示总时间，第二个参数表示间隔时间
            mCountDownTimer = new CountDownTimer(60000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (mBtGetCode != null) {
                        mBtGetCode.setText(millisUntilFinished / 1000 + Utils.getString(R.string.time_s));
                    }
                }

                @Override
                public void onFinish() {
                    if (mBtGetCode != null) {
                        mBtGetCode.setEnabled(true);
                        mBtGetCode.setText(Utils.getString(R.string.getcodeagain));
                    }
                }
            };
        }
        mCountDownTimer.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        initData();
    }

    @Override
    public void submitSuccess() {
        initData();
        KeyBoardUtils.closeKeybord(mEtCode, mContext);

        if(mRegisterInterface != null){
            mRegisterInterface.registerSuccessful();
        }
    }


    /* 使用 web 打开协议链接 */
    @Override
    public void gotoProtocol(String url) {
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra(INTENT_LINKS, url);
        startActivity(intent);
    }


    public interface RegisterInterface extends Parcelable {
        /**
         * 注册成功后调用 -- 去登录界面
         */
        void registerSuccessful();

        @Override
        int describeContents();

        @Override
        void writeToParcel(Parcel dest, int flags);
    }
}
