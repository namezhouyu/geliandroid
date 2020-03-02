package com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity.addbank_activity.bind;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity.BankListActivity;
import com.geli.m.mvp.home.mine_fragment.setting_activity.getcode_activity.GetCodeView;
import com.geli.m.utils.KeyBoardUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Steam_l on 2018/1/18.
 */

public class BindBankActivity extends MVPActivity<BindBankPresentImpl> implements View.OnClickListener, GetCodeView {
    @BindView(R.id.tv_title_name)
    TextView tv_title;
    @BindView(R.id.et_bindbank_banknumber)
    EditText et_banknumber;
    @BindView(R.id.et_bindbank_code)
    EditText et_code;
    @BindView(R.id.et_bindbank_phone)
    EditText et_phone;
    @BindView(R.id.bt_bindbank_getcode)
    Button bt_getcode;
    public static final String INTENT_NAME = "intent_name";
    public static final String INTENT_IDNUMBER = "intent_idnumber";
    private String name;
    private String idnumber;

    @Override
    protected int getResId() {
        return R.layout.activity_bindbank;
    }

    @Override
    protected void init() {
        super.init();
        name = getIntent().getStringExtra(INTENT_NAME);
        idnumber = getIntent().getStringExtra(INTENT_IDNUMBER);
    }

    @Override
    protected void initData() {
        tv_title.setText(Utils.getString(R.string.title_bindbank));
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    @OnClick({R.id.bt_bindbank_getcode, R.id.bt_bindbank_bind, R.id.iv_title_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.bt_bindbank_bind:
                binBank();
                break;
            case R.id.bt_bindbank_getcode:
                getCode();
                break;

            default:
                break;
        }
    }

    private void getCode() {
        String phone = et_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ShowSingleToast.showToast(mContext, Utils.getString(R.string.please_input_phone));
            return;
        }
        mPresenter.getCode(phone, "4", Utils.md5(phone + Utils.md5(GlobalData.Md5Str)));
    }

    private CountDownTimer mCountDownTimer;

    private void startCountDown() {
        if (mCountDownTimer == null) {

            //第一个参数表示总时间，第二个参数表示间隔时间
            mCountDownTimer = new CountDownTimer(60000, 1000) {//第一个参数表示总时间，第二个参数表示间隔时间
                @Override
                public void onTick(long millisUntilFinished) {
                    if (bt_getcode != null) {
                        bt_getcode.setText(millisUntilFinished / 1000 + "S");
                    }
                }

                @Override
                public void onFinish() {
                    if (bt_getcode != null) {
                        bt_getcode.setText(Utils.getString(R.string.resend_code));
                        bt_getcode.setClickable(true);
                    }
                }
            };
        }
        bt_getcode.setClickable(false);
        mCountDownTimer.start();
    }

    private void binBank() {
        String phone = et_phone.getText().toString().trim();
        String banknumber = et_banknumber.getText().toString().trim();
        String code = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(phone)
                || TextUtils.isEmpty(banknumber)
                || TextUtils.isEmpty(code)) {
            ShowSingleToast.showToast(mContext, Utils.getString(R.string.message_pelasefillinfo));
            return;
        }
        Map data = new HashMap();
        data.put("idCardCode", idnumber);
        data.put("name", name);
        data.put("accountNo", banknumber);
        data.put("bankPreMobile", phone);
        data.put("code", code);
        data.put("user_id", GlobalData.getUser_id());
        mPresenter.bindBank(data);
    }

    @Override
    protected BindBankPresentImpl createPresenter() {
        return new BindBankPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
        if (!msg.equals(Utils.getString(R.string.message_waitgetcode))) {
            KeyBoardUtils.closeKeybord(et_code, mContext);
            startActivity(BankListActivity.class, new Intent());
        }
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
        startCountDown();
    }
}
