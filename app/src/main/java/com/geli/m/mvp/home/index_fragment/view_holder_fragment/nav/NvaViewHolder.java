package com.geli.m.mvp.home.index_fragment.view_holder_fragment.nav;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.geli.m.R;
import com.geli.m.bean.NvaBean;
import com.geli.m.bean.base.IndexBaseBean;
import com.geli.m.coustomview.MyEasyRecyclerView;
import com.geli.m.coustomview.recyclerviewscrollerview.FastScroller;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import java.util.List;

/**
 * author:  shen
 * date:    2019/4/1
 *
 *
 * 导航
 */
public class NvaViewHolder extends BaseViewHolder<IndexBaseBean<List<NvaBean>>> {
    Context mContext;

    private RecyclerArrayAdapter mAdapter;

    @BindView(R.id.erv_NavFragment)
    MyEasyRecyclerView mErv;
    @BindView(R.id.fs_NavFragment)
    FastScroller mFastScroller;

    public NvaViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.fragment_nav);
        mContext = context;
        ButterKnife.bind(this, itemView);

        setErv();
    }

    @Override
    public void setData(final IndexBaseBean<List<NvaBean>> baseBean) {
        mAdapter.clear();
        mAdapter.addAll(baseBean.getData());

        mFastScroller.setRecyclerView(mErv.getRecyclerView());
        mFastScroller.setEnabled(false);
    }

    private void setErv() {
        mErv.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.line_color),
                0, 0, 0));
        mErv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));


        mErv.setAdapterWithProgress(mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new NavVH(parent, mContext);
            }
        });
        ViewCompat.setNestedScrollingEnabled(mErv.getRecyclerView(), false);
    }

}
