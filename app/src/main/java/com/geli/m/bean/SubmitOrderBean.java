package com.geli.m.bean;

import java.util.List;

/**
 * author:  shen
 * date:    2018/11/16
 */
public class SubmitOrderBean {

    /**
     * code : 100
     * message : ok
     * data : {"add_list":{"user_id":7287,"address_id":970,"consignee":"梁汉莲测试","mobile":"13432082403","address":"测试","is_default":1,"p_cn":"广东省","p_id":28240,"c_cn":"广州市","c_id":28241,"d_cn":"荔湾区","d_id":28243,"e_cn":"沙面街道","e_id":28244},"shop_list":[{"shop_id":100008,"shop_name":"佳尚新蔬食品","shop_type":2,"is_sh":0,"goods_list":[{"cart_id":13911,"shop_id":100008,"goods_id":96,"cart_number":100,"cart_price":"85.00","cart_subtotal":"8500.00","specification":"[{\"key\":\"规格\",\"value\":\"默认\"}]","sku_id":29546,"is_sh":0,"status":1,"goods_name":"佳尚新蔬 速冻蔬菜 速冻混合蔬菜500g*20包/箱","goods_type":1,"goods_thumb":"upload/old_img/store_100008/goods_5/small_201504281523259371.jpg","goods_unit":"件","origin_number":100}],"shop_sum":8500,"coupon_number":2}],"total_price":8500}
     */

    private int code;
    private String message;
    private DataEntity data;

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

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * add_list : {"user_id":7287,"address_id":970,"consignee":"梁汉莲测试","mobile":"13432082403","address":"测试","is_default":1,"p_cn":"广东省","p_id":28240,"c_cn":"广州市","c_id":28241,"d_cn":"荔湾区","d_id":28243,"e_cn":"沙面街道","e_id":28244}
         * shop_list : [{"shop_id":100008,"shop_name":"佳尚新蔬食品","shop_type":2,"is_sh":0,"goods_list":[{"cart_id":13911,"shop_id":100008,"goods_id":96,"cart_number":100,"cart_price":"85.00","cart_subtotal":"8500.00","specification":"[{\"key\":\"规格\",\"value\":\"默认\"}]","sku_id":29546,"is_sh":0,"status":1,"goods_name":"佳尚新蔬 速冻蔬菜 速冻混合蔬菜500g*20包/箱","goods_type":1,"goods_thumb":"upload/old_img/store_100008/goods_5/small_201504281523259371.jpg","goods_unit":"件","origin_number":100}],"shop_sum":8500,"coupon_number":2}]
         * total_price : 8500
         */

        private AddressListBean.DataEntity add_list;
        private double total_price;
        private List<ShopListEntity> shop_list;

        public AddressListBean.DataEntity getAdd_list() {
            return add_list;
        }

        public void setAdd_list(AddressListBean.DataEntity add_list) {
            this.add_list = add_list;
        }

        public double getTotal_price() {
            return total_price;
        }

        public void setTotal_price(double total_price) {
            this.total_price = total_price;
        }

        public List<ShopListEntity> getShop_list() {
            return shop_list;
        }

        public void setShop_list(List<ShopListEntity> shop_list) {
            this.shop_list = shop_list;
        }


        public static class ShopListEntity {
            /**
             * shop_id : 100008
             * shop_name : 佳尚新蔬食品
             * shop_type : 2
             * is_sh : 0
             * goods_list : [{"cart_id":13911,"shop_id":100008,"goods_id":96,"cart_number":100,"cart_price":"85.00","cart_subtotal":"8500.00","specification":"[{\"key\":\"规格\",\"value\":\"默认\"}]","sku_id":29546,"is_sh":0,"status":1,"goods_name":"佳尚新蔬 速冻蔬菜 速冻混合蔬菜500g*20包/箱","goods_type":1,"goods_thumb":"upload/old_img/store_100008/goods_5/small_201504281523259371.jpg","goods_unit":"件","origin_number":100}]
             * shop_sum : 8500
             * coupon_number : 2
             */

            private int shop_id;
            private String shop_name;
            private int shop_type;
            private int is_sh;
            private Double shop_sum;
            private int coupon_number;
            private List<GoodsListEntity> goods_list;
            private String per_cent;
            private String seventy_percent;
            public String couponPrice;
            public String message;
            public int couponId = -1;


            public int getShop_id() {
                return shop_id;
            }

            public void setShop_id(int shop_id) {
                this.shop_id = shop_id;
            }

            public String getShop_name() {
                return shop_name;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }

            public int getShop_type() {
                return shop_type;
            }

