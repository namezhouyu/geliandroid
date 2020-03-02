package com.geli.m.bean;

import java.util.List;

/**
 * Created by Steam_l on 2018/3/21.
 */

public class FindDetailsBean {
    /**
     * code : 100
     * data : {"find_res":{"view_num":0,"like_num":0,"cover_url":["upload/banner/20180227/z15197023806363.jpg","upload/banner/20180227/z15197023806363.jpg","upload/banner/20180227/z15197023806363.jpg"],"find_id":10,"author_res":{"author_name":"测试作者D","author_icon":"upload/author/author_152108531964014.jpg","author_id":24},"article_content":null,"goods_id":338,"type":2,"article_title":"六和六和六和","video_url":null,"is_like":0,"cat_id":5,"author_id":24},"goods_res":{"goods_intro":"","shop_price":"96.00","goods_thumb":"upload/201702/thumb_img/34929_thumb_G_1486517863169.jpg","goods_id":338,"goods_type":1,"specifications":[{"value":"默认","key":"规格"}]},"about_res":[{"view_num":0,"like_num":0,"article_title":"烧麦六和","cover_url":"upload/find/20180316/find_152118156834439.jpg;upload/banner/20180227/z15197023806363.jpg;upload/banner/20180227/z15197023806363.jpg","video_url":null,"find_id":1,"article_content":" 撒到发<\/p>","cat_id":6,"goods_id":337,"author_id":24,"type":2},{"view_num":3,"like_num":0,"article_title":"烧麦2","cover_url":"upload/banner/20180227/z15197023806363.jpg","video_url":"","find_id":2,"article_content":"","cat_id":3,"goods_id":6,"author_id":181,"type":2},{"view_num":0,"like_num":0,"article_title":"烧麦3","cover_url":"upload/banner/20180227/z15197023806363.jpg","video_url":"","find_id":3,"article_content":"","cat_id":4,"goods_id":7,"author_id":182,"type":2},{"view_num":0,"like_num":0,"article_title":"烧麦4","cover_url":"upload/banner/20180227/z15197023806363.jpg","video_url":"","find_id":4,"article_content":"","cat_id":2,"goods_id":8,"author_id":182,"type":2},{"view_num":0,"like_num":0,"article_title":"烧麦5","cover_url":"upload/banner/20180227/z15197023806363.jpg","video_url":"","find_id":5,"article_content":"","cat_id":3,"goods_id":9,"author_id":182,"type":2},{"view_num":1,"like_num":0,"article_title":"六和六和六和","cover_url":"upload/banner/20180227/z15197023806363.jpg;upload/banner/20180227/z15197023806363.jpg;upload/banner/20180227/z15197023806363.jpg","video_url":null,"find_id":10,"article_content":null,"cat_id":5,"goods_id":338,"author_id":24,"type":2}]}
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
         * find_res : {"view_num":0,"like_num":0,"cover_url":["upload/banner/20180227/z15197023806363.jpg","upload/banner/20180227/z15197023806363.jpg","upload/banner/20180227/z15197023806363.jpg"],"find_id":10,"author_res":{"author_name":"测试作者D","author_icon":"upload/author/author_152108531964014.jpg","author_id":24},"article_content":null,"goods_id":338,"type":2,"article_title":"六和六和六和","video_url":null,"is_like":0,"cat_id":5,"author_id":24}
         * goods_res : {"goods_intro":"","shop_price":"96.00","goods_thumb":"upload/201702/thumb_img/34929_thumb_G_1486517863169.jpg","goods_id":338,"goods_type":1,"specifications":[{"value":"默认","key":"规格"}]}
         * about_res : [{"view_num":0,"like_num":0,"article_title":"烧麦六和","cover_url":"upload/find/20180316/find_152118156834439.jpg;upload/banner/20180227/z15197023806363.jpg;upload/banner/20180227/z15197023806363.jpg","video_url":null,"find_id":1,"article_content":" 撒到发<\/p>","cat_id":6,"goods_id":337,"author_id":24,"type":2},{"view_num":3,"like_num":0,"article_title":"烧麦2","cover_url":"upload/banner/20180227/z15197023806363.jpg","video_url":"","find_id":2,"article_content":"","cat_id":3,"goods_id":6,"author_id":181,"type":2},{"view_num":0,"like_num":0,"article_title":"烧麦3","cover_url":"upload/banner/20180227/z15197023806363.jpg","video_url":"","find_id":3,"article_content":"","cat_id":4,"goods_id":7,"author_id":182,"type":2},{"view_num":0,"like_num":0,"article_title":"烧麦4","cover_url":"upload/banner/20180227/z15197023806363.jpg","video_url":"","find_id":4,"article_content":"","cat_id":2,"goods_id":8,"author_id":182,"type":2},{"view_num":0,"like_num":0,"article_title":"烧麦5","cover_url":"upload/banner/20180227/z15197023806363.jpg","video_url":"","find_id":5,"article_content":"","cat_id":3,"goods_id":9,"author_id":182,"type":2},{"view_num":1,"like_num":0,"article_title":"六和六和六和","cover_url":"upload/banner/20180227/z15197023806363.jpg;upload/banner/20180227/z15197023806363.jpg;upload/banner/20180227/z15197023806363.jpg","video_url":null,"find_id":10,"article_content":null,"cat_id":5,"goods_id":338,"author_id":24,"type":2}]
         */
        private FindResEntity find_res;
        private GoodsResEntity goods_res;
        private List<AboutResEntity> about_res;

