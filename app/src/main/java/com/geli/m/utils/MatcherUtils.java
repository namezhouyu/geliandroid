package com.geli.m.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/12/15.
 */

public class MatcherUtils {

    /**
     * 判断字符串是否电话
     *
     * @param phone
     * @return
     */
    public static boolean isPhoneRegularly(String phone) {
        String regExp = "^[1][3,4,5,7,8][0-9]{9}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * 判断字符串是否电话
     *
     * @param phone
     * @return
     */
    public static boolean isPhoneRegularlyNew(String phone) {
        if(StringUtils.isNotEmpty(phone)){
            if(phone.length() == 11){
                if(phone.startsWith("1")){
                    return true;
                }else {
                    return false;
                }
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    /**
     * 判断字符串是否电话
     *
     * @param phone
     * @return
     */
    public static boolean isPhoneRegularlyTemp(String phone) {
        String regExp = "^[1][0-9]{10}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phone);
        return m.matches();
    }



    /**
     * 判断密码是否符合要求
     *
     * @param password
     * @return
     */
    public static boolean isPasswordRegularly(String password) {
        String regExp = "((?=.*\\d)(?=.*[a-z]|[0-9]).{6,12})";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(password);
        return m.matches();
    }
}
