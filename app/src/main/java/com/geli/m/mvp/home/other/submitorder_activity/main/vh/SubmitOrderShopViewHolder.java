package com.geli.m.mvp.home.other.submitorder_activity.main.vh;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.SubmitOrderBean;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.mine_fragment.coupon_activity.CouponManagerActivity;
import com.geli.m.mvp.home.other.submitorder_activity.main.SubmitOrderActivity;
import com.geli.m.utils.PriceUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.List;

import static com.geli.m.config.Constant.INTENT_GOODS_ID;
import static com.geli.m.config.Constant.INTENT_MODE;
import static com.geli.m.config.Constant.INTENT_SHOP_ID;

/**
 * Created by Steam_l on 2018/1/22.
 *
 * 提交订单中 -- 购买的商品对应的商店 -- 项
 */
public class SubmitOrderShopViewHolder extends BaseViewHolder<SubmitOrderBean.DataEntity.ShopListEntity> {

    Context mContext;

    /** 买家留言 */
    private final EditText mEt_message;
    /** 这个"商品"购买了什么"商品" */
    private final EasyRecyclerView mErv_list;
    /** 总价 */
    private final TextView mTv_totalprice;
    /** 商品总数 -- 共xxx商品 */
    private final TextView mTv_totalnumber;
    /** 商店名字 */
    private final TextView mTv_shopname;
    /** 优惠券 -- 共xxx张优惠券 */
    private final TextView mTv_conpon;

    SubmitOrderStateInterFace mFace;

    public SubmitOrderShopViewHolder(ViewGroup parent, Context context, SubmitOrderStateInterFace face) {
        super(parent, R.layout.itemview_submitorder_shop);

        mContext = context;
        mFace = face;

        mTv_shopname = $(R.id.tv_itemview_submitorder_shopname);
        mTv_totalnumber = $(R.id.tv_itemview_submitorder_totalnumber);
        mTv_totalprice = $(R.id.tv_itemview_submitorder_totalprice);
        mErv_list = $(R.id.erv_itemview_submitorder_goodslist);
        mEt_message = $(R.id.et_itemview_submitorder_message);
        mTv_conpon = $(R.id.tv_itemview_submitorder_canuseconpon);
    }



    @Override
    public void setData(final SubmitOrderBean.DataEntity.ShopListEntity data) {
        super.setData(data);

        mTv_shopname.setText(data.getShop_name());
        setTv_totalprice(data);
        setTv_totalnumber(data);
        setErv_list(data);
        setEt_message(data);        // 输入框(买家留言) -- 监听
        setTv_conpon(data);         // 优惠券
    }

    /**
     * ((SubmitOrderActivity) mContext).mCurrType
     * 设置"总金额"
     * @param data
     */
    private void setTv_totalprice(SubmitOrderBean.DataEntity.ShopListEntity data) {
        if(mFace != null) {
            if (mFace.getCurrOrderType() == SubmitOrderActivity.TYPE_NORMAL) {    /* 普通,现货 */
                mTv_totalprice.setText(PriceUtils.getPrice(data.getShop_sum() + ""));
            } else {                                                                                /* 海外,团购 */
                if (data.getGoods_list().size() > 0) {                  // 这个商店"购买了多个商品"
                    mTv_totalprice.setText(Utils.getString(R.string.order_price,
                            Utils.getString(R.string.overseas_list_money,
                                    Utils.getFormatDoubleTwoDecimalPlaces(
                                            Utils.mul(Double.valueOf(data.getGoods_list().get(0).getCart_number()),
                                                    Double.valueOf(data.getGoods_list().get(0).getCart_price())), 2))));
                }
            }
        }
    }

    /**
     * 所有商品数量
     * @param data
     */
    private void setTv_totalnumber(SubmitOrderBean.DataEntity.ShopListEntity data) {
        int goodsSize = 0;
        for (SubmitOrderBean.DataEntity.ShopListEntity.GoodsListEntity entity : data.getGoods_list()) {
            goodsSize += entity.getCart_number();
        }
        mTv_totalnumber.setText(Utils.getString(R.string.total_goods_number
                , goodsSize
                , data.getGoods_list().get(0).getGoods_unit()));
    }



    /**
     * 设置"商店列表" -- 每个商品"都有个--商品列表"
     * @param data
     */
    private void setErv_list(final SubmitOrderBean.DataEntity.ShopListEntity data) {
        mErv_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        List<SubmitOrderBean.DataEntity.ShopListEntity.GoodsListEntity> cart_list = data.getGoods_list();
        mErv_list.setAdapterWithProgress(
                new RecyclerArrayAdapter<SubmitOrderBean.DataEntity.ShopListEntity.GoodsListEntity>(mContext, cart_list) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new SubmitOrderGoodsViewHolder(parent, mContext, data.getIs_sh());
            }
        });
    }



    /**
     * 输入框(买家留言) -- 监听
     * @param data
     */
    private void setEt_message(final SubmitOrderBean.DataEntity.ShopListEntity data) {
        mEt_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                data.message = s.toString().trim();
            }
        });
    }


    /**
     * 将"商品"，按照 "商品id, 购买数量" 再以 ";" 隔开的方式 -- 构建字符串
     * @param data
     * @return
     */
    public String getGoodsId(SubmitOrderBean.DataEntity.ShopListEntity data) {
        //｛3470,1;6,1 （商品id，购买数量；商品id，购买数量）｝单个｛3470，1｝
        String goods_id = "";
        for (SubmitOrderBean.DataEntity.ShopListEntity.GoodsListEntity entity : data.getGoods_list()) {
            goods_id += entity.getGoods_id() + "," + entity.getCart_number() + ";";
        }
        goods_id = goods_id.substring(0, goods_id.length() - 1);
        return goods_id;
    }


    /**
     * 优惠券
     * @param data
     */
    private void setTv_conpon(final SubmitOrderBean.DataEntity.ShopListEntity data) {
        mTv_conpon.setOnClickListener(new View.OnClickListener() {          // 点击事件
            @Override
            public void onClick(View v) {
                if (data.getCoupon_number() != 0) {
                    Intent intent = new Intent();
                    intent.putExtra(INTENT_MODE, CouponManagerActivity.COUPONMODE_SELECT);
                    intent.putExtra(INTENT_GOODS_ID, getGoodsId(data));
                    intent.putExtra(INTENT_SHOP_ID, data.getShop_id());
                    ((BaseActivity) mContext).startActivity(CouponManagerActivity.class, intent);
                }
            }
        });


        if (!TextUtils.isEmpty(data.couponPrice)) {                 /* 满减:优惠￥%s  */
            mTv_conpon.setText(Utils.getString(R.string.submitorder_couponmess, data.couponPrice));
            mTv_conpon.setTextColor(Utils.getColor(R.color.zhusediao));


        } else {
            mTv_conpon.setTextColor(Utils.getColor(R.color.text_color));

            if (data.getCoupon_number() == 0) {        /* 没有优惠 */
                mTv_conpon.setCompoundDrawables(null, null, null, null);
                mTv_conpon.setText("");

            } else {                                    /* 有优惠 */
                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.first_btn_jinru);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                mTv_conpon.setCompoundDrawables(null, null, drawable, null);
                mTv_conpon.setText(Utils.getString(R.string.can_use_couponnumber, data.getCoupon_number() + ""));
            }
        }
    }

    public interface SubmitOrderStateInterFace{
        /**
         * 获取当前订单类型
         * @return
         */
        int getCurrOrderType();
    }
}
