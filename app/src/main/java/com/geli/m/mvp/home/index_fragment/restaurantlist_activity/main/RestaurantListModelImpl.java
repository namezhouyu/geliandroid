package com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main;


import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import java.util.Map;

/**
 * shen
 */
public class RestaurantListModelImpl extends BaseModel {
    public void localFoodList(Map<String, String> data, BaseObserver observer) {
        universal(mApiService.localFoodList(data), observer);
    }

    public void marketZone(BaseObserver observer){
        universal(mApiService.marketZone(), observer);
    }

    public void marketType(BaseObserver observer){
        universal(mApiService.marketType(), observer);
    }
}
