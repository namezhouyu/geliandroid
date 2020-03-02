package com.geli.m.utils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.geli.m.app.GlobalData;


/**
 * Created by pks on 2017/6/6.
 * 用法：LocationUtil.getInstance().startLocation(Listener)
 *
 */
public class LocationUtil {
    private static LocationUtil instance;
    private AMapLocationClientOption mLocationOption = null;//声明mLocationOption对象
    private static AMapLocationClient mLocationClient = null;//高德地图定位
    // private AMapLocationListener mDefaultListener = null;//默认的监听器，

    private LocationUtil() {

    }

    public static synchronized LocationUtil getInstance() {
        if (instance == null) {
            instance = new LocationUtil();
        }
        if (mLocationClient == null) {
            // 初始化高德地图
            mLocationClient = new AMapLocationClient(GlobalData.getInstance()); // 声明LocationClient类
        }
        return instance;
    }

    /**
     * 开始定位，使用默认的监听器
     * 不推荐使用这种方式
     */
    public void startLocation() {
        startLocation(null);
    }


    /**
     * 开始定位
     * 推荐使用这种方式
     *
     * @param Listener 传入结果监听
     */
    public void startLocation(AMapLocationListener Listener) {
        if (Listener == null) {
            Listener = greatListener();
        }
        initLocation(); // 初始化定位配置
        mLocationClient.setLocationListener(Listener);//设置定位监听
    }

    private AMapLocationListener greatListener() {
        AMapLocationListener Listener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                //TODO对返回结果监听
            }
        };
        return Listener;
    }


    private void initLocation() {
        // 初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置定位间隔,单位毫秒,默认为2000ms
        // mLocationOption.setInterval(2000);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setOnceLocationLatest(true); // 设置单次定位

        //设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除

        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        //            if (ContextCompat.checkSelfPermission((Activity) GlobalData.getAppContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        //                    != PackageManager.PERMISSION_GRANTED) {
        //                ActivityCompat.requestPermissions((Activity) GlobalData.getAppContext(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        //            } else {
        //                //启动定位
        //                mLocationClient.startLocation();
        //            }
        //        } else {
        //        //启动定位
        mLocationClient.startLocation();
        //        }


    }
}
