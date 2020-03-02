package com.geli.m.mvp.home.mine_fragment.setting_activity.getcode_activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.coustomview.VerificationCodeInput;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.mine_fragment.setting_activity.getcode_activity.loginpass_activity.LoginPassSettingActivity;
import com.geli.m.mvp.home.mine_fragment.setting_activity.getcode_activity.paypass_activity.PayPassSettingActivity;
import com.geli.m.utils.KeyBoardUtils;
import com.geli.m.utils.LoginInformtaionSpUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;

import static com.geli.m.config.Constant.INTENT_CODE;
import static com.geli.m.config.Constant.INTENT_TYPE;

/**
 * Created by Steam_l on 2018/1/31.
 *
 * 登录密码设置 / 支付密码设置
 */
public class GetCodeActivity extends MVPActivity<GetCodePresentImpl> implements View.OnClickListener, GetCodeView {

    /** 标题 */
    @BindView(R.id.tv_title_name)
    TextView mTvTitle;
    /** 这次处理的是什么消息 -- 是"支付"还是密码"登录密码" */
    @BindView(R.id.tv_getcode_mess)
    TextView mTvMess;
    /** 手机号码 */
    @BindView(R.id.tv_getcode_phone)
    TextView mTvPhone;
    /** 包裹"倒计时"的布局 */
    @BindView(R.id.ll_getcode_time_layout)
    LinearLayout mLlCountdownLayout;
    /** 倒计时 */
    @BindView(R.id.tv_getcode_tiem)
    TextView mTvTime;
    /** 重新发送验证码 */
    @BindView(R.id.tv_getcode_resend)
    TextView mTvResend;
    /** 验证码控件 */
    @BindView(R.id.VerificationCodeInput)
    VerificationCodeInput mCodeInput;

    /** 类型 -- 忘记密码 */
    public static final int TYPE_LOGINPASS = 2;
    /** 类型 -- 支付密码 */
    public static final int TYPE_PAYPASS = 3;

    /** 当前类型(忘记密码/支付密码) */
    private int mCurrType = TYPE_LOGINPASS;

    /** 倒计时类 */
    private CountDownTimer mCountDownTimer;

    @Override
    protected int getResId() {
        return R.layout.activity_getcode;
    }

    @Override
    protected void init() {
        super.init();
        mCurrType = getIntent().getIntExtra(INTENT_TYPE, mCurrType);
    }

    @Override
    protected void initData() {
        setView();
        getCode();
    }

    @Override
    protected void initEvent() {
        codeInputListener();                // 验证码输入框中的监听事件
    }

    private void setView() {
        if (mCurrType == TYPE_LOGINPASS) {
            mTvTitle.setText(Utils.getString(R.string.title_loginpasssetting));
        } else {
            mTvTitle.setText(Utils.getString(R.string.title_paypasssetting));
        }

        // Utils.phoneEncryption() 将"手机号码"中的几个号码使用 "*" 代替
        mTvPhone.setText(Utils.phoneEncryption(
                LoginInformtaionSpUtils.getLoginInfString(mContext, LoginInformtaionSpUtils.login_phone)));

        setMessage();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    /**
     * 设置消息
     *
     * 在一个控件中显示这些"字符串" 分块设置颜色
     */
    private void setMessage() {
        // 在一个控件中显示这些"字符串" 分块设置颜色
        String isSend = Utils.getString(R.string.issend);
        String code = Utils.getString(R.string.code);
        String youPhone = Utils.getString(R.string.youphone);
        SpannableString spannableString = new SpannableString(isSend + code + youPhone);
        spannableString.setSpan(new ForegroundColorSpan(Utils.getColor(R.color.specifitext_color)),
                0, isSend.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Utils.getColor(R.color.specifitext_color)),
                isSend.length() + code.length(), spannableString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTvMess.setText(spannableString);
    }


    /**
     * 获取验证码
     */
    private void getCode() {
        String phone = LoginInformtaionSpUtils.getLoginInfString(mContext, LoginInformtaionSpUtils.login_phone);
        mPresenter.getCode(phone, mCurrType + "", Utils.md5(phone + Utils.md5(GlobalData.Md5Str)));
    }


    /**
     * 验证码输入框中的监听事件
     */
    private void codeInputListener() {
        mCodeInput.setOnCompleteListener(new VerificationCodeInput.Listener() {
            @Override
            public void onComplete(String content) {       // 验证控件"填满了内容"
                if (mPresenter.verifyCode(content)) {      // 验证验证码
                    gotoPassSetting();
                    finish();
                }
            }
        });
    }






    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    @Override
    protected GetCodePresentImpl createPresenter() {
        return new GetCodePresentImpl(this);
    }

    @OnClick({R.id.tv_getcode_resend, R.id.iv_title_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_getcode_resend:            /* 重新发送验证码 */
                startCountDown();
                break;

            case R.id.iv_title_back:
                finish();
                break;

            default:
                break;
        }
    }

    /**
     * 倒计时
     */
    private void startCountDown() {
        if (mCountDownTimer == null) {
            //第一个参数表示总时间，第二个参数表示间隔时间
            mCountDownTimer = new CountDownTimer(60000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTvResend.setVisibility(View.GONE);
                    mLlCountdownLayout.setVisibility(View.VISIBLE);
                    if (mTvTime != null) {
                        mTvTime.setText(millisUntilFinished / 1000 + "S");
                    }
                }

                @Override
                public void onFinish() {                            // 倒计时到了，就可以选择"再次发送验证码"
                    mTvResend.setVisibility(View.VISIBLE);
                    mLlCountdownLayout.setVisibility(View.GONE);
                }
            };
        }
        mCountDownTimer.start();
    }


    /**
     * 验证码验证成功后 -- 跳到对应的界面
     */
    public void gotoPassSetting() {

        if (mCurrType == TYPE_LOGINPASS) {              /* 忘记密码 */
            Intent intent = new Intent();
            intent.putExtra(INTENT_CODE, mPresenter.mCurrCode);         // 将"验证码"传递过去
            startActivity(LoginPassSettingActivity.class, intent);

        } else {                                       /* 支付密码 */
            Intent intent = new Intent();
            intent.putExtra(INTENT_CODE, mPresenter.mCurrCode);         // 将"验证码"传递过去
            startActivity(PayPassSettingActivity.class, intent);
        }
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
        KeyBoardUtils.openKeybord((EditText) mCodeInput.getChildAt(0), mContext);   // 打开软件盘
    }


    // 向后台请求了"验证码" -- 等待短信
    @Override
    public void waitGetCode() {
        mTvResend.setVisibility(View.GONE);
        mLlCountdownLayout.setVisibility(View.VISIBLE);
        startCountDown();
    }
}
