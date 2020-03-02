package com.geli.m.mvp.home.mine_fragment.accountmanagement_activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.AccountManagementBean;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.other.accountperiod_activity.AccountPeriodActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import static com.geli.m.config.Constant.AP_STATUS;
import static com.geli.m.config.Constant.AP_Status_AlreadyOpened;
import static com.geli.m.config.Constant.INTENT_SHOP_ID;

/**
 * author:  shen
 * date:    2018/11/13
 */
public class ApNotOpenViewHolder extends BaseViewHolder<AccountManagementBean.DataEntity> {

    Context mContext;
    /** 商店名字 */
    TextView mTvShopName;
    /** 开通账期 */
    Button mBtnOpen;

    public ApNotOpenViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_ap_not_open);

        mContext = context;

        mTvShopName = $(R.id.tv_shop_name_itemview_apnotopen);
        mBtnOpen = $(R.id.btn_open_itemview_apnotopen);

    }



    // EasyRecyclerView 中的  OnBindViewHolder 执行这个方法
    @Override
    public void setData(final AccountManagementBean.DataEntity entity) {
        if(entity != null){
            mTvShopName.setText(entity.getShop_name());


            mBtnOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((BaseActivity)mContext).startActivity(AccountPeriodActivity.class,
                            new Intent()
                            .putExtra(AP_STATUS, AP_Status_AlreadyOpened)
                            .putExtra(INTENT_SHOP_ID, entity.getShop_id()));
                }
            });
        }

    }
}
