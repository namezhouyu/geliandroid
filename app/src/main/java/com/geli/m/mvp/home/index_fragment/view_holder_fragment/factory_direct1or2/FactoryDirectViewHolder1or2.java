package com.geli.m.mvp.home.index_fragment.view_holder_fragment.factory_direct1or2;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.FactoryBean;
import com.geli.m.bean.base.IndexBaseBean;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.factory_direct1or2.view_holder.FactoryDirectVH1or2;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import java.util.List;

/**
 * Created by l on 2018/4/9.
 *
 * 厂家直供 -- 模型 1或2
 *
 */
public class FactoryDirectViewHolder1or2 extends BaseViewHolder<IndexBaseBean<List<FactoryBean>>> {
    private final TextView mTv_title;
    Context mContext;
    boolean isShowGoods;
    private final EasyRecyclerView mErv_content;
    private final RecyclerArrayAdapter mAdapter;

    public FactoryDirectViewHolder1or2(ViewGroup parent, Context context, final boolean isShowGoods, final RecyclerView.RecycledViewPool recycledViewPool) {
        super(parent, R.layout.fragment_factdir1or2);
        mContext = context;
        mTv_title = $(R.id.tv_index_viewholder_title);
        mErv_content = $(R.id.erv_factdir_1or2);

        mErv_content.getRecyclerView().setRecycledViewPool(recycledViewPool);

        // 默认是"垂直"布局
        mErv_content.setLayoutManager(new LinearLayoutManager(mContext));
        DividerDecoration itemDecoration = new DividerDecoration(
                Utils.getColor(R.color.line_color),
                Utils.dip2px(mContext, 1));
        itemDecoration.setDrawLastItem(false);
        mErv_content.addItemDecoration(itemDecoration);
        //                if (mRecycledViewPool == null) {
        //                    mRecycledViewPool = direViewHolder2.getRecycledViewPool();
        //                }
        mAdapter = new RecyclerArrayAdapter(mContext) {
            private int TYPE_FACTORY = 1 << 1;

            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                FactoryDirectVH1or2 direViewHolder2 = new FactoryDirectVH1or2(parent, mContext, recycledViewPool);
                direViewHolder2.isShowGoods(isShowGoods);
                //                if (mRecycledViewPool == null) {
                //                    mRecycledViewPool = direViewHolder2.getRecycledViewPool();
                //                }
                return direViewHolder2;
            }

            @Override
            public int getViewType(int position) {
                return TYPE_FACTORY;
            }
        };
        mErv_content.setAdapterWithProgress(mAdapter);
        ViewCompat.setNestedScrollingEnabled(mErv_content.getRecyclerView(), false);
    }

    @Override
    public void setData(IndexBaseBean<List<FactoryBean>> data) {
        if (data.getTitle_is_show() == 1) {
            mTv_title.setText(data.getModel_title());
        } else {
            mTv_title.setVisibility(View.GONE);
        }

        mAdapter.clear();
        mAdapter.addAll(data.getData());
    }
}
