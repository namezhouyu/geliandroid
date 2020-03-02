package com.geli.m.utils;

import java.util.List;

/**
 * author:  shen
 * date:    2018/11/17
 */
public class ArrayListUtils {

    /**
     * 不为空 且 大于0
     * @return
     */
    public static boolean notNullAndGreaterThan0(List list){
        if(list != null && list.size() > 0)
            return true;
        else
            return false;
    }
}
