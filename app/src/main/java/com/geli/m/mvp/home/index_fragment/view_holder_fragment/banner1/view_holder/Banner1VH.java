package com.geli.m.mvp.home.index_fragment.view_holder_fragment.banner1.view_holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.geli.m.R;
import com.geli.m.bean.AtsBean;
import com.geli.m.mvp.home.index_fragment.main.IndexFragment;
import com.geli.m.utils.GlideUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2017/12/17.
 * 左右小中间大
 */
public class Banner1VH extends BaseViewHolder<AtsBean> {
    private Context mContext;
    private final ImageView mIv_img;
    IndexFragment mFragment;

    public Banner1VH(ViewGroup parent, Context context, double maxWisth, double maxHeight, IndexFragment bannerFragment) {
        super(parent, R.layout.itemview_banner);
        mContext = context;
        mFragment = bannerFragment;
        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        layoutParams.width = (int) maxWisth;
        layoutParams.height = (int) maxHeight;
        itemView.setLayoutParams(layoutParams);
        mIv_img = $(R.id.itemview_banner_img);
    }

    @Override
    public void setData(final AtsBean data) {
        super.setData(data);
        GlideUtils.loadAvatar(mContext, data.getAts_pic(), mIv_img);
        mIv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragment.bannerOnClicker(mContext,data);
            }
        });
    }
}
