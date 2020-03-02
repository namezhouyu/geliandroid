package com.geli.m.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.EditText;

import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.sharesdk.onekeyshare.OnekeyShare;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static android.view.Gravity.CENTER;
import static com.geli.m.app.GlobalData.mContext;

/**
 * 工具类
 * Created by mrCTang on 2017/5/12.
 */

public class Utils {
    private static ProgressDialog progressDialog;

    public static String getString(int resid) {
        return mContext.getResources().getString(resid);
    }

    public static String getString(int resid, Object... format) {
        return String.format(mContext.getResources().getString(resid), format);
    }


    public static String getString(Context context, int resid) {
        return context.getResources().getString(resid);
    }

    public static String getString(Context context, int resid, Object... format) {
        return String.format(context.getResources().getString(resid), format);
    }


    /**是否安装指定app
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    public static boolean TextIsNull(Context context, String string, String mess) {
        if (string.isEmpty()) {
            ShowSingleToast.showToast(context, mess);
            return true;
        }
        return false;
    }

    public static String getStringNoNull(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        return string;
    }

    /**
     * 加密
     *
     * @param phone
     * @return
     */
    public static String phoneEncryption(String phone) {
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static void copy(Context context, String message) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(message);
        // cm.setPrimaryClip(ClipData.newPlainText(null, message));
        ShowSingleToast.showToast(context, Utils.getString(R.string.copy_success));
    }

