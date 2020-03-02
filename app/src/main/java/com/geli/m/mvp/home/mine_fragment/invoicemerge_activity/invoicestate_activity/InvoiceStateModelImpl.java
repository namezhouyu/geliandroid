package com.geli.m.mvp.home.mine_fragment.invoicemerge_activity.invoicestate_activity;


import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;

/**
 * shen
 */
public class InvoiceStateModelImpl extends BaseModel {
    public void invoiceDetail(String user_id, String invoice_id, BaseObserver observer) {
        universal(mApiService.invoiceDetail(user_id, invoice_id), observer);
    }
}
