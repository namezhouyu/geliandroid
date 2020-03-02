package com.geli.m.bean;

/**
 * Created by Steam_l on 2017/12/21.
 * 猜你喜欢
 */

public class InterestGoodsBean  {
    /**
     * goods_name : 上品嫩肩牛排
     * shop_price : 0.00
     * goods_thumb : upload/201702/thumb_img/35047_thumb_G_1487311972910.jpg
     * goods_id : 1361
     * goods_type : 1
     * is_best : 1
     * goods_img : upload/201702/goods_img/35047_G_1487311972271.jpg
     */
    private String goods_name;
    private String shop_price;
    private String goods_thumb;
    private int goods_id;
    private int goods_type;
    private int is_best;
    private String goods_img;

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
