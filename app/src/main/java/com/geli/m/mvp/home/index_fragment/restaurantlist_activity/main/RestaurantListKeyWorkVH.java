package com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.geli.m.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * <p>
 * 食品馆列表中的关键词列表
 */

public class RestaurantListKeyWorkVH extends BaseViewHolder<String> {

    Context mContext;

    /** 关键词 */
    @BindView(R.id.tv_KeyWord_KeyWordRestaurantVH)
    TextView mTvKeyWord;

    public RestaurantListKeyWorkVH(ViewGroup parent, Context context) {
        super(parent, R.layout.vh_keyword_restaurant);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(String data) {
        super.setData(data);

        mTvKeyWord.setText(data);
    }
}
