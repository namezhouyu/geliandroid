package com.geli.m.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by gbh on 16/12/3.
 */

public class TimeUtils {

    public static String format1 = "yyyy年MM月dd日 HH时mm分ss秒";
    public static String format2 = "yyyy-MM-dd HH:mm:ss";
    public static String format3 = "yyyy-MM-dd HH:mm:ss.sss";
    public static String format4 = "yyyy-MM-dd HH:mm";
    public static String format5 = "yyyyMMdd";
    public static String format6 = "yyyy-MM-dd";
    public static String format7 = "yyyy/MM/dd";
    public static String format8 = "yyyyMM";
    public static String format9 = "yyyy-MM";
    public static String format10 = "yyyy年MM月dd日";
    public static String format11 = "yyyy.MM.dd";
    public static String format12 = "yyyy.MM.dd  HH:mm:ss";
    public static String format13 = "yyyy.MM.dd  HH:mm";

    /**
     * 获取当前时间 （默认格式：yyyy-MM-dd HH:mm:ss）
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format2);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取当前时间
     * @param dateFormat    需要的格式
     * @return
     */
    public static String getCurrentTime(String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 将 Date 转化为字符串
     * @param date
     * @param dateFormat
     * @return
     */
    public static String dateToString(Date date, String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(date);
    }


    public static boolean compareTime(String time1, String time2, String dateFormat){
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);//创建日期转换对象HH:mm:ss为时分秒，年月日为yyyy-MM-dd
        try {
            Date dt1 = df.parse(time1);//将字符串转换为date类型
            Date dt2 = df.parse(time2);
            if(dt1.getTime()>= dt2.getTime()){  //比较时间大小,dt1小于dt2
                return true;
            }
            else{
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 取出列表中的最大值 -- 最大的时间
     * @param timeList
     * @return
     */
    public static String getMaxTime(List<String> timeList){

        String strMax = "";
        if(timeList == null)
            return strMax;
        if(timeList.size() == 0)
            return strMax;

        if(timeList != null && timeList.size() == 1){
            return timeList.get(0);
        }


        strMax = timeList.get(0);
        for(int i=1; i<timeList.size(); i ++ ){
            if(compareTime(timeList.get(i), strMax, format1)){
                strMax = timeList.get(i);
            }
        }
        return strMax;
    }

    /**
     * 两个 时间相差多少天  time1-time2
     * @param time1             减数
     * @param time2             被减数
     * @param dateFormat        //创建日期转换对象HH:mm:ss为时分秒，年月日为yyyy-MM-dd
     * @return
     */
    public static long timeDifference(String time1, String time2, String dateFormat){

        long days = 1;
        SimpleDateFormat df = new SimpleDateFormat(dateFormat); //创建日期转换对象HH:mm:ss为时分秒，年月日为yyyy-MM-dd
        try
        {
            Date d1 = df.parse(time1);
            Date d2 = df.parse(time2);

            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别

            days = diff / (1000 * 60 * 60 * 24);

            //LogUtils.i("time1:" + time1 + "------------ time2:" + time2);

            // long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
            // long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
            // System.out.println(""+days+"天"+hours+"小时"+minutes+"分");
        }catch (Exception e) {
            LogUtils.i("e.getMessage():" + e.getMessage());
        }

        return days;
    }

    public static int getMonthSpace(String date1, String date2)
            throws ParseException {

        int result = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(sdf.parse(date1));
        c2.setTime(sdf.parse(date2));

        result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);

//        Date d = new Date();
//        Calendar c = Calendar.getInstance();
//        c.setTime(d);
//        System.out.println(c.get(Calendar.MONTH));
//        一月对应0
//        十二月对应11

        return result == 0 ? 1 : Math.abs(result);

    }


    /**
     *  获取两个日期相差的月数
     * @param time1    较小的日期
     * @param time2    较大的日期
     * @return  如果d1>d2返回 月数差 否则返回0
     */
    public static int getMonthDiff(String time1, String time2, String dateFormat) {

        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        Date d1 = null;
        Date d2 = null;

        try {
            d1 = df.parse(time1);
            d2 = df.parse(time2);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(d1);
        c2.setTime(d2);

        if(c2.getTimeInMillis() < c1.getTimeInMillis())
            return 0;

        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);

        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);

        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);

        // 获取年的差值 假设 d1 = 2015-8-16  d2 = 2011-9-30
        int yearInterval = year2 - year1;

        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if(month2 < month1 || month2 == month1 && day2 < day1)
            yearInterval --;

        // 获取月数差值
        int monthInterval =  (month2 + 12) - month1  ;
        if(day2 < day1)
            monthInterval --;

        monthInterval %= 12;

        return yearInterval * 12 + monthInterval;
    }


    /**
     * 检查格式是否 符合 (正则表达式是最正确的做法，可惜不会 -- 是太麻烦了)
     *
     * @param time
     * @param dateFormat
     * @return
     */
    public static boolean checkingFormat(String time, String dateFormat){
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        boolean dateFlag = true;
        try {
            Date date = df.parse(time);
        } catch (ParseException e) {
            dateFlag = false;
        }finally{
            // 成功：true ;失败:false
            // System.out.println("日期是否满足要求"+dateflag);
        }
        return dateFlag;
    }



    public static String transForDate(Integer ms) {
        return transForDate(ms, format4);
    }

    public static String transForDate(Integer ms, String format) {
        String str = "";
        if (ms != null) {
            long msl = (long) ms * 1000;
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            if (ms != null) {
                try {
                    str = sdf.format(msl);
                } catch (Exception e) {
                    e.printStackTrace();
                    return "";
                }
            }
        }
        return str;
    }
}
