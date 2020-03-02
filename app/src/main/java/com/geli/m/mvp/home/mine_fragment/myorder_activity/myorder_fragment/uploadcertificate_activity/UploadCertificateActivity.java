package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.uploadcertificate_activity;

import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.OfflinePaymentSuccess;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.mine_fragment.myorder_activity.MyOrderActivity;
import com.geli.m.select.SelectPhotoFragment;
import com.geli.m.utils.LogUtils;
import com.geli.m.utils.PriceUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import java.io.File;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.geli.m.config.Constant.INTENT_ORDER_SN;
import static com.geli.m.config.Constant.ORDER_PRICE;

/**
 * Created by Steam_l on 2018/1/27.
 *
 * 线下支付 -- 上传凭证
 */
public class UploadCertificateActivity extends MVPActivity<UploadTransferPresenterImpl> implements View.OnClickListener, UploadTransferView {

    /** 标题 */
    @BindView(R.id.tv_title_name)
    TextView mTvTitle;
    /** 金额 */
    @BindView(R.id.tv_uploadcertificate_price)
    TextView mTvPrice;
    /** 银行 */
    @BindView(R.id.tv_uploadcertficate_unit_bank_value)
    TextView mTvBank;
    /** 开户账号 */
    @BindView(R.id.tv_uploadcertficate_unit_account_value)
    TextView mTvAccount;
    /** 开户名称 */
    @BindView(R.id.tv_uploadcertficate_unit_name_value)
    TextView mTvUnitName;

    private SelectPhotoFragment mSelectPhotoFragment;
    private String order_sn;
    private String order_price;

    @Override
    protected int getResId() {
        return R.layout.activity_uploadcertificate;
    }

    @Override
    protected void init() {
        super.init();
        order_sn = getIntent().getStringExtra(INTENT_ORDER_SN);
        order_price = getIntent().getStringExtra(ORDER_PRICE);

        LogUtils.i("order_price:" +  order_price);
    }

    @Override
    protected void initData() {
        setView();
        mPresenter.getBankInfo(GlobalData.getUser_id());
    }

    @Override
    protected void initEvent() {

    }

    private void setView() {
        String resources = Utils.getString(R.string.amounts_payable);
        String price = PriceUtils.getPrice(order_price);
        SpannableString spannableString = new SpannableString(resources + price);
        spannableString.setSpan(new ForegroundColorSpan(Utils.getColor(R.color.text_color)), 0, resources.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Utils.getColor(R.color.zhusediao)), resources.length(), spannableString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTvPrice.setText(spannableString);
        mTvTitle.setText(Utils.getString(R.string.title_uploadcertificate));

        // 选择图片
        mSelectPhotoFragment = SelectPhotoFragment.newInstance(1, 1, 0);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_uploadcertificate_content, mSelectPhotoFragment).commit();

    }

    /**
     * 上传凭证
     */
    public void uploadTransferProof() {
        List<LocalMedia> photoModelList = mSelectPhotoFragment.getPhotoModelList();
        if (photoModelList.size() <= 0) {
            ToastUtils.showToast(Utils.getString(R.string.please_select_photo));
            return;
        }
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("order_sn", order_sn)
                .addFormDataPart("user_id", GlobalData.getUser_id());
        int i = 0;
        for (LocalMedia file : photoModelList) {
            i++;
            builder.addFormDataPart("photo" + i, file.getCompressPath(), RequestBody.create(MediaType.parse("image/*"), new File(file.getCompressPath())));
        }
        MultipartBody build = builder.build();
        mPresenter.uploadTransfer(build);
    }

    @OnClick({R.id.bt_uploadcertificate_submit, R.id.iv_title_back, R.id.tv_uploadcertificate_phone})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_uploadcertificate_submit:                  /* 上传凭证 */
                uploadTransferProof();
                break;

            case R.id.iv_title_back:
                finish();
                break;

            case R.id.tv_uploadcertificate_phone:                   /* 打电话 */
                Utils.callPhone(mContext, "020-81338860");
                break;

            default:
                break;
        }
    }

    @Override
    protected UploadTransferPresenterImpl createPresenter() {
        return new UploadTransferPresenterImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ToastUtils.showToast(msg);
        PictureFileUtils.deleteCacheDirFile(mContext);
        startActivity(MyOrderActivity.class, new Intent());
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void showLoading() {
        Utils.uProgressDialog(mContext);
    }

    @Override
    public void hideLoading() {
        Utils.dismissProgress();
    }

    @Override
    public void showBankInfo(OfflinePaymentSuccess data) {
        mTvUnitName.setText(data.getShop_name());
        mTvAccount.setText(data.getBank_account());
        mTvBank.setText(data.getCompany_address());
    }

    @Override
    public void showUploadReulst(OfflinePaymentSuccess data) {

    }
}
