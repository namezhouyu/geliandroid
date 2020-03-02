package com.geli.m.mvp.home.index_fragment.view_holder_fragment.adv3;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.geli.m.R;
import com.geli.m.bean.AtsBean;
import com.geli.m.bean.base.IndexBaseBean;
import com.geli.m.coustomview.AdvertisingView3;
import com.geli.m.coustomview.StrokeTextView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import java.util.List;

/**
 * Created by l on 2018/4/9.
 */

public class AdvertsingViewHolder3 extends BaseViewHolder<IndexBaseBean<List<AtsBean>>> {
    private final StrokeTextView mTv_title;
    Context mContext;
    private final AdvertisingView3 mAdView;

    public AdvertsingViewHolder3(ViewGroup parent, Context context) {
        super(parent, R.layout.fragment_advertsing3);
        mContext = context;
        mTv_title = $(R.id.stv);
        mAdView = $(R.id.av_advertsing3);
    }

    @Override
    public void setData(IndexBaseBean<List<AtsBean>> data) {
        super.setData(data);
        if (data.getTitle_is_show() == 1) {
            mTv_title.setText(data.getModel_title());
        } else {
            mTv_title.setVisibility(View.GONE);
        }
        mAdView.setData(data.getData());
    }
}
