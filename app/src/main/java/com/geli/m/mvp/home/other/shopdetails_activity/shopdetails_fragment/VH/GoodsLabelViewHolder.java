package com.geli.m.mvp.home.other.shopdetails_activity.shopdetails_fragment.VH;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import com.geli.m.R;
import com.geli.m.bean.ShopInfoBean;
import com.geli.m.mvp.home.other.shopdetails_activity.shopdetails_fragment.ShopDetailsFragment;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.List;

/**
 * Created by Steam_l on 2018/1/12.
 *
 * 商店详情 -- 普通分类 的 "项布局"
 */

public class GoodsLabelViewHolder extends BaseViewHolder<ShopInfoBean.DataEntity.CatResEntity.CartEntity> {

    Context mContext;

    /** 小分类列表 */
    private final EasyRecyclerView mErv_xiao;
    private final CheckBox mCb_sort;

    ShopDetailsFragment mFragment;
    private final RecyclerArrayAdapter mAdapter;
    ShopDetailsFragment.SelectBean mSelectBean;


    public GoodsLabelViewHolder(ViewGroup parent, Context context, ShopDetailsFragment fragment) {
        super(parent, R.layout.itemview_shopdetails_sorttitle);
        mFragment = fragment;
        mContext = context;

        mErv_xiao = $(R.id.erv_shopdetails_sorttitle_list);
        mCb_sort = $(R.id.cb_shopdetails_sort_title);

        mErv_xiao.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mErv_xiao.setAdapterWithProgress(mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new GoodsLabelSmallViewHolder(parent, mContext, mFragment);
            }
        });
    }

    @Override
    public void setData(ShopInfoBean.DataEntity.CatResEntity.CartEntity data) {
        super.setData(data);
        super.setData(data);
        mCb_sort.setText(data.getCat_name() + "");

        mSelectBean = mFragment.getSelectBean();

        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ShopInfoBean.DataEntity.CatResEntity.CartEntity item =
                        (ShopInfoBean.DataEntity.CatResEntity.CartEntity) mAdapter.getItem(position);

                ShopDetailsFragment.SelectBean selectBean = mFragment.getSelectBean();
                List<ShopInfoBean.DataEntity.GoodsResEntity> mGoodsRes = mFragment.getGoodsList();

                int i = 0;
                for(ShopInfoBean.DataEntity.GoodsResEntity entity : mGoodsRes){
                    if(mFragment.mLeftIsScroll) {                       /* 右边滑动 */
                        if (entity.getCat_id() == selectBean.bigId) {
                            mFragment.smoothMoveToPosition(mFragment.getGoodsErv().getRecyclerView(), i);
                            break;
                        }
                    }else {                                             /* 左边点击 */
                        if(entity.getGs_id() != -1){
                            i++;
                            continue;
                        }
                        if (entity.getCat_id() == item.getCat_id()) {
                            selectBean.xiaoId = entity.getCat_id(); // 点击了哪一个"子分类"
                            mFragment.setSelectBean(selectBean);
                            mFragment.smoothMoveToPosition(mFragment.getGoodsErv().getRecyclerView(), i);
                            break;
                        }
                    }
                    i++;
                }
                mAdapter.notifyDataSetChanged();        // 刷新 子分类
            }
        });


        /* 根据一级控件是否被选中 -- 设置二级控件的选中变色 和 展开隐藏 */
        if (!mSelectBean.isOrther) {                                /* 普通分类 */
            setNormalView(data);
        } else {                                                    /* 其他分类 */
            mErv_xiao.setVisibility(View.GONE);
            mCb_sort.setChecked(false);
        }
    }


    /**
     * 根据一级控件是否被选中 -- 设置二级控件的选中变色 和 展开隐藏  -- 普通分类
     * @param data
     */
    private void setNormalView(ShopInfoBean.DataEntity.CatResEntity.CartEntity data) {
        mCb_sort.setChecked(mSelectBean.bigId == data.getCat_id());

        if (mSelectBean.bigId == data.getCat_id()) {            /* 被选中的是 -- 当前的项(一级控件) */
            selectNewItem(data);

        } else {                                                /* 当前项没有被选中 */
            mErv_xiao.setVisibility(View.GONE);
            mCb_sort.setChecked(false);
        }
    }

    /**
     * 设置选中项 -- 的小分类
     * @param data
     */
    private void selectNewItem(ShopInfoBean.DataEntity.CatResEntity.CartEntity data) {
        mCb_sort.setChecked(true);
        if (data.getSmallCartList().size() > 0) {           // 有小分类
            setSmall(data);
        } else {                                            // 没有小分类
            mErv_xiao.setVisibility(View.GONE);
        }
    }

    /**
     * 设置小分类的数据
     * @param data
     */
    private void setSmall(ShopInfoBean.DataEntity.CatResEntity.CartEntity data) {

        if(mFragment.mLeftIsScroll)
            mErv_xiao.setVisibility(View.VISIBLE);
        else {
            if(mSelectBean.isSame){                         // 如果展开再次点击就隐藏
                mErv_xiao.setVisibility(View.GONE);
            }else {
                mErv_xiao.setVisibility(View.VISIBLE);
            }
        }

        mAdapter.clear();
        mAdapter.addAll(data.getSmallCartList());
    }
}
