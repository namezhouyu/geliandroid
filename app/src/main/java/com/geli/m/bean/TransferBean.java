package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author:  shen
 * date:    2019/1/29
 *
 * 转账支付
 */
public class TransferBean implements Parcelable {


    /**
     * code : 100
     * message : ok
     * data : {"order_id":5523,"clt_nm":"广东温氏佳味食品有限公司","sub_no":"1835511000086043"}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * order_id : 5523
         * clt_nm : 广东温氏佳味食品有限公司
         * sub_no : 1835511000086043    -- 账号
         */

        private int order_id;
        private String clt_nm;
        private String sub_no;

        private String amount;          // 我添加的
        private String order_sn;        // 我添加的

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getClt_nm() {
            return clt_nm;
        }

        public void setClt_nm(String clt_nm) {
            this.clt_nm = clt_nm;
        }

        public String getSub_no() {
            return sub_no;
        }

        public void setSub_no(String sub_no) {
            this.sub_no = sub_no;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.order_id);
            dest.writeString(this.clt_nm);
            dest.writeString(this.sub_no);
            dest.writeString(this.amount);
            dest.writeString(this.order_sn);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.order_id = in.readInt();
            this.clt_nm = in.readString();
            this.sub_no = in.readString();
            this.amount = in.readString();
            this.order_sn = in.readString();
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
        dest.writeParcelable(this.data, flags);
    }

    public TransferBean() {
    }

    protected TransferBean(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<TransferBean> CREATOR = new Parcelable.Creator<TransferBean>() {
        @Override
        public TransferBean createFromParcel(Parcel source) {
            return new TransferBean(source);
        }

        @Override
        public TransferBean[] newArray(int size) {
            return new TransferBean[size];
        }
    };
}
