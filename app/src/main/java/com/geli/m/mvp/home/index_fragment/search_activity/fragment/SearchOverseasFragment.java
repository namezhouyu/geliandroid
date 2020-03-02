package com.geli.m.mvp.home.index_fragment.search_activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import butterknife.BindView;
import com.geli.m.R;
import com.geli.m.bean.OverseasGoodsOuterBean;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.index_fragment.overseas_activity.fragment.OverseasGridViewHolder;
import com.geli.m.mvp.home.index_fragment.overseas_activity.fragment.OverseasListViewHolder;
import com.geli.m.mvp.home.other.goodsdetails_activity.GoodsDetailsActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import static com.geli.m.config.Constant.INTENT_GOODS_ID;

/**
 * Created by Steam_l on 2017/12/29.
 *
 * "搜索" -- 海外
 */
public class SearchOverseasFragment extends SearchBaseFragment<OverseasGoodsOuterBean> {

    @BindView(R.id.erv_overseas_list)
    EasyRecyclerView erv_list;

    public static String ARG_SHOWSMALLCART = "arg_showsmallcart";
    public static String ARG_GOODSTYPE = "arg_goodstype";
    public int currGoodsType;

    @Override
    public int getResId() {
        return R.layout.fragment_overseas_list;
    }

    /**
     * @param goodsType       4 团购  3 期货  2 现货
     * @param isShowSmallCart
     * @return
     */
    public static SearchOverseasFragment newInstance(int goodsType, boolean isShowSmallCart) {
        SearchOverseasFragment overseasListFragment = new SearchOverseasFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_SHOWSMALLCART, isShowSmallCart);
        args.putInt(ARG_GOODSTYPE, goodsType);
        overseasListFragment.setArguments(args);
        return overseasListFragment;
    }

    public static SearchOverseasFragment newInstance(int goodsType) {
        return newInstance(goodsType, false);
    }


    @Override
    protected void init() {
        super.init();
        Bundle arguments = getArguments();
        currGoodsType = arguments.getInt(ARG_GOODSTYPE);
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
                OverseasGoodsOuterBean.OverseasGoodsBean goodsBean = (OverseasGoodsOuterBean.OverseasGoodsBean) mAdapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra(INTENT_GOODS_ID, goodsBean.getGoods_id() + "");
                ((BaseActivity) mContext).startActivity(GoodsDetailsActivity.class, intent);
            }
        });
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
    protected BaseViewHolder getListViewHolder(ViewGroup parent) {
        OverseasListViewHolder overseasListViewHolder = new OverseasListViewHolder(parent, mContext);
        overseasListViewHolder.setIsSearch(true);
        return overseasListViewHolder;
    }

    @Override
    protected BaseViewHolder getGridViewHolder(ViewGroup parent) {
        return new OverseasGridViewHolder(parent, mContext);
    }

    @Override
    protected EasyRecyclerView getEasyRecyclerView() {
        return erv_list;
    }

    @Override
    protected int setFragmentType() {
        return SearchBaseFragment.TYPE_SEARCHOVERSEAS;
    }

    @Override
    public int getGoodsType() {
        return currGoodsType;
    }
}
