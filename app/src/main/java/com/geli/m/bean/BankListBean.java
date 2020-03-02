package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * Created by Steam_l on 2018/3/24.
 */

public class BankListBean {
    /**
     * code : 100
     * data : [{"union_pay_number":"6217857000069329595","fee":"0.10%","card_type":"借记卡","add_time":"2018-03-24 15:48","bank_category_number":"中国银行"}]
     * message : 1
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

    public static class DataEntity implements Parcelable {
        /**
         * union_pay_number : 6217857000069329595
         * fee : 0.10%
         * card_type : 借记卡
         * add_time : 2018-03-24 15:48
         * bank_category_number : 中国银行
         */
        private String union_pay_number;
        private String fee;
        private String card_type;
        private String add_time;
        private String bank_category_number;
        private String bk_id;
        private String bank_img;


        protected DataEntity(Parcel in) {
            union_pay_number = in.readString();
            fee = in.readString();
            card_type = in.readString();
            add_time = in.readString();
            bank_category_number = in.readString();
            bk_id = in.readString();
            bank_img = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(union_pay_number);
            dest.writeString(fee);
            dest.writeString(card_type);
            dest.writeString(add_time);
            dest.writeString(bank_category_number);
            dest.writeString(bk_id);
            dest.writeString(bank_img);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<DataEntity> CREATOR = new Creator<DataEntity>() {
            @Override
            public DataEntity createFromParcel(Parcel in) {
                return new DataEntity(in);
            }

            @Override
            public DataEntity[] newArray(int size) {
                return new DataEntity[size];
            }
        };

        public void setUnion_pay_number(String union_pay_number) {
            this.union_pay_number = union_pay_number;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public void setCard_type(String card_type) {
            this.card_type = card_type;
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

        public String getFee() {
            return fee;
        }

        public String getCard_type() {
            return card_type;
        }

        public String getAdd_time() {
            return add_time;
        }

        public String getBank_category_number() {
            return bank_category_number;
        }

        public String getBk_id() {
            return bk_id;
        }

        public void setBk_id(String bk_id) {
            this.bk_id = bk_id;
        }

        public String getBank_img() {
            return bank_img;
        }

        public void setBank_img(String bank_img) {
            this.bank_img = bank_img;
        }
    }
}
