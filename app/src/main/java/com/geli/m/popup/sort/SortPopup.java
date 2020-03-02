package com.geli.m.popup.sort;

import android.app.Activity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.geli.m.R;
import com.geli.m.bean.RestaurantSortBean;
import com.geli.m.dialog.addcart.widget.FlowLayout;
import com.geli.m.popup.BasePopupWindow;
import com.geli.m.utils.ScreenUtils;
import com.geli.m.utils.Utils;
import com.geli.m.utils.ViewUtils;

import java.util.List;

import static android.widget.ListPopupWindow.MATCH_PARENT;

/**
 * author:  shen
 * date:    2019/7/23
 */
public abstract class SortPopup extends BasePopupWindow {

    FlowLayout mFlowLayout;

    protected abstract void selectItemText(PopupWindow popupWindow, RestaurantSortBean.DataBean sortBean);

    /**
     * @param a
     * @param bgColor   0~1
     */
    public SortPopup(Activity a, float bgColor) {
        super(a, R.layout.sort_popup, MATCH_PARENT, MATCH_PARENT, bgColor);
    }

    @Override
    protected void initView() {
        View view = getContentView();
        mFlowLayout = view.findViewById(R.id.fl_SortDialog);

        view.findViewById(R.id.rLayout_root_SortDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPopupWindow().dismiss();
            }
        });

    }

    @Override
    protected void initEvent() {
        initFl();
        getPopupWindow().setFocusable(false);
    }

    public void setData(List<RestaurantSortBean.DataBean> data) {
        buildItem(data);
    }


    private void initFl(){
        /* 存放所有属性的"流式列表" */
        mFlowLayout.setMinimumHeight(ScreenUtils.dp2PxInt(mActivity, 25));
        mFlowLayout.setChildSpacing(ScreenUtils.dp2PxInt(mActivity, 15));
        mFlowLayout.setRowSpacing(ScreenUtils.dp2PxInt(mActivity, 15));
    }


    /**
     * 根据传递过来的 "属性列表"， 为每个项生成对应的"控件"再添加到"流式列表"中
     * @param data
     */
    public void buildItem(List<RestaurantSortBean.DataBean> data) {
        if(data != null){
            mFlowLayout.removeAllViewsInLayout();


            for (int i = 0; i < data.size(); i++) {
                TextView tv = new TextView(mActivity);
                tv.setTag(data.get(i));
                tv.setText(data.get(i).getType_name());
                tv.setId(ViewUtils.generateViewId());
                tv.setLayoutParams(new FlowLayout.LayoutParams(
                        FlowLayout.LayoutParams.WRAP_CONTENT,
                        FlowLayout.LayoutParams.WRAP_CONTENT));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                tv.setTextColor(Utils.getColor(R.color.text_e75d5d));
                tv.setGravity(Gravity.CENTER);
                tv.setBackgroundResource(R.drawable.shape_bg_f8f8f8_r5);
                int p = ScreenUtils.dp2PxInt(mActivity, 10);
                tv.setPadding(p, p, p, p);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectItemText(getPopupWindow(), (RestaurantSortBean.DataBean)v.getTag());
                    }
                });
                mFlowLayout.addView(tv);
            }
        }
    }
}
