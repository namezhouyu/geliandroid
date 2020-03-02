package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Name:
 * Author:: pks
 * Date: 2017-09-01 17:19
 */

public class CouponBean implements Parcelable {
    /**
     * cp_id : 52
     * name : 优惠券名字
     * type : 3
     * money : 100.00
     * condition : 100.00
     * use_start_time : 2017-08-15 17:10
     * use_end_time : 2017-08-15 17:10
     * shop_id : 100567
     * group : 1
     * company_name : 梦火花
     */

    private int cp_id;//用于领取优惠券
    private int cpl_id;//用于使用优惠券
    private String name;
    private int type;
    private String money;
    private String condition;
    private String use_start_time;
    private String use_end_time;
    private String good_str;//商品id
    private int shop_id;//店铺id
    private int group;
    private int is_use;//0代表用户没有领取优惠劵  除了0之外的数字 说明用户已经领取了这个优惠劵
    private String company_name;

    public CouponBean(){

    }


    protected CouponBean(Parcel in) {
        cp_id = in.readInt();
        cpl_id = in.readInt();
        name = in.readString();
        type = in.readInt();
        money = in.readString();
        condition = in.readString();
        use_start_time = in.readString();
        use_end_time = in.readString();
        good_str = in.readString();
        shop_id = in.readInt();
        group = in.readInt();
        is_use = in.readInt();
        company_name = in.readString();
    }

    public static final Creator<CouponBean> CREATOR = new Creator<CouponBean>() {
        @Override
        public CouponBean createFromParcel(Parcel in) {
            return new CouponBean(in);
        }

        @Override
        public CouponBean[] newArray(int size) {
            return new CouponBean[size];
        }
    };

    public int getIs_use() {
        return is_use;
    }

    public void setIs_use(int is_use) {
        this.is_use = is_use;
    }

    public int getCpl_id() {
        return cpl_id;
    }

    public void setCpl_id(int cpl_id) {
        this.cpl_id = cpl_id;
    }

    public int getCp_id() {
        return cp_id;
    }

    public void setCp_id(int cp_id) {
        this.cp_id = cp_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getUse_start_time() {
        return use_start_time;
    }

    public void setUse_start_time(String use_start_time) {
        this.use_start_time = use_start_time;
    }

    public String getUse_end_time() {
        return use_end_time;
    }

    public void setUse_end_time(String use_end_time) {
        this.use_end_time = use_end_time;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getGood_str() {
        return good_str;
    }

    public void setGood_str(String good_str) {
        this.good_str = good_str;
    }

    @Override
    public String toString() {
        return "CouponBean{" +
                "cp_id=" + cp_id +
                ", cpl_id=" + cpl_id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", money='" + money + '\'' +
                ", condition='" + condition + '\'' +
                ", use_start_time='" + use_start_time + '\'' +
                ", use_end_time='" + use_end_time + '\'' +
                ", good_str='" + good_str + '\'' +
                ", shop_id=" + shop_id +
                ", group=" + group +
                ", company_name='" + company_name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cp_id);
        dest.writeInt(cpl_id);
        dest.writeString(name);
        dest.writeInt(type);
        dest.writeString(money);
        dest.writeString(condition);
        dest.writeString(use_start_time);
        dest.writeString(use_end_time);
        dest.writeString(good_str);
        dest.writeInt(shop_id);
        dest.writeInt(group);
        dest.writeInt(is_use);
        dest.writeString(company_name);
    }
}
