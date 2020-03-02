package com.geli.m.mvp.home.other.accountperiod_activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.api.Api;
import com.geli.m.api.ImageApiEngine;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.ShopAPDetailBean;
import com.geli.m.bean.UserAPDetailBean;
import com.geli.m.config.Constant;
import com.geli.m.dialog.AgreementDialog;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.other.FileDisplayActivity;
import com.geli.m.mvp.home.other.WebViewActivity;
import com.geli.m.utils.FilesUtils;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.LogUtils;
import com.geli.m.utils.ResourceUtil;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.ResponseBody;

import static android.view.View.generateViewId;
import static com.geli.m.BuildConfig.GL_IMAGE_URL;
import static com.geli.m.config.Constant.AP_STATUS;
import static com.geli.m.config.Constant.AP_Status_AllowablePay;
import static com.geli.m.config.Constant.AP_Status_AlreadyOpened;
import static com.geli.m.config.Constant.INTENT_BEAN;
import static com.geli.m.config.Constant.INTENT_DAY;
import static com.geli.m.config.Constant.INTENT_LINKS;
import static com.geli.m.config.Constant.INTENT_LOCAL_FILE;
import static com.geli.m.config.Constant.INTENT_SHOP_ID;
import static com.geli.m.config.Constant.INTENT_TITLE;

/**
 * author:  shen
 * date:    2018/11/3
 * <p>
 * 申请开通账期的界面
 */
public class AccountPeriodActivity extends MVPActivity<AccountPeriodPresentImpl> implements AccountPeriodView {

    public static int AP_ResultCode = 10000;

    public static int Get_File_RequestCode = 1001;
    public static int Check_File_RequestCode = 1002;

