package com.geli.m.bean;

/**
 * Created by Steam_l on 2018/3/8.
 */

public class WalletPayBean {
    /**
     * code : 100
     * data : {"user_money":"0.00","sum_amount":1612.4199800000001,"order_id":"4759"}
     * message : 提交订单成功
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
         * user_money : 0.00
         * sum_amount : 1612.4199800000001
         * order_id : 4759
         */
        private String user_money;
        private double sum_amount;
        private String order_id;

        public void setUser_money(String user_money) {
            this.user_money = user_money;
        }

        public void setSum_amount(double sum_amount) {
            this.sum_amount = sum_amount;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getUser_money() {
            return user_money;
        }

        public double getSum_amount() {
            return sum_amount;
        }

        public String getOrder_id() {
            return order_id;
        }
    }
}
