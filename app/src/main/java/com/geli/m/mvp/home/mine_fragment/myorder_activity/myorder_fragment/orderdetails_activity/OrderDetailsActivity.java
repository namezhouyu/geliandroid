package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.orderdetails_activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.api.UrlSet;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.OrderDetailsBean;
import com.geli.m.bean.OrderListBean;
import com.geli.m.bean.SubmitOrderNewBean;
import com.geli.m.config.Constant;
import com.geli.m.dialog.DownloadInvoiceDialog;
import com.geli.m.dialog.InputPassDialog;
import com.geli.m.dialog.TipDialog;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.aftersold_activity.AfterSoldActivity;
import com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.aftersolddetails_activity.AfterSoldDetailsActivity;
import com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.goodscomment_activity.GoodsCommentActivity;
import com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.logisticsdetails_activity.LogisticsDetailsActivity;
import com.geli.m.mvp.home.other.goodsdetails_activity.GoodsDetailsActivity;
import com.geli.m.mvp.home.other.pay_activity.PayActivity;
import com.geli.m.popup.OrderPopupWindows;
import com.geli.m.utils.DateFormatUtil;
import com.geli.m.utils.PriceUtils;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.TimeUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by Steam_l on 2018/1/8.
 * <p>
 * 订单详情
 */
public class OrderDetailsActivity extends MVPActivity<OrderDetailsPresentImpl> implements View.OnClickListener, OrderDetailsView {

    /**
     * 包裹"标题"的布局
     */
    @BindView(R.id.rl_title_rootlayout)
    RelativeLayout mRlTitle;
    /**
     * 返回按钮
     */
    @BindView(R.id.iv_title_back)
    ImageView mIvBack;
    /**
     * 标题
     */
    @BindView(R.id.tv_title_name)
    TextView mTvTitle;


    /**
     * 包裹"收货信息"的布局
     */
    @BindView(R.id.rl_submitorder_address_addressinfo)
    RelativeLayout mRlAddressInfo;
    /**
     * 右边选择地址的箭头
     */
    @BindView(R.id.iv_submitorder_address_enter_selectadd)
    ImageView mIvSelectAddress;
    /**
     * 地址
     */
    @BindView(R.id.tv_submitorder_address_address)
    TextView mTvAddress;
    /**
     * "默认地址"这几个字
     */
    @BindView(R.id.tv_submitorder_address_default)
    TextView mTvAdddeFault;
    /**
     * 这条地址的"别名"
     */
    @BindView(R.id.tv_submitorder_address_name)
    TextView mTvAddressName;
    /**
     * 电话
     */
    @BindView(R.id.tv_submitorder_address_phone)
    TextView mTvAddPhone;


    /** 包裹"商品具体数量，价格、每一个的信息"的布局 */
    /**
     * 订单中的每一件商品
     */
    @BindView(R.id.erv_itemview_orderdetails_goodslist)
    EasyRecyclerView mErvContent;
    /**
     * 订单中的一些提示
     */
    @BindView(R.id.iv_itemview_orderdetails_mess)
    ImageView mIvMess;
    /**
     * 商店名字
     */
    @BindView(R.id.tv_itemview_orderdetails_name)
    TextView mTvShopName;
    /**
     * 订单的金额 -- 商品总价
     */
    @BindView(R.id.tv_itemview_orderdetails_price)
    TextView mTvPrice;
    /**
     * 订单的商品数量
     */
    @BindView(R.id.tv_itemview_orderdetails_number)
    TextView mTvNumber;
    /**
     * 实付款 -- 订单的目前要付的金额
     */
    @BindView(R.id.tv_orderdetails_totalprice)
    TextView mTvTotalPrice;

    /**
     * 包裹"优惠价格"的布局
     */
    @BindView(R.id.rl_orderdetails_conponlayout)
    RelativeLayout mRlConponLayout;
    /**
     * 优惠的价格
     */
    @BindView(R.id.tv_orderdetails_couponprice)
    TextView mTvCouponPrice;
    /**
     * 包裹"运费总价"的布局
     */
    @BindView(R.id.rl_orderdetails_logistics_layout)
    RelativeLayout mRlLogisticsLayout;
    /**
     * 运费总价
     */
    @BindView(R.id.tv_orderdetails_logisticstotalprice)
    TextView mTvLogisticsPrice;
    /**
     * 商品总价
     */
    @BindView(R.id.tv_orderdetails_goodstotalprice)
    TextView mTvGoodsTotalPrice;

