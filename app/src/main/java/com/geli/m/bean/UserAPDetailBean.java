package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author:  shen
 * date:    2018/11/7
 */
public class UserAPDetailBean {


    /**
     * code : 100
     * message : ok
     * data : {"shop_name":"广州批零中心","shop_img_thumb":"upload/shops/20180108/1515399728.png","sh_day":10,"status":1,"sh_status":2,"amount":"1000.00","cost":"0.00","closing_time":0,"temp_amount":"0.00","is_temp":0}
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
         * shop_name : 广州批零中心
         * shop_img_thumb : upload/shops/20180108/1515399728.png
         * sh_day : 10
         * status : 1
         * sh_status : 2
         * amount : 1000.00
         * cost : 0.00
         * closing_time : 0
         * temp_amount : 0.00
         * is_temp : 0
         */

        private String shop_name;
        private String shop_img_thumb;
        private int sh_day;
        private int status;
        private int sh_status;
        private String amount;
        private String cost;
        private int closing_time;
        private String temp_amount;
        private int is_temp;

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_img_thumb() {
            return shop_img_thumb;
        }

        public void setShop_img_thumb(String shop_img_thumb) {
            this.shop_img_thumb = shop_img_thumb;
        }

        public int getSh_day() {
            return sh_day;
        }

        public void setSh_day(int sh_day) {
            this.sh_day = sh_day;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getSh_status() {
            return sh_status;
        }

        public void setSh_status(int sh_status) {
            this.sh_status = sh_status;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public int getClosing_time() {
            return closing_time;
        }

        public void setClosing_time(int closing_time) {
            this.closing_time = closing_time;
        }

        public String getTemp_amount() {
            return temp_amount;
        }

        public void setTemp_amount(String temp_amount) {
            this.temp_amount = temp_amount;
        }

        public int getIs_temp() {
            return is_temp;
        }

        public void setIs_temp(int is_temp) {
            this.is_temp = is_temp;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.shop_name);
            dest.writeString(this.shop_img_thumb);
            dest.writeInt(this.sh_day);
            dest.writeInt(this.status);
            dest.writeInt(this.sh_status);
            dest.writeString(this.amount);
            dest.writeString(this.cost);
            dest.writeInt(this.closing_time);
            dest.writeString(this.temp_amount);
            dest.writeInt(this.is_temp);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.shop_name = in.readString();
            this.shop_img_thumb = in.readString();
            this.sh_day = in.readInt();
            this.status = in.readInt();
            this.sh_status = in.readInt();
            this.amount = in.readString();
            this.cost = in.readString();
            this.closing_time = in.readInt();
            this.temp_amount = in.readString();
            this.is_temp = in.readInt();
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
