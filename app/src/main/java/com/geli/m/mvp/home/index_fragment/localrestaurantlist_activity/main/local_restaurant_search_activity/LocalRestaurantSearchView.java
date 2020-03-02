package com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_search_activity;


import com.geli.m.bean.RestaurantBean;
import com.geli.m.bean.RestaurantHotSearchBean;
import com.geli.m.mvp.base.BaseView;
import java.util.List;

/**
 * shen
 */
public interface LocalRestaurantSearchView extends BaseView {
    void setHotSearch(List<RestaurantHotSearchBean.DataBean> data);

    void setRestaurantShop(RestaurantBean.DataBean data);
}
