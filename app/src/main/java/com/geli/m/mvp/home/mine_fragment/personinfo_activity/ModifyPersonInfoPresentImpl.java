package com.geli.m.mvp.home.mine_fragment.personinfo_activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.BaseBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.mvp.base.BaseView;
import com.geli.m.utils.Utils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/2/6.
 */

public class ModifyPersonInfoPresentImpl extends BasePresenter<BaseView, ModifyPersonInfoModelImpl> {
    public ModifyPersonInfoPresentImpl(BaseView mvpView) {
        super(mvpView);
    }

    public void modify(Map data) {
        mModel.modify(data, new BaseObserver<ResponseBody>(this, mvpView) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    BaseBean baseBean = parseData(data.string());
                    if (baseBean.code == Constant.REQUEST_OK) {
                        mvpView.onSuccess(Utils.getString(R.string.modify_success));
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

    public void doAvatar(String path) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bm = BitmapFactory.decodeStream(fis);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", path, RequestBody.create(MediaType.parse("image/jpeg"), new File(path)))
                .addFormDataPart("user_id", GlobalData.getUser_id());

        mModel.doAvatar(builder.build(), new BaseObserver<ResponseBody>(this, mvpView, false) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    BaseBean baseBean = parseData(data.string());
                    if (baseBean.code == Constant.REQUEST_OK) {
                        mvpView.onSuccess(Utils.getString(R.string.upload_success));
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
    protected ModifyPersonInfoModelImpl createModel() {
        return new ModifyPersonInfoModelImpl();
    }
}
