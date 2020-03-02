package com.geli.m.viewholder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geli.m.R;
import com.geli.m.bean.MessBean;
import com.geli.m.utils.DateFormatUtil;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2018/3/28.
 */

public class MessViewHolder extends BaseViewHolder<MessBean.DataEntity> {
    Context mContext;
    private final ImageView mIv_img;
    private final TextView mTv_time;
    private final TextView mTv_ordernumber;
    private final TextView mTv_goods;
    private final TextView mTv_activitytime;
    private final TextView mTv_content;
    private final TextView mTv_title;

    public MessViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_notify_logistics);
        mContext = context;
        mTv_title = $(R.id.tv_itemview_notify_logistics_state);
        mTv_content = $(R.id.tv_itemview_notify_logistics_mess);
        mTv_activitytime = $(R.id.tv_itemview_notify_logistics_activitytime);
        mTv_goods = $(R.id.tv_itemview_notify_logistics_goods);
        mTv_ordernumber = $(R.id.tv_itemview_notify_logistics_ordernumber);
        mTv_time = $(R.id.tv_itemview_notify_logistics_time);
        mIv_img = $(R.id.iv_itemview_notify_logistics_img);
    }

    // 1系统消息 2通知中心 3物流消息 4商品详情 5商家首页 6 商家优惠劵  8支付提醒 9活动
    @Override
    public void setData(MessBean.DataEntity data) {
        super.setData(data);
        if (TextUtils.isEmpty(data.getPush_thumb())) {
            mIv_img.setImageResource(R.drawable.default_logo);
        } else {
            GlideUtils.loadImg(mContext, data.getPush_thumb(), mIv_img);
        }
        mTv_activitytime.setVisibility(View.INVISIBLE);
        mTv_goods.setVisibility(View.INVISIBLE);
        mTv_title.setText(data.getPush_title());
        mTv_content.setText(data.getDigest());
        mTv_time.setText(DateFormatUtil.transForBrowseDate((long) data.getCreate_time()));
        if (data.getCat_id() == 1) {
            mTv_ordernumber.setText(Utils.getString(R.string.message_formsystem, Utils.getString(R.string.system)));
        } else if (data.getCat_id() == 3) {
            mTv_ordernumber.setText(Utils.getString(R.string.order_number, data.getOrder_sn()));
            mTv_goods.setVisibility(View.VISIBLE);
            mTv_goods.setText(data.getGoods_name());
        } else if (data.getCat_id() == 4) {
            mTv_ordernumber.setText(Utils.getString(R.string.message_formsystem, Utils.getString(R.string.system)));
        } else if (data.getCat_id() == 5) {
            mTv_ordernumber.setText(Utils.getString(R.string.message_formsystem, data.getShop_name()));
        } else if (data.getCat_id() == 6) {
            mTv_ordernumber.setText(Utils.getString(R.string.message_formsystem, Utils.getString(R.string.system)));
            mTv_activitytime.setVisibility(View.VISIBLE);
            String createTime = DateFormatUtil.transForBrowseDate((long) data.getCreate_time());
            String endTime = DateFormatUtil.transForBrowseDate((long) data.getEnd_time());
            mTv_activitytime.setText(Utils.getString(R.string.activity_time, createTime, endTime));
        } else if (data.getCat_id() == 8) {
            mTv_ordernumber.setText(Utils.getString(R.string.message_formsystem, Utils.getString(R.string.system)));
        } else if (data.getCat_id() == 9) {
            mTv_ordernumber.setText(Utils.getString(R.string.message_formsystem, Utils.getString(R.string.system)));
        }
    }
}
