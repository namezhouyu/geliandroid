package com.geli.m.bean;

/**
 * Created by Steam_l on 2018/2/7.
 */

public class SubmitOverseasOrderBean {
    /**
     * code : 100
     * data : {"shop_res":{"shop_id":1,"goods_res":{"goods_name":"团购测试商品2","shop_id":1,"goods_intro":"1","goods_unit":"吨","goods_number":"4","goods_thumb":"upload/goods/20180117/151615688045658.png","goods_id":4965,"goods_spec":[{"value":"规格","key":"1kg*10包/箱 "}]},"shop_type":3,"shop_name":"格利食品网"},"add_res":{"consignee":"陈翠红","address":"怕是","address_id":937,"mobile":"15521200849","d_cn":"宁河县","c_cn":"市辖县","e_cn":"宁河镇","is_default":1,"p_cn":"天津市","user_id":239,"d_id":570,"c_id":569,"e_id":572,"p_id":338},"coupon_number":"{\"code\":100,\"message\":\"ok\",\"data\":0}"}
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
         * shop_res : {"shop_id":1,"goods_res":{"goods_name":"团购测试商品2","shop_id":1,"goods_intro":"1","goods_unit":"吨","goods_number":"4","goods_thumb":"upload/goods/20180117/151615688045658.png","goods_id":4965,"goods_spec":[{"value":"规格","key":"1kg*10包/箱 "}]},"shop_type":3,"shop_name":"格利食品网"}
         * add_res : {"consignee":"陈翠红","address":"怕是","address_id":937,"mobile":"15521200849","d_cn":"宁河县","c_cn":"市辖县","e_cn":"宁河镇","is_default":1,"p_cn":"天津市","user_id":239,"d_id":570,"c_id":569,"e_id":572,"p_id":338}
         * coupon_number : {"code":100,"message":"ok","data":0}
         */
        private SubmitOrderBean.DataEntity.ShopListEntity shop_res;
        private AddressListBean.DataEntity add_res;

        public SubmitOrderBean.DataEntity.ShopListEntity getShop_res() {
            return shop_res;
        }

        public void setShop_res(SubmitOrderBean.DataEntity.ShopListEntity shop_res) {
            this.shop_res = shop_res;
        }

        public AddressListBean.DataEntity getAdd_res() {
            return add_res;
        }

        public void setAdd_res(AddressListBean.DataEntity add_res) {
            this.add_res = add_res;
        }
    }
}
