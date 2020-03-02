/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.geli.m.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信支付回调的activity
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    /**
     * 处理微信发出的向第三方应用请求app message
     * <p>
     * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中
     * 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
     * 做点其他的事情，包括根本不打开任何页面
     */
    //	public void onGetMessageFromWXReq(WXMediaMessage msg) {
    //		if (msg != null) {
    //			Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
    //			startActivity(iLaunchMyself);
    //		}
    //	}

    /**
     * 处理微信向第三方应用发起的消息
     * <p>
     * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
     * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信
     * 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作
     * 回调。
     * <p>
     * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
     */
    //	public void onShowMessageFromWXReq(WXMediaMessage msg) {
    //		if (msg != null && msg.mediaObject != null
    //				&& (msg.mediaObject instanceof WXAppExtendObject)) {
    //			WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
    //			Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).showKeyBoard();
    //		}
    //	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //		setContentView(R.layout.payment_success_pager);
        //		Toast.makeText(WXPayEntryActivity.this,"onCreate",Toast.LENGTH_LONG).showKeyBoard();

        api = WXAPIFactory.createWXAPI(this, WeChatPay.WX_APP_ID, false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    // 微信支付回调
    @Override
    public void onReq(BaseReq baseReq) {
    }

    // 微信支付回调
    @Override
    public void onResp(BaseResp baseResp) {
        int code = baseResp.errCode;
        if(baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (code == BaseResp.ErrCode.ERR_OK) {                                /* 支付成功 -- 进入到支付成功的页面 */
                if (mWeChatPayListener != null) {
                    mWeChatPayListener.success();
                }
            } else if (code == BaseResp.ErrCode.ERR_COMM) {                        /* 支付失败2 */
                if (mWeChatPayListener != null) {
                    mWeChatPayListener.failed();
                }
            } else if (code == BaseResp.ErrCode.ERR_USER_CANCEL) {                        /* 取消支付 */
                if (mWeChatPayListener != null) {
                    mWeChatPayListener.cancel();
                }
            }
        }
        finish();
    }

    static WeChatPayListener mWeChatPayListener;

    public static void setPaySuccessIntent(WeChatPayListener listener) {
        mWeChatPayListener = listener;
    }

    public interface WeChatPayListener {
        void success();

        void failed();

        void cancel();
    }
}
