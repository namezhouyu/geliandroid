package com.geli.m.app;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.multidex.MultiDex;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import com.geli.m.broadcastreceiver.ConnectionChangeReceiver;
import com.geli.m.utils.LogUtils;
import com.geli.m.utils.LoginInformtaionSpUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
//import io.rong.imkit.RongIM;

/**
 * Created by mrCTang on 2017/5/23.
 */

public class GlobalData extends Application {

    public static String Md5Str = "ge+li-shi*pin.wangandroid+app";
    public static String cat_id;
    public static List<Boolean> isSCSCCheck;//食材商城
    public static String avaer;
    public static boolean isNeedShowUpdate = true;//是否需要提示用户更新app,进一次app只需要提示用户一次
    public static String userName = "";
    private ConnectionChangeReceiver myReceiver;
    public static boolean hasNetWork = false;//是否联网
    private static GlobalData instance;
    public static boolean isLxCustomerService;
    public static boolean isSetPayPwd;//是否设置了支付密码
    public static Context mContext;
    public static final String fileName = "gelifood";

    private static boolean isDowning = false;       /* 是否正在下载 apk*/

    private static boolean initX5 = false;

    public static String getUser_id() {
        return LoginInformtaionSpUtils.getLoginInfString(mContext, LoginInformtaionSpUtils.login_user_id);
    }

    public static String getPhone() {
        return LoginInformtaionSpUtils.getLoginInfString(mContext, LoginInformtaionSpUtils.login_phone);
    }

    public static String getToken() {
        return LoginInformtaionSpUtils.getLoginInfString(mContext, LoginInformtaionSpUtils.login_token);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        instance = this;
        isSCSCCheck = new ArrayList<>();

        //极光
        JPushInterface.setDebugMode(true);
        JPushInterface.init(mContext);

        setAlias();
        //支付初始化
        payInit();

        // RongIM.init(this);       // 融云初始化
        // CrashHandler.getInstance().init(this);//初始化全局异常管理
        registerReceiver();
        // FileUtils.LOCAL_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/geli/";//图片保存路径

        // 腾讯Bugly
        CrashReport.initCrashReport(getApplicationContext(), "d3e7ed5fbd", false);

        setRxJavaErrorHandler();
    }

    /***
     * x5内核，初始化
     */
    public static void initQb(){
        // 搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        // 初始化X5内核
        QbSdk.initX5Environment(getInstance(), new QbSdk.PreInitCallback() {

            @Override
            public void onCoreInitFinished() {
                //x5内核初始化完成回调接口，此接口回调并表示已经加载起来了x5，有可能特殊情况下x5内核加载失败，切换到系统内核。
            }

            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.e("TbsReaderView", "加载内核是否成功:" + b);
                LogUtils.i("加载内核是否成功:" + b);
            }
        });
    }

    // 这是来自 JPush Example 的设置别名的 Activity 里的代码。一般 App 的设置的调用入口，在任何方便的地方调用都可以。
    private void setAlias() {
        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS,
                LoginInformtaionSpUtils.getLoginInfString(mContext, LoginInformtaionSpUtils.login_phone)));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
            }
        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
            }
        }
    };


    public static GlobalData getInstance() {
        return instance;
    }

    private void payInit() {
        isSetPayPwd = LoginInformtaionSpUtils.getLoginInfBoolean(getInstance(), LoginInformtaionSpUtils.login_pay_pwd);
    }

    public static boolean isIsDowning() {
        return isDowning;
    }

    public static void setIsDowning(boolean isDowning) {
        GlobalData.isDowning = isDowning;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        myReceiver = new ConnectionChangeReceiver();
        this.registerReceiver(myReceiver, filter);
    }

    private void unregisterReceiver() {
        if (myReceiver != null)
            this.unregisterReceiver(myReceiver);
    }


    /**
     * RxJava2 当取消订阅后(dispose())，RxJava抛出的异常后续无法接收(此时后台线程仍在跑，可能会抛出IO等异常),全部由RxJavaPlugin接收，需要提前设置ErrorHandler
     * 详情：http://engineering.rallyhealth.com/mobile/rxjava/reactive/2017/03/15/migrating-to-rxjava-2.html#Error Handling
     */
    private void setRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LogUtils.i("RxJavaErrorHandler : ", throwable);
            }
        });
    }
    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        super.onTerminate();
        unregisterReceiver();
    }


    public static void exit(){
        android.os.Process.killProcess(android.os.Process.myPid());    //获取PID
        System.exit(0);   //常规java、c#的标准退出法，返回值为0代表正常退出
    }

}
