package com.geli.m.mvp.home.other.shopdetails_activity.shopdetails_fragment.VH;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.geli.m.R;
import com.geli.m.bean.AtsBean;
import com.geli.m.coustomview.ShapeImageView;
import com.geli.m.mvp.home.index_fragment.main.IndexFragment;
import com.geli.m.utils.GlideUtils;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * author:  shen
 * date:    2018/10/31
 *
 *
 * 这个没用到~
 */
public class AdvImgVH implements RecyclerArrayAdapter.ItemView {

    private AtsBean mAdvImg;

    Context mContext;
    public AdvImgVH(Context context){
        mContext = context;
    }


    @Override
    public View onCreateView(ViewGroup parent) {
        return LayoutInflater.from(mContext).inflate(R.layout.itemview_shopadvimg, parent, false);
    }

    @Override
    public void onBindView(View headerView) {
        ShapeImageView imageView = (ShapeImageView) headerView.findViewById(R.id.siv_itemview_shopadvimg);
        GlideUtils.loadAvatar(mContext, mAdvImg.getAts_pic(), imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IndexFragment.bannerOnClicker(mContext, mAdvImg);
            }
        });
    }
}
