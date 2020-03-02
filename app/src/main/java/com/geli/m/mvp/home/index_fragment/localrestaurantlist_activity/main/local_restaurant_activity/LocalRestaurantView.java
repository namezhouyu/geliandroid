package com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity;


import com.geli.m.bean.RestaurantGoodsBean;
import com.geli.m.bean.RestaurantGoodsShopScreen;
import com.geli.m.bean.RestaurantShopBean;
import com.geli.m.mvp.base.BaseView;
import java.util.List;

/**
 * shen
 */
public interface LocalRestaurantView extends BaseView {
    void setRestaurantShop(List<RestaurantShopBean.DataBean> data);

    void setRestaurantGoods(List<RestaurantGoodsBean.DataBean> data);

    void setGoodsShopScreen(List<RestaurantGoodsShopScreen.DataBeanX> data);
}
