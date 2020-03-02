package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.main;

import com.geli.m.app.GlobalData;
import com.geli.m.bean.BaseBean;
import com.geli.m.bean.OrderListBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import java.util.HashMap;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/1/27.
 */

public class OrderListPresentImpl extends BasePresenter<OrderListView, OrderListModelImpl> {
    public OrderListPresentImpl(OrderListView mvpView) {
        super(mvpView);
    }

    public void getOrderList(Map data, boolean isDelay) {
        mModel.getOrderList(isDelay, data, new BaseObserver<OrderListBean>(this, mvpView) {
            @Override
            protected void onSuccess(OrderListBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.showOrderList(data.getData());
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });

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
//
//    public void balancePay(Map data) {
//        mModel.balancePay(data, new BaseObserver<ResponseBody>(this, mvpView) {
//            @Override
//            protected void onSuccess(ResponseBody data) {
//                try {
//                    BaseBean baseBean = parseData(data.string());
//                    if (baseBean.code == Constant.REQUEST_OK) {
//                        mvpView.walletPaySuccess();
//                    } else {
//                        mvpView.onError(baseBean.message);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    mvpView.onError(parseError(e));
//                }
//            }
//        });
//    }

    public void universal(String url, String order_id) {
        Map data = new HashMap();
        data.put("user_id", GlobalData.getUser_id());
        data.put("order_id", order_id);
        mModel.universal(url, data, new BaseObserver<ResponseBody>(this, mvpView) {
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

    @Override
    protected OrderListModelImpl createModel() {
        return new OrderListModelImpl();
    }
}
