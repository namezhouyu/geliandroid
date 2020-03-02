package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Steam_l on 2018/1/11.
 */

public class ShopInfoBean {
    /**
     * code : 100
     * data : {"shop_info":{"shop_tel":"020-81058138","address":"广州荔湾区环市西水厂路5号一区49档","shop_longitude":"113.2408046722","count_month":45,"zhizhao":"upload/shops/y1499327596.png","shop_att":0,"shop_img":"upload/shops/y1499834890.png","shop_name":"格利食品网","virtual_quantity_sold":1607,"moq":10,"shop_id":1,"qualification":["upload/shops/y1499327596.png",""],"countOrder":2607,"shop_latitude":"23.1420124735","grade":{"com_grade":3.1,"fresh_grade":0,"logistics_grade":0},"shop_type":3,"food_production_license":"","shop_intro":"自营商铺"},"coupon":[{"createnum":100,"send_num":0,"shop_id":1,"condition":"101.00","money":"100.00","name":"优惠券test333","cp_id":116,"use_start_time":"2018-01-01 15:08","use_end_time":"2018-01-31 15:08","is_use":0,"group":0}],"catRes":{"othercart":[{"gs_name":"新品推荐","gs_type":1,"gs_id":1}],"cart":[{"cat_name":"调理品","level":0,"parent_id":0,"cat_id":1},{"cat_name":"禽类产品","level":0,"parent_id":0,"cat_id":3},{"cat_name":"水产品","level":0,"parent_id":0,"cat_id":4},{"cat_name":"米面杂粮类","level":0,"parent_id":0,"cat_id":5},{"cat_name":"休闲/干调类","level":0,"parent_id":0,"cat_id":7},{"cat_name":"鸭产品","level":1,"parent_id":3,"cat_id":357},{"cat_name":"海外","level":0,"parent_id":0,"cat_id":459}]},"goodsRes":[{"goods_name":"獐子岛 半壳扇贝 1kg*10包/箱 7-8只","origin_number":1,"shop_price":"650.00","goods_number":1000,"parent_id":147,"goods_thumb":"upload/goods/20171212/151306584263307.png","cat_id":175,"goods_id":4950,"specifications":"规格:1kg*10包/箱 7-8只","gs_id":1},{"goods_name":"獐子岛 半壳扇贝 1kg*10包/箱 7-8只","origin_number":1,"shop_price":"650.00","goods_number":993,"parent_id":0,"goods_thumb":"upload/goods/20171212/151306584263307.png","cat_id":459,"goods_id":4955,"specifications":"规格:1kg*10包/箱 7-8只","gs_id":1},{"goods_name":"半壳扇贝","origin_number":1,"shop_price":"599.00","goods_number":993,"parent_id":0,"goods_thumb":"upload/goods/20171212/151306584263307.png","cat_id":459,"goods_id":4956,"specifications":"规格:1kg*10包/箱 7-8只","gs_id":1},{"goods_name":"太阳卷 500g*40条/箱 ","origin_number":1,"shop_price":"1140.00","goods_number":1000,"parent_id":56,"goods_thumb":"upload/goods/20171212/15130653554895.png","cat_id":110,"goods_id":4949,"specifications":"规格:500g*40条/箱","gs_id":1},{"goods_name":"羔羊肉卷 2.5KG*8条/箱 ","origin_number":1,"shop_price":"1080.00","goods_number":1000,"parent_id":56,"goods_thumb":"upload/goods/20171212/151306497438198.png","cat_id":110,"goods_id":4948,"specifications":"规格:2.5KG*8条/箱","gs_id":1},{"goods_name":"三角肥牛 20KG左右/箱 抄码","origin_number":1,"shop_price":"3200.00","goods_number":999,"parent_id":57,"goods_thumb":"upload/goods/20171212/151306428452333.png","cat_id":96,"goods_id":4947,"specifications":"规格:20KG左右/箱","gs_id":1},{"goods_name":"大成 可丽鸡排 1kg*10包/箱","origin_number":1,"shop_price":"155.00","goods_number":1000,"parent_id":14,"goods_thumb":"upload/goods/20171211/151295737493407.png","cat_id":21,"goods_id":4942,"specifications":"规格:1kg*10包/箱","gs_id":1},{"goods_name":"中粮 万威客 海鲜鱼肉肠 300g*35包/箱","origin_number":1,"shop_price":"462.00","goods_number":1000,"parent_id":10,"goods_thumb":"upload/goods/20171211/151295728172331.png","cat_id":41,"goods_id":4941,"specifications":"规格:300g*35包/箱","gs_id":1},{"goods_name":"恒兴 淡晒金鲳鱼 4条/件 每条300g左右","origin_number":1,"shop_price":"106.00","goods_number":1000,"parent_id":144,"goods_thumb":"upload/goods/20171202/151220157487982.png","cat_id":149,"goods_id":4922,"specifications":"规格: 4条/件 每条300g左右","gs_id":1},{"goods_name":"玖嘉久 鱼籽福袋 1kg*6袋/箱 40±2粒/kg","origin_number":1,"shop_price":"382.00","goods_number":1000,"parent_id":10,"goods_thumb":"upload/goods/20171202/151220053477029.png","cat_id":41,"goods_id":4920,"specifications":"规格:1kg*6袋/箱","gs_id":1},{"goods_name":"玖嘉久 日式野菜鱼腐 3kg*4袋/箱 63±2粒/kg","origin_number":1,"shop_price":"534.00","goods_number":1000,"parent_id":10,"goods_thumb":"upload/goods/20171202/151220045325805.png","cat_id":41,"goods_id":4919,"specifications":"规格:3kg*4袋/箱","gs_id":1},{"goods_name":"玖嘉久 野菜丸天 3kg*4袋/箱 40±2粒/kg ","origin_number":1,"shop_price":"534.00","goods_number":1000,"parent_id":10,"goods_thumb":"upload/goods/20171202/151220036698029.png","cat_id":41,"goods_id":4918,"specifications":"规格:3kg*4袋/箱","gs_id":1},{"goods_name":"玖嘉久 腐皮鲜虾卷 3kg*4袋/箱 20±1粒/kg","origin_number":1,"shop_price":"560.00","goods_number":1000,"parent_id":10,"goods_thumb":"upload/goods/20171202/151220029533287.png","cat_id":41,"goods_id":4917,"specifications":"规格:3kg*4袋/箱","gs_id":1},{"goods_name":"玖嘉久 腐皮墨鱼卷 3kg*4袋/箱 20±1粒/kg","origin_number":1,"shop_price":"560.00","goods_number":1000,"parent_id":10,"goods_thumb":"upload/goods/20171202/151220004468486.png","cat_id":41,"goods_id":4916,"specifications":"规格:3kg*4袋/箱","gs_id":1},{"goods_name":"玖嘉久 御品鳕芋 3kg*4袋/箱 50±2粒/kg","origin_number":1,"shop_price":"483.00","goods_number":1000,"parent_id":10,"goods_thumb":"upload/goods/20171202/151219991417316.png","cat_id":41,"goods_id":4915,"specifications":"规格:3kg*4袋/箱","gs_id":1},{"goods_name":"玖嘉久 北海香菇丸 3kg*4袋/箱 50±2粒/kg","origin_number":1,"shop_price":"534.00","goods_number":1000,"parent_id":10,"goods_thumb":"upload/goods/20171202/151219982375216.png","cat_id":41,"goods_id":4914,"specifications":"规格:3kg*4袋/箱","gs_id":1},{"goods_name":"玖嘉久 深海鲍鱼丸 3kg*4袋/箱 53±2粒/kg","origin_number":1,"shop_price":"560.00","goods_number":1000,"parent_id":10,"goods_thumb":"upload/goods/20171202/151219971179503.png","cat_id":41,"goods_id":4913,"specifications":"规格:3kg*4袋/箱","gs_id":1},{"goods_name":"玖嘉久 鳕鱼菠菜丸 3kg*4袋/箱 53±2粒/kg","origin_number":1,"shop_price":"534.00","goods_number":1000,"parent_id":10,"goods_thumb":"upload/goods/20171202/151219945845892.png","cat_id":41,"goods_id":4912,"specifications":"规格:3kg*4袋/箱","gs_id":1},{"goods_name":"玖嘉久 五月花丸3kg*4袋/箱 53±2粒/kg","origin_number":1,"shop_price":"534.00","goods_number":1000,"parent_id":10,"goods_thumb":"upload/goods/20171202/151219913614478.png","cat_id":41,"goods_id":4911,"specifications":"规格:3kg*4袋/箱","gs_id":1},{"goods_name":"玖嘉久 鱼籽墨鱼球3kg*4袋/箱 53±2粒/kg","origin_number":1,"shop_price":"560.00","goods_number":1000,"parent_id":10,"goods_thumb":"upload/goods/20171202/151219897538051.png","cat_id":41,"goods_id":4910,"specifications":"规格:3kg*4袋/箱","gs_id":1}]}
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
         * shop_info : {"shop_tel":"020-81058138","address":"广州荔湾区环市西水厂路5号一区49档","shop_longitude":"113.2408046722","count_month":45,"zhizhao":"upload/shops/y1499327596.png","shop_att":0,"shop_img":"upload/shops/y1499834890.png","shop_name":"格利食品网","virtual_quantity_sold":1607,"moq":10,"shop_id":1,"qualification":["upload/shops/y1499327596.png",""],"countOrder":2607,"shop_latitude":"23.1420124735","grade":{"com_grade":3.1,"fresh_grade":0,"logistics_grade":0},"shop_type":3,"food_production_license":"","shop_intro":"自营商铺"}
         * coupon : [{"createnum":100,"send_num":0,"shop_id":1,"condition":"101.00","money":"100.00","name":"优惠券test333","cp_id":116,"use_start_time":"2018-01-01 15:08","use_end_time":"2018-01-31 15:08","is_use":0,"group":0}]
         * catRes : {"othercart":[{"gs_name":"新品推荐","gs_type":1,"gs_id":1}],"cart":[{"cat_name":"调理品","level":0,"parent_id":0,"cat_id":1},{"cat_name":"禽类产品","level":0,"parent_id":0,"cat_id":3},{"cat_name":"水产品","level":0,"parent_id":0,"cat_id":4},{"cat_name":"米面杂粮类","level":0,"parent_id":0,"cat_id":5},{"cat_name":"休闲/干调类","level":0,"parent_id":0,"cat_id":7},{"cat_name":"鸭产品","level":1,"parent_id":3,"cat_id":357},{"cat_name":"海外","level":0,"parent_id":0,"cat_id":459}]}
         * goodsRes : [{"goods_name":"獐子岛 半壳扇贝 1kg*10包/箱 7-8只","origin_number":1,"shop_price":"650.00","goods_number":1000,"parent_id":147,"goods_thumb":"upload/goods/20171212/151306584263307.png","cat_id":175,"goods_id":4950,"specifications":"规格:1kg*10包/箱 7-8只","gs_id":1},{"goods_name":"獐子岛 半壳扇贝 1kg*10包/箱 7-8只","origin_number":1,"shop_price":"650.00","goods_number":993,"parent_id":0,"goods_thumb":"upload/goods/20171212/151306584263307.png","cat_id":459,"goods_id":4955,"specifications":"规格:1kg*10包/箱 7-8只","gs_id":1},{"goods_name":"半壳扇贝","origin_number":1,"shop_price":"599.00","goods_number":993,"parent_id":0,"goods_thumb":"upload/goods/20171212/151306584263307.png","cat_id":459,"goods_id":4956,"specifications":"规格:1kg*10包/箱 7-8只","gs_id":1},{"goods_name":"太阳卷 500g*40条/箱 ","origin_number":1,"shop_price":"1140.00","goods_number":1000,"parent_id":56,"goods_thumb":"upload/goods/20171212/15130653554895.png","cat_id":110,"goods_id":4949,"specifications":"规格:500g*40条/箱","gs_id":1},{"goods_name":"羔羊肉卷 2.5KG*8条/箱 ","origin_number":1,"shop_price":"1080.00","goods_number":1000,"parent_id":56,"goods_thumb":"upload/goods/20171212/151306497438198.png","cat_id":110,"goods_id":4948,"specifications":"规格:2.5KG*8条/箱","gs_id":1},{"goods_name":"三角肥牛 20KG左右/箱 抄码","origin_number":1,"shop_price":"3200.00","goods_number":999,"parent_id":57,"goods_thumb":"upload/goods/20171212/151306428452333.png","cat_id":96,"goods_id":4947,"specifications":"规格:20KG左右/箱","gs_id":1},{"goods_name":"大成 可丽鸡排 1kg*10包/箱","origin_number":1,"shop_price":"155.00","goods_number":1000,"parent_id":14,"goods_thumb":"upload/goods/20171211/151295737493407.png","cat_id":21,"goods_id":4942,"specifications":"规格:1kg*10包/箱","gs_id":1},{"goods_name":"中粮 万威客 海鲜鱼肉肠 300g*35包/箱","origin_number":1,"shop_price":"462.00","goods_number":1000,"parent_id":10,"goods_thumb":"upload/goods/20171211/151295728172331.png","cat_id":41,"goods_id":4941,"specifications":"规格:300g*35包/箱","gs_id":1},{"goods_name":"恒兴 淡晒金鲳鱼 4条/件 每条300g左右","origin_number":1,"shop_price":"106.00","goods_number":1000,"parent_id":144,"goods_thumb":"upload/goods/20171202/151220157487982.png","cat_id":149,"goods_id":4922,"specifications":"规格: 4条/件 每条300g左右","gs_id":1},{"goods_name":"玖嘉久 鱼籽福袋 1kg*6袋/箱 40±2粒/kg","origin_number":1,"shop_price":"382.00","goods_number":1000,"parent_id":10,"goods_thumb":"upload/goods/20171202/151220053477029.png","cat_id":41,"goods_id":4920,"specifications":"规格:1kg*6袋/箱","gs_id":1},{"goods_name":"玖嘉久 日式野菜鱼腐 3kg*4袋/箱 63±2粒/kg","origin_number":1,"shop_price":"534.00","goods_number":1000,"parent_id":10,"goods_thumb":"upload/goods/20171202/151220045325805.png","cat_id":41,"goods_id":4919,"specifications":"规格:3kg*4袋/箱","gs_id":1},{"goods_name":"玖嘉久 野菜丸天 3kg*4袋/箱 40±2粒/kg ","origin_number":1,"shop_price":"534.00","goods_number":1000,"parent_id":10,"goods_thumb":"upload/goods/20171202/151220036698029.png","cat_id":41,"goods_id":4918,"specifications":"规格:3kg*4袋/箱","gs_id":1},{"goods_name":"玖嘉久 腐皮鲜虾卷 3kg*4袋/箱 20±1粒/kg","origin_number":1,"shop_price":"560.00","goods_number":1000,"parent_id":10,"goods_thumb":"upload/goods/20171202/151220029533287.png","cat_id":41,"goods_id":4917,"specifications":"规格:3kg*4袋/箱","gs_id":1},{"goods_name":"玖嘉久 腐皮墨鱼卷 3kg*4袋/箱 20±1粒/kg","origin_number":1,"shop_price":"560.00","goods_number":1000,"parent_id":10,"goods_thumb":"upload/goods/20171202/151220004468486.png","cat_id":41,"goods_id":4916,"specifications":"规格:3kg*4袋/箱","gs_id":1},{"goods_name":"玖嘉久 御品鳕芋 3kg*4袋/箱 50±2粒/kg","origin_number":1,"shop_price":"483.00","goods_number":1000,"parent_id":10,"goods_thumb":"upload/goods/20171202/151219991417316.png","cat_id":41,"goods_id":4915,"specifications":"规格:3kg*4袋/箱","gs_id":1},{"goods_name":"玖嘉久 北海香菇丸 3kg*4袋/箱 50±2粒/kg","origin_number":1,"shop_price":"534.00","goods_number":1000,"parent_id":10,"goods_thumb":"upload/goods/20171202/151219982375216.png","cat_id":41,"goods_id":4914,"specifications":"规格:3kg*4袋/箱","gs_id":1},{"goods_name":"玖嘉久 深海鲍鱼丸 3kg*4袋/箱 53±2粒/kg","origin_number":1,"shop_price":"560.00","goods_number":1000,"parent_id":10,"goods_thumb":"upload/goods/20171202/151219971179503.png","cat_id":41,"goods_id":4913,"specifications":"规格:3kg*4袋/箱","gs_id":1},{"goods_name":"玖嘉久 鳕鱼菠菜丸 3kg*4袋/箱 53±2粒/kg","origin_number":1,"shop_price":"534.00","goods_number":1000,"parent_id":10,"goods_thumb":"upload/goods/20171202/151219945845892.png","cat_id":41,"goods_id":4912,"specifications":"规格:3kg*4袋/箱","gs_id":1},{"goods_name":"玖嘉久 五月花丸3kg*4袋/箱 53±2粒/kg","origin_number":1,"shop_price":"534.00","goods_number":1000,"parent_id":10,"goods_thumb":"upload/goods/20171202/151219913614478.png","cat_id":41,"goods_id":4911,"specifications":"规格:3kg*4袋/箱","gs_id":1},{"goods_name":"玖嘉久 鱼籽墨鱼球3kg*4袋/箱 53±2粒/kg","origin_number":1,"shop_price":"560.00","goods_number":1000,"parent_id":10,"goods_thumb":"upload/goods/20171202/151219897538051.png","cat_id":41,"goods_id":4910,"specifications":"规格:3kg*4袋/箱","gs_id":1}]
         */
        private ShopInfoEntity shop_info;
        private List<CouponBean> coupon;
        private CatResEntity catRes;
        private List<GoodsResEntity> goodsRes;

