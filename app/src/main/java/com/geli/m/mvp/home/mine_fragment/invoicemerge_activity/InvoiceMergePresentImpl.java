package com.geli.m.mvp.home.mine_fragment.invoicemerge_activity;

import com.geli.m.bean.ShopInvoiceBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;

/**
 * shen
 */
public class InvoiceMergePresentImpl extends BasePresenter<InvoiceMergeView, InvoiceMergeModelImpl> {


    @Override
    protected InvoiceMergeModelImpl createModel() {
        return new InvoiceMergeModelImpl();
    }

    public InvoiceMergePresentImpl(InvoiceMergeView mvpView) {
        super(mvpView);
    }


    public void shopInvoice(String user_id) {
       mModel.shopInvoice(user_id, new BaseObserver<ShopInvoiceBean>(this, mvpView, true) {
            @Override
            protected void onSuccess(ShopInvoiceBean data) {
                if(data.getCode() == Constant.REQUEST_OK || data.getCode() == Constant.REQUEST_NODATA){
                    mvpView.setShopInvoice(data);
                }else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }


}
