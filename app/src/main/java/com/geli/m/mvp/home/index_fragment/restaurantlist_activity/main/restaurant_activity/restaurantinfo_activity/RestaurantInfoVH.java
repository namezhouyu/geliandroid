package com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.restaurant_activity.restaurantinfo_activity;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.geli.m.R;
import com.geli.m.utils.GlideUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * 列表的项 -- 图片
 */
public class RestaurantInfoVH extends BaseViewHolder<String> {


    Context mContext;
    @BindView(R.id.iv_RestaurantInfoVH)
    ImageView mIvRestaurantInfoVH;

    public RestaurantInfoVH(ViewGroup parent, Context context) {
        super(parent, R.layout.vh_restaurant_info);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(String data) {
        super.setData(data);

        GlideUtils.loadImg(mContext, data, mIvRestaurantInfoVH);
    }
}
