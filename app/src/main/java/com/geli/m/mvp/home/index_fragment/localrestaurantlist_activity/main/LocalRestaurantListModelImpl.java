package com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main;


import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import java.util.Map;

/**
 * shen
 */
public class LocalRestaurantListModelImpl extends BaseModel {

    public void localFoodList(Map<String, String> data, BaseObserver observer) {
        universal(mApiService.localFoodList(data), observer);
    }
}