    @BindView(R.id.rl_title_rootlayout)
    RelativeLayout mRlTitleRootlayout;
    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title_name)
    TextView mTvTitleName;
    @BindView(R.id.tv_title_right)
    TextView mTvTitleRight;

    @BindView(R.id.tv_toast_ap)
    TextView mTvToast;

    @BindView(R.id.tv_shop_name_ap)
    TextView mTvShopName;
    @BindView(R.id.iv_shop_image_ap)
    ImageView mIvShopImage;

    /**
     * 申请账期的天数
     */
    @BindView(R.id.rg_accountperiod)
    MyRadioGroup mRg;

    /**
     * 在订单发货 xxx 天内结算
     */
    @BindView(R.id.tv_message_ap)
    TextView mTvMessage;
    /**
     * 包裹额度的编辑框
     */
    @BindView(R.id.layout_quota_ap)
    LinearLayout mLayoutQuota;
    /**
     * 额度
     */
    @BindView(R.id.et_quota_ap)
    EditText mEtQuota;
    /**
     * 最高可调至%s元（调整以1000为单位）
     */
    @BindView(R.id.tv_max_quota_ap)
    TextView mTvMaxQuota;
    /**
     * 下载合同
     */
    @BindView(R.id.btn_down_agreement_ap)
    Button mBtnDownAgreement;
    /**
     * 上传合同
     */
    @BindView(R.id.btn_upload_agreement_ap)
    Button mBtnUploadAgreement;
    /**
     * 上传合同完成后 -- 的布局
     */
    @BindView(R.id.lyout_upload_agreement_finish_ap)
    RelativeLayout mUploadFinishLayout;
    /**
     * 上传合同完成后 -- 文本
     */
    @BindView(R.id.tv_upload_agreement_finish_ap)
    TextView mTvUploadFinish;
    /**
     * 上传合同完成后 -- 删除按钮
     */
    @BindView(R.id.btn_upload_agreement_finish_delete_ap)
    Button mBtnUploadDelete;


    /**
     * 操作流程
     */
    @BindView(R.id.tv_operation_process_ap)
    TextView mTvOperationProcess;
    /**
     * 立即开通/提交
     */
    @BindView(R.id.btn_openup_or_submit_ap)
    Button mBtnOpenUpOrSubmit;


    ShopAPDetailBean.DataBean mShopAPDetailBean;

    /**
     * 账期天数 -- 默认值3
     */
    int mDay = -1;
    /**
     * 账期状态 -- 默认：店铺已经开通账期(可申请)
     */
    int mAccountPeriodState = AP_Status_AlreadyOpened;
    /**
     * 商店id
     */
    int mShopId = -1;

    /**
     * 选择的合同文件地址
     */
    String mTreatyPath = "";
    /**
     * 第一个选择框
     */
    RadioButton mFirstRadioButton;
    UserAPDetailBean.DataBean mUserAPDetailBean;

    @Override
    protected AccountPeriodPresentImpl createPresenter() {
        return new AccountPeriodPresentImpl(this);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_accountperiod;
    }

    @Override
    protected void initData() {
        getIntentExtra();
        initView();
        initRg();

        mPresenter.getShopShDetail(mShopId + "");
    }

    @Override
    protected void initEvent() {

    }


    private void getIntentExtra() {
        Intent intent = getIntent();
        mAccountPeriodState = intent.getIntExtra(AP_STATUS, AP_Status_AlreadyOpened);
        mShopId = intent.getIntExtra(INTENT_SHOP_ID, -1);
        mUserAPDetailBean = intent.getParcelableExtra(INTENT_BEAN);

        LogUtils.i("mShopId:" + mShopId);
    }

    private void initView() {

        mTvTitleName.setText(getString(R.string.account_purchase));
        mBtnOpenUpOrSubmit.setEnabled(false);

        switch (mAccountPeriodState) {
            case AP_Status_AlreadyOpened:         /* 店铺已经开通账期(可申请) */
                mLayoutQuota.setVisibility(View.GONE);
                //mTvToast.setVisibility(View.GONE);
                mBtnOpenUpOrSubmit.setText(getString(R.string.open_up_immediately));
                break;


            case AP_Status_AllowablePay:          /* 用户可以账期支付 -- 提升额度 */
                mLayoutQuota.setVisibility(View.VISIBLE);
                //mTvToast.setVisibility(View.VISIBLE);
                mBtnOpenUpOrSubmit.setText(getString(R.string.submit));
                setEtQuota();
                break;
        }
    }

    /**
     * 设置 -- 额度编辑框
     */
    private void setEtQuota() {

        if (mUserAPDetailBean != null && StringUtils.isNotEmpty(mUserAPDetailBean.getAmount())) {
            float amount = Float.valueOf(mUserAPDetailBean.getAmount());
            if (amount >= 1000) {
                mEtQuota.setText(((int) (amount / 1000)) + "");
                mEtQuota.setSelection(mEtQuota.getText().length());
            } else {
                mEtQuota.setText("");
            }
        } else {
            mEtQuota.setText("");
        }


        mEtQuota.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().equals("0")) {
                    mEtQuota.setText("");

                } else if (StringUtils.isNotEmpty(s.toString().trim())) {                                         /* 超过上额 */
                    if (mShopAPDetailBean != null && mUserAPDetailBean != null) {
                        float value = Float.valueOf(s.toString().trim()) * 1000;
                        float max = Float.valueOf(mShopAPDetailBean.getMax());
                        if (value > max) {
                            float amount = Float.valueOf(mUserAPDetailBean.getAmount());
                            if (amount >= 1000) {
                                mEtQuota.setText(((int) (amount / 1000)) + "");
                                mEtQuota.setSelection(mEtQuota.getText().length());
                            } else {
                                mEtQuota.setText("");
                            }
                            ToastUtils.showToast(Utils.getString(R.string.max_quota_message, mShopAPDetailBean.getMax()));
                        }

                        if (mAccountPeriodState == AP_Status_AllowablePay) {
                            if (StringUtils.isNotEmpty(mTreatyPath) &&
                                    StringUtils.isNotEmpty(mEtQuota.getText().toString().trim())) {
                                mBtnOpenUpOrSubmit.setEnabled(true);
                            } else {
                                mBtnOpenUpOrSubmit.setEnabled(false);
                            }
                        }
                    }
                }
            }
        });
    }

    private void initRg() {
        mRg.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) AccountPeriodActivity.this.findViewById(checkedId);
                if (rb != null) {
                    mDay = (int) (rb.getTag());
                    mTvMessage.setText(Utils.getString(R.string.settlement_within_xxx_days_of_order_delivery, mDay));
                }
            }
        });
    }

    @OnClick({R.id.iv_title_back,
            R.id.btn_down_agreement_ap,
            R.id.btn_upload_agreement_ap,
            R.id.btn_upload_agreement_finish_delete_ap,
            R.id.tv_operation_process_ap,
            R.id.btn_openup_or_submit_ap})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.iv_title_back:                                    /* 返回 */
                finish();
                break;

            case R.id.btn_down_agreement_ap:                            /* 下载合同 */
                down();
                break;

            case R.id.btn_upload_agreement_ap:                          /* 上传合同 */
                openFileExplorer();
                break;

            case R.id.btn_upload_agreement_finish_delete_ap:            /* 删除选中的上传图片 */
                setBtnUploadAgreement("");
                break;

            case R.id.tv_operation_process_ap:                          /* 操作流程 */
                process();
                break;

            case R.id.btn_openup_or_submit_ap:                          /* 立即开通/提交 */
                openUpOrSubmit();
                break;
        }
    }

    /**
     * 下载合同
     */
    private void down() {
        if (mShopAPDetailBean != null && StringUtils.isNotEmpty(mShopAPDetailBean.getTreaty_url())) {

            //String filename = getString(R.string.contract_opening_contract);

            String extensionName = FilesUtils.getExtensionName(mShopAPDetailBean.getTreaty_url());
            String filename = getString(R.string.contract_opening_contract) + "." + extensionName;

            if (mBtnDownAgreement.getText().toString().trim().equals(getString(R.string.check))) {

                Intent intent = new Intent();
                intent.putExtra(INTENT_LOCAL_FILE, Constant.DownAgreementDir + filename);
                intent.putExtra(INTENT_LINKS, GL_IMAGE_URL + mShopAPDetailBean.getTreaty_url());
                startActivityForResult(FileDisplayActivity.class, intent, Check_File_RequestCode);

            } else {
                File file = new File(Constant.DownAgreementDir);
                if (file.exists()) {

                    String url = mShopAPDetailBean.getTreaty_url();
                    requestPermissions(mContext, url, Constant.DownAgreementDir + filename);

                } else {
                    File fileTemp = FilesUtils.createDir(Constant.DownAgreementDir);
                    if (fileTemp.exists()) {
                        String url = mShopAPDetailBean.getTreaty_url();
                        requestPermissions(mContext, url, Constant.DownAgreementDir + filename);
                    }
                }
            }
        }
    }

    /**
     * 获取下权限 -- 再下载
     *
     * @param context
     * @param url
     * @param targetPath
     */
    public void requestPermissions(final Context context, final String url, final String targetPath) {
        RxPermissions rxPermissions = new RxPermissions((BaseActivity) context);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            downFile(url, targetPath);
                        } else {
                            Utils.showMissPermissionDialog(context, Utils.getString(R.string.read_write),
                                    Utils.getString(R.string.downloading_contract1));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 保存文件
     *
     * @param url
     * @param targetPath
     */
    private void downFile(String url, final String targetPath) {

        Api mApiService = ImageApiEngine.getInstance().getApiService();
        // Observable<ResponseBody> observable = mApiService.universal(url); // 使用post 会报 405 。。。
        Observable<ResponseBody> observable = mApiService.getDownAgreement(url);

        mBtnDownAgreement.setText("正在下载...");
        mBtnDownAgreement.setEnabled(false);
        observable.subscribeOn(Schedulers.newThread())      // 在新线程中实现该方法
                .map(new Function<ResponseBody, String>() {
                    @Override
                    public String apply(ResponseBody responseBody) throws Exception {
                        FilesUtils.InputStream2File(responseBody.byteStream(), targetPath);
                        return targetPath;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        LogUtils.i("downFile -- 下载了:" + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.i("downFile -- error:", e);
                        mBtnDownAgreement.setText(getString(R.string.contract_opening_contract));
                        mBtnDownAgreement.setEnabled(true);
                        ToastUtils.showToast("下载失败");

                    }

                    @Override
                    public void onComplete() {
                        // mBtnDownAgreement.setText(getString(R.string.contract_opening_contract));
                        mBtnDownAgreement.setCompoundDrawables(null, null, null, null);
                        mBtnDownAgreement.setText(getString(R.string.check));
                        mBtnDownAgreement.setEnabled(true);
                        ToastUtils.showToast("下载完成\n" + "文件存放地址为：\n" + targetPath);
                    }
                });
    }


    /**
     * 打开系统的 文件管理
     */
    public void openFileExplorer() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);        // 系统调用Action属性
        //intent.setType(“image/*”);//选择图片
        //intent.setType(“audio/*”); //选择音频
        //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
        //intent.setType(“video/*;image/*”);//同时选择视频和图片
        //intent.setType("*/*");//无类型限制
        intent.setType("*/*");                                      // 设置文件类型
        intent.addCategory(Intent.CATEGORY_OPENABLE);               // 添加Category属性
        try {
            startActivityForResult(intent, Get_File_RequestCode);
        } catch (Exception e) {
            ToastUtils.showToast("没有正确打开文件管理器");
        }
    }


    /**
     * 操作流程
     */
    private void process() {
        if (mShopAPDetailBean != null && StringUtils.isNotEmpty(mShopAPDetailBean.getProcess())) {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra(INTENT_TITLE, getString(R.string.operation_process));
            intent.putExtra(INTENT_LINKS, mShopAPDetailBean.getProcess());
            startActivity(intent);
        }
    }

    /**
     * "立即开通"或"提交"
     */
    private void openUpOrSubmit() {

        if (mDay == -1) {
            ToastUtils.showToast("请选择账期时间");
            return;
        }
        if (StringUtils.isEmpty(mTreatyPath)) {
            ToastUtils.showToast("请选择上传合同");
            return;
        }

        switch (mAccountPeriodState) {
            case AP_Status_AlreadyOpened:         /* 店铺已经开通账期(可申请)  --  "立即开通" */
                if (mShopAPDetailBean != null && StringUtils.isNotEmpty(mShopAPDetailBean.getAgreement())) {
                    AgreementDialog dialog = AgreementDialog.newInstance(mShopAPDetailBean.getAgreement());
                    dialog.setOnclickListener(new AgreementDialog.ClickListenerInterface() {
                        @Override
                        public void doConfirm(AgreementDialog agreementDialog) {
                            openUpOrSubmit(mAccountPeriodState, "");
                            agreementDialog.dismiss();
                        }

                        @Override
                        public void doCancel() {

                        }
                    });
                    dialog.show(getSupportFragmentManager(), "AgreementDialog");
                }
                break;

            case AP_Status_AllowablePay:          /* 用户可以账期支付 -- 提升额度 -- "提交" */

                String quota = mEtQuota.getText().toString().trim();
                if (StringUtils.isEmpty(quota)) {
                    ToastUtils.showToast("请输入金额");
                    return;
                }

                int amount = Integer.valueOf(quota);
                String quotaTemp = amount * 1000 + "";
                openUpOrSubmit(mAccountPeriodState, quotaTemp);
                break;
        }
    }

    /**
     * 开通账期
     */
    private void openUpOrSubmit(int state, String quota) {
        switch (state) {
            case AP_Status_AlreadyOpened:         /* 店铺已经开通账期(可申请)  --  "立即开通" */
                mPresenter.shApply(GlobalData.getUser_id() + "", mShopId + "",
                        mDay + "", "1", "0", mTreatyPath);
                break;

            case AP_Status_AllowablePay:          /* 用户可以账期支付 -- 提升额度 -- "提交" */
                mPresenter.shApply(GlobalData.getUser_id() + "", mShopId + "",
                        mDay + "", "2", quota, mTreatyPath);

                break;
        }
    }


    /* 商店账期详情 -- 设置界面 */
    @Override
    public void showShopShDetail(ShopAPDetailBean.DataBean data) {
        // LogUtils.printJson(new Gson().toJson(data), "ShopAPDetailBean.DataBean");
        mShopAPDetailBean = data;
        setShopInfo(data);
        setRg(data);

        mTvMaxQuota.setText(Utils.getString(R.string.max_quota_message, mShopAPDetailBean.getMax()));
    }

    @Override
    public void shApplySuccess() {
        Intent intent = new Intent();
        intent.putExtra(INTENT_DAY, mDay);
        setResult(AP_ResultCode, intent);
        finish();
    }

    /**
     * 设置商店图片 和 商店名字
     *
     * @param bean
     */
    public void setShopInfo(ShopAPDetailBean.DataBean bean) {
        GlideUtils.loadImg(mContext, bean.getShop_img_thumb(), mIvShopImage);
        mTvShopName.setText(bean.getShop_name());
    }

    /**
     * 添加 单选框组 的控件
     *
     * @param bean
     */
    public void setRg(ShopAPDetailBean.DataBean bean) {

        List<LinearLayout> layoutList = new ArrayList<>();
        LinearLayout tempLayout = null;
        if (bean.getSh_detail() != null) {                            // 账期天数

            setLayoutAndRb(bean, layoutList, tempLayout);

            if (layoutList.size() > 0) {
                for (LinearLayout layout : layoutList) {
                    mRg.addView(layout);
                }
                setAlreadyOpened();     // 如果是申请开通 -- 设置默认选中第一个为天数
            }
        }
    }

    /**
     * 如果是申请开通 -- 设置默认选中第一个为天数
     * 提升的设置给定的
     */
    private void setAlreadyOpened() {
        if (mAccountPeriodState == AP_Status_AlreadyOpened) {     /* 申请开通 */
            if (mFirstRadioButton != null) {                     // 默认选中
                setDefRB(mFirstRadioButton);
            }
        } else if (mAccountPeriodState == AP_Status_AllowablePay) {    /* 提升 */
            if (mUserAPDetailBean != null) {
                for (Map.Entry<Integer, RadioButton> entry : mRadioButtonMap.entrySet()) {
                    if (entry.getKey().intValue() == mUserAPDetailBean.getSh_day()) {    // 已开通的天数
                        setDefRB(entry.getValue());
                        break;
                    }
                }
            }
        }
    }

    /**
     * 默认选中
     *
     * @param rb
     */
    private void setDefRB(RadioButton rb) {
        mRg.check(rb.getId());
        mDay = (int) rb.getTag();
        mTvMessage.setText(Utils.getString(R.string.settlement_within_xxx_days_of_order_delivery, mDay));
    }


    Map<Integer, RadioButton> mRadioButtonMap = new HashMap<>();

    /**
     * 每四个单选框为一行
     *
     * @param bean
     * @param layoutList
     * @param tempLayout
     * @return
     */
    private void setLayoutAndRb(ShopAPDetailBean.DataBean bean, List<LinearLayout> layoutList, LinearLayout tempLayout) {
        int index = 0;
        RadioButton rb = null;
        for (Integer integer : bean.getSh_detail()) {
            int intValue = integer.intValue();
            if ((index % 4) == 0) {
                tempLayout = new LinearLayout(this);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, Utils.dip2px(mContext, 5),
                        0, Utils.dip2px(mContext, 5));
                tempLayout.setLayoutParams(layoutParams);
                rb = setRb(tempLayout, intValue);
                if (index == 0) {
                    mFirstRadioButton = rb;
                }
                layoutList.add(tempLayout);
            } else {
                rb = setRb(tempLayout, intValue);
            }

            if (rb != null) {
                mRadioButtonMap.put((int) rb.getTag(), rb);
            }
            index++;
        }
    }

    /**
     * 单选框的样式
     *
     * @param tempLayout
     * @param intValue
     * @return
     */
    @NonNull
    private RadioButton setRb(LinearLayout tempLayout, int intValue) {
        RadioButton rb = new RadioButton(this);
        rb.setId(generateViewId());
        rb.setTag(intValue);
        rb.setText(intValue + "天");
        rb.setTextAppearance(this, R.style.radiobutton_day); // 设置样式

        float width = (float) mRg.getWidth();
        // 将 mRg的框按某个比例来设计
        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams((int) ((width / 24) * 5),
                RadioGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins((int) (width / 48), 0, (int) (width / 48), 0);
        rb.setLayoutParams(layoutParams);

        rb.setTextSize(12);
        rb.setButtonDrawable(android.R.color.transparent);//隐藏单选圆形按钮
        rb.setGravity(Gravity.CENTER);
        rb.setPadding(Utils.dip2px(mContext, 12), Utils.dip2px(mContext, 7),
                Utils.dip2px(mContext, 12), Utils.dip2px(mContext, 7));
        rb.setBackgroundResource(ResourceUtil.getDrawableResIDByName(mContext, "selector_btn_bg_transparent_red"));
        tempLayout.addView(rb);
        return rb;
    }


    /* 获取文件返回 -- 或处理下载问题 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Get_File_RequestCode) {
            getFileRequest(resultCode, data);
        } else if (requestCode == Check_File_RequestCode) {

        }
    }

    @Override
    public void showLoading() {
        Utils.uProgressDialog(mContext);
    }

    @Override
    public void hideLoading() {
        Utils.dismissProgress();
    }

    /**
     * 获取文件路径后 -- 处理
     *
     * @param resultCode
     * @param data
     */
    private void getFileRequest(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if ("file".equalsIgnoreCase(uri.getScheme())) {              /* 使用第三方应用打开 */
                mTreatyPath = uri.getPath();
                setBtnUploadAgreement(mTreatyPath);
                return;
            }

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {   /* 4.4以后 */
                mTreatyPath = FilesUtils.getPath(this, uri);
            } else {                                                    /* 4.4以下下系统调用方法 */
                mTreatyPath = FilesUtils.getRealPathFromURI(mContext, uri);
            }
            setBtnUploadAgreement(mTreatyPath);         // 设置上传按钮
        }
    }


    /**
     * 设置上传按钮
     *
     * @param treatyPath 要上传的文件路径
     */
    private void setBtnUploadAgreement(String treatyPath) {
        if (StringUtils.isNotEmpty(treatyPath)) {
            mBtnUploadAgreement.setVisibility(View.GONE);
            mUploadFinishLayout.setVisibility(View.VISIBLE);
            mTvUploadFinish.setText(FilesUtils.getFileName(treatyPath));
            if (mAccountPeriodState == AP_Status_AlreadyOpened) {
                mBtnOpenUpOrSubmit.setEnabled(true);

            } else if (mAccountPeriodState == AP_Status_AllowablePay) {
                if (StringUtils.isNotEmpty(mEtQuota.getText().toString().trim())) {
                    mBtnOpenUpOrSubmit.setEnabled(true);
                } else {
                    mBtnOpenUpOrSubmit.setEnabled(false);
                }
            }

        } else {
            mBtnOpenUpOrSubmit.setEnabled(false);
            mTreatyPath = "";
            mBtnUploadAgreement.setVisibility(View.VISIBLE);
            mUploadFinishLayout.setVisibility(View.GONE);
            mTvUploadFinish.setText("");
        }
    }

}
