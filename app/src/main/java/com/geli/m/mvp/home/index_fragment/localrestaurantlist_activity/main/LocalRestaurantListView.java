package com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main;


import com.geli.m.bean.RestaurantBean;
import com.geli.m.mvp.base.BaseView;

/**
 * shen
 */
public interface LocalRestaurantListView extends BaseView {
    void setRestaurantShop(RestaurantBean.DataBean data);
}
