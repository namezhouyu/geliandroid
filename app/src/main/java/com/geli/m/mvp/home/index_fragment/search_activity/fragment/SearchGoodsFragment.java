package com.geli.m.mvp.home.index_fragment.search_activity.fragment;

import android.content.Intent;
import android.view.ViewGroup;
import butterknife.BindView;
import com.geli.m.R;
import com.geli.m.bean.OverseasGoodsOuterBean;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.index_fragment.search_activity.SearchActivity;
import com.geli.m.mvp.home.other.goodsdetails_activity.GoodsDetailsActivity;
import com.geli.m.viewholder.GoodsSearchGridViewHolder;
import com.geli.m.viewholder.GoodsSearchListViewHolder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import static com.geli.m.config.Constant.INTENT_GOODS_ID;

/**
 * Created by Steam_l on 2017/12/29.
 */

public class SearchGoodsFragment extends SearchBaseFragment<OverseasGoodsOuterBean>
        implements SearchActivity.ListChangeListener {

    @BindView(R.id.erv_goodssearch_list)
    EasyRecyclerView erv_list;

    @Override
    public int getResId() {
        return R.layout.fragment_goodssearch;
    }

    @Override
    public void setData(OverseasGoodsOuterBean data) {
        if (page == 1) {
            clearData();
        }
        addAll(data.getData());
        if (data.getData().size() < 20) {
            stopMore();
        }
    }

    @Override
    public int getGoodsType() {
        return 1;
    }

    @Override
    protected BaseViewHolder getListViewHolder(ViewGroup parent) {
        return new GoodsSearchListViewHolder(parent, mContext);
    }

    @Override
    protected BaseViewHolder getGridViewHolder(ViewGroup parent) {
        return new GoodsSearchGridViewHolder(parent, mContext);
    }

    @Override
    protected EasyRecyclerView getEasyRecyclerView() {
        return erv_list;
    }

    @Override
    protected int setFragmentType() {
        return SearchBaseFragment.TYPE_SEARCHGOODS;
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
                OverseasGoodsOuterBean.OverseasGoodsBean bean = (OverseasGoodsOuterBean.OverseasGoodsBean) mAdapter.getItem(position);
                ((BaseActivity) mContext).startActivity(GoodsDetailsActivity.class, new Intent().putExtra(INTENT_GOODS_ID, bean.getGoods_id() + ""));
            }
        });
    }
}
