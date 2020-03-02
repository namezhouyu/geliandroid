package com.geli.m.popup.listpopup;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geli.m.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */

public class PopupViewHolder extends BaseViewHolder<Object> {

    Context mContext;

    @BindView(R.id.tv_item_VHListPopup)
    TextView mTvItem;


    public PopupViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.vh_list_popup);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Object o) {
        super.setData(o);
        if(o instanceof String){
            mTvItem.setText((String)o);
        }
    }
}