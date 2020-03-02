package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/**
 * author:  shen
 * date:    2018/11/27
 */
public class ShPriceBean {

    /**
     * code : 100
     * message : ok
     * data : {"day":0,"price":[{"day":10,"price":187},{"day":75,"price":425}]}
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
         * day : 0
         * price : [{"day":10,"price":187},{"day":75,"price":425}]
         */

        private int day;
        private List<PriceBean> price;

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public List<PriceBean> getPrice() {
            return price;
        }

        public void setPrice(List<PriceBean> price) {
            this.price = price;
        }

        public static class PriceBean {
            /**
             * day : 10
             * price : 187
             */

            private int day;
            private double price;
            boolean isDefault;


            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public boolean isDefault() {
                return isDefault;
            }

            public void setDefault(boolean aDefault) {
                isDefault = aDefault;
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.day);
            dest.writeList(this.price);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.day = in.readInt();
            this.price = new ArrayList<PriceBean>();
            in.readList(this.price, PriceBean.class.getClassLoader());
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
    }
}
