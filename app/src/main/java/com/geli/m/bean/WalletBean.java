package com.geli.m.bean;

/**
 * Created by Steam_l on 2018/3/26.
 */

public class WalletBean {
    /**
     * code : 100
     * data : {"user_money":"9861797.98"}
     * message : 1
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
         * user_money : 9861797.98
         */
        private String user_money;

        public void setUser_money(String user_money) {
            this.user_money = user_money;
        }

        public String getUser_money() {
            return user_money;
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "user_money='" + user_money + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WalletBean{" +
                "code=" + code +
                ", data=" + data.toString() +
                ", message='" + message + '\'' +
                '}';
    }
}
