package com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.geli.m.R;
import com.geli.m.bean.RestaurantBean;
import com.geli.m.utils.GlideUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * <p>
 * 地方食品--列表
 */
public class LocalRestaurantListViewHolder extends BaseViewHolder<RestaurantBean.DataBean.LocalFoodResBean> {

    Context mContext;

    /** 地方食品图片 */
    @BindView(R.id.iv_restaurant_restaurantVH)
    ImageView mIvRestaurant;
    /** 地方食品名字 */
    @BindView(R.id.tv_restaurant_name_restaurantVH)
    TextView mTvRestaurantName;
    /** 地方食品描述 */
    @BindView(R.id.tv_restaurant_addr_restaurantVH)
    TextView mTvRestaurantDescribe;


    public LocalRestaurantListViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.vh_restaurant);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(RestaurantBean.DataBean.LocalFoodResBean data) {
        super.setData(data);

        GlideUtils.loadImgRect(mContext, data.getImg(), mIvRestaurant);
        mTvRestaurantName.setText(data.getTitle());
        //mTvRestaurantDescribe.setText(data.getNote());
    }
}
