package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * Created by Steam_l on 2018/1/9.
 */

public class AddressListBean {

    /**
     * code : 100
     * data : [{"consignee":"tug","address":"dyhvcdtunbb","address_id":949,"mobile":"5558","d_cn":"东城区","c_cn":"市辖区","e_cn":"景山街道","is_default":1,"p_cn":"北京市","user_id":7261,"d_id":4,"c_id":3,"e_id":5,"p_id":2},{"consignee":"tyfhh","address":"fsjhccturyffk","address_id":950,"mobile":"2346586","d_cn":"东城区","c_cn":"市辖区","e_cn":"交道口街道","is_default":0,"p_cn":"北京市","user_id":7261,"d_id":4,"c_id":3,"e_id":6,"p_id":2},{"consignee":"阿姨","address":"邋遢他不","address_id":951,"mobile":"13435026831","d_cn":"延庆县","c_cn":"县","e_cn":"康庄镇","is_default":0,"p_cn":"北京市","user_id":7261,"d_id":322,"c_id":300,"e_id":324,"p_id":2}]
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
        protected DataEntity(Parcel in) {
            consignee = in.readString();
            address = in.readString();
            address_id = in.readInt();
            mobile = in.readString();
            p_cn = in.readString();
            d_cn = in.readString();
            c_cn = in.readString();
            e_cn = in.readString();
            is_default = in.readInt();
            user_id = in.readInt();
            d_id = in.readInt();
            c_id = in.readInt();
            e_id = in.readInt();
            p_id = in.readInt();
            isCheck = in.readByte() != 0;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(consignee);
            dest.writeString(address);
            dest.writeInt(address_id);
            dest.writeString(mobile);
            dest.writeString(p_cn);
            dest.writeString(d_cn);
            dest.writeString(c_cn);
            dest.writeString(e_cn);
            dest.writeInt(is_default);
            dest.writeInt(user_id);
            dest.writeInt(d_id);
            dest.writeInt(c_id);
            dest.writeInt(e_id);
            dest.writeInt(p_id);
            dest.writeByte((byte) (isCheck ? 1 : 0));
        }

        public LogisticsPrice getLogisticsPrice() {
            return mLogisticsPrice;
        }

        public void setLogisticsPrice(LogisticsPrice logisticsPrice) {
            mLogisticsPrice = logisticsPrice;
        }

        public interface LogisticsPrice {
            void onSuccess();

            void onError();
        }

        /**
         * consignee : tug
         * address : dyhvcdtunbb
         * address_id : 949
         * mobile : 5558
         * d_cn : 东城区
         * c_cn : 市辖区
         * e_cn : 景山街道
         * is_default : 1
         * p_cn : 北京市
         * user_id : 7261
         * d_id : 4
         * c_id : 3
         * e_id : 5
         * p_id : 2
         */
        private String consignee;
        private String address;
        private int address_id;
        private String mobile;
        private String p_cn = "";
        private String d_cn = "";
        private String c_cn = "";
        private String e_cn = "";
        private int is_default;
        private int user_id;
        private int d_id;
        private int c_id;
        private int e_id;
        private int p_id;
        private LogisticsPrice mLogisticsPrice;
        public boolean isCheck;


        public String getAdd() {
            String res = "";
            if (p_cn != null) {
                res += p_cn;
                if (c_cn != null) {
                    res += c_cn;
                    if (d_cn != null) {
                        res += d_cn;
                        if (e_cn != null) {
                            res += e_cn;
                        } else {
                            return res;
                        }
                    } else {
                        return res;
                    }
                } else {
                    return res;
                }
            }
            return res;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setAddress_id(int address_id) {
            this.address_id = address_id;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public void setD_cn(String d_cn) {
            this.d_cn = d_cn;
        }

        public void setC_cn(String c_cn) {
            this.c_cn = c_cn;
        }

        public void setE_cn(String e_cn) {
            this.e_cn = e_cn;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }

        public void setP_cn(String p_cn) {
            this.p_cn = p_cn;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public void setD_id(int d_id) {
            this.d_id = d_id;
        }

        public void setC_id(int c_id) {
            this.c_id = c_id;
        }

        public void setE_id(int e_id) {
            this.e_id = e_id;
        }

        public void setP_id(int p_id) {
            this.p_id = p_id;
        }

        public String getConsignee() {
            return consignee;
        }

        public String getAddress() {
            return address;
        }

        public int getAddress_id() {
            return address_id;
        }

        public String getMobile() {
            return mobile;
        }

        public String getD_cn() {
            return d_cn;
        }

        public String getC_cn() {
            return c_cn;
        }

        public String getE_cn() {
            return e_cn;
        }

        public int getIs_default() {
            return is_default;
        }

        public String getP_cn() {
            return p_cn;
        }

        public int getUser_id() {
            return user_id;
        }

        public int getD_id() {
            return d_id;
        }

        public int getC_id() {
            return c_id;
        }

        public int getE_id() {
            return e_id;
        }

        public int getP_id() {
            return p_id;
        }

    }
}
