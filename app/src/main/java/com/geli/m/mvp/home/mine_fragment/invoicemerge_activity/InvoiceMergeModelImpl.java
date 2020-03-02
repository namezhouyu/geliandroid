package com.geli.m.mvp.home.mine_fragment.invoicemerge_activity;


import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;

/**
 * shen
 */
public class InvoiceMergeModelImpl extends BaseModel {
    public void shopInvoice(String user_id, BaseObserver observer) {
        universal(mApiService.shopInvoice(user_id), observer);
    }
}
