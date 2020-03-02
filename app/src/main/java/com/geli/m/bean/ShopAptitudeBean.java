package com.geli.m.bean;

import java.util.List;

/**
 * Created by Steam_l on 2018/3/29.
 */

public class ShopAptitudeBean {
    /**
     * code : 100
     * data : {"shops_id":100558,"aptitude_id":2}
     * message : ok
     */
    private int code;
    private DataEntity data;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class DataEntity {
        /**
         * shops_id : 100558
         * aptitude_id : 2
         */
        private int shops_id;
        private int aptitude_id;
        private List<String> img;

        public void setShops_id(int shops_id) {
            this.shops_id = shops_id;
        }

        public void setAptitude_id(int aptitude_id) {
            this.aptitude_id = aptitude_id;
        }

        public int getShops_id() {
            return shops_id;
        }

        public int getAptitude_id() {
            return aptitude_id;
        }

        public List<String> getImg() {
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }
    }
}
