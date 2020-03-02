package com.geli.m.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * author:  shen
 * date:    2019/7/30
 *
 * "性批发"的"省市"整理后的数据
 */
public class RestaurantAddrArrangeBean {

    List<province> provinceList = new ArrayList<>();

    public List<province> getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(List<province> provinceList) {
        this.provinceList = provinceList;
    }

    public static class province{

        // 省
        private RestaurantAddrBean.DataBean pro;
        private List<RestaurantAddrBean.DataBean>  cityList;


        public province(RestaurantAddrBean.DataBean pro, List<RestaurantAddrBean.DataBean> cityList) {
            this.pro = pro;
            this.cityList = cityList;
        }

        public RestaurantAddrBean.DataBean getPro() {
            return pro;
        }

        public void setPro(RestaurantAddrBean.DataBean pro) {
            this.pro = pro;
        }



        public List<RestaurantAddrBean.DataBean> getCityList() {
            return cityList;
        }

        public void setCityList(List<RestaurantAddrBean.DataBean> cityList) {
            this.cityList = cityList;
        }

        @Override
        public String toString() {
            return "province{" +
                    "pro=" + pro.toString() +
                    ", cityList=" + cityList.toString() +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "RestaurantAddrArrangeBean{" +
                "provinceList=" + provinceList +
                '}';
    }
}
