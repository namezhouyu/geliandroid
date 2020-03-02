package com.geli.m.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Labor on 2017/5/24.
 */

public class AddressBean implements Serializable {

    private int code;
    private String message;
    private List<Province> data;

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

    public List<Province> getData() {
        return data;
    }

    public void setData(List<Province> data) {
        this.data = data;
    }


    public static class Province {

        private int area_id;
        private String area_name;
        private String code;
        private List<City> city_list;

        public int getArea_id() {
            return area_id;
        }

        public void setArea_id(int area_id) {
            this.area_id = area_id;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<City> getCity_list() {
            return city_list;
        }

        public void setCity_list(List<City> city_list) {
            this.city_list = city_list;
        }

        public static class City {


            private int area_id;
            private String area_name;
            private String code;
            private List<County> district_list;

            public int getArea_id() {
                return area_id;
            }

            public void setArea_id(int area_id) {
                this.area_id = area_id;
            }

            public String getArea_name() {
                return area_name;
            }

            public void setArea_name(String area_name) {
                this.area_name = area_name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public List<County> getDistrict_list() {
                return district_list;
            }

            public void setDistrict_list(List<County> district_list) {
                this.district_list = district_list;
            }

            public static class County {
                /**
                 * area_id : 4
                 * area_name : 东城区
                 * code : 110101
                 */

                private int area_id;
                private String area_name;
                private String code;

                public int getArea_id() {
                    return area_id;
                }

                public void setArea_id(int area_id) {
                    this.area_id = area_id;
                }

                public String getArea_name() {
                    return area_name;
                }

                public void setArea_name(String area_name) {
                    this.area_name = area_name;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }
            }
        }
    }
}
