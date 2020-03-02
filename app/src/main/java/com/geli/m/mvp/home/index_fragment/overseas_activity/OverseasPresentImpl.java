package com.geli.m.mvp.home.index_fragment.overseas_activity;

import android.content.Context;
import com.geli.m.bean.OverseasBean;
import com.geli.m.bean.OverseasGoodsOuterBean;
import com.geli.m.bean.OverseasSortBean;
import com.geli.m.bean.ShopAptitudeBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.home.cart_fragment.main.CartPresentImpl;
import com.geli.m.utils.ShowSingleToast;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/23.
 */

public class OverseasPresentImpl extends CartPresentImpl<OverseasView, OverseasMoelImpl> {
    public OverseasPresentImpl(OverseasView mvpView) {
        super(mvpView);
    }

    public void init(String goodsType) {
        ((OverseasMoelImpl) mModel).initOverseasList(goodsType, new BaseObserver<OverseasBean>(this, mvpView) {
            @Override
            protected void onSuccess(OverseasBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    ((OverseasView) mvpView).showData(data);
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

    public void getSortList(String goodsType, String country_id, boolean showGoods) {
        if (showGoods) {
            ((OverseasMoelImpl) mModel).getOverseasSort(goodsType, country_id, new BaseObserver<OverseasBean>(this, mvpView, false) {
                @Override
                protected void onSuccess(OverseasBean data) {
                    if (data.getCode() == Constant.REQUEST_OK) {
                        ((OverseasView) mvpView).showSortData(data);
                    } else {
                        mvpView.onError(data.getMessage());
                    }
                }
            });
        } else {
            ((OverseasMoelImpl) mModel).getOverseasSort(country_id, new BaseObserver<OverseasSortBean>(this, mvpView, false) {
                @Override
                protected void onSuccess(OverseasSortBean data) {
                    if (data.getCode() == Constant.REQUEST_OK) {
                        OverseasBean overseasBean = new OverseasBean();
                        overseasBean.setOverseasSortBean(data);
                        OverseasSortBean overseasSortBean = overseasBean.getOverseasSortBean();
                        ((OverseasView) mvpView).setSortData(overseasSortBean);
                    } else {
                        mvpView.onError(data.getMessage());
                    }
                }
            });
        }
    }

    public void getGoodsList(Map data) {
        ((OverseasMoelImpl) mModel).getOverseasGoods(data, new BaseObserver<OverseasGoodsOuterBean>(this, mvpView) {
            @Override
            protected void onSuccess(OverseasGoodsOuterBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    ((OverseasView) mvpView).showGoodsData(data.getData());
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

    public void shopAptitude(final Context context, final String shop_id) {
        ((OverseasMoelImpl) mModel).shopAptitude(shop_id, new BaseObserver<ShopAptitudeBean>(this, mvpView, false) {
            @Override
            protected void onSuccess(ShopAptitudeBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    ((OverseasView) mvpView).showShopAptitude(data.getData().getImg());
                } else {
                    ShowSingleToast.showToast(context,shop_id);
                }
            }
        });
    }


    @Override
    protected OverseasMoelImpl createModel() {
        return new OverseasMoelImpl();
    }
}
