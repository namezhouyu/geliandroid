package com.geli.m.mvp.home.mine_fragment.coupon_activity.coupon_fragment;

import com.geli.m.R;
import com.geli.m.bean.BaseBean;
import com.geli.m.bean.CouponBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.utils.Utils;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import okhttp3.ResponseBody;
import org.json.JSONObject;

/**
 * Created by Steam_l on 2018/1/20.
 */

public class CouponPresentImpl extends BasePresenter<CouponView, CouponModelImpl> {
    /** 领取优惠券 */
    public boolean isCollect = false;

    public CouponPresentImpl(CouponView mvpView) {
        super(mvpView);
    }

    public void collectCoupons(String user_id, String coupon_id) {
        isCollect = true;
        mModel.collectCoupons(user_id, coupon_id, new BaseObserver<ResponseBody>(this, mvpView, true) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    BaseBean baseBean = parseData(data.string());
                    if (baseBean.code == Constant.REQUEST_OK) {
                        mvpView.onSuccess(Utils.getString(R.string.message_clooectsuccess));
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

    public void getCouponList(String url, Map data) {
        mModel.getCouponInfo(url, data, new BaseObserver<ResponseBody>(this, mvpView) {

            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    String string = data.string();
                    BaseBean baseBean = parseData(string);
                    if (baseBean.code == Constant.REQUEST_OK) {
                        String list = new JSONObject(string).getString("data");
                        List<CouponBean> dataList = mGson.fromJson(list, new TypeToken<ArrayList<CouponBean>>() {
                        }.getType());
                        mvpView.showCouponList(dataList);
                    } else if (baseBean.code == 200) {
                        mvpView.showCouponList(null);
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
    protected CouponModelImpl createModel() {
        return new CouponModelImpl();
    }
}
