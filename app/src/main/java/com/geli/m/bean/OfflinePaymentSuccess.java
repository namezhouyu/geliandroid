package com.geli.m.bean;

import java.io.Serializable;

/**
 * Name:
 * Author:pks
 * Date: 2017-09-11 14:17
 */

public class OfflinePaymentSuccess implements Serializable {


    private String bank_name;
    private String bank_account;
    private String order_sn;
    private String amountPayable;//应付金额
    private String company_address;
    private String shop_name;//公司名称
    private String tel;
    private String payment;//转账方式


    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_account() {
        return bank_account;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getAmountPayable() {
        return amountPayable;
    }

    public void setAmountPayable(String amountPayable) {
        this.amountPayable = amountPayable;
    }

    public String getCompany_address() {
        return company_address;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "OfflinePaymentSuccess{" +
                "bank_name='" + bank_name + '\'' +
                ", bank_account=" + bank_account +
                ", order_sn='" + order_sn + '\'' +
                ", amountPayable='" + amountPayable + '\'' +
                ", company_address='" + company_address + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", tel='" + tel + '\'' +
                ", payment='" + payment + '\'' +
                '}';
    }
}
