package com.geli.m.mvp.home.index_fragment.view_holder_fragment.retail_center.view_holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.SellBean;
import com.geli.m.mvp.home.index_fragment.main.IndexFragment;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2017/12/20.
 *
 * 批零中心模块里面的列表控件的项布局
 */
public class RetailCenterVH extends BaseViewHolder<SellBean> {

    Context mContext;
    /** 批零中心的名称 */
    private final TextView mTv_name;
    /** 距离 */
    private final TextView mTv_distance;
    /** 销售数量 */
    private final TextView mTv_sold;
    /** 图片(批零中心) */
    private final ImageView mIv_img;

    public RetailCenterVH(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_retailcenter);
        mContext = context;
        mIv_img = $(R.id.iv_itemview_retailcenter_img);
        mTv_sold = $(R.id.tv_itemview_retailcenter_sold);
        mTv_distance = $(R.id.tv_itemview_retailcenter_distance);
        mTv_name = $(R.id.tv_itemview_retailcenter_name);
    }

    @Override
    public void setData(SellBean data) {
        super.setData(data);

        GlideUtils.loadImgRect(mContext, data.getShop_img(), mIv_img, false);

        // 正常情况下 "经纬度" 是通过"高德地图获取的"， 然后向后台请求数据， "data.getDistance()"这个就是和"xxx中心"的"距离"
        if (!TextUtils.isEmpty(data.getDistance() + "") &&
                !TextUtils.isEmpty(IndexFragment.mAreaBean.getLa()) &&
                !TextUtils.isEmpty(IndexFragment.mAreaBean.getLo())) {
            mTv_distance.setText(Utils.getString(R.string.distance, data.getDistance() + ""));
        } else {
            mTv_distance.setText("");
        }
        mTv_name.setText(data.getShop_name());
        mTv_sold.setText(Utils.getString(R.string.sold_single, data.getQuantity_sold()));
    }
}
