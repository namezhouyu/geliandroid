package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * author:  shen
 * date:    2019/1/2
 *
 * 发票状态
 */
public class InvoiceStateBean {
    /**
     * code : 100
     * message : ok
     * data : {"invoice_res":{"invoice_id":523,"belong":1,"name":"I Guangzhou","add_time":1517987376,"invoice_type":2,"type":2,"status":1,"send_address":{"address":"北京市北京市东城区景山街道Rrrr","consignee":"Ljh ","mobile":"13922332233"},"invoice_img":""},"order_res":[{"order_id":6273,"order_sn":"GL20190102153933000932","sum_amount":"5000.00","invoice_id":523,"add_time":1546414773},{"order_id":6274,"order_sn":"GL20190102154147082948","sum_amount":"15000.00","invoice_id":523,"add_time":1546414907}]}
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

    public static class DataBean {
        /**
         * invoice_res : {"invoice_id":523,"belong":1,"name":"I Guangzhou","add_time":1517987376,"invoice_type":2,"type":2,"status":1,"send_address":{"address":"北京市北京市东城区景山街道Rrrr","consignee":"Ljh ","mobile":"13922332233"},"invoice_img":""}
         * order_res : [{"order_id":6273,"order_sn":"GL20190102153933000932","sum_amount":"5000.00","invoice_id":523,"add_time":1546414773},{"order_id":6274,"order_sn":"GL20190102154147082948","sum_amount":"15000.00","invoice_id":523,"add_time":1546414907}]
         */

        private InvoiceResBean invoice_res;
        private List<InvoiceOrderBean.DataBean> order_res;

        public InvoiceResBean getInvoice_res() {
            return invoice_res;
        }

        public void setInvoice_res(InvoiceResBean invoice_res) {
            this.invoice_res = invoice_res;
        }

        public List<InvoiceOrderBean.DataBean> getOrder_res() {
            return order_res;
        }

        public void setOrder_res(List<InvoiceOrderBean.DataBean> order_res) {
            this.order_res = order_res;
        }

        public static class InvoiceResBean {
            /**
             * invoice_id : 523
             * belong : 1
             * name : I Guangzhou
             * add_time : 1517987376
             * invoice_type : 2
             * type : 2
             * status : 1
             * send_address : {"address":"北京市北京市东城区景山街道Rrrr","consignee":"Ljh ","mobile":"13922332233"}
             * invoice_img :
             */

            private int invoice_id;
            private int belong;
            private String name;
            private int add_time;
            private int invoice_type;
            private int type;
            private int status;
            private SendAddressBean send_address;
            private String invoice_img;

            public int getInvoice_id() {
                return invoice_id;
            }

            public void setInvoice_id(int invoice_id) {
                this.invoice_id = invoice_id;
            }

            public int getBelong() {
                return belong;
            }

            public void setBelong(int belong) {
                this.belong = belong;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getAdd_time() {
                return add_time;
            }

            public void setAdd_time(int add_time) {
                this.add_time = add_time;
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public SendAddressBean getSend_address() {
                return send_address;
            }

            public void setSend_address(SendAddressBean send_address) {
                this.send_address = send_address;
            }

            public String getInvoice_img() {
                return invoice_img;
            }

            public void setInvoice_img(String invoice_img) {
                this.invoice_img = invoice_img;
            }

            public static class SendAddressBean implements Parcelable {
                /**
                 * address : 北京市北京市东城区景山街道Rrrr
                 * consignee : Ljh
                 * mobile : 13922332233
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

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.address);
                    dest.writeString(this.consignee);
                    dest.writeString(this.mobile);
                }

                public SendAddressBean() {
                }

                protected SendAddressBean(Parcel in) {
                    this.address = in.readString();
                    this.consignee = in.readString();
                    this.mobile = in.readString();
                }

                public static final Parcelable.Creator<SendAddressBean> CREATOR = new Parcelable.Creator<SendAddressBean>() {
                    @Override
                    public SendAddressBean createFromParcel(Parcel source) {
                        return new SendAddressBean(source);
                    }

                    @Override
                    public SendAddressBean[] newArray(int size) {
                        return new SendAddressBean[size];
                    }
                };
            }
        }

        public static class OrderResBean {
            /**
             * order_id : 6273
             * order_sn : GL20190102153933000932
             * sum_amount : 5000.00
             * invoice_id : 523
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
    }
}
