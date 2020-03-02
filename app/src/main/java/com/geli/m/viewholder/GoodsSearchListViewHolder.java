package com.geli.m.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geli.m.R;
import com.geli.m.bean.CartBean;
import com.geli.m.bean.OverseasGoodsOuterBean;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.PriceUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2017/12/29.
 */

public class GoodsSearchListViewHolder extends BaseViewHolder<OverseasGoodsOuterBean.OverseasGoodsBean> {
    Context mContext;
    private final TextView mTv_price;
    private final TextView mTv_specifi;
    private final TextView mTv_name;
    private final ImageView mIv_img;

    public GoodsSearchListViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_search_list_goods);
        mContext = context;
        mIv_img = $(R.id.iv_itemview_goodssearch_gird_img);
        mTv_name = $(R.id.tv_itemview_goodssearch_list_name);
        mTv_specifi = $(R.id.tv_itemview_goodssearch_list_spectifi);
        mTv_price = $(R.id.tv_itemview_goodssearch_list_price);
    }

    @Override
    public void setData(OverseasGoodsOuterBean.OverseasGoodsBean data) {
        super.setData(data);
        mTv_name.setText(data.getGoods_name());
        mTv_price.setText(PriceUtils.getPrice(data.getShop_price()));
        if (data.getSpecifications() != null) {
            String specifi = "";
            for (CartBean.DataEntity.CartListEntity.SpecificationEntity entity : data.getSpecifications()) {
                specifi += entity.getKey() + ":" + entity.getValue() + ";";
            }
            if (specifi.length() > 0) {
                specifi = specifi.substring(0, specifi.length() - 1);
            }
            mTv_specifi.setText(specifi);
        }
        GlideUtils.loadImg(mContext, data.getGoods_thumb(), mIv_img);
    }
}
