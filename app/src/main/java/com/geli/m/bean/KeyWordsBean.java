package com.geli.m.bean;

import java.util.List;

/**
 * Created by Steam_l on 2018/3/30.
 */

public class KeyWordsBean {
    /**
     * code : 100
     * data : [{"kw_id":30,"search_number":15,"keyword":"fsdfsd"},{"kw_id":10,"search_number":6,"keyword":"狗"},{"kw_id":25,"search_number":5,"keyword":"羊肉"},{"kw_id":12,"search_number":4,"keyword":"牛肉"},{"kw_id":20,"search_number":3,"keyword":"零食"},{"kw_id":23,"search_number":3,"keyword":"牛排"},{"kw_id":24,"search_number":2,"keyword":"牛奶"},{"kw_id":17,"search_number":2,"keyword":"猪蹄"},{"kw_id":26,"search_number":1,"keyword":"鸡肉"},{"kw_id":28,"search_number":1,"keyword":"毛血旺"}]
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
         * kw_id : 30
         * search_number : 15
         * keyword : fsdfsd
         */
        private int kw_id;
        private int search_number;
        private String keyword;

        public void setKw_id(int kw_id) {
            this.kw_id = kw_id;
        }

        public void setSearch_number(int search_number) {
            this.search_number = search_number;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public int getKw_id() {
            return kw_id;
        }

        public int getSearch_number() {
            return search_number;
        }

        public String getKeyword() {
            return keyword;
        }
    }
}
