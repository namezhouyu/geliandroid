package com.geli.m.mvp.home.mine_fragment.mywallet_activity.expensesrecord_activity;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import com.geli.m.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2018/3/27.
 */

public class ExpensesTimeViewHolder extends BaseViewHolder<String> {
    Context mContext;

    public ExpensesTimeViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_expensestime);
        mContext = context;
    }

    @Override
    public void setData(String data) {
        super.setData(data);
        ((TextView) itemView).setText(data);
    }
}
