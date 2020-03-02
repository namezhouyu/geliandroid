package com.geli.m.bean;

/**
 * {"code":100,"message":"ok","data":{"consignee":"The ","mobile":"13922776553"}}
 *
 *
 * 订单联系人
 */
public class OrderContactBean {

    private int code;
    private String message;
    private DataEntity data;
    public void setCode(int code) {
         this.code = code;
     }
     public int getCode() {
         return code;
     }

    public void setMessage(String message) {
         this.message = message;
     }
     public String getMessage() {
         return message;
     }

    public void setData(DataEntity data) {
         this.data = data;
     }
     public DataEntity getData() {
         return data;
     }


    /**
     * "data":{"consignee":"The ","mobile":"13922776553"}
     */
    public static class DataEntity {

        private String consignee;
        private String mobile;
        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }
        public String getConsignee() {
            return consignee;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
        public String getMobile() {
            return mobile;
        }

    }
}