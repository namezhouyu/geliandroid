package com.geli.m.wxapi;

import android.content.Context;

import com.geli.m.utils.LogUtils;
import com.geli.m.utils.ToastUtils;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by Labor on 2017/6/12.
 */

public class WeChatPay {

    /** 微信注册的appid */
    public static String WX_APP_ID = "wxfa5d52a503eb6958";
    /** 第三方app和微信通信的接口 */
    private static IWXAPI api;

    public static void pay(Context context, String app_id, String partnerId, String nonceStr, String prepayid, String timeStamp, String packageValue, String sign) {

        if(api == null){
            api = WXAPIFactory.createWXAPI(context, WX_APP_ID);
//            api = WXAPIFactory.createWXAPI(context, null);
//            api.registerApp(APP_ID);
        }

        if (!api.isWXAppInstalled()) {                                          /* 没有安装 -- 微信 */
//            Looper.prepare();
//            Looper.loop();

            ToastUtils.showToast("请按装微信");

        } else if (api.getWXAppSupportAPI() < Build.PAY_SUPPORTED_SDK_INT) {    /* 小于某个版本 */
//            Looper.prepare();
//            Looper.loop();
            ToastUtils.showToast("请按装最新版本的微信");

        } else {                                                                /* 调用微信支付 */
            PayReq req = new PayReq();
            // 设置参数
            req.appId = WX_APP_ID;
            req.partnerId = partnerId;
            req.nonceStr = nonceStr;
            req.timeStamp = timeStamp;
            req.prepayId = prepayid;
            req.packageValue = packageValue;
            req.sign = sign;

            LogUtils.i("app_id:" + app_id);
            LogUtils.i("WX_APP_ID:" + WX_APP_ID);

            api.registerApp(WX_APP_ID);
            api.sendReq(req);                    // 调用微信支付
        }
    }


    private void text(){
        PayReq req = new PayReq();
        // 设置参数
        req.appId = "wxfa5d52a503eb6958";
        req.partnerId = "1481750992";
        req.nonceStr = "5K8264ILTKCH16CQ2502SI8ZNMTM67VS";
        req.timeStamp = "1497605099";
        req.prepayId = "wx201706161724596ac069a16f0881163872";
        req.packageValue = "Sign=WXPay";
        req.sign = "531282DC202F26CD411313A4C495359E";
        api.sendReq(req);                    // 调用微信支付
    }
}
