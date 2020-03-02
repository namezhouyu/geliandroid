package com.geli.m.mvp.home.index_fragment.view_holder_fragment.factory_direct;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.BrandBean;
import com.geli.m.bean.base.IndexBaseBean;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.factory_direct.view_holder.FactoryDirectVH;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by l on 2018/4/9.
 *
 * 品牌街(品牌直供)
 */
public class FactoryDirectViewHolder extends BaseViewHolder<IndexBaseBean<List<BrandBean>>> {
    private final TextView mTv_title;
    Context mContext;
    private final EasyRecyclerView mErv_content;
    private RecyclerArrayAdapter mAdapter;

    public FactoryDirectViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.fragment_factdir);

        mContext = context;
        mTv_title = $(R.id.tv_index_viewholder_title);
        mErv_content = $(R.id.erv_factdir);
        mErv_content.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mErv_content.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.left = Utils.dip2px(mContext, 15);
                } else {
                    outRect.left = Utils.dip2px(mContext, 10);
                }
                if (parent.getChildAdapterPosition(view) == mAdapter.getAllData().size() - 1) {
                    outRect.right = Utils.dip2px(mContext, 15);
                } else {
                    outRect.right = 0;
                }
            }
        });
        mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new FactoryDirectVH(parent, mContext);
            }
        };
        mErv_content.setAdapterWithProgress(mAdapter);
        ViewCompat.setNestedScrollingEnabled(mErv_content.getRecyclerView(), false);
    }

    @Override
    public void setData(IndexBaseBean<List<BrandBean>> data) {
        if (data.getTitle_is_show() == 1) {
            mTv_title.setText(data.getModel_title());
        } else {
            mTv_title.setVisibility(View.GONE);
        }

        List<BrandBean> data2 = data.getData();
        List<BrandBean> data1 = new ArrayList();
        final List<List<BrandBean>> info = new ArrayList();

        for (int i = 1; i <= data2.size(); i++) {
            data1.add(data2.get(i - 1));
            if (i % 2 == 0 || i - 1 == data2.size() - 1) {
                info.add(data1);
                data1 = new ArrayList();
            }
        }
        mAdapter.clear();
        mAdapter.addAll(info);
    }
}
