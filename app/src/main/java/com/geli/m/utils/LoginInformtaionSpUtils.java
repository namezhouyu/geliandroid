package com.geli.m.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mrCTang on 2017/6/13.
 */

public class LoginInformtaionSpUtils {
    static String cacheName = "login_config.xml";
    public static final String login_password = "password";//登录密码
    public static final String login_phone = "phone";//手机号码
    public static final String login_user_id = "user_id";//用户id
    public static final String login_pay_pwd = "pay_pwd";//支付密码
    public static final String login_login = "login";//1-->登录
    public static final String login_token = "token";//融云token
    public static final String LOGIN_USERINFO ="login_userinfo";

    public static void setLoginInfString(Context context, String key, String value) {
        //获取编辑器
        SharedPreferences sharedPreferences = context.getSharedPreferences(cacheName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key, value);
        //提交
        edit.commit();
    }

    public static String getLoginInfString(Context context, String key) {
        //获取编辑器
        //        if (key.equals("user_id")) {
        //            return "a63b547ffbd9505ecc864bfca9fbd988";
        //        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(cacheName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }


    public static void setLoginInfBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(cacheName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(key, value);
        //提交
        edit.commit();
    }

    public static boolean getLoginInfBoolean(Context context, String key) {
        //获取编辑器
        SharedPreferences sharedPreferences = context.getSharedPreferences(cacheName, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    public static void clearSP(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(cacheName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.clear();
        edit.commit();
    }
}
