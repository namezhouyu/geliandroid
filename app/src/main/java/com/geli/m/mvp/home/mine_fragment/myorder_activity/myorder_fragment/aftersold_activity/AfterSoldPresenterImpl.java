package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.aftersold_activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.geli.m.R;
import com.geli.m.bean.BaseBean;
import com.geli.m.bean.OrderContactBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.utils.Utils;
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
 * author:  shen
 * date:    2018/5/22
 */

public class AfterSoldPresenterImpl extends BasePresenter<AfterSoldView, AfterSoldModeImpl> {

    public AfterSoldPresenterImpl(AfterSoldView mvpView) {
        super(mvpView);
    }

    @Override
    protected AfterSoldModeImpl createModel() {
        return new AfterSoldModeImpl();
    }


    public void getContact(String user_id, String order_id){
        mModel.getContact(user_id, order_id, new BaseObserver<OrderContactBean>(this, mvpView) {
            @Override
            protected void onSuccess(OrderContactBean data) {
                mvpView.getContactSuccess(data);
            }
        });
    }

    public void aftermarket(String user_id, String type, String order_id, String content,
                            String contact, String contact_method, List<LocalMedia> bean, String img_number){

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
        builder.addFormDataPart("type", type);
        builder.addFormDataPart("order_id", order_id);
        builder.addFormDataPart("content", content);
        builder.addFormDataPart("contact", contact);
        builder.addFormDataPart("contact_method", contact_method);
        builder.addFormDataPart("img_number", img_number);

        mModel.afterSoldSubmit(builder.build(), new BaseObserver<ResponseBody>(this, mvpView) {

            @Override
            protected void onSuccess(ResponseBody data) {
//                if (data.getCode() == Constant.REQUEST_OK) {
//                    mvpView.onSuccess(data.getMessage());
//                } else {
//                    mvpView.onError(data.getMessage());
//                }

                try {
                    BaseBean baseBean = parseData(data.string());
                    if (baseBean.code == Constant.REQUEST_OK) {
                        mvpView.onSuccess(Utils.getString(R.string.message_apply_success));
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
}
