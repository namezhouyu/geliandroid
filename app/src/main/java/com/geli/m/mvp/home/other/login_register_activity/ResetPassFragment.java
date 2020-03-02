package com.geli.m.mvp.home.other.login_register_activity;

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
import com.geli.m.mvp.home.other.login_register_activity.regist_fragment.RegistPresentImpl;
import com.geli.m.utils.KeyBoardUtils;
import com.geli.m.utils.MatcherUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;

import static com.geli.m.config.Constant.INTENT_INTERFACE;

/**
 * Created by Administrator on 2017/12/15.
 *
 * 重置密码界面  -- 1注册,2忘记密码
 */
public class ResetPassFragment extends MVPFragment<RegistPresentImpl> implements View.OnClickListener, RegistView {


    @BindView(R.id.et_resetpass_phone)
    EditText mEtPhone;
    @BindView(R.id.et_resetpass_newpass)
    EditText mEtNewPass;
    @BindView(R.id.et_resetpass_code)
    EditText mEtCode;
    @BindView(R.id.et_resetpass_newpassagain)
    EditText mEtNewPassAgain;
    @BindView(R.id.bt_resetpass_getcode)
    Button mBtGetCode;
    @BindView(R.id.bt_resetpass_submit)
    Button mBtSubmit;
    @BindView(R.id.cb_resetpass_viewpass1)
    CheckBox mCbViewpass1;
    @BindView(R.id.cb_resetpass_viewpass2)
    CheckBox mCbViewpass2;

    /** 1注册,2忘记密码 */
    private int mType = 2;
    private CountDownTimer mCountDownTimer;


    ResetPassFragment.ResetPassInterface mResetPassInterface;

    public static ResetPassFragment newInstance(ResetPassFragment.ResetPassInterface resetPassInterface) {

        ResetPassFragment fragment = new ResetPassFragment();
        Bundle args = new Bundle();
        args.putParcelable(INTENT_INTERFACE, resetPassInterface);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getResId() {
        return R.layout.fragment_resetpass;
    }

    @Override
    protected void init() {
        Bundle arguments = getArguments();
        mResetPassInterface = arguments.getParcelable(INTENT_INTERFACE);
    }


    @Override
    protected void initData() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        mEtCode.setText("");
        mEtNewPass.setText("");
        mEtNewPassAgain.setText("");
        mEtPhone.setText("");
        mBtGetCode.setText(Utils.getString(R.string.getcode));
        mCbViewpass1.setChecked(false);
        mCbViewpass2.setChecked(false);
        KeyBoardUtils.changePassState(mCbViewpass1, mEtNewPass);
        KeyBoardUtils.changePassState(mCbViewpass2, mEtNewPassAgain);
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.iv_resetpass_close, R.id.bt_resetpass_getcode, R.id.cb_resetpass_viewpass1, R.id.cb_resetpass_viewpass2, R.id.bt_resetpass_submit})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_resetpass_close:                       /* 关闭重置密码  */
                closeResetPass();
                break;

            case R.id.bt_resetpass_getcode:                     /* 获取验证码  */
                getCode();
                break;

            case R.id.cb_resetpass_viewpass1:
                KeyBoardUtils.changePassState(mCbViewpass1, mEtNewPass);
                break;

            case R.id.cb_resetpass_viewpass2:
                KeyBoardUtils.changePassState(mCbViewpass2, mEtNewPassAgain);
                break;

            case R.id.bt_resetpass_submit:                      /* 提交  */
                submit();
                break;

            default:
                break;
        }
    }

    private void submit() {
        if (mPresenter.mCurrCode.isEmpty()) {
            ToastUtils.showToast(Utils.getString(R.string.message_pleasegetverificationcode));
            return;
        }
        String phone = mEtPhone.getText().toString().trim();
        if (Utils.TextIsNull(mContext, phone, Utils.getString(R.string.phone_no_null))) {
            return;
        }
        String newpass = mEtNewPass.getText().toString().trim();
        String newpassagain = mEtNewPassAgain.getText().toString().trim();
        if (!MatcherUtils.isPasswordRegularly(newpass)) {
            ToastUtils.showToast(Utils.getString(R.string.pass_matcher_failed));
            return;
        }
        if (!newpass.equals(newpassagain)) {
            ToastUtils.showToast(Utils.getString(R.string.twopass_noequals));
            return;
        }
        mPresenter.submit(mType + "", phone, mEtCode.getText().toString().trim(), newpass);
    }

    private void getCode() {
        String phone = mEtPhone.getText().toString().trim();
        if (Utils.TextIsNull(mContext, phone, Utils.getString(R.string.phone_no_null))) {
            return;
        }
        if (!MatcherUtils.isPhoneRegularly(phone)) {
            ToastUtils.showToast(Utils.getString(R.string.phone_matcher_failed));
            return;
        }
        mPresenter.getCode(phone, mType + "", Utils.md5(phone + Utils.md5(GlobalData.Md5Str)));
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    private void closeResetPass() {
        initData();
        KeyBoardUtils.closeKeybord(mEtCode, mContext);

        if(mResetPassInterface != null) {
            mResetPassInterface.closeReset();
        }
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
            mCountDownTimer = new CountDownTimer(60000, 1000) {//第一个参数表示总时间，第二个参数表示间隔时间
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
    public void submitSuccess() {
        closeResetPass();
    }

    @Override
    public void gotoProtocol(String url) {

    }



    public interface ResetPassInterface extends Parcelable {
        /**
         * 关闭重置密码
         */
        void closeReset();

        @Override
        int describeContents();

        @Override
        void writeToParcel(Parcel dest, int flags);
    }
}
