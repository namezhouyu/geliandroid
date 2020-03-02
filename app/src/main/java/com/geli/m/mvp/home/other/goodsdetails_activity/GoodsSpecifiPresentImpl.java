package com.geli.m.mvp.home.other.goodsdetails_activity;

import com.geli.m.bean.SpecifiBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.mvp.model.GoodsSpecifiModelImpl;
import com.geli.m.mvp.view.GoodsSpecifiView;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Steam_l on 2018/1/16.
 */

public class GoodsSpecifiPresentImpl<V extends GoodsSpecifiView, M extends GoodsSpecifiModelImpl> extends BasePresenter<GoodsSpecifiView, GoodsSpecifiModelImpl> {
    public GoodsSpecifiPresentImpl(GoodsSpecifiView mvpView) {
        super(mvpView);
    }

    public void getGoodsSpecifi(final String goods_id) {
        mModel.getGoodsSpecifi(goods_id,
                new Function<ResponseBody, SpecifiBean>() {
                    @Override
                    public SpecifiBean apply(@NonNull ResponseBody body) throws Exception {
                        String string = body.string();

                        SpecifiBean specifiBean = mGson.fromJson(string, SpecifiBean.class);

                        if (specifiBean.getCode() == Constant.REQUEST_OK) {
                            JSONArray jsonArray = new JSONObject(string)
                                    .getJSONObject("data")
                                    .getJSONArray("goods_sku");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                setTieredPri(specifiBean, jsonArray, i);
                            }
                        }
                        return specifiBean;
                    }

                    /**
                     * 设置优惠价格（满足xxx件 对应什么价格）
                     *
                     * 因为传递过来的是字符串 -- 所以要解析
                     */
                    private void setTieredPri(SpecifiBean specifiBean, JSONArray jsonArray, int i) {
                        List tieredPriList = new ArrayList<SpecifiBean.DataEntity.GoodsSkuEntity.TieredPri>();
                        try {
                            JSONObject tiered_pri = jsonArray.getJSONObject(i).getJSONObject("tiered_pri");
                            Iterator<String> keys = tiered_pri.keys();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                String value = tiered_pri.getString(key);
                                SpecifiBean.DataEntity.GoodsSkuEntity.TieredPri tieredPri = new SpecifiBean.DataEntity.GoodsSkuEntity.TieredPri();
                                tieredPri.setKey(Double.valueOf(key));
                                tieredPri.setValue(Double.valueOf(value));
                                tieredPriList.add(tieredPri);
                            }
                        } catch (JSONException e) {

                        } finally {
                            SpecifiBean.DataEntity.GoodsSkuEntity goodsSkuEntity = specifiBean.getData().getGoods_sku().get(i);
                            Collections.sort(tieredPriList);
                            goodsSkuEntity.setTieredPri(tieredPriList);
                        }
                    }

                }, new BaseObserver<SpecifiBean>(this, mvpView) {
                    @Override
                    protected void onSuccess(SpecifiBean data) {
                        if (data.getCode() == 100) {
                            mvpView.showGoodSpecifi(data);
                        } else {
                            mvpView.onError(data.getMessage());
                        }
                    }
                });
    }

    @Override

    protected GoodsSpecifiModelImpl createModel() {
        return new GoodsSpecifiModelImpl();
    }

}
