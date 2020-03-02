package com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.restaurant_search_activity;

import com.geli.m.bean.RestaurantBean;
import com.geli.m.bean.RestaurantHotSearchBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import java.util.Map;

/**
 * shen
 */
public class RestaurantSearchPresentImpl extends BasePresenter<RestaurantSearchView, RestaurantSearchModelImpl> {


    @Override
    protected RestaurantSearchModelImpl createModel() {
        return new RestaurantSearchModelImpl();
    }

    public RestaurantSearchPresentImpl(RestaurantSearchView mvpView) {
        super(mvpView);
    }


    public void getKeywords() {
       mModel.getKeywords(new BaseObserver<RestaurantHotSearchBean>(this, mvpView, true) {
            @Override
            protected void onSuccess(RestaurantHotSearchBean data) {
                if(data.getCode() == Constant.REQUEST_OK){
                    mvpView.setHotSearch(data.getData());
                }else {
                    mvpView.onError(data.getMessage());
                }

            }
        });
    }

    public void localFoodList(Map<String, String> map) {

        mModel.localFoodList(map, new BaseObserver<RestaurantBean>(this, mvpView, true) {
            @Override
            protected void onSuccess(RestaurantBean data) {
                if(isGetDataSuccess(data.getCode())){
                    mvpView.setRestaurantShop(data.getData());
                }else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

}
