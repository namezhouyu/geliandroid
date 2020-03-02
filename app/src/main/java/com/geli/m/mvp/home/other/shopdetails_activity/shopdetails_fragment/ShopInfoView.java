package com.geli.m.mvp.home.other.shopdetails_activity.shopdetails_fragment;

import com.geli.m.bean.ShopInfoBean;
import com.geli.m.mvp.home.cart_fragment.main.CartView;
import java.util.List;

/**
 * Created by Steam_l on 2018/1/11.
 */

public interface ShopInfoView extends CartView {
    void showInfo(ShopInfoBean bean);

    void showGoodsFromCat(List<ShopInfoBean.DataEntity.GoodsResEntity> dataBean);

    void showCollectionResult(String mess);

    void showGoodsFromCatTemp(List<ShopInfoBean.DataEntity.GoodsResEntity> dataData);
}
