package com.geli.m.broadcastreceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import cn.jpush.android.api.JPushInterface;
import com.geli.m.R;
import com.geli.m.bean.MessBean;
import com.geli.m.mvp.home.main.HomeActivity;
import com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.logisticsdetails_activity.LogisticsDetailsActivity;
import com.geli.m.mvp.home.other.WebViewActivity;
import com.geli.m.mvp.home.other.goodsdetails_activity.GoodsDetailsActivity;
import com.geli.m.mvp.home.other.login_register_activity.LoginActivity;
import com.geli.m.mvp.home.other.shopdetails_activity.ShopDetailsActivity;
import com.geli.m.utils.LoginInformtaionSpUtils;
import com.geli.m.utils.Utils;
import com.google.gson.Gson;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.geli.m.config.Constant.INTENT_GOODS_ID;
import static com.geli.m.config.Constant.INTENT_IS_OPEN;
import static com.geli.m.config.Constant.INTENT_LINKS;
import static com.geli.m.config.Constant.INTENT_ORDER_ID;
import static com.geli.m.config.Constant.INTENT_SHOP_ID;

/**
 * Created by Steam_l on 2018/3/29.
 */

public class NotifyActionBroadcastReceiver extends BroadcastReceiver {
    public static final String INTENT_DATA = "intent_data";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            //            [MyReceiver] 接收到推送下来的通知
            MessBean.DataEntity notifyBean = new Gson().fromJson(bundle.getString(JPushInterface.EXTRA_EXTRA), MessBean.DataEntity.class);
//            System.out.println(bundle.getString(JPushInterface.EXTRA_EXTRA));
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.loginbt_shape)
                            .setContentTitle(Utils.getString(R.string.app_name))
                            .setContentText(notifyBean.getDigest())
                            .setPriority(Notification.PRIORITY_HIGH)
                            .setAutoCancel(true);
            Intent resultIntent = new Intent(JPushInterface.ACTION_NOTIFICATION_OPENED);
            resultIntent.putExtra(INTENT_DATA, notifyBean);
            PendingIntent resultPendingIntent = PendingIntent.getBroadcast(
                    context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);
            Notification notification = mBuilder.build();
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            int mNotificationId = notifactionId;
            NotificationManager mNotifyMgr =
                    (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            mNotifyMgr.notify(mNotificationId, notification);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Intent resultIntent = new Intent();
            MessBean.DataEntity item = intent.getParcelableExtra(INTENT_DATA);
            int cat_id = item.getCat_id();
            if (cat_id == 1) {
                resultIntent.setClass(context, WebViewActivity.class);
                resultIntent.putExtra(INTENT_LINKS, item.getTarget_id() + "");
            } else if (cat_id == 3) {
                resultIntent.setClass(context, LogisticsDetailsActivity.class);
                resultIntent.putExtra(INTENT_ORDER_ID, item.getTarget_id() + "");
            } else if (cat_id == 4) {
                resultIntent.setClass(context, GoodsDetailsActivity.class);
                resultIntent.putExtra(INTENT_GOODS_ID, item.getTarget_id() + "");
            } else if (cat_id == 5) {
                resultIntent.setClass(context, ShopDetailsActivity.class);
                resultIntent.putExtra(INTENT_SHOP_ID, item.getTarget_id() + "");
            } else if (cat_id == 6) {
                resultIntent.setClass(context, ShopDetailsActivity.class);
                resultIntent.putExtra(INTENT_SHOP_ID, item.getTarget_id() + "")
                        .putExtra(INTENT_IS_OPEN, false);
            } else if (cat_id == 9) {
                resultIntent.setClass(context, WebViewActivity.class);
                resultIntent.putExtra(INTENT_LINKS, item.getTarget_id() + "");
            }
            Intent[] intents = makeIntentStack(context, resultIntent);
            context.startActivities(intents);
        }
    }

    Intent[] makeIntentStack(Context context, Intent formIntent) {
        int number = 2;
        boolean isLogin = LoginInformtaionSpUtils.getLoginInfString(context, LoginInformtaionSpUtils.login_login).equals("1");
        if (!isLogin) {
            number = 3;
        }
        Intent[] intents = new Intent[number];
        intents[0] = Intent.makeRestartActivityTask(new ComponentName(context, HomeActivity.class));
        intents[1] = formIntent;
        if (!isLogin) {
            intents[2] = new Intent(context, LoginActivity.class);
        }
        return intents;
    }
}
