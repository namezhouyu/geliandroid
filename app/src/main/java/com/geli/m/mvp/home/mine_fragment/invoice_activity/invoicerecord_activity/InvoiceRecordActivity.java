package com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicerecord_activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.InvoiceRecordBean;
import com.geli.m.coustomview.ErrorView;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.mine_fragment.invoicemerge_activity.invoicestate_activity.InvoiceStateActivity;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import java.util.ArrayList;
import java.util.List;

import static com.geli.m.config.Constant.INTENT_INVOICE_ID;
import static com.geli.m.config.Constant.INTENT_SHOP_NAME;

/**
 * author:  shen
 * date:    2018/12/26
 *
 * 发票记录
 */
public class InvoiceRecordActivity extends MVPActivity<InvoiceRecordPresentImpl> implements InvoiceRecordView {


    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title_name)
    TextView mTvTitleName;
    @BindView(R.id.tv_title_right)
    TextView mTvTitleRight;
    @BindView(R.id.rl_title_rootlayout)
    RelativeLayout mRlTitleRootlayout;

    @BindView(R.id.erv_invoice_list_activityInvoice)
    EasyRecyclerView mErvInvoiceList;

    RecyclerArrayAdapter mAdapter;

    List mList = null;
    int mPage = 1;

    private int mLastOffset;
    private int mLastPosition;

    @Override
    protected InvoiceRecordPresentImpl createPresenter() {
        return new InvoiceRecordPresentImpl(this);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_invoice_record;
    }

    @Override
    protected void initData() {
        mTvTitleName.setText(getString(R.string.invoice_record));

        initErv();
        setData();

        getData(mPage);
    }

    private void getData(int page) {
        mPresenter.invoiceRecords(GlobalData.getUser_id(), page + "");
    }

    @Override
    protected void initEvent() {

    }

    private void initErv() {
        mErvInvoiceList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mErvInvoiceList.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.transparent),
                Utils.dip2px(mContext, 10)));
        mErvInvoiceList.setAdapterWithProgress(initAdapter());


        setEmptyMessage();
        setErvOtherView();
        // mErvInvoiceList.showEmpty();
    }

    /**
     * 设置Erv的"错误、没数据"的界面，加载处理
     */
    private void setErvOtherView() {
        EasyRecyclerViewUtils.initEasyRecyclerView(mErvInvoiceList, R.layout.layout_erv_empty, -1,
                new ErrorView.ClickRefreshListener() {
                    @Override
                    public void clickRefresh() {
                        mPage = 1;
                        getData(mPage);
                    }
                });

        EasyRecyclerViewUtils.initAdapter(mAdapter, new EasyRecyclerViewUtils.AdapterListener() {
            @Override
            public void onMoreShow() {
                mPage++;
                getData(mPage);
            }

            @Override
            public void onErrorClick() {
                mAdapter.resumeMore();
            }
        });

        mErvInvoiceList.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                getData(mPage);
            }
        });

        mErvInvoiceList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (recyclerView.getLayoutManager() != null) {
                    getPositionAndOffset(recyclerView.getLayoutManager());
                }
            }
        });

    }

    private void setEmptyMessage(){
        View emptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_erv_empty,
                new LinearLayout(mContext), false);     // 为了点击事件，所以这里要填充出来获取控件

        TextView message = (TextView) emptyView.findViewById(R.id.tv_message_empty);
        message.setText(getString(R.string.empty_invoice_record));

    }

    private RecyclerArrayAdapter initAdapter(){
        mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new InvoiceRecordViewHolder(parent, mContext);          // 发票内容
            }
        };

        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                InvoiceRecordBean.DataBean bean = (InvoiceRecordBean.DataBean) mAdapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra(INTENT_INVOICE_ID, bean.getInvoice_id());
                intent.putExtra(INTENT_SHOP_NAME, bean.getShop_name());
                startActivity(InvoiceStateActivity.class, intent);
            }
        });


        return mAdapter;
    }

    /**
     * 记录RecyclerView当前位置
     *
     * @param layoutManager
     */
    private void getPositionAndOffset(RecyclerView.LayoutManager layoutManager) {

        View topView = layoutManager.getChildAt(0);         // 获取可视的第一个view
        if (topView != null) {
            mLastOffset = topView.getTop();                       // 获取与该view的顶部的偏移量
            mLastPosition = layoutManager.getPosition(topView);   // 得到该View的数组位置
        }
    }

    /**
     * 让RecyclerView滚动到指定位置
     */
    private void scrollToPosition() {
        if (mErvInvoiceList.getRecyclerView().getLayoutManager() != null && mLastPosition >= 0) {
            ((LinearLayoutManager) mErvInvoiceList.getRecyclerView().getLayoutManager())
                    .scrollToPositionWithOffset(mLastPosition, mLastOffset);
        }
    }

    private void setData() {
        mList = new ArrayList();

        mAdapter.addAll(mList);
    }


    @Override
    public void showLoading() {
        if(!mErvInvoiceList.getSwipeToRefresh().isRefreshing()){
            mErvInvoiceList.getSwipeToRefresh().setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        if(mErvInvoiceList.getSwipeToRefresh().isRefreshing()){
            mErvInvoiceList.getSwipeToRefresh().setRefreshing(false);
        }
    }


    @OnClick(R.id.iv_title_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void getInvoiceRecordsSuccess(InvoiceRecordBean data) {

            if (mPage == 1) {
                mAdapter.clear();
                if (data.getData() == null || data.getData().size() == 0) {
                    mErvInvoiceList.showEmpty();
                    return;
                }
            }
            mAdapter.addAll( data.getData());
//            if (mIsShow && getUserVisibleHint()) {
//                scrollToPosition();             // 让RecyclerView滚动到指定位置
//            }

            if (data.getData().size() < 20) {       // 本次回的数据小余 20 说明后面没有数据了
                mAdapter.stopMore();
            }

    }
}
