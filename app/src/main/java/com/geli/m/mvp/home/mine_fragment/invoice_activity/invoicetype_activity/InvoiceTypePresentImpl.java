package com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicetype_activity;

import com.geli.m.R;
import com.geli.m.bean.BaseBean;
import com.geli.m.bean.InvoiceBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.utils.Utils;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/1/11.
 */

public class InvoiceTypePresentImpl extends BasePresenter<InvoiceTypeView, InvoiceTypeModelImpl> {
    public InvoiceTypePresentImpl(InvoiceTypeView mvpView) {
        super(mvpView);
    }

    public void getInvoiceList(String user_id) {
        mModel.getInvoiceList(user_id, new BaseObserver<InvoiceBean>(this, mvpView,true) {
            @Override
            protected void onSuccess(InvoiceBean data) {
                if (data.getCode() == 100) {
                    mvpView.showList(data.getData());
                } else if (data.getCode() == 200) {
                    mvpView.showList(null);
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }


    public void deleteInvoice(String user_id, String invoice_id) {
        mModel.deleteInvoice(user_id, invoice_id, new BaseObserver<ResponseBody>(this, mvpView) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    BaseBean baseBean = parseData(data.string());
                    if (baseBean.code == Constant.REQUEST_OK) {
                        mvpView.onSuccess(Utils.getString(R.string.delete_success));
                    } else {
                        mvpView.onError(Utils.getString(R.string.delete_faile));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mvpView.onError(parseError(e));
                }
            }
        });
    }

    @Override
    protected InvoiceTypeModelImpl createModel() {
        return new InvoiceTypeModelImpl();
    }
}
