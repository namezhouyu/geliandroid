package com.geli.m.mvp.home.index_fragment.search_activity.fragment.vh;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.FactoryBean;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2017/12/29.
 */

public class ShopSearchGridViewHolder extends BaseViewHolder<FactoryBean> {
    Context mContext;
    private final ImageView mIv_img;
    private final TextView mTv_name;
    private final TextView mTv_distance;
    private final TextView mTv_setfrom;
    private final TextView mTv_sold;
    private final Button mBt_enter;

    public ShopSearchGridViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_search_grid_shop);
        mContext = context;
        mIv_img = $(R.id.iv_itemview_shopsearch_gird_img);
        mTv_name = $(R.id.tv_itemview_shopsearch_grid_name);
        mTv_distance = $(R.id.tv_itemview_shopsearch_grid_distance);
        mTv_setfrom = $(R.id.tv_itemview_shopsearch_grid_setfrom);
        mTv_sold = $(R.id.tv_itemview_shopsearch_grid_sold);
        mBt_enter = $(R.id.bt_itemview_shopsearch_grid_enter);
    }

    @Override
    public void setData(FactoryBean data) {
        super.setData(data);
        mTv_name.setText(data.getShop_name());
        mTv_setfrom.setText(Utils.getString(R.string.setfrom_pieces,data.getMoq(), Utils.getString(R.string.stock)));
        mTv_sold.setText(Utils.getString(R.string.sold_single, data.getQuantity_sold()));
        GlideUtils.loadImg(mContext, data.getShop_img(), mIv_img);
    }
}
