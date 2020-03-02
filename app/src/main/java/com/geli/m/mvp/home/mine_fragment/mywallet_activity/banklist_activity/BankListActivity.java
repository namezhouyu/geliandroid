package com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.BankListBean;
import com.geli.m.config.Constant;
import com.geli.m.coustomview.ErrorView;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity.addbank_activity.AddBankActivity;
import com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity.bankdetails_activity.BankDetailsActivity;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.List;

/**
 * Created by Steam_l on 2018/1/18.
 */

public class BankListActivity extends MVPActivity<BankListPresentImpl> implements View.OnClickListener, BankListView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.erv_bank_list)
    EasyRecyclerView erv_list;
    @BindView(R.id.tv_title_name)
    TextView tv_title;
    private RecyclerArrayAdapter mAdapter;
    private int page;
    public static String INTENT_TYPE = "intent_type";
    public static String TYPE_SELECT = "type_select";
    public static String TYPE_VIEW = "type_view";
    public static String TYPE_ADD = "type_add";

    public static String INTENT_DATA = "intent_data";
    private String curr_type = TYPE_VIEW;

    @Override
    protected int getResId() {
        return R.layout.activity_banklist;
    }

    @Override
    protected void init() {
        super.init();
        curr_type = getIntent().getStringExtra(INTENT_TYPE);
        if (curr_type != null && curr_type.equals(TYPE_ADD)) {
            startActivity(AddBankActivity.class, new Intent());
        }
    }

    @Override
    protected void initData() {
        tv_title.setText(Utils.getString(R.string.bank));

        erv_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        erv_list.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = Utils.dip2px(mContext, 15);
                outRect.right = Utils.dip2px(mContext, 15);
                outRect.top = Utils.dip2px(mContext, 10);
                if (parent.getChildAdapterPosition(view) == mAdapter.getAllData().size() - 1) {
                    outRect.bottom = Utils.dip2px(mContext, 10);
                } else {
                    outRect.bottom = 0;
                }
            }
        });
        erv_list.setAdapterWithProgress(mAdapter = new RecyclerArrayAdapter<BankListBean.DataEntity>(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new BankViewHolder(parent, mContext);
            }

        });
        erv_list.setRefreshListener(this);
        EasyRecyclerViewUtils.initEasyRecyclerView(erv_list, new ErrorView.ClickRefreshListener() {
            @Override
            public void clickRefresh() {
                onRefresh();
            }
        });
        EasyRecyclerViewUtils.initAdapter(mAdapter, new EasyRecyclerViewUtils.AdapterListener() {
            @Override
            public void onMoreShow() {
                super.onMoreShow();
                page++;
                getList();
            }

            @Override
            public void onErrorClick() {
                mAdapter.resumeMore();
            }
        });
    }

    @Override
    protected void initEvent() {
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                BankListBean.DataEntity item = (BankListBean.DataEntity) mAdapter.getItem(position);
                if (curr_type != null && (curr_type.equals(TYPE_SELECT) || curr_type.equals(TYPE_ADD))) {
                    Intent data = new Intent();
                    data.putExtra(INTENT_DATA, item);
                    setResult(Constant.REQUEST_OK, data);
                    finish();
                } else {
                    startActivity(BankDetailsActivity.class, new Intent().putExtra(BankDetailsActivity.INTENT_BANKID, item.getBk_id()));
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    @OnClick({R.id.rl_banklist_bottom, R.id.iv_title_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.rl_banklist_bottom:
                startActivity(AddBankActivity.class, new Intent());
                break;

            default:
                break;
        }
    }

    @Override
    protected BankListPresentImpl createPresenter() {
        return new BankListPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
    }

    @Override
    public void onError(String msg) {
        ShowSingleToast.showToast(mContext, msg);
        if (page != 1) {
            page = page - 1;
            mAdapter.pauseMore();
        } else {
            erv_list.showError();
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
    public void showList(List<BankListBean.DataEntity> dataEntities) {
        if (page == 1) {
            mAdapter.clear();
            if (null == dataEntities || dataEntities.size() == 0) {
                erv_list.showEmpty();
                return;
            }
        }
        mAdapter.addAll(dataEntities);
        if (dataEntities.size() < 20) {
            mAdapter.stopMore();
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        getList();
    }

    private void getList() {
        mPresenter.getList(GlobalData.getUser_id());
    }
}
