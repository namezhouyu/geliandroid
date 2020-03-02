package com.geli.m.mvp.home.mine_fragment.invoicemerge_activity;


import com.geli.m.bean.ShopInvoiceBean;
import com.geli.m.mvp.base.BaseView;

/**
 * shen
 */
public interface InvoiceMergeView extends BaseView {
    void setShopInvoice(ShopInvoiceBean data);
}
