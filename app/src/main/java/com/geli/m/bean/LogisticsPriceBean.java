package com.geli.m.bean;

import java.util.List;

/**
 * Created by Steam_l on 2018/3/7.
 */

public class LogisticsPriceBean {

    /**
     * code : 100
     * data : {"shop_list":[{"shop_id":"100008","fee":806.83997,"deliver_fee":300,"trunk_fee":506.84000000000003},{"shop_id":"100008","fee":806.83997,"deliver_fee":300,"trunk_fee":506.84000000000003}],"logistics_fee_sum":806.83997,"deliver_fee_sum":300,"trunk_fee_sum":506.84000000000003}
     * message : ok
     */
    private int code;
    private DataEntity data;
    private String message;
    private int is_door;//1可以配送

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

    public int getIs_door() {
        return is_door;
    }

    public void setIs_door(int is_door) {
        this.is_door = is_door;
    }

    public static class DataEntity {
        /**
         * shop_list : [{"shop_id":"100008","fee":806.83997,"deliver_fee":300,"trunk_fee":506.84000000000003},{"shop_id":"100008","fee":806.83997,"deliver_fee":300,"trunk_fee":506.84000000000003}]
         * logistics_fee_sum : 806.83997
         * deliver_fee_sum : 300
         * trunk_fee_sum : 506.84000000000003
         */
        private List<ShopListEntity> shop_list;
        private double logistics_fee_sum;
        private double deliver_fee_sum;
        private double trunk_fee_sum;

        public void setShop_list(List<ShopListEntity> shop_list) {
            this.shop_list = shop_list;
        }

        public void setLogistics_fee_sum(double logistics_fee_sum) {
            this.logistics_fee_sum = logistics_fee_sum;
        }

        public void setDeliver_fee_sum(double deliver_fee_sum) {
            this.deliver_fee_sum = deliver_fee_sum;
        }

        public void setTrunk_fee_sum(double trunk_fee_sum) {
            this.trunk_fee_sum = trunk_fee_sum;
        }

        public List<ShopListEntity> getShop_list() {
            return shop_list;
        }

        public double getLogistics_fee_sum() {
            return logistics_fee_sum;
        }

        public double getDeliver_fee_sum() {
            return deliver_fee_sum;
        }

        public double getTrunk_fee_sum() {
            return trunk_fee_sum;
        }

        public static class ShopListEntity {
            /**
             * shop_id : 100008
             * fee : 806.83997
             * deliver_fee : 300
             * trunk_fee : 506.84000000000003
             */
            private String shop_id;
            private double fee;
            private double deliver_fee;
            private double trunk_fee;

            public void setShop_id(String shop_id) {
                this.shop_id = shop_id;
            }

            public void setFee(double fee) {
                this.fee = fee;
            }

            public void setDeliver_fee(double deliver_fee) {
                this.deliver_fee = deliver_fee;
            }

            public void setTrunk_fee(double trunk_fee) {
                this.trunk_fee = trunk_fee;
            }

            public String getShop_id() {
                return shop_id;
            }

            public double getFee() {
                return fee;
            }

            public double getDeliver_fee() {
                return deliver_fee;
            }

            public double getTrunk_fee() {
                return trunk_fee;
            }
        }
    }
}
