package com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicerecord_activity;


import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;

/**
 * shen
 */
public class InvoiceRecordModelImpl extends BaseModel {
    public void invoiceRecords(String user_id, String page, BaseObserver observer) {
        universal(mApiService.invoiceRecords(user_id, page), observer);
    }
}
