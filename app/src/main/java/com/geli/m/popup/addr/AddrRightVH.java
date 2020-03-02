package com.geli.m.popup.addr;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geli.m.R;
import com.geli.m.bean.RestaurantAddrBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author:  shen
 * date:    2019/7/23
 */
public class AddrRightVH extends BaseViewHolder<RestaurantAddrBean.DataBean> {

    Context mContext;
    @BindView(R.id.tv_VHAddrRight)
    TextView mTvAddrRight;

    public AddrRightVH(ViewGroup parent, Context context) {
        super(parent, R.layout.vh_addr_right);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(RestaurantAddrBean.DataBean data) {
        super.setData(data);
        mTvAddrRight.setText(data.getArea_name());
    }
}
