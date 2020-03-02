package com.geli.m.bean;

/**
 * Created by Administrator on 2018/1/23.
 *
 * 购物车id、留言、优惠券信息json集合
 */
public class SubmitOrderJsonNormalBean {


    /**
     * cart_id : 14672,14671
     * postscript : AAA
     * cpl_id :
     */

    private String cart_id;             // 商品在购物车的id
    private String postscript;          // 留言
    private String cpl_id;              // 优惠券id

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getPostscript() {
        return postscript;
    }

    public void setPostscript(String postscript) {
        this.postscript = postscript;
    }

    public String getCpl_id() {
        return cpl_id;
    }

    public void setCpl_id(String cpl_id) {
        this.cpl_id = cpl_id;
    }

    @Override
    public String toString() {
        return "SubmitOrderJsonNormalBean{" +
                "cart_id='" + cart_id + '\'' +
                ", postscript='" + postscript + '\'' +
                ", cpl_id='" + cpl_id + '\'' +
                '}';
    }
}
