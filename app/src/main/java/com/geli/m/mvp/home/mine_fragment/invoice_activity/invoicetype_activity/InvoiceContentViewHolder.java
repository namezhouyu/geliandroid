package com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicetype_activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.InvoiceBean;
import com.geli.m.coustomview.SelectLayout;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicetype_activity.addoreditinvoice_activity.AddOrEditInvoiceActivity;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import static com.geli.m.config.Constant.INTENT_INVOICE_DATA;
import static com.geli.m.config.Constant.INVOICE_ELECTRONIC;
import static com.geli.m.config.Constant.INVOICE_UNIT;
import static com.geli.m.config.Constant.INVOICE_UNIT_SPECIAL;

/**
 * Created by Steam_l on 2018/1/11.
 */

public class InvoiceContentViewHolder extends BaseViewHolder<InvoiceBean.DataEntity> {

    Context mContext;

    /** 左边的选择 */
    private final CheckBox mCb_select;
    /** 单位税号 */
    private final TextView mTv_unitnumber;
    private final TextView mTv_unitbank;
    private final TextView mTv_unitaddress;
    private final TextView mTv_type;
    private final TextView mTv_email;
    private final TextView mTv_name;
    private final TextView mTv_phone;

    /** 编辑/修改 图片 */
    private final ImageView mIv_edit;



    public InvoiceContentViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_invoice_type_content);

        mContext = context;

        mCb_select = $(R.id.cb_itemview_invoice);
        mTv_phone = $(R.id.tv_itemview_invoice_phone);
        mTv_name = $(R.id.tv_itemview_invoice_name);
        mTv_email = $(R.id.tv_itemview_invoice_eamil);
        mTv_type = $(R.id.tv_itemview_invoice_type);
        mTv_unitaddress = $(R.id.tv_itemview_invoice_unitaddress);
        mTv_unitbank = $(R.id.tv_itemview_invoice_unitbank);
        mTv_unitnumber = $(R.id.tv_itemview_invoice_unitnumber);
        mIv_edit = $(R.id.iv_itemview_invoice_edit);
    }

    @Override
    public void setData(final InvoiceBean.DataEntity data) {
        super.setData(data);

        if (((InvoiceTypeActivity) mContext).getCurrMode() == InvoiceTypeActivity.INVOICEMODE_SELECT) { /* 选择模式 */
            setDataSelectMode();
        } else {                                                                                /* 管理模式 */
            setDataManagerMode(data);
        }

        initIvEditListener(data);
        initView(data);
    }

    /**
     * "编辑图片"的监听事件
     * @param data
     */
    private void initIvEditListener(final InvoiceBean.DataEntity data) {
        mIv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(INTENT_INVOICE_DATA, data);
                ((BaseActivity) mContext).startActivity(AddOrEditInvoiceActivity.class, intent);
            }
        });
    }

    /**
     * 设置 --  选择模式
     */
    private void setDataSelectMode() {
        ((SelectLayout) itemView).close();
        mCb_select.setChecked(false);
        mIv_edit.setVisibility(View.GONE);              // 选择模式下 不可 修改/编辑 发票
    }

    /**
     * 设置 -- 管理模式
     * @param data
     */
    private void setDataManagerMode(final InvoiceBean.DataEntity data) {
        if (((InvoiceTypeActivity) mContext).isShowSelect()) {                      /* 正在选择删除的发票 */
            mCb_select.setChecked(data.isCheck);
            ((SelectLayout) itemView).open();

            mCb_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    data.isCheck = isChecked;
                }
            });

        } else {
            ((SelectLayout) itemView).close();
        }

        mIv_edit.setVisibility(View.VISIBLE);
    }




    private void initView(InvoiceBean.DataEntity data) {
        mTv_name.setText(data.getName());
        mTv_type.setText(data.getInvoice_type() == INVOICE_ELECTRONIC ? Utils.getString(R.string.invoice_electronicinvoice) : Utils.getString(R.string.invoice_pagerinvoice));

        if (data.getBelong() == INVOICE_UNIT) {                  /* 单位 */
            initViewUnit(data);
        } else {                                                /* 个人 */
            initViewPersonal(data);
        }
    }

    /**
     * 个人
     * @param data
     */
    private void initViewPersonal(InvoiceBean.DataEntity data) {
        mTv_unitnumber.setVisibility(View.GONE);
        mTv_unitaddress.setVisibility(View.GONE);
        mTv_unitbank.setVisibility(View.GONE);

        if (data.getInvoice_type() == INVOICE_ELECTRONIC) {     /* 电子发票 */
            mTv_phone.setVisibility(View.GONE);
            mTv_email.setVisibility(View.VISIBLE);
            mTv_email.setText(Utils.getString(R.string.invoice_emailaddress, data.getEmail()));

        } else {                                                /* 纸质发票 */
            mTv_phone.setVisibility(View.VISIBLE);
            mTv_email.setVisibility(View.GONE);
            mTv_phone.setText(Utils.getString(R.string.invoice_phone, data.getTel()));
        }
    }

    /**
     * 单位
     * @param data
     */
    private void initViewUnit(InvoiceBean.DataEntity data) {
        mTv_phone.setVisibility(View.GONE);
        mTv_unitaddress.setVisibility(View.VISIBLE);
        mTv_unitnumber.setVisibility(View.VISIBLE);

        if (data.getType() == INVOICE_UNIT_SPECIAL) {                /* 增值专用 */
            mTv_unitbank.setVisibility(View.VISIBLE);
            mTv_unitbank.setText(Utils.getString(R.string.invoice_unitbankname, data.getAccount_name()));
            mTv_email.setVisibility(View.GONE);

        } else {                                                /* 增值普通 */
            mTv_unitbank.setVisibility(View.GONE);
            if (data.getInvoice_type() == INVOICE_ELECTRONIC) { //电子发票
                mTv_email.setText(Utils.getString(R.string.invoice_emailaddress, data.getEmail()));
                mTv_email.setVisibility(View.VISIBLE);
            } else {                                            //纸质发票
                mTv_email.setVisibility(View.GONE);
            }
        }
        mTv_unitaddress.setText(Utils.getString(R.string.invoice_unitaddress, data.getAddress()));
        mTv_unitnumber.setText(Utils.getString(R.string.invoice_unitnumber, data.getDuty_paragraph()));
    }
}
