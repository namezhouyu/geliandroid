package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.uploadcertificate_activity;

import com.geli.m.R;
import com.geli.m.bean.BaseBean;
import com.geli.m.bean.OfflinePaymentSuccess;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.utils.Utils;
import com.google.gson.Gson;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/12/6.
 */

public class UploadTransferPresenterImpl extends BasePresenter<UploadTransferView, UploadTransferModelImpl> {
    public UploadTransferPresenterImpl(UploadTransferView mvpView) {
        super(mvpView);
    }

    public void getBankInfo(String user_id) {
        mModel.getBankInfo(user_id, new BaseObserver<ResponseBody>(this, mvpView) {

            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    String string = data.string();
                    BaseBean baseBean = parseData(string);
                    if (baseBean.code == Constant.REQUEST_OK) {
                        OfflinePaymentSuccess info = new Gson().fromJson(new JSONObject(string).getString("data"), OfflinePaymentSuccess.class);
                        mvpView.showBankInfo(info);
                    } else {
                        mvpView.onError(baseBean.message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mvpView.onError(parseError(e));
                }
            }

        });

    }

    public void uploadTransfer(RequestBody body) {
        mModel.UploadPayProof(body, new BaseObserver<ResponseBody>(this, mvpView) {

            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    String string = data.string();
                    BaseBean baseBean = parseData(string);
                    if (baseBean.code == Constant.REQUEST_OK) {
                        mvpView.onSuccess(Utils.getString(R.string.upload_success));
                        //                        OfflinePaymentSuccess info = mGson.fromJson(new JSONObject(string).getString("data"), OfflinePaymentSuccess.class);
                        //                        mvpView.showUploadReulst(info);
                    } else {
                        mvpView.onError(baseBean.message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mvpView.onError(parseError(e));
                }
            }

        });
    }

    @Override
    protected UploadTransferModelImpl createModel() {
        return new UploadTransferModelImpl();
    }
}
