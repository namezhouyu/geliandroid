package com.geli.m.bean;

import java.util.List;

/**
 * Created by l on 2018/4/4.
 */

public class FindCatBean {
    /**
     * code : 100
     * data : [{"cat_name":"精选","cat_id":1,"status":1},{"cat_name":"菜品","cat_id":2,"status":1},{"cat_name":"展会","cat_id":3,"status":1},{"cat_name":"餐饮","cat_id":4,"status":1},{"cat_name":"批发商","cat_id":5,"status":1},{"cat_name":"行业资讯","cat_id":6,"status":1}]
     * message : ok
     */
    private int code;
    private List<DataEntity> data;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class DataEntity {
        /**
         * cat_name : 精选
         * cat_id : 1
         * status : 1
         */
        private String cat_name;
        private int cat_id;
        private int status;

        public void setCat_name(String cat_name) {
            this.cat_name = cat_name;
        }

        public void setCat_id(int cat_id) {
            this.cat_id = cat_id;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCat_name() {
            return cat_name;
        }

        public int getCat_id() {
            return cat_id;
        }

        public int getStatus() {
            return status;
        }
    }
}
