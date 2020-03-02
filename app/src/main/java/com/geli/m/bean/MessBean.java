package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * Created by Steam_l on 2018/3/28.
 */

public class MessBean {
    /**
     * code : 100
     * data : [{"is_read":0,"push_title":"跳到商品详情测试数据","create_time":1522222793,"user_id":7217,"push_id":46,"cat_id":4,"end_time":1537774793,"target_id":"3391","push_content":"跳到商品详情测试数据"},{"is_read":0,"push_title":"跳到商店优惠劵测试数据","create_time":1522222793,"user_id":0,"push_id":47,"cat_id":6,"end_time":1537774793,"target_id":"1","push_content":"跳到商店优惠劵测试数据"},{"is_read":0,"push_title":"跳到商店首页测试数据","create_time":1522222793,"user_id":0,"push_id":48,"cat_id":5,"end_time":1537774793,"target_id":"1","push_content":"跳到商店首页测试数据"},{"is_read":0,"push_title":"跳到支付提醒测试数据","create_time":1522222793,"user_id":7217,"push_id":49,"cat_id":8,"end_time":1537774793,"target_id":"5465","push_content":"跳到支付提醒测试数据"}]
     * message : ok
     */
    private int code;
    private List<DataEntity> data;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class DataEntity implements Parcelable{
        /**
         * is_read : 0
         * push_title : 跳到商品详情测试数据
         * create_time : 1522222793
         * user_id : 7217
         * push_id : 46
         * cat_id : 4
         * end_time : 1537774793
         * target_id : 3391
         * push_content : 跳到商品详情测试数据
         */
        private int is_read;
        private String push_title;
        private int create_time;
        private int user_id;
        private int push_id;
        private int cat_id;
        private int end_time;
        private String target_id;
        private String push_thumb;//图片
        private String goods_name;//商品名字
        private String order_sn;
        private String shop_name;
        private String digest;

        protected DataEntity(Parcel in) {
            is_read = in.readInt();
            push_title = in.readString();
            create_time = in.readInt();
            user_id = in.readInt();
            push_id = in.readInt();
            cat_id = in.readInt();
            end_time = in.readInt();
            target_id = in.readString();
            push_thumb = in.readString();
            goods_name = in.readString();
            order_sn = in.readString();
            shop_name = in.readString();
            digest = in.readString();
        }

        public static final Creator<DataEntity> CREATOR = new Creator<DataEntity>() {
            @Override
            public DataEntity createFromParcel(Parcel in) {
                return new DataEntity(in);
            }

            @Override
            public DataEntity[] newArray(int size) {
                return new DataEntity[size];
            }
        };

        public void setIs_read(int is_read) {
            this.is_read = is_read;
        }

        public void setPush_title(String push_title) {
            this.push_title = push_title;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public void setPush_id(int push_id) {
            this.push_id = push_id;
        }

        public void setCat_id(int cat_id) {
            this.cat_id = cat_id;
        }

        public void setEnd_time(int end_time) {
            this.end_time = end_time;
        }

        public void setTarget_id(String target_id) {
            this.target_id = target_id;
        }


        public int getIs_read() {
            return is_read;
        }

        public String getPush_title() {
            return push_title;
        }

        public int getCreate_time() {
            return create_time;
        }

        public int getUser_id() {
            return user_id;
        }

        public int getPush_id() {
            return push_id;
        }

        public int getCat_id() {
            return cat_id;
        }

        public int getEnd_time() {
            return end_time;
        }

        public String getTarget_id() {
            return target_id;
        }

        public String getPush_thumb() {
            return push_thumb;
        }

        public void setPush_thumb(String push_thumb) {
            this.push_thumb = push_thumb;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(is_read);
            dest.writeString(push_title);
            dest.writeInt(create_time);
            dest.writeInt(user_id);
            dest.writeInt(push_id);
            dest.writeInt(cat_id);
            dest.writeInt(end_time);
            dest.writeString(target_id);
            dest.writeString(push_thumb);
            dest.writeString(goods_name);
            dest.writeString(order_sn);
            dest.writeString(shop_name);
            dest.writeString(digest);
        }
    }
}
