package com.geli.m.mvp.home.mine_fragment.invoicemerge_activity;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.geli.m.R;
import com.geli.m.bean.ShopInvoiceBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2018/1/11.
 * <p>
 * 发票管理 中 发票类型(增值税普通发票， 增值税专用发票) 个人发票
 */

public class InvoiceMergeViewHolder extends BaseViewHolder<ShopInvoiceBean.DataBean> {
    Context mContext;

    /**
     * 专用发票 还是 普通发票
     */
    @BindView(R.id.tv_iteminvoicemerge)
    TextView mTvInvoiceMerge;

    public InvoiceMergeViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_invoice_merge);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(ShopInvoiceBean.DataBean data) {
        super.setData(data);
        mTvInvoiceMerge.setText(data.getShop_name());
    }
}
