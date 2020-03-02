package com.geli.m.bean;

/**
 * author:  shen
 * date:    2019/4/1
 */
public class NvaBean {

//    {
//        "id": 17,
//            "nav_title": "测试URL",
//            "nav_img": "upload/nav_img/nav_img155410405170653.jpg",
//            "nav_key": "testurl",
//            "is_show": 1,
//            "target_type": 2,
//            "target": "www.baidu.com"
//    }
    /**
     * id : 11
     * nav_title : 批零中心
     * nav_img : upload/nav_img/nav_img155410391615553.png
     * nav_key : sell
     * is_show : 1
     * target_type : 1
     * target : sell
     */

    private int id;
    private String nav_title;
    private String nav_img;
    private String nav_key;
    private int is_show;
    private int target_type;
    private String target;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNav_title() {
        return nav_title;
    }

    public void setNav_title(String nav_title) {
        this.nav_title = nav_title;
    }

    public String getNav_img() {
        return nav_img;
    }

    public void setNav_img(String nav_img) {
        this.nav_img = nav_img;
    }

    public String getNav_key() {
        return nav_key;
    }

    public void setNav_key(String nav_key) {
        this.nav_key = nav_key;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }

    public int getTarget_type() {
        return target_type;
    }

    public void setTarget_type(int target_type) {
        this.target_type = target_type;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
