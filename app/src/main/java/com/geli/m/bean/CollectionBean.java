package com.geli.m.bean;

import java.util.List;

/**
 * Created by Steam_l on 2018/4/2.
 */

public class CollectionBean {
    /**
     * code : 100
     * data : [{"article_title":"六和六和六和六和","cover_url":"http://p5av8ynvt.bkt.clouddn.com/af8b3e0e2143bb5b670ffa817866a325768875.mp4?vframe/jpg/offset/1","video_url":"http://p5av8ynvt.bkt.clouddn.com/af8b3e0e2143bb5b670ffa817866a325768875.mp4","find_id":12,"article_content":" <p>六和六和六和六和六和六和六和六和六和<\/p>"}]
     * message : 成功！
     */
    private int code;
    private List<DataEntity> data;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class DataEntity {
        /**
         * article_title : 六和六和六和六和
         * cover_url : http://p5av8ynvt.bkt.clouddn.com/af8b3e0e2143bb5b670ffa817866a325768875.mp4?vframe/jpg/offset/1
         * video_url : http://p5av8ynvt.bkt.clouddn.com/af8b3e0e2143bb5b670ffa817866a325768875.mp4
         * find_id : 12
         * article_content :  <p>六和六和六和六和六和六和六和六和六和</p>
         */
        private String article_title;
        private String cover_url;
        private String video_url;
        private int find_id;
        private String article_content;
        /**
         * shop_id : 100059
         * shop_img_thumb : default.jpg
         * shop_name : 怀来正大食品
         * shop_intro :
         */
        private int shop_id;
        private String shop_img_thumb;
        private String shop_name;
        private String shop_intro;
        /**
         * goods_name : 佳尚新蔬 青豆 500g*20/件
         * shop_price : 100.00
         * goods_thumb : upload/201703/thumb_img/35074_thumb_G_1490604401697.jpg
         * goods_id : 1755
         */
        private String goods_name;
        private String shop_price;
        private String goods_thumb;
        private int goods_id;

        public void setArticle_title(String article_title) {
            this.article_title = article_title;
        }

        public void setCover_url(String cover_url) {
            this.cover_url = cover_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public void setFind_id(int find_id) {
            this.find_id = find_id;
        }

        public void setArticle_content(String article_content) {
            this.article_content = article_content;
        }

        public String getArticle_title() {
            return article_title;
        }

        public String getCover_url() {
            return cover_url;
        }

        public String getVideo_url() {
            return video_url;
        }

        public int getFind_id() {
            return find_id;
        }

        public String getArticle_content() {
            return article_content;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public void setShop_img_thumb(String shop_img_thumb) {
            this.shop_img_thumb = shop_img_thumb;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public void setShop_intro(String shop_intro) {
            this.shop_intro = shop_intro;
        }

        public int getShop_id() {
            return shop_id;
        }

        public String getShop_img_thumb() {
            return shop_img_thumb;
        }

        public String getShop_name() {
            return shop_name;
        }

        public String getShop_intro() {
            return shop_intro;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public void setGoods_thumb(String goods_thumb) {
            this.goods_thumb = goods_thumb;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public String getShop_price() {
            return shop_price;
        }

        public String getGoods_thumb() {
            return goods_thumb;
        }

        public int getGoods_id() {
            return goods_id;
        }
    }
}
