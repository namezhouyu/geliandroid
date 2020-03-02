package com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.restaurant_activity.restaurantinfo_activity;

import com.geli.m.bean.RestaurantInfoBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;

/**
 * shen
 */
public class RestaurantInfoPresentImpl extends BasePresenter<RestaurantInfoView, RestaurantInfoModelImpl> {


    @Override
    protected RestaurantInfoModelImpl createModel() {
        return new RestaurantInfoModelImpl();
    }

    public RestaurantInfoPresentImpl(RestaurantInfoView mvpView) {
        super(mvpView);
    }

    /**
     * 新批发详情
     * @param   lf_id     新批发id
     * @return
     */
    public void marketDetail(String lf_id) {
       mModel.marketDetail(lf_id, new BaseObserver<RestaurantInfoBean>(this, mvpView, true) {
            @Override
            protected void onSuccess(RestaurantInfoBean data) {
                if(data.getCode() == Constant.REQUEST_OK){
                    // mvpView.onSuccess(data.getMessage());
                    mvpView.marketDetailSuccess(data);
                }else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }
}
