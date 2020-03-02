package com.geli.m.mvp.home.index_fragment.view_holder_fragment.factory_direct1or2.view_holder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.FactoryBean;
import com.geli.m.coustomview.MyEasyRecyclerView;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.index_fragment.main.IndexFragment;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.factory_direct1or2.view_holder.view_holder.FactoryDirectGoodsVH2;
import com.geli.m.mvp.home.other.goodsdetails_activity.GoodsDetailsActivity;
import com.geli.m.mvp.home.other.shopdetails_activity.ShopDetailsActivity;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.List;

import static com.geli.m.config.Constant.INTENT_GOODS_ID;
import static com.geli.m.config.Constant.INTENT_SHOP_ID;


/**
 * Created by Steam_l on 2017/12/18.
 *
 *  厂家直供 -- 模型2 -- 项布局
 */

public class FactoryDirectVH1or2 extends BaseViewHolder<FactoryBean> {

    private RecyclerArrayAdapter mAdapter;
    Context mContext;

    /** 厂家下对应的食品列表 */
    private final MyEasyRecyclerView mErv_goods;
    /** 默认"池" -- 缓存多少个 -- 循环利用可回收的视图 */
    RecyclerView.RecycledViewPool mRecycledViewPool;

    /** 销售xx单 -- 已售xx单 */
    private final TextView mTv_sold;
    /** 起订量 -- 目前不用 */
    private final TextView mTv_setfrom;
    /** 厂家的名字 */
    private final TextView mTv_name;
    /** 左边厂家图片 */
    private final ImageView mIv_img;
    /** 进入商店 */
    private final Button mBt_sotoshop;
    /** 距离 */
    private final TextView mTv_distance;
    /** 商店 */
    private final LinearLayout mLLayoutShop;
    /** 商店简介 */
    private final TextView mTv_introduce;

    /** 是否显示 -- 食品列表 */
    boolean isShow = false;

    public FactoryDirectVH1or2(ViewGroup parent, Context context, RecyclerView.RecycledViewPool recycledViewPool) {
        super(parent, R.layout.itemview_factdir1or2);
        mContext = context;

        mErv_goods = $(R.id.erv_itemview_factdir1or2_goods);
        mIv_img = $(R.id.iv_itemview_factdir1or2_shopimg);
        mTv_name = $(R.id.tv_itemview_factdir1or2_shopname);
        mTv_setfrom = $(R.id.tv_itemview_factdir1or2_setfrom);
        mTv_sold = $(R.id.tv_itemview_factdir1or2_sold);
        mBt_sotoshop = $(R.id.bt_itemview_factdir1or2_entershop);
        mTv_distance = $(R.id.tv_itemview_factdir1or2_distance);
        mLLayoutShop = $(R.id.lLayout_shop_ItemViewFactdir1Or2);
        mTv_introduce = $(R.id.tv_itemview_factdir1or2_introduce);


        // 横向布局
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        // manager.setRecycleChildrenOnDetach(true);
        mErv_goods.setLayoutManager(manager);
        mErv_goods.addItemDecoration(new RecyclerView.ItemDecoration() {
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

        if (recycledViewPool == null) {
            mRecycledViewPool = mErv_goods.getRecyclerView().getRecycledViewPool();
            mErv_goods.getRecyclerView().setRecycledViewPool(mRecycledViewPool);
        } else {
            mErv_goods.getRecyclerView().setRecycledViewPool(recycledViewPool);
        }

        mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                //  厂家直供2 -- 食品信息ViewHolder
                return new FactoryDirectGoodsVH2(parent, mContext);
            }
        };

        mErv_goods.setAdapterWithProgress(mAdapter);
        ViewCompat.setNestedScrollingEnabled(mErv_goods.getRecyclerView(), false);
    }

    public RecyclerView.RecycledViewPool getRecycledViewPool() {
        return mRecycledViewPool;
    }

    public void isShowGoods(boolean isShow) {
        this.isShow = isShow;
    }

    @Override
    public void setData(final FactoryBean data) {
        super.setData(data);
        if (isShow) {
            mErv_goods.setVisibility(View.VISIBLE);
            mAdapter.clear();
            mAdapter.addAll(data.getGoods_list());
            mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    List<FactoryBean.GoodsListEntity> allData = mAdapter.getAllData();
                    Intent intent = new Intent().putExtra(INTENT_GOODS_ID, allData.get(position).getGoods_id() + "");
                    ((BaseActivity) mContext).startActivity(GoodsDetailsActivity.class, intent);
                }
            });
        } else {
            mErv_goods.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(data.getDistance())&&
                !TextUtils.isEmpty(IndexFragment.mAreaBean.getLa()) &&
                !TextUtils.isEmpty(IndexFragment.mAreaBean.getLo())) {
            mTv_distance.setText(Utils.getString(R.string.distance, data.getDistance()));
        } else {
            mTv_distance.setText("");
        }

        mTv_introduce.setText(data.getShop_intro());
        GlideUtils.loadImgRect(mContext, data.getShop_img(), mIv_img);
        mTv_name.setText(data.getShop_name());
        mTv_sold.setText(Utils.getString(R.string.sold_single, data.getVirtual_quantity_sold() + ""));
//        mBt_sotoshop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent().putExtra(INTENT_SHOP_ID, data.getShop_id() + "");
//                ((BaseActivity) mContext).startActivity(ShopDetailsActivity.class, intent);
//            }
//        });


        mLLayoutShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent().putExtra(INTENT_SHOP_ID, data.getShop_id() + "");
                ((BaseActivity) mContext).startActivity(ShopDetailsActivity.class, intent);
            }
        });
    }

}
