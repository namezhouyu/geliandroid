package com.geli.m.bean;

import java.util.List;

/**
 * Created by Steam_l on 2018/1/9.
 */

public class RetailCenterBean {
    /**
     * code : 100
     * data : {}
     * message : ok
     */
    private int code;
    private List<FactoryBean> data;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(List<FactoryBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public List<FactoryBean> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

}
