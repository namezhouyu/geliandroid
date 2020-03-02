package com.geli.m.mvp.home.mine_fragment.invoicemerge_activity.invoicelist_activity.subminInvoicemerge_activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.AddressListBean;
import com.geli.m.bean.InvoiceBean;
import com.geli.m.bean.InvoiceMergeSuccessBean;
import com.geli.m.bean.InvoiceOrderBean;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.mine_fragment.address_activity.AddressActivity;
import com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicetype_activity.InvoiceTypeActivity;
import com.geli.m.mvp.home.mine_fragment.invoicemerge_activity.invoicelist_activity.InvoiceListViewHolder;
import com.geli.m.mvp.home.mine_fragment.invoicemerge_activity.invoicestate_activity.InvoiceStateActivity;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import java.util.ArrayList;

import static com.geli.m.config.Constant.BROADCAST_ADDRESS;
import static com.geli.m.config.Constant.BROADCAST_INVOICE;
import static com.geli.m.config.Constant.INTENT_BEAN;
import static com.geli.m.config.Constant.INTENT_MODE;
import static com.geli.m.config.Constant.INTENT_MONEY;
import static com.geli.m.config.Constant.INTENT_ORDER_ID;
import static com.geli.m.config.Constant.INTENT_SHOP_NAME;
import static com.geli.m.config.Constant.INVOICE_ELECTRONIC;
import static com.geli.m.config.Constant.INVOICE_PAGER;
import static com.geli.m.config.Constant.INVOICE_PERSONAL;
import static com.geli.m.config.Constant.INVOICE_UNIT_SPECIAL;
import static com.geli.m.mvp.home.index_fragment.overseas_activity.OverseasActivity.BROADCAST_DATA;

/**
 * author:  shen
 * date:    2018/12/24
 * <p>
 * 提交 -- 合并发票
 */
