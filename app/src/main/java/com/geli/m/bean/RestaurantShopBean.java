package com.geli.m.bean;

import java.util.Comparator;
import java.util.List;

/**
 * author:  shen
 * date:    2019/1/14
 * 食品馆中商店列表
 */
public class RestaurantShopBean {


    /**
     * code : 100
     * message : ok
     * data : [{"shop_id":100762,"shop_name":"测试批发市场","virtual_quantity_sold":0,"shop_img_thumb":"upload/shops/20190130/1548839201.png","shop_intro":"测试地址","stall_name":"","goods_res":[{"goods_id":5536,"shop_price":"1.00","goods_name":"1","goods_thumb":"upload/goods/20190228/s155134687553785.jpg"},{"goods_id":5538,"shop_price":"0.01","goods_name":"测试商品20190314","goods_thumb":"upload/goods/20190314/s155255529773948.jpg"},{"goods_id":5539,"shop_price":"0.05","goods_name":"测试商品2019031402","goods_thumb":"upload/goods/20190314/s155255538190220.jpg"},{"goods_id":5540,"shop_price":"0.01","goods_name":"测试商品2019032201","goods_thumb":"upload/goods/20190322/s155322063444172.jpg"}],"cat_res":["海鱼系列","白虾","田螺"]}]
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
         * shop_id : 100762
         * shop_name : 测试批发市场
         * virtual_quantity_sold : 0
         * shop_img_thumb : upload/shops/20190130/1548839201.png
         * shop_intro : 测试地址
         * stall_name :
         * goods_res : [{"goods_id":5536,"shop_price":"1.00","goods_name":"1","goods_thumb":"upload/goods/20190228/s155134687553785.jpg"},{"goods_id":5538,"shop_price":"0.01","goods_name":"测试商品20190314","goods_thumb":"upload/goods/20190314/s155255529773948.jpg"},{"goods_id":5539,"shop_price":"0.05","goods_name":"测试商品2019031402","goods_thumb":"upload/goods/20190314/s155255538190220.jpg"},{"goods_id":5540,"shop_price":"0.01","goods_name":"测试商品2019032201","goods_thumb":"upload/goods/20190322/s155322063444172.jpg"}]
         * cat_res : ["海鱼系列","白虾","田螺"]
         */

        private int shop_id;
        private String shop_name;
        private int virtual_quantity_sold;
        private String shop_img_thumb;
        private String shop_intro;
        private String stall_name;
        private List<GoodsResBean> goods_res;
        private List<String> cat_res;

        private String goods_main;      // 主营商品
        private int is_resale;          // 是否拼团

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

        public int getVirtual_quantity_sold() {
            return virtual_quantity_sold;
        }

        public void setVirtual_quantity_sold(int virtual_quantity_sold) {
            this.virtual_quantity_sold = virtual_quantity_sold;
        }

        public String getShop_img_thumb() {
            return shop_img_thumb;
        }

        public void setShop_img_thumb(String shop_img_thumb) {
            this.shop_img_thumb = shop_img_thumb;
        }

        public String getShop_intro() {
            return shop_intro;
        }

        public void setShop_intro(String shop_intro) {
            this.shop_intro = shop_intro;
        }

        public String getStall_name() {
            return stall_name;
        }

        public void setStall_name(String stall_name) {
            this.stall_name = stall_name;
        }

        public List<GoodsResBean> getGoods_res() {
            return goods_res;
        }

        public void setGoods_res(List<GoodsResBean> goods_res) {
            this.goods_res = goods_res;
        }

        public List<String> getCat_res() {
            return cat_res;
        }

        public void setCat_res(List<String> cat_res) {
            this.cat_res = cat_res;
        }

        public String getGoods_main() {
            return goods_main;
        }

        public void setGoods_main(String goods_main) {
            this.goods_main = goods_main;
        }

        public int getIs_resale() {
            return is_resale;
        }

        public void setIs_resale(int is_resale) {
            this.is_resale = is_resale;
        }

        public static class GoodsResBean {
            /**
             * goods_id : 5536
             * shop_price : 1.00
             * goods_name : 1
             * goods_thumb : upload/goods/20190228/s155134687553785.jpg
             */

            private int goods_id;
            private String shop_price;
            private String goods_name;
            private String goods_thumb;

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
        }

        // 排序
        public static class RestaurantShopComparator implements Comparator {
            public int compare(Object o1,Object o2){
                DataBean c1 = (DataBean)o1;
                DataBean c2 = (DataBean)o2;

                int result = c1.virtual_quantity_sold < c2.virtual_quantity_sold ?
                        1 : (c1.virtual_quantity_sold == c2.virtual_quantity_sold ? 0 : -1);

                return result;
            }
        }
    }
}
