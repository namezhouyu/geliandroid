package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/23.
 */

public class OverseasGoodsOuterBean {

    /**
     * code : 100
     * data : [{"goods_name":"爱尔兰鲭鱼400-600/条","goods_intro":"","origin_number":1,"shop_price":"0.00","cat_name":null,"goods_id":4887,"shop_name":"格利食品网","specifications":[{"value":"400-600/条","key":"规格"}],"shop_id":1,"goods_number":1000,"goods_thumb":"upload/goods/20171124/151150406979172.png","goods_type":3,"goods_img":"upload/goods/20171124/151150406979172.png"},{"goods_name":"巴西带鱼B级","goods_intro":"","origin_number":1,"shop_price":"0.00","cat_name":null,"goods_id":4886,"shop_name":"格利食品网","specifications":[{"value":"100-200g 200-300g 300-500g 500-700g","key":"规格"}],"shop_id":1,"goods_number":1000,"goods_thumb":"upload/goods/20171123/151142972715666.png","goods_type":3,"goods_img":"upload/goods/20171123/151142972715666.png"},{"goods_name":"巴西带鱼A级","goods_intro":"","origin_number":1,"shop_price":"0.00","cat_name":null,"goods_id":4885,"shop_name":"格利食品网","specifications":[{"value":"100-200g 200-300g 300-500g 500-700g","key":"规格"}],"shop_id":1,"goods_number":1000,"goods_thumb":"upload/goods/20171123/151142950461579.png","goods_type":3,"goods_img":"upload/goods/20171123/151142950461579.png"},{"goods_name":"墨西哥野生黄花鱼","goods_intro":"","origin_number":1,"shop_price":"0.00","cat_name":null,"goods_id":4848,"shop_name":"格利食品网","specifications":[{"value":"默认","key":"规格"}],"shop_id":1,"goods_number":1000,"goods_thumb":"upload/goods/20171110/151027800719232.png","goods_type":3,"goods_img":"upload/goods/20171110/151027800719232.png"},{"goods_name":"巴西 鸡爪","goods_intro":"","origin_number":1,"shop_price":"0.00","cat_name":null,"goods_id":4847,"shop_name":"格利食品网","specifications":[{"value":"40g-50g","key":"规格"}],"shop_id":1,"goods_number":1000,"goods_thumb":"upload/goods/20171110/151027623717032.png","goods_type":3,"goods_img":"upload/goods/20171110/151027623717032.png"},{"goods_name":"老挝木薯干","goods_intro":"","origin_number":1,"shop_price":"0.00","cat_name":null,"goods_id":4824,"shop_name":"格利食品网","specifications":[{"value":"默认","key":"规格"}],"shop_id":1,"goods_number":1001,"goods_thumb":"upload/goods/20171107/151003933843907.png","goods_type":3,"goods_img":"upload/goods/20171107/151003933843907.png"},{"goods_name":"老挝木薯","goods_intro":"","origin_number":1,"shop_price":"0.00","cat_name":null,"goods_id":4823,"shop_name":"格利食品网","specifications":[{"value":"默认","key":"规格"}],"shop_id":1,"goods_number":1000,"goods_thumb":"upload/goods/20171107/151003906854625.png","goods_type":3,"goods_img":"upload/goods/20171107/151003906854625.png"},{"goods_name":"泰国白糖","goods_intro":"","origin_number":1,"shop_price":"0.00","cat_name":null,"goods_id":4820,"shop_name":"格利食品网","specifications":[{"value":"默认","key":"规格"}],"shop_id":1,"goods_number":1000,"goods_thumb":"upload/goods/20171107/151001856525466.png","goods_type":3,"goods_img":"upload/goods/20171107/151001856525466.png"},{"goods_name":"老挝木薯粉","goods_intro":"","origin_number":1,"shop_price":"0.00","cat_name":null,"goods_id":4816,"shop_name":"格利食品网","specifications":[{"value":"默认","key":"规格"}],"shop_id":1,"goods_number":1000,"goods_thumb":"upload/goods/20171106/150995966182127.png","goods_type":3,"goods_img":"upload/goods/20171106/150995966182127.png"},{"goods_name":"老挝短米","goods_intro":"","origin_number":1,"shop_price":"0.00","cat_name":null,"goods_id":4815,"shop_name":"格利食品网","specifications":[{"value":"默认","key":"规格"}],"shop_id":1,"goods_number":1000,"goods_thumb":"upload/goods/20171106/150995938829418.png","goods_type":3,"goods_img":"upload/goods/20171106/150995938829418.png"},{"goods_name":"泰国短米","goods_intro":"","origin_number":1,"shop_price":"0.00","cat_name":null,"goods_id":4813,"shop_name":"格利食品网","specifications":[{"value":"默认","key":"规格"}],"shop_id":1,"goods_number":1000,"goods_thumb":"upload/goods/20171106/150995520111491.png","goods_type":3,"goods_img":"upload/goods/20171106/150995520111491.png"},{"goods_name":"巴西全翅","goods_intro":"","origin_number":1,"shop_price":"0.00","cat_name":null,"goods_id":4811,"shop_name":"格利食品网","specifications":[{"value":"65-85g","key":"规格"}],"shop_id":1,"goods_number":1001,"goods_thumb":"upload/goods/20171106/150993932316806.png","goods_type":3,"goods_img":"upload/goods/20171106/150993932316806.png"},{"goods_name":"巴西翅中","goods_intro":"","origin_number":1,"shop_price":"0.00","cat_name":null,"goods_id":4810,"shop_name":"格利食品网","specifications":[{"value":"26-35g","key":"规格"}],"shop_id":1,"goods_number":1000,"goods_thumb":"upload/goods/20171106/150993909450933.png","goods_type":3,"goods_img":"upload/goods/20171106/150993909450933.png"},{"goods_name":"越南巴沙鱼","goods_intro":"","origin_number":1,"shop_price":"0.00","cat_name":null,"goods_id":4804,"shop_name":"格利食品网","specifications":[{"value":"默认","key":"规格"}],"shop_id":1,"goods_number":1001,"goods_thumb":"upload/goods/20171103/150967870616744.png","goods_type":3,"goods_img":"upload/goods/20171103/150967870616744.png"},{"goods_name":"墨西哥马鲛鱼","goods_intro":"","origin_number":1,"shop_price":"0.00","cat_name":null,"goods_id":4802,"shop_name":"格利食品网","specifications":[{"value":"默认","key":"规格"}],"shop_id":1,"goods_number":1000,"goods_thumb":"upload/goods/20171102/150961612617505.png","goods_type":3,"goods_img":"upload/goods/20171102/150961612617505.png"},{"goods_name":"缅甸软壳蟹","goods_intro":"","origin_number":1,"shop_price":"0.00","cat_name":null,"goods_id":4794,"shop_name":"格利食品网","specifications":[{"value":"40/60   60/80   80/100   100/120   120/150   150/200","key":"规格"}],"shop_id":1,"goods_number":1000,"goods_thumb":"upload/goods/20171030/150932977023669.png","goods_type":3,"goods_img":"upload/goods/20171030/150932977023669.png"},{"goods_name":"越南巴沙鱼 ","goods_intro":"","origin_number":1,"shop_price":"0.00","cat_name":"鱼","goods_id":4771,"shop_name":"格利食品网","specifications":[{"value":"1kg/袋（5片/每片约200g）","key":"规格"}],"shop_id":1,"goods_number":10010,"goods_thumb":"upload/goods/20171030/150933383495286.png","goods_type":3,"goods_img":"upload/goods/20171030/150933383495286.png"}]
     * message : ok
     */
    private int code;
    private List<OverseasGoodsBean> data;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(List<OverseasGoodsBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public List<OverseasGoodsBean> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class OverseasGoodsBean  implements Parcelable{

        /**
         * goods_name : 团购测试商品2_第2期
         * goods_intro : 1
         * origin_number : 1
         * coun_id : 12
         * shop_price : 1.00
         * surplus : 7
         * cat_name : 海外的下级
         * sold_number : 4
         * surplus_percentage : 0.6363636363636364
         * end_time : 1520563259
         * goods_id : 4965
         * shop_name : 格利食品网
         * specifications : [{"value":"1kg*10包/箱","key":"规格"}]
         * shop_id : 1
         * start_time : 1516675259
         * groupon_id : 15
         * goods_number : 1111
         * target_number : 11
         * goods_thumb : upload/goods/20180117/151615688045658.png
         * goods_type : 4
         * goods_img : upload/goods/20180117/151615688045658.png
         * participants : 2
         */
        private String goods_name;
        private String goods_intro;
        private int origin_number;
        private int coun_id;
        private String goods_unit;
        private String shop_price;
        private int surplus;
        private String cat_name;
        private String coun_name;
        private int sold_number;
        private double surplus_percentage;
        private int end_time;
        private int goods_id;
        private String shop_name;
        private List<CartBean.DataEntity.CartListEntity.SpecificationEntity> specifications;
        private int shop_id;
        private int start_time;
        private int groupon_id;
        private int goods_number;
        private int target_number;
        private String goods_thumb;
        private int goods_type;
        private String goods_img;
        private int participants;

        private int is_sh;

        public OverseasGoodsBean() {

        }


        protected OverseasGoodsBean(Parcel in) {
            goods_name = in.readString();
            goods_intro = in.readString();
            origin_number = in.readInt();
            coun_id = in.readInt();
            goods_unit = in.readString();
            shop_price = in.readString();
            surplus = in.readInt();
            cat_name = in.readString();
            coun_name = in.readString();
            sold_number = in.readInt();
            surplus_percentage = in.readDouble();
            end_time = in.readInt();
            goods_id = in.readInt();
            shop_name = in.readString();
            specifications = in.createTypedArrayList(CartBean.DataEntity.CartListEntity.SpecificationEntity.CREATOR);
            shop_id = in.readInt();
            start_time = in.readInt();
            groupon_id = in.readInt();
            goods_number = in.readInt();
            target_number = in.readInt();
            goods_thumb = in.readString();
            goods_type = in.readInt();
            goods_img = in.readString();
            participants = in.readInt();
            is_sh = in.readInt();
        }

        public static final Creator<OverseasGoodsBean> CREATOR = new Creator<OverseasGoodsBean>() {
            @Override
            public OverseasGoodsBean createFromParcel(Parcel in) {
                return new OverseasGoodsBean(in);
            }

            @Override
            public OverseasGoodsBean[] newArray(int size) {
                return new OverseasGoodsBean[size];
            }
        };

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public void setGoods_intro(String goods_intro) {
            this.goods_intro = goods_intro;
        }

        public void setOrigin_number(int origin_number) {
            this.origin_number = origin_number;
        }

        public void setCoun_id(int coun_id) {
            this.coun_id = coun_id;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public void setSurplus(int surplus) {
            this.surplus = surplus;
        }

        public void setCat_name(String cat_name) {
            this.cat_name = cat_name;
        }

        public void setSold_number(int sold_number) {
            this.sold_number = sold_number;
        }

        public void setSurplus_percentage(double surplus_percentage) {
            this.surplus_percentage = surplus_percentage;
        }

        public void setEnd_time(int end_time) {
            this.end_time = end_time;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public void setSpecifications(List<CartBean.DataEntity.CartListEntity.SpecificationEntity> specifications) {
            this.specifications = specifications;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public void setStart_time(int start_time) {
            this.start_time = start_time;
        }

        public void setGroupon_id(int groupon_id) {
            this.groupon_id = groupon_id;
        }

        public void setGoods_number(int goods_number) {
            this.goods_number = goods_number;
        }

        public void setTarget_number(int target_number) {
            this.target_number = target_number;
        }

        public void setGoods_thumb(String goods_thumb) {
            this.goods_thumb = goods_thumb;
        }

        public void setGoods_type(int goods_type) {
            this.goods_type = goods_type;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        public void setParticipants(int participants) {
            this.participants = participants;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public String getGoods_intro() {
            return goods_intro;
        }

        public int getOrigin_number() {
            return origin_number;
        }

        public int getCoun_id() {
            return coun_id;
        }

        public String getShop_price() {
            return shop_price;
        }

        public int getSurplus() {
            return surplus;
        }

        public String getCat_name() {
            return cat_name;
        }

        public int getSold_number() {
            return sold_number;
        }

        public double getSurplus_percentage() {
            return surplus_percentage;
        }

        public int getEnd_time() {
            return end_time;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public List<CartBean.DataEntity.CartListEntity.SpecificationEntity> getSpecifications() {
            return specifications;
        }

        public int getShop_id() {
            return shop_id;
        }

        public int getStart_time() {
            return start_time;
        }

        public int getGroupon_id() {
            return groupon_id;
        }

        public int getGoods_number() {
            return goods_number;
        }

        public int getTarget_number() {
            return target_number;
        }

        public String getGoods_thumb() {
            return goods_thumb;
        }

        public int getGoods_type() {
            return goods_type;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public int getParticipants() {
            return participants;
        }

        public String getGoods_unit() {
            return goods_unit;
        }

        public void setGoods_unit(String goods_unit) {
            this.goods_unit = goods_unit;
        }

        public String getCoun_name() {
            return coun_name;
        }

        public void setCoun_name(String coun_name) {
            this.coun_name = coun_name;
        }

        public int getIs_sh() {
            return is_sh;
        }

        public void setIs_sh(int is_sh) {
            this.is_sh = is_sh;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(goods_name);
            dest.writeString(goods_intro);
            dest.writeInt(origin_number);
            dest.writeInt(coun_id);
            dest.writeString(goods_unit);
            dest.writeString(shop_price);
            dest.writeInt(surplus);
            dest.writeString(cat_name);
            dest.writeString(coun_name);
            dest.writeInt(sold_number);
            dest.writeDouble(surplus_percentage);
            dest.writeInt(end_time);
            dest.writeInt(goods_id);
            dest.writeString(shop_name);
            dest.writeTypedList(specifications);
            dest.writeInt(shop_id);
            dest.writeInt(start_time);
            dest.writeInt(groupon_id);
            dest.writeInt(goods_number);
            dest.writeInt(target_number);
            dest.writeString(goods_thumb);
            dest.writeInt(goods_type);
            dest.writeString(goods_img);
            dest.writeInt(participants);
            dest.writeInt(is_sh);

        }
    }
}
