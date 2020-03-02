package com.geli.m.bean;

import java.util.List;

/**
 * author:  shen
 * date:    2018/12/3
 */
public class AccountOrderBean {


    /**
     * code : 100
     * message : ok
     * data : [{"order_id":6140,"order_sn":"GL20181203095048088374","add_time":1543801848,"order_status":5,"pay_status":8,"shipping_status":2,"order_amount":"440.00","goods_res":[{"goods_thumb":"upload//20171121/151123001399701.png","goods_name":"乐福来原味烟鸭胸  200g-260g/包","goods_attr":[{"key":"规格","value":"200g-260g/包10kg/件"}],"goods_price":"187.00","cart_number":1},{"goods_thumb":"upload/201607/thumb_img/34654_thumb_G_1467683572486.jpg","goods_name":"六和爆浆鸡排  1.1kg*8包/件 ","goods_attr":[{"key":"规格","value":"1.1kg*8包/件 "}],"goods_price":"253.00","cart_number":1}]}]
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
         * order_id : 6140
         * order_sn : GL20181203095048088374
         * add_time : 1543801848
         * order_status : 5
         * pay_status : 8
         * shipping_status : 2
         * order_amount : 440.00
         * goods_res : [{"goods_thumb":"upload//20171121/151123001399701.png","goods_name":"乐福来原味烟鸭胸  200g-260g/包","goods_attr":[{"key":"规格","value":"200g-260g/包10kg/件"}],"goods_price":"187.00","cart_number":1},{"goods_thumb":"upload/201607/thumb_img/34654_thumb_G_1467683572486.jpg","goods_name":"六和爆浆鸡排  1.1kg*8包/件 ","goods_attr":[{"key":"规格","value":"1.1kg*8包/件 "}],"goods_price":"253.00","cart_number":1}]
         */

        private int order_id;
        private String order_sn;
        private int add_time;
        private int order_status;
        private int pay_status;
        private int shipping_status;
        private String order_amount;
        private List<GoodsResBean> goods_res;

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public int getPay_status() {
            return pay_status;
        }

        public void setPay_status(int pay_status) {
            this.pay_status = pay_status;
        }

        public int getShipping_status() {
            return shipping_status;
        }

        public void setShipping_status(int shipping_status) {
            this.shipping_status = shipping_status;
        }

        public String getOrder_amount() {
            return order_amount;
        }

        public void setOrder_amount(String order_amount) {
            this.order_amount = order_amount;
        }

        public List<GoodsResBean> getGoods_res() {
            return goods_res;
        }

        public void setGoods_res(List<GoodsResBean> goods_res) {
            this.goods_res = goods_res;
        }

        public static class GoodsResBean {
            /**
             * goods_thumb : upload//20171121/151123001399701.png
             * goods_name : 乐福来原味烟鸭胸  200g-260g/包
             * goods_attr : [{"key":"规格","value":"200g-260g/包10kg/件"}]
             * goods_price : 187.00
             * cart_number : 1
             */

            private String goods_thumb;
            private String goods_name;
            private String goods_price;
            private int cart_number;
            private List<GoodsAttrBean> goods_attr;

            public String getGoods_thumb() {
                return goods_thumb;
            }

            public void setGoods_thumb(String goods_thumb) {
                this.goods_thumb = goods_thumb;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public int getCart_number() {
                return cart_number;
            }

            public void setCart_number(int cart_number) {
                this.cart_number = cart_number;
            }

            public List<GoodsAttrBean> getGoods_attr() {
                return goods_attr;
            }

            public void setGoods_attr(List<GoodsAttrBean> goods_attr) {
                this.goods_attr = goods_attr;
            }

            public static class GoodsAttrBean {
                /**
                 * key : 规格
                 * value : 200g-260g/包10kg/件
                 */

                private String key;
                private String value;

                public String getKey() {
                    return key;
                }

                public void setKey(String key) {
                    this.key = key;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
        }
    }
}
