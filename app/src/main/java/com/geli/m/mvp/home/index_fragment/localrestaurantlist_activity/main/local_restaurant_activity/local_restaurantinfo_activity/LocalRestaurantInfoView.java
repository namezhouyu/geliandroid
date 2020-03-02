package com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity.local_restaurantinfo_activity;


import com.geli.m.bean.RestaurantInfoBean;
import com.geli.m.mvp.base.BaseView;

/**
 * shen
 */
public interface LocalRestaurantInfoView extends BaseView {
    void marketDetailSuccess(RestaurantInfoBean data);
}
