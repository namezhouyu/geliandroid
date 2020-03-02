package com.geli.m.bean;

/**
 * Created by pks on 2017/6/15.
 * 搜索记录
 */

public class SearchEntity {
    private String inputContent;//输入内容
    private int type;//曾经搜索的是店铺还是商品
    private long createTime;//什么时候搜索的
    private String id;//数据库id

    public SearchEntity() {
    }

    public String getInputContent() {
        return inputContent;
    }

    public void setInputContent(String inputContent) {
        this.inputContent = inputContent;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
