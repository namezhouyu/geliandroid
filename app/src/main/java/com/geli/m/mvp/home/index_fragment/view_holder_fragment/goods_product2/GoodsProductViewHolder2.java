package com.geli.m.mvp.home.index_fragment.view_holder_fragment.goods_product2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.RecommendGoodsBean;
import com.geli.m.bean.base.IndexBaseBean;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.goods_product2.view_holder.GoodsProductVH2;
import com.geli.m.mvp.home.other.goodsdetails_activity.GoodsDetailsActivity;
import com.geli.m.orther.coverflow.CoverFlowLayoutManger;
import com.geli.m.orther.coverflow.RVItemDecoration;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.List;

import static com.geli.m.config.Constant.INTENT_GOODS_ID;

/**
 * Created by l on 2018/4/9.
 *
 * 商品推荐 -- 模块显示风格2
 */
public class GoodsProductViewHolder2 extends BaseViewHolder<IndexBaseBean<List<RecommendGoodsBean>>> {
    private final TextView mTv_title;
    Context mContext;

    private final EasyRecyclerView mErv_content;
    private RecyclerArrayAdapter mAdapter;
//    private final CarouselLayoutManager mManager;

    public GoodsProductViewHolder2(ViewGroup parent, Context context) {
        super(parent, R.layout.fragment_goodsproduct2);
        mContext = context;
        mTv_title = $(R.id.tv_index_viewholder_title);
        mErv_content = $(R.id.erv_goodsproduct2);

        final int width = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();
        CoverFlowLayoutManger.Builder builder = new CoverFlowLayoutManger.Builder();
        builder.setIntervalRatio(0.1f);
        builder.setFlat(false);
        builder.setItemSpace(10);
        mErv_content.addItemDecoration(new RVItemDecoration(LinearLayoutManager.HORIZONTAL, 200));
        mErv_content.setLayoutManager(builder.build());

        mErv_content.setAdapter(mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                GoodsProductVH2 wonRecomViewHolder =
                        new GoodsProductVH2(parent, mContext, (int) (width * 0.33));
                return wonRecomViewHolder;
            }
        });

        new LinearSnapHelper().attachToRecyclerView(mErv_content.getRecyclerView());
        ViewCompat.setNestedScrollingEnabled(mErv_content.getRecyclerView(), false);

        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                RecommendGoodsBean item = (RecommendGoodsBean) mAdapter.getItem(position);
                ((BaseActivity) mContext).startActivity(GoodsDetailsActivity.class,
                        new Intent().putExtra(INTENT_GOODS_ID, item.getGoods_id() + ""));
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
