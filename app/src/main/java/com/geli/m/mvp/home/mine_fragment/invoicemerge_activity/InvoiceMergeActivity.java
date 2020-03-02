package com.geli.m.mvp.home.mine_fragment.invoicemerge_activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.ShopInvoiceBean;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.mine_fragment.invoicemerge_activity.invoicelist_activity.InvoiceListActivity;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import static com.geli.m.config.Constant.INTENT_MONEY;
import static com.geli.m.config.Constant.INTENT_ORDER_ID;
import static com.geli.m.config.Constant.INTENT_SHOP_NAME;

/**
 * author:  shen
 * date:    2018/12/21
 * <p>
 * 发票申请
 */
public class InvoiceMergeActivity extends MVPActivity<InvoiceMergePresentImpl> implements InvoiceMergeView {


    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title_name)
    TextView mTvTitleName;
    @BindView(R.id.tv_title_right)
    TextView mTvTitleRight;
    @BindView(R.id.rl_title_rootlayout)
    RelativeLayout mRlTitleRootlayout;
    @BindView(R.id.erv_shop_list_invoicemerge)
    EasyRecyclerView mErvShopList;


    RecyclerArrayAdapter mAdapter;

    @Override
    protected InvoiceMergePresentImpl createPresenter() {
        return new InvoiceMergePresentImpl(this);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_invoicemerge;
    }

    @Override
    protected void initData() {
        mTvTitleName.setText(getString(R.string.select_company));
        initErv();
        mPresenter.shopInvoice(GlobalData.getUser_id());
    }

    @Override
    protected void initEvent() {

    }


    private void initErv() {
        mErvShopList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mErvShopList.setAdapterWithProgress(initAdapter());

        EasyRecyclerViewUtils.initEasyRecyclerView(mErvShopList, R.layout.layout_erv_empty, -1);
        // mErvList.showEmpty();

    }

    private RecyclerArrayAdapter initAdapter(){
        mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new InvoiceMergeViewHolder(parent, mContext);          // 发票内容
            }
        };

        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ShopInvoiceBean.DataBean bean = (ShopInvoiceBean.DataBean) mAdapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra(INTENT_ORDER_ID, bean.getOrder_id());
                intent.putExtra(INTENT_SHOP_NAME, bean.getShop_name());
                intent.putExtra(INTENT_MONEY, bean.getInvoice_amount());
                startActivity(InvoiceListActivity.class, intent);
            }
        });

        return mAdapter;
    }

    @OnClick(R.id.iv_title_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void showLoading() {
        if (!mErvShopList.getSwipeToRefresh().isRefreshing()) {
            mErvShopList.setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        mErvShopList.setRefreshing(false);
    }

    @Override
    public void setShopInvoice(ShopInvoiceBean data) {
        if(data != null && data.getData() != null) {
            mAdapter.addAll(data.getData());
        }else {
            mErvShopList.showEmpty();
        }
    }
}
