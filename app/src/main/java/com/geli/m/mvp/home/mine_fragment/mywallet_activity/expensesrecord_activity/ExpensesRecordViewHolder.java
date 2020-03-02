package com.geli.m.mvp.home.mine_fragment.mywallet_activity.expensesrecord_activity;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.ExpensesBean;
import com.geli.m.utils.DateFormatUtil;
import com.geli.m.utils.GlideUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2018/3/27.
 */

public class ExpensesRecordViewHolder extends BaseViewHolder<ExpensesBean.DataEntity.ConsumptionEntity> {
    Context mContext;
    private final TextView mTv_name;
    private final TextView mTv_time;
    private final TextView mTv_money;
    private final ImageView mIv_img;

    public ExpensesRecordViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_expensesrecord);
        mContext = context;
        mTv_name = $(R.id.tv_itemview_expenses_name);
        mTv_time = $(R.id.tv_itemview_expenses_time);
        mTv_money = $(R.id.tv_itemview_expenses_money);
        mIv_img = $(R.id.iv_itemview_expenses_img);
    }

    @Override
    public void setData(ExpensesBean.DataEntity.ConsumptionEntity data) {
        super.setData(data);
        mTv_name.setText(TextUtils.isEmpty(data.getShop_name()) ? "" : data.getShop_name());
        mTv_money.setText("-" + data.getTotal_amount());
        mTv_time.setText(DateFormatUtil.transForDate((int) data.getAdd_time(), "MM.dd HH:mm"));
        if (TextUtils.isEmpty(data.getShop_thumb())) {
            mIv_img.setBackgroundResource(R.drawable.img_loading);
        } else {
            GlideUtils.loadAvatar(mContext, data.getShop_thumb(), mIv_img);
        }
    }
}
