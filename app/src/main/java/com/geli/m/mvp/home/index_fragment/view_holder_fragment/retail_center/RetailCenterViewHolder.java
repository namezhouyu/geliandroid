package com.geli.m.mvp.home.index_fragment.view_holder_fragment.retail_center;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.SellBean;
import com.geli.m.bean.base.IndexBaseBean;
import com.geli.m.coustomview.MyDividerDecoration;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.index_fragment.main.IndexFragment;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.retail_center.view_holder.RetailCenterVH;
import com.geli.m.mvp.home.other.shopdetails_activity.ShopDetailsActivity;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.List;

import static com.geli.m.config.Constant.INTENT_SHOP_ID;

/**
 * Created by l on 2018/4/9.
 *
 * 批零中心
 */
public class RetailCenterViewHolder extends BaseViewHolder<IndexBaseBean<List<SellBean>>> {

    Context mContext;
    IndexFragment mIndexFragment;

    /**  所有批零中心 */
    private final EasyRecyclerView mErv_content;
    /** 标题 */
    private final TextView mTv_title;

    private RecyclerArrayAdapter mAdapter;


    public RetailCenterViewHolder(ViewGroup parent, Context context, IndexFragment fragment) {
        super(parent, R.layout.fragment_retailscenter);
        mIndexFragment = fragment;
        mContext = context;
        mErv_content = $(R.id.erv_retailscenter);
        mTv_title = $(R.id.tv_index_viewholder_title);

        ViewCompat.setNestedScrollingEnabled(mErv_content.getRecyclerView(), false);

        // 设置"分隔线" -- 布局类型(宫格2列)
        mErv_content.setLayoutManager(new GridLayoutManager(mContext, 2));
        MyDividerDecoration itemDecoration = new MyDividerDecoration(
                Utils.getColor(R.color.line_color),
                Utils.dip2px(mContext, 1),
                Utils.dip2px(mContext, 15),
                Utils.dip2px(mContext, 15));
        itemDecoration.setDrawLastItem(false);
        mErv_content.addItemDecoration(itemDecoration);

        mErv_content.setAdapterWithProgress(mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new RetailCenterVH(parent, mContext);
            }
        });


        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SellBean sellBean = (SellBean) mAdapter.getAllData().get(position);
                ((BaseActivity) mContext).startActivity(ShopDetailsActivity.class,
                        new Intent().putExtra(INTENT_SHOP_ID, sellBean.getShop_id() + ""));
            }
        });
    }

    @Override
    public void setData(IndexBaseBean<List<SellBean>> data) {
        if (data.getTitle_is_show() == 1) {
            mTv_title.setText(data.getModel_title());
        } else {
            mTv_title.setVisibility(View.GONE);
        }

        mAdapter.clear();
        mAdapter.addAll(data.getData());           // 批零中心列表
    }
}
