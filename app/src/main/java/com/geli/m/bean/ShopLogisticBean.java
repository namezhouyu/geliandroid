package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * author:  shen
 * date:    2019/3/23
 */
public class ShopLogisticBean implements Parcelable {

    /**
     * code : 100
     * message : ok
     * data : [{"log_id":4,"log_name":"测试物流商2号","linkman":"小明","linkphone":"13547841025","remark":"每天2：00、14：00出发","create_time":"2019-03-06 17:17:54","is_del":0,"status":1}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * log_id : 4
         * log_name : 测试物流商2号
         * linkman : 小明
         * linkphone : 13547841025
         * remark : 每天2：00、14：00出发
         * create_time : 2019-03-06 17:17:54
         * is_del : 0
         * status : 1
         */

        private int log_id;
        private String log_name;
        private String linkman;
        private String linkphone;
        private String remark;
        private String create_time;
        private int is_del;
        private int status;

        public int getLog_id() {
            return log_id;
        }

        public void setLog_id(int log_id) {
            this.log_id = log_id;
        }

        public String getLog_name() {
            return log_name;
        }

        public void setLog_name(String log_name) {
            this.log_name = log_name;
        }

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public String getLinkphone() {
            return linkphone;
        }

        public void setLinkphone(String linkphone) {
            this.linkphone = linkphone;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getIs_del() {
            return is_del;
        }

        public void setIs_del(int is_del) {
            this.is_del = is_del;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.log_id);
            dest.writeString(this.log_name);
            dest.writeString(this.linkman);
            dest.writeString(this.linkphone);
            dest.writeString(this.remark);
            dest.writeString(this.create_time);
            dest.writeInt(this.is_del);
            dest.writeInt(this.status);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.log_id = in.readInt();
            this.log_name = in.readString();
            this.linkman = in.readString();
            this.linkphone = in.readString();
            this.remark = in.readString();
            this.create_time = in.readString();
            this.is_del = in.readInt();
            this.status = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.message);
        dest.writeTypedList(this.data);
    }

    public ShopLogisticBean() {
    }

    protected ShopLogisticBean(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<ShopLogisticBean> CREATOR = new Parcelable.Creator<ShopLogisticBean>() {
        @Override
        public ShopLogisticBean createFromParcel(Parcel source) {
            return new ShopLogisticBean(source);
        }

        @Override
        public ShopLogisticBean[] newArray(int size) {
            return new ShopLogisticBean[size];
        }
    };
}
