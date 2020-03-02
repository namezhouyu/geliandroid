package com.geli.m.mvp.home.other.transferaccounts_activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.bean.TransferBean;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.orderdetails_activity.OrderDetailsActivity;
import com.geli.m.utils.Utils;

import static com.geli.m.config.Constant.INTENT_AP_CLOSING_TIME;
import static com.geli.m.config.Constant.INTENT_BEAN;
import static com.geli.m.config.Constant.INTENT_ORDER_ID;

/**
 * author:  shen
 * date:    2019/1/28y
 * <p>
 * 转账支付
 */
public class TransferAccountsActivity extends MVPActivity<TransferAccountsPresentImpl> implements TransferAccountsView {

    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title_name)
    TextView mTvTitleName;
    @BindView(R.id.tv_title_right)
    TextView mTvTitleRight;
    @BindView(R.id.rl_title_rootlayout)
    RelativeLayout mRlTitleRootlayout;


    @BindView(R.id.tv_beneficiaryName_transferAccountsActivity)
    TextView mTvBeneficiaryName;
    @BindView(R.id.iv_payBank_transferAccountsActivity)
    ImageView mTvPayBank;
    @BindView(R.id.tv_bankInfo_transferAccountsActivity)
    TextView mTvBankInfo;
    @BindView(R.id.tv_accountNumber_transferAccountsActivity)
    TextView mTvAccountNumber;
    @BindView(R.id.tv_amount_transferAccountsActivity)
    TextView mTvAmount;
    @BindView(R.id.tv_orderNumber_transferAccountsActivity)
    TextView mTvOrderNumber;
    @BindView(R.id.btn_checkOrder_transferAccountsActivity)
    Button mBtnCheckOrder;

    TransferBean mTransferBean;
    int mCloseTime = 0;

    @Override
    protected TransferAccountsPresentImpl createPresenter() {
        return new TransferAccountsPresentImpl(this);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_transferaccounts;
    }

    @Override
    protected void initData() {
        mTvTitleName.setText(getString(R.string.transferaccounts));
        getIntentData();
        getData();
    }

    @Override
    protected void initEvent() {

    }

    private void getIntentData(){
        mTransferBean = getIntent().getParcelableExtra(INTENT_BEAN);
        mCloseTime = getIntent().getIntExtra(INTENT_AP_CLOSING_TIME, mCloseTime);
    }

    private void getData() {
        if(mTransferBean != null){
            mTvBeneficiaryName.setText(mTransferBean.getData().getClt_nm());
            mTvAccountNumber.setText(mTransferBean.getData().getSub_no());
            mTvAmount.setText(Utils.getString(R.string.money_unit_2, mTransferBean.getData().getAmount()));
            mTvOrderNumber.setText(mTransferBean.getData().getOrder_sn());
        }
    }

    @OnClick({R.id.iv_title_back, R.id.btn_checkOrder_transferAccountsActivity,
    R.id.btn_copy_transferAccountsActivity})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.iv_title_back:
                finish();
                break;

            case R.id.btn_checkOrder_transferAccountsActivity:
                goOrderDetails();
                break;

            case R.id.btn_copy_transferAccountsActivity:
                copy();
                break;
        }

    }

    private void goOrderDetails() {
        if(mTransferBean != null && mTransferBean.getData() != null){
            Intent intent = new Intent();
            intent.putExtra(INTENT_ORDER_ID, mTransferBean.getData().getOrder_id() + "");
            intent.putExtra(INTENT_AP_CLOSING_TIME, mCloseTime);
            ((BaseActivity) mContext).startActivity(OrderDetailsActivity.class, intent);
        }
    }

    @Override
    public void showLoading() {
        Utils.uProgressDialog(mContext);
    }

    @Override
    public void hideLoading() {
        Utils.dismissProgress();
    }

    private void copy() {
        String mess = "";
        mess += Utils.getString(R.string.beneficiary_name) + "：" + getTvText(mTvBeneficiaryName) + "\n";
        mess += Utils.getString(R.string.pay_bank) + "：" + getString(R.string.bank_hx) + "\n";
        mess += Utils.getString(R.string.bank_info_1) + "：" + getString(R.string.bank_hx_zh) + "\n";
        mess += Utils.getString(R.string.account_number) + "：" + getTvText(mTvAccountNumber) + "\n";
        mess += Utils.getString(R.string.amount1) + "：" + getTvText(mTvAmount) + "\n";
        mess += Utils.getString(R.string.order_number1) + "：" + getTvText(mTvOrderNumber) + "\n";
        mess += Utils.getString(R.string.transferaccounts_tips1);
        Utils.copyToastCenter(mContext, mess);
    }

    private String getTvText(TextView textView){
        if(textView != null){
           return textView.getText().toString().trim();
        }
        return "";
    }
}
