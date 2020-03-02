package com.geli.m.bean;

import java.io.Serializable;

/**
 * Created by pks on 2017/6/27.
 */

public class RegistrationProtocolBean implements Serializable {

    /**
     * code : 100
     * message : ok
     * data : {"art_url":"120.25.157.180/public/index.php/store/article/articledetail/art_id/13070.html"}
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
         * art_url : 120.25.157.180/public/index.php/store/article/articledetail/art_id/13070.html
         */

        private String art_url;

        public String getArt_url() {
            return art_url;
        }

        public void setArt_url(String art_url) {
            this.art_url = art_url;
        }
    }
}
