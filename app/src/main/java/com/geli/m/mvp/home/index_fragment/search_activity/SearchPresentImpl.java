package com.geli.m.mvp.home.index_fragment.search_activity;

import com.geli.m.bean.KeyWordsBean;
import com.geli.m.bean.OverseasGoodsOuterBean;
import com.geli.m.bean.RetailCenterBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.utils.RxUtils;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function3;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Steam_l on 2018/3/16.
 */

public class SearchPresentImpl extends BasePresenter<SearchView, SearchModelImpl> {
    public static final String KEY_GOODS = "key_goods";
    public static final String KEY_SHOPS = "key_shops";
    public static final String KEY_GROUP_BUY = "key_group_buy";
    public static final String KEY_FUTURES = "key_futures";
    public static final String KEY_SPOT = "key_spot";

    public SearchPresentImpl(SearchView mvpView) {
        super(mvpView);
    }


    public void searchAllForNormal(Map<String, String> data, String keyword) {
        Map<String, String> goods = new HashMap<>();
        Map<String, String> shop = new HashMap<>();
        goods.putAll(data);
        shop.putAll(data);
        goods.put("keywords", keyword);
        goods.put("goods_type", "1");
        shop.put("shop_name", keyword);
        Observable.zip(mModel.getGoodsObservable(goods),
                mModel.getShopsgetGoodsObservable(shop),
                new BiFunction<OverseasGoodsOuterBean, RetailCenterBean, Map<String, Object>>() {
            @Override
            public Map<String, Object> apply(@NonNull OverseasGoodsOuterBean searchGoodsBean,
                                             @NonNull RetailCenterBean retailCenterBean) throws Exception {
                Map<String, Object> data = new HashMap();
                data.put(KEY_GOODS, searchGoodsBean);
                data.put(KEY_SHOPS, retailCenterBean);
                return data;
            }
        })
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new BaseObserver<Map<String, Object>>(this, mvpView) {
                    @Override
                    protected void onSuccess(Map<String, Object> data) {
                        OverseasGoodsOuterBean searchGoodsBean = (OverseasGoodsOuterBean) data.get(KEY_GOODS);
                        RetailCenterBean retailCenterBean = (RetailCenterBean) data.get(KEY_SHOPS);
                        if (searchGoodsBean.getCode() == Constant.REQUEST_OK) {
                            mvpView.showGoods(searchGoodsBean);
                        } else {
                            mvpView.onError(searchGoodsBean.getMessage());
                        }
                        if (retailCenterBean.getCode() == Constant.REQUEST_OK) {
                            mvpView.showShops(retailCenterBean);
                        } else {
                            mvpView.onError(retailCenterBean.getMessage());
                        }
                    }
                });
    }

    public void searchAllForOverseas(Map<String, String> data) {
        Map<String, String> groupbuydata = new HashMap<String, String>();
        Map<String, String> futuresdata = new HashMap<String, String>();
        Map<String, String> spotdata = new HashMap<String, String>();
        groupbuydata.putAll(data);
        futuresdata.putAll(data);
        spotdata.putAll(data);
        groupbuydata.put("goods_type", "4");
        futuresdata.put("goods_type", "3");
        spotdata.put("goods_type", "2");
        Observable.zip(mModel.getGoodsObservable(groupbuydata)
                , mModel.getGoodsObservable(futuresdata)
                , mModel.getGoodsObservable(spotdata)
                , new Function3<OverseasGoodsOuterBean, OverseasGoodsOuterBean,
                        OverseasGoodsOuterBean, Map<String, OverseasGoodsOuterBean>>() {
                    @Override
                    public Map<String, OverseasGoodsOuterBean> apply(@NonNull OverseasGoodsOuterBean groupbuy,
                                                                     @NonNull OverseasGoodsOuterBean futures,
                                                                     @NonNull OverseasGoodsOuterBean spot) throws Exception {
                        Map<String, OverseasGoodsOuterBean> data = new HashMap();
                        data.put(KEY_GROUP_BUY, groupbuy);
                        data.put(KEY_FUTURES, futures);
                        data.put(KEY_SPOT, spot);
                        return data;
                    }
                })
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new BaseObserver<Map<String, OverseasGoodsOuterBean>>(this, mvpView) {
                    @Override
                    protected void onSuccess(Map<String, OverseasGoodsOuterBean> data) {
                        mvpView.showOverseas(data);
                    }
                });
    }

    public void searchGoods(Map data) {
        mModel.searchGoods(data, new BaseObserver<OverseasGoodsOuterBean>(this, mvpView) {
            @Override
            protected void onSuccess(OverseasGoodsOuterBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.showGoods(data);
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

    public void hotKeywords(String search_type) {
        mModel.hotKeywords(search_type, new BaseObserver<KeyWordsBean>(this, mvpView, false) {
            @Override
            protected void onSuccess(KeyWordsBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.showHotKeywords(data.getData());
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }


    public void searchShop(Map data) {
        mModel.searchShops(data, new BaseObserver<RetailCenterBean>(this, mvpView) {
            @Override
            protected void onSuccess(RetailCenterBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.showShops(data);
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }


    @Override
    protected SearchModelImpl createModel() {
        return new SearchModelImpl();
    }

}
