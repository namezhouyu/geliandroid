package com.geli.m.popup.sort;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geli.m.R;
import com.geli.m.bean.RestaurantSortBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author:  shen
 * date:    2019/7/23
 */
public class SortVH extends BaseViewHolder<RestaurantSortBean.DataBean> {

    Context mContext;
    @BindView(R.id.tv_item_VHSort)
    TextView mTvSort;

    public SortVH(ViewGroup parent, Context context) {
        super(parent, R.layout.vh_sort);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(RestaurantSortBean.DataBean data) {
        super.setData(data);

        mTvSort.setText(data.getType_name());


    }
}
