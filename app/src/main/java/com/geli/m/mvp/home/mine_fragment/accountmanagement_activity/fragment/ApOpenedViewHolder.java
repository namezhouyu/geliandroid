package com.geli.m.mvp.home.mine_fragment.accountmanagement_activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.AccountManagementBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.mine_fragment.accountorder_activity.AccountOrderActivity;
import com.geli.m.mvp.home.other.accountperiod_opened_activity.AccountPeriodOpenedActivity;
import com.geli.m.utils.TimeUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import java.util.Date;

import static com.geli.m.config.Constant.AP_STATUS;
import static com.geli.m.config.Constant.AP_Status_AllowablePay;
import static com.geli.m.config.Constant.INTENT_AP_CLOSING_TIME;
import static com.geli.m.config.Constant.INTENT_SHOP_ID;
import static com.geli.m.config.Constant.INTENT_SHOP_NAME;

/**
 * author:  shen
 * date:    2018/11/13
 */
public class ApOpenedViewHolder extends BaseViewHolder<AccountManagementBean.DataEntity> {

    Context mContext;

    /** 商店名字 */
    TextView mTvShopName;
    /** 结账日期 */
    TextView mTvTime;
    /** 查看我的账期 */
    TextView mTvCheck;
    /** 全部支付 */
    Button mBtnPay;
    /** 剩余时间 */
    TextView mTvClosingTime;
    /** 无订单提示 */
    TextView mTvNoOrder;


    public ApOpenedViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_ap_opened);

        mContext = context;

        mTvShopName = $(R.id.tv_shop_name_itemview_am);
        mTvTime = $(R.id.tv_start_time_itemview_am);
        mTvCheck = $(R.id.tv_check_itemview_am);
        mBtnPay = $(R.id.btn_pay_itemview_am);
        mTvClosingTime = $(R.id.tv_closing_time_itemview_am);
        mTvNoOrder = $(R.id.tv_no_order_itemview_am);

    }



    // EasyRecyclerView 中的  OnBindViewHolder 执行这个方法
    @Override
    public void setData(final AccountManagementBean.DataEntity entity) {
        if(entity != null){
            mTvShopName.setText(entity.getShop_name());

            if(entity.getStart_time() != 0) {
                String closeTime = TimeUtils.transForDate(entity.getClosing_time(),
                        Utils.getString(R.string.date_of_checkout_day_f));
                mTvTime.setText(closeTime);
            }else {
                mTvTime.setText(Utils.getString(R.string.date_of_checkout_day));
            }

            if(entity.getClosing_time() != 0) {
                mTvClosingTime.setText(getRemainingTime(entity));

                if(entity.getSh_status() == Constant.AP_UseStatus_NotUsed){     /* 账期已归还(未使用账期) */
                    mBtnPay.setVisibility(View.GONE);
                }else {
                    mBtnPay.setVisibility(View.VISIBLE);
                }
                mTvClosingTime.setVisibility(View.VISIBLE);
                mTvNoOrder.setVisibility(View.GONE);
            }else {
                mBtnPay.setVisibility(View.GONE);
                mTvClosingTime.setVisibility(View.GONE);
                mTvNoOrder.setVisibility(View.VISIBLE);
            }

            mTvCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((BaseActivity) mContext).startActivity(AccountPeriodOpenedActivity.class, new Intent()
                            .putExtra(AP_STATUS, AP_Status_AllowablePay)
                            .putExtra(INTENT_SHOP_ID, entity.getShop_id()));
                }
            });

            mBtnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((BaseActivity) mContext).startActivity(AccountOrderActivity.class,
                            new Intent().putExtra(INTENT_SHOP_ID, entity.getShop_id())
                                        .putExtra(INTENT_SHOP_NAME, entity.getShop_name())
                                        .putExtra(INTENT_AP_CLOSING_TIME, entity.getClosing_time()));

                }
            });
        }

    }

    /**
     * 获取剩余时间
     * @param entity
     * @return
     */
    private String getRemainingTime(AccountManagementBean.DataEntity entity) {
        String message = "";
        long currentTime = new Date().getTime();
        long time = entity.getClosing_time() - (currentTime/1000);
        if(time > 0){
            long day = time /(60 * 60 * 24);
            long h = (time / 3600) % 24;
            message = Utils.getString(R.string.remaining_day, day, h);
        }else {                         // 逾期
            message = Utils.getString(R.string.the_account_is_overdue);
        }
        return message;
    }
}
