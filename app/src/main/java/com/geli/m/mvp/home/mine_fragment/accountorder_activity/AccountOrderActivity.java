package com.geli.m.mvp.home.mine_fragment.accountorder_activity;

import android.content.Intent;
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
import com.geli.m.bean.AccountOrderBean;
import com.geli.m.bean.SubmitOrderNewBean;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.other.pay_activity.PayActivity;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import java.util.Date;
import java.util.List;

import static com.geli.m.config.Constant.INTENT_AP_CLOSING_TIME;
import static com.geli.m.config.Constant.INTENT_BEAN;
import static com.geli.m.config.Constant.INTENT_SHOP_ID;
import static com.geli.m.config.Constant.INTENT_SHOP_NAME;

/**
 * author:  shen
 * date:    2018/11/14
 * <p>
 * 账期订单详情
 */
public class AccountOrderActivity extends MVPActivity<AccountOrderPresentImpl> implements AccountOrderView {

    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title_name)
    TextView mTvTitleName;
    @BindView(R.id.tv_title_right)
    TextView mTvTitleRight;
    @BindView(R.id.rl_title_rootlayout)
    RelativeLayout mRlTitleRootlayout;

    /** 商店名称 */
    @BindView(R.id.tv_shop_name_aorder)
    TextView mTvShopName;
    /** 顶部的提示信息 */
    @BindView(R.id.tv_toast_mess_aorder)
    TextView mTvToastMess;
    @BindView(R.id.erv_aorder)
    EasyRecyclerView mErv;
    /** 价格 */
    @BindView(R.id.tv_price_aorder)
    TextView mTvPrice;
    /** 全部结账 */
    @BindView(R.id.btn_all_checkout_aorder)
    Button mBtnAllCheckout;

    RecyclerArrayAdapter mAdapter;

    int mShopId = -1;
    String mShopName = "";
    int mCloseTime = 0;

    @Override
    protected AccountOrderPresentImpl createPresenter() {
        return new AccountOrderPresentImpl(this);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_aorder;
    }

    @Override
    protected void initData() {

        getIntentData();
        setView();
        initAdapter();
        initErv();
    }

    @Override
    protected void initEvent() {
        mPresenter.shDetail(GlobalData.getUser_id(), mShopId + "");
    }

    private void getIntentData() {
        mShopId = getIntent().getIntExtra(INTENT_SHOP_ID, mShopId);
        mShopName = getIntent().getStringExtra(INTENT_SHOP_NAME);
        mCloseTime = getIntent().getIntExtra(INTENT_AP_CLOSING_TIME, 0);
    }

    private void setView() {
        mTvTitleName.setText(Utils.getString(R.string.account_management));
        mTvShopName.setText(mShopName);
        String message = "";

        if(mCloseTime > 0){
            long currentTime = new Date().getTime();
            long time = mCloseTime - (currentTime/1000);
            if(time > 0){
                long day = time /(60 * 60 * 24);
                long h = (time / 3600) % 24;
                message = Utils.getString(R.string.you_final_settlement_day_2, day, h);
            }else {                         // 逾期
                message = Utils.getString(R.string.the_account_is_overdue);
            }
        }
        mTvToastMess.setText(message);
    }

    private void initAdapter() {
        mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new AccountOrderViewHolder(parent, mContext);
            }

        };
    }

    private void initErv() {
        mErv.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.line_color), Utils.dip2px(mContext, 0.5f), Utils.dip2px(mContext, 15), 0));
        mErv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mErv.setAdapterWithProgress(mAdapter);
    }



    @OnClick({R.id.iv_title_back, R.id.btn_all_checkout_aorder})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.iv_title_back:
                finish();
                break;

            case R.id.btn_all_checkout_aorder:              /* 全部结账 */
                checkoutAll();
                break;

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

    AccountOrderBean mBean;
    @Override
    public void shDetailSuccess(AccountOrderBean data) {
        mBean = data;
        mAdapter.addAll(data.getData());
        setPrice(data.getData());
    }


    double mTotalPrice = 0;
    private void setPrice(List<AccountOrderBean.DataBean> data){
        if(data != null){
            mTotalPrice = 0;
            for(AccountOrderBean.DataBean bean : data){
                if(StringUtils.isNotEmpty(bean.getOrder_amount())){
                    mTotalPrice += Double.valueOf(bean.getOrder_amount());
                }
            }

            String amount = Utils.getFormatDoubleTwoDecimalPlaces(mTotalPrice, 2);
            mTvPrice.setText(Utils.getString(R.string.money_unit_1, amount));
        }
    }


    /**
     * 全部结账
     */
    private void checkoutAll() {
        if(mBean != null && mBean.getData() != null && mBean.getData().size() > 0) {
            Intent intent = new Intent();
            SubmitOrderNewBean.DataBean bean = new SubmitOrderNewBean.DataBean();
            bean.setOrder_sn(getOrderSnList());
            bean.setAmount(mTotalPrice);
            intent.putExtra(INTENT_BEAN, bean);
            intent.putExtra(INTENT_AP_CLOSING_TIME, mCloseTime);
            startActivity(PayActivity.class, intent);
        }
    }


    private String getOrderSnList(){
        String orderSnList = "";
        for(AccountOrderBean.DataBean dataBean : mBean.getData()){
            orderSnList += dataBean.getOrder_sn();
        }
        return orderSnList;
    }
}
