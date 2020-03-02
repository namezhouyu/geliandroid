package com.geli.m.mvp.model;

import com.geli.m.bean.SpecifiBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/1/16.
 */

public class GoodsSpecifiModelImpl extends BaseModel {
    public void getGoodsSpecifi(String goods_id,Function<ResponseBody,SpecifiBean>function, BaseObserver observer) {
        universal(mApiService.getGoodsSpecifi(goods_id)
                .map(function), observer);
    }
}
