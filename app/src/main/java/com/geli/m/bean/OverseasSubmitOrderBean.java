package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Steam_l on 2018/2/7.
 *
 * 海外订单
 */

public class OverseasSubmitOrderBean implements Parcelable {
    /**
     * shop_id : 1
     * door : 1
     * goods_number : 4
     * postscript :
     * address_id : 860
     * shipping_type : 1
     * goods_id : 4965
     * sku_id : 29647
     * pay_type : 3
     */
    private String shop_id;
    private String door;
    private String goods_number;
    private String postscript;
    private String address_id;
    private String shipping_type;
    private String goods_id;
    private String sku_id;
    private String pay_type;
    private String groupon_id;

    public OverseasSubmitOrderBean() {
    }


    protected OverseasSubmitOrderBean(Parcel in) {
        shop_id = in.readString();
        door = in.readString();
        goods_number = in.readString();
        postscript = in.readString();
        address_id = in.readString();
        shipping_type = in.readString();
        goods_id = in.readString();
        sku_id = in.readString();
        pay_type = in.readString();
        groupon_id = in.readString();
    }

    public static final Creator<OverseasSubmitOrderBean> CREATOR = new Creator<OverseasSubmitOrderBean>() {
        @Override
        public OverseasSubmitOrderBean createFromParcel(Parcel in) {
            return new OverseasSubmitOrderBean(in);
        }

        @Override
        public OverseasSubmitOrderBean[] newArray(int size) {
            return new OverseasSubmitOrderBean[size];
        }
    };

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public void setDoor(String door) {
        this.door = door;
    }

    public void setGoods_number(String goods_number) {
        this.goods_number = goods_number;
    }

    public void setPostscript(String postscript) {
        this.postscript = postscript;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public void setShipping_type(String shipping_type) {
        this.shipping_type = shipping_type;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getShop_id() {
        return shop_id;
    }

    public String getDoor() {
        return door;
    }

    public String getGoods_number() {
        return goods_number;
    }

    public String getPostscript() {
        return postscript;
    }

    public String getAddress_id() {
        return address_id;
    }

    public String getShipping_type() {
        return shipping_type;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public String getSku_id() {
        return sku_id;
    }

    public String getPay_type() {
        return pay_type;
    }


    public String getGroupon_id() {
        return groupon_id;
    }

    public void setGroupon_id(String groupon_id) {
        this.groupon_id = groupon_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shop_id);
        dest.writeString(door);
        dest.writeString(goods_number);
        dest.writeString(postscript);
        dest.writeString(address_id);
        dest.writeString(shipping_type);
        dest.writeString(goods_id);
        dest.writeString(sku_id);
        dest.writeString(pay_type);
        dest.writeString(groupon_id);
    }
}
