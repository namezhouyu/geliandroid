package com.geli.m.mvp.home.index_fragment.location_activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.City;
import com.geli.m.mvp.home.index_fragment.location_activity.LocationActivity;
import com.geli.m.utils.PinYinUtil;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2018/2/11.
 *
 * 定位中，选择城市中的，每一项的"布局"
 */
public class LocationViewHolder extends BaseViewHolder<City> {

    Context mContext;
    private final TextView mTv_city;
    private final TextView mTv_initials;

    /** 第一个position开始显示，因为第0个是头部 */
    int startShowPosition = 0;


    public LocationViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_location);
        mContext = context;
        mTv_city = $(R.id.city_name_tv);
        mTv_initials = $(R.id.initials_tv);
    }

    @Override
    public void setData(City data) {
        super.setData(data);
        mTv_city.setText(data.getArea_name());
        int position = getDataPosition();
        // 当前城市首字母 // toUpperCase转成大写
        String currentInitials = new PinYinUtil().getStringPinYin(data.getArea_name()).toUpperCase().substring(0, 1);

        if (position == startShowPosition) {                        /* 第一个position开始显示，因为第0个是头部 */
            mTv_initials.setVisibility(View.VISIBLE);
            mTv_initials.setText(currentInitials);

        } else {
            // 上一个城市首字母 //toUpperCase转成大写
            String previousInitials = new PinYinUtil().getStringPinYin(((City)((LocationActivity) mContext).mAdapter.getItem(position-1)).getArea_name()).substring(0, 1).toUpperCase();

            if (currentInitials.equals(previousInitials)) { /*当前城市首字母和上一城市的"首字母一样"隐藏当前城市首字母 */
                mTv_initials.setVisibility(View.GONE);
            } else {                                        /* 当前城市首字母和上一城市的"首字母不一样"显示当前城市首字母 */
                mTv_initials.setVisibility(View.VISIBLE);
                mTv_initials.setText(currentInitials);
            }

        }
    }
}
