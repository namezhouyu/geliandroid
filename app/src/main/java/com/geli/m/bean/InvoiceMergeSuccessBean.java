package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * author:  shen
 * date:    2019/1/2
 */
public class InvoiceMergeSuccessBean {


    /**
     * code : 100
     * message : ok
     * data : {"invoice_res":{"name":"I Guangzhou","invoice_type":2,"type":2,"belong":1},"add_time":1546478335,"address_res":{"address":"天津市天津市宁河县芦台镇12","consignee":"The ","mobile":"13922776553"},"order_res":[{"order_id":6273,"order_sn":"GL20190102153933000932","sum_amount":"5000.00","invoice_id":527,"add_time":1546414773},{"order_id":6274,"order_sn":"GL20190102154147082948","sum_amount":"15000.00","invoice_id":527,"add_time":1546414907}]}
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
         * invoice_res : {"name":"I Guangzhou","invoice_type":2,"type":2,"belong":1}
         * add_time : 1546478335
         * address_res : {"address":"天津市天津市宁河县芦台镇12","consignee":"The ","mobile":"13922776553"}
         * order_res : [{"order_id":6273,"order_sn":"GL20190102153933000932","sum_amount":"5000.00","invoice_id":527,"add_time":1546414773},{"order_id":6274,"order_sn":"GL20190102154147082948","sum_amount":"15000.00","invoice_id":527,"add_time":1546414907}]
         */

        private InvoiceResBean invoice_res;
        private int add_time;
        private InvoiceStateBean.DataBean.InvoiceResBean.SendAddressBean address_res;
        private List<InvoiceOrderBean.DataBean> order_res;

        public InvoiceResBean getInvoice_res() {
            return invoice_res;
        }

        public void setInvoice_res(InvoiceResBean invoice_res) {
            this.invoice_res = invoice_res;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public InvoiceStateBean.DataBean.InvoiceResBean.SendAddressBean getAddress_res() {
            return address_res;
        }

        public void setAddress_res(InvoiceStateBean.DataBean.InvoiceResBean.SendAddressBean address_res) {
            this.address_res = address_res;
        }

        public List<InvoiceOrderBean.DataBean> getOrder_res() {
            return order_res;
        }

        public void setOrder_res(List<InvoiceOrderBean.DataBean> order_res) {
            this.order_res = order_res;
        }

        public static class InvoiceResBean implements Parcelable {
            /**
             * name : I Guangzhou
             * invoice_type : 2
             * type : 2
             * belong : 1
             */

            private String name;
            private int invoice_type;
            private int type;
            private int belong;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getInvoice_type() {
                return invoice_type;
            }

            public void setInvoice_type(int invoice_type) {
                this.invoice_type = invoice_type;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getBelong() {
                return belong;
            }

            public void setBelong(int belong) {
                this.belong = belong;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.name);
                dest.writeInt(this.invoice_type);
                dest.writeInt(this.type);
                dest.writeInt(this.belong);
            }

            public InvoiceResBean() {
            }

            protected InvoiceResBean(Parcel in) {
                this.name = in.readString();
                this.invoice_type = in.readInt();
                this.type = in.readInt();
                this.belong = in.readInt();
            }

            public static final Parcelable.Creator<InvoiceResBean> CREATOR = new Parcelable.Creator<InvoiceResBean>() {
                @Override
                public InvoiceResBean createFromParcel(Parcel source) {
                    return new InvoiceResBean(source);
                }

                @Override
                public InvoiceResBean[] newArray(int size) {
                    return new InvoiceResBean[size];
                }
            };
        }

        public static class AddressResBean {
            /**
             * address : 天津市天津市宁河县芦台镇12
             * consignee : The
             * mobile : 13922776553
             */

            private String address;
            private String consignee;
            private String mobile;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getConsignee() {
                return consignee;
            }

            public void setConsignee(String consignee) {
                this.consignee = consignee;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }
        }

        public static class OrderResBean {
            /**
             * order_id : 6273
             * order_sn : GL20190102153933000932
             * sum_amount : 5000.00
             * invoice_id : 527
             * add_time : 1546414773
             */

            private int order_id;
            private String order_sn;
            private String sum_amount;
            private int invoice_id;
            private int add_time;

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

            public int getInvoice_id() {
                return invoice_id;
            }

            public void setInvoice_id(int invoice_id) {
                this.invoice_id = invoice_id;
            }

            public int getAdd_time() {
                return add_time;
            }

            public void setAdd_time(int add_time) {
                this.add_time = add_time;
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.invoice_res, flags);
            dest.writeInt(this.add_time);
            dest.writeParcelable(this.address_res, flags);
            dest.writeTypedList(this.order_res);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.invoice_res = in.readParcelable(InvoiceResBean.class.getClassLoader());
            this.add_time = in.readInt();
            this.address_res = in.readParcelable(InvoiceStateBean.DataBean.InvoiceResBean.SendAddressBean.class.getClassLoader());
            this.order_res = in.createTypedArrayList(InvoiceOrderBean.DataBean.CREATOR);
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
