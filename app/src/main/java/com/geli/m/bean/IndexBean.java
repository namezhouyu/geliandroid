package com.geli.m.bean;

import com.geli.m.bean.base.IndexBaseBean;
import java.util.List;

/**
 * Created by Steam_l on 2018/3/23.
 */

public class IndexBean {
    private int code;
    private String message;
    private List<IndexBaseBean> allList;

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

    public List<IndexBaseBean> getAllList() {
        return allList;
    }

    public void setAllList(List<IndexBaseBean> allList) {
        this.allList = allList;
    }
}
