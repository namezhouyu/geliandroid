package com.geli.m.mvp.home.other.pay_activity;

import android.content.Intent;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.BuildConfig;
import com.geli.m.R;
import com.geli.m.api.UrlSet;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.BalanceBean;
import com.geli.m.bean.FwqTimeBean;
import com.geli.m.bean.SubmitOrderNewBean;
import com.geli.m.bean.TransferBean;
import com.geli.m.bean.WeChatPayBean;
import com.geli.m.bean.base.EmptyBean;
import com.geli.m.config.Constant;
import com.geli.m.dialog.InputPassDialog;
import com.geli.m.dialog.TipDialog;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.mine_fragment.myorder_activity.MyOrderActivity;
import com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.uploadcertificate_activity.UploadCertificateActivity;
import com.geli.m.mvp.home.other.WebViewActivity;
import com.geli.m.mvp.home.other.accountperiod_activity.MyRadioGroup;
import com.geli.m.mvp.home.other.transferaccounts_activity.TransferAccountsActivity;
import com.geli.m.pay.alipay.AliPay;
import com.geli.m.utils.LogUtils;
import com.geli.m.utils.RsaUtils;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.ToFormatUtil;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.geli.m.wxapi.WXPayEntryActivity;
import com.geli.m.wxapi.WeChatPay;
import com.google.gson.Gson;
import java.net.URLEncoder;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * author:  shen
 * date:    2018/11/15
 */
