package com.geli.m.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Labor on 2017/6/16.
 */

public class WeChatPayBean {
    /**
     * code : 100
     * message : ok
     * data : {"datas":{"appid":"wxfa5d52a503eb6958","noncestr":"5K8264ILTKCH16CQ2502SI8ZNMTM67VS","package":"Sign=WXPay","prepayid":"wx201706161701231fe8f434310868807433","partnerid":"1481750992","timestamp":"1497603683","sign":"083DF09A3E2C1E1C3C16652979E6F6AE"},"ordersn":"GL20170616170123089298","message":"预支付完成"}
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
         * datas : {"appid":"wxfa5d52a503eb6958","noncestr":"5K8264ILTKCH16CQ2502SI8ZNMTM67VS","package":"Sign=WXPay","prepayid":"wx201706161701231fe8f434310868807433","partnerid":"1481750992","timestamp":"1497603683","sign":"083DF09A3E2C1E1C3C16652979E6F6AE"}
         * ordersn : GL20170616170123089298
         * message : 预支付完成
         */

        private DatasBean datas;
        private String ordersn;
        private String message;

        public DatasBean getDatas() {
            return datas;
        }

        public void setDatas(DatasBean datas) {
            this.datas = datas;
        }

        public String getOrdersn() {
            return ordersn;
        }

        public void setOrdersn(String ordersn) {
            this.ordersn = ordersn;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public static class DatasBean {
            /**
             * appid : wxfa5d52a503eb6958
             * noncestr : 5K8264ILTKCH16CQ2502SI8ZNMTM67VS
             * package : Sign=WXPay
             * prepayid : wx201706161701231fe8f434310868807433
             * partnerid : 1481750992
             * timestamp : 1497603683
             * sign : 083DF09A3E2C1E1C3C16652979E6F6AE
             */

            private String appid;
            private String noncestr;
            @SerializedName("package")
            private String packageX;
            private String prepayid;
            private String partnerid;
            private String timestamp;
            private String sign;

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }
        }
    }
}
