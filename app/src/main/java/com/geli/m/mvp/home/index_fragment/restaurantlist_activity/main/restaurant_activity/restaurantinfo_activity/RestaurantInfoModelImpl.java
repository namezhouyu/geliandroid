package com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.restaurant_activity.restaurantinfo_activity;


import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;

/**
 * shen
 */
public class RestaurantInfoModelImpl extends BaseModel {
    public void marketDetail(String lf_id, BaseObserver observer) {
        universal(mApiService.marketDetail(lf_id), observer);
    }
}
