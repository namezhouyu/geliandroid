package com.geli.m.bean.base;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author:  shen
 * date:    2018/11/15
 */
public class EmptyBean implements Parcelable {
    private int code;
    private Object data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeParcelable((Parcelable) this.data, flags);
        dest.writeString(this.message);
    }

    public EmptyBean() {
    }

    public EmptyBean(int code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    protected EmptyBean(Parcel in) {
        this.code = in.readInt();
        this.data = in.readParcelable(Object.class.getClassLoader());
        this.message = in.readString();
    }

    public static final Parcelable.Creator<EmptyBean> CREATOR = new Parcelable.Creator<EmptyBean>() {
        @Override
        public EmptyBean createFromParcel(Parcel source) {
            return new EmptyBean(source);
        }

        @Override
        public EmptyBean[] newArray(int size) {
            return new EmptyBean[size];
        }
    };

}
