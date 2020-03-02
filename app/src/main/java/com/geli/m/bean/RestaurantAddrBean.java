package com.geli.m.bean;

import java.util.List;

/**
 * author:  shen
 * date:    2019/7/30
 */
public class RestaurantAddrBean {


    /**
     * code : 100
     * message : ok
     * data : [{"area_id":2,"code":"11","parent_id":"1","area_name":"北京市","area_type":1},{"area_id":3,"code":"1101","parent_id":"11","area_name":"北京市","area_type":2},{"area_id":17359,"code":"36","parent_id":"1","area_name":"江西省","area_type":1},{"area_id":17360,"code":"3601","parent_id":"36","area_name":"南昌市","area_type":2},{"area_id":28240,"code":"44","parent_id":"1","area_name":"广东省","area_type":1},{"area_id":28421,"code":"4402","parent_id":"44","area_name":"韶关市","area_type":2},{"area_id":29890,"code":"4420","parent_id":"44","area_name":"中山市","area_type":2}]
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

    public static class DataBean {
        /**
         * area_id : 2
         * code : 11
         * parent_id : 1
         * area_name : 北京市
         * area_type : 1
         */

        private int area_id;
        private String code;
        private String parent_id;
        private String area_name;
        private int area_type;

        private boolean isSelect = false;       // 非后台数据

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }


        public int getArea_id() {
            return area_id;
        }

        public void setArea_id(int area_id) {
            this.area_id = area_id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public int getArea_type() {
            return area_type;
        }

        public void setArea_type(int area_type) {
            this.area_type = area_type;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "area_id=" + area_id +
                    ", code='" + code + '\'' +
                    ", parent_id='" + parent_id + '\'' +
                    ", area_name='" + area_name + '\'' +
                    ", area_type=" + area_type +
                    '}';
        }
    }
}
