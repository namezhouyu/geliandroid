package com.geli.m.drawer.menudrawer;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.geli.m.R;
import com.geli.m.bean.RestaurantGoodsShopScreen;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * <p>
 * 食品馆列表
 */

public class MenuChildVH extends BaseViewHolder<RestaurantGoodsShopScreen.DataBeanX.DataBean> {

    Context mContext;

    @BindView(R.id.cb_item_VHMenuChild)
    CheckBox mCheckBox;


    public MenuChildVH(ViewGroup parent, Context context) {
        super(parent, R.layout.vh_menu_child);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void setData(final RestaurantGoodsShopScreen.DataBeanX.DataBean data) {
        super.setData(data);
        mCheckBox.setText(data.getName());
        mCheckBox.setSelected(data.isSelected());

        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                data.setSelected(isChecked);
                mCheckBox.setSelected(isChecked);
            }
        });
    }
}
