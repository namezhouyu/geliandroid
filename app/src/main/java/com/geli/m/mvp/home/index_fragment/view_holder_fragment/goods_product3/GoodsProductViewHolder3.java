package com.geli.m.mvp.home.index_fragment.view_holder_fragment.goods_product3;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.RecommendGoodsBean;
import com.geli.m.bean.base.IndexBaseBean;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.goods_product3.view_holder.GoodsProductVH3;
import com.geli.m.mvp.home.other.goodsdetails_activity.GoodsDetailsActivity;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import java.util.List;

import static com.geli.m.config.Constant.INTENT_GOODS_ID;

/**
 * Created by l on 2018/4/9.
 *
 * 商品推荐 -- 模块显示风格3
 */
public class GoodsProductViewHolder3 extends BaseViewHolder<IndexBaseBean<List<RecommendGoodsBean>>> {
    private final TextView mTv_title;
    Context mContext;
    private final EasyRecyclerView mErv_content;
    private RecyclerArrayAdapter mAdapter;

    public GoodsProductViewHolder3(ViewGroup parent, Context context) {
        super(parent, R.layout.fragment_goodsproduct3);
        mContext = context;
        mTv_title = $(R.id.tv_index_viewholder_title);
        mErv_content = $(R.id.erv_goodsproduct3);


        mErv_content.setLayoutManager(new GridLayoutManager(mContext, 2));
        mErv_content.addItemDecoration(new SpaceDecoration(Utils.dip2px(mContext, 10)));
        mErv_content.setAdapterWithProgress(mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new GoodsProductVH3(parent, mContext);
            }
        });
        ViewCompat.setNestedScrollingEnabled(mErv_content.getRecyclerView(), false);
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                RecommendGoodsBean item = (RecommendGoodsBean) mAdapter.getItem(position);
                ((BaseActivity) mContext).startActivity(GoodsDetailsActivity.class, new Intent().putExtra(INTENT_GOODS_ID, item.getGoods_id() + ""));
            }
        });
    }

    @Override
    public void setData(IndexBaseBean<List<RecommendGoodsBean>> data) {
        if (data.getTitle_is_show() == 1) {
            mTv_title.setText(data.getModel_title());
        } else {
            mTv_title.setVisibility(View.GONE);
        }
        mAdapter.clear();
        mAdapter.addAll(data.getData());
    }
}
