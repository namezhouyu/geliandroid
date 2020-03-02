package com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.geli.m.R;
import com.geli.m.bean.RestaurantGoodsBean;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.ResourceUtil;
import com.geli.m.utils.StringUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * <p>
 * 每个食品馆的 -- 商品列表项
 */

public class GoodsLocalRestaurantVH extends BaseViewHolder<RestaurantGoodsBean.DataBean> {

    Context mContext;

//    /**
//     * 商品图片
//     */
//    @BindView(R.id.iv_goods_ShopGoodsRestaurantVH)
//    ImageView mIvGoods;
//    /**
//     * 商品名字
//     */
//    @BindView(R.id.tv_goodsName_ShopGoodsRestaurantVH)
//    TextView mTvGoodsName;
//    /**
//     * 商品价格
//     */

    @BindView(R.id.iv_goods_GoodsRestaurantVH)
    ImageView mIvGoods;
    @BindView(R.id.tv_goodsName_GoodsRestaurantVH)
    TextView mTvGoodsName;
    @BindView(R.id.tv_shop_GoodsRestaurantVH)
    TextView mTvShop;
    @BindView(R.id.tv_stall_GoodsRestaurantVH)
    TextView mTvStall;
    @BindView(R.id.tv_price_GoodsRestaurantVH)
    TextView mTvPrice;

    public GoodsLocalRestaurantVH(ViewGroup parent, Context context) {
        super(parent, R.layout.vh_goods_restaurant);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(final RestaurantGoodsBean.DataBean data) {
        super.setData(data);

        GlideUtils.loadImgRect(mContext, data.getGoods_thumb(), mIvGoods);
        mTvGoodsName.setText(data.getGoods_name());
        mTvShop.setText(data.getShop_name());
        if(StringUtils.isNotEmpty(data.getStall_name())){
            if(mTvStall.getVisibility() != View.VISIBLE){
                mTvStall.setVisibility(View.VISIBLE);
            }
            mTvStall.setText(data.getStall_name());
        }else {
            mTvStall.setVisibility(View.INVISIBLE);
        }
        mTvPrice.setText(ResourceUtil.getStringFromResources(R.string.money_unit_2, data.getShop_price()));
    }
}
