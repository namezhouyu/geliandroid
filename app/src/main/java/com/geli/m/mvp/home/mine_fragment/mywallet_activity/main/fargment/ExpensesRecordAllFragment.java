package com.geli.m.mvp.home.mine_fragment.mywallet_activity.main.fargment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.ExpensesRecordBean;
import com.geli.m.coustomview.ErrorView;
import com.geli.m.mvp.base.MVPFragment;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import java.util.List;

import static com.geli.m.mvp.home.mine_fragment.mywallet_activity.main.MyWalletActivity.ALL;
import static com.geli.m.mvp.home.mine_fragment.mywallet_activity.main.MyWalletActivity.EXPENDITURE;
import static com.geli.m.mvp.home.mine_fragment.mywallet_activity.main.MyWalletActivity.SHIFT_TO;
import static com.geli.m.mvp.home.mine_fragment.mywallet_activity.main.MyWalletActivity.WALLET_BALANCE_DETAIL_TYPE;

/**
 * author:  shen
 * date:    2018/5/28
 */

public class ExpensesRecordAllFragment extends MVPFragment<ExpensesRecordAllPresentImpl>
        implements ExpensesRecordAllView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.erv_expenses_record_all_list)
    EasyRecyclerView mErv_list;

    private String mDetailType = ALL;

    int page = 1;
    private RecyclerArrayAdapter<ExpensesRecordBean.DataEntity> mAdapter;

    boolean isShow;
    boolean isFrist = true;
    private int lastOffset;
    private int lastPosition;

    @Override
    protected ExpensesRecordAllPresentImpl createPresent() {
        return new ExpensesRecordAllPresentImpl(this);
    }

    public static ExpensesRecordAllFragment newInstance(String detailType) {

        ExpensesRecordAllFragment fragment = new ExpensesRecordAllFragment();
        Bundle args = new Bundle();
        args.putString(WALLET_BALANCE_DETAIL_TYPE, detailType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        super.init();
        Bundle arguments = getArguments();
        mDetailType = arguments.getString(WALLET_BALANCE_DETAIL_TYPE);
    }

    @Override
    public int getResId() {
        return R.layout.fragment_expenses_record_all;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        initAdapter();
        initEasyRecyclerView();

        isFrist = true;
        page = 1;
    }


    /**
     *
     * @return
     */
    public String getDetailType() {
        return mDetailType;
    }


    @Override
    public void onResume() {
        super.onResume();
        isShow = true;
        if(isFrist)
            onRefresh();
    }

    @Override
    public void onPause() {
        super.onPause();
        isShow = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isFrist)
            onRefresh();
    }

    private void getData(){
        if (mAdapter != null && isShow && getUserVisibleHint()) {

            String user_id = GlobalData.getUser_id();
            if(mDetailType.equals(ALL)){
                mPresenter.getWalletDetailAll(user_id, page + "");
            }else if(mDetailType.equals(EXPENDITURE)){
                mPresenter.getWalletDetail(user_id, page + "", "1");
            }else if(mDetailType.equals(SHIFT_TO)){
                mPresenter.getWalletDetail(user_id, page + "", "2");
            }
        }
        // 金融列表测试账号的 user_id=6115b26b227bf8aa70345756aae81cdd
        // String user_id="6115b26b227bf8aa70345756aae81cdd";




//        if(mDetailType.equals(ALL)){
//            mPresenter.getWalletDetailAll(GlobalData.getUser_id(), page + "");
//        }else if(mDetailType.equals(EXPENDITURE)){
//            mPresenter.getWalletDetail(GlobalData.getUser_id(), page + "", "1");
//        }else if(mDetailType.equals(SHIFT_TO)){
//            mPresenter.getWalletDetail(GlobalData.getUser_id(), page + "", "2");
//        }
    }


    /**
     * 初始化"EasyRecyclerView"的"适配器"
     */
    private void initAdapter(){
        mAdapter = new RecyclerArrayAdapter<ExpensesRecordBean.DataEntity>(mContext) {

            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new ExpensesRecordAllViewHolder(parent, mContext);
            }
        };

        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // ExpensesRecordBean.DataEntity item = mAdapter.getItem(position);
                // ((BaseActivity) mContext).startActivity(ArticleDetailsActivity.class, new Intent().putExtra(ArticleDetailsActivity.INTENT_FINDID, item.getFind_id() + ""));
            }
        });
    }

    /**
     * 初始化"EasyRecyclerView"
     */
    private void initEasyRecyclerView(){
        mErv_list.setLayoutManager(new LinearLayoutManager(mContext));
        mErv_list.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.transparent),
                Utils.dip2px(mContext, 0.5f),
                Utils.dip2px(mContext, 15), 0));

        mErv_list.setAdapterWithProgress(mAdapter);

        EasyRecyclerViewUtils.initEasyRecyclerView(mErv_list, new ErrorView.ClickRefreshListener() {
            @Override
            public void clickRefresh() {
                page = 1;
                onRefresh();
            }
        });
        EasyRecyclerViewUtils.initAdapter(mAdapter, new EasyRecyclerViewUtils.AdapterListener() {
            @Override
            public void onMoreShow() {
                page++;
                getData();
            }

            @Override
            public void onErrorClick() {
                mAdapter.resumeMore();
            }
        });
        mErv_list.setRefreshListener(this);

        mErv_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getLayoutManager() != null) {
                    getPositionAndOffset(recyclerView.getLayoutManager());
                }
            }
        });
    }

    /**
     * 记录RecyclerView当前位置
     *
     * @param layoutManager
     */
    private void getPositionAndOffset(RecyclerView.LayoutManager layoutManager) {
        //获取可视的第一个view
        View topView = layoutManager.getChildAt(0);
        if (topView != null) {
            //获取与该view的顶部的偏移量
            lastOffset = topView.getTop();
            //得到该View的数组位置
            lastPosition = layoutManager.getPosition(topView);
        }
    }

    /**
     * 让RecyclerView滚动到指定位置
     */
    private void scrollToPosition() {
        if (mErv_list.getRecyclerView().getLayoutManager() != null && lastPosition >= 0) {
            ((LinearLayoutManager) mErv_list.getRecyclerView().getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
        }
    }


    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
    }

    @Override
    public void onError(String msg) {
        ShowSingleToast.showToast(mContext, msg);

        if (page != 1) {
            mAdapter.pauseMore();
            //因为点击重新加载更多后page会+1;出错所以现在先-1
            page = page - 1;
        } else {
            mErv_list.showError();
        }

    }

    @Override
    public void showLoading() {
        if (page == 1) {
            if (mErv_list != null) {
                mErv_list.setRefreshing(true);
            }
        }
    }

    @Override
    public void hideLoading() {
        if (page == 1) {
            if (mErv_list != null) {
                mErv_list.setRefreshing(false);
            }
        }
    }

    @Override
    public void showData(List dataEntity) {
        if (page == 1) {
            mAdapter.clear();
        }
        if (page == 1) {
            mAdapter.insertAll(dataEntity, 0);
        } else {
            mAdapter.addAll(dataEntity);
        }
        if (dataEntity.size() < 20) {
            mAdapter.stopMore();
        }
        if (isShow && getUserVisibleHint()) {
            scrollToPosition();
        }

        isFrist = false;
    }

    @Override
    public void onRefresh() {
        page = 1;
        getData();
    }
}
