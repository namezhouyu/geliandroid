package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author:  shen
 * date:    2018/10/18
 */
public class AreaBean implements Parcelable {

    String id;
    String cityName;
    /** district:区域 */
    String address;
    /** latitude:纬度 */
    String la;
    /** longitude:经度 */
    String lo;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.cityName);
        dest.writeString(this.address);
        dest.writeString(this.la);
        dest.writeString(this.lo);
    }

    public AreaBean() {
    }

    public AreaBean(String address, String la, String lo) {
        this.address = address;
        this.la = la;
        this.lo = lo;
    }

    protected AreaBean(Parcel in) {
        this.id = in.readString();
        this.cityName = in.readString();
        this.address = in.readString();
        this.la = in.readString();
        this.lo = in.readString();
    }

    public static final Parcelable.Creator<AreaBean> CREATOR = new Parcelable.Creator<AreaBean>() {
        @Override
        public AreaBean createFromParcel(Parcel source) {
            return new AreaBean(source);
        }

        @Override
        public AreaBean[] newArray(int size) {
            return new AreaBean[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLa() {
        return la;
    }

    public void setLa(String la) {
        this.la = la;
    }

    public String getLo() {
        return lo;
    }

    public void setLo(String lo) {
        this.lo = lo;
    }

    @Override
    public String toString() {
        return "AreaBean{" +
                "id='" + id + '\'' +
                ", cityName='" + cityName + '\'' +
                ", address='" + address + '\'' +
                ", la='" + la + '\'' +
                ", lo='" + lo + '\'' +
                '}';
    }
}
