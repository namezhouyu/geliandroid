package com.geli.m.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * author:  shen
 * date:    2019/3/12
 *
 * 调用第三方地图app
 */
public class OpenMapUtils {

    public static final String PN_GAODE_MAP = "com.autonavi.minimap";   // 高德地图包名
    public static final String PN_BAIDU_MAP = "com.baidu.BaiduMap";     // 百度地图包名
    public static final String PN_TENCENT_MAP = "com.tencent.map";      // 腾讯地图包名

    /**
     * 检测应用是否安装
     *
     * @param context
     * @param packageName   应用包名
     * @return
     */
    private static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();//获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);//获取所有已安装程序的包信息
        List<String> pName = new ArrayList<String>();//用于存储所有已安装程序的包名
        //从pinfo中将包名字逐一取出，压入pName list中
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);//判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }


    /**
     * 检测地图应用是否安装
     *
     * @param context
     * @param packagename
     * @return
     */
    public static boolean checkMapAppsIsExist(Context context, String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (Exception e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo != null;
    }




    /**
     * 打开腾讯地图（公交出行，起点位置使用地图当前位置）
     *
     * 公交：type=bus，policy有以下取值(默认：0)
     * 0：较快捷 、 1：少换乘 、 2：少步行 、 3：不坐地铁
     *
     * 驾车：type=drive，policy有以下取值(默认：0)
     * 0：较快捷 、 1：无高速 、 2：距离短
     *
     * 步行：type=walk
     *
     * @param context
     * @param sLat              起点纬度
     * @param sLng              起点经度
     * @param sName             起点名称   可不填（0,0，null）
     * @param dLat              终点纬度
     * @param dLng              终点经度
     * @param dName             终点名称
     * @param isNavigation      是否导航
     */
    public static void openTencent(Context context,
                                   double sLat, double sLng, String sName,
                                   double dLat, double dLng, String dName,
                                   boolean isNavigation) {
        if (checkMapAppsIsExist(context, PN_TENCENT_MAP)) {

            String uriString = null;
            if(isNavigation) {
                StringBuilder builder = new StringBuilder();
                builder.append("qqmap://map/routeplan?type=drive&policy=0&referer=myapp");
                if (sLat != 0) {
                    builder.append("&from=").append(sName)
                            .append("&fromcoord=").append(sLat).append(",").append(sLng).append("&");
                }
                builder.append("to=").append(dName)
                        .append("&tocoord=").append(dLat).append(",").append(dLng);
                uriString = builder.toString();

            }else {
                StringBuilder builder = new StringBuilder();

                if(StringUtils.isNotEmpty(dName)){
                    /* 调起腾讯地图APP，并在图上标注位置 */
                    builder.append("qqmap://map/marker?")
                            .append("marker=coord:").append(dLat).append(",").append(dLng).append(";")
                            .append("title:").append(dName).append(";")
                            .append("addr:").append("")
                            .append("&referer=myapp");

                }else {
                    /* 调起腾讯地图APP，并在图上标注位置，标注名称由腾讯地图自动生成 */
                    builder.append("qqmap://map/geocoder?")
                            .append("coord=").append(dLat).append(",").append(dLng)
                            .append("&referer=myapp");
                }


                uriString = builder.toString();
            }


            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(uriString));
            context.startActivity(intent);

        } else {
            mapAppsNotExist(context, "您尚未安装腾讯地图", PN_TENCENT_MAP);
        }
    }


    /**
     * 打开高德地图
     *
     *  1、路线导航
     *     route 服务类型 是
     *     sourceApplication 第三方调用应用名称。如 amap 是
     *     sid 起点的POIID 否
     *     slat 起点纬度。如果不填写此参数则自动将用户当前位置设为起点纬度。 否
     *     slon 起点经度。如果不填写此参数则自动将用户当前位置设为起点经度。 否
     *     sname 起点名称 否
     *     did 终点的POIID 否
     *     dlat 终点纬度 是
     *     dlon 终点经度 是
     *     dname 终点名称 否
     *     dev 起终点是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密) 是
     *     m 驾车方式 =0（速度快）=1（费用少） =2（路程短）=3 不走高速 =4（躲避拥堵）=5（不走高速且避免收费） =6（不走高速且躲避拥堵） =7（躲避收费和拥堵） =8（不走高速躲避收费和拥堵）。 公交 =0（速度快）=1（费用少） =2（换乘较少）=3（步行少）=4（舒适）=5（不乘地铁）
     *          由于与用户本地设置冲突，Android平台7.5.9版本起不支持该参数，偏好设置将以用户本地设置为准 是
     *     t t = 0（驾车）= 1（公交）= 2（步行）= 3（骑行）= 4（火车）= 5（长途客车） （骑行仅在V7.8.8以上版本支持） 是
     *     rideType 仅当 t = 3 时该参数生效。rideType = elebike    电动车，rideType = bike/为空 自行车（电动车规划仅在V8.65.0及以上版本支持）
     *
     *     例子: dat=amapuri://route/plan/?
     *          sid=BGVIS1
     *          &slat=39.92848272&slon=116.39560823&sname=A
     *          &did=BGVIS2
     *          &dlat=39.98848272&dlon=116.47560823&dname=B
     *          &dev=0&t=0
     *
     *  2、地图标注
     *     viewMap 服务类型 是
     *     sourceApplication 第三方调用应用名称。如 sinaweibo 是
     *     poiname POI名称 是
     *     lat 经纬度参数 是
     *     lon 经纬度参数 是
     *     dev 起终点是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密) 是
     *
     *     例子: dat=androidamap://viewMap?
     *          sourceApplication=appname
     *          &poiname=abc
     *          &lat=36.2&lon=116.1
     *          &dev=0
     *
     * @param context
     * @param sLat              起点纬度
     * @param sLng              起点经度
     * @param sName             起点名称   可不填（0,0，null）
     * @param dLat              终点纬度
     * @param dLng              终点经度
     * @param dName             终点名称
     * @param isNavigation      是否导航
     */
    public static void openGaoDeMap(Context context,
                                    double sLat, double sLng, String sName,
                                    double dLat, double dLng, String dName,
                                    boolean isNavigation) {

        if (checkMapAppsIsExist(context, PN_TENCENT_MAP)) {
            String uriString = null;
            if (isNavigation) {

                StringBuilder builder = new StringBuilder();
                builder.append("amapuri://route/plan/?");
                if (sLat != 0) {
                    builder.append("slat=").append(sLat).append("&slon=").append(sLng)
                            .append("&sname=").append(sName).append("&");
                }
                builder.append("dlat=").append(dLat).append("&dlon=").append(dLng)
                        .append("&sname=").append(sName)
                        .append("&dev=0&t=0");
                uriString = builder.toString();

            } else {
                StringBuilder builder = new StringBuilder();
                /* 调起高德地图APP，并在图上标注位置 */
                builder.append("androidamap://viewMap?")
                        .append("sourceApplication=").append("myapp")
                        .append("&poiname=").append(dName)
                        .append("&lat=").append(dLat).append("&lon=").append(dLng)
                        .append("&dev=0");
                uriString = builder.toString();
            }

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setPackage(PN_GAODE_MAP);
            intent.setData(Uri.parse(uriString));

            context.startActivity(intent);
        } else {                                                            /* 未安装 */
            mapAppsNotExist(context, "您尚未安装高德地图", PN_GAODE_MAP);
        }
    }

    /**
     * 打开百度地图
     *
     *
     * @param context
     * @param sLat              起点纬度
     * @param sLng              起点经度
     * @param sName             起点名称   可不填（0,0，null）
     * @param dLat              终点纬度
     * @param dLng              终点经度
     * @param dName             终点名称
     * @param isNavigation      是否导航
     */
    public static void openBaiduMap(Context context,
                                    double sLat, double sLng, String sName,
                                    double dLat, double dLng, String dName,
                                    boolean isNavigation) {

        if (checkMapAppsIsExist(context, PN_BAIDU_MAP)) {
            String uriString = null;
            if (isNavigation) {

                StringBuilder builder = new StringBuilder();
                builder.append("baidumap://map/direction?destination=");
                if (sLat != 0) {
                    builder.append("origin=name:").append(sName)
                            .append("|latlng:").append(sLat).append(",").append(sLng).append("&");
                }
                if (dLat != 0){
                    builder.append("destination=name:").append(dName)
                            .append("|latlng:").append(dLat).append(",").append(dLng).append("&");
                }else {
                    builder.append("destination=").append(dName);
                }

                builder.append("mode=driving");
                uriString = builder.toString();
            } else {
                StringBuilder builder = new StringBuilder();
                /* 调起百度地图APP，并在图上标注位置 */

                if(dLat != 0){                                          /* 根据经纬度来定位 */
                    builder.append("baidumap://map/marker?")
                            .append("location=").append(dLat).append(",").append(dLng)
                            .append("&title=").append(dName);
                }else {                                                 /* 根据名称来定位 */
                    builder.append("baidumap://map/geocoder?")
                            .append("address=").append(dName);
                }

                uriString = builder.toString();
            }

            Intent intent = new Intent();
            intent.setData(Uri.parse(uriString));

            context.startActivity(intent);
        } else {                                                            /* 未安装 */
            mapAppsNotExist(context, "您尚未安装百度地图", PN_BAIDU_MAP);
        }
    }

    /**
     *
     * market为路径，id为包名
     * 显示手机上所有的market商店
     *
     * @param context
     * @param msg               要提示的消息 -- 您尚未安装腾讯地图
     * @param pn
     */
    private static void mapAppsNotExist(Context context, String msg, String pn){
        ToastUtils.showToast(msg);
        Uri uri = Uri.parse("market://details?id=" + pn);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }


    /**
     * 百度转高德
     *
     * @param bd_lat
     * @param bd_lon
     * @return
     */
    public static double[] bdToGaoDe(double bd_lat, double bd_lon) {
        double[] gd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
        gd_lat_lon[0] = z * Math.cos(theta);
        gd_lat_lon[1] = z * Math.sin(theta);
        return gd_lat_lon;
    }

    /**
     * 高德、腾讯转百度
     *
     *         double destination[] = gaoDeToBaidu(dlat, dlon);
     *         double dlat = destination[0];
     *         double dlon = destination[1];
     *
     * @param gd_lon
     * @param gd_lat
     * @return
     */
    private static double[] gaoDeToBaidu(double gd_lon, double gd_lat) {
        double[] bd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = gd_lon, y = gd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
        bd_lat_lon[0] = z * Math.cos(theta) + 0.0065;
        bd_lat_lon[1] = z * Math.sin(theta) + 0.006;
        return bd_lat_lon;
    }

