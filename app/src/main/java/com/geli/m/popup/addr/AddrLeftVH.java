package com.geli.m.popup.addr;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geli.m.R;
import com.geli.m.bean.RestaurantAddrArrangeBean;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author:  shen
 * date:    2019/7/23
 */
public class AddrLeftVH extends BaseViewHolder<RestaurantAddrArrangeBean.province> {

    Context mContext;
    @BindView(R.id.tv_VHAddrLeft)
    TextView mTvAddrLeft;

    public AddrLeftVH(ViewGroup parent, Context context) {
        super(parent, R.layout.vh_addr_left);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(RestaurantAddrArrangeBean.province data) {
        super.setData(data);
        mTvAddrLeft.setText(data.getPro().getArea_name());

        if(data.getPro().isSelect()){
            mTvAddrLeft.setTextColor(Utils.getColor(R.color.zhusediao));
            mTvAddrLeft.setBackgroundResource(R.drawable.shape_line_bottom2_red);
        }else {
            mTvAddrLeft.setTextColor(Utils.getColor(R.color.text_color));
            mTvAddrLeft.setBackgroundResource(R.drawable.shape_line_left14_bottom1);
        }
    }
}
