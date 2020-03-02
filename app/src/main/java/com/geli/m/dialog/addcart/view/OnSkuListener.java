package com.geli.m.dialog.addcart.view;

import com.geli.m.bean.SkuAttrBean;
import com.geli.m.bean.SpecifiBean;

/**
 * Created by wuhenzhizao on 2017/8/30.
 */
public interface OnSkuListener {
    /**
     * 属性取消选中
     *
     * @param unselectedAttribute
     */
    void onUnselected(SkuAttrBean unselectedAttribute);

    /**
     * 属性选中
     *
     * @param selectAttribute
     */
    void onSelect(SkuAttrBean selectAttribute);

    /**
     * sku选中
     *
     * @param sku
     */
    void onSkuSelected(SpecifiBean.DataEntity.GoodsSkuEntity sku);
}