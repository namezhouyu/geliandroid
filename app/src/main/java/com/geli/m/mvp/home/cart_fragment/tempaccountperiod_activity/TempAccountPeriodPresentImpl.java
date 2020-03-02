package com.geli.m.mvp.home.cart_fragment.tempaccountperiod_activity;

import com.geli.m.bean.BaseBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import okhttp3.ResponseBody;


/**
 * shen
 */
public class TempAccountPeriodPresentImpl
        extends BasePresenter<TempAccountPeriodView, TempAccountPeriodModelImpl> {


    @Override
    protected TempAccountPeriodModelImpl createModel() {
        return new TempAccountPeriodModelImpl();
    }

    public TempAccountPeriodPresentImpl(TempAccountPeriodView mvpView) {
        super(mvpView);
    }


    public void tempShApply(String user_id, String amount, String shop_id) {
       mModel.tempShApply(user_id, amount, shop_id, new BaseObserver<ResponseBody>(this, mvpView) {
           @Override
           protected void onSuccess(ResponseBody data) {
               try {
                   BaseBean baseBean = parseData(data.string());
                   if (baseBean.code == Constant.REQUEST_OK) {
                       mvpView.onSuccess(baseBean.message);
                   } else {
                       mvpView.onError(baseBean.message);
                   }
               } catch (Exception e) {
                   e.printStackTrace();
                   mvpView.onError(parseError(e));
               }
           }

       });
    }


}
