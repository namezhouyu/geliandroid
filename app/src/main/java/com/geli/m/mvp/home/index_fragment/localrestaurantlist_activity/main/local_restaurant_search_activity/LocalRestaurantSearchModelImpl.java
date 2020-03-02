package com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_search_activity;


import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.LocalRestaurantListModelImpl;

/**
 * shen
 */
public class LocalRestaurantSearchModelImpl extends LocalRestaurantListModelImpl {
    public void getKeywords(BaseObserver observer) {
        universal(mApiService.getKeywords(), observer);
    }
}
