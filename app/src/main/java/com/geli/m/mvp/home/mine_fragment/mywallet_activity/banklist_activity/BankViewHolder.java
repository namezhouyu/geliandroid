package com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.BankListBean;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2018/1/18.
 */

public class BankViewHolder extends BaseViewHolder<BankListBean.DataEntity> {
    Context mContext;
    private final TextView mTv_banknumber;
    private final TextView mTv_banktype;
    private final TextView mTv_bankname;
    private final ImageView mIv_typeimg;
    private final ImageView mIv_img;

    public BankViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_bank);
        mContext = context;
        mIv_img = $(R.id.iv_itemview_banklist_img);
        mIv_typeimg = $(R.id.iv_itemview_banklist_typeimg);
        mTv_bankname = $(R.id.tv_itemview_banklist_bankname);
        mTv_banktype = $(R.id.tv_itemview_banklist_banktype);
        mTv_banknumber = $(R.id.tv_itemview_banklist_banknumber);
    }

    @Override
    public void setData(BankListBean.DataEntity data) {
        super.setData(data);
        mTv_bankname.setText(data.getBank_category_number());
        mTv_banktype.setText(data.getCard_type());
        String banknumber = data.getUnion_pay_number();
        banknumber = banknumber.substring(banknumber.length() - 4, banknumber.length());
        mTv_banknumber.setText(Utils.getString(R.string.bank_enctypt, banknumber));
        String bank_img = data.getBank_img();
        if (TextUtils.isEmpty(bank_img)) {
            bank_img = "";
        }
        GlideUtils.loadImg(mContext, bank_img, mIv_img);
    }
}
