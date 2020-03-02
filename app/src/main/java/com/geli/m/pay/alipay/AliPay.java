package com.geli.m.pay.alipay;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.geli.m.R;
import com.geli.m.utils.RxUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by l on 2018/4/17.
 */

public class AliPay {
    public static void pay(final Activity activity, final String payData, final AliPayListener listener) {
        Observable.create(new ObservableOnSubscribe<Map<String, String>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Map<String, String>> e) throws Exception {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(payData, true);
                e.onNext(result);
            }
        }).compose(RxUtils.<Map<String, String>>rxSchedulerHelper())
                .subscribe(new Observer<Map<String, String>>() {
                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onNext(Map<String, String> stringStringMap) {
                                   PayResult payResult = new PayResult(stringStringMap);
                                   String resultStatus = payResult.getResultStatus();
                                   if (TextUtils.equals(resultStatus, "9000")) {
                                       listener.success();
                                   } else {
                                       listener.failed();
                                   }
                               }

                               @Override
                               public void onError(Throwable e) {
                                   ToastUtils.showToast(activity, Utils.getString(R.string.pay_failed));
                                   listener.failed();
                               }

                               @Override
                               public void onComplete() {

                               }
                           }
                );
    }

    public interface AliPayListener {
        void success();

        void failed();
    }
}
