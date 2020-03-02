package com.geli.m.mvp.home.find_fragment.findlist_fragment.details;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.FindDetailsBean;
import com.geli.m.utils.GlideUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by Steam_l on 2018/3/22.
 */

public class RelatedVideoViewHolder extends BaseViewHolder<FindDetailsBean.DataEntity.AboutResEntity> {
    Context mContext;
    private final RoundedImageView mRiv_img;
    private final TextView mTv_time;
    private final TextView mTv_title;
    private final ImageView mIv_play;

    public RelatedVideoViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_related_video);
        mContext = context;
        mTv_title = $(R.id.tv_itemview_related_video_title);
        mTv_time = $(R.id.tv_itemview_related_video_time);
        mRiv_img = $(R.id.riv_itemview_related_video_img);
        mIv_play = $(R.id.iv_itemview_related_video_play);
    }

    @Override
    public void setData(FindDetailsBean.DataEntity.AboutResEntity data) {
        super.setData(data);
        GlideUtils.loadImg(mContext, data.getContent_cover_url(), mRiv_img);
        mTv_title.setText(data.getArticle_title());
        if (data.getType() == 1) {
            mTv_time.setText(data.getVideo_duration());
            mIv_play.setVisibility(View.VISIBLE);
            mTv_time.setVisibility(View.VISIBLE);
        } else {
            mIv_play.setVisibility(View.GONE);
            mTv_time.setVisibility(View.GONE);
        }
    }
}
