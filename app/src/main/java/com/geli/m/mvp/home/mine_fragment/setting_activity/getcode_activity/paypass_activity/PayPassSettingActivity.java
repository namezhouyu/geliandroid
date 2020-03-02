package com.geli.m.mvp.home.mine_fragment.setting_activity.getcode_activity.paypass_activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.coustomview.PasswordInputView;
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
 * 支付密码
 */

public class PayPassSettingActivity extends MVPActivity<PayPassSettingPresentImpl> implements View.OnClickListener, BaseView {

    /** 标题 */
    @BindView(R.id.tv_title_name)
    TextView tv_title;
    /** 重新输入"支付密码" */
    @BindView(R.id.piv_paypasssetting)
    PasswordInputView mPasswordInputView;
    /** 传递过来的 -- 验证码 */
    private String mCode;

    @Override
    protected int getResId() {
        return R.layout.activity_paypasssetting;
    }

    @Override
    protected void init() {
        super.init();
        mCode = getIntent().getStringExtra(INTENT_CODE);
    }

    @Override
    protected void initData() {
        tv_title.setText(Utils.getString(R.string.title_paypasssetting));
        KeyBoardUtils.openKeybord(mPasswordInputView,mContext);
    }

    @Override
    protected void initEvent() {
    }

    @OnClick({R.id.bt_paypasssetting_submit, R.id.iv_title_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;

            case R.id.bt_paypasssetting_submit:             /* 提交支付密码 */
                submit();
                break;

            default:
                break;
        }
    }


    /**
     * 提交支付密码
     */
    private void submit() {
        String trim = mPasswordInputView.getText().toString().trim();
        if (TextUtils.isEmpty(trim) || trim.length() != 6) {
            ShowSingleToast.showToast(mContext, Utils.getString(R.string.pass_matcher_failed));
            return;
        }
        Map data = new HashMap();
        data.put("user_id", GlobalData.getUser_id());
        data.put("phone", LoginInformtaionSpUtils.getLoginInfString(mContext, LoginInformtaionSpUtils.login_phone));
        data.put("password", Utils.md5(trim));
        data.put("code", mCode);
        mPresenter.resetPayPass(data);
    }

    @Override
    protected PayPassSettingPresentImpl createPresenter() {
        return new PayPassSettingPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
        KeyBoardUtils.closeKeybord(mPasswordInputView, mContext);
        // 设置了"支付密码"
        LoginInformtaionSpUtils.setLoginInfBoolean(mContext, LoginInformtaionSpUtils.login_pay_pwd, true);
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
