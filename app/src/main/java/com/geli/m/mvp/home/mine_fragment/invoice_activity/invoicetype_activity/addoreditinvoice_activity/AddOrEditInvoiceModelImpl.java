package com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicetype_activity.addoreditinvoice_activity;

import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/11/18.
 */

public class AddOrEditInvoiceModelImpl extends BaseModel {
    public void addOrEditInvoice(String url, RequestBody body, BaseObserver observer) {
        universal(mApiService.addInvoice(url, body), observer);
    }
}
