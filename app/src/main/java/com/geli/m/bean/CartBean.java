package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/15.
 */

public class CartBean {
    /**
     * code : 100
     * data : [{"shop_id":1,"cart_list":[{"cart_id":12686,"goods_name":"去骨安格斯西冷肉 PRS 级 20KG/箱 抄码","cart_subtotal":"45.00","origin_number":1,"cart_price":"45.00","goods_unit":"箱","cart_number":1,"goods_thumb":"upload/goods/20171124/151151751922938.png","goods_id":4888,"specification":[{"value":"1kg*10包/箱","key":"规格"}],"goods_type":2,"spec":{"goods_sku":[{"sku_attr":"{1:26768}","price":"2960","goods_id":4888,"sku_id":26751,"inventory":1000,"sales":297}],"spec_attr":[{"res":[{"attr_name":"抄码，20KG/箱左右","attr_id":26768}],"spec_id":1,"spec_name":"规格"}]}},{"cart_id":12685,"goods_name":"爱尔兰鲭鱼400-600/条","cart_subtotal":"11.00","origin_number":1,"cart_price":"11.00","goods_unit":"吨","cart_number":1,"goods_thumb":"upload/goods/20171124/151150406979172.png","goods_id":4887,"specification":[{"value":"1kg*10包/箱","key":"规格"}],"goods_type":3,"spec":{"goods_sku":[{"sku_attr":"{1:26769}","price":"0","goods_id":4887,"sku_id":26752,"inventory":1000,"sales":443}],"spec_attr":[{"res":[{"attr_name":"400-600/条","attr_id":26769}],"spec_id":1,"spec_name":"规格"}]}},{"cart_id":12295,"goods_name":"大成经典盐酥鸡（专供） 2.5kg*4包/件","cart_subtotal":"2450.00","origin_number":1,"cart_price":"245.00","goods_unit":"件","cart_number":10,"goods_thumb":"upload/201606/thumb_img/34633_thumb_G_1467097937211.jpg","goods_id":1878,"specification":null,"goods_type":1,"spec":{"goods_sku":[{"sku_attr":"{1:27792}","price":"245","goods_id":1878,"sku_id":27775,"inventory":9506,"sales":331}],"spec_attr":[{"res":[{"attr_name":"2.5kg*4包/件","attr_id":27792}],"spec_id":1,"spec_name":"规格"}]}},{"cart_id":12294,"goods_name":"六和爆浆鸡排 1.1kg*8包/件 ","cart_subtotal":"230.00","origin_number":1,"cart_price":"230.00","goods_unit":"件","cart_number":1,"goods_thumb":"upload/201607/thumb_img/34654_thumb_G_1467683572486.jpg","goods_id":1899,"specification":null,"goods_type":1,"spec":{"goods_sku":[{"sku_attr":"{1:27772}","price":"230","goods_id":1899,"sku_id":27755,"inventory":9886,"sales":237}],"spec_attr":[{"res":[{"attr_name":"1.1kg*8包/件 ","attr_id":27772}],"spec_id":1,"spec_name":"规格"}]}}],"shop_img":"upload/shops/y1499834890.png","shop_name":"格利食品网","moq":10}]
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
         * shop_id : 1
         * cart_list : [{"cart_id":12686,"goods_name":"去骨安格斯西冷肉 PRS 级 20KG/箱 抄码","cart_subtotal":"45.00","origin_number":1,"cart_price":"45.00","goods_unit":"箱","cart_number":1,"goods_thumb":"upload/goods/20171124/151151751922938.png","goods_id":4888,"specification":[{"value":"1kg*10包/箱","key":"规格"}],"goods_type":2,"spec":{"goods_sku":[{"sku_attr":"{1:26768}","price":"2960","goods_id":4888,"sku_id":26751,"inventory":1000,"sales":297}],"spec_attr":[{"res":[{"attr_name":"抄码，20KG/箱左右","attr_id":26768}],"spec_id":1,"spec_name":"规格"}]}},{"cart_id":12685,"goods_name":"爱尔兰鲭鱼400-600/条","cart_subtotal":"11.00","origin_number":1,"cart_price":"11.00","goods_unit":"吨","cart_number":1,"goods_thumb":"upload/goods/20171124/151150406979172.png","goods_id":4887,"specification":[{"value":"1kg*10包/箱","key":"规格"}],"goods_type":3,"spec":{"goods_sku":[{"sku_attr":"{1:26769}","price":"0","goods_id":4887,"sku_id":26752,"inventory":1000,"sales":443}],"spec_attr":[{"res":[{"attr_name":"400-600/条","attr_id":26769}],"spec_id":1,"spec_name":"规格"}]}},{"cart_id":12295,"goods_name":"大成经典盐酥鸡（专供） 2.5kg*4包/件","cart_subtotal":"2450.00","origin_number":1,"cart_price":"245.00","goods_unit":"件","cart_number":10,"goods_thumb":"upload/201606/thumb_img/34633_thumb_G_1467097937211.jpg","goods_id":1878,"specification":null,"goods_type":1,"spec":{"goods_sku":[{"sku_attr":"{1:27792}","price":"245","goods_id":1878,"sku_id":27775,"inventory":9506,"sales":331}],"spec_attr":[{"res":[{"attr_name":"2.5kg*4包/件","attr_id":27792}],"spec_id":1,"spec_name":"规格"}]}},{"cart_id":12294,"goods_name":"六和爆浆鸡排 1.1kg*8包/件 ","cart_subtotal":"230.00","origin_number":1,"cart_price":"230.00","goods_unit":"件","cart_number":1,"goods_thumb":"upload/201607/thumb_img/34654_thumb_G_1467683572486.jpg","goods_id":1899,"specification":null,"goods_type":1,"spec":{"goods_sku":[{"sku_attr":"{1:27772}","price":"230","goods_id":1899,"sku_id":27755,"inventory":9886,"sales":237}],"spec_attr":[{"res":[{"attr_name":"1.1kg*8包/件 ","attr_id":27772}],"spec_id":1,"spec_name":"规格"}]}}]
         * shop_img : upload/shops/y1499834890.png
         * shop_name : 格利食品网
         * moq : 10
         * is_sh:1
         *
         * sh : {"amount":"2000.00","cost":"0.00","temp_amount":"0.00","is_temp":0,"status":1}
         */
        public int shop_id;
        public boolean isCheck;
        private List<CartListEntity> cart_list;
        private String shop_img;
        private String shop_name;
        private int moq;
        public String unit;
        public int size = 0;                // 商品数量
        private ShEntity sh;
        private boolean isExceed;                   // 是否超出预算(账期额度) -- 不是后台字段
        private double accountLimit;               // 购物车选中的使用账期的商品 的 总额度 -- 不是后台字段
        public boolean isUseAP;           // 是否勾选了账期购买 -- 不是后台字段
        public int shop_sh_status;        // 是否有开通账期的权限 -- 0不可以开通 1可以开通
        private int sh_apply;             // 正在申请 0没有申请 1申请中


