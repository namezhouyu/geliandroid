package com.geli.m.bean;

import java.util.List;

/**
 * Created by Steam_l on 2018/3/27.
 */

public class LogisticsDetailsBean {

    /**
     * code : 100
     * data : {"shipping_id":"201803271139066816","goods_number":1,"user_id":7217,"goods_thumb":"upload/old_img/store_100008/goods_13/small_201504291343336606.jpg","logistics":[{"add_time":"1520307878","content":"hgkhkhgghg"},{"add_time":"1520394283","content":"bhkgjhk"},{"add_time":"1521690289","content":"kh"},{"add_time":"1521784613","content":"xzvcasdf"},{"add_time":"1521871026","content":"阿斯利的开发计划"},{"add_time":"1521957432","content":"阿斯蒂芬"},{"add_time":"1522043845","content":"阿萨德发送到发生的发射点发阿斯蒂芬"},{"add_time":"1522130252","content":"子线程vsdfqwefasdadfqwef"},{"add_time":"1522216658","content":"请问废弃物而且为人妻为人妻为人全文阿萨德发送到发生阿萨德发射点发"}],"tel":"020-81339960","good_str":"1755,116","order_sn":"GL20180327113847035930"}
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

    public static class DataEntity {
        /**
         * shipping_id : 201803271139066816
         * goods_number : 1
         * user_id : 7217
         * goods_thumb : upload/old_img/store_100008/goods_13/small_201504291343336606.jpg
         * logistics : [{"add_time":"1520307878","content":"hgkhkhgghg"},{"add_time":"1520394283","content":"bhkgjhk"},{"add_time":"1521690289","content":"kh"},{"add_time":"1521784613","content":"xzvcasdf"},{"add_time":"1521871026","content":"阿斯利的开发计划"},{"add_time":"1521957432","content":"阿斯蒂芬"},{"add_time":"1522043845","content":"阿萨德发送到发生的发射点发阿斯蒂芬"},{"add_time":"1522130252","content":"子线程vsdfqwefasdadfqwef"},{"add_time":"1522216658","content":"请问废弃物而且为人妻为人妻为人全文阿萨德发送到发生阿萨德发射点发"}]
         * tel : 020-81339960
         * good_str : 1755,116
         * order_sn : GL20180327113847035930
         */
        private String shipping_id;
        private int goods_number;
        private int user_id;
        private String goods_thumb;
        private List<LogisticsEntity> logistics;
        private String tel;
        private String good_str;
        private String order_sn;

        public void setShipping_id(String shipping_id) {
            this.shipping_id = shipping_id;
        }

        public void setGoods_number(int goods_number) {
            this.goods_number = goods_number;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public void setGoods_thumb(String goods_thumb) {
            this.goods_thumb = goods_thumb;
        }

        public void setLogistics(List<LogisticsEntity> logistics) {
            this.logistics = logistics;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public void setGood_str(String good_str) {
            this.good_str = good_str;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getShipping_id() {
            return shipping_id;
        }

        public int getGoods_number() {
            return goods_number;
        }

        public int getUser_id() {
            return user_id;
        }

        public String getGoods_thumb() {
            return goods_thumb;
        }

        public List<LogisticsEntity> getLogistics() {
            return logistics;
        }

        public String getTel() {
            return tel;
        }

        public String getGood_str() {
            return good_str;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public static class LogisticsEntity {
            /**
             * add_time : 1520307878
             * content : hgkhkhgghg
             */
            private String add_time;
            private String content;

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAdd_time() {
                return add_time;
            }

            public String getContent() {
                return content;
            }
        }
    }
}
