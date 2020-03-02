package com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicetype_activity.addoreditinvoice_activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.bean.InvoiceBean;
import com.geli.m.coustomview.MyTabLayout;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.utils.Utils;

import static com.geli.m.config.Constant.INTENT_INVOICE_DATA;
import static com.geli.m.config.Constant.INVOICE_PERSONAL;

/**
 * Created by Steam_l on 2018/1/8.
 */

public class AddOrEditInvoiceActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_title_name)
    TextView mTvTitle;

    @BindView(R.id.vp_addoredit_content)
    ViewPager mVpContent;
    @BindView(R.id.mtl_invoice_layout)
    MyTabLayout mMyTabLayout;


    private InvoiceBean.DataEntity mDataEntity;

    @Override
    protected int getResId() {
        return R.layout.activity_addoredit_invoice;
    }

    @Override
    protected void init() {
        super.init();
        mDataEntity = getIntent().getParcelableExtra(INTENT_INVOICE_DATA);
    }

    @Override
    protected void initData() {
        mVpContent.setAdapter(new InvoicePagerAdapter(getSupportFragmentManager(), mDataEntity));
        mMyTabLayout.setViewPager(mVpContent);

        if (mDataEntity != null) {                                          /* 非空的 -- "修改发票" */
            mTvTitle.setText(Utils.getString(R.string.title_editinvoice));
            // 根据类型设置标签 -- 默认是"单位"的 -- 如果不是就修改
            if (mDataEntity.getBelong() == INVOICE_PERSONAL) {   /* 1：单位；  2：个人 */
                mVpContent.setCurrentItem(1);
            }

        } else {                                                            /* 空的 -- "添加发票" */
            mTvTitle.setText(Utils.getString(R.string.title_addinvoice));
        }
    }

    @Override
    protected void initEvent() {

    }

    public InvoiceBean.DataEntity getEntity() {
        return mDataEntity;
    }


    @OnClick({R.id.tv_invoice_unit, R.id.tv_invoice_personal, R.id.iv_title_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_invoice_unit:                      /* 单位 */
                mVpContent.setCurrentItem(0);
                break;

            case R.id.tv_invoice_personal:                 /* 个人 */
                mVpContent.setCurrentItem(1);
                break;

            case R.id.iv_title_back:
                finish();
                break;

            default:
                break;
        }
    }
}
