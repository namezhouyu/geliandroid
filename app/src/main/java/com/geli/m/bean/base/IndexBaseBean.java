package com.geli.m.bean.base;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Steam_l on 2017/12/21.
 */

public class IndexBaseBean<T> implements Parcelable {

    int view_type = -1;
    String view_key = "";
    int title_is_show;
    String model_title;
    T data;

    public IndexBaseBean(int view_type, String view_key, String title, int isShow, T data) {
        this.view_type = view_type;
        this.view_key = view_key;
        this.data = data;
        this.model_title = title;
        this.title_is_show = isShow;
    }

    protected IndexBaseBean(Parcel in) {
        view_type = in.readInt();
        view_key = in.readString();
        title_is_show = in.readInt();
        model_title = in.readString();
    }

    public static final Creator<IndexBaseBean> CREATOR = new Creator<IndexBaseBean>() {
        @Override
        public IndexBaseBean createFromParcel(Parcel in) {
            return new IndexBaseBean(in);
        }

        @Override
        public IndexBaseBean[] newArray(int size) {
            return new IndexBaseBean[size];
        }
    };

    public int getView_type() {
        return view_type;
    }

    public void setView_type(int view_type) {
        this.view_type = view_type;
    }

    public String getView_key() {
        return view_key;
    }

    public void setView_key(String view_key) {
        this.view_key = view_key;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public int getTitle_is_show() {
        return title_is_show;
    }

    public void setTitle_is_show(int title_is_show) {
        this.title_is_show = title_is_show;
    }

    public String getModel_title() {
        return model_title;
    }

    public void setModel_title(String model_title) {
        this.model_title = model_title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(view_type);
        dest.writeString(view_key);
        dest.writeInt(title_is_show);
        dest.writeString(model_title);
    }
}
