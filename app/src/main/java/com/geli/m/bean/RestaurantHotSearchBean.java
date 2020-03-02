package com.geli.m.bean;

import java.util.List;

/**
 * author:  shen
 * date:    2019/1/14
 *
 * 食品馆热门搜索关键字
 */
public class RestaurantHotSearchBean {

    /**
     * code : 100
     * message : ok
     * data : [{"id":1,"keywords":"测试","scount":3}]
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
         * id : 1
         * keywords : 测试
         * scount : 3
         */

        private int id;
        private String keywords;
        private int scount;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public int getScount() {
            return scount;
        }

        public void setScount(int scount) {
            this.scount = scount;
        }
    }
}
