package com.geli.m.mvp.home.index_fragment.view_holder_fragment.goods_product3.view_holder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.RecommendGoodsBean;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.PriceUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2017/12/18.
 *
 * 商品推荐 -- 模块显示风格3 -- 项布局
 */
public class GoodsProductVH3 extends BaseViewHolder<RecommendGoodsBean> {
    Context mContext;
    private final ImageView mIv_img;
    private final TextView mTv_price;
    private final TextView mTv_name;

    public GoodsProductVH3(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_goodsproduct3);
        mContext = context;
        mTv_name = $(R.id.tv_itemview_goodsproduct3_name);
        mTv_price = $(R.id.tv_itemview_goodsproduct3_money);
        mIv_img = $(R.id.iv_itemview_goodsproduct3_img);
    }

    @Override
    public void setData(RecommendGoodsBean data) {
        super.setData(data);
        mTv_name.setText(data.getGoods_name());
        mTv_price.setText(PriceUtils.getPrice(data.getShop_price()));
        GlideUtils.loadImg(mContext, data.getGoods_img(), mIv_img);
    }
}
