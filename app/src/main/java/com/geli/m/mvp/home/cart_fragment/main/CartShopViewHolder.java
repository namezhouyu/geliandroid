package com.geli.m.mvp.home.cart_fragment.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.CartBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.cart_fragment.main.CartGoodsViewHolder.GoodsClickListener;
import com.geli.m.mvp.home.other.shopdetails_activity.ShopDetailsActivity;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import static com.geli.m.config.Constant.AP_OtherStatus_Cancel;
import static com.geli.m.config.Constant.AP_OtherStatus_NormalUse;
import static com.geli.m.config.Constant.AP_OtherStatus_Revising;
import static com.geli.m.config.Constant.AP_TempAp_Apply;
import static com.geli.m.config.Constant.AP_TempAp_No;
import static com.geli.m.config.Constant.AP_TempAp_Yes;
import static com.geli.m.config.Constant.AP_UseStatus_BeOverdue;
import static com.geli.m.config.Constant.AP_shop_status_on_open;
import static com.geli.m.config.Constant.AP_shop_status_open;
import static com.geli.m.config.Constant.INTENT_SHOP_ID;

/**
 * Created by Steam_l on 2018/1/4.
 *
 * 购物车 -- 项的 商店
 */
public class CartShopViewHolder extends BaseViewHolder<CartBean.DataEntity> {
    Context mContext;

    /** 选择商店 */
    private final CheckBox mCb_check;
    /** 商店名 */
    private final TextView mTv_shopname;
    /** 距离起订还差多少... */
    private final TextView mTv_moq;

    /** 账期超额度 */
    private final TextView mTv_ap;

    /** 商品列表 */
    private final EasyRecyclerView mErv_goods;

    ShopClickListener mShopClickListener;
    GoodsClickListener mGoodsClickListener;

    public CartShopViewHolder(ViewGroup parent, Context context,
                              ShopClickListener shopClickListener,
                              GoodsClickListener goodsClickListener){//, ShopClickListener listener) {
        super(parent, R.layout.itemview_cart_shop_temp);
        mContext = context;
        mShopClickListener = shopClickListener;
        mGoodsClickListener = goodsClickListener;


        mTv_moq = $(R.id.tv_itemview_cart_moq);
        mTv_ap = $(R.id.tv_itemview_cart_ap);

        mCb_check = $(R.id.cb_itemview_cart_shopcheck);
        mTv_shopname = $(R.id.tv_itemview_cart_shopname);

        mErv_goods = $(R.id.erv_itemview_cart_goods);
    }

    @Override
    public void setData(final CartBean.DataEntity data) {
        super.setData(data);

        setErvGoods(data);
        setCheck(data);
        setShopClick(data);
    }

