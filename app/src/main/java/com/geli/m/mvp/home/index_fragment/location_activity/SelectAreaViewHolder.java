package com.geli.m.mvp.home.index_fragment.location_activity;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.District;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2018/3/9.
 */

public class SelectAreaViewHolder extends BaseViewHolder<District> {
    Context mContext;
    private final TextView mTv_name;

    public SelectAreaViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_location);
        mContext = context;
        mTv_name = $(R.id.city_name_tv);
    }

    @Override
    public void setData(District data) {
        super.setData(data);
        mTv_name.setText(data.getArea_name());
    }
}
