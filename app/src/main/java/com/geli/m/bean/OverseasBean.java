package com.geli.m.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/23.
 */

public class OverseasBean {
    private int code;
    private String message;

    private List<OverseasCountrOutrBean.OverseasCountrBean> mOverseasCountrBean;
    private List<OverseasGoodsOuterBean.OverseasGoodsBean> mOverseasGoodsBean;
    private OverseasSortBean mOverseasSortBean;


    public OverseasSortBean getOverseasSortBean() {
        return mOverseasSortBean;
    }

    public void setOverseasSortBean(OverseasSortBean overseasSortBean) {
        mOverseasSortBean = overseasSortBean;
    }

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

    public List<OverseasCountrOutrBean.OverseasCountrBean> getOverseasCountrBean() {
        return mOverseasCountrBean;
    }

    public void setOverseasCountrBean(List<OverseasCountrOutrBean.OverseasCountrBean> overseasCountrBean) {
        mOverseasCountrBean = overseasCountrBean;
    }

    public List<OverseasGoodsOuterBean.OverseasGoodsBean> getOverseasGoodsBean() {
        return mOverseasGoodsBean;
    }

    public void setOverseasGoodsBean(List<OverseasGoodsOuterBean.OverseasGoodsBean> overseasGoodsBean) {
        mOverseasGoodsBean = overseasGoodsBean;
    }
}
