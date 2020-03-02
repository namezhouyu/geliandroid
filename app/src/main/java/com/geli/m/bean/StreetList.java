package com.geli.m.bean;

import java.util.List;

/**
 * Name:
 * Author:pks
 * Date: 2017-09-25 18:56
 */

public class StreetList {


    /**
     * code : 100
     * message : 获取成功
     * data : [{"area_id":5,"code":"110101002","area_name":"景山街道"}]
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
         * area_id : 5
         * code : 110101002
         * area_name : 景山街道
         */

        private int area_id;
        private String code;
        private String area_name;

        public int getArea_id() {
            return area_id;
        }

        public void setArea_id(int area_id) {
            this.area_id = area_id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }
    }
}
