package com.geli.m.mvp.home.other.shopdetails_activity.shopdetails_fragment;

import com.geli.m.bean.GoodsFromCatBean;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.home.cart_fragment.main.CartModelImpl;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/1/11.
 */

public class ShopInfoModelImpl extends CartModelImpl {
    public void getShopInfo(Map data, BaseObserver observer) {
        universal(mApiService.getShopInfo(data), observer);
    }

    public void getGoodsFormCat(Map data, BaseObserver<GoodsFromCatBean> observer) {
        universal(mApiService.getGoodsFormCart(data), observer);
    }

    public void collection(Map<String, String> data, BaseObserver<ResponseBody> observer) {
        universal(mApiService.collection(data), observer);
    }


}
