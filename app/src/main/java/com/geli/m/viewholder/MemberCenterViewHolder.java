package com.geli.m.viewholder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geli.m.R;
import com.geli.m.bean.MemberBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2018/3/12.
 *
 * 会员中心界面 -- 列表中的每个项 -- 如:会员成长规则，积分使用规则
 */

public class MemberCenterViewHolder extends BaseViewHolder<MemberBean.DataEntity.ArticleEntity> {
    Context mContext;
    /** 内容 */
    private final TextView mTv_content;
    /** 图片 */
    private final ImageView mIv_img;

    public MemberCenterViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_helpcenter_catlist);
        mContext = context;
        mTv_content = $(R.id.tv_itemview_helpcenter_content);
        mIv_img = $(R.id.iv_itemview_helpcenter_img);
    }

    @Override
    public void setData(MemberBean.DataEntity.ArticleEntity data) {
        super.setData(data);
        mTv_content.setText(data.getArt_title());
        mIv_img.setVisibility(View.GONE);
    }
}
