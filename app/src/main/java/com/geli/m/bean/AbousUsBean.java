package com.geli.m.bean;

/**
 * Created by Administrator on 2017/11/6.
 */

public class AbousUsBean {
    /**
     * code : 100
     * data : {"code":"aboutus","art_title":"关于我们","content":" 阿什顿发的沙发的发第三方啊等水哥手打工时费撒地方阿斯顿发撒地方阿斯顿发阿叔<\/p>"}
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
         * code : aboutus
         * art_title : 关于我们
         * content :  阿什顿发的沙发的发第三方啊等水哥手打工时费撒地方阿斯顿发撒地方阿斯顿发阿叔</p>
         */
        private String code;
        private String art_title;
        private String content;

        public void setCode(String code) {
            this.code = code;
        }

        public void setArt_title(String art_title) {
            this.art_title = art_title;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCode() {
            return code;
        }

        public String getArt_title() {
            return art_title;
        }

        public String getContent() {
            return content;
        }
    }
}
