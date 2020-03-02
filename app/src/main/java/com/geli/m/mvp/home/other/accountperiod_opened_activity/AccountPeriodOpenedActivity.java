package com.geli.m.mvp.home.other.accountperiod_opened_activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.UserAPDetailBean;
import com.geli.m.dialog.TipDialog;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.mine_fragment.accountorder_activity.AccountOrderActivity;
import com.geli.m.mvp.home.mine_fragment.myorder_activity.MyOrderActivity;
import com.geli.m.mvp.home.other.accountperiod_activity.AccountPeriodActivity;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.TimeUtils;
import com.geli.m.utils.Utils;

import static com.geli.m.config.Constant.AP_OtherStatus_Cancel;
import static com.geli.m.config.Constant.AP_OtherStatus_NormalUse;
import static com.geli.m.config.Constant.AP_OtherStatus_Revising;
import static com.geli.m.config.Constant.AP_STATUS;
import static com.geli.m.config.Constant.AP_Status_AllowablePay;
import static com.geli.m.config.Constant.AP_UseStatus_BeOverdue;
import static com.geli.m.config.Constant.AP_UseStatus_InUse;
import static com.geli.m.config.Constant.AP_UseStatus_NotUsed;
import static com.geli.m.config.Constant.AP_UseStatus_TakeEffect;
import static com.geli.m.config.Constant.INTENT_AP_CLOSING_TIME;
import static com.geli.m.config.Constant.INTENT_BEAN;
import static com.geli.m.config.Constant.INTENT_DAY;
import static com.geli.m.config.Constant.INTENT_SHOP_ID;
import static com.geli.m.config.Constant.INTENT_SHOP_NAME;
import static com.geli.m.mvp.home.other.accountperiod_activity.AccountPeriodActivity.AP_ResultCode;

/**
 * author:  shen
 * date:    2018/11/7
 * <p>
 * 已开通账期界面了 -- 可以账期金额/账期天数
 */