            public void setShop_type(int shop_type) {
                this.shop_type = shop_type;
            }

            public int getIs_sh() {
                return is_sh;
            }

            public void setIs_sh(int is_sh) {
                this.is_sh = is_sh;
            }

            public Double getShop_sum() {
                return shop_sum;
            }

            public void setShop_sum(Double shop_sum) {
                this.shop_sum = shop_sum;
            }

            public int getCoupon_number() {
                return coupon_number;
            }

            public void setCoupon_number(int coupon_number) {
                this.coupon_number = coupon_number;
            }

            public List<GoodsListEntity> getGoods_list() {
                return goods_list;
            }

            public void setGoods_list(List<GoodsListEntity> goods_list) {
                this.goods_list = goods_list;
            }

            public String getPer_cent() {
                return per_cent;
            }

            public void setPer_cent(String per_cent) {
                this.per_cent = per_cent;
            }

            public String getSeventy_percent() {
                return seventy_percent;
            }

            public void setSeventy_percent(String seventy_percent) {
                this.seventy_percent = seventy_percent;
            }

            public String getCouponPrice() {
                return couponPrice;
            }

            public void setCouponPrice(String couponPrice) {
                this.couponPrice = couponPrice;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public int getCouponId() {
                return couponId;
            }

            public void setCouponId(int couponId) {
                this.couponId = couponId;
            }

            public static class GoodsListEntity {
                /**
                 * cart_id : 13911
                 * shop_id : 100008
                 * goods_id : 96
                 * cart_number : 100
                 * cart_price : 85.00
                 * cart_subtotal : 8500.00
                 * specification : [{"key":"规格","value":"默认"}]
                 * sku_id : 29546
                 * is_sh : 0
                 * status : 1
                 * goods_name : 佳尚新蔬 速冻蔬菜 速冻混合蔬菜500g*20包/箱
                 * goods_type : 1
                 * goods_thumb : upload/old_img/store_100008/goods_5/small_201504281523259371.jpg
                 * goods_unit : 件
                 * origin_number : 100
                 */

                private int cart_id;
                private int shop_id;
                private int goods_id;
                private int cart_number;
                private String cart_price;
                private String cart_subtotal;
                // private String specification;
                private int sku_id;
                private int is_sh;
                private int status;
                private String goods_name;
                private int goods_type;
                private String goods_thumb;
                private String goods_unit;
                private int origin_number;
                private List<CartBean.DataEntity.CartListEntity.SpecificationEntity> specification;

                public List<CartBean.DataEntity.CartListEntity.SpecificationEntity> getSpecification() {
                    return specification;
                }

                public void setSpecification(List<CartBean.DataEntity.CartListEntity.SpecificationEntity> specification) {
                    this.specification = specification;
                }

                public int getCart_id() {
                    return cart_id;
                }

                public void setCart_id(int cart_id) {
                    this.cart_id = cart_id;
                }

                public int getShop_id() {
                    return shop_id;
                }

                public void setShop_id(int shop_id) {
                    this.shop_id = shop_id;
                }

                public int getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(int goods_id) {
                    this.goods_id = goods_id;
                }

                public int getCart_number() {
                    return cart_number;
                }

                public void setCart_number(int cart_number) {
                    this.cart_number = cart_number;
                }

                public String getCart_price() {
                    return cart_price;
                }

                public void setCart_price(String cart_price) {
                    this.cart_price = cart_price;
                }

                public String getCart_subtotal() {
                    return cart_subtotal;
                }

                public void setCart_subtotal(String cart_subtotal) {
                    this.cart_subtotal = cart_subtotal;
                }

//                public String getSpecification() {
//                    return specification;
//                }
//
//                public void setSpecification(String specification) {
//                    this.specification = specification;
//                }

                public int getSku_id() {
                    return sku_id;
                }

                public void setSku_id(int sku_id) {
                    this.sku_id = sku_id;
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

                public String getGoods_name() {
                    return goods_name;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public int getGoods_type() {
                    return goods_type;
                }

                public void setGoods_type(int goods_type) {
                    this.goods_type = goods_type;
                }

                public String getGoods_thumb() {
                    return goods_thumb;
                }

                public void setGoods_thumb(String goods_thumb) {
                    this.goods_thumb = goods_thumb;
                }

                public String getGoods_unit() {
                    return goods_unit;
                }

                public void setGoods_unit(String goods_unit) {
                    this.goods_unit = goods_unit;
                }

                public int getOrigin_number() {
                    return origin_number;
                }

                public void setOrigin_number(int origin_number) {
                    this.origin_number = origin_number;
                }
            }
        }
    }
}
