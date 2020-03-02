package com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.restaurant_activity;

import android.content.Context;
import android.graphics.Paint;
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

import static com.geli.m.config.Constant.Gs_Id_resale;

/**
 * <p>
 * 每个食品馆的 -- 商品列表项
 */

public class GoodsRestaurantVH extends BaseViewHolder<RestaurantGoodsBean.DataBean> {

    Context mContext;


    @BindView(R.id.iv_goods_GoodsRestaurantVH)
    ImageView mIvGoods;
    @BindView(R.id.tv_goodsName_GoodsRestaurantVH)
    TextView mTvGoodsName;
    /** 可零售 */
    @BindView(R.id.tv_retail_GoodsRestaurantVH)
    TextView mTvRetail;
    @BindView(R.id.tv_shop_GoodsRestaurantVH)
    TextView mTvShop;
    @BindView(R.id.tv_stall_GoodsRestaurantVH)
    TextView mTvStall;
    @BindView(R.id.tv_price_GoodsRestaurantVH)
    TextView mTvPrice;

    public GoodsRestaurantVH(ViewGroup parent, Context context) {
        super(parent, R.layout.vh_goods_restaurant);
        mContext = context;
        ButterKnife.bind(this, itemView);

        mTvStall.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        mTvStall.getPaint().setAntiAlias(true);                  //抗锯齿
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
            mTvStall.setText("(" + data.getStall_name() + ")");
        }else {
            mTvStall.setVisibility(View.INVISIBLE);
        }
        mTvPrice.setText(ResourceUtil.getStringFromResources(R.string.money_unit_2, data.getShop_price()));


        if(data.getGs_id() == Gs_Id_resale){
            mTvRetail.setVisibility(View.VISIBLE);
        }else {
            mTvRetail.setVisibility(View.INVISIBLE);
        }
    }
}
