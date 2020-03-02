package com.geli.m.mvp.home.mine_fragment.invoice_activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicerecord_activity.InvoiceRecordActivity;
import com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicetype_activity.InvoiceTypeActivity;

import static com.geli.m.config.Constant.INTENT_MODE;

/**
 * author:  shen
 * date:    2018/12/26
 */
public class InvoiceActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title_name)
    TextView mTvTitleName;
    @BindView(R.id.tv_title_right)
    TextView mTvTitleRight;
    @BindView(R.id.rl_title_rootlayout)
    RelativeLayout mRlTitleRootlayout;


    /** 发票记录 */
    @BindView(R.id.tv_record_activityInvoice)
    TextView mTvRecord;
    /** 发票类型 */
    @BindView(R.id.tv_type_activityInvoice)
    TextView mTvType;


    @Override
    protected int getResId() {
        return R.layout.activity_invoice;
    }

    @Override
    protected void initData() {
        mTvTitleName.setText(getString(R.string.my_invoice));
    }

    @Override
    protected void initEvent() {

    }


    @OnClick({R.id.iv_title_back, R.id.tv_record_activityInvoice, R.id.tv_type_activityInvoice})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {

            case R.id.iv_title_back:
                finish();
                break;

            case R.id.tv_record_activityInvoice:                        /* 发票记录 */
                startActivity(InvoiceRecordActivity.class, new Intent());
                break;

            case R.id.tv_type_activityInvoice:                          /* 发票类型 -- 发票管理 */
                intent = new Intent().putExtra(INTENT_MODE, InvoiceTypeActivity.INVOICEMODE_MANAGER);
                startActivity(InvoiceTypeActivity.class, intent);
                break;
        }
    }
}
