package com.geli.m.mvp.home.index_fragment.overseas_activity;

import com.geli.m.bean.OverseasBean;
import com.geli.m.bean.OverseasGoodsOuterBean;
import com.geli.m.bean.OverseasSortBean;
import com.geli.m.mvp.home.cart_fragment.main.CartView;
import java.util.List;

/**
 * Created by Administrator on 2018/1/23.
 */

public interface OverseasView extends CartView {
    void showData(OverseasBean bean);

    void showSortData(OverseasBean bean);

    void showGoodsData(List<OverseasGoodsOuterBean.OverseasGoodsBean> goodsBeanList);

    void showShopAptitude(List<String> url);

    void setSortData(OverseasSortBean bean);
}
