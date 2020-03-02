package com.geli.m.mvp.home.other.login_register_activity.login_fragment;

import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/11/2.
 */

public class LoginModelImpl extends BaseModel {
    public void doLogin(String phone, String password, BaseObserver observer) {
        universal(mApiService.doLogin(phone, password)
                .map(new Function<ResponseBody, String>() {
                    @Override
                    public String apply(@NonNull ResponseBody body) throws Exception {
                        String string = body.string();
                        // Log.i("shen","string -- " + string);
                        JSONObject jsonObject = new JSONObject(string);
                        if (jsonObject.getInt("code") == 100) {
                            // 融云
                            // GetRongImToken.startRongIm(jsonObject.getJSONObject("data").getString("phone"));
                        }
                        return string;
                    }
                }), observer);
    }
}
