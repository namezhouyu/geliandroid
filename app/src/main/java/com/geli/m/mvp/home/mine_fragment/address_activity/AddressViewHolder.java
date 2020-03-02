package com.geli.m.mvp.home.mine_fragment.address_activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.AddressListBean;
import com.geli.m.coustomview.SelectLayout;
import com.geli.m.mvp.home.mine_fragment.address_activity.addaddress_activity.AddEditAddressActivity;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import static com.geli.m.config.Constant.INTENT_BEAN;

/**
 * Created by Steam_l on 2018/1/4.
 */

public class AddressViewHolder extends BaseViewHolder<AddressListBean.DataEntity> {
    Context mContext;
    private final CheckBox mCb_select;
    private final TextView mTv_default;
    private final TextView mTv_add;
    private final TextView mTv_phone;
    private final TextView mTv_name;
    private final ImageView mIv_edit;

    public AddressViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_address);
        mContext = context;
        mCb_select = $(R.id.cb_itemview_address_select);
        mTv_name = $(R.id.tv_itemview_address_name);
        mTv_phone = $(R.id.tv_itemview_address_phone);
        mTv_add = $(R.id.tv_itemview_address_add);
        mTv_default = $(R.id.tv_itemview_address_isdefault);
        mIv_edit = $(R.id.iv_itemview_address_edit);
    }

    @Override
    public void setData(final AddressListBean.DataEntity data) {
        super.setData(data);

        setSelect(data);
        setDefaultAddr(data);                       // 设置默认地址标志


        /* 管理模式下 -- 编辑图片按钮 -- 点击事件 */
        if (((AddressActivity) mContext).getAddressMode() == AddressActivity.ADDRESSMODE_MANAGER) {
            mIv_edit.setVisibility(View.VISIBLE);
            mIv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AddEditAddressActivity.class);
                    intent.putExtra(INTENT_BEAN, data);
                    mContext.startActivity(intent);
                }
            });
        }

        mTv_name.setText(data.getConsignee());
        mTv_phone.setText(Utils.getString(R.string.phone, data.getMobile()));
        String address = Utils.getStringNoNull(data.getP_cn())
                + Utils.getStringNoNull(data.getC_cn())
                + Utils.getStringNoNull(data.getD_cn())
                + Utils.getStringNoNull(data.getE_cn())
                + Utils.getStringNoNull(data.getAddress());

        mTv_add.setText(Utils.getString(R.string.shipping_address, address));
    }

    /**
     * 设置默认地址标志
     * @param data
     */
    private void setDefaultAddr(AddressListBean.DataEntity data) {
        /* 默认地址 */
        if (data.getIs_default() == 1) {
            mTv_default.setVisibility(View.VISIBLE);
        } else {
            mTv_default.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 根据是否是在选择状态
     * @param data
     */
    private void setSelect(final AddressListBean.DataEntity data) {
        boolean isShowSelect = ((AddressActivity) mContext).getIsShowSelect();
        if (isShowSelect) {
            ((SelectLayout) itemView).open();
            mCb_select.setChecked(data.isCheck);
            mCb_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    data.isCheck = isChecked;
                }
            });
        } else {
            ((SelectLayout) itemView).close();
            data.isCheck = false;
            mCb_select.setChecked(data.isCheck);
        }
    }


}