public class SubmitInvoiceMergeActivity extends MVPActivity<SubmitInvoiceMergePresentImpl> implements SubmitInvoiceMergeView {


    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title_name)
    TextView mTvTitleName;
    @BindView(R.id.tv_title_right)
    TextView mTvTitleRight;
    @BindView(R.id.rl_title_rootlayout)
    RelativeLayout mRlTitleRootlayout;

    /** 包裹 -- 选择发票类型 -- 布局 */
    @BindView(R.id.rLayout_invoice_type_submitInvoiceList)
    RelativeLayout mRLayoutInvoiceType;
    /** 选择发票类型 */
    @BindView(R.id.tv_invoice_type_submitInvoiceList)
    TextView mTvInvoiceType;

    /** 包裹 -- 选择地址 -- 布局 */
    @BindView(R.id.rLayout_address_submitInvoiceList)
    RelativeLayout mRLayoutAddressSubmitInvoiceList;
    /** 选择发票邮寄地址 */
    @BindView(R.id.tv_submitorder_address_selectadd)
    TextView mTvSelectad;
    /** 包裹 -- 选择地址后 -- 布局 */
    @BindView(R.id.rl_submitorder_address_addressinfo)
    RelativeLayout mRlAddressinfo;
    /** 选择地址后 -- 地址名 */
    @BindView(R.id.tv_submitorder_address_name)
    TextView mTvAddressName;
    /** 选择地址后 -- 电话 */
    @BindView(R.id.tv_submitorder_address_phone)
    TextView mTvAddressPhone;
    /** 选择地址后 -- 默认标志 */
    @BindView(R.id.tv_submitorder_address_default)
    TextView mTvAddressDefault;
    /** 选择地址后 -- 右方向标志 */
    @BindView(R.id.iv_submitorder_address_enter_selectadd)
    ImageView mIvAddressEnterSelectadd;
    /** 选择地址后 -- 邮寄地址 */
    @BindView(R.id.tv_submitorder_address_address)
    TextView mTvAddressAddress;

    /** 发票列表 */
    @BindView(R.id.erv_invoice_list_submitInvoiceList)
    EasyRecyclerView mErvInvoiceList;
    /** 发票合并后的价格 */
    @BindView(R.id.tv_price_submitInvoiceList)
    TextView mTvPrice;
    /** 提交按钮 */
    @BindView(R.id.btn_submit_submitInvoiceList)
    Button mBtnSubmit;


    RecyclerArrayAdapter mAdapter;


    private String mOrderIds = "";
    private String mShopName = "";
    private double mMoney = 0;
    ArrayList<InvoiceOrderBean.DataBean> mSelectList = new ArrayList<>();

    private NotifyBroadcastReceiver mBroadcastReceiver = new NotifyBroadcastReceiver();

    private int mAddressId = -1;
    private int mInvoiceId = -1;

    /** 选择了 -- "电子发票"还是"普通发票" */
    int mInvoiceType = -1;

    @Override
    protected SubmitInvoiceMergePresentImpl createPresenter() {
        return new SubmitInvoiceMergePresentImpl(this);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_submitinvoicemerge;
    }

    @Override
    protected void initData() {
        getIntentData();
        setView();
        initErv();
        setData();
    }

    @Override
    protected void initEvent() {

    }

    public void getIntentData() {
        Intent intent = getIntent();

        mOrderIds = intent.getStringExtra(INTENT_ORDER_ID);
        mShopName = intent.getStringExtra(INTENT_SHOP_NAME);
        mMoney = intent.getDoubleExtra(INTENT_MONEY, mMoney);
        mSelectList = intent.getParcelableArrayListExtra(INTENT_BEAN);

    }

    private void setView() {
        mTvTitleName.setText(mShopName);
        mTvInvoiceType.setText(getString(R.string.select_invoice_type));
        mTvSelectad.setText(getString(R.string.select_invoice_address));

        mTvPrice.setText(Utils.getString(R.string.money_unit_2,
                Utils.getFormatDoubleTwoDecimalPlaces(mMoney, 2)));

        mRLayoutAddressSubmitInvoiceList.setVisibility(View.GONE);
        mBtnSubmit.setEnabled(false);
    }

    private void initErv() {
        mErvInvoiceList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mErvInvoiceList.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.line_color),
                Utils.dip2px(mContext, 1)));
        mErvInvoiceList.setAdapterWithProgress(initAdapter());
    }


    private RecyclerArrayAdapter initAdapter(){
        return mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new InvoiceListViewHolder(parent, mContext, false, new InvoiceListViewHolder.OnCheckBoxClickListener() {
                    @Override
                    public void itemClick(int position, InvoiceOrderBean.DataBean data) {

                    }
                });          // 发票内容
            }
        };
    }

    private void setData() {
        mAdapter.addAll(mSelectList);
    }

    @OnClick({R.id.iv_title_back, R.id.btn_submit_submitInvoiceList,
            R.id.tv_invoice_type_submitInvoiceList, R.id.rLayout_address_submitInvoiceList})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;

            case R.id.tv_invoice_type_submitInvoiceList:                /* 选择发票类型 */
                intent = new Intent();
                intent.putExtra(INTENT_MODE, InvoiceTypeActivity.INVOICEMODE_SELECT);
                startActivity(InvoiceTypeActivity.class, intent);
                break;

            case R.id.rLayout_address_submitInvoiceList:                /* 选择地址 */
                intent = new Intent();
                intent.putExtra(INTENT_MODE, AddressActivity.ADDRESSMODE_SELECT);
                startActivity(AddressActivity.class, intent);
                break;

            case R.id.btn_submit_submitInvoiceList:                     /* 提交发票 */
                submit();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_ADDRESS);
        filter.addAction(BROADCAST_INVOICE);
        registerReceiver(mBroadcastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void invoiceMergeSuccess(InvoiceMergeSuccessBean bean) {
        Intent intent = new Intent();
        intent.putExtra(INTENT_BEAN, bean.getData());
        intent.putExtra(INTENT_SHOP_NAME, mShopName);
        startActivity(InvoiceStateActivity.class, intent);
    }

    public class NotifyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BROADCAST_ADDRESS)) {                 /* 改变了邮寄地址 */
                changeAddress(intent);

            }else if (action.equals(BROADCAST_INVOICE)) {
                changeInvoice(intent);
            }
        }
    }


    /**
     * 改变了邮寄地址
     * @param intent
     */
    private void changeAddress(Intent intent) {
        AddressListBean.DataEntity address = intent.getParcelableExtra(BROADCAST_DATA);
        if(address != null){
            setAddress(address);
        }
    }

    /**
     * 设置地址
     * @param entity
     */
    private void setAddress(AddressListBean.DataEntity entity) {
        if (entity != null) {
            AddressListBean.DataEntity addressEntity = entity;
            mRlAddressinfo.setVisibility(View.VISIBLE);
            mTvSelectad.setVisibility(View.GONE);
            mTvAddressDefault.setVisibility(addressEntity.getIs_default() != 0 ? View.VISIBLE : View.GONE);
            mTvAddressName.setText(addressEntity.getConsignee());
            mTvAddressAddress.setText(addressEntity.getAdd());
            mTvAddressPhone.setText(addressEntity.getMobile());
            mAddressId = addressEntity.getAddress_id();
            mBtnSubmit.setEnabled(true);
        }
    }


    /**
     * 改变了发票
     * @param intent
     */
    private void changeInvoice(Intent intent) {
        InvoiceBean.DataEntity invoice = intent.getParcelableExtra(BROADCAST_DATA);
        if (invoice == null) {
            mTvInvoiceType.setText(getString(R.string.select_invoice_type));
            mInvoiceId = -1;
            mRLayoutAddressSubmitInvoiceList.setVisibility(View.GONE);
            return;
        }

        String res = "";
        if (invoice.getBelong() == INVOICE_PERSONAL) {                  /* 个人发票 -- 都是普通发票 */
            res += Utils.getString(R.string.personal_invoice);

        } else {                                                        /* 单位发票 */
            if (invoice.getType() == INVOICE_UNIT_SPECIAL) {   // 增值专用
                res += Utils.getString(R.string.invoice_VATdedicatedinvoice);
            } else {                                           // 普通发票
                res += Utils.getString(R.string.invoice_VATordinaryinvoice);
            }
        }

        res += "-";
        if (invoice.getInvoice_type() == INVOICE_ELECTRONIC) {          /* 电子发票 */
            res += Utils.getString(R.string.invoice_electronicinvoice);
            mRLayoutAddressSubmitInvoiceList.setVisibility(View.GONE);
            mInvoiceType = INVOICE_ELECTRONIC;
            mBtnSubmit.setEnabled(true);
        } else {                                                        /* 纸质发票 */
            res += Utils.getString(R.string.invoice_pagerinvoice);
            mRLayoutAddressSubmitInvoiceList.setVisibility(View.VISIBLE);
            mInvoiceType = INVOICE_PAGER;
            if(mAddressId != -1){
                mBtnSubmit.setEnabled(true);
            }else {
                mBtnSubmit.setEnabled(false);
            }
        }

        mInvoiceId = invoice.getInvoice_id();
        mTvInvoiceType.setText(Utils.getString(R.string.choose, res));
    }


    /**
     * 提交发票
     */
    private void submit() {
        String addrStr = mAddressId + "";
        if(mInvoiceType == INVOICE_ELECTRONIC){
            addrStr = "";
        }
        mPresenter.invoiceMerge(GlobalData.getUser_id(), mOrderIds, mInvoiceId + "", addrStr);
    }

}