        @Override
        public String toString() {
            return "DataEntity{" +
                    "shop_id=" + shop_id +
                    ", isCheck=" + isCheck +
                    ", cart_list=" + cart_list +
                    ", shop_img='" + shop_img + '\'' +
                    ", shop_name='" + shop_name + '\'' +
                    ", moq=" + moq +
                    ", unit='" + unit + '\'' +
                    ", size=" + size +
                    ", sh=" + sh +
                    ", isExceed=" + isExceed +
                    ", accountLimit=" + accountLimit +
                    ", isUseAP=" + isUseAP +
                    ", shop_sh_status=" + shop_sh_status +
                    ", sh_apply=" + sh_apply +
                    '}';
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public void setCart_list(List<CartListEntity> cart_list) {
            this.cart_list = cart_list;
        }

        public void setShop_img(String shop_img) {
            this.shop_img = shop_img;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public void setMoq(int moq) {
            this.moq = moq;
        }

        public int getShop_id() {
            return shop_id;
        }

        public List<CartListEntity> getCart_list() {
            return cart_list;
        }

        public String getShop_img() {
            return shop_img;
        }

        public String getShop_name() {
            return shop_name;
        }

        public int getMoq() {
            return moq;
        }

        public ShEntity getSh() {
            return sh;
        }

        public void setSh(ShEntity sh) {
            this.sh = sh;
        }

        public boolean isExceed() {
            return isExceed;
        }

        public void setExceed(boolean exceed) {
            isExceed = exceed;
        }

        public double getAccountLimit() {
            return accountLimit;
        }

        public void setAccountLimit(double accountLimit) {
            this.accountLimit = accountLimit;
        }

        public int getShop_sh_status() {
            return shop_sh_status;
        }

        public void setShop_sh_status(int shop_sh_status) {
            this.shop_sh_status = shop_sh_status;
        }

        public int getSh_apply() {
            return sh_apply;
        }

        public void setSh_apply(int sh_apply) {
            this.sh_apply = sh_apply;
        }

        public static class CartListEntity implements Parcelable {
            /**
             * cart_id : 12686
             * goods_name : 去骨安格斯西冷肉 PRS 级 20KG/箱 抄码
             * cart_subtotal : 45.00
             * origin_number : 1
             * cart_price : 45.00
             * goods_unit : 箱
             * cart_number : 1
             * goods_thumb : upload/goods/20171124/151151751922938.png
             * goods_id : 4888
             * specification : [{"value":"1kg*10包/箱","key":"规格"}]
             * goods_type : 2
             * spec : {
             * "goods_sku":[{"sku_attr":"{1:26768}","price":"2960","goods_id":4888,"sku_id":26751,"inventory":1000,"sales":297}]
             * ,"spec_attr":[{"res":[{"attr_name":"抄码，20KG/箱左右","attr_id":26768}],"spec_id":1,"spec_name":"规格"}]}
             *
             * is_sh : 0
             */
            public int shop_id;
            public boolean isCheck;
            private int cart_id;
            private String goods_name;
            private String cart_subtotal;
            private int origin_number;
            private String cart_price;
            private String goods_unit;
            private int cart_number = 1;            // -- 这个是起订量?
            private String goods_thumb;
            private int goods_id;
            private List<SpecificationEntity> specification;
            private int goods_type;
            private SpecEntity spec;
            private int sku_id = -1;        // 型号
            public boolean isShowEdit;
            public String json = "";
            public List<SpecificationEntity> tempSpecifi;//缓存规格
            public boolean isDialog;            // 弹窗修改 -- true : 弹出修改的时候
            public int is_sh = 0;                   // 是否为账期结算，1是0非
            private int status;                     // 1正常，2失效
            private double sh_price;                // 账期价格（单价）
            private int shop_sh_status;             // 商店账期状态  0：未开通； 1：开通了


            @Override
            public String toString() {
                return "CartListEntity{" +
                        "shop_id=" + shop_id +
                        ", isCheck=" + isCheck +
                        ", cart_id=" + cart_id +
                        ", goods_name='" + goods_name + '\'' +
                        ", cart_subtotal='" + cart_subtotal + '\'' +
                        ", origin_number=" + origin_number +
                        ", cart_price='" + cart_price + '\'' +
                        ", goods_unit='" + goods_unit + '\'' +
                        ", cart_number=" + cart_number +
                        ", goods_thumb='" + goods_thumb + '\'' +
                        ", goods_id=" + goods_id +
                        ", specification=" + specification +
                        ", goods_type=" + goods_type +
                        ", spec=" + spec +
                        ", sku_id=" + sku_id +
                        ", isShowEdit=" + isShowEdit +
                        ", json='" + json + '\'' +
                        ", tempSpecifi=" + tempSpecifi +
                        ", isDialog=" + isDialog +
                        ", is_sh=" + is_sh +
                        ", status=" + status +
                        ", sh_price=" + sh_price +
                        ", shop_sh_status=" + shop_sh_status +
                        '}';
            }

            public CartListEntity() {

            }

            protected CartListEntity(Parcel in) {
                shop_id = in.readInt();
                isCheck = in.readByte() != 0;
                cart_id = in.readInt();
                goods_name = in.readString();
                cart_subtotal = in.readString();
                origin_number = in.readInt();
                cart_price = in.readString();
                goods_unit = in.readString();
                cart_number = in.readInt();
                goods_thumb = in.readString();
                goods_id = in.readInt();
                goods_type = in.readInt();
                sku_id = in.readInt();
                is_sh = in.readInt();
                status = in.readInt();
                sh_price = in.readDouble();
                shop_sh_status = in.readInt();
            }

            public static final Creator<CartListEntity> CREATOR = new Creator<CartListEntity>() {
                @Override
                public CartListEntity createFromParcel(Parcel in) {
                    return new CartListEntity(in);
                }

                @Override
                public CartListEntity[] newArray(int size) {
                    return new CartListEntity[size];
                }
            };

            public void setCart_id(int cart_id) {
                this.cart_id = cart_id;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public void setCart_subtotal(String cart_subtotal) {
                this.cart_subtotal = cart_subtotal;
            }

            public void setOrigin_number(int origin_number) {
                this.origin_number = origin_number;
            }

            public void setCart_price(String cart_price) {
                this.cart_price = cart_price;
            }

            public void setGoods_unit(String goods_unit) {
                this.goods_unit = goods_unit;
            }

            public void setCart_number(int cart_number) {
                this.cart_number = cart_number;
            }

            public void setGoods_thumb(String goods_thumb) {
                this.goods_thumb = goods_thumb;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public void setSpecification(List<SpecificationEntity> specification) {
                this.specification = specification;
            }

            public void setGoods_type(int goods_type) {
                this.goods_type = goods_type;
            }

            public void setSpec(SpecEntity spec) {
                this.spec = spec;
            }

            public int getCart_id() {
                return cart_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public String getCart_subtotal() {
                return cart_subtotal;
            }

            public int getOrigin_number() {
                return origin_number;
            }

            public String getCart_price() {
                return cart_price;
            }

            public String getGoods_unit() {
                return goods_unit;
            }

            public int getCart_number() {
                return cart_number;
            }

            public String getGoods_thumb() {
                return goods_thumb;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public List<SpecificationEntity> getSpecification() {
                return specification;
            }

            public int getGoods_type() {
                return goods_type;
            }

            public SpecEntity getSpec() {
                return spec;
            }


            public int getSku_id() {
                return sku_id;
            }

            public void setSku_id(int sku_id) {
                this.sku_id = sku_id;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public int getIs_sh() {
                return is_sh;
            }

            public void setIs_sh(int is_sh) {
                this.is_sh = is_sh;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public double getSh_price() {
                return sh_price;
            }

            public void setSh_price(double sh_price) {
                this.sh_price = sh_price;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(shop_id);
                dest.writeByte((byte) (isCheck ? 1 : 0));
                dest.writeInt(cart_id);
                dest.writeString(goods_name);
                dest.writeString(cart_subtotal);
                dest.writeInt(origin_number);
                dest.writeString(cart_price);
                dest.writeString(goods_unit);
                dest.writeInt(cart_number);
                dest.writeString(goods_thumb);
                dest.writeInt(goods_id);
                dest.writeInt(goods_type);
                dest.writeInt(sku_id);
                dest.writeInt(is_sh);
                dest.writeInt(status);
                dest.writeDouble(sh_price);
                dest.writeInt(shop_sh_status);
            }

            public static class SpecificationEntity implements Parcelable {
//                /**
//                 * value : 1kg*10包/箱
//                 * key : 规格
//                 */

                private String value;
                private String key;

                public SpecificationEntity() {

                }

                public SpecificationEntity(String key, String value) {
                    this.value = value;
                    this.key = key;
                }

                protected SpecificationEntity(Parcel in) {
                    value = in.readString();
                    key = in.readString();
                }

                public static final Creator<SpecificationEntity> CREATOR = new Creator<SpecificationEntity>() {
                    @Override
                    public SpecificationEntity createFromParcel(Parcel in) {
                        return new SpecificationEntity(in);
                    }

                    @Override
                    public SpecificationEntity[] newArray(int size) {
                        return new SpecificationEntity[size];
                    }
                };

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

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(value);
                    dest.writeString(key);
                }
            }

            public static class SpecEntity {
                /**
                 * goods_sku : [{"sku_attr":"{1:26768}","price":"2960","goods_id":4888,"sku_id":26751,"inventory":1000,"sales":297}]
                 * spec_attr : [{"res":[{"attr_name":"抄码，20KG/箱左右","attr_id":26768}],"spec_id":1,"spec_name":"规格"}]
                 */
                private List<GoodsSkuEntity> goods_sku;
                private List<SpecAttrEntity> spec_attr;

                public void setGoods_sku(List<GoodsSkuEntity> goods_sku) {
                    this.goods_sku = goods_sku;
                }

                public void setSpec_attr(List<SpecAttrEntity> spec_attr) {
                    this.spec_attr = spec_attr;
                }

                public List<GoodsSkuEntity> getGoods_sku() {
                    return goods_sku;
                }

                public List<SpecAttrEntity> getSpec_attr() {
                    return spec_attr;
                }

                public static class GoodsSkuEntity {
                    /**
                     * sku_attr : {1:26768}
                     * price : 2960
                     * goods_id : 4888
                     * sku_id : 26751
                     * inventory : 1000
                     * sales : 297
                     */
                    private String sku_attr;
                    private String price;
                    private int goods_id;
                    private int sku_id;
                    private int inventory;
                    private int sales;

                    public void setSku_attr(String sku_attr) {
                        this.sku_attr = sku_attr;
                    }

                    public void setPrice(String price) {
                        this.price = price;
                    }

                    public void setGoods_id(int goods_id) {
                        this.goods_id = goods_id;
                    }

                    public void setSku_id(int sku_id) {
                        this.sku_id = sku_id;
                    }

                    public void setInventory(int inventory) {
                        this.inventory = inventory;
                    }

                    public void setSales(int sales) {
                        this.sales = sales;
                    }

                    public String getSku_attr() {
                        return sku_attr;
                    }

                    public String getPrice() {
                        return price;
                    }

                    public int getGoods_id() {
                        return goods_id;
                    }

                    public int getSku_id() {
                        return sku_id;
                    }

                    public int getInventory() {
                        return inventory;
                    }

                    public int getSales() {
                        return sales;
                    }
                }

                public static class SpecAttrEntity {
                    /**
                     * res : [{"attr_name":"抄码，20KG/箱左右","attr_id":26768}]
                     * spec_id : 1
                     * spec_name : 规格
                     */
                    private List<ResEntity> res;
                    private int spec_id;
                    private String spec_name;

                    public void setRes(List<ResEntity> res) {
                        this.res = res;
                    }

                    public void setSpec_id(int spec_id) {
                        this.spec_id = spec_id;
                    }

                    public void setSpec_name(String spec_name) {
                        this.spec_name = spec_name;
                    }

                    public List<ResEntity> getRes() {
                        return res;
                    }

                    public int getSpec_id() {
                        return spec_id;
                    }

                    public String getSpec_name() {
                        return spec_name;
                    }

                    public static class ResEntity {
                        /**
                         * attr_name : 抄码，20KG/箱左右
                         * attr_id : 26768
                         */
                        private String attr_name;
                        private int attr_id;

                        public void setAttr_name(String attr_name) {
                            this.attr_name = attr_name;
                        }

                        public void setAttr_id(int attr_id) {
                            this.attr_id = attr_id;
                        }

                        public String getAttr_name() {
                            return attr_name;
                        }

                        public int getAttr_id() {
                            return attr_id;
                        }
                    }
                }
            }
        }

        public static class ShEntity implements Parcelable {

            /**
             * amount : 2000.00
             * cost : 0.00
             * temp_amount : 0.00
             * is_temp : 0
             * status : 1
             * max : 5000.00
             * sh_day : 10
             * day_rate : 1
             *
             */

            private String amount;              // 账期额度
            private String cost;                // 已使用的额度
            private String temp_amount;         // 临时账期额度
            private int is_temp;                // 是否申请过临时额度，1是0非  -- 如果是2就是申请中
            private int status;                 // 账期状态，0取消账期权限，1开通权限，2账期修改中
            private String max;                 // 商店账期最大值
            private int sh_day;                 // 账期天数
            private Double day_rate;               // 日利息 -- 要除以100  --
            private int sh_status;                 // 账期状态***，1使用账期付款(未确定收货)，1账期已归还(未使用账期)，
            // 3账期逾期, 4账期开始生效(确认收货)

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getCost() {
                return cost;
            }

            public void setCost(String cost) {
                this.cost = cost;
            }

            public String getTemp_amount() {
                return temp_amount;
            }

            public void setTemp_amount(String temp_amount) {
                this.temp_amount = temp_amount;
            }

            public int getIs_temp() {
                return is_temp;
            }

            public void setIs_temp(int is_temp) {
                this.is_temp = is_temp;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getMax() {
                return max;
            }

            public void setMax(String max) {
                this.max = max;
            }

            public int getSh_day() {
                return sh_day;
            }

            public void setSh_day(int sh_day) {
                this.sh_day = sh_day;
            }

            public Double getDay_rate() {
                return day_rate;
            }

            public void setDay_rate(Double day_rate) {
                this.day_rate = day_rate;
            }

            public int getSh_status() {
                return sh_status;
            }

            public void setSh_status(int sh_status) {
                this.sh_status = sh_status;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.amount);
                dest.writeString(this.cost);
                dest.writeString(this.temp_amount);
                dest.writeInt(this.is_temp);
                dest.writeInt(this.status);
                dest.writeString(this.max);
                dest.writeInt(this.sh_day);
                dest.writeDouble(this.day_rate);
                dest.writeInt(this.sh_status);
            }

            public ShEntity() {
            }

            protected ShEntity(Parcel in) {
                this.amount = in.readString();
                this.cost = in.readString();
                this.temp_amount = in.readString();
                this.is_temp = in.readInt();
                this.status = in.readInt();
                this.max = in.readString();
                this.sh_day = in.readInt();
                this.day_rate = in.readDouble();
                this.sh_status = in.readInt();
            }

            public static final Parcelable.Creator<ShEntity> CREATOR = new Parcelable.Creator<ShEntity>() {
                @Override
                public ShEntity createFromParcel(Parcel source) {
                    return new ShEntity(source);
                }

                @Override
                public ShEntity[] newArray(int size) {
                    return new ShEntity[size];
                }
            };
        }
    }
}
