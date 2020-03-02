package com.geli.m.mvp.home.mine_fragment.invoicemerge_activity.invoicelist_activity;

import com.geli.m.bean.InvoiceOrderBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;

/**
 * shen
 */
public class InvoiceListPresentImpl extends BasePresenter<InvoiceListView, InvoiceListModelImpl> {


    @Override
    protected InvoiceListModelImpl createModel() {
        return new InvoiceListModelImpl();
    }

    public InvoiceListPresentImpl(InvoiceListView mvpView) {
        super(mvpView);
    }


    public void invoiceOrder(String user_id, String order_id) {
        mModel.invoiceOrder(user_id, order_id, new BaseObserver<InvoiceOrderBean>(this, mvpView, true) {
            @Override
            protected void onSuccess(InvoiceOrderBean data) {
                if(data.getCode() == Constant.REQUEST_OK ){
                        // || data.getCode() == Constant.REQUEST_NODATA){
                    mvpView.setInvoiceOrder(data);
                }else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }



}