    public static void copyToastCenter(Context context, String message) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(message);
        // cm.setPrimaryClip(ClipData.newPlainText(null, message));
        ToastUtils.showToast(Utils.getString(R.string.copy_success), CENTER);
    }

    /**
     * 大电话
     * @param context
     * @param phone
     */
    public static void callPhone(final Context context, final String phone) {
        callPhone(context, phone, null);
    }

    public static void callPhone(final Context context, final String phone, final DialogConfirmListener listener) {
        new AlertDialog.Builder(context).setTitle(Utils.getString(R.string.contactthemerchat))
                .setMessage(Utils.getString(R.string.welcomeyouradvice) + phone)
                .setPositiveButton(Utils.getString(R.string.makesuretomakecall), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + phone));
                        new RxPermissions((Activity) context)
                                .request(Manifest.permission.CALL_PHONE)
                                .subscribe(new Observer<Boolean>() {
                                               @Override
                                               public void onSubscribe(Disposable d) {

                                               }

                                               @SuppressLint("MissingPermission")
                                               @Override
                                               public void onNext(Boolean aBoolean) {
                                                    if (aBoolean) {
                                                        if (null != listener) {
                                                            listener.confirm();
                                                        }
                                                        context.startActivity(intent);
                                                    } else {
                                                        Utils.showMissPermissionDialog(context, Utils.getString(R.string.orderdetails_dialumber), Utils.getString(R.string.permissioncallphone));
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
                }).setNegativeButton(Utils.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    public static boolean etIsNull(EditText et) {
        if(et != null){
            return TextUtils.isEmpty(et.getText().toString().trim());
        }else {
            return true;
        }
    }


    /**
     * 创建一个进度对话框
     *
     * @param context
     */
    public static void uProgressDialog(Context context) {
        uProgressDialog(context, "");
    }

    public static void uProgressDialog(Context context, String message) {
        dismissProgress();
        if (progressDialog == null) {
            synchronized (Utils.class) {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(context);
                }
            }
        }
        if (TextUtils.isEmpty(message)) {
            progressDialog.setMessage(Utils.getString(R.string.htttp_requesting));
        } else {
            progressDialog.setMessage(message);
        }
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public static void setShare(Context context, String httpUrl, String imageUrl, String title, String textMessage) {

        if (imageUrl == null) {
            imageUrl = null;
        }
        if (httpUrl == null) {
            //httpUrl = "http://app.gelifood.com/d/mobile/";
            httpUrl = "http://app.gelistore.com/d/mobile/";
        }
        if (title == null) {
            title = "好吃的牛肉丸";
        }
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(httpUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(textMessage);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(imageUrl);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(httpUrl);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(textMessage);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("格利食品网");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(httpUrl);

        // 启动分享GUI
        oks.show(context);
    }

    /**
     * 关闭progressDialog
     */
    public static void dismissProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog.cancel();
        }
        progressDialog = null;
    }

    public interface DialogConfirmListener {
        void confirm();
    }


    /**
     * @param context
     * @return
     */
    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public static int GugleData(String data, String ziduan) throws JSONException {
        JSONObject json = new JSONObject(data);
        int anInt = json.getInt(ziduan);
        return anInt;
    }

    /**
     * double 乘法,确保精度不丢失
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double mul(Double d1, Double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2).doubleValue();
    }

    /**
     * 手机号码中间四位打星号 如131****1234
     *
     * @param phoneNum
     * @return
     */
    public static String getPhoneReplaceStars(String phoneNum) {
        String phone = "";
        if (!TextUtils.isEmpty(phoneNum)) {
            int phoneNumLen = phoneNum.length();
            if (phoneNumLen > 7) {
                phone = phoneNum.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
            } else {
                phone = phoneNum;
            }
        }
        return phone;

    }


    /**
     * 格式化 double，默认保留digit小数
     *
     * @param d1
     * @return
     */
    public static String getFormatDoubleTwoDecimalPlaces(Double d1, int digit) {
        String df = "#,##0.";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(df);
        for (int i = 0; i < digit; i++) {
            stringBuilder.append("0");
        }
        DecimalFormat decimalFormat = new DecimalFormat(stringBuilder.toString());// 格式化设置
        return decimalFormat.format(d1);
    }

    public static int getColor(int colorId) {
        return ContextCompat.getColor(GlobalData.getInstance(), colorId);
    }

    /**
     * 拼html
     *
     * @param content
     * @return
     */
    public static String getJSContent(String content) {
        String html = "<html>";
        html += "<head>" +
                //                "<style>*{margin-left:0;margin-right:0;padding-left:0;padding-right:0;}.aabbccdd{width:100%!important;height:auto !important;margin:0 !important;padding:0 !important;}p{padding:0 !important;margin-left:0 !important;margin-right:0 !important;}p *{padding:0 !important;margin:0 !important;position:relative !important;}</style>"+
                "<style>*{margin-left:0;margin-right:0;padding-left:0;padding-right:0;}p{font-size:18px !important;}img{width:100%!important;height:auto !important;margin:0 !important;padding:0 !important;}p{padding:0 !important;margin-left:0 !important;margin-right:0 !important;}p *{padding:0 !important;margin:0 !important;position:relative !important;}</style>" +//图片适配不变形
                "</head>";
        html += "<body><div id=\"small\" class=\"main\"style=\"color:#676767\" style=\"width:device-width\" type = \"application/x-shockwave-flash\">"
                + content + "</div></body><html>";//其中的color是设置字体颜色、font-size为字体大小
        return html;
    }


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static void startActivity(Context context, Class clas) {
        context.startActivity(new Intent(context, clas));
    }


    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本code
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * pxתdp
     */
    public static int px2dip(Context ctx, float pxValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    private static AlertDialog.Builder builder;
    private static AlertDialog alertDialog;

    public static void showMissPermissionDialog(final Context context) {
        showMissPermissionDialog(context, Utils.getString(R.string.necessary), "");
    } // 启动应用的设置

    public static void showMissPermissionDialog(final Context context, String message, String orther) {
        if (builder == null) {
            builder = new AlertDialog.Builder(context);
            builder.setTitle(Utils.getString(R.string.title_help));
            //materialDialog.setMovementMethod(LinkMovementMethod.getInstance());
            builder.setPositiveButton(Utils.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cancelDialog();
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    cancelDialog();
                }
            });
            builder.setNegativeButton(Utils.getString(R.string.setting), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cancelDialog();
                    startAppSettings(context);
                }
            });
        }
        orther = orther.isEmpty() ? "" : "，" + orther;
        builder.setMessage(Utils.getString(R.string.permissionmessage, message, Utils.getString(R.string.used) + orther));
        alertDialog = builder.create();
        alertDialog.show();
    }

    public static void cancelDialog() {
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
            builder = null;
        }
    }


    public static void startAppSettings(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 存偏好设置
     *
     * @param context
     * @param SPname  偏好设置文件名字
     * @param key
     * @param value
     */
    public static void setSP(Context context, String SPname, String key, String value) {
        //获取编辑器
        SharedPreferences sharedPreferences = context.getSharedPreferences(SPname, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key, value);
        //提交
        edit.commit();
    }

    /**
     * 取偏好设置内容
     *
     * @param context
     * @param SPname  偏好设置文件名字
     * @param key
     * @return
     */
    public static String getSP(Context context, String SPname, String key) {
        //获取编辑器
        SharedPreferences sharedPreferences = context.getSharedPreferences(SPname, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }


    public static String getString(Object o) {
        String result = "";
        if (o instanceof String) {
            String s = (String) o;
            if (!TextUtils.isEmpty(s)) {
                return s;
            }
        }
        if (o != null) {
            return o.toString();
        }
        return result;
    }

    public static String md5(String string) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";

    }

    public static boolean isLogin(Context context, boolean toLoginActivity) {
        return isLogin(context, null, toLoginActivity);
    }

    //未登录跳转
    public static boolean isLogin(Context context, Class myClass, boolean toLoginActivity) {
        boolean isLogin = false;
        if (LoginInformtaionSpUtils.getLoginInfString(context, LoginInformtaionSpUtils.login_login).trim().equals("1")) {
            isLogin = true;
            if (myClass != null) {
                Intent intent = new Intent(context, myClass);
                context.startActivity(intent);
            }
            return isLogin;
        }
        if (toLoginActivity) {
            //            Intent intent = new Intent(context, LoginActivity.class);
            //            context.startActivity(intent);
        }
        return isLogin;
    }

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 6000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    /**
     * 判断号码
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
     * 时间戳转换成时间
     *
     * @param stamp
     * @return
     */
    public static String stamp2Date(String stamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if ("0".equals(stamp) || "0.0".equals(stamp)) {
            return stamp;
        }
        return simpleDateFormat.format(new Date(Long.valueOf(stamp + "000")));
    }

}
