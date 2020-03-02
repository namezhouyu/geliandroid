package com.geli.m.mvp.home.other.submitorder_activity.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.AddressListBean;
import com.geli.m.bean.CouponBean;
import com.geli.m.bean.InvoiceBean;
import com.geli.m.bean.OverseasSubmitOrderBean;
import com.geli.m.bean.ShopLogisticBean;
import com.geli.m.bean.SubmitOrderBean;
import com.geli.m.bean.SubmitOrderJsonNormalBean;
import com.geli.m.bean.SubmitOrderNewBean;
import com.geli.m.config.Constant;
import com.geli.m.dialog.FuturesAgreementDialog;
import com.geli.m.dialog.logistic.ShopLogisticDialog;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.index_fragment.overseas_activity.OverseasActivity;
import com.geli.m.mvp.home.mine_fragment.address_activity.AddressActivity;
import com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicetype_activity.InvoiceTypeActivity;
import com.geli.m.mvp.home.other.pay_activity.PayActivity;
import com.geli.m.mvp.home.other.submitorder_activity.main.vh.SubmitOrderShopViewHolder;
import com.geli.m.mvp.home.other.submitorder_activity.success.OrderSubmitSuccessActivity;
import com.geli.m.popup.OrderPopupWindows;
import com.geli.m.utils.ArrayListUtils;
import com.geli.m.utils.PriceUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.google.gson.Gson;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Steam_l on 2018/1/2.
 * <p>
 * 提交订单
 */
public class SubmitOrderActivity extends MVPActivity<SubmitOrderPresentImpl> implements View.OnClickListener, SubmitOrderView {

    /**
     * 标题名
     */
    @BindView(R.id.tv_title_name)
    TextView mTvTitle;

    /**
     * 提交订单
     */
    @BindView(R.id.bt_submitorder)
    Button mBtSubmitOrder;

    /**
     * 订单列表 (同一个商店的为一项)
     */
    @BindView(R.id.erv_submitorder_list)
    EasyRecyclerView mErvContent;
    /**
     * 是否选择发票
     */
    @BindView(R.id.tv_submitorder_invoice)
    TextView mTvInvoice;
    /**
     * 合计
     */
    @BindView(R.id.tv_submitorder_total_price)
    TextView mTvTotalPrice;


    /**
     * 包裹"请选择地址" 和 "地址、电话"的布局 -- 的布局
     */
    @BindView(R.id.tv_submitorder_address_selectadd)
    TextView mTvSelectad;

    /**
     * 包裹"地址、电话"的布局
     */
    @BindView(R.id.rl_submitorder_address_addressinfo)
    RelativeLayout mRlAddInfo;
    /**
     * "地址、电话"布局 -- 电话
     */
    @BindView(R.id.tv_submitorder_address_phone)
    TextView mTvAddressPhone;
    /**
     * "地址、电话"布局 -- 名称(这个地址别名)
     */
    @BindView(R.id.tv_submitorder_address_name)
    TextView mTvAddressName;
    /**
     * "地址、电话"布局 -- 具体地址
     */
    @BindView(R.id.tv_submitorder_address_address)
    TextView mTvAddressAdd;
    /**
     * "地址、电话"布局 -- 默认地址(文本框)
     */
    @BindView(R.id.tv_submitorder_address_default)
    TextView mTvDefault;

    /**
     * 送货方式-- 疑问 -- 问号图片
     */
    @BindView(R.id.iv_submitorder_mess)
    ImageView mIvMess;


    /**
     * 送货方式的布局(包括两个单选框--格利冷运、自行提货)
     */
    @BindView(R.id.rg_submitorder_logistics_method)
    RadioGroup mRgLogistics;
    /**
     * 送货方式 -- 格利冷运(单选框)
     */
    @BindView(R.id.cb_submitorder_gelilengyun)
    RadioButton mCbLengYun;
    /**
     * 选择"自行提货/格利冷运"对应显示的"文本提示"
     */
    @BindView(R.id.tv_submitorder_tihuomess)
    TextView mTvTihuoMess;
    /**
     * 选择"格利冷运"对应显示的"文本提示"
     */
    @BindView(R.id.tv_submitorder_tihuomess_phone)
    TextView mTvTihuoMessPhone;


