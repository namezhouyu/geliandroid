package com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicetype_activity.addoreditinvoice_activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import com.geli.m.R;
import com.geli.m.bean.BaseBean;
import com.geli.m.bean.InvoiceBean;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.mvp.base.BaseView;
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

import static com.geli.m.BuildConfig.GL_IMAGE_URL;
import static com.geli.m.config.Constant.INVOICE_ELECTRONIC;
import static com.geli.m.config.Constant.INVOICE_PERSONAL;
import static com.geli.m.config.Constant.INVOICE_UNIT;
import static com.geli.m.config.Constant.INVOICE_UNIT_COMMON;
import static com.geli.m.config.Constant.INVOICE_UNIT_SPECIAL;

/**
 * Created by Administrator on 2017/11/18.
 */

public class AddOrEditInvoicePresenterImpl extends BasePresenter<BaseView, AddOrEditInvoiceModelImpl> {

    @Override
    protected AddOrEditInvoiceModelImpl createModel() {
        return new AddOrEditInvoiceModelImpl();
    }

    public AddOrEditInvoicePresenterImpl(BaseView mvpView) {
        super(mvpView);
    }

    public void addOrEditInvoice(String url, final InvoiceBean.DataEntity entity, List<LocalMedia> selectList) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (entity.getBelong() == INVOICE_UNIT) {                /* 单位发票 */
            setBelongUnit(entity, selectList, builder);
        } else if (entity.getBelong() == INVOICE_PERSONAL) {     /* 个人发票 */
            setBelongPersonal(entity, builder);
        }

        builder.addFormDataPart("user_id", entity.getUser_id());
        if (entity.isEditInvoice) {                                 // 如果是修改,需要添加invoice_id参数
            builder.addFormDataPart("invoice_id", entity.getInvoice_id() + "");
        }


        mModel.addOrEditInvoice(url, builder.build(), new BaseObserver<ResponseBody>(this, mvpView) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    BaseBean baseBean = parseData(data.string());
                    if (baseBean.code == 100) {
                        if (entity.isEditInvoice) {         /* 修改*/
                            mvpView.onSuccess(Utils.getString(R.string.message_modifysuccess));
                        } else {                            /* 新增 */
                            mvpView.onSuccess(Utils.getString(R.string.message_addsuccess));
                        }
                    } else {
                        if (entity.isEditInvoice) {         /* 修改 */
                            mvpView.onError(Utils.getString(R.string.message_modifyfailure));
                        } else {                            /* 新增 */
                            mvpView.onError(Utils.getString(R.string.message_addfailure));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mvpView.onError(parseError(e));
                }
            }

        });
    }


    /**
     * 设置 -- 修改/添加"单位发票"的参数
     * @param entity
     * @param selectList
     * @param builder
     */
    private void setBelongUnit(InvoiceBean.DataEntity entity, List<LocalMedia> selectList, MultipartBody.Builder builder) {

        builder.addFormDataPart("name", entity.getName())
                .addFormDataPart("belong", entity.getBelong() + "")
                .addFormDataPart("type", entity.getType() + "")
                .addFormDataPart("invoice_type", entity.getInvoice_type() + "")
                .addFormDataPart("duty_paragraph", entity.getDuty_paragraph())
                .addFormDataPart("address", entity.getAddress());

        if (entity.getType() == INVOICE_UNIT_SPECIAL) {                /* 单位专用 */
            builder.addFormDataPart("account_name", entity.getAccount_name())
                    .addFormDataPart("account", entity.getAccount())
                    .addFormDataPart("code", entity.getCode())
                    .addFormDataPart("tel", entity.getTel());

        } else if (entity.getType() == INVOICE_UNIT_COMMON) {         /* 单位普通 */
            if (entity.getInvoice_type() == INVOICE_ELECTRONIC) {    // 电子
                builder.addFormDataPart("email", entity.getEmail());
            }
        }

        setInvoicePhoto(selectList, builder);       // 设置发票图片参数
    }

    /**
     * 设置发票图片参数
     * @param selectList
     * @param builder
     */
    private void setInvoicePhoto(List<LocalMedia> selectList, MultipartBody.Builder builder) {
        String license = "";
        int i = 0;
        if (selectList != null) {
            FileInputStream fis = null;
            String path;
            for (LocalMedia model : selectList) {
                path = model.getPath();
                if (path.startsWith(GL_IMAGE_URL)) {
                    license += path.substring(GL_IMAGE_URL.length(), path.length()) + ";";
                    continue;
                }
                path = model.getCompressPath();
                i++;
                try {
                    fis = new FileInputStream(path);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap bm = BitmapFactory.decodeStream(fis);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                builder.addFormDataPart("photo" + i, path, RequestBody.create(MediaType.parse("image/jpeg"), byteArrayOutputStream.toByteArray()));
            }
        }

        if (!TextUtils.isEmpty(license)) {
            license = license.substring(0, license.length() - 1);
            builder.addFormDataPart("invoice", license);
        }
    }


    /**
     * 设置 -- 修改/添加"个人发票"的参数
     * @param entity
     * @param builder
     */
    private void setBelongPersonal(InvoiceBean.DataEntity entity, MultipartBody.Builder builder) {
        builder.addFormDataPart("name", entity.getName())
                .addFormDataPart("belong", entity.getBelong() + "")
                .addFormDataPart("invoice_type", entity.getInvoice_type() + "");

        if (entity.getInvoice_type() == INVOICE_ELECTRONIC) {       /* 电子 */
            builder.addFormDataPart("email", entity.getEmail());
        } else {                                                    /* 纸质 */
            builder.addFormDataPart("tel", entity.getTel());
        }
    }

}
