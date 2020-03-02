package com.geli.m.mvp.home.index_fragment.search_activity.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import com.geli.m.R;
import com.geli.m.coustomview.ErrorView;
import com.geli.m.mvp.base.BaseFragment;
import com.geli.m.mvp.home.index_fragment.search_activity.SearchActivity;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import java.util.List;

/**
 * Created by Steam_l on 2018/2/3.
 *
 * "搜索"中的"内容的Fragment" -- 刷新，加载更多，切换显示样式
 */
public abstract class SearchBaseFragment<T> extends BaseFragment
        implements SearchActivity.ListChangeListener,
        ErrorView.ClickRefreshListener, SwipeRefreshLayout.OnRefreshListener {

    public static final int TYPE_SEARCHGOODS = 1 << 0;
    public static final int TYPE_SEARCHSHOP = 1 << 1;
    public static final int TYPE_SEARCHOVERSEAS = 1 << 2;
    protected int curr_fragmenttype = TYPE_SEARCHGOODS;

    protected boolean isShow;
    protected RecyclerArrayAdapter mGridAdapter;
    protected RecyclerArrayAdapter mListAdapter;
    protected RecyclerArrayAdapter mAdapter;
    protected LinearLayoutManager mListManager;
    protected GridLayoutManager mGridManager;
    protected SpaceDecoration mGridDecoration;
    protected DividerDecoration mListDecoration;
    public int page = 1;

    public abstract void setData(T data);

    protected abstract BaseViewHolder getListViewHolder(ViewGroup parent);

    protected abstract BaseViewHolder getGridViewHolder(ViewGroup parent);

    protected abstract EasyRecyclerView getEasyRecyclerView();

    protected abstract int setFragmentType();

    public void setOnItemClickListener(RecyclerArrayAdapter.OnItemClickListener onItemClickListener) {
        mListAdapter.setOnItemClickListener(onItemClickListener);
        mGridAdapter.setOnItemClickListener(onItemClickListener);
    }

    public void clearData() {
        mListAdapter.clear();
        mGridAdapter.clear();
    }

    public void addAll(List collection) {
        mListAdapter.addAll(collection);
        mGridAdapter.addAll(collection);
    }

    public void stopMore() {
        mListAdapter.stopMore();
        mGridAdapter.stopMore();
    }

    /**
     * @return 1普通 2现货 3期货 4团购
     */
    public int getGoodsType() {
        return 0;
    }

    protected int getPage() {
        return page;
    }

    public int getType() {
        return curr_fragmenttype;
    }

    @Override
    protected void init() {
        super.init();
        ((SearchActivity) mContext).setListChangeObserver(this);
    }

    @Override
    protected void initData() {
        curr_fragmenttype = setFragmentType();
        mListAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return getListViewHolder(parent);
            }
        };
        mGridAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return getGridViewHolder(parent);
            }
        };
        mListManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mGridManager = new GridLayoutManager(mContext, 2);
        mGridManager.setSpanSizeLookup(mGridAdapter.obtainGridSpanSizeLookUp(2));
        mGridDecoration = new SpaceDecoration(Utils.dip2px(mContext, 10));
        mGridDecoration.setPaddingEdgeSide(true);
        mGridDecoration.setPaddingStart(true);
        mGridDecoration.setPaddingHeaderFooter(true);
        mListDecoration = new DividerDecoration(
                Utils.getColor(R.color.line_color),
                Utils.dip2px(mContext, 1),
                Utils.dip2px(mContext, 15),
                0);
        mAdapter = mListAdapter;
        getEasyRecyclerView().setLayoutManager(mListManager);
        getEasyRecyclerView().addItemDecoration(mListDecoration);
        getEasyRecyclerView().setAdapter(mAdapter);
        EasyRecyclerViewUtils.AdapterListener listener = new EasyRecyclerViewUtils.AdapterListener() {
            @Override
            public void onMoreShow() {
                super.onMoreShow();
                page++;
                ((SearchActivity) mContext).getData();
            }

            @Override
            public void onErrorClick() {
                mAdapter.resumeMore();
            }
        };
        EasyRecyclerViewUtils.initAdapter(mGridAdapter, listener);
        EasyRecyclerViewUtils.initAdapter(mListAdapter, listener);
        EasyRecyclerViewUtils.initEasyRecyclerView(getEasyRecyclerView(), this);
        // getEasyRecyclerView().setRefreshListener(this);
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


    /* 根据"标志"设置，是显示"宫格"或"列表"样式 */
    @Override
    public void changeState(boolean state) {

        if (!state) {                   /* 宫格 */
            getEasyRecyclerView().removeItemDecoration(mListDecoration);
            getEasyRecyclerView().addItemDecoration(mGridDecoration);
            getEasyRecyclerView().setLayoutManager(mGridManager);
            getEasyRecyclerView().setAdapterWithProgress(mGridAdapter);
            if (mListAdapter.getAllData().size() <= 0) {
                getEasyRecyclerView().showEmpty();
            }
            mAdapter = mGridAdapter;

        } else {                        /* 列表 */
            getEasyRecyclerView().removeItemDecoration(mGridDecoration);
            getEasyRecyclerView().addItemDecoration(mListDecoration);
            getEasyRecyclerView().setLayoutManager(mListManager);
            getEasyRecyclerView().setAdapterWithProgress(mListAdapter);
            if (mGridAdapter.getAllData().size() <= 0) {
                getEasyRecyclerView().showEmpty();
            }
            mAdapter = mListAdapter;
        }
    }

    @Override
    public void clickRefresh() {
        ((SearchActivity) mContext).getData();
    }

    @Override
    public void onRefresh() {

    }
}
