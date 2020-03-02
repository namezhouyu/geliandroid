package com.geli.m.coustomview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.CartBean;
import com.geli.m.bean.SpecifiBean;
import com.geli.m.utils.LogUtils;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Steam_l on 2018/1/16.
 * 具体规格
 */
public class SpecificLayout extends LinearLayout{

    /** 某分类的属性的 -- 统称 */
    private TextView mTvName;
    /** 流式瀑布 */
    private FluidLayout mFlLayout;
    private SpecifiBean.DataEntity.SpecAttrEntity mCurrEntity;

    public SpecificLayout(Context context) {
        super(context);
    }

    public SpecificLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SpecificLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setFlIsRadio(boolean isRadio) {
        mFlLayout.isRadio = isRadio;
    }

    /**
     *
     * @param specAttrEntity        某个分类的属性 (不是所有属性)
     * @param sku_id                商品的某个型号 -- 一个型号(有多个属性标签)
     * @param goods_sku             所有型号的商品
     */
    public void setData(SpecifiBean.DataEntity.SpecAttrEntity specAttrEntity,
                        int sku_id,
                        List<SpecifiBean.DataEntity.GoodsSkuEntity> goods_sku) {

        String sku_attr = get_sku_attr(sku_id, goods_sku);                  // 获取这个型号的所有属性
        List<TextView> viewList = setSpecAttrItem(specAttrEntity, sku_attr);    // 为这个分类的属性设置item

        if (TextUtils.isEmpty(sku_attr)) {      // 没有默认的
            // 为null触发监听,设置数据
            onCheckBoxClickListener(viewList.get(0));
        }
    }

    /**
     * 为这个分类的属性设置item -- 里面的每个小属性
     *
     * @param specAttrEntity    某个分类的属性 (不是所有属性)
     * @param sku_attr          默认类型
     * @return
     */
    @NonNull
    private List<TextView> setSpecAttrItem(SpecifiBean.DataEntity.SpecAttrEntity specAttrEntity, String sku_attr) {
        mCurrEntity = specAttrEntity;
        mTvName.setText(mCurrEntity.getSpec_name());
        mTvName.setTag(mCurrEntity.getSpec_id());

        List<String> specificList = new ArrayList<>();
        List<SpecifiBean.DataEntity.SpecAttrEntity.ResEntity> res = mCurrEntity.getRes();
        for (int j = 0; j < res.size(); j++) {
            specificList.add(res.get(j).getAttr_name());
        }

        mFlLayout.setDrawableId(R.drawable.cb_popupbuy_selector);
        List<TextView> view = mFlLayout.getView(specificList);  // 根据传递的数据中
        for (int j = 0; j < view.size(); j++) {
            CheckBox checkBox = (CheckBox) view.get(j);
            checkBox.setTag(res.get(j).getAttr_id());
            /* 默认 */
            if (sku_attr.contains(mCurrEntity.getSpec_id() + ":" + res.get(j).getAttr_id())) {
                checkBox.setChecked(true);
            }
            mFlLayout.addView(checkBox);
        }
        return view;
    }

    /**
     * 获取这个型号的 -- 所有属性 -- "sku_attr": "{1:30272}" 或 "sku_attr": "{1:30272,2:30276}"
     * @param sku_id            商品的某个型号
     * @param goods_sku         所有型号
     * @return
     */
    private String get_sku_attr(int sku_id, List<SpecifiBean.DataEntity.GoodsSkuEntity> goods_sku) {
        String sku_attr = "";
        for (int i = 0; i < goods_sku.size(); i++) {
            SpecifiBean.DataEntity.GoodsSkuEntity goodsSkuEntity = goods_sku.get(i);
            // 设置默认第一个
            //  if (sku_id == -1) {
            //      sku_id = goodsSkuEntity.getSku_id();
            //   }
            if (sku_id == goodsSkuEntity.getSku_id()) {
                sku_attr = goodsSkuEntity.getSku_attr();
                break;
            }
        }
        return sku_attr;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTvName = (TextView) findViewById(R.id.tv_dialog_goodsdetails_addcart_name);
        mFlLayout = (FluidLayout) findViewById(R.id.fl_dialog_goodsdetails_addcart_taste_layout);
        mFlLayout.setCheckBoxClickListener(new FluidLayout.CheckBoxClickListener() {
            @Override
            public void onCheckBoxClick(View v) {
                onCheckBoxClickListener(v);
            }
        });
    }

