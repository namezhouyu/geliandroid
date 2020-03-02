package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * Created by Steam_l on 2018/3/27.
 */

public class ExpensesBean {
    /**
     * code : 100
     * data : {"consumption":[{"use_type":4,"out_trade_no":"GL20180326173647075573","balance":9930700,"total_amount":"9900.00","trade_status":0,"goods":[{"goods_name":"佳尚新蔬 青豆 500g*20/件 ","goods_number":"100"},{"goods_name":"佳尚新蔬 速冻蔬菜 速冻青梗菜500g*14包/箱","goods_number":"5"}],"shop_name":"佳尚新蔬食品","addr":"湖北省武汉市武昌区踏踏","add_time":1522057011,"order_info":"{\"order_id\":5454,\"user_id\":7217,\"num_str\":\"100,5\",\"order_sn\":\"GL20180326173647075573\",\"sum_amount\":\"9900.00\",\"order_type\":1,\"good_str\":\"1755,116\"}"},{"use_type":4,"out_trade_no":"GL20180326162611077214","balance":9940600,"total_amount":null,"trade_status":0,"goods":[{"goods_name":null,"goods_number":""}],"shop_name":null,"addr":"天津市天津市宁河县踏踏","add_time":1522052774,"order_info":null},{"use_type":4,"out_trade_no":"GL20180326161040050573","balance":9950500,"total_amount":null,"trade_status":0,"goods":[{"goods_name":null,"goods_number":""}],"shop_name":null,"addr":"天津市天津市宁河县踏踏","add_time":1522052359,"order_info":null},{"use_type":4,"out_trade_no":"GL20180326161040050573","balance":9960400,"total_amount":null,"trade_status":0,"goods":[{"goods_name":null,"goods_number":""}],"shop_name":null,"addr":"天津市天津市宁河县踏踏","add_time":1522052245,"order_info":null},{"use_type":4,"out_trade_no":"GL20180326161040050573","balance":9970300,"total_amount":null,"trade_status":0,"goods":[{"goods_name":null,"goods_number":""}],"shop_name":null,"addr":"天津市天津市宁河县踏踏","add_time":1522051843,"order_info":null},{"use_type":4,"out_trade_no":"GL20180326160235055343","balance":9980200,"total_amount":null,"trade_status":0,"goods":[{"goods_name":null,"goods_number":""}],"shop_name":null,"addr":"天津市天津市宁河县踏踏","add_time":1522051357,"order_info":null},{"use_type":4,"out_trade_no":"GL20180326155157092409","balance":9990100,"total_amount":null,"trade_status":0,"goods":[{"goods_name":null,"goods_number":""}],"shop_name":null,"addr":"北京市北京市东城区看看","add_time":1522050809,"order_info":null},{"use_type":4,"out_trade_no":"GL20180326154957001738","balance":100,"total_amount":null,"trade_status":0,"goods":[{"goods_name":null,"goods_number":""}],"shop_name":null,"addr":"天津市天津市宁河县踏踏","add_time":1522050599,"order_info":null},{"use_type":4,"out_trade_no":"GL20180326115042048002","balance":9861798,"total_amount":null,"trade_status":0,"goods":[{"goods_name":null,"goods_number":""}],"shop_name":null,"addr":"天津市天津市宁河县踏踏","add_time":1522036244,"order_info":null},{"use_type":4,"out_trade_no":"GL20180324151850028686","balance":9863799,"total_amount":null,"trade_status":0,"goods":[{"goods_name":null,"goods_number":""}],"shop_name":null,"addr":"天津市天津市宁河县踏踏","add_time":1521875932,"order_info":null},{"use_type":4,"out_trade_no":"GL20180323134834072997","balance":9864799,"total_amount":null,"trade_status":0,"goods":[{"goods_name":null,"goods_number":""}],"shop_name":null,"addr":"天津市天津市宁河县踏踏","add_time":1521784117,"order_info":null},{"use_type":4,"out_trade_no":"GL20180322093001089900","balance":9864879,"total_amount":null,"trade_status":0,"goods":[{"goods_name":null,"goods_number":""}],"shop_name":null,"addr":"天津市天津市宁河县踏踏","add_time":1521682206,"order_info":null},{"use_type":4,"out_trade_no":"GL20180320163226094906","balance":9867879,"total_amount":"0.00","trade_status":0,"goods":[{"goods_name":"团购测试商品2","goods_number":"1"}],"shop_name":"格利食品网","addr":"天津市天津市宁河县踏踏","add_time":1521536038,"order_info":"{\"order_id\":5390,\"num_str\":\"1\",\"order_sn\":\"GL20180320163226094906\",\"sum_amount\":\"0.00\",\"order_type\":2,\"good_str\":\"4965\",\"percentage\":2}"},{"use_type":4,"out_trade_no":"GL20180320162822068624","balance":9867879,"total_amount":"0.00","trade_status":0,"goods":[{"goods_name":"APP测试期货商品数据","goods_number":"1"}],"shop_name":"格利食品网","addr":"天津市天津市宁河县踏踏","add_time":1521535692,"order_info":"{\"order_id\":5389,\"num_str\":\"1\",\"order_sn\":\"GL20180320162822068624\",\"sum_amount\":\"0.00\",\"order_type\":2,\"good_str\":\"5499\",\"percentage\":2}"},{"use_type":4,"out_trade_no":"GL20180320163226094906","balance":9874879,"total_amount":"0.00","trade_status":0,"goods":[{"goods_name":"团购测试商品2","goods_number":"1"}],"shop_name":"格利食品网","addr":"天津市天津市宁河县踏踏","add_time":1521534749,"order_info":"{\"order_id\":5390,\"num_str\":\"1\",\"order_sn\":\"GL20180320163226094906\",\"sum_amount\":\"0.00\",\"order_type\":2,\"good_str\":\"4965\",\"percentage\":1}"},{"use_type":4,"out_trade_no":"GL20180320162822068624","balance":9874880,"total_amount":"0.00","trade_status":0,"goods":[{"goods_name":"APP测试期货商品数据","goods_number":"1"}],"shop_name":"格利食品网","addr":"天津市天津市宁河县踏踏","add_time":1521534504,"order_info":"{\"order_id\":5389,\"num_str\":\"1\",\"order_sn\":\"GL20180320162822068624\",\"sum_amount\":\"0.00\",\"order_type\":2,\"good_str\":\"5499\",\"percentage\":1}"},{"use_type":4,"out_trade_no":"GL20180320160403006646","balance":9877880,"total_amount":"0.00","trade_status":0,"goods":[{"goods_name":"APP测试期货商品数据","goods_number":"1"}],"shop_name":"格利食品网","addr":"天津市天津市宁河县踏踏","add_time":1521533091,"order_info":"{\"order_id\":5388,\"num_str\":\"1\",\"order_sn\":\"GL20180320160403006646\",\"sum_amount\":\"0.00\",\"order_type\":2,\"good_str\":\"5499\",\"percentage\":2}"},{"use_type":4,"out_trade_no":"GL20180320160403006646","balance":9884880,"total_amount":"0.00","trade_status":0,"goods":[{"goods_name":"APP测试期货商品数据","goods_number":"1"}],"shop_name":"格利食品网","addr":"天津市天津市宁河县踏踏","add_time":1521533046,"order_info":"{\"order_id\":5388,\"num_str\":\"1\",\"order_sn\":\"GL20180320160403006646\",\"sum_amount\":\"0.00\",\"order_type\":2,\"good_str\":\"5499\",\"percentage\":1}"},{"use_type":4,"out_trade_no":"GL20180320135818026716","balance":9887880,"total_amount":"0.00","trade_status":0,"goods":[{"goods_name":"团购测试商品2","goods_number":"1"}],"shop_name":"格利食品网","addr":"天津市天津市宁河县踏踏","add_time":1521532988,"order_info":"{\"order_id\":5381,\"num_str\":\"1\",\"order_sn\":\"GL20180320135818026716\",\"sum_amount\":\"0.00\",\"order_type\":2,\"good_str\":\"4965\",\"percentage\":1}"},{"use_type":4,"out_trade_no":"GL20180320135818026716","balance":9887880,"total_amount":"0.00","trade_status":0,"goods":[{"goods_name":"团购测试商品2","goods_number":"1"}],"shop_name":"格利食品网","addr":"天津市天津市宁河县踏踏","add_time":1521530231,"order_info":"{\"order_id\":5381,\"num_str\":\"1\",\"order_sn\":\"GL20180320135818026716\",\"sum_amount\":\"0.00\",\"order_type\":2,\"good_str\":\"4965\"}"}]}
     * message : 0
     * total_record : 69
     */
    private int code;
    private DataEntity data;
    private String message;
    private int total_record;

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTotal_record(int total_record) {
        this.total_record = total_record;
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

    public int getTotal_record() {
        return total_record;
    }

    public static class DataEntity {
        /**
         * consumption : [{"use_type":4,"out_trade_no":"GL20180326173647075573","balance":9930700,"total_amount":"9900.00","trade_status":0,"goods":[{"goods_name":"佳尚新蔬 青豆 500g*20/件 ","goods_number":"100"},{"goods_name":"佳尚新蔬 速冻蔬菜 速冻青梗菜500g*14包/箱","goods_number":"5"}],"shop_name":"佳尚新蔬食品","addr":"湖北省武汉市武昌区踏踏","add_time":1522057011,"order_info":"{\"order_id\":5454,\"user_id\":7217,\"num_str\":\"100,5\",\"order_sn\":\"GL20180326173647075573\",\"sum_amount\":\"9900.00\",\"order_type\":1,\"good_str\":\"1755,116\"}"},{"use_type":4,"out_trade_no":"GL20180326162611077214","balance":9940600,"total_amount":null,"trade_status":0,"goods":[{"goods_name":null,"goods_number":""}],"shop_name":null,"addr":"天津市天津市宁河县踏踏","add_time":1522052774,"order_info":null},{"use_type":4,"out_trade_no":"GL20180326161040050573","balance":9950500,"total_amount":null,"trade_status":0,"goods":[{"goods_name":null,"goods_number":""}],"shop_name":null,"addr":"天津市天津市宁河县踏踏","add_time":1522052359,"order_info":null},{"use_type":4,"out_trade_no":"GL20180326161040050573","balance":9960400,"total_amount":null,"trade_status":0,"goods":[{"goods_name":null,"goods_number":""}],"shop_name":null,"addr":"天津市天津市宁河县踏踏","add_time":1522052245,"order_info":null},{"use_type":4,"out_trade_no":"GL20180326161040050573","balance":9970300,"total_amount":null,"trade_status":0,"goods":[{"goods_name":null,"goods_number":""}],"shop_name":null,"addr":"天津市天津市宁河县踏踏","add_time":1522051843,"order_info":null},{"use_type":4,"out_trade_no":"GL20180326160235055343","balance":9980200,"total_amount":null,"trade_status":0,"goods":[{"goods_name":null,"goods_number":""}],"shop_name":null,"addr":"天津市天津市宁河县踏踏","add_time":1522051357,"order_info":null},{"use_type":4,"out_trade_no":"GL20180326155157092409","balance":9990100,"total_amount":null,"trade_status":0,"goods":[{"goods_name":null,"goods_number":""}],"shop_name":null,"addr":"北京市北京市东城区看看","add_time":1522050809,"order_info":null},{"use_type":4,"out_trade_no":"GL20180326154957001738","balance":100,"total_amount":null,"trade_status":0,"goods":[{"goods_name":null,"goods_number":""}],"shop_name":null,"addr":"天津市天津市宁河县踏踏","add_time":1522050599,"order_info":null},{"use_type":4,"out_trade_no":"GL20180326115042048002","balance":9861798,"total_amount":null,"trade_status":0,"goods":[{"goods_name":null,"goods_number":""}],"shop_name":null,"addr":"天津市天津市宁河县踏踏","add_time":1522036244,"order_info":null},{"use_type":4,"out_trade_no":"GL20180324151850028686","balance":9863799,"total_amount":null,"trade_status":0,"goods":[{"goods_name":null,"goods_number":""}],"shop_name":null,"addr":"天津市天津市宁河县踏踏","add_time":1521875932,"order_info":null},{"use_type":4,"out_trade_no":"GL20180323134834072997","balance":9864799,"total_amount":null,"trade_status":0,"goods":[{"goods_name":null,"goods_number":""}],"shop_name":null,"addr":"天津市天津市宁河县踏踏","add_time":1521784117,"order_info":null},{"use_type":4,"out_trade_no":"GL20180322093001089900","balance":9864879,"total_amount":null,"trade_status":0,"goods":[{"goods_name":null,"goods_number":""}],"shop_name":null,"addr":"天津市天津市宁河县踏踏","add_time":1521682206,"order_info":null},{"use_type":4,"out_trade_no":"GL20180320163226094906","balance":9867879,"total_amount":"0.00","trade_status":0,"goods":[{"goods_name":"团购测试商品2","goods_number":"1"}],"shop_name":"格利食品网","addr":"天津市天津市宁河县踏踏","add_time":1521536038,"order_info":"{\"order_id\":5390,\"num_str\":\"1\",\"order_sn\":\"GL20180320163226094906\",\"sum_amount\":\"0.00\",\"order_type\":2,\"good_str\":\"4965\",\"percentage\":2}"},{"use_type":4,"out_trade_no":"GL20180320162822068624","balance":9867879,"total_amount":"0.00","trade_status":0,"goods":[{"goods_name":"APP测试期货商品数据","goods_number":"1"}],"shop_name":"格利食品网","addr":"天津市天津市宁河县踏踏","add_time":1521535692,"order_info":"{\"order_id\":5389,\"num_str\":\"1\",\"order_sn\":\"GL20180320162822068624\",\"sum_amount\":\"0.00\",\"order_type\":2,\"good_str\":\"5499\",\"percentage\":2}"},{"use_type":4,"out_trade_no":"GL20180320163226094906","balance":9874879,"total_amount":"0.00","trade_status":0,"goods":[{"goods_name":"团购测试商品2","goods_number":"1"}],"shop_name":"格利食品网","addr":"天津市天津市宁河县踏踏","add_time":1521534749,"order_info":"{\"order_id\":5390,\"num_str\":\"1\",\"order_sn\":\"GL20180320163226094906\",\"sum_amount\":\"0.00\",\"order_type\":2,\"good_str\":\"4965\",\"percentage\":1}"},{"use_type":4,"out_trade_no":"GL20180320162822068624","balance":9874880,"total_amount":"0.00","trade_status":0,"goods":[{"goods_name":"APP测试期货商品数据","goods_number":"1"}],"shop_name":"格利食品网","addr":"天津市天津市宁河县踏踏","add_time":1521534504,"order_info":"{\"order_id\":5389,\"num_str\":\"1\",\"order_sn\":\"GL20180320162822068624\",\"sum_amount\":\"0.00\",\"order_type\":2,\"good_str\":\"5499\",\"percentage\":1}"},{"use_type":4,"out_trade_no":"GL20180320160403006646","balance":9877880,"total_amount":"0.00","trade_status":0,"goods":[{"goods_name":"APP测试期货商品数据","goods_number":"1"}],"shop_name":"格利食品网","addr":"天津市天津市宁河县踏踏","add_time":1521533091,"order_info":"{\"order_id\":5388,\"num_str\":\"1\",\"order_sn\":\"GL20180320160403006646\",\"sum_amount\":\"0.00\",\"order_type\":2,\"good_str\":\"5499\",\"percentage\":2}"},{"use_type":4,"out_trade_no":"GL20180320160403006646","balance":9884880,"total_amount":"0.00","trade_status":0,"goods":[{"goods_name":"APP测试期货商品数据","goods_number":"1"}],"shop_name":"格利食品网","addr":"天津市天津市宁河县踏踏","add_time":1521533046,"order_info":"{\"order_id\":5388,\"num_str\":\"1\",\"order_sn\":\"GL20180320160403006646\",\"sum_amount\":\"0.00\",\"order_type\":2,\"good_str\":\"5499\",\"percentage\":1}"},{"use_type":4,"out_trade_no":"GL20180320135818026716","balance":9887880,"total_amount":"0.00","trade_status":0,"goods":[{"goods_name":"团购测试商品2","goods_number":"1"}],"shop_name":"格利食品网","addr":"天津市天津市宁河县踏踏","add_time":1521532988,"order_info":"{\"order_id\":5381,\"num_str\":\"1\",\"order_sn\":\"GL20180320135818026716\",\"sum_amount\":\"0.00\",\"order_type\":2,\"good_str\":\"4965\",\"percentage\":1}"},{"use_type":4,"out_trade_no":"GL20180320135818026716","balance":9887880,"total_amount":"0.00","trade_status":0,"goods":[{"goods_name":"团购测试商品2","goods_number":"1"}],"shop_name":"格利食品网","addr":"天津市天津市宁河县踏踏","add_time":1521530231,"order_info":"{\"order_id\":5381,\"num_str\":\"1\",\"order_sn\":\"GL20180320135818026716\",\"sum_amount\":\"0.00\",\"order_type\":2,\"good_str\":\"4965\"}"}]
         */
        private List<ConsumptionEntity> consumption;
        private List dataList;

        public void setConsumption(List<ConsumptionEntity> consumption) {
            this.consumption = consumption;
        }

        public List<ConsumptionEntity> getConsumption() {
            return consumption;
        }

        public List getDataList() {
            return dataList;
        }

        public void setDataList(List dataList) {
            this.dataList = dataList;
        }

        public static class ConsumptionEntity implements Parcelable{
            /**
             * use_type : 4
             * out_trade_no : GL20180326173647075573
             * balance : 9930700
             * total_amount : 9900.00
             * trade_status : 0
             * goods : [{"goods_name":"佳尚新蔬 青豆 500g*20/件 ","goods_number":"100"},{"goods_name":"佳尚新蔬 速冻蔬菜 速冻青梗菜500g*14包/箱","goods_number":"5"}]
             * shop_name : 佳尚新蔬食品
             * addr : 湖北省武汉市武昌区踏踏
             * add_time : 1522057011
             * order_info : {"order_id":5454,"user_id":7217,"num_str":"100,5","order_sn":"GL20180326173647075573","sum_amount":"9900.00","order_type":1,"good_str":"1755,116"}
             */
            private int use_type;
            private String out_trade_no;
            private int balance;
            private String total_amount;
            private int trade_status;
            private List<GoodsEntity> goods;
            private String shop_name;
            private String addr;
            private long add_time;
            private String order_info;
            private String shop_thumb;

            protected ConsumptionEntity(Parcel in) {
                use_type = in.readInt();
                out_trade_no = in.readString();
                balance = in.readInt();
                total_amount = in.readString();
                trade_status = in.readInt();
                goods = in.createTypedArrayList(GoodsEntity.CREATOR);
                shop_name = in.readString();
                addr = in.readString();
                add_time = in.readLong();
                order_info = in.readString();
                shop_thumb = in.readString();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(use_type);
                dest.writeString(out_trade_no);
                dest.writeInt(balance);
                dest.writeString(total_amount);
                dest.writeInt(trade_status);
                dest.writeTypedList(goods);
                dest.writeString(shop_name);
                dest.writeString(addr);
                dest.writeLong(add_time);
                dest.writeString(order_info);
                dest.writeString(shop_thumb);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<ConsumptionEntity> CREATOR = new Creator<ConsumptionEntity>() {
                @Override
                public ConsumptionEntity createFromParcel(Parcel in) {
                    return new ConsumptionEntity(in);
                }

                @Override
                public ConsumptionEntity[] newArray(int size) {
                    return new ConsumptionEntity[size];
                }
            };

            public void setUse_type(int use_type) {
                this.use_type = use_type;
            }

            public void setOut_trade_no(String out_trade_no) {
                this.out_trade_no = out_trade_no;
            }

            public void setBalance(int balance) {
                this.balance = balance;
            }

            public void setTotal_amount(String total_amount) {
                this.total_amount = total_amount;
            }

            public void setTrade_status(int trade_status) {
                this.trade_status = trade_status;
            }

            public void setGoods(List<GoodsEntity> goods) {
                this.goods = goods;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }

            public void setAddr(String addr) {
                this.addr = addr;
            }

            public void setAdd_time(long add_time) {
                this.add_time = add_time;
            }

            public void setOrder_info(String order_info) {
                this.order_info = order_info;
            }

            public int getUse_type() {
                return use_type;
            }

            public String getOut_trade_no() {
                return out_trade_no;
            }

            public int getBalance() {
                return balance;
            }

            public String getTotal_amount() {
                return total_amount;
            }

            public int getTrade_status() {
                return trade_status;
            }

            public List<GoodsEntity> getGoods() {
                return goods;
            }

            public String getShop_name() {
                return shop_name;
            }

            public String getAddr() {
                return addr;
            }

            public long getAdd_time() {
                return add_time;
            }

            public String getOrder_info() {
                return order_info;
            }

            public String getShop_thumb() {
                return shop_thumb;
            }

            public void setShop_thumb(String shop_thumb) {
                this.shop_thumb = shop_thumb;
            }

            public static class GoodsEntity implements Parcelable{
                /**
                 * goods_name : 佳尚新蔬 青豆 500g*20/件
                 * goods_number : 100
                 */
                private String goods_name;
                private String goods_number;

                protected GoodsEntity(Parcel in) {
                    goods_name = in.readString();
                    goods_number = in.readString();
                }

                public static final Creator<GoodsEntity> CREATOR = new Creator<GoodsEntity>() {
                    @Override
                    public GoodsEntity createFromParcel(Parcel in) {
                        return new GoodsEntity(in);
                    }

                    @Override
                    public GoodsEntity[] newArray(int size) {
                        return new GoodsEntity[size];
                    }
                };

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public void setGoods_number(String goods_number) {
                    this.goods_number = goods_number;
                }

                public String getGoods_name() {
                    return goods_name;
                }

                public String getGoods_number() {
                    return goods_number;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(goods_name);
                    dest.writeString(goods_number);
                }
            }
        }
    }
}
