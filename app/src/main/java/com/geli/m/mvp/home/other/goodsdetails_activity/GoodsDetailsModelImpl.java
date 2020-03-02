package com.geli.m.mvp.home.other.goodsdetails_activity;

import com.geli.m.bean.ShPriceBean;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.model.GoodsSpecifiModelImpl;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/1/17.
 */

public class GoodsDetailsModelImpl extends GoodsSpecifiModelImpl {
    public void getGoodsDetails(String user_id, String goods_id, BaseObserver observer) {
        universal(mApiService.getGoodsDetails(user_id, goods_id), observer);
    }

    public void collectionGood(Map data, BaseObserver observer) {
        universal(mApiService.collection(data), observer);
    }

    public void addCart(Map data, BaseObserver observer) {
        universal(mApiService.addOrEditCart(data), observer);
    }

    public void toLike(String user_id, String com_id, BaseObserver<ResponseBody> observer) {
        universal(mApiService.commentLike(user_id, com_id), observer);
    }


    public void shPrice(String user_id, String goods_id, String shop_id,BaseObserver<ShPriceBean> observer) {
        universal(mApiService.shPrice(user_id, goods_id, shop_id), observer);
    }

}
