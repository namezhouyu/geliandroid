package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Steam_l on 2018/2/5.
 */

public class PersonInfoBean {
    /**
     * code : 100
     * data : {"receivingGoods":0,"truename":"","phone":"13076151870","pendingPayment":38,"sex":0,"nickname":"13076151870","waitingDelivery":0,"avatar":"","complete":2}
     * message : ok
     */
    private int code;
    private DataEntity data;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class DataEntity implements Parcelable{
        /**
         * receivingGoods : 0
         * truename :
         * phone : 13076151870
         * pendingPayment : 38
         * sex : 0
         * nickname : 13076151870
         * waitingDelivery : 0
         * avatar :
         * complete : 2
         */
        private int receivingGoods;
        private String truename;
        private String phone;
        private int pendingPayment;
        private int sex;
        private String nickname;
        private int waitingDelivery;
        private String avatar;
        private int complete;
        private String rank_name;


        protected DataEntity(Parcel in) {
            receivingGoods = in.readInt();
            truename = in.readString();
            phone = in.readString();
            pendingPayment = in.readInt();
            sex = in.readInt();
            nickname = in.readString();
            waitingDelivery = in.readInt();
            avatar = in.readString();
            complete = in.readInt();
            rank_name = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(receivingGoods);
            dest.writeString(truename);
            dest.writeString(phone);
            dest.writeInt(pendingPayment);
            dest.writeInt(sex);
            dest.writeString(nickname);
            dest.writeInt(waitingDelivery);
            dest.writeString(avatar);
            dest.writeInt(complete);
            dest.writeString(rank_name);
        }

        @Override
        public int describeContents() {
            return 0;
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

        public void setReceivingGoods(int receivingGoods) {
            this.receivingGoods = receivingGoods;
        }

        public void setTruename(String truename) {
            this.truename = truename;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setPendingPayment(int pendingPayment) {
            this.pendingPayment = pendingPayment;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setWaitingDelivery(int waitingDelivery) {
            this.waitingDelivery = waitingDelivery;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setComplete(int complete) {
            this.complete = complete;
        }

        public int getReceivingGoods() {
            return receivingGoods;
        }

        public String getTruename() {
            return truename;
        }

        public String getPhone() {
            return phone;
        }

        public int getPendingPayment() {
            return pendingPayment;
        }

        public int getSex() {
            return sex;
        }

        public String getNickname() {
            return nickname;
        }

        public int getWaitingDelivery() {
            return waitingDelivery;
        }

        public String getAvatar() {
            return avatar;
        }

        public int getComplete() {
            return complete;
        }


        public String getRank_name() {
            return rank_name;
        }

        public void setRank_name(String rank_name) {
            this.rank_name = rank_name;
        }
    }
}
