package com.geli.m.mvp.home.mine_fragment.setting_activity.getcode_activity.loginpass_activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.api.UrlSet;
import com.geli.m.mvp.base.BaseView;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.utils.KeyBoardUtils;
import com.geli.m.utils.LoginInformtaionSpUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;
import java.util.HashMap;
import java.util.Map;

import static com.geli.m.config.Constant.INTENT_CODE;

/**
 * Created by Steam_l on 2018/1/31.
 *
 * 重新设置"登录密码"
 */
public class LoginPassSettingActivity extends MVPActivity<ChangeLoginPassPresentImpl> implements View.OnClickListener, BaseView {

    /** 标题 */
    @BindView(R.id.tv_title_name)
    TextView mTvTitle;
    /** 是否显示"密码" */
    @BindView(R.id.cb_loginpasssetting_viewpass)
    CheckBox mCbViewPass;
    /** 密码输入框 */
    @BindView(R.id.et_loginpasssetting_newpassword)
    EditText mEtPass;

    /** 传递过来的 -- 验证码 */
    private String mCode;

    @Override
    protected int getResId() {
        return R.layout.activity_loginpasssetting;
    }

    @Override
    protected void init() {
        super.init();
        mCode = getIntent().getStringExtra(INTENT_CODE);
    }

    @Override
    protected void initData() {
        mTvTitle.setText(Utils.getString(R.string.title_loginpasssetting));
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.cb_loginpasssetting_viewpass, R.id.bt_loginpasssetting_submit, R.id.iv_title_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;

            case R.id.cb_loginpasssetting_viewpass:                 /* 是否显示"密码" */
                KeyBoardUtils.changePassState(mCbViewPass, mEtPass);    // 切换密码状态
                break;

            case R.id.bt_loginpasssetting_submit:                   /* 提交按钮 */
                submit();
                break;

            default:
                break;
        }
    }

    private void submit() {
        String trim = mEtPass.getText().toString().trim();
        if (TextUtils.isEmpty(trim) || trim.length() < 6) {
            ShowSingleToast.showToast(mContext, Utils.getString(R.string.pass_matcher_failed));
            return;
        }

        Map data = new HashMap();
        data.put("phone", LoginInformtaionSpUtils.getLoginInfString(mContext, LoginInformtaionSpUtils.login_phone));
        data.put("password", trim);
        data.put("code", mCode);
        mPresenter.changeLoginPass(UrlSet.doForgetPwd, data);
        KeyBoardUtils.closeKeybord(mEtPass, mContext);
    }

    @Override
    protected ChangeLoginPassPresentImpl createPresenter() {
        return new ChangeLoginPassPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
        // 重新设置 -- 登录密码 在本地
        LoginInformtaionSpUtils.setLoginInfString(mContext, LoginInformtaionSpUtils.login_password, mEtPass.getText().toString().trim());
        finish();
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
}
