package com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main;


import com.geli.m.bean.RestaurantAddrArrangeBean;
import com.geli.m.bean.RestaurantBean;
import com.geli.m.bean.RestaurantSortBean;
import com.geli.m.mvp.base.BaseView;
import java.util.List;

/**
 * shen
 */
public interface RestaurantListView extends BaseView {
    void setRestaurantShop(RestaurantBean.DataBean data);

    void setAddrPopup(RestaurantAddrArrangeBean data);

    void setSortBean(List<RestaurantSortBean.DataBean> data);
}
