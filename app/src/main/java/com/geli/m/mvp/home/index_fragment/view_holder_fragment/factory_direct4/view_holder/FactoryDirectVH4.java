package com.geli.m.mvp.home.index_fragment.view_holder_fragment.factory_direct4.view_holder;

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
 * Created by Steam_l on 2017/12/18.
 *
 * 厂家直供 -- 模型3 -- 项布局
 */

public class FactoryDirectVH4 extends BaseViewHolder<FactoryBean> {
    Context mContext;
    private final ImageView mIv_img;
    private final TextView mTv_sold;
    private final TextView mTv_number;
    private final TextView mTv_unit;


    public FactoryDirectVH4(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_factdir4);
        mContext = context;
        mTv_unit = $(R.id.tv_itemview_factdir4_unit);
        mTv_number = $(R.id.tv_itemview_factdir4_number);
        mTv_sold = $(R.id.tv_itemview_factdir4_sold);
        mIv_img = $(R.id.iv_itemview_factdir4_img);
    }

    @Override
    public void setData(FactoryBean data) {
        super.setData(data);
        GlideUtils.loadImgRect(mContext, data.getShop_img(), mIv_img);
        mTv_number.setText(Utils.getString(R.string.setfrom_pieces, data.getMoq(), Utils.getString(R.string.stock)));
        mTv_sold.setText(Utils.getString(R.string.sold_single, data.getQuantity_sold()));
        mTv_unit.setText(data.getShop_name());
    }
}
