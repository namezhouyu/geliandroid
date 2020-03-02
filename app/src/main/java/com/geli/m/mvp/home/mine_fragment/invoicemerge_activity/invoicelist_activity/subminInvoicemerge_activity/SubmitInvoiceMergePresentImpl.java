package com.geli.m.mvp.home.mine_fragment.invoicemerge_activity.invoicelist_activity.subminInvoicemerge_activity;

import com.geli.m.bean.InvoiceMergeSuccessBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;

/**
 * shen
 */
public class SubmitInvoiceMergePresentImpl extends BasePresenter<SubmitInvoiceMergeView, SubmitInvoiceMergeModelImpl> {


    @Override
    protected SubmitInvoiceMergeModelImpl createModel() {
        return new SubmitInvoiceMergeModelImpl();
    }

    public SubmitInvoiceMergePresentImpl(SubmitInvoiceMergeView mvpView) {
        super(mvpView);
    }

    public void invoiceMerge(String user_id, String order_id,
                             String invoice_id, String address_id) {
       mModel.invoiceMerge(user_id, order_id, invoice_id, address_id,
               new BaseObserver<InvoiceMergeSuccessBean>(this, mvpView, true) {
            @Override
            protected void onSuccess(InvoiceMergeSuccessBean data) {
                if(data.getCode() == Constant.REQUEST_OK){
                    mvpView.invoiceMergeSuccess(data);
                    // mvpView.onSuccess(data.getMessage());
                }else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }


}
