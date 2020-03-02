package com.geli.m.bean;

import java.util.List;

/**
 * Created by Steam_l on 2018/3/21.
 */

public class FindListBean {
    /**
     * code : 100
     * data : [{"view_num":7,"like_num":1,"cover_url":["http://p5av8ynvt.bkt.clouddn.com/af8b3e0e2143bb5b670ffa817866a325768875.mp4?vframe/jpg/offset/1"],"find_id":12,"create_time":1521183011,"author_res":{"author_name":"测试作者C","author_icon":"upload/author/author_152108530542189.jpg","author_id":23},"view_type":1,"type":1,"article_title":"六和六和六和六和","video_url":"http://p5av8ynvt.bkt.clouddn.com/af8b3e0e2143bb5b670ffa817866a325768875.mp4","is_like":0,"cat_id":3,"author_id":23},{"view_num":0,"like_num":0,"cover_url":["upload/banner/20180227/z15197023806363.jpg","upload/banner/20180227/z15197023806363.jpg","upload/banner/20180227/z15197023806363.jpg"],"find_id":10,"create_time":1521168264,"author_res":{"author_name":"测试作者D","author_icon":"upload/author/author_152108531964014.jpg","author_id":24},"view_type":2,"type":2,"article_title":"六和六和六和","video_url":null,"is_like":0,"cat_id":5,"author_id":24},{"view_num":0,"like_num":0,"cover_url":[null],"find_id":6,"create_time":1521006229,"author_res":null,"view_type":1,"type":1,"article_title":"好吃的测试商品","video_url":"http://p5av8ynvt.bkt.clouddn.com/d41d8cd98f00b204e9800998ecf8427e341397.mp4","is_like":0,"cat_id":2,"author_id":3},{"view_num":1,"like_num":0,"cover_url":[null],"find_id":7,"create_time":1521006229,"author_res":null,"view_type":1,"type":1,"article_title":"测试商品标题1","video_url":"http://p5av8ynvt.bkt.clouddn.com/d41d8cd98f00b204e9800998ecf8427e393609.mp4","is_like":0,"cat_id":2,"author_id":3},{"view_num":0,"like_num":0,"cover_url":["upload/find/20180316/find_152118156834439.jpg","upload/banner/20180227/z15197023806363.jpg","upload/banner/20180227/z15197023806363.jpg"],"find_id":1,"create_time":1519805007,"author_res":{"author_name":"测试作者D","author_icon":"upload/author/author_152108531964014.jpg","author_id":24},"view_type":2,"type":2,"article_title":"烧麦六和","video_url":null,"is_like":0,"cat_id":6,"author_id":24},{"view_num":3,"like_num":0,"cover_url":["upload/banner/20180227/z15197023806363.jpg"],"find_id":2,"create_time":1519805007,"author_res":null,"view_type":1,"type":2,"article_title":"烧麦2","video_url":"","is_like":0,"cat_id":3,"author_id":181},{"view_num":0,"like_num":0,"cover_url":["upload/banner/20180227/z15197023806363.jpg"],"find_id":3,"create_time":1519805007,"author_res":null,"view_type":1,"type":2,"article_title":"烧麦3","video_url":"","is_like":0,"cat_id":4,"author_id":182},{"view_num":0,"like_num":0,"cover_url":["upload/banner/20180227/z15197023806363.jpg"],"find_id":4,"create_time":1519805007,"author_res":null,"view_type":1,"type":2,"article_title":"烧麦4","video_url":"","is_like":0,"cat_id":2,"author_id":182},{"view_num":0,"like_num":0,"cover_url":["upload/banner/20180227/z15197023806363.jpg"],"find_id":5,"create_time":1519805007,"author_res":null,"view_type":1,"type":2,"article_title":"烧麦5","video_url":"","is_like":0,"cat_id":3,"author_id":182},{"view_num":2100,"like_num":500,"cover_url":["http://p5av8ynvt.bkt.clouddn.com/d41d8cd98f00b204e9800998ecf8427e230517.mp4?vframe/jpg/offset/1"],"find_id":8,"create_time":1519805007,"author_res":{"author_name":"测试作者A","author_icon":"upload/author/author_152108528265137.jpg","author_id":21},"view_type":1,"type":1,"article_title":"测试商品标题2","video_url":"http://p5av8ynvt.bkt.clouddn.com/d41d8cd98f00b204e9800998ecf8427e230517.mp4","is_like":0,"cat_id":2,"author_id":21},{"view_num":100,"like_num":60,"cover_url":["upload/find/20180316/find_152118156834439.jpg"],"find_id":9,"create_time":1519805007,"author_res":{"author_name":"测试作者C","author_icon":"upload/author/author_152108530542189.jpg","author_id":23},"view_type":1,"type":1,"article_title":"测试商品标题3","video_url":"http://p5av8ynvt.bkt.clouddn.com/cf1dc0b7daded0a4c671ec8151da00ec940481.mp4","is_like":0,"cat_id":2,"author_id":23}]
     * message : ok
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
         * view_num : 7
         * like_num : 1
         * cover_url : ["http://p5av8ynvt.bkt.clouddn.com/af8b3e0e2143bb5b670ffa817866a325768875.mp4?vframe/jpg/offset/1"]
         * find_id : 12
         * create_time : 1521183011
         * author_res : {"author_name":"测试作者C","author_icon":"upload/author/author_152108530542189.jpg","author_id":23}
         * view_type : 1
         * type : 1
         * article_title : 六和六和六和六和
         * video_url : http://p5av8ynvt.bkt.clouddn.com/af8b3e0e2143bb5b670ffa817866a325768875.mp4
         * is_like : 0
         * cat_id : 3
         * author_id : 23
         */
        private int view_num;
        private int like_num;
        private List<String> cover_url;
        private int find_id;
        private int create_time;
        private AuthorResEntity author_res;
        private int view_type;//1 视频 2文章
        private int type;//1一图 2多图
        private String article_title;
        private String video_url;
        private int is_like;
        private int cat_id;
        private int author_id;

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

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public void setAuthor_res(AuthorResEntity author_res) {
            this.author_res = author_res;
        }

        public void setView_type(int view_type) {
            this.view_type = view_type;
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

        public int getCreate_time() {
            return create_time;
        }

        public AuthorResEntity getAuthor_res() {
            return author_res;
        }

        public int getView_type() {
            return view_type;
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

        public static class AuthorResEntity {
            /**
             * author_name : 测试作者C
             * author_icon : upload/author/author_152108530542189.jpg
             * author_id : 23
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
}
