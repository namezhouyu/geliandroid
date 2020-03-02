package com.geli.m.bean;

import java.util.List;

/**
 * Created by Steam_l on 2018/3/8.
 */

public class HelpCenterBean {
    /**
     * code : 100
     * data : {"cat_list":[{"cat_img":"/uploads/article_category/20170609\\8ad86b88bb673ce9cdd7d084168d4a7c.png","cat_name":"注册登录","cat_id":9},{"cat_img":"","cat_name":"支付问题","cat_id":18},{"cat_img":"/uploads/article_category/20170609\\cf72d6fc8f636aab9e7e5ebd7f069c9c.png","cat_name":"操作指南","cat_id":11},{"cat_img":"/uploads/article_category/20170609\\e006c187a41b918500647e41c49b26e7.png","cat_name":"联系客服","cat_id":12}],"help_list":[{"art_id":13064,"art_url":"php.gl.com/index.php/store/article/articledetail/art_id/13064.html","art_title":"如何添加/删除常用地址？"},{"art_id":13066,"art_url":"php.gl.com/index.php/store/article/articledetail/art_id/13066.html","art_title":"优惠券如何查看和使用"},{"art_id":13071,"art_url":"php.gl.com/index.php/store/article/articledetail/art_id/13071.html","art_title":"格利食品网收费模式调整公告"},{"art_id":13067,"art_url":"php.gl.com/index.php/store/article/articledetail/art_id/13067.html","art_title":"充值怎么操作？"},{"art_id":13062,"art_url":"php.gl.com/index.php/store/article/articledetail/art_id/13062.html","art_title":"如何登陆格利食品网？"}]}
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
         * cat_list : [{"cat_img":"/uploads/article_category/20170609\\8ad86b88bb673ce9cdd7d084168d4a7c.png","cat_name":"注册登录","cat_id":9},{"cat_img":"","cat_name":"支付问题","cat_id":18},{"cat_img":"/uploads/article_category/20170609\\cf72d6fc8f636aab9e7e5ebd7f069c9c.png","cat_name":"操作指南","cat_id":11},{"cat_img":"/uploads/article_category/20170609\\e006c187a41b918500647e41c49b26e7.png","cat_name":"联系客服","cat_id":12}]
         * help_list : [{"art_id":13064,"art_url":"php.gl.com/index.php/store/article/articledetail/art_id/13064.html","art_title":"如何添加/删除常用地址？"},{"art_id":13066,"art_url":"php.gl.com/index.php/store/article/articledetail/art_id/13066.html","art_title":"优惠券如何查看和使用"},{"art_id":13071,"art_url":"php.gl.com/index.php/store/article/articledetail/art_id/13071.html","art_title":"格利食品网收费模式调整公告"},{"art_id":13067,"art_url":"php.gl.com/index.php/store/article/articledetail/art_id/13067.html","art_title":"充值怎么操作？"},{"art_id":13062,"art_url":"php.gl.com/index.php/store/article/articledetail/art_id/13062.html","art_title":"如何登陆格利食品网？"}]
         */
        private List<CatListEntity> cat_list;
        private List<HelpListEntity> help_list;

        public void setCat_list(List<CatListEntity> cat_list) {
            this.cat_list = cat_list;
        }

        public void setHelp_list(List<HelpListEntity> help_list) {
            this.help_list = help_list;
        }

        public List<CatListEntity> getCat_list() {
            return cat_list;
        }

        public List<HelpListEntity> getHelp_list() {
            return help_list;
        }

        public static class CatListEntity {
            /**
             * cat_img : /uploads/article_category/20170609\8ad86b88bb673ce9cdd7d084168d4a7c.png
             * cat_name : 注册登录
             * cat_id : 9
             */
            private String cat_img;
            private String cat_name;
            private int cat_id;

            public void setCat_img(String cat_img) {
                this.cat_img = cat_img;
            }

            public void setCat_name(String cat_name) {
                this.cat_name = cat_name;
            }

            public void setCat_id(int cat_id) {
                this.cat_id = cat_id;
            }

            public String getCat_img() {
                return cat_img;
            }

            public String getCat_name() {
                return cat_name;
            }

            public int getCat_id() {
                return cat_id;
            }
        }

        public static class HelpListEntity {
            /**
             * art_id : 13064
             * art_url : php.gl.com/index.php/store/article/articledetail/art_id/13064.html
             * art_title : 如何添加/删除常用地址？
             */
            private int art_id;
            private String art_url;
            private String art_title;

            public void setArt_id(int art_id) {
                this.art_id = art_id;
            }

            public void setArt_url(String art_url) {
                this.art_url = art_url;
            }

            public void setArt_title(String art_title) {
                this.art_title = art_title;
            }

            public int getArt_id() {
                return art_id;
            }

            public String getArt_url() {
                return art_url;
            }

            public String getArt_title() {
                return art_title;
            }
        }
    }
}
