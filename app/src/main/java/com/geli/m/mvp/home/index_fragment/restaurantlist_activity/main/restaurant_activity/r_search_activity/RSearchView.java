package com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.restaurant_activity.r_search_activity;


import com.geli.m.bean.KeyWordsBean;
import com.geli.m.bean.RestaurantGoodsBean;
import com.geli.m.bean.RestaurantShopBean;
import com.geli.m.mvp.base.BaseView;
import java.util.List;

/**
 * shen
 */
public interface RSearchView extends BaseView {
    void showHotKeywords(List<KeyWordsBean.DataEntity> data);

    void setRestaurantShop(List<RestaurantShopBean.DataBean> data);

    void setRestaurantGoods(List<RestaurantGoodsBean.DataBean> data);
}
