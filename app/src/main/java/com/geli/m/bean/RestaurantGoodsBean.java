package com.geli.m.bean;

import java.util.List;

/**
 * author:  shen
 * date:    2019/3/22
 */
public class RestaurantGoodsBean {

    /**
     * code : 100
     * message : ok
     * data : [{"goods_id":5536,"shop_price":"1.00","goods_name":"1","goods_thumb":"upload/goods/20190228/s155134687553785.jpg","virtual_quantity_sold":1,"shop_id":100762,"shop_name":"测试批发市场","stall_name":"测试档口"},{"goods_id":5539,"shop_price":"0.05","goods_name":"测试商品2019031402","goods_thumb":"upload/goods/20190314/s155255538190220.jpg","virtual_quantity_sold":null,"shop_id":100762,"shop_name":"测试批发市场","stall_name":"测试档口"},{"goods_id":5538,"shop_price":"0.01","goods_name":"测试商品20190314","goods_thumb":"upload/goods/20190314/s155255529773948.jpg","virtual_quantity_sold":null,"shop_id":100762,"shop_name":"测试批发市场","stall_name":"测试档口"},{"goods_id":5540,"shop_price":"0.01","goods_name":"测试商品2019032201","goods_thumb":"upload/goods/20190322/s155322063444172.jpg","virtual_quantity_sold":1,"shop_id":100762,"shop_name":"测试批发市场","stall_name":"测试档口"}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * goods_id : 5536
         * shop_price : 1.00
         * goods_name : 1
         * goods_thumb : upload/goods/20190228/s155134687553785.jpg
         * virtual_quantity_sold : 1
         * shop_id : 100762
         * shop_name : 测试批发市场
         * stall_name : 测试档口
         */

        private int goods_id;
        private String shop_price;
        private String goods_name;
        private String goods_thumb;
        private int virtual_quantity_sold;
        private int shop_id;
        private String shop_name;
        private String stall_name;
        private int gs_id;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_thumb() {
            return goods_thumb;
        }

        public void setGoods_thumb(String goods_thumb) {
            this.goods_thumb = goods_thumb;
        }

        public int getVirtual_quantity_sold() {
            return virtual_quantity_sold;
        }

        public void setVirtual_quantity_sold(int virtual_quantity_sold) {
            this.virtual_quantity_sold = virtual_quantity_sold;
        }

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getStall_name() {
            return stall_name;
        }

        public void setStall_name(String stall_name) {
            this.stall_name = stall_name;
        }

        public int getGs_id() {
            return gs_id;
        }

        public void setGs_id(int gs_id) {
            this.gs_id = gs_id;
        }
    }
}
