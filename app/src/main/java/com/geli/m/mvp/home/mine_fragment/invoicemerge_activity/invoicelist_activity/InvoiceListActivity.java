package com.geli.m.mvp.home.mine_fragment.invoicemerge_activity.invoicelist_activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
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
import com.geli.m.bean.InvoiceOrderBean;
import com.geli.m.dialog.TipDialog;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.mine_fragment.invoicemerge_activity.invoicelist_activity.subminInvoicemerge_activity.SubmitInvoiceMergeActivity;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.ResourceUtil;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import java.util.ArrayList;
import java.util.List;

import static com.geli.m.config.Constant.INTENT_BEAN;
import static com.geli.m.config.Constant.INTENT_MONEY;
import static com.geli.m.config.Constant.INTENT_ORDER_ID;
import static com.geli.m.config.Constant.INTENT_SHOP_NAME;

/**
 * author:  shen
 * date:    2018/12/24
 *
 * 发票列表 -- 可以合并的发票
 */
public class InvoiceListActivity extends MVPActivity<InvoiceListPresentImpl> implements InvoiceListView {

    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title_name)
    TextView mTvTitleName;
    @BindView(R.id.tv_title_right)
    TextView mTvTitleRight;
    @BindView(R.id.rl_title_rootlayout)
    RelativeLayout mRlTitleRootlayout;
    @BindView(R.id.erv_invoice_list_invoiceList)
    EasyRecyclerView mErvInvoiceList;

    @BindView(R.id.btn_merge_invoiceList)
    Button mBtnMerge;

    /** 订单id使用","分开 */
    private String mOrderIds = "";
    private String mShopName = "";
    /** 达到这个"价格"才可以合并 */
    private String mInvoiceAmount = "";

    RecyclerArrayAdapter mAdapter;

    /** 是否符合合并的要求 */
    private boolean isCompliance = false;
    private double mMoney = 0;

    ArrayList<InvoiceOrderBean.DataBean> mSelectList = new ArrayList<>();

    @Override
    protected InvoiceListPresentImpl createPresenter() {
        return new InvoiceListPresentImpl(this);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_invoicelist;
    }

    @Override
    protected void initData() {
        getIntentData();
        setView();
        initErv();
        mPresenter.invoiceOrder(GlobalData.getUser_id(), mOrderIds);
    }


    @Override
    protected void initEvent() {

    }


    public void getIntentData() {
        Intent intent = getIntent();
        mOrderIds = intent.getStringExtra(INTENT_ORDER_ID);
        mShopName = intent.getStringExtra(INTENT_SHOP_NAME);
        mInvoiceAmount = intent.getStringExtra(INTENT_MONEY);
    }

    private void setView() {
        mTvTitleRight.setText(getString(R.string.all_select));
        mTvTitleName.setText(mShopName);

        mBtnMerge.setBackgroundResource(ResourceUtil.getDrawableResIDByName(mContext, "shape_gray"));
    }

    private void initErv() {
        mErvInvoiceList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mErvInvoiceList.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.line_color),
                Utils.dip2px(mContext, 1)));
        mErvInvoiceList.setAdapterWithProgress(initAdapter());


        EasyRecyclerViewUtils.initEasyRecyclerView(mErvInvoiceList, R.layout.layout_erv_empty, -1);

        mErvInvoiceList.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.invoiceOrder(GlobalData.getUser_id(), mOrderIds);
            }
        });
    }


    private RecyclerArrayAdapter initAdapter(){
        mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new InvoiceListViewHolder(parent, mContext, true,
                        new InvoiceListViewHolder.OnCheckBoxClickListener() {
                            @Override
                            public void itemClick(int position, InvoiceOrderBean.DataBean data) {
                                setButton();
                            }
                        });          // 发票内容
            }
        };

        // 添加"脚布局"
        mAdapter.addFooter(new FoodText());

        return  mAdapter;
    }

    /**
     * 设置按钮
     */
    private void setButton(){
        List<InvoiceOrderBean.DataBean> list = mAdapter.getAllData();
        mMoney = 0;
        mSelectList.clear();
        for(InvoiceOrderBean.DataBean bean : list){
            if(bean.isClick()) {
                mMoney += StringUtils.isEmpty(bean.getSum_amount()) ? 0 : Double.valueOf(bean.getSum_amount());
                mSelectList.add(bean);
            }
        }

        // LogUtils.i("money:" + mMoney);

        double money = StringUtils.isEmpty(mInvoiceAmount) ? 0 : Double.valueOf(mInvoiceAmount);

        if(mMoney > money){
            isCompliance = true;
            mBtnMerge.setBackgroundResource(ResourceUtil.getDrawableResIDByName(mContext, "selector_btn_bg_red_gray_enabled"));
        }else {
            isCompliance = false;
            mBtnMerge.setBackgroundResource(ResourceUtil.getDrawableResIDByName(mContext, "shape_gray"));
        }
    }

    class FoodText implements RecyclerArrayAdapter.ItemView{

        @Override
        public View onCreateView(ViewGroup parent) {
            return LayoutInflater.from(mContext).inflate(R.layout.include_invoicr_list_foot, parent, false);
        }

        @Override
        public void onBindView(View headerView) {

        }
    }


    private void showTipDialog() {
        String src = Utils.getString(R.string.invoice_merge_condition);

        TipDialog dialog = TipDialog.newInstance(src);
        dialog.isShowTitle(false);
        dialog.isShowCancel(false);
        dialog.setConfirmSrc("确定");
        dialog.setConfirmTextColor(Utils.getColor(R.color.zhusediao));

        dialog.setOnclickListener(new TipDialog.ClickListenerInterface() {
            @Override
            public void doConfirm(TipDialog tipDialog) {
                goSubmitInvoiceMerge();
                tipDialog.dismiss();
            }

            @Override
            public void doCancel() {

            }
        });
        dialog.show(getSupportFragmentManager(), "TipDialog");
    }

    @OnClick({R.id.iv_title_back, R.id.tv_title_right, R.id.btn_merge_invoiceList})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.iv_title_back:
                finish();
                break;

            case R.id.tv_title_right:                   /* 全选 */
                selectAll();
                break;


            case R.id.btn_merge_invoiceList:            /* 合并发票 */
                merge();
                break;
        }
    }

    /**
     * 合并
     */
    private void merge() {
        if(mSelectList.size() > 0) {         // 一张也可以合并
            if(isCompliance){
                goSubmitInvoiceMerge();
            }else {
                ToastUtils.showToast(Utils.getString(R.string.invoice_merge_condition_1, mInvoiceAmount));
            }
        }else {
            ToastUtils.showToast(Utils.getString(R.string.select_invoice));
        }

    }

    /**
     * 全选/取消
     */
    private void selectAll() {
        if(mTvTitleRight.getText().toString().equals(getString(R.string.all_select))){
            mTvTitleRight.setText(getString(R.string.cancel));
            for (int i = 0; i < mAdapter.getAllData().size(); i++) {
                ((InvoiceOrderBean.DataBean) mAdapter.getAllData().get(i)).setClick(true);
            }
        }else {
            mTvTitleRight.setText(getString(R.string.all_select));
            for (int i = 0; i < mAdapter.getAllData().size(); i++) {
                ((InvoiceOrderBean.DataBean) mAdapter.getAllData().get(i)).setClick(false);
            }
        }

        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void showLoading() {
        if (!mErvInvoiceList.getSwipeToRefresh().isRefreshing()) {
            mErvInvoiceList.setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        mErvInvoiceList.setRefreshing(false);
    }

    @Override
    public void onError(String msg) {
        super.onError(msg);
    }

    private void goSubmitInvoiceMerge(){
        String orderIds = "";
        for(InvoiceOrderBean.DataBean bean : mSelectList){
            orderIds += bean.getOrder_id() + ",";
        }
        if(orderIds.length() > 0 && orderIds.endsWith(",")){
            orderIds = orderIds.substring(0, orderIds.length() - 1);
        }

        Intent intent = new Intent();
        intent.putExtra(INTENT_ORDER_ID, orderIds);
        intent.putExtra(INTENT_SHOP_NAME, mShopName);
        intent.putExtra(INTENT_MONEY, mMoney);
        intent.putParcelableArrayListExtra(INTENT_BEAN, mSelectList);
        startActivity(SubmitInvoiceMergeActivity.class, intent);
    }

    @Override
    public void setInvoiceOrder(InvoiceOrderBean data) {
        if(data != null && data.getData() != null){
            mAdapter.clear();
            mAdapter.addAll(data.getData());
        }else {
            mErvInvoiceList.showEmpty();
        }
    }
}
