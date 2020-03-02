package com.geli.m.mvp.home.cart_fragment.main;

import com.geli.m.R;
import com.geli.m.bean.BaseBean;
import com.geli.m.bean.CartBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.home.other.goodsdetails_activity.GoodsSpecifiPresentImpl;
import com.geli.m.utils.Utils;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/1/15.
 */

public class CartPresentImpl<V extends CartView, M extends CartModelImpl>
        extends GoodsSpecifiPresentImpl<CartView, CartModelImpl> {

    /** 添加编辑购物车 */
    public boolean isEdit = false;
    public boolean isDelete = false;
    /**  */
    public boolean isGetCartList = false;
    /** 添加 */
    public boolean isAddCart = false;

    public boolean isPost() {
        return isAddCart || isGetCartList || isDelete || isEdit;
    }

    public CartPresentImpl(CartView mvpView) {
        super(mvpView);
    }

    public void getCartList(Map data, boolean isShowLoading) {
        isGetCartList = true;
        ((CartModelImpl) mModel).getCartList(data, new BaseObserver<ResponseBody>(this, mvpView, isShowLoading) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    String string = data.string();
                    BaseBean baseBean = parseData(string);
                    if (baseBean.code == Constant.REQUEST_OK) {
                        CartBean cartBean = mGson.fromJson(string, CartBean.class);
                        ((CartView) mvpView).showCartList(cartBean.getData());
                    } else if (baseBean.code == 200) {
                        // mvpView.onSuccess(baseBean.message);
                        ((CartView) mvpView).showCartList(null);
                    } else {
                        mvpView.onError(baseBean.message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mvpView.onError(parseError(e));
                } finally {
                    isGetCartList = false;
                }
            }
        });
    }

    public void getCartList(Map data) {
        getCartList(data, false);
    }

    public void editCart(Map data, final boolean isAdd) {
        isEdit = !isAdd;
        this.isAddCart = isAdd;
        if (BaseActivity.gotoLogin(mvpView)) {
            mvpView.onError(Utils.getString(R.string.please_login));
            return;
        }
        ((CartModelImpl) mModel).editCart(data, new BaseObserver<ResponseBody>(this, mvpView, true) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    BaseBean baseBean = parseData(data.string());
                    if (baseBean.code == Constant.REQUEST_OK) {
                        if (isAdd) {
                            mvpView.onSuccess(Utils.getString(R.string.add_success));
                        } else {
                            mvpView.onSuccess(Utils.getString(R.string.edit_success));
                        }
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



    public void deleteCart(String user_id, String cart_id) {
        isDelete = true;
        ((CartModelImpl) mModel).delCart(user_id, cart_id, new BaseObserver<ResponseBody>(this, mvpView, true) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    BaseBean baseBean = parseData(data.string());
                    if (baseBean.code == Constant.REQUEST_OK) {
                        mvpView.onSuccess(Utils.getString(R.string.delete_success));
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

    @Override
    protected CartModelImpl createModel() {
        return new CartModelImpl();
    }

}
