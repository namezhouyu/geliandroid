package com.geli.m.mvp.home.index_fragment.view_holder_fragment.goods_product2.view_holder;

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
 * Created by Steam_l on 2017/12/17.
 * 左右小中间大
 *
 * 商品推荐 -- 模块显示风格2 -- 项布局
 */

public class GoodsProductVH2 extends BaseViewHolder<RecommendGoodsBean> {
    boolean first = true;
    private Context mContext;
    private final ImageView mIv_img;
    private final TextView mTv_price;
    private final TextView mTv_name;

    public GoodsProductVH2(ViewGroup parent, Context context, int maxWidth) {
        super(parent, R.layout.itemview_goodsproduct2);
        mContext = context;

        // 左右小中间大
        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        layoutParams.width = maxWidth;
        itemView.setLayoutParams(layoutParams);

        mIv_img = $(R.id.iv_itemview_goodsproduct2_img);
        mTv_name = $(R.id.tv_itemview_goodsproduct2_name);
        mTv_price = $(R.id.tv_itemview_goodsproduct2_price);
    }

    @Override
    public void setData(RecommendGoodsBean data) {
        super.setData(data);
        GlideUtils.loadImg(mContext, data.getGoods_img(), mIv_img);

        mTv_name.setText(data.getGoods_name());
        mTv_price.setText(PriceUtils.getPrice(data.getShop_price()));
    }


}
