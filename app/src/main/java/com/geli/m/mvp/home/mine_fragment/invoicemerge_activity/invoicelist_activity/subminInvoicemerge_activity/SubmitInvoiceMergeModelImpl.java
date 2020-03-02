package com.geli.m.mvp.home.mine_fragment.invoicemerge_activity.invoicelist_activity.subminInvoicemerge_activity;


import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;

/**
 * shen
 */
public class SubmitInvoiceMergeModelImpl extends BaseModel {
    public void invoiceMerge(String user_id, String order_id,
                             String invoice_id, String address_id,
                             BaseObserver observer) {
        universal(mApiService.invoiceMerge(user_id, order_id, invoice_id, address_id), observer);
    }
}
