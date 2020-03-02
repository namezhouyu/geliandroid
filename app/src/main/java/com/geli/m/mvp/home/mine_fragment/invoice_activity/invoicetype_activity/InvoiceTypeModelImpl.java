package com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicetype_activity;

import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;

/**
 * Created by Steam_l on 2018/1/11.
 */

public class InvoiceTypeModelImpl extends BaseModel {
    public void getInvoiceList(String user_id, BaseObserver observer) {
        universal(mApiService.getInvoice(user_id), observer);
    }

    public void deleteInvoice(String user_id, String invoice_id, BaseObserver observer) {
        universal(mApiService.deleteInvoice(user_id, invoice_id), observer);
    }
}
