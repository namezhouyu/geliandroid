package com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity.lr_search_activity;

import com.geli.m.bean.KeyWordsBean;
import com.geli.m.bean.RestaurantGoodsBean;
import com.geli.m.bean.RestaurantShopBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import java.util.Map;

/**
 * shen
 */
public class LRSearchPresentImpl extends BasePresenter<LRSearchView, LRSearchModelImplLocal> {


    @Override
    protected LRSearchModelImplLocal createModel() {
        return new LRSearchModelImplLocal();
    }

    public LRSearchPresentImpl(LRSearchView mvpView) {
        super(mvpView);
    }


    public void hotKeywords(String search_type) {
        mModel.hotKeywords(search_type, new BaseObserver<KeyWordsBean>(this, mvpView, false) {
            @Override
            protected void onSuccess(KeyWordsBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.showHotKeywords(data.getData());
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

//    /**
//     * 新批发商店列表
//     * @param lf_id         食品馆id
//     * @param page
//     * @param sort	        1默认排序2销量
//     * @param keywords	    搜索关键词
//     * @return
//     */
//    public void localFoodShops(String lf_id, String page, String sort, String keywords) {
//        mModel.localFoodShops(lf_id, page, sort, keywords, new BaseObserver<RestaurantShopBean>(this, mvpView, true) {
//            @Override
//            protected void onSuccess(RestaurantShopBean data) {
//                if(data.getCode() == Constant.REQUEST_OK){
//                    mvpView.setRestaurantShop(data.getData());
//                }else if(data.getCode() == Constant.REQUEST_NODATA){
//                    mvpView.onError(data.getMessage());
//                }else {
//                    mvpView.onError(data.getMessage());
//                }
//
//            }
//        });
//    }
//
//
//    /**
//     * 新批发商品列表
//     * @param lf_id         食品馆id
//     * @param page
//     * @param sort	        1默认排序2销量
//     * @param keywords	    搜索关键词
//     * @param brand         品牌id
//     * @return
//     */
//    public void localFoodGoods(String lf_id, String page, String sort, String keywords, String brand) {
//        mModel.localFoodGoods(lf_id, page, sort, keywords, brand, new BaseObserver<RestaurantGoodsBean>(this, mvpView, true) {
//            @Override
//            protected void onSuccess(RestaurantGoodsBean data) {
//                if(data.getCode() == Constant.REQUEST_OK){
//                    mvpView.setRestaurantGoods(data.getData());
//                }else if(data.getCode() == Constant.REQUEST_NODATA){
//                    mvpView.onError(data.getMessage());
//                }else {
//                    mvpView.onError(data.getMessage());
//                }
//
//            }
//        });
//    }


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
}
