package com.geli.m.mvp.home.mine_fragment.invoicemerge_activity.invoicestate_activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.InvoiceMergeSuccessBean;
import com.geli.m.bean.InvoiceOrderBean;
import com.geli.m.bean.InvoiceStateBean;
import com.geli.m.dialog.DownloadInvoiceDialog;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.mine_fragment.invoicemerge_activity.invoicelist_activity.InvoiceListViewHolder;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.TimeUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import static com.geli.m.config.Constant.INTENT_BEAN;
import static com.geli.m.config.Constant.INTENT_INVOICE_ID;
import static com.geli.m.config.Constant.INTENT_SHOP_NAME;
import static com.geli.m.config.Constant.INVOICE_ELECTRONIC;
import static com.geli.m.config.Constant.INVOICE_PAGER;
import static com.geli.m.config.Constant.INVOICE_PERSONAL;
import static com.geli.m.config.Constant.INVOICE_State_Success;
import static com.geli.m.config.Constant.INVOICE_State_Wait;
import static com.geli.m.config.Constant.INVOICE_UNIT_SPECIAL;
import static com.geli.m.utils.TimeUtils.format11;

/**
 * author:  shen
 * date:    2018/12/26
 */
public class InvoiceStateActivity extends MVPActivity<InvoiceStatePresentImpl> implements InvoiceStateView {


    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title_name)
    TextView mTvTitleName;
    @BindView(R.id.tv_title_right)
    TextView mTvTitleRight;
    @BindView(R.id.rl_title_rootlayout)
    RelativeLayout mRlTitleRootlayout;

    @BindView(R.id.layout_invoice_info_activityInvoiceState)
    LinearLayout mLayoutInvoiceInfo;
    /** 包裹 -- 发票状态 -- 布局 */
    @BindView(R.id.layout_state_activityInvoiceState)
    LinearLayout mLayoutState;
    /** 发票状态 */
    @BindView(R.id.tv_state_activityInvoiceState)
    TextView mTvState;

    @BindView(R.id.cLayout_invoice_info_activityInvoiceState)
    ConstraintLayout mCLayoutInvoiceInfo;
    /** 发票类型 */
    @BindView(R.id.tv_invoice_type_activityInvoiceState)
    TextView mTvInvoiceType;
    /** 发票抬头 */
    @BindView(R.id.tv_invoice_company_activityInvoiceState)
    TextView mTvInvoiceCompany;
    /** 发票开具时间 */
    @BindView(R.id.tv_invoice_time_activityInvoiceState)
    TextView mTvInvoiceTime;
    /** 发票开具时间 / 申请合并时间 */
    @BindView(R.id.tv_invoice_time_temp_activityInvoiceState)
    TextView mTvInvoiceTimeTemp;


    /** 包裹 -- 收货人/邮寄地址 -- 布局 */
    @BindView(R.id.cLayout_invoice_addr_activityInvoiceState)
    ConstraintLayout mCLayoutInvoiceAddr;
    /** 收货人 */
    @BindView(R.id.tv_invoice_receiver_activityInvoiceState)
    TextView mTvInvoiceReceiver;
    /** 邮寄地址 */
    @BindView(R.id.tv_invoice_address_activityInvoiceState)
    TextView mTvInvoiceAddress;

    /** 发票列表 */
    @BindView(R.id.erv_invoice_list_activityInvoiceState)
    EasyRecyclerView mErvInvoiceList;

    /** 发票下载 */
    @BindView(R.id.btn_down_invoice_activityInvoiceState)
    Button mBtnDownInvoice;

    RecyclerArrayAdapter mAdapter;

    /** 当前发票的材质 -- 电子发票/纸质发票 */
    int mCurInvoiceType = INVOICE_ELECTRONIC;

    int mInvoiceId = -1;
    String mShopName = "";
    InvoiceMergeSuccessBean.DataBean mBean = null;
    private String mInvoiceImg;

    @Override
    protected InvoiceStatePresentImpl createPresenter() {
        return new InvoiceStatePresentImpl(this);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_invoicestate;
    }

    @Override
    protected void initData() {
        getIntentData();
        initErv();
        mTvTitleName.setText(mShopName);

        if(mInvoiceId != -1){
            mPresenter.invoiceDetail(GlobalData.getUser_id(), mInvoiceId + "");
        }else if(mBean !=  null){
            arrangementData(mBean);
        }
    }


    @Override
    protected void initEvent() {

    }


    public void getIntentData() {
        Intent intent = getIntent();
        mInvoiceId = intent.getIntExtra(INTENT_INVOICE_ID, mInvoiceId);
        mShopName = intent.getStringExtra(INTENT_SHOP_NAME);
        mBean = intent.getParcelableExtra(INTENT_BEAN);
    }

    /**
     * 整理传递过来的数据
     * @param bean
     */
    private void arrangementData(InvoiceMergeSuccessBean.DataBean bean){
        InvoiceStateBean.DataBean.InvoiceResBean invoiceResBean = new InvoiceStateBean.DataBean.InvoiceResBean();
        invoiceResBean.setBelong(bean.getInvoice_res().getBelong());
        invoiceResBean.setAdd_time(bean.getAdd_time());
        invoiceResBean.setInvoice_type(bean.getInvoice_res().getInvoice_type());
        invoiceResBean.setStatus(INVOICE_State_Wait);
        invoiceResBean.setType(bean.getInvoice_res().getType());
        invoiceResBean.setSend_address(bean.getAddress_res());


        setView(invoiceResBean);
        mAdapter.addAll(bean.getOrder_res());
    }

    private void setView(InvoiceStateBean.DataBean.InvoiceResBean bean) {

        if(bean != null){
            mTvInvoiceType.setText(setInvoiceType(bean));
            mTvInvoiceCompany.setText(bean.getName());
            mTvInvoiceTime.setText(TimeUtils.transForDate(bean.getAdd_time(), format11));

            mCurInvoiceType = bean.getInvoice_type();
            if (mCurInvoiceType == INVOICE_ELECTRONIC) {               /* 电子发票 */
                mCLayoutInvoiceAddr.setVisibility(View.GONE);
                mInvoiceImg = bean.getInvoice_img();
                if(StringUtils.isNotEmpty(mInvoiceImg)){
                    mBtnDownInvoice.setVisibility(View.VISIBLE);
                }else {
                    mBtnDownInvoice.setVisibility(View.GONE);
                }

            } else if (mCurInvoiceType == INVOICE_PAGER) {              /* 纸质发票 */
                mBtnDownInvoice.setVisibility(View.GONE);

                if(bean.getSend_address() != null){
                    mCLayoutInvoiceAddr.setVisibility(View.VISIBLE);
                    mTvInvoiceReceiver.setText(bean.getSend_address().getConsignee());
                    mTvInvoiceAddress.setText(bean.getSend_address().getAddress());
                }else {
                    mCLayoutInvoiceAddr.setVisibility(View.VISIBLE);
                    mTvInvoiceReceiver.setText("");
                    mTvInvoiceAddress.setText("");
                }

            }
            setState(bean);
        }
    }

    /**
     * 设置状态
     */
    private void setState(InvoiceStateBean.DataBean.InvoiceResBean bean) {
        Resources resources = mContext.getResources();
        Drawable drawable = null;
        String state = "";
        String stateText = "";

        if (bean.getStatus() == INVOICE_State_Wait) {
            drawable = resources.getDrawable(R.drawable.icon_shenqingzhuangtai);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            state = getString(R.string.application_submitted);
            stateText = getString(R.string.merge_invoice_time);

        } else if (bean.getStatus() == INVOICE_State_Success){
            drawable = resources.getDrawable(R.drawable.icon_tijiaochenggong80_white);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            state = getString(R.string.successful_booking);
            stateText = getString(R.string.invoice_time);
        }

        mTvState.setCompoundDrawables(drawable, null, null, null);
        mTvState.setText(state);
        mTvInvoiceTimeTemp.setText(stateText);
    }


    private void initErv() {
        mErvInvoiceList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mErvInvoiceList.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.transparent),
                Utils.dip2px(mContext, 1)));
        mErvInvoiceList.setAdapterWithProgress(initAdapter());
    }

    private RecyclerArrayAdapter initAdapter() {
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

    @Override
    public void showLoading() {
        Utils.uProgressDialog(mContext);
    }

    @Override
    public void hideLoading() {
        Utils.dismissProgress();
    }

    @OnClick({R.id.iv_title_back, R.id.btn_down_invoice_activityInvoiceState})
    public void onViewClicked(View view) {

        switch (view.getId()){
            case R.id.iv_title_back:
                finish();
                break;

            case R.id.btn_down_invoice_activityInvoiceState:
                DownloadInvoiceDialog.newInstance(mInvoiceImg).show(getSupportFragmentManager(), "");
                break;
        }
    }

    @Override
    public void getInvoiceDetail(InvoiceStateBean.DataBean data) {
        if(data != null){
            setView(data.getInvoice_res());
            mAdapter.addAll(data.getOrder_res());
        }
    }


    /**
     * 发票类型
     * @param data
     */
    private String setInvoiceType(InvoiceStateBean.DataBean.InvoiceResBean data) {

        String res = "";
        if (data.getBelong() == INVOICE_PERSONAL) {                     /* 个人发票 -- 都是普通发票 */
            res += Utils.getString(R.string.personal_invoice);

        } else {                                                        /* 单位发票 */
            if (data.getType() == INVOICE_UNIT_SPECIAL) {       // 增值专用
                res += Utils.getString(R.string.invoice_VATdedicatedinvoice);
            } else {                                           // 普通发票
                res += Utils.getString(R.string.invoice_VATordinaryinvoice);
            }
        }

        res += " ";
        if (data.getInvoice_type() == INVOICE_ELECTRONIC) {             /* 电子发票 */
            res += Utils.getString(R.string.invoice_electronicinvoice);
        } else {                                                        /* 纸质发票 */
            res += Utils.getString(R.string.invoice_pagerinvoice);

        }
        return res;
    }
}
