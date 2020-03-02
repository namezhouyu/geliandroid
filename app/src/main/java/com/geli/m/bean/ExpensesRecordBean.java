package com.geli.m.bean;

import java.util.List;


/**
 * 消费详情
 */
public class ExpensesRecordBean {

    /**
     * {
     * "code":100,
     * "message":"ok",
     * "data":[{"order_sn":"","add_time":1525659570,"plat_srl":"1812700000573265","ptn_srl":"1525659570104","trade_type":1,"use_type":1,"trade_money":"8955.00","remark":"\u94f6\u5546\u51fa\u91d1(\u4e2a\u4eba)","name":""},{"order_sn":"GL20180420114731685291","add_time":1524196057,"plat_srl":"1811000000557677","ptn_srl":"1524196057778","trade_type":1,"use_type":4,"trade_money":"26.40","remark":"\u9884\u4ed8\u6b3e(\u56e2\u8d2d30%)","name":"\u683c\u5229\u98df\u54c1\u7f51"},{"order_sn":"GL20180420114536581656","add_time":1524195962,"plat_srl":"1811000000557673","ptn_srl":"1524195962137","trade_type":1,"use_type":4,"trade_money":"330.00","remark":"\u9884\u4ed8\u6b3e(\u56e2\u8d2d30%)","name":"\u683c\u5229\u98df\u54c1\u7f51"},{"order_sn":"GL20180420093043514581","add_time":1524187897,"plat_srl":"1811000000557497","ptn_srl":"1524187897476","trade_type":1,"use_type":4,"trade_money":"9.90","remark":"\u9884\u4ed8\u6b3e(\u56e2\u8d2d30%)","name":"\u683c\u5229\u98df\u54c1\u7f51"},{"order_sn":"GL20180419170630815017","add_time":1524128796,"plat_srl":"1810900000556508","ptn_srl":"1524128796213","trade_type":1,"use_type":4,"trade_money":"3.30","remark":"\u9884\u4ed8\u6b3e(\u56e2\u8d2d30%)","name":"\u683c\u5229\u98df\u54c1\u7f51"},{"order_sn":"GL20180419165217737350","add_time":1524128613,"plat_srl":"1810900000556503","ptn_srl":"1524128613193","trade_type":1,"use_type":4,"trade_money":"19.80","remark":"\u9884\u4ed8\u6b3e(\u56e2\u8d2d30%)","name":"\u683c\u5229\u98df\u54c1\u7f51"},{"order_sn":"GL20180419165148372578","add_time":1524128502,"plat_srl":"1810900000556501","ptn_srl":"1524128502504","trade_type":1,"use_type":4,"trade_money":"330.00","remark":"\u9884\u4ed8\u6b3e(\u56e2\u8d2d30%)","name":"\u683c\u5229\u98df\u54c1\u7f51"},{"order_sn":"GL20180419161439643253","add_time":1524125787,"plat_srl":"1810900000556347","ptn_srl":"1524125787252","trade_type":1,"use_type":4,"trade_money":"13.20","remark":"\u9884\u4ed8\u6b3e(\u56e2\u8d2d30%)","name":"\u683c\u5229\u98df\u54c1\u7f51"},{"order_sn":"GL20180419161320974811","add_time":1524125733,"plat_srl":"1810900000556345","ptn_srl":"1524125733122","trade_type":1,"use_type":4,"trade_money":"135.30","remark":"\u9884\u4ed8\u6b3e(\u56e2\u8d2d30%)","name":"\u683c\u5229\u98df\u54c1\u7f51"},{"order_sn":"GL20180419160544343086","add_time":1524125152,"plat_srl":"1810900000556285","ptn_srl":"1524125152753","trade_type":1,"use_type":4,"trade_money":"132.00","remark":"\u9884\u4ed8\u6b3e(\u56e2\u8d2d30%)","name":"\u683c\u5229\u98df\u54c1\u7f51"}]}
     */
    private int code;
    private String message;
    private List<DataEntity> data;

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }
    public List<DataEntity> getData() {
        return data;
    }



    public static class DataEntity {


        private String order_sn;
        /** 时间 */
        private int add_time;
        private String plat_srl;
        /** 订单号? 交易号 */
        private String ptn_srl;
        /** 交易类型 */
        private int trade_type;
        private int use_type;
        private String trade_money;
        private String remark;
        private String name;

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public String getPlat_srl() {
            return plat_srl;
        }

        public void setPlat_srl(String plat_srl) {
            this.plat_srl = plat_srl;
        }

        public String getPtn_srl() {
            return ptn_srl;
        }

        public void setPtn_srl(String ptn_srl) {
            this.ptn_srl = ptn_srl;
        }

        public int getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(int trade_type) {
            this.trade_type = trade_type;
        }

        public int getUse_type() {
            return use_type;
        }

        public void setUse_type(int use_type) {
            this.use_type = use_type;
        }

        public String getTrade_money() {
            return trade_money;
        }

        public void setTrade_money(String trade_money) {
            this.trade_money = trade_money;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}

