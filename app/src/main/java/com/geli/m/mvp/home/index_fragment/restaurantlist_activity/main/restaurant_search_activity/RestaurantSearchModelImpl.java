package com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.restaurant_search_activity;


import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.RestaurantListModelImpl;

/**
 * shen
 */
public class RestaurantSearchModelImpl extends RestaurantListModelImpl {
    public void getKeywords(BaseObserver observer) {
        universal(mApiService.getKeywords(), observer);
    }
}