        public void setFind_res(FindResEntity find_res) {
            this.find_res = find_res;
        }

        public void setGoods_res(GoodsResEntity goods_res) {
            this.goods_res = goods_res;
        }

        public void setAbout_res(List<AboutResEntity> about_res) {
            this.about_res = about_res;
        }

        public FindResEntity getFind_res() {
            return find_res;
        }

        public GoodsResEntity getGoods_res() {
            return goods_res;
        }

        public List<AboutResEntity> getAbout_res() {
            return about_res;
        }

        public static class FindResEntity {
            /**
             * view_num : 0
             * like_num : 0
             * cover_url : ["upload/banner/20180227/z15197023806363.jpg","upload/banner/20180227/z15197023806363.jpg","upload/banner/20180227/z15197023806363.jpg"]
             * find_id : 10
             * author_res : {"author_name":"测试作者D","author_icon":"upload/author/author_152108531964014.jpg","author_id":24}
             * article_content : null
             * goods_id : 338
             * type : 2
             * article_title : 六和六和六和
             * video_url : null
             * is_like : 0
             * cat_id : 5
             * author_id : 24
             */
            private int view_num;
            private int like_num;
            private List<String> cover_url;
            private int find_id;
            private AuthorResEntity author_res;
            private String article_content;
            private int goods_id;
            private int type;
            private String article_title;
            private String video_url;
            private int is_like;
            private int cat_id;
            private int author_id;
            private int is_collection;
            private String content_url;
            private String content_cover_url;

            public void setView_num(int view_num) {
                this.view_num = view_num;
            }

            public void setLike_num(int like_num) {
                this.like_num = like_num;
            }

            public void setCover_url(List<String> cover_url) {
                this.cover_url = cover_url;
            }

            public void setFind_id(int find_id) {
                this.find_id = find_id;
            }

            public void setAuthor_res(AuthorResEntity author_res) {
                this.author_res = author_res;
            }

