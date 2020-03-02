package com.geli.m.mvp.home.other.shopdetails_activity.shopdetails_fragment;

import com.geli.m.R;
import com.geli.m.bean.BaseBean;
import com.geli.m.bean.GoodsFromCatBean;
import com.geli.m.bean.ShopInfoBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.home.cart_fragment.main.CartPresentImpl;
import com.geli.m.utils.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/1/11.
 */

public class ShopInfoPresentImpl extends CartPresentImpl<ShopInfoView, ShopInfoModelImpl> {

    public ShopInfoPresentImpl(ShopInfoView mvpView) {
        super(mvpView);
    }

    @Override
    protected ShopInfoModelImpl createModel() {
        return new ShopInfoModelImpl();
    }

    public void getShopInfo(Map data) {
        ((ShopInfoModelImpl) mModel).getShopInfo(data, new BaseObserver<ShopInfoBean>(this, mvpView) {
            @Override
            protected void onSuccess(ShopInfoBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    ((ShopInfoView) mvpView).showInfo(data);
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

    public void getGoodsFromCat(String shop_id, String cat_id) {
        Map<String, String> data = new HashMap<>();
        data.put("shop_id", shop_id);
        data.put("cat_id", cat_id);
        ((ShopInfoModelImpl) mModel).getGoodsFormCat(data, new BaseObserver<GoodsFromCatBean>(this, mvpView, false) {
            @Override
            protected void onSuccess(GoodsFromCatBean data) {
                if (data.getCode() == 100) {
                    ((ShopInfoView) mvpView).showGoodsFromCat(data.getData());
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

    public void getGoodsFromCatTemp(String shop_id, String cat_id) {
        Map<String, String> data = new HashMap<>();
        data.put("shop_id", shop_id);
        data.put("cat_id", cat_id);
        ((ShopInfoModelImpl) mModel).getGoodsFormCat(data, new BaseObserver<GoodsFromCatBean>(this, mvpView, false) {
            @Override
            protected void onSuccess(GoodsFromCatBean data) {
                if (data.getCode() == 100) {
                    ((ShopInfoView) mvpView).showGoodsFromCatTemp(data.getData());
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }


    public void getGoodsFromOtherCat(String shop_id, String gs_id) {
        Map<String, String> data = new HashMap<>();
        data.put("shop_id", shop_id);
        data.put("gs_id", gs_id);
        ((ShopInfoModelImpl) mModel).getGoodsFormCat(data, new BaseObserver<GoodsFromCatBean>(this, mvpView, false) {
            @Override
            protected void onSuccess(GoodsFromCatBean data) {
                if (data.getCode() == 100) {
                    ((ShopInfoView) mvpView).showGoodsFromCat(data.getData());
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

    public void getGoodsFromOtherCatTemp(String shop_id, String gs_id) {
        Map<String, String> data = new HashMap<>();
        data.put("shop_id", shop_id);
        data.put("gs_id", gs_id);
        ((ShopInfoModelImpl) mModel).getGoodsFormCat(data, new BaseObserver<GoodsFromCatBean>(this, mvpView, false) {
            @Override
            protected void onSuccess(GoodsFromCatBean data) {
                if (data.getCode() == 100) {
                    ((ShopInfoView) mvpView).showGoodsFromCatTemp(data.getData());
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

    /**
     * 收藏
     * @param data
     */
    public void shopCollection(Map<String, String> data) {
        if (BaseActivity.gotoLogin(mvpView)) {
            mvpView.onError(Utils.getString(R.string.please_login));
            return;
        }
        ((ShopInfoModelImpl) mModel).collection(data, new BaseObserver<ResponseBody>(this, mvpView) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    BaseBean baseBean = parseData(data.string());
                    if (baseBean.code == Constant.REQUEST_OK) {
                        ((ShopInfoView) mvpView).showCollectionResult(baseBean.message);
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


    public List<ShopInfoBean.DataEntity.CatResEntity.CartEntity> setCartData(List<ShopInfoBean.DataEntity.CatResEntity.CartEntity> cartEntityList){

        /** "0级分类"列表 */
        List<ShopInfoBean.DataEntity.CatResEntity.CartEntity> dCart = new ArrayList<>();
        Iterator<ShopInfoBean.DataEntity.CatResEntity.CartEntity> iterator = cartEntityList.iterator();

        /* 抽出"0级分类" */
        while (iterator.hasNext()) {
            ShopInfoBean.DataEntity.CatResEntity.CartEntity next = iterator.next();
            if (next.getLevel() == 0) {
                dCart.add(next);
                iterator.remove();      // 在此列表中 -- 去掉这个"0级分类"???
            }
        }


        /* 遍历"0级分类",设置对应的"1级分类" */
        for (int i = 0; i < dCart.size(); i++) {
            ShopInfoBean.DataEntity.CatResEntity.CartEntity cartEntity = dCart.get(i);      // "0级分类"

            iterator = cartEntityList.iterator();
            while (iterator.hasNext()) {//遍历剩余的1级cart
                ShopInfoBean.DataEntity.CatResEntity.CartEntity next = iterator.next();
                // 循环的分类的"父分类id" 是否是 对应"0级分类"的id
                if (next.getParent_id() == cartEntity.getCat_id()) {
                    //1级添加到0级carlist
                    cartEntity.getSmallCartList().add(next);
                    iterator.remove();
                }
            }
        }

        Collections.sort(dCart, new ShopInfoBean.DataEntity.CatResEntity.CartEntity.CartComparator());

        return dCart;
    }

    public List<ShopInfoBean.DataEntity.GoodsResEntity> setGoodsResData(
            List<ShopInfoBean.DataEntity.GoodsResEntity> goodsRes,
            List<ShopInfoBean.DataEntity.CatResEntity.CartEntity> cartList,
            List<ShopInfoBean.DataEntity.CatResEntity.OthercartEntity> otherCartList) {

        // Collections.sort(goodsRes, new ShopInfoBean.DataEntity.GoodsResEntity.GoodsResComparator());

        for(ShopInfoBean.DataEntity.CatResEntity.OthercartEntity entity : otherCartList){

            ShopInfoBean.DataEntity.GoodsResEntity goodsResEntity = new ShopInfoBean.DataEntity.GoodsResEntity();
            goodsResEntity.setGs_id(entity.getGs_id());
            goodsResEntity.dataNUll = true;
            goodsResEntity.setGoods_name("大预加载:" + entity.getGs_name());
            goodsResEntity.setGoods_number(12);
            goodsResEntity.setShop_price("10.0");
            goodsRes.add(goodsResEntity);


        }

        for(ShopInfoBean.DataEntity.CatResEntity.CartEntity entity : cartList){
            if(!(entity.getSmallCartList().size() > 0)) {
                ShopInfoBean.DataEntity.GoodsResEntity goodsResEntity = new ShopInfoBean.DataEntity.GoodsResEntity();
                goodsResEntity.setCat_id(entity.getCat_id());
                goodsResEntity.setParent_id(entity.getParent_id());
                goodsResEntity.dataNUll = true;
                goodsResEntity.setGoods_name("大预加载:" + entity.getCat_name());
                goodsResEntity.setGoods_number(12);
                goodsResEntity.setShop_price("10.0");
                goodsRes.add(goodsResEntity);
            }

            for(ShopInfoBean.DataEntity.CatResEntity.CartEntity smallCartEntity : entity.getSmallCartList()){
                ShopInfoBean.DataEntity.GoodsResEntity smallGoodsResEntity = new ShopInfoBean.DataEntity.GoodsResEntity();
                smallGoodsResEntity.setCat_id(smallCartEntity.getCat_id());
                smallGoodsResEntity.setParent_id(smallCartEntity.getParent_id());
                smallGoodsResEntity.dataNUll = true;
                smallGoodsResEntity.setGoods_name("小预加载:" + smallCartEntity.getCat_name());
                smallGoodsResEntity.setGoods_number(12);
                smallGoodsResEntity.setShop_price("10.0");
                goodsRes.add(smallGoodsResEntity);
            }
        }



        // Collections.sort(goodsRes, new ShopInfoBean.DataEntity.GoodsResEntity.GoodsResComparator());

        return goodsRes;
    }

    /**
     * 将"各种分类"使用Map存储
     * @param othercartList 其他分类
     * @param cartList  普通分类
     * @return
     */
    public Map<Integer, String> parse(List<ShopInfoBean.DataEntity.CatResEntity.OthercartEntity> othercartList,
                                      List<ShopInfoBean.DataEntity.CatResEntity.CartEntity> cartList) {

        Map<Integer, String> temp = new HashMap<>();
        if(othercartList != null && othercartList.size()>0){
            for(ShopInfoBean.DataEntity.CatResEntity.OthercartEntity othercartEntity : othercartList){
                temp.put(othercartEntity.getGs_id() + 100000, othercartEntity.getGs_name());
            }
        }

        if(cartList != null && cartList.size()>0){
            for(ShopInfoBean.DataEntity.CatResEntity.CartEntity cartEntity : cartList){

                if(cartEntity.getSmallCartList().size() > 0){
                    for(ShopInfoBean.DataEntity.CatResEntity.CartEntity smallCartEntity : cartEntity.getSmallCartList()) {
                        temp.put(smallCartEntity.getCat_id(), cartEntity.getCat_name() + "-" + smallCartEntity.getCat_name());
                    }
                }else {
                    temp.put(cartEntity.getCat_id(), cartEntity.getCat_name());
                }
            }
        }

        return temp;
    }

    /**
     * 将"各种分类"使用Map存储 -- 这里区分了下"其他分类 和 普通分类" 混了会出问题的
     * @param othercartList 其他分类
     * @param cartList  普通分类
     * @return
     */
    public Map<Integer, Map<Integer, String>> parse2(List<ShopInfoBean.DataEntity.CatResEntity.OthercartEntity> othercartList,
                                      List<ShopInfoBean.DataEntity.CatResEntity.CartEntity> cartList) {

        Map<Integer, Map<Integer, String>> tempMap = new HashMap<>();

        if(othercartList != null && othercartList.size()>0){
            Map<Integer, String> temp = new HashMap<>();
            for(ShopInfoBean.DataEntity.CatResEntity.OthercartEntity othercartEntity : othercartList){
                temp.put(othercartEntity.getGs_id(), othercartEntity.getGs_name());
            }
            tempMap.put(1, temp);
        }

        if(cartList != null && cartList.size()>0){
            Map<Integer, String> temp = new HashMap<>();
            for(ShopInfoBean.DataEntity.CatResEntity.CartEntity cartEntity : cartList){
                if(cartEntity.getSmallCartList().size() > 0){
                    for(ShopInfoBean.DataEntity.CatResEntity.CartEntity smallCartEntity : cartEntity.getSmallCartList()) {
                        temp.put(smallCartEntity.getCat_id(), cartEntity.getCat_name() + "-" + smallCartEntity.getCat_name());
                    }
                }else {
                    temp.put(cartEntity.getCat_id(), cartEntity.getCat_name());
                }
            }
            tempMap.put(2, temp);
        }

        return tempMap;
    }
}
