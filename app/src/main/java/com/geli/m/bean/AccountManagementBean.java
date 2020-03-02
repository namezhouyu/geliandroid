package com.geli.m.bean;

import java.util.List;

/**
 * author:  shen
 * date:    2018/11/13
 */
public class AccountManagementBean {

    /**
     * code : 100
     * message : ok
     * data : [{"shop_name":"广州批零中心","start_time":0,"closing_time":0},{"shop_name":"广州批零中心","start_time":0,"closing_time":0}]
     */

    private int code;
    private String message;
    private List<DataEntity> data;

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

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * shop_name : 广州批零中心
         * start_time : 0
         * closing_time : 0
         */

        private String shop_name;
        private int shop_id;
        private int start_time;             // 开始时间
        private int closing_time;           // 结算时间
        private int sh_status;                 // 有没有开通账期

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public int getStart_time() {
            return start_time;
        }

        public void setStart_time(int start_time) {
            this.start_time = start_time;
        }

        public int getClosing_time() {
            return closing_time;
        }

        public void setClosing_time(int closing_time) {
            this.closing_time = closing_time;
        }

        public int getSh_status() {
            return sh_status;
        }

        public void setSh_status(int sh_status) {
            this.sh_status = sh_status;
        }
    }
}
