package com.geli.m.bean;

import java.util.List;

/**
 * author:  shen
 * date:    2019/3/29
 */
public class RestaurantShopScreen {

    /**
     * code : 100
     * message : ok
     * data : [{"screen_name":"综合排序","screen_key":"sort","data":[{"value":0,"name":"综合"},{"value":1,"name":"销量"}]},{"screen_name":"分类","screen_key":"cat","data":[{"value":4,"name":"水产品"},{"value":6,"name":"果蔬类"},{"value":466,"name":"水果"}]}]
     */

    private int code;
    private String message;
    private List<DataBeanX> data;

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

    public List<DataBeanX> getData() {
        return data;
    }

    public void setData(List<DataBeanX> data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * screen_name : 综合排序
         * screen_key : sort
         * data : [{"value":0,"name":"综合"},{"value":1,"name":"销量"}]
         */

        private String screen_name;
        private String screen_key;
        private List<DataBean> data;

        public String getScreen_name() {
            return screen_name;
        }

        public void setScreen_name(String screen_name) {
            this.screen_name = screen_name;
        }

        public String getScreen_key() {
            return screen_key;
        }

        public void setScreen_key(String screen_key) {
            this.screen_key = screen_key;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * value : 0
             * name : 综合
             */

            private int value;
            private String name;

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