    /**
     * 包裹"发票类型"的布局
     */
    @BindView(R.id.rl_orderdetails_invoicelayout)
    RelativeLayout mRlInvoiceLayout;
    /**
     * 发票类型 -- 点击就去选择
     */
    @BindView(R.id.tv_orderdetails_invoicetype)
    TextView mTvInvoice;
    /**
     * 送货方式
     */
    @BindView(R.id.tv_orderdetails_logisticsmethod)
    TextView mTvLogisticsMethod;


    /**
     * 底部的 -- 左边按钮
     */
    @BindView(R.id.bt_orderdetails_left)
    Button mBtLeft;
    /**
     * 底部的 -- 右边按钮
     */
    @BindView(R.id.bt_orderdetails_right)
    Button mBtRight;
    /**
     * 底部的 -- 中间按钮
     */
    @BindView(R.id.bt_orderdetails_middle)
    Button mBtMiddle;


    /**
     * 订单标题 -- 如：待付款
     */
    @BindView(R.id.tv_orderdetails_toasttitle)
    TextView mTvToastTitle;
    /**
     * 订单提示内容
     */
    @BindView(R.id.tv_orderdetails_toastmess)
    TextView mTvToastMess;

    /**
     * 订单号
     */
    @BindView(R.id.tv_orderdetails_ordernumber)
    TextView mTvOrderNumber;
    /**
     * 开单时间
     */
    @BindView(R.id.tv_orderdetails_ordertime)
    TextView mTvOrderTime;


    private RecyclerArrayAdapter<OrderListBean.DataEntity.GoodsListEntity> mAdapter;

    private String order_id;

    private OrderDetailsBean.DataEntity mOrderEntity;
    private InputPassDialog.InputCompleteListener mInputCompleteListener;
    private InputPassDialog mInputPassDialog;
    private TipDialog mReceivingDialog;

