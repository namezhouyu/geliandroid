package com.geli.m.mvp.home.index_fragment.view_holder_fragment.factory_direct4;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.FactoryBean;
import com.geli.m.bean.base.IndexBaseBean;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.factory_direct4.view_holder.FactoryDirectVH4;
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
 * 厂家直供 -- 模型4
 */
public class FactoryDirectViewHolder4 extends BaseViewHolder<IndexBaseBean<List<FactoryBean>>> {
    private final TextView mTv_title;
    Context mContext;
    private final EasyRecyclerView mErv_content;
    private RecyclerArrayAdapter mAdapter;

    public FactoryDirectViewHolder4(ViewGroup parent, Context context) {
        super(parent, R.layout.fragment_factdir4);
        mContext = context;
        mTv_title = $(R.id.tv_index_viewholder_title);
        mErv_content = $(R.id.erv_factdir4);
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
        mErv_content.setAdapterWithProgress(mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new FactoryDirectVH4(parent, mContext);
            }

        });
        ViewCompat.setNestedScrollingEnabled(mErv_content.getRecyclerView(), false);
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FactoryBean item = (FactoryBean) mAdapter.getItem(position);
                ((BaseActivity) mContext).startActivity(ShopDetailsActivity.class, new Intent().putExtra(INTENT_SHOP_ID, item.getShop_id() + ""));
            }
        });
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
