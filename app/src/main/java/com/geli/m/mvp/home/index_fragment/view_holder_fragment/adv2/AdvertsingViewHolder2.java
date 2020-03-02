package com.geli.m.mvp.home.index_fragment.view_holder_fragment.adv2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.AtsBean;
import com.geli.m.bean.base.IndexBaseBean;
import com.geli.m.coustomview.AdvertisingView2;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import java.util.List;

/**
 * Created by l on 2018/4/9.
 */

public class AdvertsingViewHolder2 extends BaseViewHolder<IndexBaseBean<List<AtsBean>>> {
    private final AdvertisingView2 mAdView;
    Context mContext;
    private final TextView mTv_title;

    public AdvertsingViewHolder2(ViewGroup parent, Context context) {
        super(parent, R.layout.fragment_advertsing2);
        mContext = context;
        mTv_title = $(R.id.tv_advertsin2_title);
        mAdView = $(R.id.av_advertsing2);
    }

    @Override
    public void setData(IndexBaseBean<List<AtsBean>> data) {
        super.setData(data);
        if (data.getTitle_is_show() == 1) {
            mTv_title.setText(Utils.getString(R.string.goodstaste, data.getModel_title()));
        } else {
            mTv_title.setVisibility(View.GONE);
        }
        mAdView.setData(data.getData());
    }
}
