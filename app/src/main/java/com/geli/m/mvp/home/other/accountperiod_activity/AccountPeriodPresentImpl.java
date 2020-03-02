package com.geli.m.mvp.home.other.accountperiod_activity;

import com.geli.m.bean.BaseBean;
import com.geli.m.bean.ShopAPDetailBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * author:  shen
 * date:    2018/11/3
 */
public class AccountPeriodPresentImpl extends BasePresenter<AccountPeriodView, AccountPeriodModelImpl> {
    public AccountPeriodPresentImpl(AccountPeriodView mvpView) {
        super(mvpView);
    }

    @Override
    protected AccountPeriodModelImpl createModel() {
        return new AccountPeriodModelImpl();
    }


    /* 商店账期详情 */
    public void getShopShDetail(String shop_id) {
        mModel.getShopShDetail(shop_id, new BaseObserver<ShopAPDetailBean>(this, mvpView) {
            @Override
            protected void onSuccess(ShopAPDetailBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.showShopShDetail(data.getData());
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }


    /**
     * 账期申请
     * @param user_id
     * @param shop_id           商店Id
     * @param sh_day            申请天数
     * @param apply_type        申请类型，1开通，2修改额度
     * @param amount            额度，修改额度的时候使用
     * @param filePath          上传的文件，name值为"file"
     */
    public void shApply(String user_id, String shop_id, String sh_day, String apply_type, String amount, String filePath) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        File file = new File(filePath);
        builder.addFormDataPart("file", file.getName(),
                RequestBody.create(MediaType.parse("multipart/form-data"), file));

        builder.addFormDataPart("user_id", user_id);
        builder.addFormDataPart("shop_id", shop_id);
        builder.addFormDataPart("sh_day", sh_day);
        builder.addFormDataPart("apply_type", apply_type);
        builder.addFormDataPart("amount", amount);

        mModel.shApply(builder.build(), new BaseObserver<ResponseBody>(this, mvpView) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    BaseBean baseBean = parseData(data.string());
                    if (baseBean.code == Constant.REQUEST_OK) {
                        mvpView.onSuccess(baseBean.message);
                        mvpView.shApplySuccess();
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
