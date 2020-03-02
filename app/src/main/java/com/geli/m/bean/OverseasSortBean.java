package com.geli.m.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/23.
 * 国家对应的分类
 */

public class OverseasSortBean {
    /**
     * code : 100
     * countries_id : 0
     * data : [{"cat_img":"upload/category/c1508208667.png","cat_name":"鱼制品","cat_id":34},{"cat_img":"upload/category/20170608/b667c51061343c873ee2ed930cb3d0da.jpg","cat_name":"粮油类","cat_id":306},{"cat_img":"upload/category/c1509976881.png","cat_name":"肉类","cat_id":482},{"cat_img":"upload/category/c1509976940.png","cat_name":"水产类","cat_id":483}]
     * message : ok
     */
    private int code;
    private int countries_id;
    private List<DataEntity> data;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setCountries_id(int countries_id) {
        this.countries_id = countries_id;
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

    public int getCountries_id() {
        return countries_id;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class DataEntity {
        /**
         * cat_img : upload/category/c1508208667.png
         * cat_name : 鱼制品
         * cat_id : 34
         */
        private String cat_img;
        private String cat_name;
        private int cat_id;

        public void setCat_img(String cat_img) {
            this.cat_img = cat_img;
        }

        public void setCat_name(String cat_name) {
            this.cat_name = cat_name;
        }

        public void setCat_id(int cat_id) {
            this.cat_id = cat_id;
        }

        public String getCat_img() {
            return cat_img;
        }

        public String getCat_name() {
            return cat_name;
        }

        public int getCat_id() {
            return cat_id;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof DataEntity) {
                return this.cat_id == ((DataEntity) obj).cat_id;
            }
            return super.equals(obj);
        }
    }
}
