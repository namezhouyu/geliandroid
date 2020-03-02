package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Steam_l on 2018/1/16.
 *
 * 规格
 *
 */

public class SpecifiBean implements Parcelable {
    /**
     * code : 100
     * data : {"goods_sku":[{"sku_attr":"{1:27700}","price":"10","goods_id":1975,"sku_id":27683,"inventory":9999,"sales":237}],"spec_attr":[{"res":[{"attr_name":"默认","attr_id":27700}],"spec_id":1,"spec_name":"规格"}]}
     * message : ok
     */
    private int code;
    private DataEntity data;
    private String message;

    public SpecifiBean() {

    }

    protected SpecifiBean(Parcel in) {
        code = in.readInt();
        message = in.readString();
    }

    public static final Creator<SpecifiBean> CREATOR = new Creator<SpecifiBean>() {
        @Override
        public SpecifiBean createFromParcel(Parcel in) {
            return new SpecifiBean(in);
        }

        @Override
        public SpecifiBean[] newArray(int size) {
            return new SpecifiBean[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(message);
    }

    public static class DataEntity implements Parcelable {
        /**
         * goods_sku : [{"sku_attr":"{1:27700}","price":"10","goods_id":1975,"sku_id":27683,"inventory":9999,"sales":237}]
         * spec_attr : [{"res":[{"attr_name":"默认","attr_id":27700}],"spec_id":1,"spec_name":"规格"}]
         */
        private List<GoodsSkuEntity> goods_sku;
        private List<SpecAttrEntity> spec_attr;

        public DataEntity() {
        }

        protected DataEntity(Parcel in) {
            goods_sku = in.createTypedArrayList(GoodsSkuEntity.CREATOR);
            spec_attr = in.createTypedArrayList(SpecAttrEntity.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(goods_sku);
            dest.writeTypedList(spec_attr);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<DataEntity> CREATOR = new Creator<DataEntity>() {
            @Override
            public DataEntity createFromParcel(Parcel in) {
                return new DataEntity(in);
            }

            @Override
            public DataEntity[] newArray(int size) {
                return new DataEntity[size];
            }
        };

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


        public static class GoodsSkuEntity implements Parcelable {
            /**
             * sku_attr : {1:27700}
             * price : 10
             * goods_id : 1975
             * sku_id : 27683
             * inventory : 9999
             * sales : 237
             */
            private String sku_attr;
            private String price;
            private int goods_id;
            private int sku_id;
            private int inventory;
            private int sales;
            private String tiered_content = "";
            private List<TieredPri> mTieredPri;

            private List<String> resIdList;      // 我自己添加的
            List<SkuAttrBean> mSkuAttrBeans;      // 我自己添加的

            public List<String> getResIdList() {
                return resIdList;
            }

            public void setResIdList(List<String> resIdList) {
                this.resIdList = resIdList;
            }

            protected GoodsSkuEntity(Parcel in) {
                sku_attr = in.readString();
                price = in.readString();
                goods_id = in.readInt();
                sku_id = in.readInt();
                inventory = in.readInt();
                sales = in.readInt();
                tiered_content = in.readString();
                mTieredPri = in.createTypedArrayList(TieredPri.CREATOR);
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(sku_attr);
                dest.writeString(price);
                dest.writeInt(goods_id);
                dest.writeInt(sku_id);
                dest.writeInt(inventory);
                dest.writeInt(sales);
                dest.writeString(tiered_content);
                dest.writeTypedList(mTieredPri);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<GoodsSkuEntity> CREATOR = new Creator<GoodsSkuEntity>() {
                @Override
                public GoodsSkuEntity createFromParcel(Parcel in) {
                    return new GoodsSkuEntity(in);
                }

                @Override
                public GoodsSkuEntity[] newArray(int size) {
                    return new GoodsSkuEntity[size];
                }
            };

            public List<TieredPri> getTieredPri() {
                return mTieredPri;
            }

            public void setTieredPri(List<TieredPri> tieredPri) {
                mTieredPri = tieredPri;
            }

            public String getTiered_content() {
                return tiered_content;
            }

            public void setTiered_content(String tiered_content) {
                this.tiered_content = tiered_content;
            }

            public static class TieredPri implements Parcelable, Comparable<TieredPri> {
                private double key;
                private double value;

                public TieredPri() {

                }

                protected TieredPri(Parcel in) {
                    key = in.readDouble();
                    value = in.readDouble();
                }

                public static final Creator<TieredPri> CREATOR = new Creator<TieredPri>() {
                    @Override
                    public TieredPri createFromParcel(Parcel in) {
                        return new TieredPri(in);
                    }

                    @Override
                    public TieredPri[] newArray(int size) {
                        return new TieredPri[size];
                    }
                };

                public double getKey() {
                    return key;
                }

                public void setKey(double key) {
                    this.key = key;
                }

                public double getValue() {
                    return value;
                }

                public void setValue(double value) {
                    this.value = value;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeDouble(key);
                    dest.writeDouble(value);
                }

                @Override
                public int compareTo(@NonNull TieredPri o) {
                    return (int) (o.getKey() - this.getKey());
                }
            }

            public GoodsSkuEntity() {

            }

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

            public List<SkuAttrBean> getSkuAttrBeans() {
                return mSkuAttrBeans;
            }

            public void setSkuAttrBeans(List<SkuAttrBean> skuAttrBeans) {
                mSkuAttrBeans = skuAttrBeans;
            }

            @Override
            public String toString() {
                return "GoodsSkuEntity{" +
                        "sku_attr='" + sku_attr + '\'' +
                        ", price='" + price + '\'' +
                        ", goods_id=" + goods_id +
                        ", sku_id=" + sku_id +
                        ", inventory=" + inventory +
                        ", sales=" + sales +
                        ", tiered_content='" + tiered_content + '\'' +
                        ", mTieredPri=" + mTieredPri +
                        ", resIdList=" + resIdList +
                        '}';
            }
        }

        public static class SpecAttrEntity implements Parcelable {
            /**
             * res : [{"attr_name":"默认","attr_id":27700}]
             * spec_id : 1
             * spec_name : 规格
             */
            private List<ResEntity> res;
            private int spec_id;                // 某个分类的属性的 -- id/序号
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


            public static class ResEntity implements Parcelable {
                /**
                 * attr_name : 默认
                 * attr_id : 27700
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


                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.attr_name);
                    dest.writeInt(this.attr_id);
                }

                public ResEntity() {
                }

                protected ResEntity(Parcel in) {
                    this.attr_name = in.readString();
                    this.attr_id = in.readInt();
                }

                public static final Creator<ResEntity> CREATOR = new Creator<ResEntity>() {
                    @Override
                    public ResEntity createFromParcel(Parcel source) {
                        return new ResEntity(source);
                    }

                    @Override
                    public ResEntity[] newArray(int size) {
                        return new ResEntity[size];
                    }
                };

                @Override
                public String toString() {
                    return "ResEntity{" +
                            "attr_name='" + attr_name + '\'' +
                            ", attr_id=" + attr_id +
                            '}';
                }
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeTypedList(this.res);
                dest.writeInt(this.spec_id);
                dest.writeString(this.spec_name);
            }

            public SpecAttrEntity() {
            }

            protected SpecAttrEntity(Parcel in) {
                this.res = in.createTypedArrayList(ResEntity.CREATOR);
                this.spec_id = in.readInt();
                this.spec_name = in.readString();
            }

            public static final Creator<SpecAttrEntity> CREATOR = new Creator<SpecAttrEntity>() {
                @Override
                public SpecAttrEntity createFromParcel(Parcel source) {
                    return new SpecAttrEntity(source);
                }

                @Override
                public SpecAttrEntity[] newArray(int size) {
                    return new SpecAttrEntity[size];
                }
            };

            @Override
            public String toString() {
                return "SpecAttrEntity{" +
                        "res=" + res.toString() +
                        ", spec_id=" + spec_id +
                        ", spec_name='" + spec_name + '\'' +
                        '}';
            }


            /**
             * 排序 -- 根据"传递进来的经纬度"和"列表中的经纬度"计算出距离，再排序
             */
            public static class SpecifiComparator implements Comparator {

                public int compare(Object o1, Object o2){
                    SpecAttrEntity s1 = (SpecAttrEntity)o1;
                    SpecAttrEntity s2 = (SpecAttrEntity)o2;

                    int result = s1.getSpec_id() > s2.getSpec_id() ? 1 :
                            (s1.getSpec_id() == s2.getSpec_id() ? 0 : -1);
                    return result;
                }
            }
        }
    }
}

/**

    ╔═══════════════════════════════════════════════════════════════════════════════════════
    ║
    ║ {
    ║     "code": 100,
    ║     "message": "ok",
    ║     "data": {
    ║         "spec_attr": [
    ║             {
    ║                 "spec_id": 1,
    ║                 "spec_name": "规格",
    ║                 "res": [
    ║                     {
    ║                         "attr_id": 33171,
    ║                         "attr_name": "11cm-12cm"
    ║                     },
    ║                     {
    ║                         "attr_id": 33173,
    ║                         "attr_name": "12cm-13cm"
    ║                     }
    ║                 ]
    ║             },
    ║             {
    ║                 "spec_id": 5,
    ║                 "spec_name": "级别",
    ║                 "res": [
    ║                     {
    ║                         "attr_id": 33172,
    ║                         "attr_name": "A级"
    ║                     },
    ║                     {
    ║                         "attr_id": 33174,
    ║                         "attr_name": "B级"
    ║                     }
    ║                 ]
    ║             }
    ║         ],
    ║         "goods_sku": [
    ║             {
    ║                 "sku_id": 33136,
    ║                 "goods_id": 5481,
    ║                 "sku_attr": "{1:33171,5:33172}",
    ║                 "price": "20000.00",
    ║                 "inventory": 200,
    ║                 "sales": 0,
    ║                 "pack_attr": "{\"l\":\"0\",\"w\":\"0\",\"h\":\"0\",\"v\":0,\"wg\":\"1000\"}",
    ║                 "refrigerate": "-18℃",
    ║                 "tiered_pri": "",
    ║                 "virtual_sales": 425,
    ║                 "sh_price": null
    ║             },
    ║             {
    ║                 "sku_id": 33137,
    ║                 "goods_id": 5481,
    ║                 "sku_attr": "{1:33173,5:33174}",
    ║                 "price": "19500.00",
    ║                 "inventory": 200,
    ║                 "sales": 0,
    ║                 "pack_attr": "{\"l\":\"0\",\"w\":\"0\",\"h\":\"0\",\"v\":0,\"wg\":\"1000\"}",
    ║                 "refrigerate": "-18℃",
    ║                 "tiered_pri": "",
    ║                 "virtual_sales": 472,
    ║                 "sh_price": null
    ║             }
    ║         ]
    ║     }
    ║ }
    ╚═══════════════════════════════════════════════════════════════════════════════════════
 */