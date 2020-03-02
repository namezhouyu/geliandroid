package com.geli.m.mvp.home.mine_fragment.invoicemerge_activity.invoicelist_activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.geli.m.R;
import com.geli.m.bean.InvoiceOrderBean;
import com.geli.m.utils.TimeUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import static com.geli.m.utils.TimeUtils.format13;

/**
 * Created by Steam_l on 2018/1/11.
 * <p>
 * 发票管理 中 发票类型(增值税普通发票， 增值税专用发票) 个人发票
 */

public class InvoiceListViewHolder extends BaseViewHolder<InvoiceOrderBean.DataBean> {
    Context mContext;

    @BindView(R.id.cb_select_InvoiceListVH)
    CheckBox mCbSelect;
    @BindView(R.id.tv_time_InvoiceListVH)
    TextView mTvTime;
    @BindView(R.id.tv_order_sn_InvoiceListVH)
    TextView mTvOrderSn;
    @BindView(R.id.tv_money_InvoiceListVH)
    TextView mTvMoney;

    boolean isSelect;
    OnCheckBoxClickListener mListener;

    public InvoiceListViewHolder(ViewGroup parent, Context context, boolean isSelect, OnCheckBoxClickListener listener) {
        super(parent, R.layout.itemview_invoice_list);
        mContext = context;
        this.isSelect = isSelect;
        mListener = listener;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(InvoiceOrderBean.DataBean data) {
        super.setData(data);

        mTvTime.setText(TimeUtils.transForDate(data.getAdd_time(), format13));
        mTvOrderSn.setText(Utils.getString(R.string.order_number, data.getOrder_sn()));
        mTvMoney.setText(Utils.getString(R.string.money_unit_2, data.getSum_amount()));

        if(isSelect) {
            setCbClick(data);

        }else {
            mCbSelect.setVisibility(View.GONE);
        }
    }

    public void setCbClick(final InvoiceOrderBean.DataBean data) {
        mCbSelect.setChecked(data.isClick());
        mCbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mListener != null){
                    data.setClick(isChecked);
                    mListener.itemClick(getAdapterPosition(), data);
                }
            }
        });
    }

    public interface OnCheckBoxClickListener {
        void itemClick(int position, InvoiceOrderBean.DataBean data);
    }
}
