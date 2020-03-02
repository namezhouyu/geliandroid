package com.geli.m.mvp.home.index_fragment.search_activity;

import com.geli.m.bean.KeyWordsBean;
import com.geli.m.bean.OverseasGoodsOuterBean;
import com.geli.m.bean.RetailCenterBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import io.reactivex.Observable;
import java.util.Map;

/**
 * Created by Steam_l on 2018/3/16.
 */

public class SearchModelImpl extends BaseModel {
    public void searchGoods(Map<String, String> data, BaseObserver<OverseasGoodsOuterBean> observer) {
        universal(getGoodsObservable(data), observer);
    }

    public void hotKeywords(String search_type, BaseObserver<KeyWordsBean> observer) {
        universal(mApiService.hotKeywords(search_type), observer);
    }

    public Observable getGoodsObservable(Map data) {
        return mApiService.searchGoods(data);
    }

    public void searchShops(Map<String, String> data, BaseObserver<RetailCenterBean> observer) {
        universal(getShopsgetGoodsObservable(data), observer);
    }

    public Observable getShopsgetGoodsObservable(Map data) {
        return mApiService.searchShops(data);
    }
}
