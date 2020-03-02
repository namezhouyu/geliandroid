package com.geli.m.mvp.home.cart_fragment.main;

import com.geli.m.bean.CartBean;
import com.geli.m.mvp.view.GoodsSpecifiView;
import java.util.List;

/**
 * Created by Administrator on 2018/1/15.
 */

public interface CartView extends GoodsSpecifiView {
    void showCartList(List<CartBean.DataEntity> dataEntities);
}
