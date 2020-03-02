package com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity;

import com.geli.m.bean.RestaurantGoodsBean;
import com.geli.m.bean.RestaurantGoodsShopScreen;
import com.geli.m.bean.RestaurantShopBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import java.util.Map;

/**
 * shen
 */
public class LocalRestaurantPresentImpl extends BasePresenter<LocalRestaurantView, LocalRestaurantModelImpl> {


    @Override
    protected LocalRestaurantModelImpl createModel() {
        return new LocalRestaurantModelImpl();
    }

    public LocalRestaurantPresentImpl(LocalRestaurantView mvpView) {
        super(mvpView);
    }



    /**
     * 新批发商店列表
     * @param data
     * @return
     */
    public void localFoodShops(Map<String, String> data) {
       mModel.localFoodShops(data, new BaseObserver<RestaurantShopBean>(this, mvpView, true) {
            @Override
            protected void onSuccess(RestaurantShopBean data) {
                if(data.getCode() == Constant.REQUEST_OK){
                    mvpView.setRestaurantShop(data.getData());
                }else if(data.getCode() == Constant.REQUEST_NODATA){
                    mvpView.onError(data.getMessage());
                }else {
                    mvpView.onError(data.getMessage());
                }

            }
        });
    }


    /**
     * 新批发商品列表
     * @param data
     * @return
     */
    public void localFoodGoods(Map<String, String> data) {
        mModel.localFoodGoods(data, new BaseObserver<RestaurantGoodsBean>(this, mvpView, true) {
            @Override
            protected void onSuccess(RestaurantGoodsBean data) {
                if(data.getCode() == Constant.REQUEST_OK){
                    mvpView.setRestaurantGoods(data.getData());
                }else if(data.getCode() == Constant.REQUEST_NODATA){
                    mvpView.onError(data.getMessage());
                }else {
                    mvpView.onError(data.getMessage());
                }

            }
        });
    }


    public void getGoodsScreen(String lf_id) {
        mModel.getGoodsScreen(lf_id, new BaseObserver<RestaurantGoodsShopScreen>(this, mvpView, true) {
            @Override
            protected void onSuccess(RestaurantGoodsShopScreen data) {
                if(data.getCode() == Constant.REQUEST_OK){
                    mvpView.setGoodsShopScreen(data.getData());
                }else if(data.getCode() == Constant.REQUEST_NODATA){
                    mvpView.onError(data.getMessage());
                }else {
                    mvpView.onError(data.getMessage());
                }

            }
        });
    }

    public void getShopScreen(String lf_id) {
        mModel.getShopScreen(lf_id, new BaseObserver<RestaurantGoodsShopScreen>(this, mvpView, true) {
            @Override
            protected void onSuccess(RestaurantGoodsShopScreen data) {
                if(data.getCode() == Constant.REQUEST_OK){
                    mvpView.setGoodsShopScreen(data.getData());
                }else if(data.getCode() == Constant.REQUEST_NODATA){
                    mvpView.onError(data.getMessage());
                }else {
                    mvpView.onError(data.getMessage());
                }

            }
        });
    }
}
