package com.geli.m.bean;

import java.util.List;

/**
 * author:  shen
 * date:    2018/12/28
 *
 * 可以合并发票的厂家列表
 */
public class ShopInvoiceBean {


    /**
     * code : 100
     * message : ok
     * data : [{"shop_name":"测试批零中心","order_id":"5402,5403,5417,5419,5420,5441","invoice_amount":"0.00"}]
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
         * shop_name : 测试批零中心
         * order_id : 5402,5403,5417,5419,5420,5441
         * invoice_amount : 0.00
         */

        private String shop_name;
        private String order_id;
        private String invoice_amount;

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getInvoice_amount() {
            return invoice_amount;
        }

        public void setInvoice_amount(String invoice_amount) {
            this.invoice_amount = invoice_amount;
        }
    }
}