//    /**
//     * 百度坐标系 (BD-09) 与 火星坐标系 (GCJ-02)的转换
//     * 即 百度 转 谷歌、高德
//     *
//     * @param latLng
//     * @returns
//     *
//     * 使用此方法需要下载导入百度地图的BaiduLBS_Android.jar包
//     */
//    public static LatLng BD09ToGCJ02(LatLng latLng) {
//        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
//        double x = latLng.longitude - 0.0065;
//        double y = latLng.latitude - 0.006;
//        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
//        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
//        double gg_lat = z * Math.sin(theta);
//        double gg_lng = z * Math.cos(theta);
//        return new LatLng(gg_lat, gg_lng);
//    }
//
//    /**
//     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换
//     * 即谷歌、高德 转 百度
//     *
//     * @param latLng
//     * @returns
//     *
//     * 需要百度地图的BaiduLBS_Android.jar包
//     */
//    public static LatLng GCJ02ToBD09(LatLng latLng) {
//        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
//        double z = Math.sqrt(latLng.longitude * latLng.longitude + latLng.latitude * latLng.latitude) + 0.00002 * Math.sin(latLng.latitude * x_pi);
//        double theta = Math.atan2(latLng.latitude, latLng.longitude) + 0.000003 * Math.cos(latLng.longitude * x_pi);
//        double bd_lat = z * Math.sin(theta) + 0.006;
//        double bd_lng = z * Math.cos(theta) + 0.0065;
//        return new LatLng(bd_lat, bd_lng);
//    }
}
