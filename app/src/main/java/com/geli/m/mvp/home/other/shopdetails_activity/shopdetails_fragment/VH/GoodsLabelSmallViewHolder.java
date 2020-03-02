package com.geli.m.mvp.home.other.shopdetails_activity.shopdetails_fragment.VH;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.CheckBox;
import com.geli.m.R;
import com.geli.m.bean.ShopInfoBean;
import com.geli.m.mvp.home.other.shopdetails_activity.shopdetails_fragment.ShopDetailsFragment;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2018/1/12.
 *
 * 商店详情 -- 正常分类 的 小分类 的 "项布局"
 */

public class GoodsLabelSmallViewHolder extends BaseViewHolder<ShopInfoBean.DataEntity.CatResEntity.CartEntity> {

    private final CheckBox mCb_smallsort;
    ShopDetailsFragment mFragment;
    Context mContext;

    public GoodsLabelSmallViewHolder(ViewGroup parent, Context context, ShopDetailsFragment fragment) {
        super(parent, R.layout.itemview_shopdetails_smallsorttitle);
        mFragment = fragment;
        mContext = context;
        mCb_smallsort = $(R.id.cb_shopdetails_smalsort_title);
    }

    @Override
    public void setData(ShopInfoBean.DataEntity.CatResEntity.CartEntity data) {
        super.setData(data);
        mCb_smallsort.setText(data.getCat_name() + "");

        ShopDetailsFragment.SelectBean selectBean = mFragment.getSelectBean();
        mCb_smallsort.setChecked(selectBean.xiaoId == data.getCat_id());

    }
}