    /**
     * 配送类型 --  1:格利冷运、2:第三方(自提?)
     */
    private int mShippingType = -1;

    private int mAddressid = -1;
    private int mInvoiceId = -1;
    /**
     * 优惠总
     */
    private double mTotalCouponPrice = 0.0;
    /**
     * 订单总金额
     */
    private double mOrderTotalPrice;

    public String cart_id = "";
    private RecyclerArrayAdapter<SubmitOrderBean.DataEntity.ShopListEntity> mAdapter;

    /**
     * 当前订单类型 -- (普通,现货) 或 (期货,团购)
     */
    public int mCurrType = TYPE_NORMAL;
    /**
     * 普通,现货
     */
    public static int TYPE_NORMAL = 1 << 0;
    /**
     * 海外 -- 期货,团购
     */
    public static int TYPE_OVERSEAS = 1 << 1;

    OverseasSubmitOrderBean mOverseasBean;

    /**
     * 协议内容 -- 一开始如果是"期货"，就会向后台请求协议，放的这个字符串变量中; 如果同意协议了，就清空
     */
    private String mTerms;
    /**
     * 是不是团购
     */
    boolean mIsGroup = false;
    private NotifyBroadcastReceiver mBroadcastReceiver = new NotifyBroadcastReceiver();
    /**
     * 是不是账期 0:不是 1:是
     */
    int mIsSh = 0;

    SubmitOrderBean.DataEntity mBean;

    /**
     * 新批发 -- 商店对应的物流
     */
    ShopLogisticBean mLogisticBean = null;

    @Override
    protected int getResId() {
        return R.layout.activity_submitorder;
    }

    @Override
    protected void init() {
        super.init();
        mImmersionBar.keyboardEnable(false).init();
        getIntentExtra();       // 获取"意图"传递过来的数据
    }


    @Override
    protected void initData() {
        mTvTitle.setText(Utils.getString(R.string.title_submitorder));

        initErvContent();          // 初始化 -- 订单列表 (同一个商店的为一项)
        getOrderInfo();             // 获取"订单信息" -- 根据"订单类型(如:海外)"
        ViewCompat.setNestedScrollingEnabled(mErvContent.getRecyclerView(), false);
    }

    @Override
    protected void initEvent() {
        initRgLogistics();             // 初始化 -- 送货方式(单选框组的监听事件)
    }


    /**
     * 获取"意图"传递过来的数据
     */
    private void getIntentExtra() {
        mCurrType = getIntent().getIntExtra(Constant.INTENT_TYPE, mCurrType);
        mIsSh = getIntent().getIntExtra(Constant.INTENT_IS_SH, mIsSh);

        if (mCurrType == TYPE_OVERSEAS) {               /* 海外 -- 期货,团购 */
            mOverseasBean = getIntent().getParcelableExtra(Constant.INTENT_OVERSEAS_DATA);
        } else {                                        /* 普通,现货 */
            cart_id = getIntent().getStringExtra(Constant.INTENT_CART_ID);
        }
    }


