package com.geli.m.mvp.home.index_fragment.view_holder_fragment.banner1;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.PagerSnapHelper;
import android.view.ViewGroup;
import com.geli.m.R;
import com.geli.m.bean.AtsBean;
import com.geli.m.bean.base.IndexBaseBean;
import com.geli.m.mvp.home.index_fragment.main.IndexFragment;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.banner1.view_holder.Banner1VH;
import com.geli.m.orther.CarouselLayoutManager;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.List;

/**
 * Created by l on 2018/4/9.
 *
 * 轮播图 -- 使用"列表" 存放 "ImageView" 显示图片 其中使用了 "CarouselLayoutManager"布局管理(有中间图片大，两边小的特点)
 */

public class Banner1ViewHolder extends BaseViewHolder<IndexBaseBean<List<AtsBean>>> {

    Context mContext;
    private final EasyRecyclerView mErv_content;
    private RecyclerArrayAdapter mAdapter;

    public Banner1ViewHolder(ViewGroup parent, Context context, final IndexFragment fragment) {
        super(parent, R.layout.fragment_banner);
        mContext = context;
        mErv_content = $(R.id.erv_banner);
        final int width = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();

        CarouselLayoutManager manager = new CarouselLayoutManager(mContext, (int) (width * 0.11));
        manager.setMinScale(0.76f);
        mErv_content.setLayoutManager(manager);
        new PagerSnapHelper().attachToRecyclerView(mErv_content.getRecyclerView());
        ViewCompat.setNestedScrollingEnabled(mErv_content.getRecyclerView(), false); // 禁用嵌套滑动

        mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                final Banner1VH mBanner1VH = new Banner1VH(parent, mContext,
                        (width * 0.84), (width * 0.45), fragment);
                //                mBannewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                //                    @Override
                //                    public void onClick(View v) {
                //                        BannerFragment.this.bannerOnClicker(mBanner1VH.getAdapterPosition());
                //                    }
                //                });
                return mBanner1VH;
            }
        };
        mErv_content.setAdapterWithProgress(mAdapter);
    }

    @Override
    public void setData(IndexBaseBean<List<AtsBean>> data) {
        mAdapter.clear();
        mAdapter.addAll(data.getData());
    }
}
