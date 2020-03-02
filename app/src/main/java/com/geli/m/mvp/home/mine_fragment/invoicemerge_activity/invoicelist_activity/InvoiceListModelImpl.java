package com.geli.m.mvp.home.mine_fragment.invoicemerge_activity.invoicelist_activity;


import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;

/**
 * shen
 */
public class InvoiceListModelImpl extends BaseModel {
    public void invoiceOrder(String user_id, String order_id, BaseObserver observer) {
        universal(mApiService.invoiceOrder(user_id, order_id), observer);
    }
}
