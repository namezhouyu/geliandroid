package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steam_l on 2018/1/27.
 */

public class OrderListBean {
    /**
     * code : 100
     * data : [{},{}]
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

    public static class DataEntity implements Parcelable {
        /**
         * sum_amount : 34000.00
         * goods_amount : 34000.00
         * good_str : 1752
         * shop_name : 上海牧鲜谷食品有限公司
         * shipping_status : 0
         * order_status : 1
         * pay_status : 2
         * invoice_img :
         * goods_list : [{"goods_subtotal":"34000.00","goods_name":"匹凸匹黑箱肥牛","goods_intro":"全部采用澳洲进口牛肉","goods_price":"34000.00","goods_thumb":"upload/201703/thumb_img/35071_thumb_G_1490409776468.jpg","cart_number":1,"goods_id":1752,"logistics":"0.00","goods_attr":"[{\"key\":\"规格\",\"value\":\"默认\"}]","goods_type":1,"order_id":4601,"evaluate":1}]
         * order_amount : 34000.00
         * percentage : 1
         * shop_str : 100538
         * invoice_id : 62
         * pay_type : 6
         * proof : 0
         * order_id : 4601
         * add_time : 1516870036
         * is_pay : 1
         * order_sn : GL20180125164716683932
         */
        private String sum_amount;
        private String goods_amount;
        private String good_str;
        private String shop_name;
        private int shipping_status;        // 商品配送情况    0未发货,1已发货,2已收货,4退货
        private int order_status;
        private int pay_status;//0未付款;1付款中;2已付款3付款异常
        private String invoice_img;
        private ArrayList<GoodsListEntity> goods_list;
        private String order_amount;
        private int percentage;//1 30% 2 70% 3全部付款完
        private String shop_str;
        private int invoice_id;
        private int pay_type; // 0未支付 1微信 2支付宝 3线下 4余额(支付方式)
        private int proof; //0不是线下支付  线下汇款（1待提交凭证,2审核中，3重新提交凭证，4审核成功）
        private int order_id;
        private int add_time;
        private int is_pay; // 1可以支付 2不可以支付,主要用于期货判断
        private String order_sn;
        private String order_type;
        private String per_cent;
        private String seventy_percent;
        private int is_comment;
        private String service_tel;
        private int after_sold_status;   // 售后状态 0:申请售后;1:待受理;2:受理中;3:售后完成

        private int is_sh;               // 0:非账期 1：账期

        private String logistics_price;     // 运费
        private int logistics_is_pay;       // 0：需要支付运费 1：已支付运费/不用支付运费
        private int closing_time;           // 账期最后结算日

        public String[] getState() {
            return new String[]{order_status + "", pay_status + "", shipping_status + ""};
        }

        public void setSum_amount(String sum_amount) {
            this.sum_amount = sum_amount;
        }

        public void setGoods_amount(String goods_amount) {
            this.goods_amount = goods_amount;
        }

