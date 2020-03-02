package com.geli.m.bean;

import java.io.Serializable;

/**
 * Created by mrCTang on 2017/5/22.
 */

public class GetCodeBean implements Serializable {

    /**
     * code : 100
     * message : null
     * data : 6419
     */

    private int code;
    private Object message;
    private String data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
