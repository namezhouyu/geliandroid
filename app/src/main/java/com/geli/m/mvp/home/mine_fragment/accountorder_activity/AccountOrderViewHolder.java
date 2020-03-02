package com.geli.m.mvp.home.mine_fragment.accountorder_activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.geli.m.R;
import com.geli.m.bean.AccountOrderBean;
import com.geli.m.bean.SubmitOrderNewBean;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.other.pay_activity.PayActivity;
import com.geli.m.utils.TimeUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import static com.geli.m.config.Constant.INTENT_BEAN;
import static com.geli.m.utils.TimeUtils.format12;

/**
 * author:  shen
 * date:    2018/11/15
 */
public class AccountOrderViewHolder extends BaseViewHolder<AccountOrderBean.DataBean> {

    Context mContext;
    /** 订单号 */
    @BindView(R.id.tv_order_sn_ivaorder)
    TextView mTvOrderSn;
    /** 开始时间 */
    @BindView(R.id.tv_order_start_time_ivaorder)
    TextView mTvOrderStartTime;
    /** 商品列表 */
    @BindView(R.id.erv_order_details_ivaorder)
    EasyRecyclerView mErvOrderDetails;
    /** 价格 */
    @BindView(R.id.tv_price_ivaorder)
    TextView mTvPrice;
    /** 商品数量 */
    @BindView(R.id.tv_goods_count_ivaorder)
    TextView mTvGoodsCount;
    /** 支付 */
    @BindView(R.id.btn_pay_ivaorder)
    Button mBtnPay;

    RecyclerArrayAdapter mAdapter;

    public AccountOrderViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_accountorder);

        mContext = context;

        ButterKnife.bind(this, itemView);

        initAdapter();
        initErv();
    }


    @Override
    public void setData(final AccountOrderBean.DataBean data) {
        mTvOrderSn.setText(data.getOrder_sn());
        String addTime = TimeUtils.transForDate(data.getAdd_time(), format12);
        mTvOrderStartTime.setText(addTime);
        mTvPrice.setText(Utils.getString(R.string.total_unit, data.getOrder_amount()));
        mTvGoodsCount.setText(Utils.getString(R.string.total_goods_count, data.getOrder_amount()));

        mAdapter.addAll(data.getGoods_res());

        setPay(data);

    }


    private void initAdapter() {
        mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new AccountOrderDetailsViewHolder(parent, mContext);
            }

        };
    }

    private void initErv() {
        mErvOrderDetails.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.transparent), Utils.dip2px(mContext, 0.5f), Utils.dip2px(mContext, 15), 0));
        mErvOrderDetails.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mErvOrderDetails.setAdapterWithProgress(mAdapter);
    }



    private void setPay(final AccountOrderBean.DataBean data) {
        mBtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                SubmitOrderNewBean.DataBean bean = new SubmitOrderNewBean.DataBean();
                bean.setOrder_sn(data.getOrder_sn());
                bean.setAmount(Double.valueOf(data.getOrder_amount()));
                intent.putExtra(INTENT_BEAN, bean);
                ((BaseActivity)mContext).startActivity(PayActivity.class, intent);
            }
        });
    }

}
