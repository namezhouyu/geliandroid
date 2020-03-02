package com.geli.m.dialog.addcart;

import com.geli.m.bean.SpecifiBean;
import java.util.List;

/**
 * author:  shen
 * date:    2019/1/21
 */
public class AddCartPriceUtils {
    /**
     * 根据数量 -- 设置价格 -- 有些商品达到一定的量是有优惠的
     *
     *       mTieredPri": [
     *              {"key": 10,"value": 90},
     *              {"key": 5,"value": 95},
     *              {"key": 1,"value": 100}
     *          ]
     *
     * @param currTieredPri         当前的优惠
     * @param number                加入购物车的数量
     * @return                      返回的值 -- 就是价钱
     */
    public static String setCurrPrice(List<SpecifiBean.DataEntity.GoodsSkuEntity.TieredPri> currTieredPri,
                                      String number) {
        String price = "";
        if (currTieredPri != null && currTieredPri.size() > 0) {        //  是否有优惠
            for (SpecifiBean.DataEntity.GoodsSkuEntity.TieredPri tieredPri : currTieredPri) {
                if (Integer.valueOf(number) >= tieredPri.getKey()) {
                    return tieredPri.getValue() + "";
                }
            }
        }
        return price;
    }

}
