package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Steam_l on 2017/12/21.
 * 轮播图
 */

public class AtsBean implements Parcelable {
    public static final String GOODS = "goods";
    public static final String SHOP = "shop";
    public static final String URL = "url";
    /**
     * ats_id : 13
     * ats_link :
     * goods_id : 4684
     * goods_type : 1
     * ats_pic : upload/ats/car1501552209.png
     * prom_id : null
     * skip_priority : 目标
     */
    private int ats_id;
    private String ats_link;
    private int goods_id;
    private int goods_type;
    private int shop_id;
    private String ats_pic;
    private String prom_id;
    private String skip_priority;

    public AtsBean(String ats_pic) {
        this.ats_pic = ats_pic;
    }

    protected AtsBean(Parcel in) {
        ats_id = in.readInt();
        ats_link = in.readString();
        goods_id = in.readInt();
        goods_type = in.readInt();
        shop_id = in.readInt();
        ats_pic = in.readString();
        prom_id = in.readString();
        skip_priority = in.readString();
    }

    public static final Creator<AtsBean> CREATOR = new Creator<AtsBean>() {
        @Override
        public AtsBean createFromParcel(Parcel in) {
            return new AtsBean(in);
        }

        @Override
        public AtsBean[] newArray(int size) {
            return new AtsBean[size];
        }
    };

    public void setAts_id(int ats_id) {
        this.ats_id = ats_id;
    }

    public void setAts_link(String ats_link) {
        this.ats_link = ats_link;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public void setGoods_type(int goods_type) {
        this.goods_type = goods_type;
    }

    public void setAts_pic(String ats_pic) {
        this.ats_pic = ats_pic;
    }

    public void setProm_id(String prom_id) {
        this.prom_id = prom_id;
    }

    public void setSkip_priority(String skip_priority) {
        this.skip_priority = skip_priority;
    }

    public int getAts_id() {
        return ats_id;
    }

    public String getAts_link() {
        return ats_link;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public int getGoods_type() {
        return goods_type;
    }

    public String getAts_pic() {
        return ats_pic;
    }

    public String getProm_id() {
        return prom_id;
    }

    public String getSkip_priority() {
        return skip_priority;
    }


    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ats_id);
        dest.writeString(ats_link);
        dest.writeInt(goods_id);
        dest.writeInt(goods_type);
        dest.writeInt(shop_id);
        dest.writeString(ats_pic);
        dest.writeString(prom_id);
        dest.writeString(skip_priority);
    }
}
