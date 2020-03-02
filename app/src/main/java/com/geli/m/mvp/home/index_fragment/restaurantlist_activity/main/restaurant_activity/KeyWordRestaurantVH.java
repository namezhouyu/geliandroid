package com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.restaurant_activity;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.geli.m.R;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * <p>
 * 每个食品馆的商店中 -- 关键字列表项
 */

public class KeyWordRestaurantVH extends BaseViewHolder<String> {

    Context mContext;
    /** 0:默认红色 1:白色 */
    int mStyle = 0;

    /**  */
    @BindView(R.id.tv_KeyWordVH)
    TextView mTvKeyWord;

    public KeyWordRestaurantVH(ViewGroup parent, Context context, int style) {
        super(parent, R.layout.vh_key_word);
        mContext = context;
        ButterKnife.bind(this, itemView);
        mStyle = style;
    }

    public KeyWordRestaurantVH(ViewGroup parent, Context context) {
        super(parent, R.layout.vh_key_word);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(String data) {
        super.setData(data);

        mTvKeyWord.setText(data);
        if(mStyle == 1){
            mTvKeyWord.setTextColor(Utils.getColor(R.color.white));
            mTvKeyWord.setBackgroundResource(R.drawable.shape_bg_stroke_white_r2);
        }
    }


}
