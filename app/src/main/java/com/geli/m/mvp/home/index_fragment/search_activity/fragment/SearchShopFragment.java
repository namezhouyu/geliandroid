package com.geli.m.mvp.home.index_fragment.search_activity.fragment;

import android.content.Intent;
import android.view.ViewGroup;
import butterknife.BindView;
import com.geli.m.R;
import com.geli.m.bean.FactoryBean;
import com.geli.m.bean.RetailCenterBean;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.index_fragment.search_activity.SearchActivity;
import com.geli.m.mvp.home.index_fragment.search_activity.fragment.vh.ShopSearchGridViewHolder;
import com.geli.m.mvp.home.index_fragment.search_activity.fragment.vh.ShopSearchListViewHolder;
import com.geli.m.mvp.home.other.shopdetails_activity.ShopDetailsActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import static com.geli.m.config.Constant.INTENT_SHOP_ID;

/**
 * Created by Steam_l on 2017/19.
 */

public class SearchShopFragment extends SearchBaseFragment<RetailCenterBean> implements SearchActivity.ListChangeListener {
    @BindView(R.id.erv_shopsearch_list)
    EasyRecyclerView erv_list;

    @Override
    public int getResId() {
        return R.layout.fragment_shopsearch;
    }

    @Override
    public void setData(RetailCenterBean data) {
        if (page == 1) {
            clearData();
        }
        addAll(data.getData());
        if (data.getData().size() < 20) {
            stopMore();
        }
    }

    @Override
    protected BaseViewHolder getListViewHolder(ViewGroup parent) {
        return new ShopSearchListViewHolder(parent, mContext);
    }

    @Override
    protected BaseViewHolder getGridViewHolder(ViewGroup parent) {
        return new ShopSearchGridViewHolder(parent, mContext);
    }

    @Override
    protected EasyRecyclerView getEasyRecyclerView() {
        return erv_list;
    }

    @Override
    protected int setFragmentType() {
        return SearchBaseFragment.TYPE_SEARCHSHOP;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initEvent() {
        setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FactoryBean bean = (FactoryBean) mAdapter.getItem(position);
                ((BaseActivity) mContext).startActivity(ShopDetailsActivity.class, new Intent().putExtra(INTENT_SHOP_ID, bean.getShop_id() + ""));
            }
        });
    }
}
