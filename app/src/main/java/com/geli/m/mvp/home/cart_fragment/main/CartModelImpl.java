package com.geli.m.mvp.home.cart_fragment.main;

import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.model.GoodsSpecifiModelImpl;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/15.
 */

public class CartModelImpl extends GoodsSpecifiModelImpl {
    public void getCartList(Map data, BaseObserver observer) {
        universal(mApiService.getCartList(data), observer);
    }

    public void editCart(Map data, BaseObserver observer) {
        universal(mApiService.addOrEditCart(data), observer);
    }

    public void delCart(String user_id, String cart_id, BaseObserver observer) {
        universal(mApiService.deleteCart(user_id, cart_id), observer);
    }

}
