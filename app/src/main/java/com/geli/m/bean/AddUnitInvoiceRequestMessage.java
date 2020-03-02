package com.geli.m.bean;

import java.io.Serializable;

/**
 * 添加发票(单位)
 * Created by mrCTang on 2017/9/13.
 */

public class AddUnitInvoiceRequestMessage implements Serializable {
    private String user_id;
    private String name;//单位名称
    private int belong;//发票类型（1为单位 2为个人）
    private int type;//增值税类型（1为专用 2为普通）
    private int invoice_type;//1电子发票 2 纸质发票
    private String tel;//单位电话 例： 020-123456
    private String duty_paragraph;//单位税号 （纯数字）
    private String address;//单位地址
    private String account_name;//单位开户名称
    private String account;//单位开户账号
    private String code;//邮政编码
    private String email = "";//电子邮箱
    private String photo;//营业执照图片（最多4张图）
    private boolean isEditInvoice;
    private int invoice_id;

    @Override
    public String toString() {
        return "AddUnitInvoiceRequestMessage{" +
                "user_id='" + user_id + '\'' +
                ", name='" + name + '\'' +
                ", BELONG=" + belong +
                ", type=" + type +
                ", invoice_type=" + invoice_type +
                ", tel='" + tel + '\'' +
                ", duty_paragraph='" + duty_paragraph + '\'' +
                ", address='" + address + '\'' +
                ", account_name='" + account_name + '\'' +
                ", account='" + account + '\'' +
                ", code='" + code + '\'' +
                ", email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                ", isEditInvoice=" + isEditInvoice +
                ", invoice_id=" + invoice_id +
                '}';
    }

    public boolean isEditInvoice() {
        return isEditInvoice;
    }

    public void setEditInvoice(boolean editInvoice) {
        isEditInvoice = editInvoice;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBelong() {
        return belong;
    }

    public void setBelong(int belong) {
        this.belong = belong;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getInvoice_type() {
        return invoice_type;
    }

    public void setInvoice_type(int invoice_type) {
        this.invoice_type = invoice_type;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDuty_paragraph() {
        return duty_paragraph;
    }

    public void setDuty_paragraph(String duty_paragraph) {
        this.duty_paragraph = duty_paragraph;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
