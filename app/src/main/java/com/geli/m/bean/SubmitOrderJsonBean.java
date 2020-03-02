package com.geli.m.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/23.
 */

public class SubmitOrderJsonBean {
    private String address_id;
    private String pay_type;
    private String shipping_type;//配送类型1格利2提配
    private String door;//默认为0 送货上门为1
    private String invoice_id;
    private String pay_pwd;
    private List<CartRes> cartRes;
    //海外用到,其他不用
    private String goods_id;
    private String goods_number;
    private String postscript;
    private String shop_id;
    private String sku_id;
    private String groupon_id;

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getShipping_type() {
        return shipping_type;
    }

    public void setShipping_type(String shipping_type) {
        this.shipping_type = shipping_type;
    }

    public String getDoor() {
        return door;
    }

    public void setDoor(String door) {
        this.door = door;
    }

    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getPay_pwd() {
        return pay_pwd;
    }

    public void setPay_pwd(String pay_pwd) {
        this.pay_pwd = pay_pwd;
    }

    public List<CartRes> getCartRes() {
        return cartRes;
    }

    public void setCartRes(List<CartRes> cartRes) {
        this.cartRes = cartRes;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(String goods_number) {
        this.goods_number = goods_number;
    }

    public String getPostscript() {
        return postscript;
    }

    public void setPostscript(String postscript) {
        this.postscript = postscript;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getSku_id() {
        return sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    public String getGroupon_id() {
        return groupon_id;
    }

    public void setGroupon_id(String groupon_id) {
        this.groupon_id = groupon_id;
    }


    public static class CartRes {
        private String cart_id;
        private String cpl_id;
        private String postscript;
//        private SubmitOrderBean.DataEntity.ShopListEntity.LogisticsEntity logistics;


        public String getCart_id() {
            return cart_id;
        }

        public void setCart_id(String cart_id) {
            this.cart_id = cart_id;
        }

        public String getCpl_id() {
            return cpl_id;
        }

        public void setCpl_id(String cpl_id) {
            this.cpl_id = cpl_id;
        }

        public String getPostscript() {
            return postscript;
        }

        public void setPostscript(String postscript) {
            this.postscript = postscript;
        }

//        public SubmitOrderBean.DataEntity.ShopListEntity.LogisticsEntity getLogistics() {
//            return logistics;
//        }
//
//        public void setLogistics(SubmitOrderBean.DataEntity.ShopListEntity.LogisticsEntity logistics) {
//            this.logistics = logistics;
//        }
    }
}
