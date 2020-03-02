package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import java.util.List;

/**
 * Created by Steam_l on 2018/1/11.
 */

public class InvoiceBean {
    /**
     * code : 100
     * data : [{"address":"","code":null,"belong":2,"duty_paragraph":null,"type":1,"license":"0","user_id":239,"account_name":"","name":"塔头","invoice_id":231,"tel":"83830","invoice_type":1,"add_time":1515654637,"account":null,"email":"特特"},{"address":"","code":null,"belong":2,"duty_paragraph":null,"type":1,"license":"0","user_id":239,"account_name":"","name":"顶级","invoice_id":230,"tel":"346454","invoice_type":2,"add_time":1515654303,"account":null,"email":"和地理"},{"address":"和地理丽丽","code":0,"belong":1,"duty_paragraph":64345454,"type":2,"license":"upload/invoice/20171030/15093665584489856.jpg","user_id":239,"account_name":"","name":"弟弟交手机了","invoice_id":203,"tel":"","invoice_type":1,"add_time":1509366558,"account":"0","email":"弟弟你理解"}]
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

    public static class DataEntity implements Comparable<DataEntity>, Parcelable {
        public boolean isEditInvoice;
        public boolean isCheck;
        /**
         * address :
         * code : null
         * belong : 2
         * duty_paragraph : null
         * type : 1
         * license : 0
         * user_id : 239
         * account_name :
         * name : 塔头
         * invoice_id : 231
         * tel : 83830
         * invoice_type : 1
         * add_time : 1515654637
         * account : null
         * email : 特特
         */
        private String address;
        private String code;
        private int belong;
        private String duty_paragraph;
        private int type;
        private String license;
        private String user_id;
        private String account_name;
        private String name;
        private int invoice_id = -1;
        private String tel;
        private int invoice_type;
        private int add_time;
        private String account;
        private String email;

        public DataEntity() {

        }

        protected DataEntity(Parcel in) {
            address = in.readString();
            code = in.readString();
            belong = in.readInt();
            duty_paragraph = in.readString();
            type = in.readInt();
            license = in.readString();
            user_id = in.readString();
            account_name = in.readString();
            name = in.readString();
            invoice_id = in.readInt();
            tel = in.readString();
            invoice_type = in.readInt();
            add_time = in.readInt();
            account = in.readString();
            email = in.readString();
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

        public void setAddress(String address) {
            this.address = address;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setBelong(int belong) {
            this.belong = belong;
        }

        public void setDuty_paragraph(String duty_paragraph) {
            this.duty_paragraph = duty_paragraph;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public void setAccount_name(String account_name) {
            this.account_name = account_name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setInvoice_id(int invoice_id) {
            this.invoice_id = invoice_id;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public void setInvoice_type(int invoice_type) {
            this.invoice_type = invoice_type;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public String getCode() {
            return code;
        }

        public int getBelong() {
            return belong;
        }

        public String getDuty_paragraph() {
            return duty_paragraph;
        }

        public int getType() {
            return type;
        }

        public String getLicense() {
            return license;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getAccount_name() {
            return account_name;
        }

        public String getName() {
            return name;
        }

        public int getInvoice_id() {
            return invoice_id;
        }

        public String getTel() {
            return tel;
        }

        public int getInvoice_type() {
            return invoice_type;
        }

        public int getAdd_time() {
            return add_time;
        }

        public String getAccount() {
            return account;
        }

        public String getEmail() {
            return email;
        }

        // 如果参数字符串等于此字符串，则返回值 0；
        // 如果此字符串按字典顺序小于字符串参数，则返回一个小于 0 的值；
        // 如果此字符串按字典顺序大于字符串参数，则返回一个大于 0 的值。
        @Override
        public int compareTo(@NonNull DataEntity o) {
            int belong = this.getBelong() - o.getBelong();      /* 1：单位 2：个人 */
            int type = this.getType() - o.getType();
            if (belong == 0) {
                return type;
            }
            return belong;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(address);
            dest.writeString(code);
            dest.writeInt(belong);
            dest.writeString(duty_paragraph);
            dest.writeInt(type);
            dest.writeString(license);
            dest.writeString(user_id);
            dest.writeString(account_name);
            dest.writeString(name);
            dest.writeInt(invoice_id);
            dest.writeString(tel);
            dest.writeInt(invoice_type);
            dest.writeInt(add_time);
            dest.writeString(account);
            dest.writeString(email);
        }
    }
}
