package com.geli.m.mvp.home.mine_fragment.invoicemerge_activity.invoicelist_activity;


import com.geli.m.bean.InvoiceOrderBean;
import com.geli.m.mvp.base.BaseView;

/**
 * shen
 */
public interface InvoiceListView extends BaseView {
    void setInvoiceOrder(InvoiceOrderBean data);
}
