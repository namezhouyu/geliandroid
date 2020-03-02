package com.geli.m.mvp.home.find_fragment.findlist_fragment.vh;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.FindListBean;
import com.geli.m.mvp.home.find_fragment.findlist_fragment.FindListFragment;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by Steam_l on 2018/3/21.
 */

public class FindListVideoViewHolder extends BaseViewHolder<FindListBean.DataEntity> {
    Context mContext;


    /** 最外的标题 */
    private final TextView mTv_articletitle;

    /** 放视频图片的 */
    private final RoundedImageView mRiv_img;
    /** 放视频图片里面的播放按钮 */
    private final ImageView mIv_play;
    /** 放视频图片里面的标题 */
    private final TextView mTv_title;

    /** 底部头像 */
    private final RoundedImageView mRiv_avatar;
    /** 底部昵称 */
    private final TextView mTv_name;
    /** 浏览次数 */
    private final TextView mTv_viewnumber;
    /** 点赞 */
    private final CheckBox mCb_like;

    FindListFragment mFragment;

    public FindListVideoViewHolder(ViewGroup parent, Context context, FindListFragment fragment) {
        super(parent, R.layout.itemview_find_video);
        mContext = context;
        mFragment = fragment;
        mRiv_img = $(R.id.riv_itemview_find_video_img);
        mRiv_avatar = $(R.id.riv_itemview_find_video_avatar);
        mTv_name = $(R.id.tv_itemview_find_video_name);
        mTv_title = $(R.id.tv_itemview_find_video_title);
        mTv_viewnumber = $(R.id.tv_itemview_find_video_viewnumber);
        mTv_articletitle = $(R.id.tv_itemview_find_article1_title);
        mCb_like = $(R.id.cb_itemview_find_video_like);
        mIv_play = $(R.id.iv_itemview_find_video_play);
    }

    @Override
    public void setData(final FindListBean.DataEntity data) {
        super.setData(data);
        if (data.getType() == 1) {                          // 1一图
            mIv_play.setVisibility(View.VISIBLE);
            mTv_title.setVisibility(View.VISIBLE);
            mTv_articletitle.setVisibility(View.GONE);
            mTv_title.setText(data.getArticle_title());

        } else {                                            // 2多图
            mIv_play.setVisibility(View.GONE);
            mTv_title.setVisibility(View.GONE);
            mTv_articletitle.setVisibility(View.VISIBLE);
            mTv_articletitle.setText(data.getArticle_title());
        }
        for (String url : data.getCover_url()) {
            GlideUtils.loadImg(mContext, url, mRiv_img);
        }


        SpannableString spannableString = new SpannableString(Utils.getString(R.string.view_number) + "(" + data.getView_num() + ")");
        spannableString.setSpan(new ForegroundColorSpan(Utils.getColor(R.color.zhusediao)), Utils.getString(R.string.view_number).length(), spannableString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTv_viewnumber.setText(spannableString);
        mCb_like.setText("(" + data.getLike_num() + ")");
        mCb_like.setChecked(data.getIs_like() == 1);
        mCb_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCb_like.isChecked()) {
                    data.setIs_like(1);
                    data.setLike_num(data.getLike_num() + 1);
                } else {
                    data.setIs_like(0);
                    data.setLike_num(data.getLike_num() - 1);
                }
                mCb_like.setText("(" + data.getLike_num() + ")");
                mFragment.like(getDataPosition());
            }
        });
        FindListBean.DataEntity.AuthorResEntity author_res = data.getAuthor_res();
        if (null != author_res) {
            GlideUtils.loadImg(mContext, author_res.getAuthor_icon(), mRiv_avatar);
            mTv_name.setText(author_res.getAuthor_name());
        }
    }
}
