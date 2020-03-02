package com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity.unbinbank_activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.coustomview.PasswordInputView;
import com.geli.m.mvp.base.BaseView;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity.BankListActivity;
import com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity.bankdetails_activity.BankDetailsActivity;
import com.geli.m.utils.KeyBoardUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;

/**
 * Created by Steam_l on 2018/3/24.
 */

public class UnBinBankActivity extends MVPActivity<UnBinBankPresentImpl> implements View.OnClickListener, BaseView {
    @BindView(R.id.piv_unbin_inputpass)
    PasswordInputView piv_pass;
    @BindView(R.id.tv_title_name)
    TextView tv_title;
    private String mBank_id;

    @Override
    protected int getResId() {
        return R.layout.activity_unbinbank;
    }

    @Override
    protected void init() {
        super.init();
        mBank_id = getIntent().getStringExtra(BankDetailsActivity.INTENT_BANKID);
    }

    @Override
    protected void initData() {
        tv_title.setText(Utils.getString(R.string.title_unbinbank));
        KeyBoardUtils.openKeybord(piv_pass, mContext);
        piv_pass.setFocusable(true);
        piv_pass.requestFocus();
    }

    @Override
    protected void initEvent() {
        piv_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 6 && piv_pass != null) {
                    mPresenter.unBin(GlobalData.getUser_id(), mBank_id, Utils.md5(s.toString().trim()));
                }
            }
        });
    }

    @OnClick({R.id.iv_title_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    protected UnBinBankPresentImpl createPresenter() {
        return new UnBinBankPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
        KeyBoardUtils.closeKeybord(piv_pass, mContext);
        startActivity(BankListActivity.class, new Intent());
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
