package com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main;

import com.geli.m.bean.RestaurantBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import java.util.Map;

/**
 * shen
 */
public class LocalRestaurantListPresentImpl extends BasePresenter<LocalRestaurantListView, LocalRestaurantListModelImpl> {


    @Override
    protected LocalRestaurantListModelImpl createModel() {
        return new LocalRestaurantListModelImpl();
    }

    public LocalRestaurantListPresentImpl(LocalRestaurantListView mvpView) {
        super(mvpView);
    }


    public void localFoodList(Map<String, String> map) {

        mModel.localFoodList(map, new BaseObserver<RestaurantBean>(this, mvpView, true) {
            @Override
            protected void onSuccess(RestaurantBean data) {
                if(data.getCode() == Constant.REQUEST_OK || data.getCode() == Constant.REQUEST_NODATA){
                    mvpView.setRestaurantShop(data.getData());
                }else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

}
