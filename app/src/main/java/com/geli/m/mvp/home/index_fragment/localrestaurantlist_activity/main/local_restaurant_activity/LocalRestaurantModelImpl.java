package com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity;


import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import java.util.Map;

/**
 * shen
 */
public class LocalRestaurantModelImpl extends BaseModel {

    public void localFoodShops(Map<String, String> data, BaseObserver observer) {
        universal(mApiService.localFoodShops(data), observer);
    }

    public void localFoodGoods(Map<String, String> data, BaseObserver observer) {
        universal(mApiService.localFoodGoods(data), observer);
    }

    public void getGoodsScreen(String lf_id, BaseObserver observer) {
        universal(mApiService.getGoodsScreen(lf_id), observer);
    }

    public void getShopScreen(String lf_id, BaseObserver observer) {
        universal(mApiService.getShopScreen(lf_id), observer);
    }

}
