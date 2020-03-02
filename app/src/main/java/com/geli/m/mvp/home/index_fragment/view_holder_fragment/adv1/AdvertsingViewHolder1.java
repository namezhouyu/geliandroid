package com.geli.m.mvp.home.index_fragment.view_holder_fragment.adv1;

import android.content.Context;
import android.view.ViewGroup;
import com.geli.m.R;
import com.geli.m.bean.AtsBean;
import com.geli.m.bean.base.IndexBaseBean;
import com.geli.m.coustomview.AdvertisingView1;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import java.util.List;

/**
 * Created by l on 2018/4/9.
 * 广告
 *
 */
public class AdvertsingViewHolder1 extends BaseViewHolder<IndexBaseBean<List<AtsBean>>> {
    Context mContext;
    private final AdvertisingView1 mAdView;

    public AdvertsingViewHolder1(ViewGroup parent, Context context) {
        super(parent, R.layout.fragment_advertsing1);
        mContext = context;
        mAdView = $(R.id.av_advertsing1);
    }

    @Override
    public void setData(IndexBaseBean<List<AtsBean>> data) {
        super.setData(data);
        mAdView.setData(data.getData());
    }
}
