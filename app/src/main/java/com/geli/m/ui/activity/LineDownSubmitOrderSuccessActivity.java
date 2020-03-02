package com.geli.m.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.geli.m.R;
import com.geli.m.bean.OfflinePayBean;
import com.geli.m.mvp.home.main.HomeActivity;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.mine_fragment.myorder_activity.MyOrderActivity;
import com.geli.m.utils.PriceUtils;
import com.geli.m.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Steam_l on 2018/1/5.
 */

public class LineDownSubmitOrderSuccessActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_submitordersuccess_phone)
    TextView tv_callphone;
    @BindView(R.id.tv_linedown_suborder_bank)
    TextView tv_bank;
    @BindView(R.id.tv_linedown_suborder_bankname)
    TextView tv_bankname;
    @BindView(R.id.tv_linedown_suborder_banknumber)
    TextView tv_banknumber;
    @BindView(R.id.tv_linedown_suborder_price)
    TextView tv_price;
    @BindView(R.id.tv_title_name)
    TextView tv_title;
    public static final String INTENT_DATA = "intent_data";
    OfflinePayBean.DataEntity mDataEntity;

    @Override
    protected int getResId() {
        return R.layout.activity_linedownsubmitordersuccess;
    }

    @Override
    protected void init() {
        super.init();
        mDataEntity = getIntent().getParcelableExtra(INTENT_DATA);
        tv_title.setText(Utils.getString(R.string.submit_order));
    }

    @Override
    protected void initData() {
        if (mDataEntity != null) {
            tv_bank.setText(mDataEntity.getShop_name());
            tv_bankname.setText(mDataEntity.getBank_name());
            tv_banknumber.setText(mDataEntity.getBank_account() + "");
            tv_price.setText(PriceUtils.getPrice(mDataEntity.getSum_amount()));
        }
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.tv_linedown_suborder_copy, R.id.bt_submitordersucccess_index, R.id.iv_title_back, R.id.bt_submitordersucccess_myorder, R.id.tv_submitordersuccess_phone})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.tv_linedown_suborder_copy:
                copy();
                break;
            case R.id.bt_submitordersucccess_index:
                startActivity(HomeActivity.class, new Intent());
                break;
            case R.id.bt_submitordersucccess_myorder:
                startActivity(MyOrderActivity.class, new Intent());
                finish();
                break;
            case R.id.tv_submitordersuccess_phone:
                Utils.callPhone(mContext, tv_callphone.getText().toString().trim());
                break;

            default:
                break;
        }
    }

    private void copy() {
        String mess = "";
        mess += Utils.getString(R.string.pay_method) + Utils.getString(R.string.bank_transfer);
        mess += Utils.getString(R.string.amounts_payable) + tv_price.getText();
        mess += Utils.getString(R.string.unit_bank) + tv_bank.getText();
        mess += Utils.getString(R.string.unit_name) + tv_bankname.getText();
        mess += Utils.getString(R.string.unit_account) + tv_banknumber.getText();
        Utils.copy(mContext,mess);
    }
}
