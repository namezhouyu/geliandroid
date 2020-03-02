package com.geli.m.mvp.home.mine_fragment.invoicemerge_activity.invoicestate_activity;


import com.geli.m.bean.InvoiceStateBean;
import com.geli.m.mvp.base.BaseView;

/**
 * shen
 */
public interface InvoiceStateView extends BaseView {
    void getInvoiceDetail(InvoiceStateBean.DataBean data);
}
