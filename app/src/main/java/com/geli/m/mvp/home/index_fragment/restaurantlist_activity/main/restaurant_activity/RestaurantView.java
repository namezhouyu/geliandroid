package com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.restaurant_activity;


import com.geli.m.bean.RestaurantGoodsBean;
import com.geli.m.bean.RestaurantGoodsShopScreen;
import com.geli.m.bean.RestaurantShopBean;
import com.geli.m.mvp.base.BaseView;
import java.util.List;

/**
 * shen
 */
public interface RestaurantView extends BaseView {
    void setRestaurantShop(List<RestaurantShopBean.DataBean> data);

    void setRestaurantGoods(List<RestaurantGoodsBean.DataBean> data);

    void setGoodsShopScreen(List<RestaurantGoodsShopScreen.DataBeanX> data);
}
