package com.geli.m.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mrCTang on 2017/6/2.
 */

public class GoodsFromCatBean implements Serializable {

    /**
     * code : 100
     * data : [{"goods_name":"春江 大掌鸭  12kg/箱","origin_number":1,"shop_price":"345.00","parent_id":"3","goods_thumb":"upload/201704/thumb_img/35300_thumb_G_1493348329759.jpg","cat_id":3,"goods_id":2545,"specifications":[{"value":"12kg/箱","key":"规格"}]},{"goods_name":"鹅肠  12kg/箱","origin_number":1,"shop_price":"190.00","parent_id":"3","goods_thumb":"upload/201704/thumb_img/35238_thumb_G_1493348157033.jpg","cat_id":3,"goods_id":2483,"specifications":[{"value":"12kg/箱","key":"规格"}]},{"goods_name":"鹅头  15kg/箱","origin_number":1,"shop_price":"318.00","parent_id":"3","goods_thumb":"upload/201704/thumb_img/35237_thumb_G_1493347834224.jpg","cat_id":3,"goods_id":2482,"specifications":[{"value":"15kg/箱","key":"规格"}]},{"goods_name":"鹅心  15kg/箱","origin_number":1,"shop_price":"65.00","parent_id":"3","goods_thumb":"upload/201704/thumb_img/35236_thumb_G_1493347008561.jpg","cat_id":3,"goods_id":2481,"specifications":[{"value":"15kg/箱","key":"规格"}]},{"goods_name":"鹅肾  15kg/箱","origin_number":1,"shop_price":"490.00","parent_id":"3","goods_thumb":"upload/201704/thumb_img/35235_thumb_G_1493347992503.jpg","cat_id":3,"goods_id":2480,"specifications":[{"value":"15kg/箱","key":"规格"}]},{"goods_name":"光鹅（7斤-8斤/只）","origin_number":1,"shop_price":"15.00","parent_id":"3","goods_thumb":"","cat_id":3,"goods_id":2478,"specifications":[{"value":"1kg（抄码）","key":"规格"}]},{"goods_name":"三和 6只白条鸭（4.4斤/只）  13.2kg ","origin_number":1,"shop_price":"130.00","parent_id":"3","goods_thumb":"upload/201705/thumb_img/35231_thumb_G_1493696529845.jpg","cat_id":357,"goods_id":2476,"specifications":[{"value":"13.2kg","key":"规格"}]},{"goods_name":"三和 鸭肝  10kg/箱","origin_number":1,"shop_price":"37.00","parent_id":"3","goods_thumb":"upload/201705/thumb_img/35226_thumb_G_1493776030637.jpg","cat_id":357,"goods_id":2471,"specifications":[{"value":"10kg/箱","key":"规格"}]},{"goods_name":"六和鸭翅根   10kg/箱（约100个）","origin_number":1,"shop_price":"70.00","parent_id":"3","goods_thumb":"upload/201608/thumb_img/34883_thumb_G_1472091108670.jpg","cat_id":357,"goods_id":2196,"specifications":[{"value":" 10kg/箱（约100个）","key":"规格"}]},{"goods_name":"鸭二节翅（166个）10kg/件","origin_number":1,"shop_price":"128.00","parent_id":"3","goods_thumb":"upload/201608/thumb_img/34878_thumb_G_1472090396430.jpg","cat_id":357,"goods_id":2191,"specifications":[{"value":"10kg/件","key":"规格"}]}]
     * message : ok
     */
    private int code;
    private List<ShopInfoBean.DataEntity.GoodsResEntity> data;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(List<ShopInfoBean.DataEntity.GoodsResEntity> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public List<ShopInfoBean.DataEntity.GoodsResEntity> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class DataEntity {
        /**
         * goods_name : 春江 大掌鸭  12kg/箱
         * origin_number : 1
         * shop_price : 345.00
         * parent_id : 3
         * goods_thumb : upload/201704/thumb_img/35300_thumb_G_1493348329759.jpg
         * cat_id : 3
         * goods_id : 2545
         * specifications : [{"value":"12kg/箱","key":"规格"}]
         */
        private String goods_name;
        private int origin_number;
        private String shop_price;
        private String parent_id;
        private String goods_thumb;
        private int cat_id;
        private int goods_id;
        private List<CartBean.DataEntity.CartListEntity.SpecificationEntity> specifications;

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public void setOrigin_number(int origin_number) {
            this.origin_number = origin_number;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public void setGoods_thumb(String goods_thumb) {
            this.goods_thumb = goods_thumb;
        }

        public void setCat_id(int cat_id) {
            this.cat_id = cat_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public void setSpecifications(List<CartBean.DataEntity.CartListEntity.SpecificationEntity> specifications) {
            this.specifications = specifications;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public int getOrigin_number() {
            return origin_number;
        }

        public String getShop_price() {
            return shop_price;
        }

        public String getParent_id() {
            return parent_id;
        }

        public String getGoods_thumb() {
            return goods_thumb;
        }

        public int getCat_id() {
            return cat_id;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public List<CartBean.DataEntity.CartListEntity.SpecificationEntity> getSpecifications() {
            return specifications;
        }

    }
}
