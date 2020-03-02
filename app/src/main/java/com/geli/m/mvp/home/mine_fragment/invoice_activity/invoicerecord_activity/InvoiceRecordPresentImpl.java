package com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicerecord_activity;

import com.geli.m.bean.InvoiceRecordBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;

/**
 * shen
 */
public class InvoiceRecordPresentImpl extends BasePresenter<InvoiceRecordView, InvoiceRecordModelImpl> {


    @Override
    protected InvoiceRecordModelImpl createModel() {
        return new InvoiceRecordModelImpl();
    }

    public InvoiceRecordPresentImpl(InvoiceRecordView mvpView) {
        super(mvpView);
    }


    public void invoiceRecords(String user_id, String page) {
       mModel.invoiceRecords(user_id, page, new BaseObserver<InvoiceRecordBean>(this, mvpView, true) {
            @Override
            protected void onSuccess(InvoiceRecordBean data) {
                if(data.getCode() == Constant.REQUEST_OK || data.getCode() == Constant.REQUEST_NODATA){
                    mvpView.getInvoiceRecordsSuccess(data);
                    // mvpView.onSuccess(data.getMessage());
                }else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }


}
