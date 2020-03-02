package com.geli.m.mvp.home.index_fragment.view_holder_fragment.may_you_like.view_holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.InterestGoodsBean;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.other.goodsdetails_activity.GoodsDetailsActivity;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.PriceUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import java.util.List;

import static com.geli.m.config.Constant.INTENT_GOODS_ID;

/**
 * Created by Steam_l on 2017/12/18.
 *
 * 推荐商品(猜你喜欢) -- 项布局 -- 每项有两个商品
 */
public class MayYouLikeVH extends BaseViewHolder<List<InterestGoodsBean>> {
    Context mContext;
    private final RelativeLayout mFl_root2;
    private final RelativeLayout mFl_root1;

    private final ImageView mIv_img1;
    private final TextView mTv_price1;
    private final TextView mTv_name1;

    private final ImageView mIv_img2;
    private final TextView mTv_price2;
    private final TextView mTv_name2;

    public MayYouLikeVH(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_youlike);

        mContext = context;
        mFl_root1 = $(R.id.fl_item_youlike_rootlayout1);
        mFl_root2 = $(R.id.fl_item_youlike_rootlayout2);

        mTv_name1 = $(R.id.tv_itemview_factdir3_name);
        mTv_price1 = $(R.id.tv_itemview_factdir3_price);
        mIv_img1 = $(R.id.iv_itemview_factdir3_img);

        mTv_name2 = $(R.id.tv_itemview_factdir2_name);
        mTv_price2 = $(R.id.tv_itemview_factdir2_price);
        mIv_img2 = $(R.id.iv_itemview_factdir2_img);

    }

    @Override
    public void setData(List<InterestGoodsBean> data) {
        super.setData(data);

        final InterestGoodsBean interestGoodsBean = data.get(0);
        mTv_name1.setText(interestGoodsBean.getGoods_name());
        mTv_price1.setText(PriceUtils.getPrice(interestGoodsBean.getShop_price()));
        GlideUtils.loadImg(mContext, interestGoodsBean.getGoods_thumb(), mIv_img2);
        // GlideUtils.loadImgRect(mContext, interestGoodsBean.getGoods_thumb(), mIv_img2, false);

        mFl_root1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent().putExtra(INTENT_GOODS_ID,
                        interestGoodsBean.getGoods_id() + "");
                ((BaseActivity) mContext).startActivity(GoodsDetailsActivity.class, intent);
            }
        });

        if (data.size() == 1) {                         // 只有一条的，那只能隐藏这个
            mFl_root2.setVisibility(View.GONE);
        } else {
            mFl_root2.setVisibility(View.VISIBLE);
            final InterestGoodsBean interestGoodsBean1 = data.get(1);
            mTv_name2.setText(interestGoodsBean1.getGoods_name());
            mTv_price2.setText(PriceUtils.getPrice(interestGoodsBean1.getShop_price()));
            GlideUtils.loadImg(mContext, interestGoodsBean1.getGoods_thumb(), mIv_img1);
            mFl_root2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent().putExtra(INTENT_GOODS_ID,
                            interestGoodsBean1.getGoods_id() + "");
                    ((BaseActivity) mContext).startActivity(GoodsDetailsActivity.class, intent);
                }
            });

        }
    }
}
