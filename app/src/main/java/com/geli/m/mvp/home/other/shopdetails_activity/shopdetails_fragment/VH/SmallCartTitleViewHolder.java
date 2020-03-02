package com.geli.m.mvp.home.other.shopdetails_activity.shopdetails_fragment.VH;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.CartBean;
import com.geli.m.mvp.home.other.shopdetails_activity.shopdetails_fragment.ShopDetailsFragment;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2018/1/19.
 *
 * "购物车"标题
 */
public class SmallCartTitleViewHolder extends BaseViewHolder<CartBean.DataEntity> {
    Context mContext;

    /** 标题 */
    private final TextView mTv_title;
    /** 起订量 */
    private final TextView mTv_moq;
    ShopDetailsFragment mFragment;

    SmallCartTitleI mSmallCartTitleI;

    public SmallCartTitleViewHolder(ViewGroup parent, Context context, SmallCartTitleI smallCartTitleI) {
        super(parent, R.layout.itemview_smallcart_shopname);
        mContext = context;
        mSmallCartTitleI = smallCartTitleI;
        mTv_title = $(R.id.tv_itemview_smallcart_title);
        mTv_moq = $(R.id.tv_itemview_smallcart_moq);
    }

    @Override
    public void setData(CartBean.DataEntity data) {
        super.setData(data);
        mTv_title.setText(data.getShop_name());

        // 设置起订量
        if (mSmallCartTitleI != null) {
            int moq = data.getMoq();    // 商店的起订量 -- 不是单个商品的
            int moqSize = mSmallCartTitleI.getCartGoodsNumber();
            if (moq - moqSize > 0) {
                mTv_moq.setVisibility(View.VISIBLE);
                mTv_moq.setText(Utils.getString(R.string.moq, (moq - moqSize) + "", data.unit));
            }else{
                mTv_moq.setVisibility(View.GONE);
            }
        }
    }

    public interface SmallCartTitleI{
        /**
         * 获取 -- 当前购物车中所有商品的数量之和
         * @return
         */
        int getCartGoodsNumber();
    }
}
