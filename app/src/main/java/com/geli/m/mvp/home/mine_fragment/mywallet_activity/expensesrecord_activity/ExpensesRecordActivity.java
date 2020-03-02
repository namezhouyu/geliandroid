package com.geli.m.mvp.home.mine_fragment.mywallet_activity.expensesrecord_activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.ExpensesBean;
import com.geli.m.coustomview.ErrorView;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import java.util.List;

/**
 * Created by Steam_l on 2018/3/27.
 *
 * 消费记录
 */

public class ExpensesRecordActivity extends MVPActivity<ExpensesRecordPresentImpl>
        implements View.OnClickListener, ExpensesRecordView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.tv_title_name)
    TextView tv_title;
    @BindView(R.id.erv_expensesrecord_list)
    EasyRecyclerView erv_list;
    private int page = 1;
    private RecyclerArrayAdapter mAdapter;

    @Override
    protected int getResId() {
        return R.layout.activity_expensesrecord;
    }

    @Override
    protected void initData() {
        tv_title.setText(Utils.getString(R.string.expenses_record));
        erv_list.setLayoutManager(new LinearLayoutManager(mContext));
        erv_list.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.backgroundColor), Utils.dip2px(mContext, 1), Utils.dip2px(mContext, 15), 0));
        mAdapter = new RecyclerArrayAdapter(mContext) {
            private static final int TYPE_TIME = 1 << 0;
            private static final int TYPE_EXPENSES = 1 << 1;

            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                if (viewType == TYPE_TIME) {
                    return new ExpensesTimeViewHolder(parent, mContext);
                }
                return new ExpensesRecordViewHolder(parent, mContext);
            }

            @Override
            public int getViewType(int position) {
                Object item = getItem(position);
                if (item instanceof String) {
                    return TYPE_TIME;
                }
                return TYPE_EXPENSES;
            }
        };
        erv_list.setAdapterWithProgress(mAdapter);
        erv_list.setRefreshListener(this);
        EasyRecyclerViewUtils.initAdapter(mAdapter, new EasyRecyclerViewUtils.AdapterListener() {
            @Override
            public void onMoreShow() {
                super.onMoreShow();
                page++;
                getExpenses();
            }

            @Override
            public void onErrorClick() {
                super.onErrorClick();
                mAdapter.resumeMore();
            }
        });
        EasyRecyclerViewUtils.initEasyRecyclerView(erv_list, new ErrorView.ClickRefreshListener() {
            @Override
            public void clickRefresh() {
                onRefresh();
            }
        });
        onRefresh();
    }

    @Override
    protected void initEvent() {
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (!(mAdapter.getItem(position) instanceof String)) {
                    ExpensesBean.DataEntity.ConsumptionEntity entity = (ExpensesBean.DataEntity.ConsumptionEntity) mAdapter.getItem(position);
                    startActivity(ExpensesDetailsActivity.class, new Intent().putExtra(ExpensesDetailsActivity.INTENT_DATA, entity));
                }
            }
        });
    }

    @Override
    protected ExpensesRecordPresentImpl createPresenter() {
        return new ExpensesRecordPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
    }

    @Override
    public void onError(String msg) {
        ShowSingleToast.showToast(mContext, msg);
        if (page != 1) {
            page--;
            mAdapter.pauseMore();
        }
    }

    @Override
    public void showLoading() {
        if (page == 1) {
            erv_list.setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        if (page == 1) {
            erv_list.setRefreshing(false);
        }
    }

    @Override
    public void showData(List entityList) {
        if (page == 1) {
            mAdapter.clear();
            if (null == entityList || entityList.size() == 0) {
                erv_list.showEmpty();
                return;
            }
        }
        mAdapter.addAll(entityList);
        if (entityList.size() < 20) {
            mAdapter.stopMore();
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        getExpenses();
    }

    private void getExpenses() {
        mPresenter.getExpenses(GlobalData.getUser_id(), page + "");
    }

    @OnClick({R.id.iv_title_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;

            default:
                break;
        }
    }
}