        public void setShop_info(ShopInfoEntity shop_info) {
            this.shop_info = shop_info;
        }

        public void setCoupon(List<CouponBean> coupon) {
            this.coupon = coupon;
        }

        public void setCatRes(CatResEntity catRes) {
            this.catRes = catRes;
        }

        public void setGoodsRes(List<GoodsResEntity> goodsRes) {
            this.goodsRes = goodsRes;
        }

        public ShopInfoEntity getShop_info() {
            return shop_info;
        }

        public List<CouponBean> getCoupon() {
            return coupon;
        }

        public CatResEntity getCatRes() {
            return catRes;
        }

        public List<GoodsResEntity> getGoodsRes() {
            return goodsRes;
        }

        public static class ShopInfoEntity {
            /**
             * shop_tel : 020-81058138
             * address : 广州荔湾区环市西水厂路5号一区49档
             * shop_longitude : 113.2408046722
             * count_month : 45
             * zhizhao : upload/shops/y1499327596.png
             * shop_att : 0
             * shop_img : upload/shops/y1499834890.png
             * shop_name : 格利食品网
             * virtual_quantity_sold : 1607
             * moq : 10
             * shop_id : 1
             * qualification : ["upload/shops/y1499327596.png",""]
             * countOrder : 2607
             * shop_latitude : 23.1420124735
             * grade : {"com_grade":3.1,"fresh_grade":0,"logistics_grade":0}
             * shop_type : 3
             * food_production_license :
             * shop_intro : 自营商铺
             */

