package com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicerecord_activity;


import com.geli.m.bean.InvoiceRecordBean;
import com.geli.m.mvp.base.BaseView;

/**
 * shen
 */
public interface InvoiceRecordView extends BaseView {
    void getInvoiceRecordsSuccess(InvoiceRecordBean data);
}
