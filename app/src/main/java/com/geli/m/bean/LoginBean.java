package com.geli.m.bean;

import java.io.Serializable;

/**
 * Created by Labor on 2017/5/22.
 */

public class LoginBean implements Serializable {
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

    @Override
    public String toString() {
        return "LoginMessage{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        private String user_id;
        private String phone;
        private String ry_token;
        private boolean pay_pwd;//是否设置了支付密码

        public boolean isPay_pwd() {
            return pay_pwd;
        }

        public void setPay_pwd(boolean pay_pwd) {
            this.pay_pwd = pay_pwd;
        }

        public String getRy_token() {
            return ry_token;
        }

        public void setRy_token(String ry_token) {
            this.ry_token = ry_token;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "user_id='" + user_id + '\'' +
                    ", phone='" + phone + '\'' +
                    ", ry_token='" + ry_token + '\'' +
                    ", pay_pwd=" + pay_pwd +
                    '}';
        }
    }
}
