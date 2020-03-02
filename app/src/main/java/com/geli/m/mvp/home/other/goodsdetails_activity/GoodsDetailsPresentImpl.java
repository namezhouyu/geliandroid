package com.geli.m.mvp.home.other.goodsdetails_activity;

import com.geli.m.R;
import com.geli.m.bean.BaseBean;
import com.geli.m.bean.GoodsDetailsBean;
import com.geli.m.bean.ShPriceBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.utils.Utils;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/1/17.
 */

public class GoodsDetailsPresentImpl extends GoodsSpecifiPresentImpl<GoodsDetailsView, GoodsDetailsModelImpl> {
    public boolean isToLike;

    public GoodsDetailsPresentImpl(GoodsDetailsView mvpView) {
        super(mvpView);
    }

    public void getGoodsDetails(String user_id, String goods_id) {
        ((GoodsDetailsModelImpl) mModel).getGoodsDetails(user_id, goods_id, new BaseObserver<GoodsDetailsBean>(this, mvpView) {
            @Override
            protected void onSuccess(GoodsDetailsBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    ((GoodsDetailsView) mvpView).showGoodsData(data.getData());
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

    /**
     * 改变"收藏"
     * @param data
     */
    public void collectionGood(Map data) {
        if (BaseActivity.gotoLogin(mvpView)) {
            mvpView.onError(Utils.getString(R.string.please_login));
            return;
        }
        ((GoodsDetailsModelImpl) mModel).collectionGood(data, new BaseObserver<ResponseBody>(this, mvpView) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    BaseBean baseBean = parseData(data.string());
                    if (baseBean.code == Constant.REQUEST_OK) {
                        ((GoodsDetailsView) mvpView).showCollectionResult(baseBean.message);
                    } else {
                        mvpView.onError(baseBean.message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mvpView.onError(parseError(e));
                }
            }
        });
    }

    /**
     * 添加到购物车
     * @param data
     */
    public void addCart(Map data) {
        if (BaseActivity.gotoLogin(mvpView)) {
            mvpView.onError(Utils.getString(R.string.please_login));
            return;
        }

        ((GoodsDetailsModelImpl) mModel).addCart(data, new BaseObserver<ResponseBody>(this, mvpView, true) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    BaseBean baseBean = parseData(data.string());
                    if (baseBean.code == Constant.REQUEST_OK) {
                        mvpView.onSuccess(Utils.getString(R.string.add_success));
                    } else {
                        mvpView.onError(baseBean.message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mvpView.onError(parseError(e));
                }
            }
        });
    }


    /**
     *
     * @param user_id   用户id
     * @param com_id    评论id
     */
    public void toLike(String user_id, String com_id) {
        if (BaseActivity.gotoLogin(mvpView)) {
            mvpView.onError(Utils.getString(R.string.please_login));
            return;
        }

        isToLike = true;
        ((GoodsDetailsModelImpl) mModel).toLike(user_id, com_id, new BaseObserver<ResponseBody>(this, mvpView) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    BaseBean baseBean = parseData(data.string());
                    if (baseBean.code == Constant.REQUEST_OK) {
                        mvpView.onSuccess(baseBean.message);
                    } else {
                        mvpView.onError(baseBean.message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mvpView.onError(parseError(e));
                }
            }
        });
    }


    /**
     * 账期价格
     * @param user_id
     * @param goods_id
     * @param shop_id
     */
    public void shPrice(String user_id, String goods_id, String shop_id) {
        // universal(mApiService.shPrice(user_id, goods_id, shop_id), observer);
        ((GoodsDetailsModelImpl) mModel).shPrice(user_id, goods_id, shop_id,
                new BaseObserver<ShPriceBean>(this, mvpView) {
            @Override
            protected void onSuccess(ShPriceBean data) {
                ((GoodsDetailsView) mvpView).showShPrice(data);
            }
        });

    }

    @Override
    protected GoodsDetailsModelImpl createModel() {
        return new GoodsDetailsModelImpl();
    }
}
