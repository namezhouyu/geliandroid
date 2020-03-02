package com.geli.m.bean;

import java.util.List;

/**
 * Created by Steam_l on 2018/3/10.
 */

public class MemberBean {
    /**
     * code : 100
     * data : {"integral":{"experience_grade":0,"rank_name":"青铜","rank_fraction":100,"rank_img":"php.gl.com/public/static/admin/images/qingtong.png","current_integral":0},"user":{"truename":"刘彬","avatar":""},"article":[{"art_url":"php.gl.com/index.php/store/article/articledetail/art_id/13096.html","art_title":"会员成长规则"},{"art_url":"php.gl.com/index.php/store/article/articledetail/art_id/13097.html","art_title":"积分使用规则"}]}
     * message : 成功!
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
         * integral : {"experience_grade":0,"rank_name":"青铜","rank_fraction":100,"rank_img":"php.gl.com/public/static/admin/images/qingtong.png","current_integral":0}
         * user : {"truename":"刘彬","avatar":""}
         * article : [{"art_url":"php.gl.com/index.php/store/article/articledetail/art_id/13096.html","art_title":"会员成长规则"},{"art_url":"php.gl.com/index.php/store/article/articledetail/art_id/13097.html","art_title":"积分使用规则"}]
         */
        private IntegralEntity integral;
        private UserEntity user;
        private List<ArticleEntity> article;

        public void setIntegral(IntegralEntity integral) {
            this.integral = integral;
        }

        public void setUser(UserEntity user) {
            this.user = user;
        }

        public void setArticle(List<ArticleEntity> article) {
            this.article = article;
        }

        public IntegralEntity getIntegral() {
            return integral;
        }

        public UserEntity getUser() {
            return user;
        }

        public List<ArticleEntity> getArticle() {
            return article;
        }

        public static class IntegralEntity {
            /**
             * experience_grade : 0
             * rank_name : 青铜
             * rank_fraction : 100
             * rank_img : php.gl.com/public/static/admin/images/qingtong.png
             * current_integral : 0
             */
            private int experience_grade;//当前
            private String rank_name;
            private int rank_fraction;//总
            private String rank_img;
            private int current_integral;//积分

            public void setExperience_grade(int experience_grade) {
                this.experience_grade = experience_grade;
            }

            public void setRank_name(String rank_name) {
                this.rank_name = rank_name;
            }

            public void setRank_fraction(int rank_fraction) {
                this.rank_fraction = rank_fraction;
            }

            public void setRank_img(String rank_img) {
                this.rank_img = rank_img;
            }

            public void setCurrent_integral(int current_integral) {
                this.current_integral = current_integral;
            }

            public int getExperience_grade() {
                return experience_grade;
            }

            public String getRank_name() {
                return rank_name;
            }

            public int getRank_fraction() {
                return rank_fraction;
            }

            public String getRank_img() {
                return rank_img;
            }

            public int getCurrent_integral() {
                return current_integral;
            }
        }

        public static class UserEntity {
            /**
             * truename : 刘彬
             * avatar :
             */
            private String truename;
            private String avatar;

            public void setTruename(String truename) {
                this.truename = truename;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getTruename() {
                return truename;
            }

            public String getAvatar() {
                return avatar;
            }
        }

        public static class ArticleEntity {
            /**
             * art_url : php.gl.com/index.php/store/article/articledetail/art_id/13096.html
             * art_title : 会员成长规则
             */
            private String art_url;
            private String art_title;

            public void setArt_url(String art_url) {
                this.art_url = art_url;
            }

            public void setArt_title(String art_title) {
                this.art_title = art_title;
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
