package com.geli.m.bean;

/**
 * Created by l on 2018/4/12.
 */

public class TermsBean {
    /**
     * code : 100
     * data : {"art_id":13100,"content":"<p>团购协议<\/p>"}
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
         * art_id : 13100
         * content : <p>团购协议</p>
         */
        private int art_id;
        private String content;

        public void setArt_id(int art_id) {
            this.art_id = art_id;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getArt_id() {
            return art_id;
        }

        public String getContent() {
            return content;
        }
    }
}
