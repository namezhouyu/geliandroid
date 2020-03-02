package com.geli.m.mvp.home.index_fragment.view_holder_fragment.goods_product1.view_holder;

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
 * 商品推荐 -- 模块显示风格1 -- 项布局
 */
public class GoodsProductVH1 extends BaseViewHolder<RecommendGoodsBean> {
    Context mContext;
    private final TextView mTv_price;
    private final TextView mTv_name;
    private final ImageView mIv_img;

    public GoodsProductVH1(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_goodsproduct1);

        mContext = context;
        mIv_img = $(R.id.iv_itemview_goodsproduct1_img);
        mTv_name = $(R.id.tv_itemview_goodsproduct1_name);
        mTv_price = $(R.id.tv_itemview_goodsproduct1_price);
    }

    @Override
    public void setData(RecommendGoodsBean data) {
        super.setData(data);
        GlideUtils.loadImg(mContext, data.getGoods_img(), mIv_img);
        mTv_name.setText(data.getGoods_name());
        mTv_price .setText(PriceUtils.getPrice(data.getShop_price()));
    }
}
