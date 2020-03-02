package com.geli.m.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pks on 2017/6/9.
 */

public class City implements Serializable {
    private String code;
    private String area_name;
    private String area_id;
    private ArrayList<District> district_list;


    public City() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public ArrayList<District> getDistrict_list() {
        return district_list;
    }

    public void setDistrict_list(ArrayList<District> district_list) {
        this.district_list = district_list;
    }

}
