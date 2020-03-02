package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.logisticsdetails_activity;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.LogisticsDetailsBean;
import com.geli.m.coustomview.LogisticsDetailsRound;
import com.geli.m.utils.DateFormatUtil;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2018/3/5.
 */

public class LogisticsDetailsViewHolder extends BaseViewHolder<LogisticsDetailsBean.DataEntity.LogisticsEntity> {
    final LogisticsDetailsRound mRound;
    private final TextView mTv_mess;
    private final TextView mTv_time;
    private final TextView mTv_date;
    Context mContext;

    public LogisticsDetailsViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_logisticsdetails);
        mContext = context;
        mRound = $(R.id.ldr_itemview_logisticsdetails_rount);
        mTv_date = $(R.id.tv_itemview_logisticsdetails_date);//月日
        mTv_time = $(R.id.tv_itemview_logisticsdetails_time);//时间
        mTv_mess = $(R.id.tv_itemview_logisticsdetails_mess);
    }

    @Override
    public void setData(LogisticsDetailsBean.DataEntity.LogisticsEntity data) {
        super.setData(data);
        if (getAdapterPosition() == 0 && ((LogisticsDetailsActivity) mContext).mAdapter.getAllData().size() - 1 != 0) {
            mRound.setState(false, false, true);
            setGray();
        } else if (getAdapterPosition() == ((LogisticsDetailsActivity) mContext).mAdapter.getAllData().size() - 1) {
            mRound.setState(true, true, false);
            setRed();
        } else {
            mRound.setState(false, false, false);
            setGray();
        }
        mTv_mess.setText(data.getContent());
        mTv_date.setText(DateFormatUtil.transForDate(Integer.valueOf(data.getAdd_time()), "MM-dd"));
        mTv_time.setText(DateFormatUtil.transForDate(Integer.valueOf(data.getAdd_time()), "HH:mm"));
    }

    public void setRed() {
        mTv_date.setTextSize(14);
        mTv_time.setTextSize(14);
        mTv_mess.setTextSize(14);
        mTv_date.setTextColor(Utils.getColor(R.color.zhusediao));
        mTv_time.setTextColor(Utils.getColor(R.color.zhusediao));
        mTv_mess.setTextColor(Utils.getColor(R.color.zhusediao));
    }

    public void setGray() {
        mTv_date.setTextSize(12);
        mTv_time.setTextSize(12);
        mTv_mess.setTextSize(12);
        mTv_date.setTextColor(Utils.getColor(R.color.specifitext_color));
        mTv_time.setTextColor(Utils.getColor(R.color.specifitext_color));
        mTv_mess.setTextColor(Utils.getColor(R.color.specifitext_color));
    }
}
