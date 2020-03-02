package com.geli.m.bean.base;

import java.util.List;

/**
 * Created by Steam_l on 2018/1/9.
 */

public class MyListBaseBean {
    private int code;
    private String message;
    private List<String> data;
    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
    public List<String> getData() {
        return data;
    }
}
