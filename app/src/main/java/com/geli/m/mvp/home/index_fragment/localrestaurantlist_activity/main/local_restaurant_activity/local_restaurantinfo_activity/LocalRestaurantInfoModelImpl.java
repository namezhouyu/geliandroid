package com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity.local_restaurantinfo_activity;


import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;

/**
 * shen
 */
public class LocalRestaurantInfoModelImpl extends BaseModel {
    public void marketDetail(String lf_id, BaseObserver observer) {
        universal(mApiService.marketDetail(lf_id), observer);
    }
}