    /**
     * 初始化 -- 订单列表 (同一个商店的为一项)
     */
    private void initErvContent() {
        mErvContent.setLayoutManager(new LinearLayoutManager(mContext));
        mErvContent.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.line_color), Utils.dip2px(mContext, 5)));
        mErvContent.setAdapterWithProgress(mAdapter = new RecyclerArrayAdapter<SubmitOrderBean.DataEntity.ShopListEntity>(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new SubmitOrderShopViewHolder(parent, mContext,
                        new SubmitOrderShopViewHolder.SubmitOrderStateInterFace() {
                            @Override
                            public int getCurrOrderType() {
                                return mCurrType;
                            }
                        });
            }
        });
    }

    /**
     * 获取"订单信息" -- 根据"订单类型(如:海外)" -- 实际上是提交购物车的数据后，返回订单的数据
     */
    private void getOrderInfo() {

        if (mCurrType == TYPE_NORMAL) {                            /* 普通,现货 */
            mPresenter.getOrderInfo(GlobalData.getUser_id(), cart_id, mIsSh + "");

        } else {                                                    /* 期货,团购 */
            Map data = new HashMap();
            data.put("user_id", GlobalData.getUser_id());
            data.put("goods_id", mOverseasBean.getGoods_id());
            data.put("goods_number", mOverseasBean.getGoods_number());
            data.put("sku_id", mOverseasBean.getSku_id());
            String goods_type = Constant.Goods_Type_Futures + "";                        // 海外直采-期货

            if (StringUtils.isNotEmpty(mOverseasBean.getGroupon_id())) {        // 海外直采-团购
                data.put("groupon_id", mOverseasBean.getGroupon_id());
                goods_type = Constant.Goods_Type_GroupBuy + "";
            }
            mPresenter.getOrderInfoFormOverseas(data);
            mPresenter.getTerms(goods_type);            // 获取"海外条款"
        }
    }

    /**
     * 初始化 -- 送货方式(单选框组的监听事件)
     * <p>
     * 送货方式的布局(包括两个单选框--格利冷运、自行提货)
     */
    private void initRgLogistics() {
        mRgLogistics.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.cb_submitorder_gelilengyun) {         /* 送货方式 -- 格利冷运 */
                    mShippingType = 1;
                    mTvTihuoMess.setText(R.string.submit_order_tihuomess_geli);
                    mTvTihuoMessPhone.setVisibility(View.VISIBLE);
                    mTvTihuoMessPhone.setText(getString(R.string.submit_order_tihuomess_phone));

                    if (isNewWholesale()) {
                        mTvTihuoMess.setText(R.string.submit_order_tihuomess_geli);
                        mTvTihuoMessPhone.setVisibility(View.VISIBLE);
                        mTvTihuoMessPhone.setText(getString(R.string.call));
                        mPresenter.getLogisticInfo(mBean.getShop_list().get(0).getShop_id() + "");
                    }

                } else if (checkedId == R.id.cb_submitorder_zixingtihuo) {    /* 送货方式 -- 自行提取 */
                    mShippingType = 2;
                    mTvTihuoMess.setText(R.string.submit_order_tihuomess);
                    mTvTihuoMessPhone.setVisibility(View.GONE);
                }
            }
        });
    }


    @OnClick({R.id.rl_submitorder_address_addressinfo, R.id.bt_submitorder,
            R.id.tv_submitorder_invoice, R.id.iv_submitorder_mess,
            R.id.iv_title_back, R.id.tv_submitorder_address_selectadd,
            R.id.tv_submitorder_tihuomess_phone})
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_title_back:
                finsh();
                break;

            case R.id.bt_submitorder:                               /* 提交订单 */
                submitOrder();
                break;

            case R.id.iv_submitorder_mess:                          /* 送货方式右边的问号 */
                showPopup();
                break;

            case R.id.tv_submitorder_invoice:                       /* 是否选择发票 */
                intent = new Intent();
                intent.putExtra(Constant.INTENT_MODE, InvoiceTypeActivity.INVOICEMODE_SELECT);
                startActivity(InvoiceTypeActivity.class, intent);
                break;

            case R.id.tv_submitorder_address_selectadd:             /* 选择地址 -- 还未选中的布局 */
            case R.id.rl_submitorder_address_addressinfo:           /* 选择地址 -- 已选中的布局 */
                intent = new Intent();
                intent.putExtra(Constant.INTENT_MODE, AddressActivity.ADDRESSMODE_SELECT);
                startActivity(AddressActivity.class, intent);
                break;

            case R.id.tv_submitorder_tihuomess_phone:               /* 打电话 / (新批发)显示物流信息 */
                if (isNewWholesale()) {
                    if (mLogisticBean == null) {
                        mPresenter.getLogisticInfo(mBean.getShop_list().get(0).getShop_id() + "");
                    } else {
                        getLogisticInfoSuccess(mLogisticBean);
                    }
                } else {
                    Utils.callPhone(mContext, mTvTihuoMessPhone.getText().toString().trim());
                }
                break;

            default:
                break;
        }
    }


    /**
     * 是不是新批发
     *
     * @return
     */
    private boolean isNewWholesale() {
        if (mBean != null && mBean.getShop_list() != null &&
                mBean.getShop_list().size() > 0 &&
                mBean.getShop_list().get(0).getShop_type() == Constant.Goods_Type_NewWholesale) {
            return true;
        }

        return false;
    }

    /**
     * 期货协议弹窗
     *
     * @return
     */
    private void agreeFuturesAgreement() {
        int goods_type = mAdapter.getAllData().get(0).getGoods_list().get(0).getGoods_type();
        // 期货协议弹窗
        FuturesAgreementDialog dialog = FuturesAgreementDialog.newInstance(mTerms, goods_type);
        dialog.setListener(new FuturesAgreementDialog.Listener() {
            @Override
            public void agree() {
                mTerms = "";
                submitOrder();
            }
        });
        dialog.show(getSupportFragmentManager(), "");
    }

    /**
     * 提交订单
     */
    private void submitOrder() {

        mIsGroup = false;

        if (mAddressid == -1) {
            ToastUtils.showToast(mContext, Utils.getString(R.string.plase_select_address));
            return;
        }

        if (mShippingType == -1) {
            ToastUtils.showToast(mContext, Utils.getString(R.string.plase_select_logistic));
            return;
        }

        if (!TextUtils.isEmpty(mTerms)) {            // 如果是账期的还要 -- 同意协议
            agreeFuturesAgreement();
            return;
        }

        mBtSubmitOrder.setEnabled(false);
        mBtSubmitOrder.setText(Utils.getString(R.string.lock));
        mBtSubmitOrder.setBackgroundResource(R.drawable.bggray_lefttopbot2r_shape);

        if (mCurrType == TYPE_NORMAL) {
            Map<String, String> map = setSubmitOrderJsonNormalBean();
            mPresenter.submitOrderNew(map);

        } else if (mCurrType == TYPE_OVERSEAS) {
            Map<String, String> map = setSubmitOrderJsonOverseasBean();
            mPresenter.submitOrderOvOrGroup(map, mIsGroup);
        }
    }


    /*------------------------- 普通、现货  （设置提交的数据）start -----------------------------*/

    /**
     * 普通商品生成订单信息和订单商品
     * <p>
     * user_id=c335251627a139685a81313cbad758e1
     * address_id = 1080
     * invoice_id
     * shipping_type = 2
     * is_sh    0/1
     * data=[{"cart_id":"14672,14671","postscript":"AAA","cpl_id":""},
     * {"cart_id":"14674,14673","postscript":"AAA","cpl_id":""}]
     */

    public Map<String, String> setSubmitOrderJsonNormalBean() {
        Map<String, String> map =new HashMap<>();
        List<SubmitOrderJsonNormalBean> jsonBeanNewList = new ArrayList<>();
        List<SubmitOrderBean.DataEntity.ShopListEntity> shopList = mAdapter.getAllData();
        for (SubmitOrderBean.DataEntity.ShopListEntity shopEntity : shopList) {     /* 一个个商店添加 */
            jsonBeanNewList.add(setTypeNormal(shopEntity));
        }
        map.put("data", new Gson().toJson(jsonBeanNewList));
        map.put("user_id", GlobalData.getUser_id());
        map.put("address_id", mAddressid + "");
        map.put("shipping_type", mShippingType + "");
        map.put("is_sh", mIsSh + "");
        if (mInvoiceId != -1) {
            map.put("invoice_id", mInvoiceId + "");
        }
        return map;
    }

    /**
     * 设置：提交数据 -- 普通、现货
     *
     * @param shopEntity
     */
    private SubmitOrderJsonNormalBean setTypeNormal(SubmitOrderBean.DataEntity.ShopListEntity shopEntity) {

        SubmitOrderJsonNormalBean jsonBeanNew = new SubmitOrderJsonNormalBean();
        String cartId = "";
        List<SubmitOrderBean.DataEntity.ShopListEntity.GoodsListEntity> cart_list = shopEntity.getGoods_list();
        for (SubmitOrderBean.DataEntity.ShopListEntity.GoodsListEntity cartEntity : cart_list) {
            cartId += cartEntity.getCart_id() + ",";
        }
        cartId = cartId.substring(0, cartId.length() - 1);
        jsonBeanNew.setCart_id(cartId);
        jsonBeanNew.setPostscript(StringUtils.isEmpty(shopEntity.message) ? "" : shopEntity.message);

        if (shopEntity.couponId != -1) {                    // 设置选中的"优惠券"
            jsonBeanNew.setCpl_id(shopEntity.couponId + "");
        } else {
            jsonBeanNew.setCpl_id("");
        }
        return jsonBeanNew;
    }
    /*------------------------- 普通、现货  （设置提交的数据）end -----------------------------*/


    /*------------------------- 海外团购  （设置提交的数据） start -----------------------------*/
    public Map<String, String> setSubmitOrderJsonOverseasBean() {

        Map<String, String> map = new HashMap<>();
        List<SubmitOrderBean.DataEntity.ShopListEntity> shopList = mAdapter.getAllData();
        for (SubmitOrderBean.DataEntity.ShopListEntity shopEntity : shopList) {     /*  */
            map = setTypeOverseas(shopEntity);
        }
        return map;
    }

    /**
     * 设置：提交数据 -- 团购、海外的
     *
     * @param shopEntity
     */
    private Map<String, String> setTypeOverseas(SubmitOrderBean.DataEntity.ShopListEntity shopEntity) {

        if (ArrayListUtils.notNullAndGreaterThan0(shopEntity.getGoods_list())) {
            Map<String, String> map = new HashMap<>();
            map.put("user_id", GlobalData.getUser_id() + "");
            map.put("address_id", mAddressid + "");
            map.put("shipping_type", mShippingType + "");
            map.put("postscript", shopEntity.message + "");
            map.put("sku_id", shopEntity.getGoods_list().get(0).getSku_id() + "");
            map.put("goods_number", shopEntity.getGoods_list().get(0).getCart_number() + "");

            String goodsId = "";
            for (SubmitOrderBean.DataEntity.ShopListEntity.GoodsListEntity goodsListEntity :
                    shopEntity.getGoods_list()) {
                if (StringUtils.isNotEmpty(goodsId)) {
                    goodsId += ",";
                }
                goodsId += goodsListEntity.getGoods_id();
            }
            map.put("goods_id", goodsId);
            if (shopEntity.getGoods_list().get(0).getGoods_type() == 4) {
                mIsGroup = true;
                map.put("groupon_id", mOverseasBean.getGroupon_id() + "");
            }
            if (mInvoiceId != -1) {
                map.put("invoice_id", mInvoiceId + "");
            }
            return map;
        } else {
            return null;
        }

    }

    /*------------------------- 海外团购  （设置提交的数据）end -----------------------------*/

    /**
     * 将"条款(说明)"弹窗，绑定在 "问号?图片控件"
     */
    private void showPopup() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {   /* API要大于19 */
            OrderPopupWindows
                    .newInstance(Utils.getString(R.string.submitorder_logistics_mess))
                    .showAsDropDown(mIvMess, 0, 0, Gravity.BOTTOM);
        }
    }

    @Override
    protected SubmitOrderPresentImpl createPresenter() {
        return new SubmitOrderPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
    }


    @Override
    public void onError(String msg) {
        ShowSingleToast.showToast(mContext, msg);
    }

    @Override
    public void showLoading() {
        Utils.uProgressDialog(mContext);
    }

    @Override
    public void hideLoading() {
        Utils.dismissProgress();
    }


    /* 显示获取到的订单信息 */
    @Override
    public void showOrderInfo(SubmitOrderBean.DataEntity entity) {
        mAdapter.clear();
        mAdapter.addAll(entity.getShop_list());
        mBean = entity;

        setPrice(entity.getTotal_price());
        setAddress(entity);
        if (!isNewWholesale()) {
            mRgLogistics.check(R.id.cb_submitorder_gelilengyun);
        }
    }

    /**
     * 设置地址
     */
    private void setAddress(SubmitOrderBean.DataEntity entity) {
        if (entity.getAdd_list() != null) {
            AddressListBean.DataEntity addressEntity = entity.getAdd_list();
            mRlAddInfo.setVisibility(View.VISIBLE);
            mTvSelectad.setVisibility(View.GONE);
            mTvDefault.setVisibility(addressEntity.getIs_default() != 0 ? View.VISIBLE : View.GONE);
            mTvAddressName.setText(addressEntity.getConsignee());
            mTvAddressAdd.setText(addressEntity.getAdd());
            mTvAddressPhone.setText(addressEntity.getMobile());
            mAddressid = addressEntity.getAddress_id();
        }
    }

    /**
     * 设置价格
     *
     * @param total
     */
    private void setPrice(double total) {

        mOrderTotalPrice = total;

        if (mCurrType == TYPE_NORMAL) {   /* 普通,现货 */
            mTvTotalPrice.setText(PriceUtils.getPrice(total));

        } else {                          /* 海外 -- 期货,团购 */
            if (mAdapter.getAllData().size() > 0) {
                SubmitOrderBean.DataEntity.ShopListEntity entity = mAdapter.getAllData().get(0);
                String price3 = entity.getPer_cent() + "";
                String price7 = entity.getSeventy_percent() + "";
                mTvTotalPrice.setText(PriceUtils.getOverseasPrice(price3, price7));
            }
        }
    }

    @Override
    public void finsh() {
        finish();
    }

    @Override
    public void showTerms(String terms) {
        mTerms = terms;
    }

    @Override
    public void submitOrderSuccess(SubmitOrderNewBean bean) {
        if (mIsSh == 1) {
            startActivity(OrderSubmitSuccessActivity.class, new Intent());
        } else {
            Intent intent = new Intent();
            intent.putExtra(Constant.INTENT_BEAN, bean.getData());
            startActivity(PayActivity.class, intent);
        }
    }


    @Override
    public void getLogisticInfoSuccess(ShopLogisticBean data) {
        // ShopLogisticDialog dialog = ShopLogisticDialog.newInstance(data);
        mLogisticBean = data;
        ShopLogisticDialog dialog = new ShopLogisticDialog(data);
        dialog.show(getSupportFragmentManager(), "ShopLogisticDialog");
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCAST_ADDRESS);
        filter.addAction(Constant.BROADCAST_COUPON);
        filter.addAction(Constant.BROADCAST_INVOICE);
        registerReceiver(mBroadcastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    public class NotifyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.BROADCAST_ADDRESS)) {                 /* 改变了邮寄地址 */
                changeAddress(intent);

            } else if (action.equals(Constant.BROADCAST_COUPON)) {
                changeCoupon(intent);

            } else if (action.equals(Constant.BROADCAST_INVOICE)) {
                changeInvoice(intent);
            }
        }
    }

    /**
     * 改变了发票
     *
     * @param intent
     */
    private void changeInvoice(Intent intent) {
        InvoiceBean.DataEntity invoice = intent.getParcelableExtra(OverseasActivity.BROADCAST_DATA);
        if (invoice == null) {
            mTvInvoice.setText(Utils.getString(R.string.whether_invoice));
            mInvoiceId = -1;
            return;
        }

        String res = "";
        if (invoice.getBelong() == 2) {                     //个人

            res += Utils.getString(R.string.personal_invoice);
        } else {
            if (invoice.getType() == 1) {                   //增值专用
                res += Utils.getString(R.string.invoice_VATdedicatedinvoice);
            } else {
                res += Utils.getString(R.string.invoice_VATordinaryinvoice);
            }
        }

        res += "-";
        if (invoice.getInvoice_type() == 1) {               //电子发票
            res += Utils.getString(R.string.invoice_electronicinvoice);
        } else {
            res += Utils.getString(R.string.invoice_pagerinvoice);
        }
        mInvoiceId = invoice.getInvoice_id();
        mTvInvoice.setText(Utils.getString(R.string.choose, res));
    }

    /**
     * 改变了优惠券
     *
     */
    private void changeCoupon(Intent intent) {
        CouponBean bean = intent.getParcelableExtra(OverseasActivity.BROADCAST_DATA);
        if (bean == null) {
            return;
        }
        int index = 0;
        for (SubmitOrderBean.DataEntity.ShopListEntity child : mAdapter.getAllData()) {

            if (child.getShop_id() == bean.getShop_id()) {
                String couponPrice = child.couponPrice;

                /* 之前已经选了优惠的，但是又重新操作了 */
                if (StringUtils.isNotEmpty(couponPrice)) {
                    mOrderTotalPrice += Double.valueOf(couponPrice);    // 加上之前减掉的优惠
                    mTotalCouponPrice -= Double.valueOf(couponPrice);   // 减去之前选中的优惠

                    // 价格变回选中优惠之前的
                    double price = child.getShop_sum() + Double.valueOf(couponPrice);
                    child.setShop_sum(price);
                }

                /* 重新计算总价格 */
                if (StringUtils.isEmpty(bean.getMoney()) && bean.getCpl_id() == -1) {       /* 不选择优惠券 */
                    child.couponPrice = "";
                    child.couponId = -1;

                } else {
                    child.couponId = bean.getCpl_id();
                    child.couponPrice = bean.getMoney();
                    double price = child.getShop_sum() - Double.valueOf(bean.getMoney());
                    child.setShop_sum(price);
                    mTotalCouponPrice += Double.valueOf(bean.getMoney());
                    mOrderTotalPrice -= mTotalCouponPrice;
                }
                mAdapter.update(child, index);

                mTvTotalPrice.setText(PriceUtils.getPrice(mOrderTotalPrice));
                setRgLogisticsCheck();
                return;
            }
            index++;
        }
    }

    /**
     * 从优惠券界面回来 -- 设置下物流 "冷运、自提"
     */
    private void setRgLogisticsCheck() {
        int tagId = R.id.cb_submitorder_gelilengyun;
        for (int i = 0; i < mRgLogistics.getChildCount(); i++) {
            RadioButton childAt = (RadioButton) mRgLogistics.getChildAt(i);
            if (childAt.isChecked()) {     // 选中的按钮
                tagId = childAt.getId();
            }
        }

        // 这里又重新选中
        mRgLogistics.clearCheck();
        mRgLogistics.check(tagId);
    }


    /**
     * 改变了邮寄地址
     *
     * @param intent
     */
    private void changeAddress(Intent intent) {
        AddressListBean.DataEntity address = intent.getParcelableExtra(OverseasActivity.BROADCAST_DATA);
        if (address != null) {
            mAddressid = address.getAddress_id();
            getOrderInfo();
        }
    }
}