    private int mClosingTime = 0;

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getOrderDetails(GlobalData.getUser_id(), order_id);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_orderdetails;
    }

    @Override
    protected void init() {
        super.init();
        mImmersionBar.keyboardEnable(false).statusBarColor(R.color.zhusediao).init();
        setView();
        order_id = getIntent().getStringExtra(Constant.INTENT_ORDER_ID);
        mClosingTime = getIntent().getIntExtra(Constant.INTENT_AP_CLOSING_TIME, 0);
    }

    @Override
    protected void initData() {
        setErv();
        setAdapter();
    }

    @Override
    protected void initEvent() {

    }


    private void setView() {
        mRlTitle.setBackgroundColor(Utils.getColor(R.color.zhusediao));
        mIvBack.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.nav_btn_white_fanhui));
        mTvTitle.setTextColor(Color.WHITE);
        mTvTitle.setText(Utils.getString(R.string.order_details));
        mRlAddressInfo.setVisibility(View.VISIBLE);
        mIvSelectAddress.setVisibility(View.GONE);
    }

    private void setErv() {
        mErvContent.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mErvContent.setAdapterWithProgress(mAdapter = new RecyclerArrayAdapter<OrderListBean.DataEntity.GoodsListEntity>(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyOrderGoodsViewHolder(parent, mContext);
            }
        });
        ViewCompat.setNestedScrollingEnabled(mErvContent.getRecyclerView(), false);
    }

    private void setAdapter() {
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                OrderListBean.DataEntity.GoodsListEntity item = mAdapter.getItem(position);
                startActivity(GoodsDetailsActivity.class, new Intent().putExtra(Constant.INTENT_GOODS_ID, item.getGoods_id() + ""));
            }
        });
    }


    /**
     * 设置 -- 提示消息
     *
     * @param mess
     * @param resId
     */
    public void setToastTitle(String mess, int resId) {
        mTvToastTitle.setText(mess);
        Drawable drawable = ContextCompat.getDrawable(mContext, resId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        mTvToastTitle.setCompoundDrawables(drawable, null, null, null);
    }


    /*----------------------------  设置底部两个按钮的内容  -----------------------------*/
    private void setBtnState(OrderDetailsBean.DataEntity.OrderInfoEntity entity) {
        if (Arrays.equals(entity.getState(), Constant.STATE_PAY)) {
            setPayState(entity);
        } else if (Arrays.equals(entity.getState(), Constant.STATE_SHIP) ||
                Arrays.equals(entity.getState(), Constant.STATE_SHIP_AP_180)) {
            setShipState(entity);
        } else if (Arrays.equals(entity.getState(), Constant.STATE_RECEIVING) ||
                Arrays.equals(entity.getState(), Constant.STATE_SHIP_AP_181)) {
            setReceivingState(entity);
        } else if ((Arrays.equals(entity.getState(), Constant.STATE_EVALUATION) && entity.getIs_comment() == 0) ||
                (Arrays.equals(entity.getState(), Constant.STATE_SHIP_AP_582) && entity.getIs_comment() == 0)) {
            setEvaluationgState(entity);
        } else if ((Arrays.equals(entity.getState(), Constant.STATE_EVALUATION) && entity.getIs_comment() == 1) ||
                (Arrays.equals(entity.getState(), Constant.STATE_SHIP_AP_582) && entity.getIs_comment() == 1)) {
            setAftemarketState(entity);
        } else if (Arrays.equals(entity.getState(), Constant.STATE_CANCELORDER)) {
            setCancelOrderState(entity);
        }
    }

    /**
     * 待付款
     *
     * @param entity
     */
    private void setPayState(OrderDetailsBean.DataEntity.OrderInfoEntity entity) {
        setToastTitle(Utils.getString(R.string.pre_payment), R.drawable.icon_daifukuang);
        mTvToastMess.setText(Utils.getString(R.string.order_pay_state));
        mBtLeft.setText(Utils.getString(R.string.cancel_order));
        setPayState(entity, false);
    }


    /**
     * 待发货
     *
     * @param entity
     */
    private void setShipState(OrderDetailsBean.DataEntity.OrderInfoEntity entity) {
        setToastTitle(Utils.getString(R.string.to_be_delivered), R.drawable.icon_daifahuo);
        mBtLeft.setVisibility(View.GONE);


        String message = "";
        if (isOverseas(entity)) {
            message = Utils.getString(R.string.order_ship_normal_state);
        } else {
            message = Utils.getString(Utils.getString(R.string.order_ship_overseas_state));
        }

        if (entity.getLogistics_is_pay() == Constant.Logistics_Status_Yes) {       // 需要支付运费
            if (Double.valueOf(entity.getLogistics_price()) == 0) {       // 0元 -- 说明在审核中
                message = Utils.getString(R.string.freight_calculation);
                mBtRight.setText(Utils.getString(R.string.payment_of_freight));
                mBtRight.setEnabled(false);
            } else {
                message = Utils.getString(R.string.wait_pay_freight);
                mBtRight.setText(Utils.getString(R.string.payment_of_freight));
            }
        } else {
            mBtRight.setText(Utils.getString(R.string.remind_the_shipment));
        }

        mTvToastMess.setText(message);
    }

    /**
     * 待收货
     *
     * @param entity
     */
    private void setReceivingState(OrderDetailsBean.DataEntity.OrderInfoEntity entity) {
        setToastTitle(Utils.getString(R.string.to_be_received), R.drawable.icon_daishouhuo);
        mBtLeft.setText(Utils.getString(R.string.check_logistics));
        if (isOverseas(entity)) {
            if (entity.getPercentage() != 3) {
                mTvToastMess.setText(Utils.getString(R.string.order_receiving_overseas_pay_state));
                setPayState(entity, true);
            } else {
                mTvToastMess.setText(Utils.getString(R.string.order_receiving_overseas_state));
                mBtRight.setText(Utils.getString(R.string.confirm_receipt));
            }
        } else {
            mTvToastMess.setText(Utils.getString(R.string.order_receiving_normal_state));
            mBtRight.setText(Utils.getString(R.string.confirm_receipt));
        }

        if (entity.getIs_sh() == Constant.AP_True) {                   // 账期
            if (entity.getShipping_status() == 1) {          // 未收货
                mTvToastMess.setText(Utils.getString(R.string.order_receiving_normal_state));
                mBtRight.setText(Utils.getString(R.string.confirm_receipt));
            } else if (entity.getShipping_status() == 2) {
                String closingTime = TimeUtils.transForDate(mClosingTime, TimeUtils.format11);
                mTvToastMess.setText(Utils.getString(R.string.you_final_settlement_day, closingTime));
                mBtRight.setText(Utils.getString(R.string.account_payment));                   // 支付
            }
        }
    }

    /**
     * 待评价
     *
     * @param entity
     */
    private void setEvaluationgState(OrderDetailsBean.DataEntity.OrderInfoEntity entity) {
        setToastTitle(Utils.getString(R.string.be_evaluated), R.drawable.icon_daipingjia);
        if (!TextUtils.isEmpty(entity.getInvoice_img())) {
            mBtLeft.setText(Utils.getString(R.string.download_invoice));
        } else {
            mBtLeft.setVisibility(View.GONE);
        }
        mBtRight.setText(Utils.getString(R.string.go_to_evaluation));
        mTvToastMess.setText(Utils.getString(R.string.order_evaluationg_state));
    }

    /**
     * 售后
     *
     * @param entity
     */
    private void setAftemarketState(OrderDetailsBean.DataEntity.OrderInfoEntity entity) {
        if (!TextUtils.isEmpty(entity.getInvoice_img())) {
            mBtLeft.setText(Utils.getString(R.string.download_invoice));
        } else {
            mBtLeft.setVisibility(View.GONE);
        }

        // 右边按钮
        String buttonName = "";
        switch (entity.getAfter_sold_status()) {     // 0:申请售后;1:待受理;2:受理中;3:售后完成
            case Constant.AfterSold_Status_Apply:                             /* 申请售后 */
                buttonName = Utils.getString(R.string.apply_for_after_sales);
                break;
            case Constant.AfterSold_Status_Admissible:                        /* 待受理 */
                buttonName = Utils.getString(R.string.to_accept);
                break;
            case Constant.AfterSold_Status_Admissibility:                     /* 受理中 */
                buttonName = Utils.getString(R.string.in_the_processing);
                break;
            case Constant.AfterSold_Status_Completion:                        /* 售后完成 */
                buttonName = Utils.getString(R.string.after_complete);
                break;
            default:
                buttonName = Utils.getString(R.string.apply_for_after_sales);
                break;
        }
        mBtRight.setText(buttonName);
        mTvToastMess.setText(Utils.getString(R.string.order_aftemarket_state));
    }


    /**
     * 订单已取消
     *
     * @param entity
     */
    private void setCancelOrderState(OrderDetailsBean.DataEntity.OrderInfoEntity entity) {
        mBtLeft.setVisibility(View.GONE);
        mBtRight.setVisibility(View.GONE);
        setToastTitle(Utils.getString(R.string.order_cancel), R.drawable.icon_daifukuang);
        mTvToastMess.setText(Utils.getString(R.string.order_cancel));
    }


    /**
     * 设置支付
     *
     * @param entity
     * @param isOverseas
     */
    private void setPayState(OrderDetailsBean.DataEntity.OrderInfoEntity entity, boolean isOverseas) {
        mBtRight.setEnabled(true);
        if (entity.getIs_pay() != 1) {
            mBtRight.setEnabled(false);
            mBtRight.setText(Utils.getString(R.string.wait_change));
        } else {
            if (entity.getPay_type() == Constant.Pay_Type_LineDown) {      // 线下
                setProofPay(entity, isOverseas);
            } else {                                                //支付
                if (isOverseas) {
                    if (StringUtils.isEmpty(entity.getSeventy_percent()) ||
                            Double.valueOf(entity.getSeventy_percent()) == 0) {
                        mBtRight.setEnabled(false);
                    }
                    mBtRight.setText(Utils.getString(R.string.pay_balance));
                } else {
                    mBtRight.setText(Utils.getString(R.string.immediate_payment));
                }
            }
        }
    }

    /**
     * 线下支付
     *
     * @param entity
     * @param isOverseas
     */
    private void setProofPay(OrderDetailsBean.DataEntity.OrderInfoEntity entity, boolean isOverseas) {
        if (entity.getProof() == Constant.Pay_Proof_WaitSubmit) {            /* 上传凭证 */

            if (StringUtils.isEmpty(entity.getSeventy_percent()) ||
                    Double.valueOf(entity.getSeventy_percent()) == 0) {
                mBtRight.setEnabled(false);
            }

            if (isOverseas) {
                mBtRight.setText(Utils.getString(R.string.pay_balance));
            } else {
                mBtRight.setText(Utils.getString(R.string.immediate_payment));
            }

        } else if (entity.getProof() == Constant.Pay_Proof_Audit) {          /* 审核中 */
            mBtRight.setText(Utils.getString(R.string.under_review));
            mTvToastMess.setText(Utils.getString(R.string.order_under_review_state));
            mBtRight.setEnabled(false);

        } else if (entity.getProof() == Constant.Pay_Proof_AgainSubmit) {    /* 审核失败 */
            mBtRight.setText(Utils.getString(R.string.re_payment));
        }
    }

    @OnClick({R.id.tv_orderdetails_contactshop, R.id.tv_orderdetails_contactservice,
            R.id.bt_orderdetails_left, R.id.iv_title_back, R.id.bt_orderdetails_right,
            R.id.tv_orderdetails_copy, R.id.iv_itemview_orderdetails_mess,
            R.id.bt_orderdetails_middle})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_title_back:
                finish();
                break;

            case R.id.tv_orderdetails_contactshop:              /* 联系商家 */
                callPhone(mOrderEntity, shopTel);
                break;

            case R.id.tv_orderdetails_contactservice:           /* 联系客服 */
                callPhone(mOrderEntity, serviceTel);
                break;

            case R.id.iv_itemview_orderdetails_mess:            /* ?提示信息 */
                showPopup();
                break;

            case R.id.bt_orderdetails_right:                    /* 右下角两个按钮 */
            case R.id.bt_orderdetails_left:
            case R.id.bt_orderdetails_middle:
                complyForState(v.getId());
                break;

            case R.id.tv_orderdetails_copy:                     /* 复制"订单号"到系统剪贴板 */
                Utils.copy(mContext, mTvOrderNumber.getText().toString().trim());
                break;

            default:
                break;
        }
    }

    /**
     * 联系商家
     */
    int shopTel = 1;
    /**
     * 联系客服
     */
    int serviceTel = 2;

    private void callPhone(OrderDetailsBean.DataEntity orderEntity, int phoneType) {
        if (orderEntity != null) {
            if (phoneType == shopTel) {
                Utils.callPhone(mContext, mOrderEntity.getOrder_info().getShop_tel());
            } else if (phoneType == serviceTel) {
                Utils.callPhone(mContext, mOrderEntity.getOrder_info().getService_tel());
            }

        }
    }

    /**
     * 显示弹窗 -- ?提示信息
     */
    private void showPopup() {
        OrderPopupWindows.newInstance(Utils.getString(R.string.submitorder_order_mess))
                .showAsDropDown(mIvMess);
    }


    /*-------------------------------- 底部两个按钮的点击事件 -------------------------------*/
    private void complyForState(int id) {
        if (mOrderEntity != null) {
            if (Arrays.equals(mOrderEntity.getOrder_info().getState(), Constant.STATE_PAY)) {        /*待支付*/
                complyPayState(mOrderEntity.getOrder_info(), id);
            } else if (Arrays.equals(mOrderEntity.getOrder_info().getState(), Constant.STATE_SHIP) ||
                    Arrays.equals(mOrderEntity.getOrder_info().getState(), Constant.STATE_SHIP_AP_180)) {/*待发货*/
                complyShipState(mOrderEntity.getOrder_info());
            } else if (Arrays.equals(mOrderEntity.getOrder_info().getState(), Constant.STATE_RECEIVING) ||
                    Arrays.equals(mOrderEntity.getOrder_info().getState(), Constant.STATE_SHIP_AP_181)) {   /*待收货*/
                complyReceivingState(mOrderEntity.getOrder_info(), id);
            } else if ((Arrays.equals(mOrderEntity.getOrder_info().getState(), Constant.STATE_EVALUATION)
                    && mOrderEntity.getOrder_info().getIs_comment() == 0) ||
                    (Arrays.equals(mOrderEntity.getOrder_info().getState(), Constant.STATE_SHIP_AP_582)
                            && mOrderEntity.getOrder_info().getIs_comment() == 0)) {                  /* 待评价 */
                complyEvaluationState(mOrderEntity.getOrder_info(), id);
            } else if ((Arrays.equals(mOrderEntity.getOrder_info().getState(), Constant.STATE_EVALUATION)
                    && mOrderEntity.getOrder_info().getIs_comment() == 1) ||
                    (Arrays.equals(mOrderEntity.getOrder_info().getState(), Constant.STATE_SHIP_AP_582)
                            && mOrderEntity.getOrder_info().getIs_comment() == 1)) {                  /* 售后 */
                complyAftermarketState(mOrderEntity.getOrder_info(), id);
            } else if (Arrays.equals(mOrderEntity.getOrder_info().getState(), Constant.STATE_AFTERMARKET)) {  /* 售后 */
                complyAftermarketState(mOrderEntity.getOrder_info(), id);
            }
        }
    }


    boolean Receiving;


    /**
     * 提醒发货
     *
     * @param order_info
     */
    private void complyShipState(OrderDetailsBean.DataEntity.OrderInfoEntity order_info) {
        if (order_info.getLogistics_is_pay() == Constant.Logistics_Status_Yes) {       // 需要支付运费
            if (Double.valueOf(order_info.getLogistics_price()) > 0) {       // 0元 -- 说明在审核中
                Intent intent = new Intent();
                SubmitOrderNewBean.DataBean dataBean = new SubmitOrderNewBean.DataBean();
                dataBean.setOrder_sn(order_info.getOrder_sn());
                dataBean.setAmount(Double.valueOf(order_info.getLogistics_price()));

                intent.putExtra(Constant.INTENT_IS_LOGISTICS, true);
                intent.putExtra(Constant.INTENT_BEAN, dataBean);
                startActivity(PayActivity.class, intent);
            }
        } else {
            mPresenter.universal(UrlSet.remind, order_info.getOrder_id() + ""); //提醒发货
        }
    }

    /**
     * 确认收货
     *
     * @param order_info
     * @param id
     */
    private void complyReceivingState(final OrderDetailsBean.DataEntity.OrderInfoEntity order_info, int id) {
        if (id == R.id.bt_orderdetails_right) {
            if (isOverseas(order_info)) {                                           /* 海外订单 */
                if (order_info.getIs_pay() == 1 &&                          // 可以支付
                        order_info.getPercentage() != Constant.Pay_Percentage_All) { // 不是"全款支付"
                    complyPayState(order_info, id);
                    return;
                }
            }

            if (order_info.getIs_sh() == Constant.AP_True) {                   // 账期
                if (order_info.getShipping_status() == 1) {
                    confirmReceipt(order_info);
                } else if (order_info.getShipping_status() == 2) {
                    complyPayState(order_info, id);                     // 支付
                }
            } else {
                confirmReceipt(order_info);
            }


        } else if (id == R.id.bt_orderdetails_left) {  // 查看物流
            startActivity(LogisticsDetailsActivity.class, new Intent().putExtra(Constant.INTENT_ORDER_ID, order_id));

        } else if (id == R.id.bt_orderdetails_middle) {  // 账期结算
            ToastUtils.showToast("账期结算");
        }

    }


    /**
     * 确认收货 -- 窗口
     *
     * @param entity
     */
    private void confirmReceipt(final OrderDetailsBean.DataEntity.OrderInfoEntity entity) {
        // 确认收货
        mReceivingDialog = TipDialog.newInstance(Utils.getString(R.string.order_confirmreceipt));
        mReceivingDialog.setOnclickListener(new TipDialog.ClickListenerInterface() {
            @Override
            public void doConfirm(TipDialog tipDialog) {
                Receiving = true;
                mPresenter.universal(UrlSet.confirmReceipt, entity.getOrder_id() + "");
            }

            @Override
            public void doCancel() {
            }
        });
        mReceivingDialog.show(getSupportFragmentManager(), "");
    }

    /**
     * 评价
     *
     * @param order_info
     * @param id
     */
    private void complyEvaluationState(OrderDetailsBean.DataEntity.OrderInfoEntity order_info, int id) {
        if (id == R.id.bt_orderdetails_right) {
            Intent intent = new Intent().putParcelableArrayListExtra(Constant.INTENT_COMMENT_GOODS, order_info.getGoods_list());
            startActivity(GoodsCommentActivity.class, intent);
        } else {                                            //下载发票
            downloadInvoice(order_info.getInvoice_img());
        }
    }

    /**
     * 售后!
     *
     * @param id
     */
    private void complyAftermarketState(OrderDetailsBean.DataEntity.OrderInfoEntity order_info, int id) {
        if (id == R.id.bt_orderdetails_right) {                 /* 底部 -- 右边按钮 -- 售后 */

            if (order_info.getAfter_sold_status() == Constant.AfterSold_Status_Apply) {   // 还没有申请售后
                Intent intent = new Intent().putExtra(Constant.INTENT_ORDER_ID, order_info.getOrder_id() + "");
                startActivity(AfterSoldActivity.class, intent);
            } else {
                Intent intent = new Intent().putExtra(Constant.INTENT_ORDER_ID, order_info.getOrder_id() + "");
                startActivity(AfterSoldDetailsActivity.class, intent);
            }
        } else {                                        // 下载发票
            downloadInvoice(order_info.getInvoice_img());
        }
    }

    public void downloadInvoice(String url) {
        DownloadInvoiceDialog.newInstance(url).show(getSupportFragmentManager(), "");
    }

    /**
     * 支付
     *
     * @param order_info
     * @param id
     */
    private void complyPayState(final OrderDetailsBean.DataEntity.OrderInfoEntity order_info, int id) {
        if (id == R.id.bt_orderdetails_right) {

            double amount = Double.valueOf(getPrice(order_info));
            SubmitOrderNewBean.DataBean dataBean = new SubmitOrderNewBean.DataBean();
            dataBean.setOrder_sn(order_info.getOrder_sn());
            dataBean.setAmount(amount);
            Intent intent = new Intent();
            intent.putExtra(Constant.INTENT_BEAN, dataBean);
            startActivity(PayActivity.class, intent);
        } else {                                                            // 取消订单
            cancelOrder(order_info);
        }
    }

    /**
     * 取消订单
     *
     * @param order_info
     */
    private void cancelOrder(final OrderDetailsBean.DataEntity.OrderInfoEntity order_info) {
        final TipDialog tipDialog = TipDialog.newInstance(Utils.getString(R.string.is_cancel_order));
        tipDialog.setOnclickListener(new TipDialog.ClickListenerInterface() {
            @Override
            public void doConfirm(TipDialog tipDialog) {
                tipDialog.dismiss();
                mPresenter.universal(UrlSet.cancelOrder, order_info.getOrder_id() + "");
                finish();
            }

            @Override
            public void doCancel() {
                tipDialog.dismiss();
            }
        });
        tipDialog.show(getSupportFragmentManager(), "");
    }


    @Override
    protected OrderDetailsPresentImpl createPresenter() {
        return new OrderDetailsPresentImpl(this);
    }


    @Override
    public void onSuccess(String msg) {
        ToastUtils.showToast(msg);
        if (Receiving) {
            Receiving = !Receiving;
            if (mReceivingDialog != null) {
                mReceivingDialog.dismiss();
            }
            finish();
        }
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
    public void showOrderDetails(OrderDetailsBean.DataEntity bean) {
        if (bean != null && bean.getOrder_info() != null && bean.getOrder_info().getGoods_list() != null) {
            int i = 0;
            int is_sh = bean.getOrder_info().getIs_sh();
            for (OrderListBean.DataEntity.GoodsListEntity goodsListEntity : bean.getOrder_info().getGoods_list()) {
                bean.getOrder_info().getGoods_list().get(i).setIs_sh(is_sh);
            }
            mAdapter.clear();
            mAdapter.addAll(bean.getOrder_info().getGoods_list());
            mOrderEntity = bean;
            setAddress(bean.getAddress_info());             // 设置地址
            setOrderData(bean.getOrder_info());             // 设置订单信息
            setInvoiceData(bean.getOrder_invoice());        // 设置发票信息
            setBtnState(bean.getOrder_info());              // 设置按钮文字
        } else {
            ToastUtils.showToast("获取订单异常");
            finish();
        }
    }


    /**
     * 设置地址
     *
     * @param address_info
     */
    private void setAddress(OrderDetailsBean.DataEntity.AddressInfoEntity address_info) {
        mTvAddress.setText(address_info.getAdd());
        mTvAddPhone.setText(address_info.getMobile());
        mTvAddressName.setText(address_info.getConsignee());
        mTvAdddeFault.setVisibility(address_info.getIs_default() == 1 ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置发票信息
     *
     * @param order_invoice
     */
    private void setInvoiceData(OrderDetailsBean.DataEntity.OrderInvoiceEntity order_invoice) {
        if (order_invoice == null) {                    // 没有发票信息
            mRlInvoiceLayout.setVisibility(View.GONE);
        } else {
            mTvInvoice.setText(order_invoice.getType() + "-" + order_invoice.getInvoice_type());
        }
    }

    /**
     * 海外订单
     */
    public boolean isOverseas(OrderDetailsBean.DataEntity.OrderInfoEntity bean) {
        return (bean.getOrder_type().equals(Constant.Goods_Type_Spot + "") ||
                bean.getOrder_type().equals(Constant.Goods_Type_Futures + ""));
    }


    /**
     * 设置这个订单的信息
     *
     * @param bean
     */
    private void setOrderData(OrderDetailsBean.DataEntity.OrderInfoEntity bean) {
        if (!isOverseas(bean)) {                        // 不是"海外的"隐藏提示信息
            mIvMess.setVisibility(View.GONE);
        }

        mTvOrderNumber.setText(Utils.getString(R.string.order_number, bean.getOrder_sn()));
        mTvOrderTime.setText(Utils.getString(R.string.openorder_time, DateFormatUtil.transForDate(Integer.valueOf(bean.getAdd_time()))));
        mTvShopName.setText(bean.getShop_name());

        setOrderNumber(bean);                           // 设置订单的商品数量
        setPrice(bean);                                 // 设置价格
        setLogisticsMethod(bean);                       // 设置物流方式
        setLogisticsPriceAndConponPrice(bean);          // 设置"运费总价"和"优惠价格"
    }


    /**
     * 设置订单中商品的数量
     *
     * @param bean
     */
    private void setOrderNumber(OrderDetailsBean.DataEntity.OrderInfoEntity bean) {
        if (bean.getGoods_list().size() > 0) {
            ArrayList<OrderListBean.DataEntity.GoodsListEntity> goods_list = bean.getGoods_list();
            int size = 0;
            for (OrderListBean.DataEntity.GoodsListEntity entity : goods_list) {
                size += entity.getCart_number();
            }
            mTvNumber.setText(Utils.getString(R.string.total_goods_number, size + "",
                    Utils.getString(R.string.stock)));
        }
    }

    /**
     * 设置价格
     *
     * @param bean
     */
    private void setPrice(OrderDetailsBean.DataEntity.OrderInfoEntity bean) {

        if (isOverseas(bean)) {                                             /* 海外订单 */
            String serventy;                // 剩下的 70%
            if (bean.getIs_pay() == 0) {
                serventy = Utils.getString(R.string.wait_change);
            } else {
                if (bean.getPercentage() == Constant.Pay_Percentage_30) {
                    serventy = Utils.getString(R.string.wait_change);
                } else {
                    if (StringUtils.isEmpty(bean.getSeventy_percent()) ||
                            Double.valueOf(bean.getSeventy_percent()) == 0) {
                        serventy = Utils.getString(R.string.wait_change);

                    } else {
                        serventy = bean.getSeventy_percent();
                    }
                }
            }

            mTvPrice.setText(PriceUtils.getOverseasPrice(R.string.overaeas_order_price1,
                    bean.getPer_cent() + "", serventy));
            mTvGoodsTotalPrice.setText(PriceUtils.getOverseasPrice(R.string.overaeas_order_price,
                    bean.getPer_cent() + "", serventy));

            if (Arrays.equals(bean.getState(), Constant.STATE_PAY) ||
                    Arrays.equals(bean.getState(), Constant.STATE_SHIP)) {               /* 30% -- 待付款、待发货 */
                mTvTotalPrice.setText(PriceUtils.getPrice(bean.getPer_cent()));

            } else if (Arrays.equals(bean.getState(), Constant.STATE_RECEIVING) &&       /* 70% -- 待收货 */
                    bean.getPercentage() != Constant.Pay_Percentage_All) {

                if (bean.getIs_pay() == 0) {                        // 不可支付
                    mTvTotalPrice.setText(Utils.getString(R.string.wait_change));
                } else {                                            // 可以支付
                    mTvTotalPrice.setText(PriceUtils.getPrice(bean.getSeventy_percent()));
                }
            } else {                                                            /* 全部 */
                mTvTotalPrice.setText(PriceUtils.getOverseasPrice(R.string.overaeas_order_price1, bean.getPer_cent() + "", bean.getSeventy_percent() + ""));
            }

        } else {
            mTvPrice.setText(Utils.getString(R.string.total) + PriceUtils.getPrice(bean.getOrder_amount()));
            mTvGoodsTotalPrice.setText(PriceUtils.getPrice(bean.getOrder_amount()));

            mTvTotalPrice.setText(PriceUtils.getPrice(bean.getSum_amount()));
        }
    }


    /**
     * 设置物流方式
     *
     * @param bean
     */
    private void setLogisticsMethod(OrderDetailsBean.DataEntity.OrderInfoEntity bean) {
        if (bean.getShipping_type() == 1) {
            mTvLogisticsMethod.setText(Utils.getString(R.string.geli_lengyun));
        } else {
            mTvLogisticsMethod.setText(Utils.getString(R.string.zixing_tihuo));
        }
    }

    /**
     * 设置"运费总价"和"优惠价格"
     *
     * @param bean
     */
    private void setLogisticsPriceAndConponPrice(OrderDetailsBean.DataEntity.OrderInfoEntity bean) {
        if (Double.valueOf(bean.getLogistics_price()) == 0) {
            mRlLogisticsLayout.setVisibility(View.GONE);
        } else {
            mTvLogisticsPrice.setText(PriceUtils.getPrice(bean.getLogistics_price()));
        }
        if (Double.valueOf(bean.getDiscount_amount()) == 0) {
            mRlConponLayout.setVisibility(View.GONE);
        } else {
            mTvCouponPrice.setText(PriceUtils.getPrice(bean.getDiscount_amount()));
        }
    }


//    /**
//     * 如果是 --
//     * true -- 海外订单 (2:海外现货; 3:海外期货)
//     * false -- 不是海外订单
//     * @param entity
//     * @return
//     */
//    public boolean isOverseas(PayBean entity) {
//        return entity.getOrder_type().equals(Goods_Type_Spot + "") ||
//                entity.getOrder_type().equals(Goods_Type_Futures + "");
//    }
//

    /**
     * 获取支付价格
     *
     * @return
     */
    private String getPrice(OrderDetailsBean.DataEntity.OrderInfoEntity entity) {
        if (entity == null) {
            return "";
        }
        String price = "";
        if (isOverseas(entity)) {                                           // 海外的
            if (entity.getPercentage() == Constant.Pay_Percentage_70) {
                price = entity.getSeventy_percent();                        // 定金30%
            } else {
                price = entity.getPer_cent();                               // 70%
            }
        } else {
            price = entity.getSum_amount();
        }
        return price;
    }


}
