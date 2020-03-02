package com.geli.m.bean;

/**
 * Created by Steam_l on 2018/1/9.
 */

public class BaseBean {
    public String message;
    public int code;
    public String data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