    /**
     * 勾选/取消勾选"商店" -- 选择按钮
     * @param data
     */
    private void setCheck(final CartBean.DataEntity data) {
        mCb_check.setChecked(data.isCheck);
        mCb_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mShopClickListener != null){
                    data.isCheck = mCb_check.isChecked();
                    mShopClickListener.cbShopClickVH(getAdapterPosition(), data);
                }
            }
        });


    }

    /**
     * 设置商店名 和 点击事件
     * @param data
     */
    private void setShopClick(final CartBean.DataEntity data) {
        String shopName = data.getShop_name();
        if(shopName.length() > 11){
            shopName = shopName.substring(0, 11);
        }
        mTv_shopname.setText(shopName);


        if (data.getShop_id() == Constant.overseasId) {
            mTv_shopname.setClickable(false);
        } else {
            mTv_shopname.setClickable(true);
            mTv_shopname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra(INTENT_SHOP_ID, data.getShop_id() + "");
                    ((BaseActivity) mContext).startActivity(ShopDetailsActivity.class, intent);
                }
            });

            // setMoq(data);            // 起订量 -- 不要了

            setExceed(data);            // 账期预算 -- 是否超出了
        }
    }

    /**
     * 起订量
     * @param data
     */
    private void setMoq(CartBean.DataEntity data) {
        if (data.size < data.getMoq()) {                    // 商品数量 小于 起订量
            mTv_moq.setVisibility(View.VISIBLE);
            mTv_moq.setText(Utils.getString(R.string.moq_cart, data.getMoq() + ""));
            // mTv_moq.setText(Utils.getString(R.string.moq, (data.getMoq() - data.size) + "", data.unit));
        } else {
            mTv_moq.setVisibility(View.GONE);
        }
    }


    /**
     * 账期预算 -- 是否超出了
     * @param shopEntity
     */
    private void setExceed(final CartBean.DataEntity shopEntity) {
        CartBean.DataEntity.ShEntity shEntity = shopEntity.getSh();
        if(shopEntity.isUseAP && getSelectItem(shopEntity)) {
            mTv_ap.setVisibility(View.VISIBLE);

            if(shopEntity.shop_sh_status == AP_shop_status_open){
                if (shEntity != null) {
                    switch (shopEntity.getSh().getStatus()){
                        case AP_OtherStatus_Cancel:                         /* 0取消账期权限 */
                            mTv_ap.setText(Utils.getString(R.string.unopen_go_open));
                            mTv_ap.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (mShopClickListener != null) {
                                        mShopClickListener.openApClick(shopEntity.shop_id);
                                    }
                                }
                            });
                            break;

                        case AP_OtherStatus_NormalUse:                         /* 1开通权限 */
                            setBeOverdue(shopEntity);
                            break;

                        case AP_OtherStatus_Revising:                         /* 2账期修改中 */
                            mTv_ap.setText(Utils.getString(R.string.adjustment_of_accounts));
                            break;

                        default:
                            break;
                    }
                } else {
                    if(shopEntity.getSh_apply() == 0) {
                        mTv_ap.setText(Utils.getString(R.string.unopen_go_open));
                        mTv_ap.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mShopClickListener != null) {
                                    mShopClickListener.openApClick(shopEntity.shop_id);
                                }
                            }
                        });
                    }else {
                        mTv_ap.setText(Utils.getString(R.string.apply_ap));
                    }
                }
            }else if(shopEntity.shop_sh_status == AP_shop_status_on_open){
                mTv_ap.setText(Utils.getString(R.string.unopen_ap));
            }

        }else {                                     // 不勾选账期
            mTv_ap.setVisibility(View.GONE);
        }
    }

    private boolean getSelectItem(CartBean.DataEntity shopEntity){
        if(shopEntity.isCheck){
            return true;
        }else {
            boolean isSelect = false;
            for(CartBean.DataEntity.CartListEntity goodsEntity : shopEntity.getCart_list()){
                if(goodsEntity.isCheck){
                    isSelect = true;
                    break;
                }
            }
            return isSelect;
        }
    }

    /**
     * 设置逾期
     * @param shopEntity
     */
    private void setBeOverdue(final CartBean.DataEntity shopEntity){
        if(shopEntity.getSh().getSh_status() == AP_UseStatus_BeOverdue){
            mTv_ap.setText(Utils.getString(R.string.ap_be_overdue));
        }else {
            setViewByExceed(shopEntity);
        }
    }

    /**
     * 设置超额
     * @param shopEntity
     */
    private void setViewByExceed(final CartBean.DataEntity shopEntity) {
        if(shopEntity.isExceed()) {                                 // 超出

            if(shopEntity.isExceed()) {                                 // 超出
                if (shopEntity.getSh().getIs_temp() == AP_TempAp_No) {
                mTv_ap.setText(Utils.getString(R.string.exceed_account_go));
                mTv_ap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mShopClickListener != null) {
                            mShopClickListener.applyApTempClick(shopEntity.shop_id, shopEntity.getSh());
                        }
                    }
                });
                } else if(shopEntity.getSh().getIs_temp() == AP_TempAp_Yes){
                    mTv_ap.setText(Utils.getString(R.string.exceed_account_cannot_buy));
                }else if(shopEntity.getSh().getIs_temp() == AP_TempAp_Apply){         // 未申请临时账期 -- 正在申请临时账期
                    mTv_ap.setText(Utils.getString(R.string.apply_temp_ap));
                }
            }
        }else {
            mTv_ap.setVisibility(View.GONE);
        }
    }

    RecyclerArrayAdapter mAdapter;
    public void setErvGoods(CartBean.DataEntity ervGoods) {
        mAdapter = new RecyclerArrayAdapter(mContext) {

            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new CartGoodsViewHolder(parent, mContext, mGoodsClickListener);
            }
        };

        mErv_goods.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mErv_goods.getRecyclerView().setHasFixedSize(false);
        mErv_goods.setAdapterWithProgress(mAdapter);
        // mErv_goods.setRefreshListener(this);

        mAdapter.addAll(ervGoods.getCart_list());
    }

    public interface ShopClickListener{
        /**
         * 勾选/取消勾选"商店"
         */
        void cbShopClickVH(int position, CartBean.DataEntity data);

        /**
         * 如果"账期超额了"没有申请过临时账期额度"就可以去申请
         * @param shopId        商店id
         * @param shEntity      账期的情况
         */
        void applyApTempClick(int shopId, CartBean.DataEntity.ShEntity shEntity);


        /**
         * 开通账期
         * @param shopId        商店id
         */
        void openApClick(int shopId);

    }
}
