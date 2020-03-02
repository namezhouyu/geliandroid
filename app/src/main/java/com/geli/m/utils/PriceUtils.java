package com.geli.m.utils;

import com.geli.m.R;

/**
 * author:  shen
 * date:    2018/11/17
 *
 * 获取价格
 */
public class PriceUtils {

    /**
     * 获取价格 -- ￥%
     * @param price
     * @return
     */
    public static String getPrice(Object price) {
        Double tempPrice = getDouble(price);
        if (tempPrice > 0) {
            String priceTwoDecimal = Utils.getFormatDoubleTwoDecimalPlaces(tempPrice, 2);
            return Utils.getString(R.string.overseas_list_money, priceTwoDecimal);
        }
        return Utils.getString(R.string.overseas_list_money, Utils.getString(R.string.inquiry));
    }



    /**
     * 获取海外订单的价格
     * @param price
     * @return
     */
    public static String getOverseasPrice(Object price) {
        Double tempPrice = getDouble(price);
        if (tempPrice > 0) {
            return getOverseasPrice(tempPrice * 0.3 + "", tempPrice * 0.7 + "");
        }
        return Utils.getString(R.string.overseas_price, "30%%", "70%%");
    }


    /**
     * 获取海外订单价格 -- 返回字符串 -- ￥%s订金  ￥%s余款
     * @param price3        定金
     * @param price7        余款
     * @return
     */
    public static String getOverseasPrice(String price3, String price7) {
        return getOverseasPrice(R.string.overseas_price, price3, price7);
    }


    /**
     * 获取海外订单价格 -- 返回字符串 -- "xx格式" 订金 余款
     * @param strId         xx格式
     * @param price3        定金
     * @param price7        余款
     * @return
     */
    public static String getOverseasPrice(int strId, String price3, String price7) {
        if (!price7.equals(Utils.getString(R.string.wait_change))) {        // 不是核算中
            price7 = Utils.getFormatDoubleTwoDecimalPlaces(Double.valueOf(price7), 2);
            if(price7.equals("0.00")){              // 0 就要设置为 核算中
                price7 = Utils.getString(R.string.wait_change);
            }
        }
        String tempPrice3 = Utils.getFormatDoubleTwoDecimalPlaces(Double.valueOf(price3), 2);
        return Utils.getString(strId, tempPrice3, price7);
    }


    /**
     * xx价格起
     * @param price
     * @return
     */
    public static String getStartPrice(Object price) {
        if(price instanceof String || price instanceof Double){
            Double tempPrice = getDouble(price);
            return Utils.getString(R.string.start_money, tempPrice);
        }
        return Utils.getString(R.string.overseas_list_money, Utils.getString(R.string.inquiry));
    }



    /**
     * 无价格的(或价格为0) -- 联系客服
     * 有价格的就忽略
     *
     * @param price
     * @return
     */
    public static boolean judgmentPrice(Object price) {
        if (price instanceof String) {
            if (Double.valueOf(price + "") > 0) {
                return true;
            }
        } else if (price instanceof Double) {
            if ((Double) price > 0) {
                return true;
            }
        }
        ToastUtils.showToast(Utils.getString(R.string.please_contact_customer_service_inquiry));
        return false;
    }


    private static Double getDouble(Object price){
        Double tempPrice = 0.0;
        if (price instanceof String) {
            tempPrice = Double.valueOf(price + "");
        } else if (price instanceof Double) {
            tempPrice = (Double) price;
        }
        return tempPrice;
    }
}