            /** 联系电话 */
            private String shop_tel;
            /** 商店地址 */
            private String address;
            /** 商店 */
            private String shop_longitude;
            /** 当月销量 */
            private int count_month;
            /** 执照 */
            private String zhizhao;
            /** 是否关注（1是0否） */
            private int shop_att;
            /** 商店缩略图 */
            private String shop_img;
            /** 商店名 */
            private String shop_name;
            /** 虚拟总订单量 */
            private int virtual_quantity_sold;
            /**  */
            private int moq;
            /** 商店ID */
            private int shop_id;
            /**  */
            private List<String> qualification;
            /** 总订单量 */
            private int countOrder;
            /** 商店位置纬度 */
            private String shop_latitude;
            /**  */
            private AtsBean adv;
            /** 商家评分 */
            private GradeEntity grade;
            /** 商店类型(1批零2工厂3自营) */
            private int shop_type;
            /**  */
            private String food_production_license;
            /** 商店简介 */
            private String shop_intro;
            /**  */
            private String shop_detail_url;

            private int sh;

            private int is_resale;              // 0:不拼团/不零售   1:拼团/可零售
            private String goods_main;             // 主营商品

            public void setShop_tel(String shop_tel) {
                this.shop_tel = shop_tel;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public void setShop_longitude(String shop_longitude) {
                this.shop_longitude = shop_longitude;
            }

            public void setCount_month(int count_month) {
                this.count_month = count_month;
            }

            public void setZhizhao(String zhizhao) {
                this.zhizhao = zhizhao;
            }

            public void setShop_att(int shop_att) {
                this.shop_att = shop_att;
            }

            public void setShop_img(String shop_img) {
                this.shop_img = shop_img;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }

            public void setVirtual_quantity_sold(int virtual_quantity_sold) {
                this.virtual_quantity_sold = virtual_quantity_sold;
            }

            public void setMoq(int moq) {
                this.moq = moq;
            }

            public void setShop_id(int shop_id) {
                this.shop_id = shop_id;
            }

            public void setQualification(List<String> qualification) {
                this.qualification = qualification;
            }

            public void setCountOrder(int countOrder) {
                this.countOrder = countOrder;
            }

            public void setShop_latitude(String shop_latitude) {
                this.shop_latitude = shop_latitude;
            }

            public void setGrade(GradeEntity grade) {
                this.grade = grade;
            }

            public void setShop_type(int shop_type) {
                this.shop_type = shop_type;
            }

            public void setFood_production_license(String food_production_license) {
                this.food_production_license = food_production_license;
            }

            public void setShop_intro(String shop_intro) {
                this.shop_intro = shop_intro;
            }

            public String getShop_tel() {
                return shop_tel;
            }

            public String getAddress() {
                return address;
            }

            public String getShop_longitude() {
                return shop_longitude;
            }

            public int getCount_month() {
                return count_month;
            }

            public String getZhizhao() {
                return zhizhao;
            }

            public int getShop_att() {
                return shop_att;
            }

            public String getShop_img() {
                return shop_img;
            }

            public String getShop_name() {
                return shop_name;
            }

            public int getVirtual_quantity_sold() {
                return virtual_quantity_sold;
            }

            public int getMoq() {
                return moq;
            }

            public int getShop_id() {
                return shop_id;
            }

            public List<String> getQualification() {
                return qualification;
            }

            public int getCountOrder() {
                return countOrder;
            }

            public String getShop_latitude() {
                return shop_latitude;
            }

            public GradeEntity getGrade() {
                return grade;
            }

            public int getShop_type() {
                return shop_type;
            }

            public String getFood_production_license() {
                return food_production_license;
            }

            public String getShop_intro() {
                return shop_intro;
            }

            public String getShop_detail_url() {
                return shop_detail_url;
            }

            public void setShop_detail_url(String shop_detail_url) {
                this.shop_detail_url = shop_detail_url;
            }

            public AtsBean getAdv() {
                return adv;
            }

            public void setAdv(AtsBean adv) {
                this.adv = adv;
            }

            public int getSh() {
                return sh;
            }

            public void setSh(int sh) {
                this.sh = sh;
            }

            public int getIs_resale() {
                return is_resale;
            }

            public void setIs_resale(int is_resale) {
                this.is_resale = is_resale;
            }

            public String getGoods_main() {
                return goods_main;
            }

            public void setGoods_main(String goods_main) {
                this.goods_main = goods_main;
            }

            public static class GradeEntity implements Parcelable {
                /**
                 * com_grade : 3.1
                 * fresh_grade : 0
                 * logistics_grade : 0
                 */
                /** 服务态度 */
                private double com_grade;
                /** 冰鲜程度 */
                private double fresh_grade;
                /** 物流态度 */
                private double logistics_grade;
                /**  */
                private double serve_grade;


                protected GradeEntity(Parcel in) {
                    com_grade = in.readDouble();
                    fresh_grade = in.readDouble();
                    logistics_grade = in.readDouble();
                    serve_grade = in.readDouble();
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeDouble(com_grade);
                    dest.writeDouble(fresh_grade);
                    dest.writeDouble(logistics_grade);
                    dest.writeDouble(serve_grade);
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                public static final Creator<GradeEntity> CREATOR = new Creator<GradeEntity>() {
                    @Override
                    public GradeEntity createFromParcel(Parcel in) {
                        return new GradeEntity(in);
                    }

                    @Override
                    public GradeEntity[] newArray(int size) {
                        return new GradeEntity[size];
                    }
                };

                public void setCom_grade(double com_grade) {
                    this.com_grade = com_grade;
                }

                public void setFresh_grade(double fresh_grade) {
                    this.fresh_grade = fresh_grade;
                }

                public void setLogistics_grade(double logistics_grade) {
                    this.logistics_grade = logistics_grade;
                }

                public double getCom_grade() {
                    return com_grade;
                }

                public double getFresh_grade() {
                    return fresh_grade;
                }

                public double getLogistics_grade() {
                    return logistics_grade;
                }

                public double getServe_grade() {
                    return serve_grade;
                }

                public void setServe_grade(double serve_grade) {
                    this.serve_grade = serve_grade;
                }
            }
        }

        /**
         * "分类" -- 其他分类(推荐/爆款/新品...)、普通分类
         */
        public static class CatResEntity {
            /**
             * othercart : [{"gs_name":"新品推荐","gs_type":1,"gs_id":1}]
             * cart : [{"cat_name":"调理品","level":0,"parent_id":0,"cat_id":1},{"cat_name":"禽类产品","level":0,"parent_id":0,"cat_id":3},{"cat_name":"水产品","level":0,"parent_id":0,"cat_id":4},{"cat_name":"米面杂粮类","level":0,"parent_id":0,"cat_id":5},{"cat_name":"休闲/干调类","level":0,"parent_id":0,"cat_id":7},{"cat_name":"鸭产品","level":1,"parent_id":3,"cat_id":357},{"cat_name":"海外","level":0,"parent_id":0,"cat_id":459}]
             */
            private List<OthercartEntity> othercart;
            private List<CartEntity> cart;

            public void setOthercart(List<OthercartEntity> othercart) {
                this.othercart = othercart;
            }

            public void setCart(List<CartEntity> cart) {
                this.cart = cart;
            }

            public List<OthercartEntity> getOthercart() {
                return othercart;
            }

            public List<CartEntity> getCart() {
                return cart;
            }

            /**
             * 其他分类(推荐/爆款/新品...)
             */
            public static class OthercartEntity {
                /**
                 * gs_name : 新品推荐
                 * gs_type : 1
                 * gs_id : 1
                 */
                /** 其他分类名 */
                private String gs_name;
                /** 其他分类类型 */
                private int gs_type;
                /** 其他分类ID */
                private int gs_id;

                public void setGs_name(String gs_name) {
                    this.gs_name = gs_name;
                }

                public void setGs_type(int gs_type) {
                    this.gs_type = gs_type;
                }

                public void setGs_id(int gs_id) {
                    this.gs_id = gs_id;
                }

                public String getGs_name() {
                    return gs_name;
                }

                public int getGs_type() {
                    return gs_type;
                }

                public int getGs_id() {
                    return gs_id;
                }

                @Override
                public String toString() {
                    return "OthercartEntity{" +
                            "gs_name='" + gs_name + '\'' +
                            ", gs_type=" + gs_type +
                            ", gs_id=" + gs_id +
                            '}';
                }
            }

            /**
             * 普通分类
             */
            public static class CartEntity {
                /**
                 * cat_name : 调理品
                 * level : 0
                 * parent_id : 0
                 * cat_id : 1
                 */

                /** 商品分类名 */
                private String cat_name;
                /** 分类级别 */
                private int level;
                /** 商品分类父级ID */
                private int parent_id;
                /** 商品分类ID */
                private int cat_id;
                /** 小分类(子分类) */
                private List<CartEntity> smallCartList = new ArrayList<>();

                public void setCat_name(String cat_name) {
                    this.cat_name = cat_name;
                }

                public void setLevel(int level) {
                    this.level = level;
                }

                public void setParent_id(int parent_id) {
                    this.parent_id = parent_id;
                }

                public void setCat_id(int cat_id) {
                    this.cat_id = cat_id;
                }

                public String getCat_name() {
                    return cat_name;
                }

                public int getLevel() {
                    return level;
                }

                public int getParent_id() {
                    return parent_id;
                }

                public int getCat_id() {
                    return cat_id;
                }

                public List<CartEntity> getSmallCartList() {
                    return smallCartList;
                }

                public void setSmallCartList(List<CartEntity> smallCartList) {
                    this.smallCartList = smallCartList;
                }

                @Override
                public boolean equals(Object obj) {
                    if (obj instanceof CartEntity) {
                        return this.cat_id == ((CartEntity) obj).getCat_id();
                    } else if (obj instanceof ShopInfoBean.DataEntity.GoodsResEntity) {
                        return this.cat_id == ((GoodsResEntity) obj).getParent_id();
                    }
                    return super.equals(obj);
                }

                // 排序
                public static class CartComparator implements Comparator {
                    public int compare(Object o1,Object o2){
                        CartEntity c1 = (CartEntity)o1;
                        CartEntity c2 = (CartEntity)o2;

                        int result = c1.level > c2.level ? 1 : (c1.level == c2.level ? 0 : -1);

                        if(result == 0){                           // 等级相同，比疯了编号
                            result = c1.cat_id > c2.cat_id ? 1 : 0;
                        }
                        return result;
                    }
                }

                @Override
                public String toString() {
                    return "CartEntity{" +
                            "cat_name='" + cat_name + '\'' +
                            ", level=" + level +
                            ", parent_id=" + parent_id +
                            ", cat_id=" + cat_id +
                            ", smallCartList=" + smallCartList +
                            '}';
                }
            }
        }

        public static class GoodsResEntity implements Parcelable {
            /**
             * goods_name : 獐子岛 半壳扇贝 1kg*10包/箱 7-8只
             * origin_number : 1
             * shop_price : 650.00
             * goods_number : 1000
             * parent_id : 147
             * goods_thumb : upload/goods/20171212/151306584263307.png
             * cat_id : 175
             * goods_id : 4950
             * specifications : 规格:1kg*10包/箱 7-8只
             * gs_id : 1
             */
            /** 商品名 */
            private String goods_name = "";
            /** 起卖数量 */
            private int origin_number = 0;
            /** 商品价格 */
            private String shop_price = "";
            /**  */
            private int goods_number = 0;
            /** 所属分类的"父分类ID" */
            private int parent_id = 0;//其他的小cat的大catId
            /** 商品缩略图 */
            private String goods_thumb = "";
            /** 所属分类ID */
            private int cat_id = 0; //其他的小catId
            /** 商品ID */
            private int goods_id = 0;
            /**  */
            private List<CartBean.DataEntity.CartListEntity.SpecificationEntity> specifications;
            /** 其他分类ID */
            private int gs_id = -1; //新品推荐的id
            /**  */
            private int table = 0;
            /**  */
            private String goods_unit;

            /** trur : 说明这个商品数据是空的 */
            public boolean dataNUll = false;

            /** 属性数量 -- 所有小的 */
            private int sku_count;          // 后台数据

            public GoodsResEntity() {

            }

            protected GoodsResEntity(Parcel in) {
                goods_name = in.readString();
                origin_number = in.readInt();
                shop_price = in.readString();
                goods_number = in.readInt();
                parent_id = in.readInt();
                goods_thumb = in.readString();
                cat_id = in.readInt();
                goods_id = in.readInt();
                gs_id = in.readInt();
                table = in.readInt();
                goods_unit = in.readString();
                sku_count = in.readInt();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(goods_name);
                dest.writeInt(origin_number);
                dest.writeString(shop_price);
                dest.writeInt(goods_number);
                dest.writeInt(parent_id);
                dest.writeString(goods_thumb);
                dest.writeInt(cat_id);
                dest.writeInt(goods_id);
                dest.writeInt(gs_id);
                dest.writeInt(table);
                dest.writeString(goods_unit);
                dest.writeInt(sku_count);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<GoodsResEntity> CREATOR = new Creator<GoodsResEntity>() {
                @Override
                public GoodsResEntity createFromParcel(Parcel in) {
                    return new GoodsResEntity(in);
                }

                @Override
                public GoodsResEntity[] newArray(int size) {
                    return new GoodsResEntity[size];
                }
            };

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public void setOrigin_number(int origin_number) {
                this.origin_number = origin_number;
            }

            public void setShop_price(String shop_price) {
                this.shop_price = shop_price;
            }

            public void setGoods_number(int goods_number) {
                this.goods_number = goods_number;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }

            public void setGoods_thumb(String goods_thumb) {
                this.goods_thumb = goods_thumb;
            }

            public void setCat_id(int cat_id) {
                this.cat_id = cat_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public void setSpecifications(List<CartBean.DataEntity.CartListEntity.SpecificationEntity> specifications) {
                this.specifications = specifications;
            }

            public void setGs_id(int gs_id) {
                this.gs_id = gs_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public int getOrigin_number() {
                return origin_number;
            }

            public String getShop_price() {
                return shop_price;
            }

            public int getGoods_number() {
                return goods_number;
            }

            public int getParent_id() {
                return parent_id;
            }

            public String getGoods_thumb() {
                return goods_thumb;
            }

            public int getCat_id() {
                return cat_id;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public List<CartBean.DataEntity.CartListEntity.SpecificationEntity> getSpecifications() {
                return specifications;
            }

//            public List<CartBean.DataEntity.CartListEntity.SpecificationEntity> getSpecificationArray() {
//                if(specifications != null) {
//                    List<CartBean.DataEntity.CartListEntity.SpecificationEntity> entityList = new ArrayList<>();
//                    for (CartBean.DataEntity.CartListEntity.SpecificationEntity entity : specifications) {
//                        // LogUtils.i("entity.getKey():" + entity.getKey() + "---entity.getValue():" + entity.getValue());
//                        if (StringUtils.isNotEmpty(entity.getValue())) {
//                            String[] value = entity.getValue().split(";");
//                            for (String s : value) {
//                                entityList.add(new CartBean.DataEntity.CartListEntity.SpecificationEntity(entity.getKey(), s));
//                            }
//                        }
//                    }
//                    return entityList;
//                }else {
//                    return new ArrayList<>();
//                }
//            }


            public int getGs_id() {
                return gs_id;
            }

            public int getTable() {
                return table;
            }

            public void setTable(int table) {
                this.table = table;
            }

            public String getGoods_unit() {
                return goods_unit;
            }

            public void setGoods_unit(String goods_unit) {
                this.goods_unit = goods_unit;
            }


            public int getSku_count() {
                return sku_count;
            }

            public void setSku_count(int sku_count) {
                this.sku_count = sku_count;
            }

            // 排序
            public static class GoodsResComparator implements Comparator {
                public int compare(Object o1,Object o2){
                    GoodsResEntity g1 = (GoodsResEntity)o1;
                    GoodsResEntity g2 = (GoodsResEntity)o2;

//                    至于降序升序，可以这样比较：
//                    假如A的值大于B，你返回1。这样调用Collections.sort()方法就是升序
//                    假如A的值大于B，你返回-1。这样调用Collections.sort()方法就是降序
                    int result = 1;
                    if(g1.gs_id > -1 && g2.gs_id > -1){     // 都是普通分类

                        // 4要排前面
                        if(g1.gs_id == 4 ){
                            result = 1;
                        }else if(g2.gs_id == 4){
                            result = -1;
                        }else if(g1.gs_id == 4  && g2.gs_id == 4){
                            result = 0;
                        }else {
                            result = g1.cat_id > g2.cat_id ? 1 : (g1.cat_id == g2.cat_id ? 0 : -1);
                        }

                    }else if(g1.gs_id > -1 && g2.gs_id == -1){  //
                        result = -1;

                    }else if(g1.gs_id == -1 && g2.gs_id > -1){
                        result = 1;

                    }else if(g1.gs_id == -1 && g2.gs_id == -1){
                        result = g1.goods_id > g2.goods_id ? 1 : (g1.goods_id == g2.goods_id ? 0 : -1);
                    }

                    return result;
                }
            }

            @Override
            public String toString() {
                return "GoodsResEntity{" +
                        "gs_id=" + gs_id +
                        ", goods_id=" + goods_id +
                        ", cat_id=" + cat_id +
                        ", goods_name='" + goods_name + '\'' +
                        ", origin_number=" + origin_number +
                        ", shop_price='" + shop_price + '\'' +
                        ", goods_number=" + goods_number +
                        ", parent_id=" + parent_id +
                        ", goods_thumb='" + goods_thumb + '\'' +
                        ", specifications=" + specifications +
                        ", table=" + table +
                        ", goods_unit='" + goods_unit + '\'' +
                        ", dataNUll=" + dataNUll +
                        '}';
            }

            @Override
            public boolean equals(Object obj) {
                if(obj instanceof GoodsResEntity){
                    GoodsResEntity entity = (GoodsResEntity) obj;

                    if(gs_id > -1 && entity.getGs_id() > -1){
                        return entity.getGoods_id() == goods_id;
                    }else if(gs_id == -1 && entity.getGs_id() > -1){
                        return false;
                    }else if(gs_id > -1 && entity.getGs_id() == -1){
                        return false;
                    }else if(gs_id == -1 && entity.getGs_id() == -1){
                        return entity.getGoods_id() == goods_id;
                    }
                }
                return false;
            }


        }
    }
}
