package com.geli.m.dialog.logistic;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.geli.m.R;
import com.geli.m.bean.ShopLogisticBean;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * author:  shen
 * date:    2019/3/23
 */
public class ShopLogisticVH extends BaseViewHolder<ShopLogisticBean.DataBean> {

    Context mContext;
    @BindView(R.id.tv_logisticName_ShopLogisticVH)
    TextView mTvLogisticName;
    @BindView(R.id.tv_logisticInfo_ShopLogisticVH)
    TextView mTvLogisticInfo;
    @BindView(R.id.tv_userNameAndPhone_ShopLogisticVH)
    TextView mTvUserNameAndPhone;
    @BindView(R.id.btn_call_ShopLogisticVH)
    Button mBtnCall;


    public ShopLogisticVH(ViewGroup parent, Context context) {
        super(parent, R.layout.vh_shop_logistic);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void setData(final ShopLogisticBean.DataBean data) {
        super.setData(data);
        /**
         * log_id : 4
         * log_name : 测试物流商2号
         * linkman : 小明
         * linkphone : 13547841025
         * remark : 每天2：00、14：00出发
         * create_time : 2019-03-06 17:17:54
         * is_del : 0
         * status : 1
         */
        mTvLogisticName.setText(data.getLog_name());
        mTvLogisticInfo.setText(data.getRemark());
        mTvUserNameAndPhone.setText(data.getLinkman() + " " + data.getLinkphone());

        mBtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.callPhone(mContext, data.getLinkphone());
            }
        });

    }
}
