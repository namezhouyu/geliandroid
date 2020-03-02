package com.geli.m.mvp.home.index_fragment.search_activity.fragment.vh;

import android.content.Context;
import android.view.ViewGroup;
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

public class ShopSearchListViewHolder extends BaseViewHolder<FactoryBean> {
    Context mContext;
    private final TextView mTv_sold;
    private final TextView mTv_setfrom;
    private final TextView mTv_name;
    private final ImageView mIv_img;

    public ShopSearchListViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_search_list_shop);
        mContext = context;
        mIv_img = $(R.id.iv_itemview_shopsearch_list_img);
        mTv_name = $(R.id.tv_itemview_shopsearch_list_name);
        mTv_setfrom = $(R.id.tv_itemview_shopsearch_list_setfrom);
        mTv_sold = $(R.id.tv_itemview_shopsearch_list_sold);
    }

    @Override
    public void setData(FactoryBean data) {
        super.setData(data);
        mTv_name.setText(data.getShop_name());
        mTv_setfrom.setText(Utils.getString(R.string.setfrom_pieces, data.getMoq() + "", Utils.getString(R.string.stock)));
        mTv_sold.setText(Utils.getString(R.string.sold_single, data.getSales()));
        GlideUtils.loadImg(mContext, data.getShop_img(), mIv_img);
    }
}
