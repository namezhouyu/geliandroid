package com.geli.m.mvp.home.index_fragment.view_holder_fragment.may_you_like;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.InterestGoodsBean;
import com.geli.m.bean.base.IndexBaseBean;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.may_you_like.view_holder.MayYouLikeVH;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by l on 2018/4/9.
 *
 * 推荐商品
 */
public class MayYouLikeViewHolder extends BaseViewHolder<IndexBaseBean<List<InterestGoodsBean>>> {
    private final TextView mTv_title;
    Context mContext;
    private final EasyRecyclerView mErv_content;
    private final RecyclerArrayAdapter mAdapter;

    public MayYouLikeViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.fragment_mayyoulike);

        mContext = context;
        mTv_title = $(R.id.tv_index_viewholder_title);
        mErv_content = $(R.id.erv_mayyoulike);

        // 横向布局 -- 排序从头到尾
        mErv_content.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        // 添加分割线
        mErv_content.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                // 左间隔
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.left = Utils.dip2px(mContext, 15);
                } else {
                    outRect.left = Utils.dip2px(mContext, 10);
                }

                // 右间隔
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
                return new MayYouLikeVH(parent, mContext);
            }
        };

        mErv_content.setAdapterWithProgress(mAdapter);
        ViewCompat.setNestedScrollingEnabled(mErv_content.getRecyclerView(), false);
    }

    @Override
    public void setData(IndexBaseBean<List<InterestGoodsBean>> data) {
        if (data.getTitle_is_show() == 1) {
            mTv_title.setText(data.getModel_title());
        } else {
            mTv_title.setVisibility(View.GONE);
        }

        final List<List<InterestGoodsBean>> allData = new ArrayList();
        List<InterestGoodsBean> data1 = new ArrayList();
        List<InterestGoodsBean> info = data.getData();

        for (int i = 0; i < info.size(); i++) {     // 总结的有点烦，就是2个2个放到一个"数组列表"，再将这个放到"大数组列表"
            if (i != 0 && i % 2 == 0) {         // 1、3、 5
                allData.add(data1);
                data1 = new ArrayList<>();
            }
            data1.add(info.get(i));

            if (i % 2 != 0&&i == info.size() - 1) {     // 最后一个(总数是单数的)，将这个还是讲这个只有一个"小数组列表"的放到"大数组列表中"
                allData.add(data1);
            }
        }

        mAdapter.clear();
        mAdapter.addAll(allData);
    }
}
