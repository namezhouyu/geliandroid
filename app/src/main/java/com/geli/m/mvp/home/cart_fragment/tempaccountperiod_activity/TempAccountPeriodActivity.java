package com.geli.m.mvp.home.cart_fragment.tempaccountperiod_activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.CartBean;
import com.geli.m.dialog.TipDialog;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;

import static com.geli.m.config.Constant.INTENT_BEAN;
import static com.geli.m.config.Constant.INTENT_SHOP_ID;

/**
 * author:  shen
 * date:    2018/11/12
 *
 * 申请 -- 临时账期
 */
public class TempAccountPeriodActivity extends MVPActivity<TempAccountPeriodPresentImpl> implements TempAccountPeriodView {

    @BindView(R.id.rl_title_rootlayout)
    RelativeLayout mRlTitleRootlayout;
    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title_name)
    TextView mTvTitleName;
    @BindView(R.id.tv_title_right)
    TextView mTvTitleRight;


    /** 可用额度(元) */
    @BindView(R.id.tv_available_quota_tap)
    TextView mTvAvailableQuota;
    /** 已使用(元) */
    @BindView(R.id.tv_amount_used_tap)
    TextView mTvAmountUsed;
    /** 临时账期额度 */
    @BindView(R.id.et_quota_tap)
    EditText mEtQuota;
    /** 最多申请临时账期额度 */
    @BindView(R.id.tv_max_quota_tap)
    TextView mTvMaxQuota;
    /** 提交申请 */
    @BindView(R.id.btn_adjustment_tap)
    Button mBtnAdjustment;

    CartBean.DataEntity.ShEntity mShEntity;
    int shop_id = -1;

    @Override
    protected TempAccountPeriodPresentImpl createPresenter() {
        return new TempAccountPeriodPresentImpl(this);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_tempaccountperiod;
    }

    @Override
    protected void initData() {
        mTvTitleName.setText(getString(R.string.temp_ap));
        getIntentExtra();
        setView();
        setEditWatcher();
    }


    @Override
    protected void initEvent() {

    }

    private void getIntentExtra(){
        Intent intent = getIntent();
        mShEntity = intent.getParcelableExtra(INTENT_BEAN);
        shop_id = intent.getIntExtra(INTENT_SHOP_ID, -1);
    }

    private void setView() {
        if(mShEntity != null){
            mTvAvailableQuota.setText(mShEntity.getAmount());
            mTvAmountUsed.setText(mShEntity.getCost());

            double maxQuota = Double.valueOf(mShEntity.getMax()) -
                    Double.valueOf(mShEntity.getAmount());
            String max = Utils.getFormatDoubleTwoDecimalPlaces(maxQuota, 2);
            mTvMaxQuota.setText(Utils.getString(R.string.max_temp_available, max));
        }

        mBtnAdjustment.setEnabled(false);
    }



    private void setEditWatcher() {
        mEtQuota.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(StringUtils.isNotEmpty(s.toString().trim())){
                    if(s.toString().trim().equals("0")) {
                        mEtQuota.setText("");
                    }else {
                        mBtnAdjustment.setEnabled(true);
                    }
                }else {
                    mBtnAdjustment.setEnabled(false);
                }
            }
        });
    }


    @OnClick({R.id.iv_title_back,R.id.btn_adjustment_tap})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.iv_title_back:
                finish();
                break;

            case R.id.btn_adjustment_tap:
                adjustment();
                break;
        }
    }

    private void adjustment(){
        if(shop_id == -1){
            ToastUtils.showToast(getString(R.string.please_reenter_the_store));
            return;
        }

        String quota = mEtQuota.getText().toString().trim();
        if(StringUtils.isNotEmpty(quota) && !quota.equals("0")){
            mPresenter.tempShApply(GlobalData.getUser_id(), quota, shop_id + "");
        }else {
            ToastUtils.showToast(getString(R.string.please_enter_the_amount));
        }
    }

    @Override
    public void onSuccess(String msg) {
        showTigDialog();
    }


    private void showTigDialog(){
        TipDialog dialog = TipDialog.newInstance(getString(R.string.wait));
        dialog.isShowTitle(false)
                .isShowCancel(false)
                .isShowConfirm(true)
                .setConfirmSrc(getString(R.string.dialog_confirm))
                .setConfirmTextColor(Utils.getColor(R.color.zhusediao))
                .setOnclickListener(new TipDialog.ClickListenerInterface() {
                    @Override
                    public void doConfirm(TipDialog tipDialog) {
                        tipDialog.dismiss();
                    }

                    @Override
                    public void doCancel() {

                    }
                })
                .show(getSupportFragmentManager(), "tempAP");
    }
}
