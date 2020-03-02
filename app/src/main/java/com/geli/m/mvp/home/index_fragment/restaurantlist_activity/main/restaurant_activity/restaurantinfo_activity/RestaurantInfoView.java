package com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.restaurant_activity.restaurantinfo_activity;


import com.geli.m.bean.RestaurantInfoBean;
import com.geli.m.mvp.base.BaseView;

/**
 * shen
 */
public interface RestaurantInfoView extends BaseView {
    void marketDetailSuccess(RestaurantInfoBean data);
}
