package com.geli.m.bean;

/**
 * author:  shen
 * date:    2018/12/5
 *
 * 格利支付 -- 获取系统时间
 *
 * 获取服务器时间接口(POST请求)：pay.geli.com/cinpay/getFwqTime.htm
 * 参数：
 * 无
 *
 * 返回数据：
 * code 返回代码code=0时成功
 * rep_msg 返回信息
 * time 时间戳 （code=0时返回）
 */
public class FwqTimeBean {


    /**
     * code : 0
     * rep_msg : 操作成功
     * time : 1543997065                //  秒为单位
     * rep_code :
     */

    private int code;
    private String rep_msg;
    private int time;               //  秒为单位
    private String rep_code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRep_msg() {
        return rep_msg;
    }

    public void setRep_msg(String rep_msg) {
        this.rep_msg = rep_msg;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getRep_code() {
        return rep_code;
    }

    public void setRep_code(String rep_code) {
        this.rep_code = rep_code;
    }
}
