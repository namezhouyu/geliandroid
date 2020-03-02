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
 *
 * 商店详情 -- "推荐" 的 "项布局"
 */

public class GoodsLabelOrtherViewHolder extends BaseViewHolder<ShopInfoBean.DataEntity.CatResEntity.OthercartEntity> {
    private final CheckBox mCb_sort;

    SelectInterface mSelectInterface;
    Context mContext;

    public GoodsLabelOrtherViewHolder(ViewGroup parent, Context context, SelectInterface selectInterface) {
        super(parent, R.layout.itemview_shopdetails_orthertitle);
        mSelectInterface = selectInterface;
        mContext = context;
        mCb_sort = $(R.id.cb_shopdetails_orther_title);
    }

    @Override
    public void setData(ShopInfoBean.DataEntity.CatResEntity.OthercartEntity data) {
        super.setData(data);
        mCb_sort.setText(data.getGs_name() + "");


        if(mSelectInterface != null){
            ShopDetailsFragment.SelectBean selectBean = mSelectInterface.getSelect();

            if (selectBean.isOrther) {                                      /* 是 "推荐" 拿到"推荐商品的id" 赋值过去 */
                mCb_sort.setChecked(selectBean.bigId == data.getGs_id());
            }else{                                                          /* 不是 -- "推荐" -- 就不选中 */
                mCb_sort.setChecked(false);
            }
        }

    }

    public interface SelectInterface{
        /**
         * 获取目前选中的情况
         * @return
         */
        ShopDetailsFragment.SelectBean getSelect();
    }

}
