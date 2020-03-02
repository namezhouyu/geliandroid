package com.geli.m.mvp.home.mine_fragment.browse_activity;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.BrowseBean;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.PriceUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2018/3/26.
 *
 * 浏览记录"项"
 */

public class BrowseViewHolder extends BaseViewHolder<BrowseBean.DataEntity> {
    Context mContext;
    /** 价格 */
    private final TextView mTv_price;
    /** 商品 */
    private final TextView mTv_name;
    /** 商品图片 */
    private final ImageView mIv_img;

    public BrowseViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_browse);
        mContext = context;
        mIv_img = $(R.id.iv_itemview_browse_img);
        mTv_name = $(R.id.tv_itemview_browse_name);
        mTv_price = $(R.id.tv_itemview_browse_price);
    }

    @Override
    public void setData(BrowseBean.DataEntity data) {
        super.setData(data);
        GlideUtils.loadImg(mContext, data.getGoods_thumb(), mIv_img);
        mTv_name.setText(data.getGoods_name());
        mTv_price.setText(PriceUtils.getPrice(data.getShop_price()));
    }
}
