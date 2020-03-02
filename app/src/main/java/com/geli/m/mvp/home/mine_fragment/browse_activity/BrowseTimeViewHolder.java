package com.geli.m.mvp.home.mine_fragment.browse_activity;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.BrowseBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2018/3/26.
 *
 * 浏览时间(某个时间段)
 */

public class BrowseTimeViewHolder extends BaseViewHolder<BrowseBean.BrowseTime> {
    Context mContext;
    private final TextView mTv_time;

    public BrowseTimeViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_browsetime);
        mContext = context;
        mTv_time = $(R.id.tv_itemview_browsetime_time);
    }

    @Override
    public void setData(BrowseBean.BrowseTime data) {
        super.setData(data);
        mTv_time.setText(data.getTime());
    }
}
