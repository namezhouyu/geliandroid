package com.geli.m.bean;

import android.text.TextUtils;
import java.util.ArrayList;

/**
 * Created by Steam_l on 2018/1/29.
 */

public class OrderDetailsBean {

    /**
     * code : 100
     * data : {"address_info":{"country":1,"consignee":"阿姨","address":"hdhdh","city":"长治市","address_id":561,"mobile":"13435026831","is_default":1,"zipcode":"","province":"山西","user_id":239,"street":null,"district":"长治县","tel":""},"order_invoice":{"invoice_img":"","belong":"单位","downloads":0,"invoice_id":64,"invoice_type":"纸质发票","type":"增值税专用发票","invoice_status":"已开发票"},"order_info":{"receipt_time":0,"sum_amount":"523.00","discount_amount":"0.00","address_id":561,"shipping_type":1,"shop_name":"格利食品网","shipping_status":0,"pay_time":0,"shipping_fee":"0.00","order_status":1,"pay_status":0,"invoice_img":"","goods_list":[{"goods_subtotal":"23.00","goods_name":"霸王","goods_intro":"","goods_price":"23.00","goods_thumb":"","cart_number":1,"goods_id":5074,"logistics":"0.00","goods_attr":[{"value":"20个/包","key":"规格"},{"value":"辣","key":"口味"}],"goods_type":1,"order_id":4608,"evaluate":1},{"goods_subtotal":"500.00","goods_name":"测试商品5","goods_intro":"","goods_price":"100.00","goods_thumb":"","cart_number":5,"goods_id":5071,"logistics":"0.00","goods_attr":[{"value":"100g/包","key":"规格"},{"value":"香","key":"test1"}],"goods_type":1,"order_id":4608,"evaluate":1}],"shop_str":"1","is_comment":0,"invoice_id":64,"pay_type":3,"proof":1,"order_id":4608,"add_time":1517197547,"shipping_time":0,"is_pay":1,"order_sn":"GL20180129114547097420"}}
     * message : ok
     */
    private int code;
    private DataEntity data;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class DataEntity {
        /**
         * address_info : {"country":1,"consignee":"阿姨","address":"hdhdh","city":"长治市","address_id":561,"mobile":"13435026831","is_default":1,"zipcode":"","province":"山西","user_id":239,"street":null,"district":"长治县","tel":""}
         * order_invoice : {"invoice_img":"","belong":"单位","downloads":0,"invoice_id":64,"invoice_type":"纸质发票","type":"增值税专用发票","invoice_status":"已开发票"}
         * order_info : {"receipt_time":0,"sum_amount":"523.00","discount_amount":"0.00","address_id":561,"shipping_type":1,"shop_name":"格利食品网","shipping_status":0,"pay_time":0,"shipping_fee":"0.00","order_status":1,"pay_status":0,"invoice_img":"","goods_list":[{"goods_subtotal":"23.00","goods_name":"霸王","goods_intro":"","goods_price":"23.00","goods_thumb":"","cart_number":1,"goods_id":5074,"logistics":"0.00","goods_attr":[{"value":"20个/包","key":"规格"},{"value":"辣","key":"口味"}],"goods_type":1,"order_id":4608,"evaluate":1},{"goods_subtotal":"500.00","goods_name":"测试商品5","goods_intro":"","goods_price":"100.00","goods_thumb":"","cart_number":5,"goods_id":5071,"logistics":"0.00","goods_attr":[{"value":"100g/包","key":"规格"},{"value":"香","key":"test1"}],"goods_type":1,"order_id":4608,"evaluate":1}],"shop_str":"1","is_comment":0,"invoice_id":64,"pay_type":3,"proof":1,"order_id":4608,"add_time":1517197547,"shipping_time":0,"is_pay":1,"order_sn":"GL20180129114547097420"}
         */
        private AddressInfoEntity address_info;
        private OrderInvoiceEntity order_invoice;
        private OrderInfoEntity order_info;

        public void setAddress_info(AddressInfoEntity address_info) {
            this.address_info = address_info;
        }

        public void setOrder_invoice(OrderInvoiceEntity order_invoice) {
            this.order_invoice = order_invoice;
        }

        public void setOrder_info(OrderInfoEntity order_info) {
            this.order_info = order_info;
        }

        public AddressInfoEntity getAddress_info() {
            return address_info;
        }

        public OrderInvoiceEntity getOrder_invoice() {
            return order_invoice;
        }

        public OrderInfoEntity getOrder_info() {
            return order_info;
        }

        public static class AddressInfoEntity {
            /**
             * country : 1
             * consignee : 阿姨
             * address : hdhdh
             * city : 长治市
             * address_id : 561
             * mobile : 13435026831
             * is_default : 1
             * zipcode :
             * province : 山西
             * user_id : 239
             * street : null
             * district : 长治县
             * tel :
             */
            private int country;
            private String consignee;
            private String address;
            private String city;
            private int address_id;
            private String mobile;
            private int is_default;
            private String zipcode;
            private String province;
            private int user_id;
            private String street;
            private String district;
            private String tel;

            public String getAdd() {
                String address = "";
                if (!TextUtils.isEmpty(city)) {
                    address += city;
                    if (!TextUtils.isEmpty(province)) {
                        address += city;
                        if (!TextUtils.isEmpty(district)) {
                            address += district;
                            if (!TextUtils.isEmpty(this.address)) {
                                address += this.address;
                            }
                        }
                    }
                }
                return address;
            }

            public void setCountry(int country) {
                this.country = country;
            }

            public void setConsignee(String consignee) {
                this.consignee = consignee;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public void setAddress_id(int address_id) {
                this.address_id = address_id;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public void setIs_default(int is_default) {
                this.is_default = is_default;
            }

            public void setZipcode(String zipcode) {
                this.zipcode = zipcode;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public int getCountry() {
                return country;
            }

            public String getConsignee() {
                return consignee;
            }

            public String getAddress() {
                return address;
            }

            public String getCity() {
                return city;
            }

            public int getAddress_id() {
                return address_id;
            }

            public String getMobile() {
                return mobile;
            }

            public int getIs_default() {
                return is_default;
            }

            public String getZipcode() {
                return zipcode;
            }

            public String getProvince() {
                return province;
            }

            public int getUser_id() {
                return user_id;
            }

            public String getStreet() {
                return street;
            }

            public String getDistrict() {
                return district;
            }

            public String getTel() {
                return tel;
            }
        }

        public static class OrderInvoiceEntity {
            /**
             * invoice_img :
             * belong : 单位
             * downloads : 0
             * invoice_id : 64
             * invoice_type : 纸质发票
             * type : 增值税专用发票
             * invoice_status : 已开发票
             */
            private String invoice_img;
            private String belong;
            private int downloads;
            private int invoice_id;
            private String invoice_type;
            private String type;
            private String invoice_status;

            public void setInvoice_img(String invoice_img) {
                this.invoice_img = invoice_img;
            }

            public void setBelong(String belong) {
                this.belong = belong;
            }

            public void setDownloads(int downloads) {
                this.downloads = downloads;
            }

            public void setInvoice_id(int invoice_id) {
                this.invoice_id = invoice_id;
            }

            public void setInvoice_type(String invoice_type) {
                this.invoice_type = invoice_type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setInvoice_status(String invoice_status) {
                this.invoice_status = invoice_status;
            }

            public String getInvoice_img() {
                return invoice_img;
            }

            public String getBelong() {
                return belong;
            }

            public int getDownloads() {
                return downloads;
            }

            public int getInvoice_id() {
                return invoice_id;
            }

            public String getInvoice_type() {
                return invoice_type;
            }

            public String getType() {
                return type;
            }

            public String getInvoice_status() {
                return invoice_status;
            }
        }

        public static class OrderInfoEntity {
            /**
             * receipt_time : 0
             * sum_amount : 1569.42
             * discount_amount : 100.00
             * shipping_type : 1
             * order_status : 1
             * delivery_fee : 300.00
             * pay_status : 0
             * invoice_img :
             * goods_list : [{"goods_subtotal":"128.00","goods_name":"佳尚新蔬 速冻蔬菜 速冻南瓜泥500g*20包/箱","goods_intro":"500g* 20包/箱","goods_price":"128.00","goods_thumb":"upload/old_img/store_100008/goods_141/small_201504291339019926.jpg","cart_number":1,"goods_id":114,"logistics":"0.00","goods_attr":[{"value":"默认","key":"规格"}],"goods_type":1,"order_id":5313},{"goods_subtotal":"80.00","goods_name":"佳尚新蔬 速冻蔬菜 速冻青梗菜500g*14包/箱","goods_intro":"500g*14包/箱","goods_price":"80.00","goods_thumb":"upload/old_img/store_100008/goods_13/small_201504291343336606.jpg","cart_number":1,"goods_id":116,"logistics":"0.00","goods_attr":[{"value":"默认","key":"规格"}],"goods_type":1,"order_id":5313},{"goods_subtotal":"98.00","goods_name":"佳尚新蔬 青豆 500g*20/件 ","goods_intro":"","goods_price":"98.00","goods_thumb":"upload/201703/thumb_img/35074_thumb_G_1490604401697.jpg","cart_number":1,"goods_id":1755,"logistics":"0.00","goods_attr":[{"value":"500G/包","key":"规格"},{"value":"酸味","key":"口味"}],"goods_type":1,"order_id":5313}]
             * order_amount : 306.00
             * shop_str : 100008
             * is_comment : 0
             * invoice_id : 0
             * pay_type : 3
             * proof : 1
             * shipping_time : 0
             * address_id : 958
             * shop_name : 佳尚新蔬食品
             * shipping_status : 0
             * pay_time : 0
             * shipping_fee : 1063.42
             * logistics_price : 1363
             * order_id : 5313
             * add_time : 1520923116
             * is_pay : 1
             * order_sn : GL20180313143836052937
             */
            private int receipt_time;
            private String shop_tel;
            private String service_tel;
            private String sum_amount;
            private String discount_amount;
            private int shipping_type;
            private int order_status;
            private String delivery_fee;
            private int pay_status;
            private String invoice_img;
            private ArrayList<OrderListBean.DataEntity.GoodsListEntity> goods_list;
            private String order_amount;
            private String shop_str;
            private int is_comment;
            private int invoice_id;
            private int pay_type;
            private int proof;
            private int shipping_time;
            private int address_id;
            private String shop_name;
            private int shipping_status;
            private int pay_time;
            private String shipping_fee;
            //private String logistics_price;
            private int order_id;
            private int add_time;
            private int is_pay;
            private String order_sn;
            private String order_type;
            private String per_cent;
            private String seventy_percent;
            private int percentage;
            private int after_sold_status;   // 售后状态 0:申请售后;1:待受理;2:受理中;3:售后完成

            private int is_sh;               // 0:非账期 1：账期

            private String logistics_price;     // 运费
            private int logistics_is_pay;       // 0：需要支付运费 1：已支付运费/不用支付运费

            public String[] getState() {
                return new String[]{order_status + "", pay_status + "", shipping_status + ""};
            }

            public void setReceipt_time(int receipt_time) {
                this.receipt_time = receipt_time;
            }

            public void setSum_amount(String sum_amount) {
                this.sum_amount = sum_amount;
            }

            public void setDiscount_amount(String discount_amount) {
                this.discount_amount = discount_amount;
            }

            public void setShipping_type(int shipping_type) {
                this.shipping_type = shipping_type;
            }

            public void setOrder_status(int order_status) {
                this.order_status = order_status;
            }

            public void setDelivery_fee(String delivery_fee) {
                this.delivery_fee = delivery_fee;
            }

            public void setPay_status(int pay_status) {
                this.pay_status = pay_status;
            }

            public void setInvoice_img(String invoice_img) {
                this.invoice_img = invoice_img;
            }

            public void setGoods_list(ArrayList<OrderListBean.DataEntity.GoodsListEntity> goods_list) {
                this.goods_list = goods_list;
            }

            public void setOrder_amount(String order_amount) {
                this.order_amount = order_amount;
            }

            public void setShop_str(String shop_str) {
                this.shop_str = shop_str;
            }

            public void setIs_comment(int is_comment) {
                this.is_comment = is_comment;
            }

            public void setInvoice_id(int invoice_id) {
                this.invoice_id = invoice_id;
            }

            public void setPay_type(int pay_type) {
                this.pay_type = pay_type;
            }

            public void setProof(int proof) {
                this.proof = proof;
            }

            public void setShipping_time(int shipping_time) {
                this.shipping_time = shipping_time;
            }

            public void setAddress_id(int address_id) {
                this.address_id = address_id;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }

            public void setShipping_status(int shipping_status) {
                this.shipping_status = shipping_status;
            }

            public void setPay_time(int pay_time) {
                this.pay_time = pay_time;
            }

            public void setShipping_fee(String shipping_fee) {
                this.shipping_fee = shipping_fee;
            }

            public void setLogistics_price(String logistics_price) {
                this.logistics_price = logistics_price;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public void setAdd_time(int add_time) {
                this.add_time = add_time;
            }

            public void setIs_pay(int is_pay) {
                this.is_pay = is_pay;
            }

            public void setOrder_sn(String order_sn) {
                this.order_sn = order_sn;
            }

            public int getReceipt_time() {
                return receipt_time;
            }

            public String getSum_amount() {
                return sum_amount;
            }

            public String getDiscount_amount() {
                return discount_amount;
            }

            public int getShipping_type() {
                return shipping_type;
            }

            public int getOrder_status() {
                return order_status;
            }

            public String getDelivery_fee() {
                return delivery_fee;
            }

            public int getPay_status() {
                return pay_status;
            }

            public String getInvoice_img() {
                return invoice_img;
            }

            public ArrayList<OrderListBean.DataEntity.GoodsListEntity> getGoods_list() {
                return goods_list;
            }

            public String getOrder_amount() {
                return order_amount;
            }

            public String getShop_str() {
                return shop_str;
            }

            public int getIs_comment() {
                return is_comment;
            }

            public int getInvoice_id() {
                return invoice_id;
            }

            public int getPay_type() {
                return pay_type;
            }

            public int getProof() {
                return proof;
            }

            public int getShipping_time() {
                return shipping_time;
            }

            public int getAddress_id() {
                return address_id;
            }

            public String getShop_name() {
                return shop_name;
            }

            public int getShipping_status() {
                return shipping_status;
            }

            public int getPay_time() {
                return pay_time;
            }

            public String getShipping_fee() {
                return shipping_fee;
            }

            public String getLogistics_price() {
                return logistics_price;
            }

            public int getOrder_id() {
                return order_id;
            }

            public int getAdd_time() {
                return add_time;
            }

            public int getIs_pay() {
                return is_pay;
            }

            public String getOrder_sn() {
                return order_sn;
            }

            public String getOrder_type() {
                return order_type;
            }

            public void setOrder_type(String order_type) {
                this.order_type = order_type;
            }

            public String getPer_cent() {
                return per_cent;
            }

            public void setPer_cent(String per_cent) {
                this.per_cent = per_cent;
            }

            public String getSeventy_percent() {
                return seventy_percent;
            }

            public void setSeventy_percent(String seventy_percent) {
                this.seventy_percent = seventy_percent;
            }

            public int getPercentage() {
                return percentage;
            }

            public void setPercentage(int percentage) {
                this.percentage = percentage;
            }

            public String getShop_tel() {
                return shop_tel;
            }

            public void setShop_tel(String shop_tel) {
                this.shop_tel = shop_tel;
            }

            public String getService_tel() {
                return service_tel;
            }

            public void setService_tel(String service_tel) {
                this.service_tel = service_tel;
            }

            public int getAfter_sold_status() {
                return after_sold_status;
            }

            public void setAfter_sold_status(int after_sold_status) {
                this.after_sold_status = after_sold_status;
            }

            public int getIs_sh() {
                return is_sh;
            }

            public void setIs_sh(int is_sh) {
                this.is_sh = is_sh;
            }

            public int getLogistics_is_pay() {
                return logistics_is_pay;
            }

            public void setLogistics_is_pay(int logistics_is_pay) {
                this.logistics_is_pay = logistics_is_pay;
            }
        }
    }
}