            public void setArticle_content(String article_content) {
                this.article_content = article_content;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public void setType(int type) {
                this.type = type;
            }

            public void setArticle_title(String article_title) {
                this.article_title = article_title;
            }

            public void setVideo_url(String video_url) {
                this.video_url = video_url;
            }

            public void setIs_like(int is_like) {
                this.is_like = is_like;
            }

            public void setCat_id(int cat_id) {
                this.cat_id = cat_id;
            }

            public void setAuthor_id(int author_id) {
                this.author_id = author_id;
            }

            public int getView_num() {
                return view_num;
            }

            public int getLike_num() {
                return like_num;
            }

            public List<String> getCover_url() {
                return cover_url;
            }

            public int getFind_id() {
                return find_id;
            }

            public AuthorResEntity getAuthor_res() {
                return author_res;
            }

            public String getArticle_content() {
                return article_content;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public int getType() {
                return type;
            }

            public String getArticle_title() {
                return article_title;
            }

            public String getVideo_url() {
                return video_url;
            }

            public int getIs_like() {
                return is_like;
            }

            public int getCat_id() {
                return cat_id;
            }

            public int getAuthor_id() {
                return author_id;
            }

            public int getIs_collection() {
                return is_collection;
            }

            public void setIs_collection(int is_collection) {
                this.is_collection = is_collection;
            }

            public String getContent_url() {
                return content_url;
            }

            public void setContent_url(String content_url) {
                this.content_url = content_url;
            }

            public String getContent_cover_url() {
                return content_cover_url;
            }

            public void setContent_cover_url(String content_cover_url) {
                this.content_cover_url = content_cover_url;
            }

            public static class AuthorResEntity {
                /**
                 * author_name : 测试作者D
                 * author_icon : upload/author/author_152108531964014.jpg
                 * author_id : 24
                 */
                private String author_name;
                private String author_icon;
                private int author_id;

                public void setAuthor_name(String author_name) {
                    this.author_name = author_name;
                }

                public void setAuthor_icon(String author_icon) {
                    this.author_icon = author_icon;
                }

                public void setAuthor_id(int author_id) {
                    this.author_id = author_id;
                }

                public String getAuthor_name() {
                    return author_name;
                }

                public String getAuthor_icon() {
                    return author_icon;
                }

                public int getAuthor_id() {
                    return author_id;
                }
            }
        }

        public static class GoodsResEntity {
            /**
             * goods_intro :
             * shop_price : 96.00
             * goods_thumb : upload/201702/thumb_img/34929_thumb_G_1486517863169.jpg
             * goods_id : 338
             * goods_type : 1
             * specifications : [{"value":"默认","key":"规格"}]
             */
            private String goods_intro;
            private String shop_price;
            private String goods_thumb;
            private String goods_name;
            private int goods_id;
            private int goods_type;
            private List<SpecificationsEntity> specifications;

            public void setGoods_intro(String goods_intro) {
                this.goods_intro = goods_intro;
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

            public void setGoods_type(int goods_type) {
                this.goods_type = goods_type;
            }

            public void setSpecifications(List<SpecificationsEntity> specifications) {
                this.specifications = specifications;
            }

            public String getGoods_intro() {
                return goods_intro;
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

            public int getGoods_type() {
                return goods_type;
            }

            public List<SpecificationsEntity> getSpecifications() {
                return specifications;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public static class SpecificationsEntity {
                /**
                 * value : 默认
                 * key : 规格
                 */
                private String value;
                private String key;

                public void setValue(String value) {
                    this.value = value;
                }

                public void setKey(String key) {
                    this.key = key;
                }

                public String getValue() {
                    return value;
                }

                public String getKey() {
                    return key;
                }
            }
        }

        public static class AboutResEntity {
            /**
             * view_num : 0
             * like_num : 0
             * article_title : 烧麦六和
             * cover_url : upload/find/20180316/find_152118156834439.jpg;upload/banner/20180227/z15197023806363.jpg;upload/banner/20180227/z15197023806363.jpg
             * video_url : null
             * find_id : 1
             * article_content :  撒到发</p>
             * cat_id : 6
             * goods_id : 337
             * author_id : 24
             * type : 2
             */
            private String video_duration;
            private int view_num;
            private int like_num;
            private String article_title;
            private List<String> cover_url;
            private String video_url;
            private int find_id;
            private String article_content;
            private int cat_id;
            private int goods_id;
            private int author_id;
            private int type;
            private String content_cover_url;

            public void setView_num(int view_num) {
                this.view_num = view_num;
            }

            public void setLike_num(int like_num) {
                this.like_num = like_num;
            }

            public void setArticle_title(String article_title) {
                this.article_title = article_title;
            }

            public void setCover_url(List<String> cover_url) {
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

            public void setCat_id(int cat_id) {
                this.cat_id = cat_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public void setAuthor_id(int author_id) {
                this.author_id = author_id;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getView_num() {
                return view_num;
            }

            public int getLike_num() {
                return like_num;
            }

            public String getArticle_title() {
                return article_title;
            }

            public List<String> getCover_url() {
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

            public int getCat_id() {
                return cat_id;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public int getAuthor_id() {
                return author_id;
            }

            public int getType() {
                return type;
            }

            public String getVideo_duration() {
                return video_duration;
            }

            public void setVideo_duration(String video_duration) {
                this.video_duration = video_duration;
            }

            public String getContent_cover_url() {
                return content_cover_url;
            }

            public void setContent_cover_url(String content_cover_url) {
                this.content_cover_url = content_cover_url;
            }
        }
    }
}
