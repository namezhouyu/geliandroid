package com.geli.m.bean;

import java.util.List;

/**
 * author:  shen
 * date:    2019/1/2
 */
public class InvoiceRecordBean {

    /**
     * code : 100
     * message : ok
     * data : [{"invoice_id":1,"add_time":1508225612,"invoice_type":1,"type":1,"status":0,"is_merge":0,"sum_amount":null,"shop_name":null},{"invoice_id":9,"add_time":1508225612,"invoice_type":1,"type":1,"status":0,"is_merge":0,"sum_amount":null,"shop_name":null},{"invoice_id":10,"add_time":1508225612,"invoice_type":1,"type":1,"status":0,"is_merge":0,"sum_amount":null,"shop_name":null},{"invoice_id":34,"add_time":1508225612,"invoice_type":1,"type":1,"status":0,"is_merge":0,"sum_amount":null,"shop_name":null},{"invoice_id":67,"add_time":1516869274,"invoice_type":2,"type":2,"status":0,"is_merge":0,"sum_amount":null,"shop_name":null},{"invoice_id":68,"add_time":1516869274,"invoice_type":2,"type":2,"status":0,"is_merge":0,"sum_amount":null,"shop_name":null},{"invoice_id":395,"add_time":1517987376,"invoice_type":2,"type":2,"status":0,"is_merge":0,"sum_amount":null,"shop_name":null},{"invoice_id":403,"add_time":1517987376,"invoice_type":2,"type":2,"status":0,"is_merge":0,"sum_amount":null,"shop_name":null},{"invoice_id":404,"add_time":1517987376,"invoice_type":2,"type":2,"status":0,"is_merge":0,"sum_amount":null,"shop_name":null},{"invoice_id":407,"add_time":1516869274,"invoice_type":1,"type":2,"status":0,"is_merge":0,"sum_amount":null,"shop_name":null},{"invoice_id":408,"add_time":1516869274,"invoice_type":1,"type":2,"status":0,"is_merge":0,"sum_amount":null,"shop_name":null},{"invoice_id":409,"add_time":1516869274,"invoice_type":1,"type":2,"status":0,"is_merge":0,"sum_amount":null,"shop_name":null},{"invoice_id":410,"add_time":1516869274,"invoice_type":1,"type":2,"status":0,"is_merge":0,"sum_amount":null,"shop_name":null},{"invoice_id":413,"add_time":1516869274,"invoice_type":1,"type":2,"status":0,"is_merge":0,"sum_amount":null,"shop_name":null},{"invoice_id":415,"add_time":1516869274,"invoice_type":1,"type":2,"status":0,"is_merge":0,"sum_amount":null,"shop_name":null},{"invoice_id":416,"add_time":1516869274,"invoice_type":1,"type":2,"status":0,"is_merge":0,"sum_amount":null,"shop_name":null},{"invoice_id":417,"add_time":1516869274,"invoice_type":1,"type":2,"status":0,"is_merge":0,"sum_amount":null,"shop_name":null},{"invoice_id":419,"add_time":1516869274,"invoice_type":1,"type":2,"status":0,"is_merge":0,"sum_amount":null,"shop_name":null},{"invoice_id":420,"add_time":1516869274,"invoice_type":1,"type":2,"status":0,"is_merge":0,"sum_amount":null,"shop_name":null},{"invoice_id":421,"add_time":1517987376,"invoice_type":2,"type":2,"status":0,"is_merge":0,"sum_amount":null,"shop_name":null}]
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
         * invoice_id : 1
         * add_time : 1508225612
         * invoice_type : 1
         * type : 1
         * status : 0
         * is_merge : 0
         * sum_amount : null
         * shop_name : null
         *
         "invoice_id":         //发票id
         "add_time":         //申请时间
         "invoice_type":     //1电子发票 2 纸质发票
         "type":         //1增值税专用发票  2增值税普通发票
         "status":      //开票状态(1等待开票2开票成功)
         "is_merge":    //是否为合并开票
         "sum_amount": 发票金额
         "shop_name": 开票商家
         belong     //个人 / 企业
         */

        private int invoice_id;
        private int add_time;
        private int invoice_type;
        private int type;
        private int status;
        private int is_merge;
        private String sum_amount;
        private String shop_name;
        private int belong;

        public int getInvoice_id() {
            return invoice_id;
        }

        public void setInvoice_id(int invoice_id) {
            this.invoice_id = invoice_id;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public int getInvoice_type() {
            return invoice_type;
        }

        public void setInvoice_type(int invoice_type) {
            this.invoice_type = invoice_type;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getIs_merge() {
            return is_merge;
        }

        public void setIs_merge(int is_merge) {
            this.is_merge = is_merge;
        }

        public String getSum_amount() {
            return sum_amount;
        }

        public void setSum_amount(String sum_amount) {
            this.sum_amount = sum_amount;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public int getBelong() {
            return belong;
        }

        public void setBelong(int belong) {
            this.belong = belong;
        }
    }
}
