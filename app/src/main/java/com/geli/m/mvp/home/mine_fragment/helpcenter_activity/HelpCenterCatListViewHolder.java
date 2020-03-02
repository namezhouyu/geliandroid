package com.geli.m.mvp.home.mine_fragment.helpcenter_activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.HelpCenterBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2018/3/8.
 *
 * 帮助中心 -- 底下列表的"项"
 *
 */
public class HelpCenterCatListViewHolder extends BaseViewHolder<HelpCenterBean.DataEntity.HelpListEntity> {

    Context mContext;
    private final ImageView mIv_img;
    private final TextView mTv_content;

    public HelpCenterCatListViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_helpcenter_catlist);
        mContext = context;
        mTv_content = $(R.id.tv_itemview_helpcenter_content);
        mIv_img = $(R.id.iv_itemview_helpcenter_img);
    }

    @Override
    public void setData(HelpCenterBean.DataEntity.HelpListEntity data) {
        super.setData(data);
        mIv_img.setVisibility(View.GONE);
        mTv_content.setText(data.getArt_title());
    }
}
