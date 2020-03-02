package com.geli.m.mvp.home.other.pay_activity;

import com.geli.m.bean.BalanceBean;
import com.geli.m.bean.BaseBean;
import com.geli.m.bean.FwqTimeBean;
import com.geli.m.bean.TransferBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import java.io.IOException;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * shen
 */
public class PayPresentImpl extends BasePresenter<PayView, PayModelImpl> {


    @Override
    protected PayModelImpl createModel() {
        return new PayModelImpl();
    }

    public PayPresentImpl(PayView mvpView) {
        super(mvpView);
    }


//    public void orderPay(Map data, final String pay_type) {
//        mModel.orderPay(data, new BaseObserver<ResponseBody>(this, mvpView) {
//            @Override
//            protected void onSuccess(ResponseBody data) {
//                try {
//                    String string = data.string();
//                    BaseBean baseBean = parseData(string);
//                    if (baseBean.code == Constant.REQUEST_OK) {
//                        mvpView.payResult(string, pay_type);
//                    } else {
//                        ToastUtils.showToast(baseBean.message);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    ToastUtils.showToast(parseError(e));
//                }
//            }
//        });
//    }

    public void isGeliPay(String user_id, String order_sn) {
        mModel.isGeliPay(user_id, order_sn, new BaseObserver<BalanceBean>(this, mvpView) {
            @Override
            protected void onSuccess(BalanceBean data) {
                mvpView.isGeliPay(data);
            }
        });
    }

    public void orderPayNew(String user_id, String order_sn, final String pay_type, String pay_pwd) {
        mModel.orderPayNew(user_id, order_sn, pay_type, pay_pwd, new BaseObserver<ResponseBody>(this, mvpView) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    mvpView.payResult(data.string().toString(), pay_type);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void logisticsPay(String user_id, String order_sn, final String pay_type, String pay_pwd) {
        mModel.logisticsPay(user_id, order_sn, pay_type, pay_pwd, new BaseObserver<ResponseBody>(this, mvpView) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    mvpView.payResult(data.string().toString(), pay_type);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void balancePay(Map data) {
        mModel.balancePay(data, new BaseObserver<ResponseBody>(this, mvpView) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    BaseBean baseBean = parseData(data.string());
                    if (baseBean.code == Constant.REQUEST_OK) {
                        mvpView.walletPaySuccess();
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


    public void getFwqTime(final int code) {
        mModel.getFwqTime1(new BaseObserver<FwqTimeBean>(this, mvpView) {
            @Override
            protected void onSuccess(FwqTimeBean data) {
                if(data.getCode() == 0) {
                    mvpView.getFwqTimeSuccess(code, data);
                }else {
                    onError(data.getCode() + ":" + data.getRep_msg());
                }
            }


        });
    }



    public void transfer(String user_id, String order_sn) {
        mModel.transfer(user_id, order_sn, new BaseObserver<TransferBean>(this, mvpView, true) {
            @Override
            protected void onSuccess(TransferBean data) {
                if(data.getCode() == Constant.REQUEST_OK){
                    mvpView.getTransferData(data);
                }else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

}
