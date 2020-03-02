package com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicetype_activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.InvoiceTitleBean;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2018/1/11.
 *
 * 发票管理 中 发票类型(增值税普通发票， 增值税专用发票) 个人发票
 */

public class InvoiceTitleViewHolder extends BaseViewHolder<InvoiceTitleBean> {
    Context mContext;

    /** 专用发票 还是 普通发票 */
    private final TextView mTv_title;

    public InvoiceTitleViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_smallcart_shopname);
        mContext = context;
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        layoutParams.topMargin = Utils.dip2px(mContext,5);
        mTv_title = $(R.id.tv_itemview_smallcart_title);
    }

    @Override
    public void setData(InvoiceTitleBean data) {
        super.setData(data);
        mTv_title.setText(data.title);
    }
}
