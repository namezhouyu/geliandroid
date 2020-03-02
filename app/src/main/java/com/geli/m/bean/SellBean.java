package com.geli.m.bean;

/**
 * Created by Steam_l on 2017/12/21.
 * 批零中心
 */

public class SellBean {
    /**
     * quantity_sold : 1253
     * shop_id : 100568
     * shop_longitude : 113.320484
     * distance : 0
     * shop_latitude : 23.082231
     * shop_type : 1
     * shop_img : upload/shops/20170927/150648440841264.png
     * shop_name : 深圳批零中心
     * shop_intro : 我是 深圳批零中心
     * moq : 1
     * virtual_quantity_sold : 1238
     */
    private int quantity_sold;
    private int shop_id;
    private String shop_longitude;
    private int distance;
    private String shop_latitude;
    private int shop_type;
    private String shop_img;
    private String shop_name;
    private String shop_intro;
    private int moq;
    private int virtual_quantity_sold;

    public void setQuantity_sold(int quantity_sold) {
        this.quantity_sold = quantity_sold;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public void setShop_longitude(String shop_longitude) {
        this.shop_longitude = shop_longitude;
    }

    public void setDistance(int distance) {
        this.distance = distance;
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

    public int getQuantity_sold() {
        return quantity_sold;
    }

    public int getShop_id() {
        return shop_id;
    }

    public String getShop_longitude() {
        return shop_longitude;
    }

    public int getDistance() {
        return distance;
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
