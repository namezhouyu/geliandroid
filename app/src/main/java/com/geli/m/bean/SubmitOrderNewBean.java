package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author:  shen
 * date:    2018/11/16
 */
public class SubmitOrderNewBean {

    /**
     * code : 100
     * message : 提交订单成功
     * data : {"amount":8500,"order_sn":"GL20181119094238039933"}
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

    public static class DataBean implements Parcelable {
        /**
         * amount : 8500
         * order_sn : GL20181119094238039933
         */

        private Double amount;
        private String order_sn;

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.amount);
            dest.writeString(this.order_sn);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.amount = (Double) in.readValue(Double.class.getClassLoader());
            this.order_sn = in.readString();
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        @Override
        public String toString() {
            return "DataBean{" +
                    "amount=" + amount +
                    ", order_sn='" + order_sn + '\'' +
                    '}';
        }
    }
}
