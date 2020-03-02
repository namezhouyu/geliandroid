package com.geli.m.mvp.home.mine_fragment.coupon_activity.coupon_fragment;

import com.geli.m.bean.CouponBean;
import com.geli.m.mvp.base.BaseView;
import java.util.List;

/**
 * Created by Administrator on 2018/1/23.
 */

public interface CouponView extends BaseView {
    void showCouponList(List<CouponBean> couponBeanList);
}
