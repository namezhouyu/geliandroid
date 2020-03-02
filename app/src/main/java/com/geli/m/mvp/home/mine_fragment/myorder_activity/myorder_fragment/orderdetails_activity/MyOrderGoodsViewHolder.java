package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.orderdetails_activity;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.CartBean;
import com.geli.m.bean.OrderListBean;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.PriceUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import static com.geli.m.config.Constant.Goods_Type_Futures;
import static com.geli.m.config.Constant.Goods_Type_GroupBuy;

/**
 * Created by Steam_l on 2018/1/8.
 *
 * 订单对应的商品显示
 *
 */
public class MyOrderGoodsViewHolder extends BaseViewHolder<OrderListBean.DataEntity.GoodsListEntity> {

    Context mContext;

    /** 订单中某商品 -- 数量 */
    private final TextView mTv_number;
    /** 订单中某商品 -- 规格 */
    private final TextView mTv_specifi;
    /** 订单中某商品 -- 类型 -- 商品、现货、期货、团购 */
    private final TextView mTv_type;
    /** 订单中某商品 -- 名称 */
    private final TextView mTv_name;
    /** 订单中某商品 -- 图片 */
    private final ImageView mIv_img;
    /** 订单中某商品 -- 单价 */
    private final TextView mTv_price;

    /** 订单中某商品 -- 账期文本 */
    private final TextView mTv_sh;

    public MyOrderGoodsViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_submitorder_goods);
        mContext = context;
        mTv_name = $(R.id.tv_itemview_submitorder_goodsname);
        mTv_type = $(R.id.tv_itemview_submitorder_goodstype);
        mTv_specifi = $(R.id.tv_itemview_submitorder_goodsspecifi);
        mTv_number = $(R.id.tv_itemview_submitorder_number);
        mTv_price = $(R.id.tv_itemview_submitorder_price);
        mIv_img = $(R.id.iv_itemview_submitorder_img);
        mTv_sh = $(R.id.tv_itemview_submitorder_sh);
        mTv_type.setVisibility(View.VISIBLE);
    }

    @Override
    public void setData(OrderListBean.DataEntity.GoodsListEntity data) {
        super.setData(data);

        GlideUtils.loadImg(mContext, data.getGoods_thumb() == null ? "" : data.getGoods_thumb(), mIv_img);

        setSh(data);

        setGoodsType(data);                         // 设置商品类型
        setShopAndGoodsNumAndPrice(data);           // 设置"商店名称、订单这种商品数量、这种商品总价格"
        setSpecifi(data);                           // 设置规格
    }


    private void setSh(OrderListBean.DataEntity.GoodsListEntity data){
        if(data.getIs_sh() == 0){
            mTv_sh.setVisibility(View.GONE);
        }else if(data.getIs_sh() == 1){
            mTv_sh.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置 -- 订单中某商品 -- 类型 -- 商品、现货、期货、团购
     * @param data
     */
    private void setGoodsType(OrderListBean.DataEntity.GoodsListEntity data) {
        String type = "";
        if (data.getGoods_type() == Goods_Type_Futures) {                   // 期货
            type = Utils.getString(R.string.futures);
        } else if (data.getGoods_type() == Goods_Type_GroupBuy) {           // 团购
            type = Utils.getString(R.string.group_buy);
        }

        if (TextUtils.isEmpty(type)) {
            mTv_type.setVisibility(View.GONE);
        } else {
            mTv_type.setText(type);
        }
    }

    /**
     * 设置"商店名称、订单这种商品数量、这种商品总价格"
     * @param data
     */
    private void setShopAndGoodsNumAndPrice(OrderListBean.DataEntity.GoodsListEntity data) {
        mTv_name.setText(data.getGoods_name());
        mTv_number.setText(Utils.getString(R.string.buy_goods_number, data.getCart_number()));
        if (data.getGoods_type() == Goods_Type_Futures || data.getGoods_type() == Goods_Type_GroupBuy) {
            mTv_price.setVisibility(View.GONE);
        } else {
            mTv_price.setText(PriceUtils.getPrice(data.getGoods_price()));
        }
    }


    /**
     * 设置规格
     * @param data
     */
    private void setSpecifi(OrderListBean.DataEntity.GoodsListEntity data) {
        String specifi = "";
        if (data.getGoods_attr() != null) {
            for (CartBean.DataEntity.CartListEntity.SpecificationEntity specificationEntity : data.getGoods_attr()) {
                specifi += specificationEntity.getKey() + ":" + specificationEntity.getValue() + "\n";
            }
        }
        mTv_specifi.setText(specifi.trim());
    }
}
