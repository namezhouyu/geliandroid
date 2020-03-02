package com.geli.m.mvp.home.index_fragment.view_holder_fragment.other;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.geli.m.R;
import com.geli.m.bean.base.IndexBaseBean;
import com.geli.m.coustomview.MyEasyRecyclerView;
import com.geli.m.coustomview.recyclerviewscrollerview.FastScroller;
import com.geli.m.mvp.view.FastScrollLinearLayoutManager;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import java.util.List;

/**
 * Created by l on 2018/4/9.
 *
 * 其他的
 */
public class OtherViewHolder extends BaseViewHolder<IndexBaseBean<List<Integer>>> {
    Context mContext;

    private RecyclerArrayAdapter mAdapter;

    @BindView(R.id.erv_homeOtherItemView)
    MyEasyRecyclerView mErv;
    @BindView(R.id.fs_homeOtherItemView)
    FastScroller mFastScroller;

    public OtherViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_home_other);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(final IndexBaseBean<List<Integer>> baseBean) {
        setErv();

        mAdapter.clear();
        mAdapter.addAll(baseBean.getData());

        mFastScroller.setRecyclerView(mErv.getRecyclerView());
    }

    private void setErv() {
        mErv.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.line_color),
                0, 0, 0));
        mErv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mErv.setLayoutManager(new FastScrollLinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));


        mErv.setAdapterWithProgress(mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new OtherVH(parent, mContext);
            }
        });
        ViewCompat.setNestedScrollingEnabled(mErv.getRecyclerView(), false);
    }

}
