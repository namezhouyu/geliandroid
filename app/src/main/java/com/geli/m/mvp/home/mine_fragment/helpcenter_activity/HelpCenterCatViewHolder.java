package com.geli.m.mvp.home.mine_fragment.helpcenter_activity;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.HelpCenterBean;
import com.geli.m.utils.GlideUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2018/3/8.
 *
 * 帮助中心 -- 头顶列表的"项"
 */

public class HelpCenterCatViewHolder extends BaseViewHolder<HelpCenterBean.DataEntity.CatListEntity> {

    Context mContext;

    /** 显示的帮助内容 */
    private final TextView mTv_content;
    private final ImageView mIv_img;

    public HelpCenterCatViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_helpcenter_catlist);
        mContext = context;
        mTv_content = $(R.id.tv_itemview_helpcenter_content);
        mIv_img = $(R.id.iv_itemview_helpcenter_img);
    }

    @Override
    public void setData(HelpCenterBean.DataEntity.CatListEntity data) {
        super.setData(data);
        mTv_content.setText(data.getCat_name());
        GlideUtils.loadImg(mContext, data.getCat_img(), mIv_img);
    }
}
