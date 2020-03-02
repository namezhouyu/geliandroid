package com.geli.m.mvp.home.find_fragment.findlist_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.FindListBean;
import com.geli.m.coustomview.ErrorView;
import com.geli.m.mvp.base.MVPFragment;
import com.geli.m.mvp.home.find_fragment.findlist_fragment.details.ArticleDetailsActivity;
import com.geli.m.mvp.home.find_fragment.findlist_fragment.details.VideoDetailsActivity;
import com.geli.m.mvp.home.find_fragment.findlist_fragment.vh.FindListArticleViewHolder;
import com.geli.m.mvp.home.find_fragment.findlist_fragment.vh.FindListVideoViewHolder;
import com.geli.m.mvp.home.find_fragment.main.FindFragment;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.geli.m.config.Constant.INTENT_FIND_ID;

/**
 * Created by Steam_l on 2018/3/21.
 *
 * 发现每个类型
 */
public class FindListFragment extends MVPFragment<FindListPresentImpl> implements FindListView, SwipeRefreshLayout.OnRefreshListener {

    public static final String ARG_CATID = "arg_catid";

    @BindView(R.id.erv_find)
    EasyRecyclerView mErvContent;

    private String cat_id;
    int page = 1;
    private RecyclerArrayAdapter<FindListBean.DataEntity> mAdapter;
    private int mLastOffset;
    private int mLastPosition;

    /**
     * 当前点赞的"项下标"
     */
    int mLikePosition;

    public boolean isShow;


    public static FindListFragment newInstance(String cat_id) {

        FindListFragment findListFragment = new FindListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATID, cat_id);
        findListFragment.setArguments(args);
        return findListFragment;
    }

    public void setCatId(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCatId() {
        return cat_id;
    }


    @Override
    public int getResId() {
        return R.layout.fragment_findlist;
    }

    @Override
    protected void init() {
        super.init();
        Bundle arguments = getArguments();
        cat_id = arguments.getString(ARG_CATID);
    }

    @Override
    protected void initData() {
        setAdapter();
        setErv();
    }

    @Override
    protected void initEvent() {
    }

    private void setAdapter() {
        mAdapter = new RecyclerArrayAdapter<FindListBean.DataEntity>(mContext) {
            public final int type_video = 1;
            public final int type_article = 2;

            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                if (viewType == type_video) {
                    return new FindListVideoViewHolder(parent, mContext, FindListFragment.this);
                }
                return new FindListArticleViewHolder(parent, mContext, FindListFragment.this);
            }

            @Override
            public int getViewType(int position) {
                FindListBean.DataEntity dataEntity = getAllData().get(position);
                if (dataEntity.getView_type() == 1) {           // 1：视频 2：文章
                    return type_video;
                } else {
                    return type_article;
                }
            }
        };

        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FindListBean.DataEntity item = mAdapter.getItem(position);
                if (item.getType() == 1) {
                    startActivity(VideoDetailsActivity.class, new Intent().putExtra(INTENT_FIND_ID, item.getFind_id() + ""));
                } else {
                    startActivity(ArticleDetailsActivity.class, new Intent().putExtra(INTENT_FIND_ID, item.getFind_id() + ""));
                }
            }
        });
    }


    private void setErv() {
        mErvContent.setLayoutManager(new LinearLayoutManager(mContext));
        mErvContent.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.line_color), Utils.dip2px(mContext, 0.5f), Utils.dip2px(mContext, 15), 0));
        mErvContent.setAdapterWithProgress(mAdapter);
        EasyRecyclerViewUtils.initEasyRecyclerView(mErvContent, new ErrorView.ClickRefreshListener() {
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
                onRefresh();
            }

            @Override
            public void onErrorClick() {
                mAdapter.resumeMore();
            }
        });
        mErvContent.setRefreshListener(this);

        mErvContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
     * 点赞
     * @param position
     */
    public void like(int position) {
        mLikePosition = position;
        FindListBean.DataEntity item = mAdapter.getItem(position);
        mPresenter.like(GlobalData.getUser_id(), item.getFind_id() + "");
    }

    /**
     * 记录RecyclerView当前位置
     *
     * @param layoutManager
     */
    private void getPositionAndOffset(RecyclerView.LayoutManager layoutManager) {

        View topView = layoutManager.getChildAt(0);           // 获取可视的第一个view
        if (topView != null) {
            mLastOffset = topView.getTop();                         // 获取与该view的顶部的偏移量
            mLastPosition = layoutManager.getPosition(topView);     // 得到该View的数组位置
        }
    }

    /**
     * 让RecyclerView滚动到指定位置
     */
    private void scrollToPosition() {
        if (mErvContent.getRecyclerView().getLayoutManager() != null && mLastPosition >= 0) {
            ((LinearLayoutManager) mErvContent.getRecyclerView().getLayoutManager())
                    .scrollToPositionWithOffset(mLastPosition, mLastOffset);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isShow = true;
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
        onRefresh();
    }

    @Override
    protected FindListPresentImpl createPresent() {
        return new FindListPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showToast(msg);

        /* 取消点赞、点赞失败 */
        if (mPresenter.isLike) {
            mPresenter.isLike = false;
            FindListBean.DataEntity item = mAdapter.getItem(mLikePosition);

            // 根据 "取消点赞/点赞" 处理
            item.setIs_like(item.getIs_like() == 1 ? 0 : 1);
            if (item.getIs_like() == 1) {
                item.setLike_num(item.getLike_num() + 1);
            } else {
                item.setLike_num(item.getLike_num() - 1);
            }
            mAdapter.update(item, mLikePosition);
            return;
        }

        /* 刷新数据失败 */
        if (page != 1) {
            mAdapter.pauseMore();
            page = page - 1;                 // 因为点击重新加载更多后page会+1;出错所以现在先-1
        } else {
            mErvContent.showError();
        }

    }

    @Override
    public void showLoading() {
        if (page == 1) {
            if (mErvContent != null) {
                mErvContent.setRefreshing(true);
            }
        }
    }

    @Override
    public void hideLoading() {
        if (page == 1) {
            if (mErvContent != null) {
                mErvContent.setRefreshing(false);
            }
        }
    }

    @Override
    public void showList(List<FindListBean.DataEntity> dataEntity) {
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
            scrollToPosition();                     // 让RecyclerView滚动到指定位置
        }
    }

    @Override
    public void onRefresh() {
        if (mAdapter != null && isShow && getUserVisibleHint()) {
            mContext.sendBroadcast(new Intent().setAction(FindFragment.RECEIVER_CAT));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void getData() {
        if (mAdapter != null && isShow && getUserVisibleHint()) {

            Map<String, String> data = new HashMap<>();
            data.put("user_id", GlobalData.getUser_id());

            if (!cat_id.equals("1")) {
                data.put("cat_id", cat_id);
            }

            // action_type -- 1：下拉刷新  2：上拉加载更多
            int index;
            if (page != 1) {
                data.put("action_type", "2");
                index = page * 20 - 1;
                List<FindListBean.DataEntity> allData = mAdapter.getAllData();

                // 最后一个时间戳
                try {
                    FindListBean.DataEntity dataEntity = allData.get(index);
                    data.put("time", dataEntity.getCreate_time() + "");
                } catch (Exception e) {

                }
            } else {
                data.put("action_type", "1");
            }

            data.put("page", page + "");
            boolean isRefresh = true;
            if (page == 1 && mAdapter.getAllData().size() != 0) {
                isRefresh = false;
            }

            mPresenter.getList(data, isRefresh);
        }
    }
}
