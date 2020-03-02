package com.geli.m.bean;

import com.luck.picture.lib.entity.LocalMedia;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/26.
 *
 * 商品评论
 */

public class GoodsCommentBean {
    private String goods_id;
    private String com_content;
    private String grade;
    private String goods_attr;
    private String anonymity;
    private String img_number=0+"";
    private String goods_name;
    private String goods_img;
    private String order_id;
    public List<LocalMedia> imgList = new ArrayList<>();

    public GoodsCommentBean(String goods_id, String com_content, String grade, String goods_attr, String anonymity,String goods_name,String goods_img,String order_id) {
        this.goods_id = goods_id;
        this.com_content = com_content;
        this.grade = grade;
        this.goods_attr = goods_attr;
        this.anonymity = anonymity;
        this.goods_name = goods_name;
        this.goods_img = goods_img;
        this.order_id = order_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getCom_content() {
        return com_content;
    }

    public void setCom_content(String com_content) {
        this.com_content = com_content;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGoods_attr() {
        return goods_attr;
    }

    public void setGoods_attr(String goods_attr) {
        this.goods_attr = goods_attr;
    }

    public String getAnonymity() {
        return anonymity;
    }

    public void setAnonymity(String anonymity) {
        this.anonymity = anonymity;
    }

    public String getImg_number() {
        return img_number;
    }

    public void setImg_number(String img_number) {
        this.img_number = img_number;
    }

    public List<LocalMedia> getImgList() {
        return imgList;
    }

    public void setImgList(List<LocalMedia> imgList) {
        this.imgList = imgList;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
