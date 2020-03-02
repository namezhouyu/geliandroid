package com.geli.m.bean;


import java.util.List;

/**
 * 售后跟踪详情
 */
public class AfterSoldDetailsBean {

    private int code;
    private String message;
    private DataEntity data;

    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
    public void setData(DataEntity data){
        this.data = data;
    }
    public DataEntity getData(){
        return this.data;
    }

    public static class DataEntity {
        private int id;

        /** 订单号 */
        private int order_id;
        /** 联系人 */
        private String contact;
        /** 联系人电话 */
        private String contact_method;
        /** 售后状态 */
        private int type;
        /** 提交的售后内容 */
        private String content;
        /** 提交的售后图片 */
        private List<String> img;
        /** 提交的时间 */
        private int add_time;

        private List<Dispose_res> dispose_res;

        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setOrder_id(int order_id){
            this.order_id = order_id;
        }
        public int getOrder_id(){
            return this.order_id;
        }
        public void setContact(String contact){
            this.contact = contact;
        }
        public String getContact(){
            return this.contact;
        }
        public void setContact_method(String contact_method){
            this.contact_method = contact_method;
        }
        public String getContact_method(){
            return this.contact_method;
        }
        public void setType(int type){
            this.type = type;
        }
        public int getType(){
            return this.type;
        }
        public void setContent(String content){
            this.content = content;
        }
        public String getContent(){
            return this.content;
        }
        public void setImg(List<String> img){
            this.img = img;
        }
        public List<String> getImg(){
            return this.img;
        }
        public void setAdd_time(int add_time){
            this.add_time = add_time;
        }
        public int getAdd_time(){
            return this.add_time;
        }
        public void setDispose_res(List<Dispose_res> dispose_res){
            this.dispose_res = dispose_res;
        }
        public List<Dispose_res> getDispose_res(){
            return this.dispose_res;
        }

        public static class Dispose_res {
            private int add_time;

            private int action;

            private String dispose_detail;

            public void setAdd_time(int add_time){
                this.add_time = add_time;
            }
            public int getAdd_time(){
                return this.add_time;
            }
            public void setAction(int action){
                this.action = action;
            }
            public int getAction(){
                return this.action;
            }
            public void setDispose_detail(String dispose_detail){
                this.dispose_detail = dispose_detail;
            }
            public String getDispose_detail(){
                return this.dispose_detail;
            }
        }
    }


    @Override
    public String toString() {
        return "AfterSoldDetailsBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
