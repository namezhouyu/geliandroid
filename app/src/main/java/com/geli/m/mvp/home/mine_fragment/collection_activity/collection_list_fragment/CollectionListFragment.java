package com.geli.m.mvp.home.mine_fragment.collection_activity.collection_list_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import butterknife.BindView;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.CollectionBean;
import com.geli.m.coustomview.ErrorView;
import com.geli.m.mvp.base.MVPFragment;
import com.geli.m.mvp.home.find_fragment.findlist_fragment.details.ArticleDetailsActivity;
import com.geli.m.mvp.home.find_fragment.findlist_fragment.details.VideoDetailsActivity;
import com.geli.m.mvp.home.other.goodsdetails_activity.GoodsDetailsActivity;
import com.geli.m.mvp.home.other.shopdetails_activity.ShopDetailsActivity;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.geli.m.config.Constant.ARGS_TYPE;
import static com.geli.m.config.Constant.INTENT_FIND_ID;
import static com.geli.m.config.Constant.INTENT_GOODS_ID;
import static com.geli.m.config.Constant.INTENT_SHOP_ID;
import static com.geli.m.config.Constant.TYPE_ARTICLE;
import static com.geli.m.config.Constant.TYPE_GOODS;
import static com.geli.m.config.Constant.TYPE_SHOP;
import static com.geli.m.config.Constant.TYPE_VIDEO;

/**
 * Created by Steam_l on 2018/4/2.
 */

public class CollectionListFragment extends MVPFragment<CollectionPresentImpl> implements CollectionView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.erv_collection_list)
    EasyRecyclerView mErvList;

    /** 这个fragment 是什么类型 */
    public int mCurrType = TYPE_SHOP;
    private int page = 1;
    public int mTempId = -1;
    /** 列表中的下标 -- 用作取消收藏后在列表中删除 */
    private int mCancelCollPosition = -1;
    public boolean isShow;

    private RecyclerArrayAdapter<CollectionBean.DataEntity> mAdapter;


    @Override
    protected CollectionPresentImpl createPresent() {
        return new CollectionPresentImpl(this);
    }

    @Override
    public int getResId() {
        return R.layout.fragment_collection;
    }

    public static CollectionListFragment newInstance(int type) {
        CollectionListFragment collectionListFragment = new CollectionListFragment();
        Bundle arg = new Bundle();
        arg.putInt(ARGS_TYPE, type);
        collectionListFragment.setArguments(arg);
        return collectionListFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        isShow = true;
        getList();
    }

    @Override
    public void onPause() {
        super.onPause();
        isShow = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        getList();
    }

    @Override
    protected void init() {
        super.init();
        mCurrType = getArguments().getInt(ARGS_TYPE, mCurrType);
    }

    @Override
    protected void initData() {
        initErv();
        initErvAdapter();
    }

    @Override
    protected void initEvent() {

    }


    private void initErv(){
        mErvList.setLayoutManager(new LinearLayoutManager(mContext));
        mErvList.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.line_color), Utils.dip2px(mContext, 1), Utils.dip2px(mContext, 15), 0));
        mAdapter = new RecyclerArrayAdapter<CollectionBean.DataEntity>(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new CollectionViewHolder(parent, mContext, mCurrType, new CollectionViewHolder.BCListener() {
                    @Override
                    public void cancelColl(int id, int position) {
                        cancelColl(id, position);
                    }
                });
            }
        };

        mErvList.setAdapterWithProgress(mAdapter);
        EasyRecyclerViewUtils.initAdapter(mAdapter, new EasyRecyclerViewUtils.AdapterListener() {
            @Override
            public void onMoreShow() {                      // 加载更多
                super.onMoreShow();
                page++;
                getList();
            }

            @Override
            public void onErrorClick() {
                super.onErrorClick();
                mAdapter.resumeMore();
            }
        });

        EasyRecyclerViewUtils.initEasyRecyclerView(mErvList, new ErrorView.ClickRefreshListener() {
            @Override
            public void clickRefresh() {
                onRefresh();
            }
        });

        mErvList.setRefreshListener(this);
    }

    private void initErvAdapter(){
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                CollectionBean.DataEntity item = mAdapter.getItem(position);

                Intent intent;
                switch (mCurrType){
                    case TYPE_GOODS:       /* 食品 */
                        intent = new Intent().putExtra(INTENT_GOODS_ID, item.getGoods_id() + "");
                        startActivity(GoodsDetailsActivity.class, intent);
                        break;

                    case TYPE_SHOP:          /* 店铺 */
                        intent = new Intent().putExtra(INTENT_SHOP_ID, item.getShop_id() + "");
                        startActivity(ShopDetailsActivity.class, intent);
                        break;

                    case TYPE_ARTICLE:      /* 文章 */
                        intent = new Intent().putExtra(INTENT_FIND_ID, item.getFind_id() + "");
                        startActivity(ArticleDetailsActivity.class, intent);
                        break;

                    case TYPE_VIDEO:        /* 视频 */
                        intent =new Intent().putExtra(INTENT_FIND_ID, item.getFind_id() + "");
                        startActivity(VideoDetailsActivity.class, intent);
                        break;
                }
            }
        });
    }



    /**
     * 取消"收藏"
     * @param id            要取消的id
     * @param position      在列表控件的下标
     */
    public void cancelColl(int id, int position) {
        Map<String, String> data = new HashMap<>();

        data.put("col_type", mCurrType + "");
        switch (mCurrType){
            case TYPE_GOODS:
                data.put("goods_id", id + "");
                break;
            case TYPE_SHOP:
                data.put("shop_id", id + "");
                break;
            default:
                data.put("find_id", id + "");
                break;
        }
        data.put("user_id", GlobalData.getUser_id());

        mTempId = id;
        mCancelCollPosition = position;
        mPresenter.collection(data);
    }


    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);

        if (mPresenter.isColl && mTempId != -1) {         // 取消"收藏"成功后的操作
            mPresenter.isColl = false;
            List<CollectionBean.DataEntity> allData = mAdapter.getAllData();
            //int i = 0;
            for (CollectionBean.DataEntity childe : allData) {
                int id;
                switch (mCurrType){
                    case TYPE_GOODS:
                        id = childe.getGoods_id();
                        break;
                    case TYPE_SHOP:
                        id = childe.getShop_id();
                        break;
                    default:
                        id = childe.getFind_id();
                        break;
                }

                if (id == mTempId && mCancelCollPosition != -1) {
                    mAdapter.remove(mCancelCollPosition);
                }
            }

            if (mAdapter.getAllData().size() == 0) {
                mErvList.showEmpty();
            }
        }
    }

    @Override
    public void onError(String msg) {
        ShowSingleToast.showToast(mContext, msg);
        if (page != 1) {
            page--;
            mAdapter.pauseMore();
        } else {
            mErvList.showError();
        }
    }

    @Override
    public void showLoading() {
        if (page == 1) {
            mErvList.setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        if (page == 1) {
            mErvList.setRefreshing(false);
        }
    }

    @Override
    public void showList(List<CollectionBean.DataEntity> entityList) {
        if (page == 1) {
            mAdapter.clear();
            if (null == entityList || entityList.size() == 0) {
                mErvList.showEmpty();
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
        getList();
    }

    private void getList() {
        if (isShow && getUserVisibleHint()) {
            mPresenter.getList(GlobalData.getUser_id(), mCurrType + "");
        }
    }
}