public class AccountPeriodOpenedActivity extends MVPActivity<AccountPeriodOpenedPresentImpl>
        implements AccountPeriodOpenedView {

    public static int APO_ResultCode = 20000;

    @BindView(R.id.rl_title_rootlayout)
    RelativeLayout mRlTitleRootlayout;
    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title_name)
    TextView mTvTitleName;
    @BindView(R.id.tv_title_right)
    TextView mTvTitleRight;

    /**
     * 商店图片
     */
    @BindView(R.id.iv_shop_image_apo)
    ImageView mIvShopImage;
    /**
     * 商店名称
     */
    @BindView(R.id.tv_shop_name_apo)
    TextView mTvShopName;
    /**
     * 开通日期
     */
    @BindView(R.id.tv_open_days_apo)
    TextView mTvOpenDays;
    /**
     * 可用金额
     */
    @BindView(R.id.tv_amount_available_apo)
    TextView mTvAmountAvailable;
    /**
     * 结账日期
     */
    @BindView(R.id.tv_closing_date_apo)
    TextView mTvClosingDate;

    /**
     * 包裹"结账日期"的布局
     */
    @BindView(R.id.layout_closing_date_apo)
    LinearLayout mLayoutClosingDate;


    /**
     * 申请账期调整
     */
    @BindView(R.id.btn_adjustment)
    Button mBtnAdjustment;


    /**
     * 账期状态 -- 默认：用户可以账期支付
     */
    int mAccountPeriodState = AP_Status_AllowablePay;
    /**
     * 商店id
     */
    int mShopId = -1;

    UserAPDetailBean.DataBean mBean;
    private boolean isChange = false;

    @Override
    protected AccountPeriodOpenedPresentImpl createPresenter() {
        return new AccountPeriodOpenedPresentImpl(this);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_accountperiod_opened;
    }

    @Override
    protected void initData() {
        mTvTitleName.setText(getString(R.string.account_purchase));
        getIntentExtra();
        mPresenter.getUserShDetail(GlobalData.getUser_id(), mShopId + "");
    }

    @Override
    protected void initEvent() {

    }

    private void getIntentExtra() {
        Intent intent = getIntent();
        mAccountPeriodState = intent.getIntExtra(AP_STATUS, AP_Status_AllowablePay);
        mShopId = intent.getIntExtra(INTENT_SHOP_ID, -1);
    }

    @Override
    public void showUserShDetail(UserAPDetailBean.DataBean bean) {
        mBean = bean;

        GlideUtils.loadImg(mContext, bean.getShop_img_thumb(), mIvShopImage);
        mTvShopName.setText(bean.getShop_name());

        mTvOpenDays.setText(bean.getSh_day() + "天");
        setMoney(bean);
        setSh_status(bean);
        setStatus(bean);
    }

    private void setMoney(UserAPDetailBean.DataBean bean) {
        if (bean != null) {
            if (StringUtils.isNotEmpty(bean.getAmount()) && StringUtils.isNotEmpty(bean.getCost())) {
                double amount = Double.valueOf(bean.getAmount());
                double cost = Double.valueOf(bean.getCost());
                double tempMoney = 0;
                if (StringUtils.isNotEmpty(bean.getTemp_amount())) {
                    tempMoney = Double.valueOf(bean.getTemp_amount());
                }

                String money = Utils.getFormatDoubleTwoDecimalPlaces(amount - cost + tempMoney, 2);
                mTvAmountAvailable.setText(money);
            }
        }
    }


    /**
     * 根据账期状态设置控件
     *
     * @param bean
     */
    private void setSh_status(UserAPDetailBean.DataBean bean) {
        if (bean != null) {
            switch (mBean.getSh_status()) {
                case AP_UseStatus_InUse:            /* 账期使用中 */
                case AP_UseStatus_BeOverdue:        /* 账期逾期 */
                case AP_UseStatus_TakeEffect:       /* 账期开始生效(确认收货) */
                    mLayoutClosingDate.setVisibility(View.VISIBLE);
                    mTvClosingDate.setText(getStringTime(bean.getClosing_time()));
                    break;

                case AP_UseStatus_NotUsed:           /* 账期已归还(未使用账期) */
                    mLayoutClosingDate.setVisibility(View.GONE);
                    break;
            }
        }
    }

    /**
     * 设置按钮状态 根据  status
     *
     * @param bean
     */
    public void setStatus(UserAPDetailBean.DataBean bean) {
        if (bean != null) {
            switch (mBean.getStatus()) {

                case AP_OtherStatus_Cancel:              /* 取消账期权限 */
                    mBtnAdjustment.setEnabled(false);
                    break;

                case AP_OtherStatus_NormalUse:           /* 开通权限 -- 正常使用 */
                    mBtnAdjustment.setEnabled(true);
                    break;

                case AP_OtherStatus_Revising:           /* 账期修改中 */
                    mBtnAdjustment.setEnabled(false);
                    mBtnAdjustment.setText(getString(R.string.to_be_audited));
                    break;
            }
        }
    }

    /**
     * 将时间戳转换成字符串
     *
     * @param time
     * @return
     */
    private String getStringTime(int time) {
        if (time > 0) {
            return TimeUtils.transForDate(time, TimeUtils.format10);
        }
        return "";
    }


    @OnClick({R.id.btn_adjustment, R.id.iv_title_back})
    public void clickView(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                if (isChange) {
                    setResult(APO_ResultCode);
                }
                finish();
                break;

            case R.id.btn_adjustment:
                setBtnAdjustmentClick();
                break;
        }
    }


    /**
     * 申请账期调整
     */
    private void setBtnAdjustmentClick() {
        if (mBean != null) {

            switch (mBean.getSh_status()) {
                case AP_UseStatus_InUse:           /* 使用账期付款(未确定收货) */
                    showTipDialogInUse(Utils.getString(R.string.being_processed), getString(R.string.go_check));
                    break;

                case AP_UseStatus_TakeEffect:      /* 账期开始生效(确认收货) */
                    showTipDialogInUse(Utils.getString(R.string.non_checkout), getString(R.string.go_settlement));
                    break;

                case AP_UseStatus_NotUsed:          /* 账期已归还(未使用账期) */
                    startActivityForResult(AccountPeriodActivity.class, new Intent()    // 去调整
                            .putExtra(AP_STATUS, mAccountPeriodState)
                            .putExtra(INTENT_SHOP_ID, mShopId)
                            .putExtra(INTENT_BEAN, mBean), 1);
                    break;

                case AP_UseStatus_BeOverdue:         /* 账期逾期 */
                    showTipDialogInUse(Utils.getString(R.string.be_overdue), getString(R.string.go_settlement));
                    break;
            }
        }
    }


    private void showTipDialogInUse(String src, String confirmSrc) {
        TipDialog dialog = TipDialog.newInstance(src);
        dialog.isShowTitle(false);
        dialog.setCancelSrc(getString(R.string.back));
        dialog.setConfirmSrc(confirmSrc);
        dialog.setConfirmTextColor(Utils.getColor(R.color.text_5de7a8));
        dialog.setConfirmTextColor(Utils.getColor(R.color.zhusediao));

        dialog.setOnclickListener(new TipDialog.ClickListenerInterface() {
            @Override
            public void doConfirm(TipDialog tipDialog) {
                switch (mBean.getSh_status()) {
                    case AP_UseStatus_InUse:           /* 使用账期付款(未确定收货) */
                        startActivity(MyOrderActivity.class, new Intent());
                        break;

                    case AP_UseStatus_TakeEffect:      /* 账期开始生效(确认收货) */
                    case AP_UseStatus_BeOverdue:         /* 账期逾期 */
                        startActivity(AccountOrderActivity.class,
                                new Intent().putExtra(INTENT_SHOP_ID, mShopId)
                                        .putExtra(INTENT_SHOP_NAME, mBean.getShop_name())
                                        .putExtra(INTENT_AP_CLOSING_TIME, mBean.getClosing_time()));
                        break;
                }

                tipDialog.dismiss();
            }

            @Override
            public void doCancel() {

            }
        });
        dialog.show(getSupportFragmentManager(), "TipDialogInUse");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AP_ResultCode) {
            int day = data.getIntExtra(INTENT_DAY, -1);
            if (day != -1) {
                mPresenter.getUserShDetail(GlobalData.getUser_id(), mShopId + "");
                showTipDialog(day);
                isChange = true;
            }
        }
    }

    private void showTipDialog(int day) {
        String src = Utils.getString(R.string.wait_for_the_result, day);
        TipDialog dialog = TipDialog.newInstance(src);
        dialog.isShowTitle(false);
        dialog.isShowCancel(false);
        dialog.setConfirmSrc("确定");
        dialog.setConfirmTextColor(Utils.getColor(R.color.zhusediao));

        dialog.setOnclickListener(new TipDialog.ClickListenerInterface() {
            @Override
            public void doConfirm(TipDialog tipDialog) {
                tipDialog.dismiss();
            }

            @Override
            public void doCancel() {

            }
        });
        dialog.show(getSupportFragmentManager(), "TipDialog");
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isChange) {
                setResult(APO_ResultCode);
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showLoading() {
        Utils.uProgressDialog(mContext);
    }

    @Override
    public void hideLoading() {
        Utils.dismissProgress();
    }
}
