package com.geli.m.bean;

import com.geli.m.utils.GsonUtils;
import com.geli.m.utils.LogUtils;
import com.geli.m.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steam_l on 2018/1/17.
 * 食品详情类!
 */
public class GoodsDetailsBean {

    /**
     * code : 100
     * data : {"goods_intro":"500g*20包/箱","shop_longitude":"","goods_coll":1,"click_count":1372,"specifications":"[{\"key\":\"规格\",\"value\":\"默认\"}]","quantity_sold":1403,"goods_name":"佳尚新蔬 速冻蔬菜 速冻西兰花500g*20包/箱","origin_number":100,"shop_tel":"13957141028","shop_price":"105.00","cat_name":"花菜类","goods_id":84,"goods_desc":"<html><head><style>img{width:100%}<\/style><\/head><body><p><img title=\"西兰花4.jpg\" src=\"http://www.gelifood.com/data/files/store_100008/goods_142/201506190952228174.jpg\"/><\/p><\/body><\/html>","shop_name":"佳尚新蔬食品","is_show":1,"virtual_quantity_sold":1403,"shop_id":100008,"goods_number":9930,"goods_unit":"件","goods_thumb":"upload/201706/thumb_img/33362_thumb_G_1497410495762.jpg","shop_latitude":"","shop_type":0,"wap_desc":"","goods_type":1,"goods_spec":{"goods_sku":[{"sku_attr":"{1:29574}","price":"105","goods_id":84,"sku_id":29557,"inventory":9930,"sales":575}],"spec_attr":[{"res":[{"attr_name":"默认","attr_id":29574}],"spec_id":1,"spec_name":"规格"}]},"goods_comment":[{"goods_intro":"500g*20包/箱","com_grade":5,"user_id":250,"com_id":156,"com_content":"hao ","nickname":"hongson","avatar":"upload/users/25ca48617debf0b3e7db6884d1958f8d/20170929/1506677245.jpg","add_time":"2017.10.09"}],"goods_img":[{"thumb_url":"upload/201706/thumb_img/33362_thumb_P_1497335421795.jpg","img_url":"upload/201706/goods_img/33362_P_1497335421675.jpg"},{"thumb_url":"upload/201706/thumb_img/33362_thumb_P_1497410495793.jpg","img_url":"upload/201706/goods_img/33362_P_1497410495460.jpg"}]}
     * message :
     */
    private int code;
    private DataBean data;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public DataBean getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class DataBean {
        /**
         * goods_intro : 500g*20包/箱
         * shop_longitude :
         * goods_coll : 1
         * click_count : 1372
         * specifications : [{"key":"规格","value":"默认"}]
         * quantity_sold : 1403
         * goods_name : 佳尚新蔬 速冻蔬菜 速冻西兰花500g*20包/箱
         * origin_number : 100
         * shop_tel : 13957141028
         * shop_price : 105.00
         * cat_name : 花菜类
         * goods_id : 84
         * goods_desc : <html><head><style>img{width:100%}</style></head><body><p><img title="西兰花4.jpg" src="http://www.gelifood.com/data/files/store_100008/goods_142/201506190952228174.jpg"/></p></body></html>
         * shop_name : 佳尚新蔬食品
         * is_show : 1
         * virtual_quantity_sold : 1403
         * shop_id : 100008
         * goods_number : 9930
         * goods_unit : 件
         * goods_thumb : upload/201706/thumb_img/33362_thumb_G_1497410495762.jpg
         * shop_latitude :
         * shop_type : 0
         * wap_desc :
         * goods_type : 1
         * goods_spec : {"goods_sku":[{"sku_attr":"{1:29574}","price":"105","goods_id":84,"sku_id":29557,"inventory":9930,"sales":575}],"spec_attr":[{"res":[{"attr_name":"默认","attr_id":29574}],"spec_id":1,"spec_name":"规格"}]}
         * goods_comment : [{"goods_intro":"500g*20包/箱","com_grade":5,"user_id":250,"com_id":156,"com_content":"hao ","nickname":"hongson","avatar":"upload/users/25ca48617debf0b3e7db6884d1958f8d/20170929/1506677245.jpg","add_time":"2017.10.09"}]
         * goods_img : [{"thumb_url":"upload/201706/thumb_img/33362_thumb_P_1497335421795.jpg","img_url":"upload/201706/goods_img/33362_P_1497335421675.jpg"},{"thumb_url":"upload/201706/thumb_img/33362_thumb_P_1497410495793.jpg","img_url":"upload/201706/goods_img/33362_P_1497410495460.jpg"}]
         */
        private String goods_intro;
        private String shop_longitude;
        private int goods_coll;
        private int click_count;
        private String specifications;
        private int quantity_sold;
        private String goods_name;
        private int origin_number;
        private String shop_tel;
        private String shop_price;
        private String cat_name;
        private int goods_id;
        private String groupon_id;//团购ID
        private String goods_desc;
        private String shop_name;
        private int is_show;
        private int virtual_quantity_sold;
        private int shop_id;
        private int target_number;//总数
        private int sold_number;//参团
        private int surplus;//
        private int goods_number;
        private String goods_unit;
        private String goods_thumb;
        private String shop_latitude;
        private int shop_type;
        private String wap_desc;
        private int goods_type;
        private String rule_url;
        private GoodsCommentBean goods_comment;
        private ShStatusBean sh_status;
        private List<GoodsImgBean> goods_img;
        private int participants;
        private int sku_count;

        private int gs_id;          // 6:拼团/可零售

        public void setGoods_intro(String goods_intro) {
            this.goods_intro = goods_intro;
        }

        public void setShop_longitude(String shop_longitude) {
            this.shop_longitude = shop_longitude;
        }

        public void setGoods_coll(int goods_coll) {
            this.goods_coll = goods_coll;
        }

        public void setClick_count(int click_count) {
            this.click_count = click_count;
        }

        public void setSpecifications(String specifications) {
            this.specifications = specifications;
        }

        public void setQuantity_sold(int quantity_sold) {
            this.quantity_sold = quantity_sold;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public void setOrigin_number(int origin_number) {
            this.origin_number = origin_number;
        }

        public void setShop_tel(String shop_tel) {
            this.shop_tel = shop_tel;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public void setCat_name(String cat_name) {
            this.cat_name = cat_name;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public void setGoods_desc(String goods_desc) {
            this.goods_desc = goods_desc;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public void setIs_show(int is_show) {
            this.is_show = is_show;
        }

        public void setVirtual_quantity_sold(int virtual_quantity_sold) {
            this.virtual_quantity_sold = virtual_quantity_sold;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public void setGoods_number(int goods_number) {
            this.goods_number = goods_number;
        }

        public void setGoods_unit(String goods_unit) {
            this.goods_unit = goods_unit;
        }

        public void setGoods_thumb(String goods_thumb) {
            this.goods_thumb = goods_thumb;
        }

        public void setShop_latitude(String shop_latitude) {
            this.shop_latitude = shop_latitude;
        }

        public void setShop_type(int shop_type) {
            this.shop_type = shop_type;
        }

        public void setWap_desc(String wap_desc) {
            this.wap_desc = wap_desc;
        }

        public void setGoods_type(int goods_type) {
            this.goods_type = goods_type;
        }

        public void setGoods_comment(GoodsCommentBean goods_comment) {
            this.goods_comment = goods_comment;
        }

        public void setGoods_img(List<GoodsImgBean> goods_img) {
            this.goods_img = goods_img;
        }

        public String getGoods_intro() {
            return goods_intro;
        }

        public String getShop_longitude() {
            return shop_longitude;
        }

        public int getGoods_coll() {
            return goods_coll;
        }

        public int getClick_count() {
            return click_count;
        }

        public String getSpecifications() {
            return specifications;
        }

        public int getQuantity_sold() {
            return quantity_sold;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public int getOrigin_number() {
            return origin_number;
        }

        public String getShop_tel() {
            return shop_tel;
        }

        public String getShop_price() {
            return shop_price;
        }

        public String getCat_name() {
            return cat_name;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public String getGoods_desc() {
            return goods_desc;
        }

        public String getShop_name() {
            return shop_name;
        }

        public int getIs_show() {
            return is_show;
        }

        public int getVirtual_quantity_sold() {
            return virtual_quantity_sold;
        }

        public int getShop_id() {
            return shop_id;
        }

        public int getGoods_number() {
            return goods_number;
        }

        public String getGoods_unit() {
            return goods_unit;
        }

        public String getGoods_thumb() {
            return goods_thumb;
        }

        public String getShop_latitude() {
            return shop_latitude;
        }

        public int getShop_type() {
            return shop_type;
        }

        public String getWap_desc() {
            return wap_desc;
        }

        public int getGoods_type() {
            return goods_type;
        }


        public GoodsCommentBean getGoods_comment() {
            return goods_comment;
        }

        public List<GoodsImgBean> getGoods_img() {
            return goods_img;
        }

        public String getGroupon_id() {
            return groupon_id;
        }

        public void setGroupon_id(String groupon_id) {
            this.groupon_id = groupon_id;
        }

        public int getTarget_number() {
            return target_number;
        }

        public void setTarget_number(int target_number) {
            this.target_number = target_number;
        }

        public int getSold_number() {
            return sold_number;
        }

        public void setSold_number(int sold_number) {
            this.sold_number = sold_number;
        }

        public int getSurplus() {
            return surplus;
        }

        public void setSurplus(int surplus) {
            this.surplus = surplus;
        }

        public String getRule_url() {
            return rule_url;
        }

        public void setRule_url(String rule_url) {
            this.rule_url = rule_url;
        }

        public int getParticipants() {
            return participants;
        }

        public void setParticipants(int participants) {
            this.participants = participants;
        }


        public ShStatusBean getSh_status() {
            return sh_status;
        }

        public void setSh_status(ShStatusBean sh_status) {
            this.sh_status = sh_status;
        }

        public int getGs_id() {
            return gs_id;
        }

        public void setGs_id(int gs_id) {
            this.gs_id = gs_id;
        }

        public static class GoodsSpecEntity {
            /**
             * goods_sku : [{"sku_attr":"{1:29574}","price":"105","goods_id":84,"sku_id":29557,"inventory":9930,"sales":575}]
             * spec_attr : [{"res":[{"attr_name":"默认","attr_id":29574}],"spec_id":1,"spec_name":"规格"}]
             */
            private List<SpecifiBean.DataEntity.GoodsSkuEntity> goods_sku;
            private List<SpecifiBean.DataEntity.SpecAttrEntity> spec_attr;

            public void setGoods_sku(List<SpecifiBean.DataEntity.GoodsSkuEntity> goods_sku) {
                this.goods_sku = goods_sku;
            }

            public void setSpec_attr(List<SpecifiBean.DataEntity.SpecAttrEntity> spec_attr) {
                this.spec_attr = spec_attr;
            }

            public List<SpecifiBean.DataEntity.GoodsSkuEntity> getGoods_sku() {
                return goods_sku;
            }

            public List<SpecifiBean.DataEntity.SpecAttrEntity> getSpec_attr() {
                return spec_attr;
            }

        }

        public static class GoodsCommentBean {

            /**
             * com_like : 0
             * comment_number : 12
             * com_grade : 5
             * user_id : 239
             * is_like : 1
             * com_id : 543
             * com_content : 内容1
             * nickname :
             * avatar :
             * com_photo : ["upload/comment/1517019878_168_comment.jpg","upload/comment/1517019878_70996_comment.jpg"]
             * add_time : 2018.01.27
             */
            private int com_like;
            private int comment_number;
            private int com_grade;
            private int user_id;
            private int is_like;//是否点赞  0没点赞1已点赞
            private int com_id;
            private String com_content;
            private String nickname;
            private String avatar;
            private List<String> com_photo;
            private String add_time;
            private List<CartBean.DataEntity.CartListEntity.SpecificationEntity> goods_attr;

            public void setCom_like(int com_like) {
                this.com_like = com_like;
            }

            public void setComment_number(int comment_number) {
                this.comment_number = comment_number;
            }

            public void setCom_grade(int com_grade) {
                this.com_grade = com_grade;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public void setIs_like(int is_like) {
                this.is_like = is_like;
            }

            public void setCom_id(int com_id) {
                this.com_id = com_id;
            }

            public void setCom_content(String com_content) {
                this.com_content = com_content;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public void setCom_photo(List<String> com_photo) {
                this.com_photo = com_photo;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public int getCom_like() {
                return com_like;
            }

            public int getComment_number() {
                return comment_number;
            }

            public int getCom_grade() {
                return com_grade;
            }

            public int getUser_id() {
                return user_id;
            }

            public int getIs_like() {
                return is_like;
            }

            public int getCom_id() {
                return com_id;
            }

            public String getCom_content() {
                return com_content;
            }

            public String getNickname() {
                return nickname;
            }

            public String getAvatar() {
                return avatar;
            }

            public List<String> getCom_photo() {
                return com_photo;
            }

            public String getAdd_time() {
                return add_time;
            }

            public List<CartBean.DataEntity.CartListEntity.SpecificationEntity> getGoods_attr() {
                return goods_attr;
            }

            public void setGoods_attr(List<CartBean.DataEntity.CartListEntity.SpecificationEntity> goods_attr) {
                this.goods_attr = goods_attr;
            }
        }

        public static class ShStatusBean {
            /**
             * status : 3
             * sh_day : 0
             *
             *
             * 1 用户可以账期支付，
             * 2用户已经申请账期支付，
             * 3店铺已经开通账期(可申请)，
             * 4不可以账期支付(店铺未开通或者商品未开通)，
             */

            private int status;
            private int sh_day;

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getSh_day() {
                return sh_day;
            }

            public void setSh_day(int sh_day) {
                this.sh_day = sh_day;
            }
        }

        /**
         * 原图 + 略缩图
         */
        public static class GoodsImgBean {
            /**
             * thumb_url : upload/201706/thumb_img/33362_thumb_P_1497335421795.jpg
             * img_url : upload/201706/goods_img/33362_P_1497335421675.jpg
             */
            private String thumb_url;
            private String img_url;

            public void setThumb_url(String thumb_url) {
                this.thumb_url = thumb_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getThumb_url() {
                return thumb_url;
            }

            public String getImg_url() {
                return img_url;
            }
        }



        public List<CartBean.DataEntity.CartListEntity.SpecificationEntity> getSpecificationArray() {
            if(StringUtils.isNotEmpty(specifications)){
                List<CartBean.DataEntity.CartListEntity.SpecificationEntity> entityListTemp = null;
                List<CartBean.DataEntity.CartListEntity.SpecificationEntity> entityList = new ArrayList<>();
                try {
                    LogUtils.i("specifications:" + specifications);
                    entityListTemp = GsonUtils.jsonToArrayList(specifications,
                            CartBean.DataEntity.CartListEntity.SpecificationEntity.class);
                    for(CartBean.DataEntity.CartListEntity.SpecificationEntity entity : entityListTemp){
                        // LogUtils.i("entity.getKey():" + entity.getKey() + "---entity.getValue():" + entity.getValue());
                        if(StringUtils.isNotEmpty(entity.getValue())){
                            String[] value = entity.getValue().split(";");
                            for(String s : value){
                                entityList.add(new CartBean.DataEntity.CartListEntity.SpecificationEntity(entity.getKey(), s));
                            }
                        }
                    }
                }catch (Exception e){
                    LogUtils.i("数据解析错误:", e);
                    entityList = new ArrayList<>();
                }finally {
                    return entityList;
                }

            }else {
                return new ArrayList<>();
            }
        }

        public int getSku_count() {
            return sku_count;
        }

        public void setSku_count(int sku_count) {
            this.sku_count = sku_count;
        }
    }
}
