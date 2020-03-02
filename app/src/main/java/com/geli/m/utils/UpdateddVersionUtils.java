package com.geli.m.utils;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import com.geli.m.R;
import com.geli.m.api.ApiEngine;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.AppVersion;
import com.geli.m.config.Constant;
import com.geli.m.dialog.TipDialog;
import com.geli.m.dialog.UpdatedVersionDialog;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BaseActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.geli.m.dialog.UpdatedVersionDialog.downLoadIdSPName;
import static com.geli.m.dialog.UpdatedVersionDialog.spKey;

/**
 * Created by Steam_l on 2018/3/27.
 */

public class UpdateddVersionUtils {

    public static int REQUEST_CODE_APP_INSTALL = 123;
    private static Context mContext;

    public interface UpdatedVerionListener {
        void update(AppVersion.DataBean versionData);
    }

    public interface UpdatedVersionAutoListener {
        void update(AppVersion.DataBean versionData);
    }

    public static void checkVersionUpdated(final Context context) {
        checkVersionUpdated(context, null, null);
    }

    public static void checkVersionUpdated(final Context context, UpdatedVersionAutoListener autoListener) {
        checkVersionUpdated(context, null, autoListener);
    }

    public static void checkVersionUpdated(final Context context, final UpdatedVerionListener listener) {
        checkVersionUpdated(context, listener, null);
    }

    public static void checkVersionUpdated(final Context context, final UpdatedVerionListener listener, final UpdatedVersionAutoListener autoListener) {
        mContext = context;
        ApiEngine.getInstance().getApiService()
                .versionUpdated()
                .compose(RxUtils.<AppVersion>rxSchedulerHelper())
                .subscribe(new Observer<AppVersion>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AppVersion appVersion) {
                        if (appVersion.getCode() == Constant.REQUEST_OK) {
                            AppVersion.DataBean data = appVersion.getData();
                            if (!(Integer.valueOf(data.getVersions()) > Utils.getVersionCode(context))) {
                                return;
                            }

                            if (autoListener != null) {
                                autoListener.update(data);
                                requestPermissions(context, data.getUrl(), data.getVersions() + "",
                                        data.getDesc(), data.getIs_update());
                                return;
                            }

                            if (listener != null) {
                                listener.update(data);
                                return;
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showToast(BaseObserver.parseError(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static void requestPermissions(final Context context, final String downLoadUrl, final String newVerSon,
                                          final String updateDesc, final int is_update) {
        RxPermissions rxPermissions = new RxPermissions((BaseActivity) context);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            if (!TextUtils.isEmpty(downLoadUrl)) {
                                UpdatedVersionDialog.newInstance(context, updateDesc, downLoadUrl, newVerSon, is_update)
                                        .show(((BaseActivity) context).getSupportFragmentManager(), "");
                            }
                        } else {
                            Utils.showMissPermissionDialog(mContext, Utils.getString(R.string.read_write),
                                    Utils.getString(R.string.message_downloadnewapk));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 安装新的程序
     *
     * @param context
     * @param uri     新版本的下载到本地的路径
     */
    public static void installNewVersion(Context context, Uri uri) {
        if(checkInstallPermission()) {
            //执行动作
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //执行的数据类型
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }



    public static boolean checkInstallPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean hasInstallPermission = isHasInstallPermissionWithO(mContext);
            if (!hasInstallPermission) {
                // 如果不去设置，也是可以安装的(每次安装的时候，系统弹出窗口，是否允许安装app)
//                showTigDialog();
//                return false;
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isHasInstallPermissionWithO(Context context){
        if (context == null){
            return false;
        }
        return context.getPackageManager().canRequestPackageInstalls();
    }

    /**
     * 开启设置安装未知来源应用权限界面
     * @param context
     */
    @RequiresApi (api = Build.VERSION_CODES.O)
    private static void startInstallPermissionSettingActivity(Context context) {
        if (context == null){
            return;
        }
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        // 获取当前apk包URI，并设置到intent中（这一步设置，可让“未知应用权限设置界面”只显示当前应用的设置项）
        Uri packageURI = Uri.parse("package:"+context.getPackageName());
        intent.setData(packageURI);
        ((Activity)context).startActivityForResult(intent, REQUEST_CODE_APP_INSTALL);
    }


    /**
     *
     */
    private static void showTigDialog(){
        TipDialog dialog = TipDialog.newInstance(Utils.getString(R.string.install_setting_message));
        dialog.isShowTitle(true)
                .isShowCancel(true)
                .isShowConfirm(true)
                .setTitleSrc(Utils.getString(R.string.title_help))
                .setConfirmSrc(Utils.getString(R.string.setting))
                .setConfirmTextColor(Utils.getColor(R.color.zhusediao))
                .setOnclickListener(new TipDialog.ClickListenerInterface() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void doConfirm(TipDialog tipDialog) {
                        tipDialog.dismiss();
                        startInstallPermissionSettingActivity(mContext);
                    }

                    @Override
                    public void doCancel() {
                    }
                })
                .show(((BaseActivity)mContext).getSupportFragmentManager(), "tempAP");
    }



    /**
     * 下载APK
     *
     * @param context
     * @param downLoadUrl
     * @param newVerSon
     */
    public static void downLoadNewApk(Context context, String downLoadUrl, String newVerSon) {
        if(GlobalData.isIsDowning()){
            return;
        }else {
            GlobalData.setIsDowning(true);
        }

        //TODO这里要改
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downLoadUrl));
        request.setTitle(Utils.getString(R.string.manager_updated));
        // 设置"下载后"保存的位置(data/data/包名/downloads/gelishancheng_vxxx.apk ) -- 重新设置文件名
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "gelishancheng_v" + newVerSon + ".apk");
        // 表示下载进行中和下载完成的通知栏是否显示。
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);


        /* 下载请求分配的一个唯一的ID */
        String versionDownloadIdpks = String.valueOf(downloadManager.enqueue(request));
        Utils.setSP(context, downLoadIdSPName, spKey, String.valueOf(versionDownloadIdpks));  // 保存
    }
}
