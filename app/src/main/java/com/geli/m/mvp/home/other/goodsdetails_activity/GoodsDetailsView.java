package com.geli.m.mvp.home.other.goodsdetails_activity;

import com.geli.m.bean.GoodsDetailsBean;
import com.geli.m.bean.ShPriceBean;
import com.geli.m.mvp.view.GoodsSpecifiView;

/**
 * Created by Steam_l on 2018/1/17.
 */

public interface GoodsDetailsView extends GoodsSpecifiView {
    void showGoodsData(GoodsDetailsBean.DataBean data);

    void showCollectionResult(String message);

    void showShPrice(ShPriceBean data);
}
