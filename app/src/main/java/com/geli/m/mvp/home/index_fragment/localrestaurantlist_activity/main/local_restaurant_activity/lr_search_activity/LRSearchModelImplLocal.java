package com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity.lr_search_activity;


import com.geli.m.bean.KeyWordsBean;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity.LocalRestaurantModelImpl;

/**
 * shen
 */
public class LRSearchModelImplLocal extends LocalRestaurantModelImpl {
    public void hotKeywords(String search_type, BaseObserver<KeyWordsBean> observer) {
        universal(mApiService.hotKeywords(search_type), observer);
    }
}
