package com.geli.m.bean;

/**
 * Created by Steam_l on 2017/12/21.
 * 品牌街
 */

public class BrandBean {

    /**
     * shop_id : 100492
     * shop_longitude :
     * shop_latitude :
     * shop_type : 2
     * shop_img : upload\shops\20171109\20171109102412_99_shop_img.jpg
     * shop_name : 不二制油公司
     * shop_intro :
     * moq : 1
     * virtual_quantity_sold : 1581
     */
    private int shop_id;
    private String shop_longitude;
    private String shop_latitude;
    private int shop_type;
    private String shop_img;
    private String shop_name;
    private String shop_intro;
    private int moq;
    private int virtual_quantity_sold;

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public void setShop_longitude(String shop_longitude) {
        this.shop_longitude = shop_longitude;
    }

    public void setShop_latitude(String shop_latitude) {
        this.shop_latitude = shop_latitude;
    }

    public void setShop_type(int shop_type) {
        this.shop_type = shop_type;
    }

    public void setShop_img(String shop_img) {
        this.shop_img = shop_img;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public void setShop_intro(String shop_intro) {
        this.shop_intro = shop_intro;
    }

    public void setMoq(int moq) {
        this.moq = moq;
    }

    public void setVirtual_quantity_sold(int virtual_quantity_sold) {
        this.virtual_quantity_sold = virtual_quantity_sold;
    }

    public int getShop_id() {
        return shop_id;
    }

    public String getShop_longitude() {
        return shop_longitude;
    }

    public String getShop_latitude() {
        return shop_latitude;
    }

    public int getShop_type() {
        return shop_type;
    }

    public String getShop_img() {
        return shop_img;
    }

    public String getShop_name() {
        return shop_name;
    }

    public String getShop_intro() {
        return shop_intro;
    }

    public int getMoq() {
        return moq;
    }

    public int getVirtual_quantity_sold() {
        return virtual_quantity_sold;
    }
}
