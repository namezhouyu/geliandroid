package com.geli.m.bean;

/**
 * Created by pks on 2017/6/29.
 */

public class AppVersion {
    /**
     * code : 100
     * message : ok
     * data : {"versions":4,"url":"120.25.157.180/upload/android/files/app-release.apk"}
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
         * versions : 4
         * url : 120.25.157.180/upload/android/files/app-release.apk
         */

        private int versions;
        private String url;
        private String desc;
        private int is_update;

        public int getVersions() {
            return versions;
        }

        public void setVersions(int versions) {
            this.versions = versions;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getIs_update() {
            return is_update;
        }

        public void setIs_update(int is_update) {
            this.is_update = is_update;
        }
    }
}
