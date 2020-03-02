package com.geli.m.drawer.menudrawer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.geli.m.R;
import com.geli.m.bean.RestaurantGoodsShopScreen;
import com.geli.m.mvp.home.other.accountperiod_activity.MyRadioGroup;
import com.geli.m.utils.ResourceUtil;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.generateViewId;

/**
 * <p>
 * 食品馆列表
 */

public class MenuSortVH extends BaseViewHolder<RestaurantGoodsShopScreen.DataBeanX> {

    Context mContext;

    /** 标题 */
    @BindView(R.id.tv_menu_MenuSortVH)
    TextView mTvTitle;
    /** 所有选中的值 */
    @BindView(R.id.rg_MenuSortVH)
    MyRadioGroup mRg;

    /** 第一个选择框 */
    RadioButton mFirstRadioButton;

    LayoutInflater mInflater;

    ViewGroup mParent;

    RestaurantGoodsShopScreen.DataBeanX mDataBeanX;

    FirstSortListener mListener;

    public MenuSortVH(ViewGroup parent, Context context, FirstSortListener listener) {
        super(parent, R.layout.vh_menu_sort);
        mContext = context;
        mParent = parent;
        ButterKnife.bind(this, itemView);

        mListener = listener;
        mInflater = LayoutInflater.from(context);
        initRg();
    }



    @Override
    public void setData(RestaurantGoodsShopScreen.DataBeanX data) {
        super.setData(data);
        mTvTitle.setText(data.getScreen_name());

        mDataBeanX = data;
        setRg(data);


        if(mFirstRadioButton != null){
            RestaurantGoodsShopScreen.DataBeanX.DataBean rbTag =
                    (RestaurantGoodsShopScreen.DataBeanX.DataBean) mFirstRadioButton.getTag();
            mRg.check(mFirstRadioButton.getId());
            if(mListener != null && rbTag != null){
                mListener.firstSortListener(rbTag);
            }
        }
    }


    private void initRg(){
        mRg.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                RadioButton rb = $(checkedId);
                if(rb != null){
                    RestaurantGoodsShopScreen.DataBeanX.DataBean rbTag =
                            (RestaurantGoodsShopScreen.DataBeanX.DataBean) rb.getTag();

                    if(mListener != null && rbTag != null){
                        mListener.firstSortListener(rbTag);
                    }
                }
            }
        });
    }


    /**
     * 添加 单选框组 的控件
     * @param data
     */
    public void setRg(RestaurantGoodsShopScreen.DataBeanX data) {

        mRg.removeAllViews();

        List<LinearLayout> layoutList = new ArrayList<>();
        if(data.getData() != null){                            // 账期天数

            setLayoutAndRb(data, layoutList);

            if(layoutList.size() > 0){
                for(LinearLayout layout : layoutList){
                    mRg.addView(layout);
                }
            }
        }
    }


    Map<Integer, RadioButton> mRadioButtonMap = new HashMap<>();
    /**
     * 每3个单选框为一行
     * @param data
     * @param layoutList
     * @return
     */
    private void setLayoutAndRb(RestaurantGoodsShopScreen.DataBeanX data,
                                List<LinearLayout> layoutList) {
        int index = 0;
        RadioButton rb = null;
        LinearLayout tempLayout = null;
        for(RestaurantGoodsShopScreen.DataBeanX.DataBean bean : data.getData()){
            if((index % 3) == 0) {
                tempLayout = new LinearLayout(mContext);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, Utils.dip2px(mContext, 5),
                        0, Utils.dip2px(mContext, 5));
                tempLayout.setLayoutParams(layoutParams);
                tempLayout.addView(rb = setRb(bean));
                if(index == 0){
                    mFirstRadioButton = rb;
                }
                layoutList.add(tempLayout);
            }else {
                tempLayout.addView(rb = setRb(bean));
            }

            if(rb != null) {
                mRadioButtonMap.put(
                        ((RestaurantGoodsShopScreen.DataBeanX.DataBean) rb.getTag()).getValue(),
                        rb);
            }
            index++;
        }
    }


    /**
     * 单选框的样式
     * @param bean
     * @return
     */
    @NonNull
    private RadioButton setRbTemp(RestaurantGoodsShopScreen.DataBeanX.DataBean bean) {

        RadioButton rb = (RadioButton) mInflater.inflate(R.layout.vh_menu_sort_child, mParent, false);
        rb.setTag(bean);
        rb.setText(bean.getName());
        return rb;
    }

    /**
     * 单选框的样式
     * @param bean
     * @return
     */
    @NonNull
    private RadioButton setRb(RestaurantGoodsShopScreen.DataBeanX.DataBean bean) {
        RadioButton rb = new RadioButton(mContext);
        rb.setId(generateViewId());
        rb.setTag(bean);
        rb.setText(bean.getName());
        rb.setTextAppearance(mContext, R.style.radiobutton_menu); // 设置样式

        // 将 mRg的框按某个比例来设计
        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                Utils.dip2px(mContext, 70),
                Utils.dip2px(mContext, 30)
        );
        layoutParams.setMargins(Utils.dip2px(mContext, 5),
                Utils.dip2px(mContext, 10),
                Utils.dip2px(mContext, 5),
                0);
        rb.setLayoutParams(layoutParams);

        rb.setTextSize(12);
        rb.setButtonDrawable(android.R.color.transparent);//隐藏单选圆形按钮
        rb.setGravity(Gravity.CENTER);
        rb.setPadding(Utils.dip2px(mContext, 2), 0,
                Utils.dip2px(mContext, 2), 0);
        rb.setBackgroundResource(ResourceUtil.getDrawableResIDByName(mContext,
                "selector_sort_checked_item"));

        return rb;
    }
}
