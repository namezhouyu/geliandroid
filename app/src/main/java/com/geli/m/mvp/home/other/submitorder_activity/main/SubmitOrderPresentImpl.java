package com.geli.m.mvp.home.other.submitorder_activity.main;

import com.geli.m.bean.BaseBean;
import com.geli.m.bean.ShopLogisticBean;
import com.geli.m.bean.SubmitOrderBean;
import com.geli.m.bean.SubmitOrderNewBean;
import com.geli.m.bean.TermsBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/1/22.
 */

public class SubmitOrderPresentImpl extends BasePresenter<SubmitOrderView, SubmitOrderModelImpl> {

    @Override
    protected SubmitOrderModelImpl createModel() {
        return new SubmitOrderModelImpl();
    }

    public SubmitOrderPresentImpl(SubmitOrderView mvpView) {
        super(mvpView);
    }

    /**
     * 获取订单信息
     * @param data
     */
    public void getOrderInfo(Map data) {
        mModel.getOrderInfo(data, new BaseObserver<SubmitOrderBean>(this, mvpView) {
            @Override
            protected void onSuccess(SubmitOrderBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.showOrderInfo(data.getData());
                } else {
                    mvpView.onError(data.getMessage());
                    mvpView.finsh();
                }
            }
        });
    }

    /**
     * 获取订单信息   --
     * @param user_id
     * @param cart_id
     */
    public void getOrderInfo(String user_id, String cart_id, String is_sh) {
        mModel.settlement(user_id, cart_id, is_sh, new BaseObserver<SubmitOrderBean>(this, mvpView) {
            @Override
            protected void onSuccess(SubmitOrderBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.showOrderInfo(data.getData());
                } else {
                    mvpView.onError(data.getMessage());
                    mvpView.finsh();
                }
            }
        });
    }

    /**
     * 获取"海外订单"信息
     * @param data
     */
    public void getOrderInfoFormOverseas(Map data) {
        mModel.getOrderInfoFormOverseas(data, new BaseObserver<SubmitOrderBean>(this, mvpView) {
            @Override
            protected void onSuccess(SubmitOrderBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.showOrderInfo(data.getData());
                } else {
                    mvpView.onError(data.getMessage());
                    mvpView.finsh();
                }
            }
        });
    }

    /**
     * 获取 -- 海外条款
     * @param goods_type
     */
    public void getTerms(String goods_type) {
        mModel.getTerms(goods_type, new BaseObserver<ResponseBody>(this, mvpView, false) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    String string = data.string();
                    BaseBean baseBean = parseData(string);
                    if (baseBean.code == Constant.REQUEST_OK) {
                        TermsBean termsBean = mGson.fromJson(string, TermsBean.class);
                        mvpView.showTerms(termsBean.getData().getContent());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

//
//    /**
//     * 提交订单
//     * @param user_id
//     * @param json
//     * @param curr_type
//     * @param isGroup
//     */
//    public void submitOrder(String user_id, String json, int curr_type, boolean isGroup) {
//        BaseObserver<SubmitOrderNewBean> observer = new BaseObserver<SubmitOrderNewBean>(this, mvpView) {
//            @Override
//            protected void onSuccess(SubmitOrderNewBean data) {
//                if(data.getCode() == Constant.REQUEST_OK){
//                    mvpView.submitOrderSuccess(data);
//                }else {
//                    mvpView.onError(data.getMessage());
//                }
//            }
//        };
//
//
//        if (isGroup) {
//            mModel.submitOverseasGroupOrder(user_id, json, observer);
//        } else if (curr_type == SubmitOrderActivity.TYPE_OVERSEAS) {
//            mModel.submitOverseasOrder(user_id, json, observer);
//        } else {
//            mModel.submitOrderNew(user_id, json, observer);
//        }
//    }


    /**
     * 提交订单 -- 普通商品
     * @param map
     */
    public void submitOrderNew(Map<String, String> map) {
        mModel.submitOrderNew(map, new BaseObserver<SubmitOrderNewBean>(this, mvpView) {
            @Override
            protected void onSuccess(SubmitOrderNewBean data) {
                if(data.getCode() == Constant.REQUEST_OK){
                    mvpView.submitOrderSuccess(data);
                }else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

    /**
     * 提交订单 -- 团购，海外
     * @param map
     */
    public void submitOrderOvOrGroup(Map<String, String> map, boolean isGroup) {

        BaseObserver observer = new BaseObserver<SubmitOrderNewBean>(this, mvpView) {
            @Override
            protected void onSuccess(SubmitOrderNewBean data) {
                if(data.getCode() == Constant.REQUEST_OK){
                    mvpView.submitOrderSuccess(data);
                }else {
                    mvpView.onError(data.getMessage());
                }
            }
        };

        if(isGroup) {
            mModel.submitGrouponOrder(map, observer);
        }else {
            mModel.submitFuturesOrder(map, observer);
        }
    }

    /**
     * 获取批发商店 -- 所有的物流商信息
     * @param shop_id
     */
    public void getLogisticInfo(String shop_id) {
        mModel.getLogisticInfo(shop_id, new BaseObserver<ShopLogisticBean>(this, mvpView) {
            @Override
            protected void onSuccess(ShopLogisticBean data) {
                if(data.getCode() == Constant.REQUEST_OK){
                    mvpView.getLogisticInfoSuccess(data);
                }else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

}
