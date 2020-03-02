package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author:  shen
 * date:    2018/11/15
 */
public class PayBean implements Parcelable {
    private int order_id;
    private int is_pay;
    private String order_type;
    private String per_cent;
    private String seventy_percent;
    private int percentage;
    private String sum_amount;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(int is_pay) {
        this.is_pay = is_pay;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getPer_cent() {
        return per_cent;
    }

    public void setPer_cent(String per_cent) {
        this.per_cent = per_cent;
    }

    public String getSeventy_percent() {
        return seventy_percent;
    }

    public void setSeventy_percent(String seventy_percent) {
        this.seventy_percent = seventy_percent;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getSum_amount() {
        return sum_amount;
    }

    public void setSum_amount(String sum_amount) {
        this.sum_amount = sum_amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.order_id);
        dest.writeInt(this.is_pay);
        dest.writeString(this.order_type);
        dest.writeString(this.per_cent);
        dest.writeString(this.seventy_percent);
        dest.writeInt(this.percentage);
        dest.writeString(this.sum_amount);
    }

    public PayBean() {
    }

    protected PayBean(Parcel in) {
        this.order_id = in.readInt();
        this.is_pay = in.readInt();
        this.order_type = in.readString();
        this.per_cent = in.readString();
        this.seventy_percent = in.readString();
        this.percentage = in.readInt();
        this.sum_amount = in.readString();
    }

    public static final Parcelable.Creator<PayBean> CREATOR = new Parcelable.Creator<PayBean>() {
        @Override
        public PayBean createFromParcel(Parcel source) {
            return new PayBean(source);
        }

        @Override
        public PayBean[] newArray(int size) {
            return new PayBean[size];
        }
    };
}
