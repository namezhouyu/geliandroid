package com.geli.m.mvp.home.other.accountperiod_activity;

import com.geli.m.bean.ShopAPDetailBean;
import com.geli.m.mvp.base.BaseView;

/**
 * author:  shen
 * date:    2018/11/3
 */
public interface AccountPeriodView extends BaseView {
    void showShopShDetail(ShopAPDetailBean.DataBean data);

    void shApplySuccess();
}
