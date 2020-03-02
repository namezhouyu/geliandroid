package com.geli.m.bean;

/**
 * author:  shen
 * date:    2019/1/29
 *
 * isGeLiPay 接口
 */
public class BalanceBean {

    /**
     * code : 100
     * message : ok
     * data : {"balance":"0.91"}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * balance : 0.91
         */

        private String balance;         // 余额

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }
    }
}
