package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * author:  shen
 * date:    2018/12/29
 *
 * 可以合并的发票列表
 */
public class InvoiceOrderBean {

    /**
     * code : 100
     * message : ok
     * data : [{"order_id":6270,"order_sn":"GL20181229140623089969","sum_amount":"1100.00","add_time":1546063583}]
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
         * order_id : 6270
         * order_sn : GL20181229140623089969
         * sum_amount : 1100.00
         * add_time : 1546063583
         */

        private int order_id;
        private String order_sn;
        private String sum_amount;
        private int add_time;
        private int invoice_id;

        private boolean isClick = false;        // 不是后台的数据

        public DataBean(int order_id, String order_sn, String sum_amount, int add_time, int invoice_id, boolean isClick) {
            this.order_id = order_id;
            this.order_sn = order_sn;
            this.sum_amount = sum_amount;
            this.add_time = add_time;
            this.invoice_id = invoice_id;
            this.isClick = isClick;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getSum_amount() {
            return sum_amount;
        }

        public void setSum_amount(String sum_amount) {
            this.sum_amount = sum_amount;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public int getInvoice_id() {
            return invoice_id;
        }

        public void setInvoice_id(int invoice_id) {
            this.invoice_id = invoice_id;
        }

        public boolean isClick() {
            return isClick;
        }

        public void setClick(boolean click) {
            isClick = click;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "order_id=" + order_id +
                    ", order_sn='" + order_sn + '\'' +
                    ", sum_amount='" + sum_amount + '\'' +
                    ", add_time=" + add_time +
                    ", invoice_id=" + invoice_id +
                    ", isClick=" + isClick +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.order_id);
            dest.writeString(this.order_sn);
            dest.writeString(this.sum_amount);
            dest.writeInt(this.add_time);
            dest.writeInt(this.invoice_id);
            dest.writeByte(this.isClick ? (byte) 1 : (byte) 0);
        }

        protected DataBean(Parcel in) {
            this.order_id = in.readInt();
            this.order_sn = in.readString();
            this.sum_amount = in.readString();
            this.add_time = in.readInt();
            this.invoice_id = in.readInt();
            this.isClick = in.readByte() != 0;
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
}
