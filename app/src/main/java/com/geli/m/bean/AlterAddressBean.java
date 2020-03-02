package com.geli.m.bean;

import java.io.Serializable;

/**
 * Created by mrCTang on 2017/6/14.
 */

public class AlterAddressBean implements Serializable {

    /**
     * code : 100
     * message : ok
     * data : {"address_id":1011,"consignee":"主席","mobile":"14381438143","province":"2","city":"3","district":"4","address":"哈哈还","zipcode":"","tel":"","is_default":1}
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

    public static class DataBean {
        /**
         * address_id : 1011
         * consignee : 主席
         * mobile : 14381438143
         * province : 2
         * city : 3
         * district : 4
         * address : 哈哈还
         * zipcode :
         * tel :
         * is_default : 1
         */

        private int address_id;
        private String consignee;
        private String mobile;
        private String province;
        private String city;
        private String district;
        private String address;
        private String zipcode;
        private String tel;
        private int is_default;
        private int street;

        public int getStreet() {
            return street;
        }

        public void setStreet(int street) {
            this.street = street;
        }

        public int getAddress_id() {
            return address_id;
        }

        public void setAddress_id(int address_id) {
            this.address_id = address_id;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }
    }
}
