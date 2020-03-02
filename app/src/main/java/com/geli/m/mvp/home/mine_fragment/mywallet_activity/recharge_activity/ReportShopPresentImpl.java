package com.geli.m.mvp.home.mine_fragment.mywallet_activity.recharge_activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.geli.m.bean.BaseBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.mvp.base.BaseView;
import com.luck.picture.lib.entity.LocalMedia;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/3/30.
 */

public class ReportShopPresentImpl extends BasePresenter<BaseView, ReportShopModelImpl> {
    public ReportShopPresentImpl(BaseView mvpView) {
        super(mvpView);
    }

    public void reportShop(List<LocalMedia> bean, String user_id, String shop_id, String message, String report_type) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        FileInputStream fis = null;
        String path;
        int i = 0;
        for (LocalMedia media : bean) {
            path = media.getCompressPath();
            try {
                fis = new FileInputStream(path);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            i++;
            Bitmap bm = BitmapFactory.decodeStream(fis);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
            builder.addFormDataPart("photo" + i, path, RequestBody.create(MediaType.parse("image/jpeg"), byteArrayOutputStream.toByteArray()));
        }
        builder.addFormDataPart("user_id", user_id);
        builder.addFormDataPart("shop_id", shop_id);
        builder.addFormDataPart("message", message);
        builder.addFormDataPart("report_type", report_type);
        mModel.reportMerchant(builder.build(), new BaseObserver<ResponseBody>(this, mvpView) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    BaseBean baseBean = parseData(data.string());
                    if (baseBean.code == Constant.REQUEST_OK) {
                        mvpView.onSuccess(baseBean.message);
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
    protected ReportShopModelImpl createModel() {
        return new ReportShopModelImpl();
    }
}