public class PayActivity extends MVPActivity<PayPresentImpl> implements PayView {


    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title_name)
    TextView mTvTitleName;
    @BindView(R.id.tv_title_right)
    TextView mTvTitleRight;
    @BindView(R.id.rl_title_rootlayout)
    RelativeLayout mRlTitleRootlayout;


    @BindView(R.id.tv_price_pay)
    TextView mTvPrice;

    /**
     * 提示 -- 合并订单无法使用转账支付
     */
    @BindView(R.id.tv_tip_pay)
    TextView mTvTip;

    /**
     * 申请账期的天数
     */
    @BindView(R.id.mrg_pay)
    MyRadioGroup mRg;
    @BindView(R.id.rb_paymethod_weixin_pay)
    RadioButton mRbWeixin;
    @BindView(R.id.rb_paymethod_alpay_pay)
    RadioButton mRbAlpay;
    @BindView(R.id.rb_paymethod_linedown_pay)
    RadioButton mRbLineDown;
    @BindView(R.id.rb_paymethod_geli_pay)
    RadioButton mRbGeli;
    @BindView(R.id.rb_paymethod_wallet_pay)
    RadioButton mRbWallet;
    @BindView(R.id.rb_paymethod_transferAccounts_pay)
    RadioButton mRbTransferAccounts;


    @BindView(R.id.btn_pay_pay)
    Button mBtnPayPay;

    public InputPassDialog mInputPassDialog;
    SubmitOrderNewBean.DataBean mBean;

    private int mPayType = Constant.Pay_Type_GeLi;

    /**
     * 是不是物流费
     */
    private boolean isLogistics = false;

    String mAmount = "0";
    int mCloseTime = 0;

    @Override
    protected PayPresentImpl createPresenter() {
        return new PayPresentImpl(this);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_pay;
    }

    @Override
    protected void initData() {
        mTvTitleName.setText(Utils.getString(R.string.settlement_1));
        getIntentData();

        if (mBean == null && mBean.getAmount() == 0 &&
                StringUtils.isEmpty(mBean.getOrder_sn())) {
            return;
        }

        setView(mBean);
        initMyRG();
    }

    private void getIntentData() {
        mBean = getIntent().getParcelableExtra(Constant.INTENT_BEAN);
        mCloseTime = getIntent().getIntExtra(Constant.INTENT_AP_CLOSING_TIME, mCloseTime);
        isLogistics = getIntent().getBooleanExtra(Constant.INTENT_IS_LOGISTICS, false);
    }

    @Override
    protected void initEvent() {

    }

    public void setView(SubmitOrderNewBean.DataBean entity) {
        if (entity != null) {
            mAmount = Utils.getFormatDoubleTwoDecimalPlaces(entity.getAmount(), 2);
            mTvPrice.setText(Utils.getString(R.string.money_unit_1, mAmount));
        }
    }

    public void initMyRG() {
        mRg.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                mTvTip.setVisibility(View.INVISIBLE);
                switch (checkedId) {
                    case R.id.rb_paymethod_weixin_pay:
                        mPayType = Constant.Pay_Type_WeChat;
                        mBtnPayPay.setText(Utils.getString(R.string.immediate_payment));
                        break;

                    case R.id.rb_paymethod_alpay_pay:
                        mPayType = Constant.Pay_Type_AliPay;
                        mBtnPayPay.setText(Utils.getString(R.string.immediate_payment));
                        break;

                    case R.id.rb_paymethod_linedown_pay:
                        mPayType = Constant.Pay_Type_LineDown;
                        mBtnPayPay.setText(Utils.getString(R.string.next));
                        break;

                    case R.id.rb_paymethod_geli_pay:
                        mPayType = Constant.Pay_Type_GeLi;
                        mBtnPayPay.setText(Utils.getString(R.string.immediate_payment));
                        break;

                    case R.id.rb_paymethod_wallet_pay:
                        mPayType = Constant.Pay_Type_Wallet;
                        break;

                    case R.id.rb_paymethod_transferAccounts_pay:
                        mPayType = Constant.Pay_Type_TransferAccounts;
                        if (mBean.getOrder_sn().startsWith(Constant.Order_Type_MO)) {
                            mTvTip.setVisibility(View.VISIBLE);
                            mBtnPayPay.setText(Utils.getString(R.string.go_single_pay));
                        } else {
                            mBtnPayPay.setText(Utils.getString(R.string.next));
                        }
                        break;
                }
            }
        });

        mRg.check(R.id.rb_paymethod_geli_pay);          // 默认格利支付
    }


    @OnClick({R.id.iv_title_back, R.id.btn_pay_pay})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                setResult(10);
                finish();
                break;

            case R.id.btn_pay_pay:
                pay();
                break;
        }
    }

    /**
     * 支付
     */
    private void pay() {
        if (mBean == null) {
            ToastUtils.showToast("请返回重新支付");
            return;
        }

        switch (mPayType) {
            case Constant.Pay_Type_WeChat:
            case Constant.Pay_Type_AliPay:
                weChatOrAliPayOrGeLiPay(mBean.getOrder_sn(), mPayType + "", "");
                break;

            case Constant.Pay_Type_LineDown:
                xianXiaPay(mBean);
                break;

            case Constant.Pay_Type_GeLi:
                geLiPay(mBean.getOrder_sn());
                break;

            case Constant.Pay_Type_Wallet:
                break;

            case Constant.Pay_Type_TransferAccounts:
                if (mBean.getOrder_sn().startsWith(Constant.Order_Type_MO)) {
                    startActivity(MyOrderActivity.class, new Intent().putExtra(Constant.INTENT_POSITION, Constant.POSITION_PAY));
                } else {
                    mPresenter.transfer(GlobalData.getUser_id(), mBean.getOrder_sn());
                }
                break;
        }
    }

    /**
     * 微信/支付宝/格利 支付
     *
     * @param order_sn
     * @param pay_type
     */
    public void weChatOrAliPayOrGeLiPay(String order_sn, String pay_type, String pay_pwd) {
        if (isLogistics) {
            mPresenter.logisticsPay(GlobalData.getUser_id(), order_sn, pay_type, pay_pwd);
        } else {
            mPresenter.orderPayNew(GlobalData.getUser_id(), order_sn, pay_type, pay_pwd);
        }
    }

    /**
     * 格利支付
     *
     * @param order_sn
     */
    public void geLiPay(String order_sn) {
        mPresenter.isGeliPay(GlobalData.getUser_id(), order_sn);
    }

    /**
     * 线下支付
     *
     * @param entity
     */
    private void xianXiaPay(SubmitOrderNewBean.DataBean entity) {
        if (isLogistics) {
            ToastUtils.showToast(getString(R.string.logistics_fees_do_not_support_offline_payment));
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(Constant.INTENT_ORDER_SN, entity.getOrder_sn() + "");
        intent.putExtra(Constant.ORDER_PRICE, mBean.getAmount() + "");
        startActivity(UploadCertificateActivity.class, intent);
        finish();
    }

    /**
     * 微信支付
     *
     * @param res
     */
    private void weChatPay(String res) {
        WXPayEntryActivity.setPaySuccessIntent(new WXPayEntryActivity.WeChatPayListener() {
            @Override
            public void success() {
                goSuccessActivity();
            }

            @Override
            public void failed() {
                ToastUtils.showToast(Utils.getString(R.string.pay_failed));
            }

            @Override
            public void cancel() {
            }
        });

        WeChatPayBean offlinePayBean = new Gson().fromJson(res, WeChatPayBean.class);
        WeChatPayBean.DataBean.DatasBean datas = offlinePayBean.getData().getDatas();
        WeChatPay.pay(mContext, datas.getAppid(), datas.getPartnerid(),
                datas.getNoncestr(), datas.getPrepayid(),
                datas.getTimestamp(), datas.getPackageX(),
                datas.getSign());
    }

    /*-------------------------------------------------------------------------------------*/

    /**
     * 支付宝
     *
     * @param res
     */
    private void aliPay(String res) {
        String payData = null;
        try {
            payData = new JSONObject(res).getString("data");
            AliPay.pay(this, payData, new AliPay.AliPayListener() {
                @Override
                public void success() {
                    goSuccessActivity();
                }

                @Override
                public void failed() {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 微信/支付宝/格利 支付 -- 后台请求，获取值后再处理
     *
     * @param res      后台返回的 值
     * @param pay_type
     */
    @Override
    public void payResult(String res, String pay_type) {
        if (pay_type.equals(Constant.Pay_Type_WeChat + "")) {                /* 微信支付 */
            weChatPay(res);
        } else if (pay_type.equals(Constant.Pay_Type_AliPay + "")) {                                                    /* 支付宝支付 */
            aliPay(res);
        } else if (pay_type.equals(Constant.Pay_Type_GeLi + "")) {
            geLiPayResult(res);
        }
    }

    private void geLiPayResult(String res) {
        EmptyBean emptyBean = new Gson().fromJson(res, EmptyBean.class);
        if (emptyBean != null) {
            if (emptyBean.getCode() == 100) {
                ToastUtils.showToast(Utils.getString(R.string.pay_success));
                goSuccessActivity();
                if (mInputPassDialog != null) {
                    mInputPassDialog.dismiss();
                }
            } else {
                ToastUtils.showToast(emptyBean.getMessage());
            }
        } else {
            ToastUtils.showToast(Utils.getString(R.string.pay_failed));
        }
    }


    @Override
    public void isGeliPay(BalanceBean bean) {
        switch (bean.getCode()) {
            case Constant.GeLiPay_can_pay:                           /* 100：为可以格利支付 */
                determine(bean);
                break;

            case Constant.GeLiPay_Not_Open:                          /* 201：商家没有开通格利支付 */
                ToastUtils.showToast(getString(R.string.gelipay_not_open));
                break;

            case Constant.GeLiPay_No_Bindings:                       /* 202 ：没有绑定格利支付 */
                showTipDialog(bean.getCode(), "",
                        Utils.getString(R.string.gelipay_no_bindings),
                        Utils.getString(R.string.go_open));
                break;

            case Constant.GeLiPay_No_payment_password_was_set:       /* 203 ：没有设置支付密码 */
                showTipDialog(bean.getCode(), "",
                        Utils.getString(R.string.gelipay_no_payment_password_was_set),
                        Utils.getString(R.string.go_set));
                break;

            case Constant.GeLiPay_error:                             /* 209 ：格利支付异常 */
                ToastUtils.showToast(getString(R.string.gelipay_error));
                break;
        }
    }

    private void determine(BalanceBean bean) {
        // inputPass();

        if (bean.getData() != null) {
            String balance = bean.getData().getBalance();
            if (StringUtils.isNotEmpty(balance)) {
                double b = Double.valueOf(balance);
                if (mBean.getAmount() > b) {
                    String amount = Utils.getFormatDoubleTwoDecimalPlaces(b, 2);
                    showTipDialog(bean.getCode(), Utils.getString(R.string.not_sufficient_funds),
                            Utils.getString(R.string.balance_unit, amount),
                            Utils.getString(R.string.go_recharge));
                } else {
                    inputPass();
                }
            } else {
                inputPass();
            }
        } else {
            inputPass();
        }
    }

    @Override
    public void getFwqTimeSuccess(int code, FwqTimeBean data) {
        int time = data.getTime();

        String sp = "m";
        String jm = "1";            // 1格利钱包 2修改支付密码
        String dm = BuildConfig.GL_DOMAIN;
        String un = "";
        String tm = "";

        if (code == Constant.GeLiPay_No_Bindings) {
            jm = Constant.jm_Bindings;
        } else if (code == Constant.GeLiPay_No_payment_password_was_set) {
            jm = Constant.jm_set_password;
        }

        un = encode(GlobalData.getPhone());
        tm = encode(time + "");

        // 组合 链接
        String url = BuildConfig.GL_PAY_URL + UrlSet.specialLogin + "?" +
                "sp=" + sp + "&" +
                "jm=" + jm + "&" +
                "dm=" + dm + "&" +
                "un=" + un + "&" +
                "tm=" + tm;

        // LogUtils.i("url:" + url);
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra(Constant.INTENT_LINKS, url);
        intent.putExtra(Constant.INTENT_IS_SHOW_TITLE, false);
        startActivity(intent);
    }

    @Override
    public void getTransferData(TransferBean data) {
        if (data.getData() != null) {
            data.getData().setAmount(mAmount);
            data.getData().setOrder_sn(mBean.getOrder_sn());

            Intent intent = new Intent();
            intent.putExtra(Constant.INTENT_BEAN, data);
            intent.putExtra(Constant.INTENT_AP_CLOSING_TIME, mCloseTime);
            startActivity(TransferAccountsActivity.class, intent);
        }
    }

    /**
     * Rsa加密
     *
     * @param str
     * @return
     */
    public static String encode(String str) {
        try {
            byte[] pub_key = Base64.decode(UrlSet.public_key, Base64.DEFAULT);
            byte[] data = RsaUtils.RsaEncode(pub_key, str.getBytes());
            String tmTemp = new String(Base64.encode(data, Base64.NO_WRAP));
            return URLEncoder.encode(tmTemp);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.i("encode -- 加密失败:", e);
            return "";
        }
    }


    /**
     * 输入密码
     */
    private void inputPass() {
        mInputPassDialog = InputPassDialog.newInstance(
                ToFormatUtil.toDecimalFormat(mBean.getAmount(), 2), false);
        mInputPassDialog.setInputCompleteListener(new InputPassDialog.InputCompleteListener() {
            @Override
            public void inputComplete(String pass, String price) {
                weChatOrAliPayOrGeLiPay(mBean.getOrder_sn(), mPayType + "", pass);
            }
        });
        mInputPassDialog.show(getSupportFragmentManager(), "");
    }


    String url = "";

    private void showTipDialog(final int code, String title, String content, String confirmSrc) {
        TipDialog dialog = TipDialog.newInstance(content);
        dialog.isShowTitle(StringUtils.isNotEmpty(title));
        dialog.setTitleSrc(title);
        dialog.setCancelSrc(getString(R.string.back));
        dialog.setConfirmSrc(confirmSrc);
        dialog.setConfirmTextColor(Utils.getColor(R.color.text_b4b4b4));
        dialog.setConfirmTextColor(Utils.getColor(R.color.zhusediao));

        dialog.setOnclickListener(new TipDialog.ClickListenerInterface() {
            @Override
            public void doConfirm(TipDialog tipDialog) {
                mPresenter.getFwqTime(code);
                tipDialog.dismiss();
            }

            @Override
            public void doCancel() {

            }
        });
        dialog.show(getSupportFragmentManager(), "showTipDialog");
    }


    private void goSuccessActivity() {
        Intent intent = new Intent(mContext, OrderPaySuccessActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void showLoading() {
        Utils.uProgressDialog(mContext);
    }

    @Override
    public void hideLoading() {
        Utils.dismissProgress();
    }


    /* 弃用 */
//                /**
//                 * 钱包支付
//                 */
//                private void walletPay() {
//                    // 没有设置"支付密码"
//                    if (!LoginInformtaionSpUtils.getLoginInfBoolean(mContext, LoginInformtaionSpUtils.login_pay_pwd)) {
//                        ToastUtils.showToast(Utils.getString(R.string.please_setpaypass));
//                        Intent intent = new Intent().putExtra(INTENT_TYPE, GetCodeActivity.TYPE_PAYPASS);
//                        startActivity(GetCodeActivity.class, intent);
//                        return;
//                    }
//
//                    InputPassDialog.InputCompleteListener mInputCompleteListener =
//                            new InputPassDialog.InputCompleteListener() {
//                                @Override
//                                public void inputComplete(String pass, String price) {
//                                    Map<String, String> data = new HashMap<>();
//                                    data.put("user_id", GlobalData.getUser_id());
//                                    data.put("sign", Utils.md5(GlobalData.Md5Str + GlobalData.getUser_id() + Utils.md5(pass)).toUpperCase());
//                                    data.put("order_id", entity.getOrder_id() + "");
//                                    data.put("pay_pwd", Utils.md5(pass));
//                                    data.put("sum_amount", price);
//                                    balancePay(data);
//                                }
//                            };
//
//                    String price = getPrice();
//                    mInputPassDialog = InputPassDialog.newInstance(price, false);
//                    mInputPassDialog.setInputCompleteListener(mInputCompleteListener);
//                    mInputPassDialog.show(getSupportFragmentManager(), "");
//                }

//    /**
//     * 钱包支付
//     * @param data
//     */
//    public void balancePay(Map<String, String> data) {
//        mPresenter.balancePay(data);
//    }

    /* 钱包支付成功 */
    @Override
    public void walletPaySuccess() {
        if (mInputPassDialog != null) {
            mInputPassDialog.dismiss();
        }
        // onRefresh();
    }
}
