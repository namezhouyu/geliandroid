package com.geli.m.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Steam_l on 2018/3/24.
 */

public class BankDetailsBean {

    /**
     * code : 100
     * data : {"union_pay_number":"6217857000069329595","bk_id":29,"fee":"0.10%","telephone":"95566","card_type":"借记卡","default":0,"bank_phone":13726595135,"user_id":7217,"bank_name":"","card_name":"借记IC个人普卡","name":"刘彬","add_time":"2018-03-24 15:48","bank_category_number":"中国银行"}
     * message : 1
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
         * union_pay_number : 6217857000069329595
         * bk_id : 29
         * fee : 0.10%
         * telephone : 95566
         * card_type : 借记卡
         * default : 0
         * bank_phone : 13726595135
         * user_id : 7217
         * bank_name :
         * card_name : 借记IC个人普卡
         * name : 刘彬
         * add_time : 2018-03-24 15:48
         * bank_category_number : 中国银行
         */
        private String union_pay_number;
        private int bk_id;
        private String fee;
        private String telephone;
        private String card_type;
        @SerializedName("default")
        private int defaultX;
        private String bank_phone;
        private int user_id;
        private String bank_name;
        private String card_name;
        private String name;
        private String add_time;
        private String bank_category_number;

        public void setUnion_pay_number(String union_pay_number) {
            this.union_pay_number = union_pay_number;
        }

        public void setBk_id(int bk_id) {
            this.bk_id = bk_id;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public void setCard_type(String card_type) {
            this.card_type = card_type;
        }

        public void setDefaultX(int defaultX) {
            this.defaultX = defaultX;
        }

        public void setBank_phone(String bank_phone) {
            this.bank_phone = bank_phone;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public void setCard_name(String card_name) {
            this.card_name = card_name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public void setBank_category_number(String bank_category_number) {
            this.bank_category_number = bank_category_number;
        }

        public String getUnion_pay_number() {
            return union_pay_number;
        }

        public int getBk_id() {
            return bk_id;
        }

        public String getFee() {
            return fee;
        }

        public String getTelephone() {
            return telephone;
        }

        public String getCard_type() {
            return card_type;
        }

        public int getDefaultX() {
            return defaultX;
        }

        public String getBank_phone() {
            return bank_phone;
        }

        public int getUser_id() {
            return user_id;
        }

        public String getBank_name() {
            return bank_name;
        }

        public String getCard_name() {
            return card_name;
        }

        public String getName() {
            return name;
        }

        public String getAdd_time() {
            return add_time;
        }

        public String getBank_category_number() {
            return bank_category_number;
        }
    }
}
