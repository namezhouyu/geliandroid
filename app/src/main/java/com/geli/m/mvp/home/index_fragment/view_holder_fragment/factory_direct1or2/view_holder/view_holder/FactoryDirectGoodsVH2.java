package com.geli.m.mvp.home.index_fragment.view_holder_fragment.factory_direct1or2.view_holder.view_holder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.FactoryBean;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.PriceUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import static com.geli.m.BuildConfig.GL_IMAGE_URL;

/**
 * Created by Steam_l on 2017/12/18.
 *
 * 厂家直供 -- 模型2 -- 项布局中的 -- 食品信息项布局
 */

public class FactoryDirectGoodsVH2 extends BaseViewHolder<FactoryBean.GoodsListEntity> {
    Context mContext;
    /** 价格 */
    private final TextView mTv_price;
    /** 食品名称 */
    private final TextView mTv_nmae;
    /** 食品图片 */
    private final ImageView mIv_img;

    public FactoryDirectGoodsVH2(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_factdir2_goods);
        mContext = context;
        mIv_img = $(R.id.iv_itemview_factdir2_img);
        mTv_nmae = $(R.id.tv_itemview_factdir2_name);
        mTv_price = $(R.id.tv_itemview_factdir2_price);
    }

    @Override
    public void setData(FactoryBean.GoodsListEntity data) {
        super.setData(data);
        GlideUtils.loadImg(mContext, GL_IMAGE_URL + data.getGoods_img(), mIv_img);
        mTv_nmae.setText(data.getGoods_name());
        mTv_price.setText(PriceUtils.getPrice(data.getShop_price()));
    }
}
