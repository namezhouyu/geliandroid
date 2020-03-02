package com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.geli.m.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * <p>
 * 每个食品馆的商店中 -- 关键字列表项
 */

public class KeyWordLocalRestaurantVH extends BaseViewHolder<String> {

    Context mContext;

    /**  */
    @BindView(R.id.tv_KeyWordVH)
    TextView mTvKeyWord;

    public KeyWordLocalRestaurantVH(ViewGroup parent, Context context) {
        super(parent, R.layout.vh_key_word);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(String data) {
        super.setData(data);

        mTvKeyWord.setText(data);
    }


}
