package com.geli.m.bean;

/**
 * Created by Steam_l on 2018/3/29.
 */

public class NotifyBean {
    /**
     * cat_id : 4
     * target_id : 42
     * title : 打法是对
     * content : 士大夫撒旦飞洒地方撒
     */
    private String cat_id;
    private String target_id;
    private String title;
    private String content;

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public void setTarget_id(String target_id) {
        this.target_id = target_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCat_id() {
        return cat_id;
    }

    public String getTarget_id() {
        return target_id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
