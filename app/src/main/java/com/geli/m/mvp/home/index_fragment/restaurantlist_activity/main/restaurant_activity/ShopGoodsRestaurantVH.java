package com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.restaurant_activity;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.geli.m.R;
import com.geli.m.bean.RestaurantShopBean;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.ResourceUtil;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * <p>
 * 每个食品馆的商店中 -- 商品列表项
 */

public class ShopGoodsRestaurantVH extends BaseViewHolder<RestaurantShopBean.DataBean.GoodsResBean> {

    Context mContext;

    /** 商品图片 */
    @BindView(R.id.iv_goods_ShopGoodsRestaurantVH)
    ImageView mIvGoods;
    /** 商品名字 */
    @BindView(R.id.tv_goodsName_ShopGoodsRestaurantVH)
    TextView mTvGoodsName;
    /** 商品价格 */
    @BindView(R.id.tv_price_ShopGoodsRestaurantVH)
    TextView mTvPrice;

    public ShopGoodsRestaurantVH(ViewGroup parent, Context context) {
        super(parent, R.layout.vh_shop_goods_restaurant);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(final RestaurantShopBean.DataBean.GoodsResBean data) {
        super.setData(data);

        GlideUtils.loadImgRect(mContext, data.getGoods_thumb(), mIvGoods);
        mTvGoodsName.setText(data.getGoods_name());
        mTvPrice.setText(ResourceUtil.getStringFromResources(R.string.money_unit_2, data.getShop_price()));
    }
}