        public void setGood_str(String good_str) {
            this.good_str = good_str;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public void setShipping_status(int shipping_status) {
            this.shipping_status = shipping_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public void setPay_status(int pay_status) {
            this.pay_status = pay_status;
        }

        public void setInvoice_img(String invoice_img) {
            this.invoice_img = invoice_img;
        }

        public void setGoods_list(ArrayList<GoodsListEntity> goods_list) {
            this.goods_list = goods_list;
        }

        public void setOrder_amount(String order_amount) {
            this.order_amount = order_amount;
        }

        public void setPercentage(int percentage) {
            this.percentage = percentage;
        }

        public void setShop_str(String shop_str) {
            this.shop_str = shop_str;
        }

        public void setInvoice_id(int invoice_id) {
            this.invoice_id = invoice_id;
        }

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
        }

        public void setProof(int proof) {
            this.proof = proof;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public void setIs_pay(int is_pay) {
            this.is_pay = is_pay;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getSum_amount() {
            return sum_amount;
        }

        public String getGoods_amount() {
            return goods_amount;
        }

        public String getGood_str() {
            return good_str;
        }

        public String getShop_name() {
            return shop_name;
        }

        public int getShipping_status() {
            return shipping_status;
        }

        public int getOrder_status() {
            return order_status;
        }

        public int getPay_status() {
            return pay_status;
        }

        public String getInvoice_img() {
            return invoice_img;
        }

        public ArrayList<GoodsListEntity> getGoods_list() {
            return goods_list;
        }

        public String getOrder_amount() {
            return order_amount;
        }

        public int getPercentage() {
            return percentage;
        }

        public String getShop_str() {
            return shop_str;
        }

        public int getInvoice_id() {
            return invoice_id;
        }

        public int getPay_type() {
            return pay_type;
        }

        public int getProof() {
            return proof;
        }

        public int getOrder_id() {
            return order_id;
        }

        public int getAdd_time() {
            return add_time;
        }

        public int getIs_pay() {
            return is_pay;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        public String getPer_cent() {
            return per_cent;
        }

        public void setPer_cent(String per_cent) {
            this.per_cent = per_cent;
        }

        public String getSeventy_percent() {
            return seventy_percent;
        }

        public void setSeventy_percent(String seventy_percent) {
            this.seventy_percent = seventy_percent;
        }

        public int getIs_comment() {
            return is_comment;
        }

        public void setIs_comment(int is_comment) {
            this.is_comment = is_comment;
        }

        public String getService_tel() {
            return service_tel;
        }

        public void setService_tel(String service_tel) {
            this.service_tel = service_tel;
        }

        public int getAfter_sold_status() {
            return after_sold_status;
        }

        public void setAfter_sold_status(int after_sold_status) {
            this.after_sold_status = after_sold_status;
        }

        public int getIs_sh() {
            return is_sh;
        }

        public void setIs_sh(int is_sh) {
            this.is_sh = is_sh;
        }

        public String getLogistics_price() {
            return logistics_price;
        }

        public void setLogistics_price(String logistics_price) {
            this.logistics_price = logistics_price;
        }

        public int getLogistics_is_pay() {
            return logistics_is_pay;
        }

        public void setLogistics_is_pay(int logistics_is_pay) {
            this.logistics_is_pay = logistics_is_pay;
        }

        public static class GoodsListEntity implements Parcelable {
            /**
             * goods_subtotal : 34000.00
             * goods_name : 匹凸匹黑箱肥牛
             * goods_intro : 全部采用澳洲进口牛肉
             * goods_price : 34000.00
             * goods_thumb : upload/201703/thumb_img/35071_thumb_G_1490409776468.jpg
             * cart_number : 1
             * goods_id : 1752
             * logistics : 0.00
             * goods_attr : [{"key":"规格","value":"默认"}]
             * goods_type : 1
             * order_id : 4601
             * evaluate : 1
             */
            private String goods_subtotal;
            private String goods_name;
            private String goods_intro;
            private String goods_price;
            private String goods_thumb;
            private int cart_number;
            private int goods_id;
            private String logistics;
            private ArrayList<CartBean.DataEntity.CartListEntity.SpecificationEntity> goods_attr;
            private int goods_type;
            private int order_id;
            private int evaluate;
            private String goods_unit;

            private int is_sh = 0;              // 我加的再上层 // 0:非账期 1：账期

            protected GoodsListEntity(Parcel in) {
                goods_subtotal = in.readString();
                goods_name = in.readString();
                goods_intro = in.readString();
                goods_price = in.readString();
                goods_thumb = in.readString();
                cart_number = in.readInt();
                goods_id = in.readInt();
                logistics = in.readString();
                goods_attr = in.createTypedArrayList(CartBean.DataEntity.CartListEntity.SpecificationEntity.CREATOR);
                goods_type = in.readInt();
                order_id = in.readInt();
                evaluate = in.readInt();
                goods_unit = in.readString();

                is_sh = in.readInt();
            }

            public static final Creator<GoodsListEntity> CREATOR = new Creator<GoodsListEntity>() {
                @Override
                public GoodsListEntity createFromParcel(Parcel in) {
                    return new GoodsListEntity(in);
                }

                @Override
                public GoodsListEntity[] newArray(int size) {
                    return new GoodsListEntity[size];
                }
            };

            public void setGoods_subtotal(String goods_subtotal) {
                this.goods_subtotal = goods_subtotal;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public void setGoods_intro(String goods_intro) {
                this.goods_intro = goods_intro;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public void setGoods_thumb(String goods_thumb) {
                this.goods_thumb = goods_thumb;
            }

            public void setCart_number(int cart_number) {
                this.cart_number = cart_number;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public void setLogistics(String logistics) {
                this.logistics = logistics;
            }

            public void setGoods_attr(ArrayList<CartBean.DataEntity.CartListEntity.SpecificationEntity> goods_attr) {
                this.goods_attr = goods_attr;
            }

            public void setGoods_type(int goods_type) {
                this.goods_type = goods_type;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public void setEvaluate(int evaluate) {
                this.evaluate = evaluate;
            }

            public String getGoods_subtotal() {
                return goods_subtotal;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public String getGoods_intro() {
                return goods_intro;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public String getGoods_thumb() {
                return goods_thumb;
            }

            public int getCart_number() {
                return cart_number;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public String getLogistics() {
                return logistics;
            }

            public ArrayList<CartBean.DataEntity.CartListEntity.SpecificationEntity> getGoods_attr() {
                return goods_attr;
            }

            public int getGoods_type() {
                return goods_type;
            }

            public int getOrder_id() {
                return order_id;
            }

            public int getEvaluate() {
                return evaluate;
            }

            public String getGoods_unit() {
                return goods_unit;
            }

            public void setGoods_unit(String goods_unit) {
                this.goods_unit = goods_unit;
            }

            public int getIs_sh() {
                return is_sh;
            }

            public void setIs_sh(int is_sh) {
                this.is_sh = is_sh;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(goods_subtotal);
                dest.writeString(goods_name);
                dest.writeString(goods_intro);
                dest.writeString(goods_price);
                dest.writeString(goods_thumb);
                dest.writeInt(cart_number);
                dest.writeInt(goods_id);
                dest.writeString(logistics);
                dest.writeTypedList(goods_attr);
                dest.writeInt(goods_type);
                dest.writeInt(order_id);
                dest.writeInt(evaluate);
                dest.writeString(goods_unit);
                dest.writeInt(is_sh);
            }

            @Override
            public String toString() {
                return "GoodsListEntity{" +
                        "goods_subtotal='" + goods_subtotal + '\'' +
                        ", goods_name='" + goods_name + '\'' +
                        ", goods_intro='" + goods_intro + '\'' +
                        ", goods_price='" + goods_price + '\'' +
                        ", goods_thumb='" + goods_thumb + '\'' +
                        ", cart_number=" + cart_number +
                        ", goods_id=" + goods_id +
                        ", logistics='" + logistics + '\'' +
                        ", goods_attr=" + goods_attr +
                        ", goods_type=" + goods_type +
                        ", order_id=" + order_id +
                        ", evaluate=" + evaluate +
                        ", goods_unit='" + goods_unit + '\'' +
                        ", is_sh='" + is_sh + '\'' +
                        '}';
            }

        }

        public int getClosing_time() {
            return closing_time;
        }

        public void setClosing_time(int closing_time) {
            this.closing_time = closing_time;
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "sum_amount='" + sum_amount + '\'' +
                    ", goods_amount='" + goods_amount + '\'' +
                    ", good_str='" + good_str + '\'' +
                    ", shop_name='" + shop_name + '\'' +
                    ", getStatu()='" + getState() + '\'' +
                    ", shipping_status=" + shipping_status +
                    ", order_status=" + order_status +
                    ", pay_status=" + pay_status +
                    ", invoice_img='" + invoice_img + '\'' +
                    ", goods_list=" + goods_list +
                    ", order_amount='" + order_amount + '\'' +
                    ", percentage=" + percentage +
                    ", shop_str='" + shop_str + '\'' +
                    ", invoice_id=" + invoice_id +
                    ", pay_type=" + pay_type +
                    ", proof=" + proof +
                    ", order_id=" + order_id +
                    ", add_time=" + add_time +
                    ", is_pay=" + is_pay +
                    ", order_sn='" + order_sn + '\'' +
                    ", order_type='" + order_type + '\'' +
                    ", per_cent='" + per_cent + '\'' +
                    ", seventy_percent='" + seventy_percent + '\'' +
                    ", is_comment=" + is_comment +
                    ", service_tel='" + service_tel + '\'' +
                    ", after_sold_status=" + after_sold_status +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.sum_amount);
            dest.writeString(this.goods_amount);
            dest.writeString(this.good_str);
            dest.writeString(this.shop_name);
            dest.writeInt(this.shipping_status);
            dest.writeInt(this.order_status);
            dest.writeInt(this.pay_status);
            dest.writeString(this.invoice_img);
            dest.writeTypedList(this.goods_list);
            dest.writeString(this.order_amount);
            dest.writeInt(this.percentage);
            dest.writeString(this.shop_str);
            dest.writeInt(this.invoice_id);
            dest.writeInt(this.pay_type);
            dest.writeInt(this.proof);
            dest.writeInt(this.order_id);
            dest.writeInt(this.add_time);
            dest.writeInt(this.is_pay);
            dest.writeString(this.order_sn);
            dest.writeString(this.order_type);
            dest.writeString(this.per_cent);
            dest.writeString(this.seventy_percent);
            dest.writeInt(this.is_comment);
            dest.writeString(this.service_tel);
            dest.writeInt(this.after_sold_status);
            dest.writeInt(this.is_sh);
            dest.writeString(this.logistics_price);
            dest.writeInt(this.logistics_is_pay);
            dest.writeInt(this.closing_time);
        }

        public DataEntity() {
        }

        protected DataEntity(Parcel in) {
            this.sum_amount = in.readString();
            this.goods_amount = in.readString();
            this.good_str = in.readString();
            this.shop_name = in.readString();
            this.shipping_status = in.readInt();
            this.order_status = in.readInt();
            this.pay_status = in.readInt();
            this.invoice_img = in.readString();
            this.goods_list = in.createTypedArrayList(GoodsListEntity.CREATOR);
            this.order_amount = in.readString();
            this.percentage = in.readInt();
            this.shop_str = in.readString();
            this.invoice_id = in.readInt();
            this.pay_type = in.readInt();
            this.proof = in.readInt();
            this.order_id = in.readInt();
            this.add_time = in.readInt();
            this.is_pay = in.readInt();
            this.order_sn = in.readString();
            this.order_type = in.readString();
            this.per_cent = in.readString();
            this.seventy_percent = in.readString();
            this.is_comment = in.readInt();
            this.service_tel = in.readString();
            this.after_sold_status = in.readInt();
            this.is_sh = in.readInt();
            this.logistics_price = in.readString();
            this.logistics_is_pay = in.readInt();
            this.closing_time = in.readInt();
        }

        public static final Parcelable.Creator<DataEntity> CREATOR = new Parcelable.Creator<DataEntity>() {
            @Override
            public DataEntity createFromParcel(Parcel source) {
                return new DataEntity(source);
            }

            @Override
            public DataEntity[] newArray(int size) {
                return new DataEntity[size];
            }
        };
    }
}
