package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.aftersolddetails_activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.AfterSoldDetailsBean;
import com.geli.m.coustomview.AfterSoldDetailsRound;
import com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.aftersolddetails_activity.AfterSoldDetailsActivity;
import com.geli.m.utils.DateFormatUtil;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2018/3/5.
 */

public class AfterSoldDetailsViewHolder extends BaseViewHolder<AfterSoldDetailsBean.DataEntity.Dispose_res> {
    final AfterSoldDetailsRound mRound;
    private final TextView mTv_mess;
    private final TextView mTv_datetime;
    private final View mV_Line;
    Context mContext;

    public AfterSoldDetailsViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_after_sold_details);
        mContext = context;
        mRound = $(R.id.ldr_itemview_after_sold_details_rount);
        mTv_datetime = $(R.id.tv_itemview_after_sold_details_datetime);
        mTv_mess = $(R.id.tv_itemview_after_sold_details_mess);
        mV_Line = $(R.id.v_line);
    }

    @Override
    public void setData(AfterSoldDetailsBean.DataEntity.Dispose_res data) {
        super.setData(data);
        int position = getAdapterPosition();
        int headerCount = ((AfterSoldDetailsActivity) mContext).mAdapter.getHeaderCount(); // 头数量
        int allCount = ((AfterSoldDetailsActivity) mContext).mAdapter.getItemCount(); // 包含头脚
        int count = ((AfterSoldDetailsActivity) mContext).mAdapter.getCount(); // 不包含头脚


        if(position == 0 + headerCount){                             /* 第一个 -- 因为有一个头布局*/
            setRed();
            if(count == 1) {                        // 只有一个
                mRound.setState(true, true, true);
                mV_Line.setVisibility(View.GONE);
            }else if (count > 1) {                  // 有多个
                mRound.setState(true, true, false);
                mV_Line.setVisibility(View.VISIBLE);
            }

        }else if(position == (count - 1) + headerCount){             /* 最后一个 */
            mRound.setState(false, false, true);
            setGray();
            mV_Line.setVisibility(View.GONE);

        } else {
            mRound.setState(false, false, false);
            setGray();
            mV_Line.setVisibility(View.VISIBLE);
        }


            mTv_mess.setText(data.getDispose_detail());
            mTv_datetime.setText(DateFormatUtil.transForDate(Integer.valueOf(data.getAdd_time()), "yyy-MM-dd HH:mm:ss"));

    }

    public void setRed() {
        mTv_datetime.setTextSize(14);
        mTv_mess.setTextSize(14);
        mTv_datetime.setTextColor(Utils.getColor(R.color.text_999999));
        mTv_mess.setTextColor(Utils.getColor(R.color.text_color));
    }

    public void setGray() {
        mTv_datetime.setTextSize(12);
        mTv_mess.setTextSize(12);
        mTv_datetime.setTextColor(Utils.getColor(R.color.text_999999));
        mTv_mess.setTextColor(Utils.getColor(R.color.text_color));
    }
}
