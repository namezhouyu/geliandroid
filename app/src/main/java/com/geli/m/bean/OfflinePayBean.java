package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Steam_l on 2018/3/8.
 */

public class OfflinePayBean {
    /**
     * code : 100
     * data : {"sum_amount":"1612.42","bank_name":"广州市尚冻冻电子商务有限公司","shop_name":"广州市尚冻冻电子商务有限公司","bank_account":399330100100018440}
     * message : 提交订单成功
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

    public static class DataEntity implements Parcelable {
        /**
         * sum_amount : 1612.42
         * bank_name : 广州市尚冻冻电子商务有限公司
         * shop_name : 广州市尚冻冻电子商务有限公司
         * bank_account : 399330100100018440
         */
        private String sum_amount;
        private String bank_name;
        private String shop_name;
        private String bank_account;

        protected DataEntity(Parcel in) {
            sum_amount = in.readString();
            bank_name = in.readString();
            shop_name = in.readString();
            bank_account = in.readString();
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

        public void setSum_amount(String sum_amount) {
            this.sum_amount = sum_amount;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public void setBank_account(String bank_account) {
            this.bank_account = bank_account;
        }

        public String getSum_amount() {
            return sum_amount;
        }

        public String getBank_name() {
            return bank_name;
        }

        public String getShop_name() {
            return shop_name;
        }

        public String getBank_account() {
            return bank_account;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(sum_amount);
            dest.writeString(bank_name);
            dest.writeString(shop_name);
            dest.writeString(bank_account);
        }
    }
}
