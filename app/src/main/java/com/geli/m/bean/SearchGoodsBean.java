package com.geli.m.bean;

import java.util.List;

/**
 * Created by Steam_l on 2018/3/16.
 */

public class SearchGoodsBean {
    /**
     * code : 100
     * data : [{"goods_name":"测试团购商品_第7期","origin_number":1,"coun_id":5,"shop_price":"11.00","surplus":111,"sold_number":0,"goods_id":4964,"specifications":[{"value":"1kg*10包/箱","key":"规格"}],"coun_name":"越南","groupon_id":23,"goods_unit":"件","target_number":111,"goods_thumb":"upload/goods/20180117/151615683372556.png","goods_type":4,"participants":0},{"goods_name":"团购测试商品2_第3期","origin_number":1,"coun_id":12,"shop_price":"1.00","surplus":11,"sold_number":0,"goods_id":4965,"specifications":[{"value":"1kg*10包/箱","key":"规格"}],"coun_name":"缅甸","groupon_id":22,"goods_unit":"吨","target_number":11,"goods_thumb":"upload/goods/20180117/151615688045658.png","goods_type":4,"participants":0},{"goods_name":"海外团购APP数据测试_第1期","origin_number":1,"coun_id":5,"shop_price":"1000.00","surplus":10,"sold_number":0,"goods_id":5500,"specifications":[{"value":"10kg/箱","key":"规格"}],"coun_name":"越南","groupon_id":24,"goods_unit":"箱","target_number":10,"goods_thumb":"","goods_type":4,"participants":0}]
     * message : ok
     */
    private int code;
    private List<DataEntity> data;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class DataEntity {
        /**
         * goods_name : 测试团购商品_第7期
         * origin_number : 1
         * coun_id : 5
         * shop_price : 11.00
         * surplus : 111
         * sold_number : 0
         * goods_id : 4964
         * specifications : [{"value":"1kg*10包/箱","key":"规格"}]
         * coun_name : 越南
         * groupon_id : 23
         * goods_unit : 件
         * target_number : 111
         * goods_thumb : upload/goods/20180117/151615683372556.png
         * goods_type : 4
         * participants : 0
         */
        private String goods_name;
        private int origin_number;
        private int coun_id;
        private String shop_price;
        private int surplus;
        private int sold_number;
        private int goods_id;
        private List<SpecificationsEntity> specifications;
        private String coun_name;
        private int groupon_id;
        private String goods_unit;
        private int target_number;
        private String goods_thumb;
        private int goods_type;
        private int participants;

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public void setOrigin_number(int origin_number) {
            this.origin_number = origin_number;
        }

        public void setCoun_id(int coun_id) {
            this.coun_id = coun_id;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public void setSurplus(int surplus) {
            this.surplus = surplus;
        }

        public void setSold_number(int sold_number) {
            this.sold_number = sold_number;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public void setSpecifications(List<SpecificationsEntity> specifications) {
            this.specifications = specifications;
        }

        public void setCoun_name(String coun_name) {
            this.coun_name = coun_name;
        }

        public void setGroupon_id(int groupon_id) {
            this.groupon_id = groupon_id;
        }

        public void setGoods_unit(String goods_unit) {
            this.goods_unit = goods_unit;
        }

        public void setTarget_number(int target_number) {
            this.target_number = target_number;
        }

        public void setGoods_thumb(String goods_thumb) {
            this.goods_thumb = goods_thumb;
        }

        public void setGoods_type(int goods_type) {
            this.goods_type = goods_type;
        }

        public void setParticipants(int participants) {
            this.participants = participants;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public int getOrigin_number() {
            return origin_number;
        }

        public int getCoun_id() {
            return coun_id;
        }

        public String getShop_price() {
            return shop_price;
        }

        public int getSurplus() {
            return surplus;
        }

        public int getSold_number() {
            return sold_number;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public List<SpecificationsEntity> getSpecifications() {
            return specifications;
        }

        public String getCoun_name() {
            return coun_name;
        }

        public int getGroupon_id() {
            return groupon_id;
        }

        public String getGoods_unit() {
            return goods_unit;
        }

        public int getTarget_number() {
            return target_number;
        }

        public String getGoods_thumb() {
            return goods_thumb;
        }

        public int getGoods_type() {
            return goods_type;
        }

        public int getParticipants() {
            return participants;
        }

        public static class SpecificationsEntity {
            /**
             * value : 1kg*10包/箱
             * key : 规格
             */
            private String value;
            private String key;

            public void setValue(String value) {
                this.value = value;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getValue() {
                return value;
            }

            public String getKey() {
                return key;
            }
        }
    }
}
