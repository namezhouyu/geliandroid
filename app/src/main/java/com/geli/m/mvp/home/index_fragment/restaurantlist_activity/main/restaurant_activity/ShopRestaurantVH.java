package com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.restaurant_activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.geli.m.R;
import com.geli.m.bean.RestaurantShopBean;
import com.geli.m.config.Constant;
import com.geli.m.coustomview.MyEasyRecyclerView;
import com.geli.m.mvp.home.other.goodsdetails_activity.GoodsDetailsActivity;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import java.util.Arrays;
import java.util.List;

import static com.geli.m.config.Constant.INTENT_GOODS_ID;

/**
 * <p>
 * 每个食品馆的商店列表项
 */

public class ShopRestaurantVH extends BaseViewHolder<RestaurantShopBean.DataBean> {

    Context mContext;

    /** 商店图片 */
    @BindView(R.id.iv_shop_ShopRestaurantVH)
    ImageView mIvShop;
    /** 商店名称 */
    @BindView(R.id.tv_shopName_ShopRestaurantVH)
    TextView mTvShopName;
    /** 拼团 */
    @BindView(R.id.tv_Assemble_ShopRestaurantVH)
    TextView mTvAssemble;
    /** 商店描述 */
    @BindView(R.id.tv_description_ShopRestaurantVH)
    TextView mTvDescription;
//    /** 商店摊位(详细信息) */
//    @BindView(R.id.tv_stallInfo_ShopRestaurantVH)
//    TextView mTvStallInfo;
    /** 商店摊位 */
    @BindView(R.id.tv_stall_ShopRestaurantVH)
    TextView mTvStall;
    /** 商店关键字--列表 */
    @BindView(R.id.erv_keyWord_ShopRestaurantVH)
    MyEasyRecyclerView mErvKeyWord;
    /** 商店的商品列表 */
    @BindView(R.id.erv_goods_ShopRestaurantVH)
    MyEasyRecyclerView mErvGoods;


    RecyclerArrayAdapter mAdapterKeyWord;
    RecyclerArrayAdapter mAdapterGoods;

    public ShopRestaurantVH(ViewGroup parent, Context context) {
        super(parent, R.layout.vh_shop_restaurant);
        mContext = context;
        ButterKnife.bind(this, itemView);

        initErv();

        mTvStall.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        mTvStall.getPaint().setAntiAlias(true);                  //抗锯齿
    }

    @Override
    public void setData(final RestaurantShopBean.DataBean data) {
        super.setData(data);

        GlideUtils.loadImgRect(mContext, data.getShop_img_thumb(), mIvShop);
        mTvShopName.setText(data.getShop_name());
        mTvDescription.setText(data.getShop_intro());

        if(StringUtils.isNotEmpty(data.getStall_name())){
            if(mTvStall.getVisibility() != View.VISIBLE){
                mTvStall.setVisibility(View.VISIBLE);
            }
            mTvStall.setText("(" + data.getStall_name() + ")");
        }else {
            mTvStall.setVisibility(View.INVISIBLE);
        }

        if(data.getIs_resale() == Constant.resale_true){
            mTvAssemble.setVisibility(View.VISIBLE);
        }else {
            mTvAssemble.setVisibility(View.INVISIBLE);
        }

        mAdapterGoods.clear();
        mAdapterKeyWord.clear();

        mAdapterGoods.addAll(data.getGoods_res());

        String[] goodsMains = data.getGoods_main().split(",");
        if(StringUtils.isNotEmpty(data.getGoods_main()) && goodsMains.length > 0 ){
            List goodsMainList = Arrays.asList(goodsMains);
            if(goodsMainList != null && goodsMainList.size() > 3){
                mAdapterKeyWord.addAll(goodsMainList.subList(0, 3));
            }else {
                mAdapterKeyWord.addAll(goodsMainList);
            }
        }



    }



    private void initErv() {
        mErvKeyWord.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mErvKeyWord.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.transparent),
                Utils.dip2px(mContext, 11f), 0, 0));
        mErvKeyWord.setAdapterWithProgress(initAdapterKeyWord());

        mErvGoods.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mErvGoods.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.transparent),
                Utils.dip2px(mContext, 12f), 0, 0));
        mErvGoods.setAdapterWithProgress(initAdapterGoods());
    }

    private RecyclerArrayAdapter initAdapterKeyWord() {
        mAdapterKeyWord = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new KeyWordRestaurantVH(parent, mContext);
            }
        };

        return mAdapterKeyWord;
    }

    private RecyclerArrayAdapter initAdapterGoods() {
        mAdapterGoods = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new ShopGoodsRestaurantVH(parent, mContext);
            }
        };


        mAdapterGoods.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                RestaurantShopBean.DataBean.GoodsResBean bean = (RestaurantShopBean.DataBean.GoodsResBean) mAdapterGoods.getItem(position);
                Intent intent = new Intent(mContext, GoodsDetailsActivity.class);
                intent.putExtra(INTENT_GOODS_ID, bean.getGoods_id() + "");
                mContext.startActivity(intent);
            }
        });

        return mAdapterGoods;
    }
}
