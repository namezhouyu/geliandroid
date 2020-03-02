package com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity.lr_search_activity.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import com.geli.m.R;
import com.geli.m.coustomview.ErrorView;
import com.geli.m.mvp.base.BaseFragment;
import com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity.lr_search_activity.LRSearchActivity;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import java.util.List;

/**
 * author:  shen
 * date:    2019/3/25
 */
public abstract class LRSearchBaseFragment<T> extends BaseFragment
        implements ErrorView.ClickRefreshListener, SwipeRefreshLayout.OnRefreshListener{

    protected RecyclerArrayAdapter mAdapter;
    public int mPage = 1;
    protected boolean isShow;

    public abstract void setData(T data);
    protected abstract BaseViewHolder getViewHolder(ViewGroup parent);
    protected abstract EasyRecyclerView getEasyRecyclerView();

    @Override
    public int getResId() {
        return 0;
    }

    @Override
    protected void initData() {

        initAdapter();
        initErv();
    }



    private void initAdapter() {
        mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return getViewHolder(parent);
            }
        };
    }

    private void initErv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        DividerDecoration decoration = new DividerDecoration(
                Utils.getColor(R.color.line_color),
                Utils.dip2px(mContext, 1),
                0, 0);

        getEasyRecyclerView().setLayoutManager(layoutManager);
        getEasyRecyclerView().addItemDecoration(decoration);
        getEasyRecyclerView().setAdapter(mAdapter);
        EasyRecyclerViewUtils.AdapterListener listener = new EasyRecyclerViewUtils.AdapterListener() {
            @Override
            public void onMoreShow() {
                super.onMoreShow();
                mPage++;
                ((LRSearchActivity) mContext).getData();
            }

            @Override
            public void onErrorClick() {
                mAdapter.resumeMore();
            }
        };

        EasyRecyclerViewUtils.initAdapter(mAdapter, listener);
        EasyRecyclerViewUtils.initEasyRecyclerView(getEasyRecyclerView(), this);
        getEasyRecyclerView().setRefreshListener(this);
    }

    @Override
    protected void initEvent() {

    }



    public void clearData() {
        mAdapter.clear();
    }

    public void addAll(List collection) {
        mAdapter.addAll(collection);
    }

    public void stopMore() {
        mAdapter.stopMore();
    }

    public void setOnItemClickListener(RecyclerArrayAdapter.OnItemClickListener onItemClickListener) {
        mAdapter.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public void onRefresh() {
        ((LRSearchActivity) mContext).getData();
    }

    @Override
    public void clickRefresh() {
        ((LRSearchActivity) mContext).getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        isShow = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isShow = false;
    }

    public boolean isShow() {
        return isShow && getUserVisibleHint();
    }
}
