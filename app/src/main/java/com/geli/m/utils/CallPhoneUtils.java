package com.geli.m.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.geli.m.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * Name:
 * Author: pks
 * Date: 2017-09-06 09:43
 */

public class CallPhoneUtils {
    /**
     * 直接拨打电话
     *
     * @param activity
     * @param phoneNum
     */
    public static void callPhone(final Activity activity, String phoneNum) {
        if (TextUtils.isEmpty(phoneNum) || activity == null) {
            return;
        }
        final Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNum));
        new RxPermissions(activity)
                .request(Manifest.permission.CALL_PHONE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            activity.startActivity(intent);
                        } else {
                            Utils.showMissPermissionDialog(activity, Utils.getString(R.string.orderdetails_dialumber), Utils.getString(R.string.permissioncallphone));
                        }
                    }
                });

    }
}
