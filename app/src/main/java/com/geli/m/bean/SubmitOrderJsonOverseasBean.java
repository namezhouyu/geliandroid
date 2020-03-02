package com.geli.m.bean;

/**
 *
 *
 * 海外团购
 */
public class SubmitOrderJsonOverseasBean {


    /**
     * cart_id : 14672,14671
     * postscript : AAA
     * cpl_id :
     */

    private String cart_id;             // 商品在购物车的id
    private String postscript;          // 留言
    private String cpl_id;              // 优惠券id


//    user_id 	post 	是 	用户ID唯一标识(md5加密)
//    address_id 	post 	  	地址ID
//    shipping_type 	post 	  	配送方式
//    groupon_id 	post 	  	团购id
//    postscript 	post 	  	留言
//    sku_id 	post
//    goods_number 	post 	  	购买数量
//    invoice_id 	post 	  	发票id

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
