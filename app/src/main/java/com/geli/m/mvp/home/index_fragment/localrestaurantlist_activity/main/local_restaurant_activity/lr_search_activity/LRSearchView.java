package com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity.lr_search_activity;


import com.geli.m.bean.KeyWordsBean;
import com.geli.m.bean.RestaurantGoodsBean;
import com.geli.m.bean.RestaurantShopBean;
import com.geli.m.mvp.base.BaseView;
import java.util.List;

/**
 * shen
 */
public interface LRSearchView extends BaseView {
    void showHotKeywords(List<KeyWordsBean.DataEntity> data);

    void setRestaurantShop(List<RestaurantShopBean.DataBean> data);

    void setRestaurantGoods(List<RestaurantGoodsBean.DataBean> data);
}
