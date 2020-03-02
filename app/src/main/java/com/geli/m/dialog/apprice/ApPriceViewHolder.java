package com.geli.m.dialog.apprice;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.ShPriceBean;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2018/3/27.
 * <p>
 * 消费记录
 */

public class ApPriceViewHolder extends BaseViewHolder<ShPriceBean.DataBean.PriceBean> {

    Context mContext;

    /** 天数 */
    TextView mTvDay;
    /** 价格 */
    TextView mTvPrice;
    /** 是不是默认选中的账期 */
    TextView mTvDefaultAccounts;


    public ApPriceViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.vh_ap_price);
        mContext = context;

        mTvDay = $(R.id.tv_day_vhapprice);
        mTvPrice = $(R.id.tv_price_vhapprice);
        mTvDefaultAccounts = $(R.id.tv_default_accounts_vhapprice);
    }

    @Override
    public void setData(ShPriceBean.DataBean.PriceBean data) {
        super.setData(data);


        if(data != null){
            mTvDay.setText(Utils.getString(R.string.n_days_to_settle_accounts, data.getDay()));
            mTvPrice.setText(Utils.getString(R.string.money_1,
                    Utils.getFormatDoubleTwoDecimalPlaces(data.getPrice(), 2)));

            if(data.isDefault()){
                mTvDefaultAccounts.setVisibility(View.VISIBLE);
            }else {
                mTvDefaultAccounts.setVisibility(View.GONE);
            }
        }
    }
}