    /**
     * 单选框选择事件  --  每选择一次就回调一次
     * @param v
     */
    private void onCheckBoxClickListener(View v){
        if (mChangeListener != null) {
            boolean isCheck = false;
            if (v instanceof CheckBox) {
                isCheck = ((CheckBox) v).isChecked();
            }
            if (isCheck) {
                mCurrSpecificId = (int) v.getTag();
                mChangeListener.change(getResId());
            }
        }
    }

    /** 小属性id */
    private int mCurrSpecificId = -1;

    /**
     * id拼接,用于判断当前是那个sku_id
     *
     * @return  如：1:30272
     */
    public String getResId() {
        if (mCurrSpecificId == -1) {
            return "";
        }
        return mTvName.getTag() + ":" + mCurrSpecificId;
    }

    /**
     * 获取属性分类id
     * @return
     */
    public String getSpecId() {
        return mCurrEntity.getSpec_id() + "";
    }


    /*  获取属性 -- 选择的 */
    /**
     * 发送数据用到的json
     *
     * @return
     */
    public String getJson() {
        CartBean.DataEntity.CartListEntity.SpecificationEntity entity =
                new CartBean.DataEntity.CartListEntity.SpecificationEntity();

        entity.setKey(getName());
        entity.setValue(getValue());
        return new Gson().toJson(entity);
    }


    /**
     * 分类属性的名称
     *
     * @return
     */
    public String getName() {
        return mTvName.getText().toString().trim();
    }

    /**
     * 选中的cb string
     *
     * @return
     */
    public String getValue() {
        List<CheckBox> allView = mFlLayout.getAllView();
        String value = "";
        for (CheckBox checkBox : allView) {
            if (checkBox.isChecked()) {
                value = checkBox.getText().toString();
            }
        }
        return value;
    }


    /**
     * 更新 -- 选中状态
     *
     *     SkuMap -- key:1 -- value:30272,30274
     *     SkuMap -- key:2 -- value:30273,30276
     *
     * @param skuMap
     */
    public void updateSpeState(Map<String, String> skuMap) {
        Set<String> keySet = skuMap.keySet();
        if (skuMap.size() != 0) {
            if (keySet.contains(getSpecId())) {     // 所有大分类 包含 当前这个分类

                String values = skuMap.get(getSpecId());
                LogUtils.i("==1、values：" + values);
                LogUtils.i("==2、getSpecId()：" + getSpecId());

                for (int i = 0; i < mFlLayout.getChildCount(); i++) {
                    CheckBox checkBox = (CheckBox) mFlLayout.getChildAt(i);
                    String tag = checkBox.getTag() + "";
                    /* 不等于当前选中的tag -- 点击了"某个按钮" 设置"其他按钮" */
                    if (mCurrSpecificId != Integer.valueOf(tag)) {
                        LogUtils.i("==3、mCurrSpecificId：" + mCurrSpecificId);
                        LogUtils.i("==4、values：" + values);
                        LogUtils.i("==5、tag：" + tag);
                        if (!values.contains(tag)) {            /* 大分类 没有包含了这个属性 */
                            checkBox.setChecked(false);
                            checkBox.setEnabled(false);
                        } else {                                /* 单选情况下,避免选中后可以取消 */
                            checkBox.setEnabled(true);
                        }
                    }else {
                        LogUtils.i("==6、mCurrSpecificId：" + mCurrSpecificId);
                        LogUtils.i("==7、tag：" + tag);
                    }
                }
            }
        }

//                ==1、values：30272,30274
//                ==2、getSpecId()：1
//                ==6、mCurrSpecificId：30272
//                ==7、tag：30272

//                ==3、mCurrSpecificId：30272
//                ==4、values：30272,30274
//                ==5、tag：30274

//                ==1、values：30273,30276
//                ==2、getSpecId()：2
    }


    OnChangeListener mChangeListener;

    public void setOnChangeListener(OnChangeListener listener) {
        mChangeListener = listener;
    }

    public interface OnChangeListener {
        void change(String res);
    }


}
