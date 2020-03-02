package com.geli.m.bean;

/**
 * Created by Steam_l on 2017/12/21.
 * 商品推荐
 */

public class RecommendGoodsBean {

    /**
     * goods_name : 半壳扇贝
     * shop_price : 599.00
     * goods_thumb : upload/goods/20171212/151306584263307.png
     * goods_id : 4956
     * goods_type : 1
     * is_best : 1
     * goods_img : upload/goods/20171212/151306584263307.png
     */
    private String goods_name;
    private String shop_price;
    private String goods_thumb;
    private int goods_id;
    private int goods_type;
    private int is_best;
    private String goods_img;

    public RecommendGoodsBean(String goods_img) {
        this.goods_img = goods_img;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public void setGoods_thumb(String goods_thumb) {
        this.goods_thumb = goods_thumb;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public void setGoods_type(int goods_type) {
        this.goods_type = goods_type;
    }

    public void setIs_best(int is_best) {
        this.is_best = is_best;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public String getShop_price() {
        return shop_price;
    }

    public String getGoods_thumb() {
        return goods_thumb;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public int getGoods_type() {
        return goods_type;
    }

    public int getIs_best() {
        return is_best;
    }

    public String getGoods_img() {
        return goods_img;
    }
}
