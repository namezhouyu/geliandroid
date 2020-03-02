package com.geli.m.mvp.home.mine_fragment.invoicemerge_activity.invoicestate_activity;

import com.geli.m.bean.InvoiceStateBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;

/**
 * shen
 */
public class InvoiceStatePresentImpl extends BasePresenter<InvoiceStateView, InvoiceStateModelImpl> {


    @Override
    protected InvoiceStateModelImpl createModel() {
        return new InvoiceStateModelImpl();
    }

    public InvoiceStatePresentImpl(InvoiceStateView mvpView) {
        super(mvpView);
    }


    public void invoiceDetail(String user_id, String invoice_id) {
        mModel.invoiceDetail(user_id, invoice_id, new BaseObserver<InvoiceStateBean>(this, mvpView, true) {
            @Override
            protected void onSuccess(InvoiceStateBean data) {
                if(data.getCode() == Constant.REQUEST_OK){
                    // mvpView.onSuccess(data.getMessage());
                    mvpView.getInvoiceDetail(data.getData());
                }else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }


}
