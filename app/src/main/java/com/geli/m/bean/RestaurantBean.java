package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * author:  shen
 * date:    2019/1/15
 */
public class RestaurantBean {


    /**
     * code : 100
     * message : ok
     * data : {"local_food_res":[{"lf_id":9,"title":"广州测试","img":"/upload/local_food/852c82a9d505e88bc47def1547172727.jpg","content":"/upload/local_food/663c32c81d60640c7fac8e1547172729.jpg","note":"dasdassada","distance":3051},{"lf_id":10,"title":"湖南常德市","img":"/upload/local_food/6287e1141b34322ac106f31547458732.jpg","content":"/upload/local_food/783ed439be18f168cdfa381547458749.jpg","note":"1111","distance":571972},{"lf_id":11,"title":"北京测试","img":"/upload/local_food/50415829ce94d7c174c0a71547458783.jpg","content":"/upload/local_food/718e4a1909179e4aa15e831547458786.jpg","note":"111111","distance":1887573}],"count":3}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * local_food_res : [{"lf_id":9,"title":"广州测试","img":"/upload/local_food/852c82a9d505e88bc47def1547172727.jpg","content":"/upload/local_food/663c32c81d60640c7fac8e1547172729.jpg","note":"dasdassada","distance":3051},{"lf_id":10,"title":"湖南常德市","img":"/upload/local_food/6287e1141b34322ac106f31547458732.jpg","content":"/upload/local_food/783ed439be18f168cdfa381547458749.jpg","note":"1111","distance":571972},{"lf_id":11,"title":"北京测试","img":"/upload/local_food/50415829ce94d7c174c0a71547458783.jpg","content":"/upload/local_food/718e4a1909179e4aa15e831547458786.jpg","note":"111111","distance":1887573}]
         * count : 3
         */

        private int count;
        private List<LocalFoodResBean> local_food_res;
        //private List<String> highlight;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<LocalFoodResBean> getLocal_food_res() {
            return local_food_res;
        }

        public void setLocal_food_res(List<LocalFoodResBean> local_food_res) {
            this.local_food_res = local_food_res;
        }

        public static class LocalFoodResBean implements Parcelable {
            /**
             * lf_id : 9
             * title : 广州测试
             * img : /upload/local_food/852c82a9d505e88bc47def1547172727.jpg
             * content : /upload/local_food/663c32c81d60640c7fac8e1547172729.jpg
             * note : dasdassada
             * distance : 3051
             */

            private int lf_id;
            private String title;
            private String img;
            private String content;
            private String note;
            private int distance;
            private String scale;          // 规模
            private String market_main;    // 主营
            private int city_id;           // 城市id
            private String feature;        // 特征
            private String address;        // 地址
            private String title_highlight;        // title中的高亮字符串
            private String address_detail;        // 地址 -- 190805 替代 address

            public int getLf_id() {
                return lf_id;
            }

            public void setLf_id(int lf_id) {
                this.lf_id = lf_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getNote() {
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }

            public int getDistance() {
                return distance;
            }

            public void setDistance(int distance) {
                this.distance = distance;
            }

            public String getScale() {
                return scale;
            }

            public void setScale(String scale) {
                this.scale = scale;
            }

            public String getMarket_main() {
                return market_main;
            }

            public void setMarket_main(String market_main) {
                this.market_main = market_main;
            }

            public int getCity_id() {
                return city_id;
            }

            public void setCity_id(int city_id) {
                this.city_id = city_id;
            }

            public String getFeature() {
                return feature;
            }

            public void setFeature(String feature) {
                this.feature = feature;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getTitle_highlight() {
                return title_highlight;
            }

            public void setTitle_highlight(String title_highlight) {
                this.title_highlight = title_highlight;
            }

            public String getAddress_detail() {
                return address_detail;
            }

            public void setAddress_detail(String address_detail) {
                this.address_detail = address_detail;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.lf_id);
                dest.writeString(this.title);
                dest.writeString(this.img);
                dest.writeString(this.content);
                dest.writeString(this.note);
                dest.writeInt(this.distance);

                dest.writeString(this.scale);
                dest.writeString(this.market_main);
                dest.writeInt(this.city_id);
                dest.writeString(this.feature);
                dest.writeString(this.address);
                dest.writeString(this.title_highlight);
                dest.writeString(this.address_detail);

            }

            public LocalFoodResBean() {
            }

            protected LocalFoodResBean(Parcel in) {
                this.lf_id = in.readInt();
                this.title = in.readString();
                this.img = in.readString();
                this.content = in.readString();
                this.note = in.readString();
                this.distance = in.readInt();

                this.scale = in.readString();
                this.market_main = in.readString();
                this.city_id = in.readInt();
                this.feature = in.readString();
                this.address = in.readString();
                this.title_highlight = in.readString();
                this.address_detail = in.readString();
            }

            public static final Parcelable.Creator<LocalFoodResBean> CREATOR = new Parcelable.Creator<LocalFoodResBean>() {
                @Override
                public LocalFoodResBean createFromParcel(Parcel source) {
                    return new LocalFoodResBean(source);
                }

                @Override
                public LocalFoodResBean[] newArray(int size) {
                    return new LocalFoodResBean[size];
                }
            };
        }
    }
}
