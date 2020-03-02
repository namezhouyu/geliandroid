package com.geli.m.bean;

import java.io.Serializable;

/**
 * Name:
 * Author:pks
 * Date: 2017-09-23 16:13
 */

public class NewAddressBean implements Serializable {


    /**
     * code : 100
     * message : ok
     * data : {"user_id":250,"consignee":"老莫好","province":"2","city":"3","district":"4","zipcode":"","mobile":"13104851701","tel":"","address":"啊啊啊","is_default":0,"address_id":696}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * user_id : 250
         * consignee : 老莫好
         * province : 2
         * city : 3
         * district : 4
         * zipcode :
         * mobile : 13104851701
         * tel :
         * address : 啊啊啊
         * is_default : 0
         * address_id : 696
         */

        private int user_id;
        private String consignee;
        private String province;
        private String city;
        private String district;
        private String zipcode;
        private String mobile;
        private String tel;
        private String address;
        private String all_address;//省市区街 拼接 起来的 路径
        private int is_default;
        private int address_id;

        public String getAll_address() {
            return all_address;
        }

        public void setAll_address(String all_address) {
            this.all_address = all_address;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }

        public int getAddress_id() {
            return address_id;
        }

        public void setAddress_id(int address_id) {
            this.address_id = address_id;
        }
    }
}
