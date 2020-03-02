package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pks on 2017/6/9.
 * 区
 */

public class District implements Parcelable {

    /**
     * area_id : 1292
     * area_name : 市辖区
     * code : 130401
     */

    private String area_id;
    private String area_name;
    private String code;

    protected District(Parcel in) {
        area_id = in.readString();
        area_name = in.readString();
        code = in.readString();
    }

    public static final Creator<District> CREATOR = new Creator<District>() {
        @Override
        public District createFromParcel(Parcel in) {
            return new District(in);
        }

        @Override
        public District[] newArray(int size) {
            return new District[size];
        }
    };

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(area_id);
        dest.writeString(area_name);
        dest.writeString(code);
    }
}
