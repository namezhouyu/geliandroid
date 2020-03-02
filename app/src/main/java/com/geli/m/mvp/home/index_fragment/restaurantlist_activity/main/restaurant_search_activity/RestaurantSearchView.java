package com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.restaurant_search_activity;


import com.geli.m.bean.RestaurantBean;
import com.geli.m.bean.RestaurantHotSearchBean;
import com.geli.m.mvp.base.BaseView;
import java.util.List;

/**
 * shen
 */
public interface RestaurantSearchView extends BaseView {
    void setHotSearch(List<RestaurantHotSearchBean.DataBean> data);

    void setRestaurantShop(RestaurantBean.DataBean data);
}
