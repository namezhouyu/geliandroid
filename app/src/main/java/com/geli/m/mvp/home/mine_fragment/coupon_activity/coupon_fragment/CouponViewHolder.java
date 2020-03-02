package com.geli.m.mvp.home.mine_fragment.coupon_activity.coupon_fragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.CouponBean;
import com.geli.m.mvp.home.mine_fragment.coupon_activity.coupon_fragment.CouponFragment;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import static com.geli.m.config.Constant.VIEWTYPE_CANUSE;
import static com.geli.m.config.Constant.VIEWTYPE_EXPIRED;
import static com.geli.m.config.Constant.VIEWTYPE_NOUSE;
import static com.geli.m.config.Constant.VIEWTYPE_SHOPALL;
import static com.geli.m.config.Constant.VIEWTYPE_USEHISTORY;


/**
 * Created by Steam_l on 2018/1/11.
 */

public class CouponViewHolder extends BaseViewHolder<CouponBean> {

    Context mContext;
    CouponFragment mFragment;

    private final Button mBt_receive;
    private final ImageView mIv_yiguoqi;
    private final ImageView mIv_yilingqu;
    private final ImageView mIv_yishiyong;
    private final TextView mTv_time;
    private final TextView mTv_name;
    private final TextView mTv_specifi;
    private final TextView mTv_price;
    private final RelativeLayout mRl_left;

    public CouponViewHolder(ViewGroup parent, Context context, CouponFragment fragment) {
        super(parent, R.layout.itemview_coupon);
        mContext = context;
        mFragment = fragment;
        mRl_left = $(R.id.rl_itemview_coupon_leftlayout);
        mTv_price = $(R.id.tv_itemview_coupon_price);
        mTv_specifi = $(R.id.tv_itemview_coupon_specifi);
        mTv_name = $(R.id.tv_itemview_coupon_name);
        mTv_time = $(R.id.tv_itemview_coupon_time);
        mIv_yilingqu = $(R.id.civ_itemview_coupon_yilingqu);
        mIv_yiguoqi = $(R.id.civ_itemview_coupon_yiguoqi);
        mIv_yishiyong = $(R.id.civ_itemview_coupon_yishiyong);
        mBt_receive = $(R.id.bt_itemview_coupon_receive);
    }

    @Override
    public void setData(final CouponBean data) {
        super.setData(data);
        mTv_name.setText(data.getName());
        mTv_price.setText(Utils.getString(R.string.overseas_list_money, data.getMoney()));
        mTv_specifi.setText(Utils.getString(R.string.coupon_user_specifi, data.getCondition()));
        mTv_time.setText(Utils.getString(R.string.coupon_time, data.getUse_start_time(), data.getUse_end_time()));

        switch (mFragment.mCurrViewType){
            case VIEWTYPE_SHOPALL:                  /* 在商家列表 */
                // 显示或隐藏"领取按钮"
                mBt_receive.setVisibility(data.getIs_use() == 0 ? View.VISIBLE : View.GONE);
                // 显示或隐藏"领取戳"
                mIv_yilingqu.setVisibility(data.getIs_use() == 0 ? View.GONE : View.VISIBLE);
                mBt_receive.setText(Utils.getString(R.string.receive));
                break;

            case VIEWTYPE_NOUSE:                    /* 管理页面-未使用 */
                mIv_yilingqu.setVisibility(View.GONE);
                mIv_yiguoqi.setVisibility(View.GONE);
                mIv_yishiyong.setVisibility(View.GONE);
                mBt_receive.setVisibility(View.GONE);
                break;

            case VIEWTYPE_USEHISTORY:               /* 使用历史 -- 使用记录  */
                mRl_left.setBackgroundResource(R.drawable.img_youhuiquanzuo);
                mTv_specifi.setTextColor(Utils.getColor(R.color.specifitext_color));
                mTv_price.setTextColor(Utils.getColor(R.color.specifitext_color));
                mBt_receive.setVisibility(View.GONE);
                mIv_yilingqu.setVisibility(View.GONE);
                mIv_yiguoqi.setVisibility(View.GONE);
                mIv_yishiyong.setVisibility(View.VISIBLE);
                break;

            case VIEWTYPE_EXPIRED:                  /* 过期 */
                mTv_specifi.setTextColor(Utils.getColor(R.color.specifitext_color));
                mTv_price.setTextColor(Utils.getColor(R.color.specifitext_color));
                mRl_left.setBackgroundResource(R.drawable.img_youhuiquanzuo);
                mIv_yiguoqi.setVisibility(View.VISIBLE);
                mIv_yilingqu.setVisibility(View.GONE);
                mIv_yishiyong.setVisibility(View.GONE);
                mBt_receive.setVisibility(View.GONE);
                break;

            case VIEWTYPE_CANUSE:                   /* 订单可用 */
                mIv_yilingqu.setVisibility(View.INVISIBLE);
                mIv_yiguoqi.setVisibility(View.INVISIBLE);
                mIv_yishiyong.setVisibility(View.INVISIBLE);
                mBt_receive.setVisibility(View.INVISIBLE);
                break;
        }

        // 领取按钮点击监听事件
        mBt_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragment.mCurrViewType == VIEWTYPE_SHOPALL) {
                    //领取
                    mFragment.collectCoupons(data.getCp_id());
                } else if (mFragment.mCurrViewType == VIEWTYPE_NOUSE) {
                    //使用
                }
            }
        });
    }
}
