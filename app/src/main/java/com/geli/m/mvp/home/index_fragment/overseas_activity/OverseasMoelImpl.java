package com.geli.m.mvp.home.index_fragment.overseas_activity;

import android.text.TextUtils;
import com.geli.m.R;
import com.geli.m.bean.OverseasBean;
import com.geli.m.bean.OverseasCountrOutrBean;
import com.geli.m.bean.OverseasGoodsOuterBean;
import com.geli.m.bean.OverseasSortBean;
import com.geli.m.bean.ShopAptitudeBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.home.cart_fragment.main.CartModelImpl;
import com.geli.m.utils.Utils;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/23.
 */

public class OverseasMoelImpl extends CartModelImpl {

    public void getOverseasSort(String goodsType, String countries_id, BaseObserver observer) {
        HashMap<String, String> data = new HashMap<>();
        data.put("goods_type", goodsType);
        if (!TextUtils.isEmpty(countries_id)) {
            data.put("countries_id", countries_id);
        }
        Map sortData = new HashMap();
        if (!TextUtils.isEmpty(countries_id)) {
            sortData.put("countries_id", countries_id);
        }
        universal(Observable.zip(mApiService.getOverseasSortList(sortData)
                , mApiService.getOverseasGoodsList(data)
                , new BiFunction<OverseasSortBean, OverseasGoodsOuterBean, OverseasBean>() {
                    @Override
                    public OverseasBean apply(@NonNull OverseasSortBean overseasSortBean, @NonNull OverseasGoodsOuterBean overseasGoodsOuterBean) throws Exception {
                        OverseasBean overseasBean = new OverseasBean();
                        if (overseasSortBean.getCode() == Constant.REQUEST_OK && overseasGoodsOuterBean.getCode() == Constant.REQUEST_OK) {
                            overseasBean.setCode(Constant.REQUEST_OK);
                            overseasBean.setOverseasSortBean(overseasSortBean);
                            overseasBean.setOverseasGoodsBean(overseasGoodsOuterBean.getData());
                        } else {
                            overseasBean.setMessage(Utils.getString(R.string.error_refresh));
                        }
                        return overseasBean;
                    }
                }), observer);
    }

    public void getOverseasSort(String countries_id, BaseObserver<OverseasSortBean> observer) {
        Map sortData = new HashMap();
        if (!TextUtils.isEmpty(countries_id)) {
            sortData.put("countries_id", countries_id);
        }
        universal(mApiService.getOverseasSortList(sortData), observer);
    }


    public void getOverseasGoods(Map data, BaseObserver observer) {
        universal(mApiService.getOverseasGoodsList(data), observer);
    }

    public void initOverseasList(String goodsType, BaseObserver observer) {
        HashMap<String, String> data = new HashMap<>();
        data.put("goods_type", goodsType);
        universal(Observable.zip(mApiService.getOverseasCountriesList()
                , mApiService.getOverseasGoodsList(data)
                , new BiFunction<OverseasCountrOutrBean, OverseasGoodsOuterBean, OverseasBean>() {
                    @Override
                    public OverseasBean apply(@NonNull OverseasCountrOutrBean overseasCountrOutrBean, @NonNull OverseasGoodsOuterBean overseasGoodsOuterBean) throws Exception {
                        OverseasBean overseasBean = new OverseasBean();
                        if (overseasCountrOutrBean.getCode() == Constant.REQUEST_OK
                                && overseasGoodsOuterBean.getCode() == Constant.REQUEST_OK) {
                            overseasBean.setCode(100);
                            overseasBean.setOverseasCountrBean(overseasCountrOutrBean.getData());
                            overseasBean.setOverseasGoodsBean(overseasGoodsOuterBean.getData());
                        } else {
                            overseasBean.setMessage(Utils.getString(R.string.error_refresh));
                        }
                        return overseasBean;
                    }
                })
                , observer);

    }

    public void shopAptitude(String shop_id, BaseObserver<ShopAptitudeBean> observer) {
        universal(mApiService.shopAptitude(shop_id), observer);
    }
}
