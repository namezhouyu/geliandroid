package com.geli.m.mvp.home.index_fragment.view_holder_fragment.factory_direct3.view_holder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.FactoryBean;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.PriceUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2017/12/18.
 */
public class FactoryDirectVH3 extends BaseViewHolder<FactoryBean> {
    Context mContext;
    private final ImageView mIv_img;
    private final TextView mTv_price;
    private final TextView mTv_unit;
    private final TextView mTv_name;


    public FactoryDirectVH3(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_factdir3);
        mContext = context;
        mTv_name = $(R.id.tv_itemview_factdir3_name);
        mTv_unit = $(R.id.tv_itemview_factdir3_unit);
        mTv_price = $(R.id.tv_itemview_factdir3_price);
        mIv_img = $(R.id.iv_itemview_factdir3_img);
    }

    @Override
    public void setData(FactoryBean data) {
        super.setData(data);
        mTv_unit.setText(Utils.getString(R.string.from_unit, data.getShop_name()));
        if (data.getGoods_list().size() != 0) {
            FactoryBean.GoodsListEntity goodsListEntity = data.getGoods_list().get(0);
            mTv_name.setText(goodsListEntity.getGoods_name());
            mTv_price.setText(PriceUtils.getPrice(goodsListEntity.getShop_price()));
            GlideUtils.loadImg(mContext, goodsListEntity.getGoods_img(), mIv_img);
        }
    }
}
