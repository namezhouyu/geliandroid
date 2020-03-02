package com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.restaurant_activity.r_search_activity;


import com.geli.m.bean.KeyWordsBean;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.restaurant_activity.RestaurantModelImpl;

/**
 * shen
 */
public class RSearchModelImpl extends RestaurantModelImpl {
    public void hotKeywords(String search_type, BaseObserver<KeyWordsBean> observer) {
        universal(mApiService.hotKeywords(search_type), observer);
    }
}
