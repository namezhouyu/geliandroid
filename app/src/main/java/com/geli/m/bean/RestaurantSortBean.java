package com.geli.m.bean;

import java.util.List;

/**
 * author:  shen
 * date:    2019/7/30
 */
public class RestaurantSortBean {

    /**
     * code : 100
     * message : ok
     * data : [{"type_id":1,"type_name":"水产"}]
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
         * type_id : 1
         * type_name : 水产
         */

        private int type_id;
        private String type_name;

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }
    }
}
