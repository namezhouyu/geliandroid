package com.geli.m.mvp.home.index_fragment.view_holder_fragment.factory_direct.view_holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.geli.m.R;
import com.geli.m.bean.BrandBean;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.other.shopdetails_activity.ShopDetailsActivity;
import com.geli.m.utils.GlideUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import java.util.List;

import static com.geli.m.config.Constant.INTENT_SHOP_ID;

/**
 * Created by Steam_l on 2017/12/18.
 *
 * 品牌街 -- 项布局
 */
public class FactoryDirectVH extends BaseViewHolder<List<BrandBean>> {
    Context mContext;
    private final FrameLayout mFl_rootlayout2;
    private final FrameLayout mFl_rootlayout1;
    private final ImageView mIv_img1;
    private final ImageView mIv_img2;


    public FactoryDirectVH(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_factdir);
        mContext = context;

        mFl_rootlayout1 = $(R.id.fl_item_factdir_rootlayout1);
        mIv_img1 = $(R.id.iv_itemview_factdir_img1);

        mFl_rootlayout2 = $(R.id.fl_item_factdir_rootlayout2);
        mIv_img2 = $(R.id.iv_itemview_factdir_img2);
    }

    @Override
    public void setData(final List<BrandBean> data) {
        super.setData(data);
        if (data.size() == 1) {
            mIv_img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((BaseActivity) mContext).startActivity(ShopDetailsActivity.class, new Intent().putExtra(INTENT_SHOP_ID, data.get(0).getShop_id() + ""));
                }
            });
            mFl_rootlayout2.setVisibility(View.GONE);
            GlideUtils.loadImgRect(mContext, data.get(0).getShop_img(), mIv_img1);


        } else {
            mFl_rootlayout2.setVisibility(View.VISIBLE);
            GlideUtils.loadImgRect(mContext, data.get(0).getShop_img(), mIv_img1);
            GlideUtils.loadImgRect(mContext, data.get(1).getShop_img(), mIv_img2);
            mIv_img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((BaseActivity) mContext).startActivity(ShopDetailsActivity.class, new Intent().putExtra(INTENT_SHOP_ID, data.get(0).getShop_id() + ""));
                }
            });
            mIv_img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((BaseActivity) mContext).startActivity(ShopDetailsActivity.class, new Intent().putExtra(INTENT_SHOP_ID, data.get(1).getShop_id() + ""));
                }
            });
        }
    }
}
