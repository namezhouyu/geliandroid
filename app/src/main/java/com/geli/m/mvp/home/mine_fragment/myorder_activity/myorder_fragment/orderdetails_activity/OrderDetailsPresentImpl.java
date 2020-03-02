package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.orderdetails_activity;

import com.geli.m.app.GlobalData;
import com.geli.m.bean.BaseBean;
import com.geli.m.bean.OrderDetailsBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import java.util.HashMap;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/1/29.
 */

public class OrderDetailsPresentImpl extends BasePresenter<OrderDetailsView, OrderDetailsModelImpl> {

    public OrderDetailsPresentImpl(OrderDetailsView mvpView) {
        super(mvpView);
    }

    public void getOrderDetails(String user_id, String order_id) {
        mModel.getOrderDetails(user_id, order_id, new BaseObserver<OrderDetailsBean>(this, mvpView) {
            @Override
            protected void onSuccess(OrderDetailsBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.showOrderDetails(data.getData());
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

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
    protected OrderDetailsModelImpl createModel() {
        return new OrderDetailsModelImpl();
    }
}
