package com.geli.m.mvp.home.mine_fragment.accountmanagement_activity.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import butterknife.BindView;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.AccountManagementBean;
import com.geli.m.coustomview.ErrorView;
import com.geli.m.mvp.base.MVPFragment;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import static com.geli.m.config.Constant.AP_Already_Opened;
import static com.geli.m.config.Constant.AP_Open_State;

/**
 * author:  shen
 * date:    2018/11/13
 */
public class AMFragment extends MVPFragment<AMPresentImpl>
        implements AMView, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.erv_am)
    EasyRecyclerView mErvList;

    RecyclerArrayAdapter mAdapter = null;

    int mOpenState = AP_Already_Opened;


    @Override
    protected AMPresentImpl createPresent() {
        return new AMPresentImpl(this);
    }

    /**
     * @param apOpenState  1开通了,2未开通
     * @return
     */
    public static AMFragment newInstance(int apOpenState) {
        AMFragment amFragment = new AMFragment();
        Bundle args = new Bundle();
        args.putInt(AP_Open_State, apOpenState);
        amFragment.setArguments(args);
        return amFragment;
    }

    @Override
    protected void init() {
        super.init();
        Bundle arguments = getArguments();
        mOpenState = arguments.getInt(AP_Open_State);
    }


    @Override
    public int getResId() {
        return R.layout.fragment_am;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        initAdapter();
        initErv();
        onRefresh();
    }

    /**
     * 设置适配器
     */
    private void initAdapter() {
        mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                if(viewType == AP_Already_Opened){
                    return new ApOpenedViewHolder(parent, mContext);
                }else {
                    return new ApNotOpenViewHolder(parent, mContext);
                }
            }

            @Override
            public int getViewType(int position) {
                return mOpenState;
            }
        };

        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }

    /**
     * 设置erv的布局和适配器，刷新的处理
     */
    private void initErv() {
        mErvList.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.line_color), Utils.dip2px(mContext, 0.5f), Utils.dip2px(mContext, 15), 0));
        mErvList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mErvList.setAdapterWithProgress(mAdapter);

        mErvList.setRefreshListener(this);                                  // 下拉刷新

        EasyRecyclerViewUtils.initEasyRecyclerView(mErvList, new ErrorView.ClickRefreshListener() {

            @Override
            public void clickRefresh() {
                onRefresh();
            }
        });
    }

    @Override
    public void showLoading() {
        if (mErvList != null) {
            mErvList.setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {

        if (mErvList != null) {
            mErvList.setRefreshing(false);
        }
    }

    @Override
    public void getShListSuccess(AccountManagementBean data) {
        mAdapter.clear();
        mAdapter.addAll(data.getData());
    }

    // 下拉刷新
    @Override
    public void onRefresh() {
        mPresenter.getShList(GlobalData.getUser_id(), mOpenState + "");
    }


    @Override
    public void onSuccess(String msg) {

    }
}
