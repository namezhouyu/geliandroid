package com.geli.m.bean;

import java.util.List;

/**
 * author:  shen
 * date:    2018/11/5
 */
public class ShopAPDetailBean {


    /**
     * code : 100
     * message : ok
     * data : {"shop_name":"广州批零中心","shop_img_thumb":"upload/shops/20180108/1515399728.png","treaty_url":"upload/shops/treaty/7205ee770b3fd7eec908b41541497614.jpg","status":3,"sh_detail":[10,75],"min":"101.00","max":"1001.00","process":"https://www.baidu.com","agreement":"http://php.gl.com/store/article/articledetail/art_id/13102.html"}
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
         * shop_name : 广州批零中心
         * shop_img_thumb : upload/shops/20180108/1515399728.png
         * treaty_url : upload/shops/treaty/7205ee770b3fd7eec908b41541497614.jpg
         * status : 3
         * sh_detail : [10,75]
         * min : 101.00
         * max : 1001.00
         * process : https://www.baidu.com
         * agreement : http://php.gl.com/store/article/articledetail/art_id/13102.html
         */

        private String shop_name;
        private String shop_img_thumb;
        private String treaty_url;
        private int status;
        private String min;
        private String max;
        private String process;
        private String agreement;
        private List<Integer> sh_detail;

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_img_thumb() {
            return shop_img_thumb;
        }

        public void setShop_img_thumb(String shop_img_thumb) {
            this.shop_img_thumb = shop_img_thumb;
        }

        public String getTreaty_url() {
            return treaty_url;
        }

        public void setTreaty_url(String treaty_url) {
            this.treaty_url = treaty_url;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getProcess() {
            return process;
        }

        public void setProcess(String process) {
            this.process = process;
        }

        public String getAgreement() {
            return agreement;
        }

        public void setAgreement(String agreement) {
            this.agreement = agreement;
        }

        public List<Integer> getSh_detail() {
            return sh_detail;
        }

        public void setSh_detail(List<Integer> sh_detail) {
            this.sh_detail = sh_detail;
        }
    }
}
