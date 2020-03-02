package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.OrderListBean;
import com.geli.m.config.Constant;
import com.geli.m.dialog.BottomDialog;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.orderdetails_activity.MyOrderGoodsViewHolder;
import com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.orderdetails_activity.OrderDetailsActivity;
import com.geli.m.utils.PriceUtils;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.TimeUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.Arrays;


/**
 * Created by Steam_l on 2018/1/8.
 */

public class MyOrderShopViewHolder extends BaseViewHolder<OrderListBean.DataEntity> {

    Context mContext;
    /**
     * 商品列表
     */
    private final EasyRecyclerView mErv_list;
    /**
     * 商品列表适配器
     */
    private RecyclerArrayAdapter mAdapter;
    /**
     * 某张单 -- 商品的数量
     */
    private final TextView mTv_number;
    /**
     * 某张单 -- 所有商品的价格
     */
    private final TextView mTv_price;
    /**
     * 商店名称
     */
    private final TextView mTv_name;
    /**
     * 此单 -- 状态
     */
    private final TextView mTv_state;
    /**
     * 底下 -- 左边按钮
     */
    private final Button mBt_left;
    /**
     * 底下 -- 右边按钮
     */
    private final Button mBt_right;

    private BottomDialog mBottomDialog;
    MyOrderFragment mFragment;
    /**
     * 当前这个"布局"显示的内容 -- 全部;待付款;已发货...
     */
    public String[] mCurrState;

    ClickListener mClickListener;

    public MyOrderShopViewHolder(ViewGroup parent, Context context, MyOrderFragment fragment,
                                 String[] currState, ClickListener clickListener) {
        super(parent, R.layout.itemview_myorder_shop);
        mContext = context;
        mFragment = fragment;
        mCurrState = currState;
        mClickListener = clickListener;

        mTv_price = $(R.id.tv_itemview_myorder_price);
        mErv_list = $(R.id.erv_itemview_myorder_goodslist);
        mTv_state = $(R.id.tv_itemview_myorder_state);
        mTv_name = $(R.id.tv_itemview_myorder_name);
        mBt_left = $(R.id.bt_itemview_myorder_left);
        mBt_right = $(R.id.bt_itemview_myorder_right);
        mTv_number = $(R.id.tv_itemview_myorder_number);

//        mAdapter = new RecyclerArrayAdapter(mContext) {
//            @Override
//            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
//                return new MyOrderGoodsViewHolder(parent, mContext);
//            }
//        };
    }


    // EasyRecyclerView 中的  OnBindViewHolder 执行这个方法
    @Override
    public void setData(final OrderListBean.DataEntity entity) {
        // mAdapter.clear();

        mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyOrderGoodsViewHolder(parent, mContext);
            }
        };

        //entity.getIs_sh()

        setErvAdapter(entity);
        setShopName(entity);                    // 设置商店名
        setPrice(entity);
        setGoodCount(entity);                   // 设置商品数量
        setBtState(entity);                     // 设置界面
        setBtnListener(entity);                 // 设置按钮点击事件
    }


    private void setErvAdapter(final OrderListBean.DataEntity entity) {


        if (entity != null && entity.getGoods_list() != null) {
            int i = 0;
            int is_sh = entity.getIs_sh();
            for (OrderListBean.DataEntity.GoodsListEntity goodsListEntity : entity.getGoods_list()) {
                entity.getGoods_list().get(i).setIs_sh(is_sh);
            }
        }


        mAdapter.addAll(entity.getGoods_list());
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                intent.putExtra(Constant.INTENT_ORDER_ID, entity.getOrder_id() + "");
                intent.putExtra(Constant.INTENT_AP_CLOSING_TIME, entity.getClosing_time());
                ((BaseActivity) mContext).startActivity(OrderDetailsActivity.class, intent);
            }
        });

        mErv_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mErv_list.setAdapterWithProgress(mAdapter);
    }

    /**
     * 设置商店名 -- 如果是"海外直采"就将"商店名"设置为对应的名字
     *
     * @param entity
     */
    private void setShopName(OrderListBean.DataEntity entity) {
        String shop_name = entity.getShop_name();
        if (entity.getGoods_list().size() > 0) {
            // 拿第一个做例子
            OrderListBean.DataEntity.GoodsListEntity goodsEntity = entity.getGoods_list().get(0);
            switch (goodsEntity.getGoods_type()) {
                case Constant.Goods_Type_Spot:                                   /* 海外直采-现货 */
                    shop_name = Utils.getString(R.string.order_title_gelispot);
                    break;
                case Constant.Goods_Type_Futures:                                /* 海外直采-期货 */
                    shop_name = Utils.getString(R.string.order_title_gelisfutures);
                    break;
                case Constant.Goods_Type_GroupBuy:                               /* 海外直采-团购 */
                    shop_name = Utils.getString(R.string.order_title_gelisgroup_buy);
                    break;
            }
        }
        mTv_name.setText(shop_name);
    }


    /**
     * 设置价格
     *
     * @param entity
     */
    private void setPrice(OrderListBean.DataEntity entity) {
        if (isOverseas(entity)) {               /* 海外订单 -- 2:海外现货; 3:海外期货*/
            String seventy_percent = entity.getSeventy_percent();       // 百分之七十
            if (StringUtils.isEmpty(seventy_percent)) {
                seventy_percent = Utils.getString(R.string.wait_change);
            } else if (Double.valueOf(seventy_percent) == 0) {
                seventy_percent = Utils.getString(R.string.wait_change);
            }

            // 合计：￥0.03 + ￥核算中
            mTv_price.setText(Utils.getString(R.string.total) +
                    PriceUtils.getOverseasPrice(R.string.overaeas_order_price1,
                            entity.getPer_cent() + "",
                            seventy_percent + ""));

        } else {                                /* 不是海外订单 */
            mTv_price.setText(PriceUtils.getPrice(entity.getSum_amount()));
        }
    }

    /**
     * 如果是 --
     * true -- 海外订单 (2:海外现货; 3:海外期货)
     * false -- 不是海外订单
     *
     * @param entity
     * @return
     */
    public boolean isOverseas(OrderListBean.DataEntity entity) {
        return entity.getOrder_type().equals(Constant.Goods_Type_Spot + "")
                || entity.getOrder_type().equals(Constant.Goods_Type_Futures + "");
    }

    /**
     * 设置食品数量
     *
     * @param entity
     */
    private void setGoodCount(OrderListBean.DataEntity entity) {
        if (entity.getGoods_list().size() > 0) {
            mTv_number.setText(Utils.getString(R.string.total_goods_number,
                    entity.getGoods_list().size(),
                    Utils.getString(R.string.stock)));
        }
    }

    /**
     * 设置界面
     *
     * @param entity
     */
    private void setBtState(OrderListBean.DataEntity entity) {
        mBt_left.setVisibility(View.VISIBLE);
        mBt_right.setVisibility(View.VISIBLE);
        mBt_left.setEnabled(true);
        mBt_right.setEnabled(true);

        if (Arrays.equals(mCurrState, Constant.STATE_ALL)) {
            setBtnStateAll(entity);
        } else if (Arrays.equals(mCurrState, Constant.STATE_PAY)) {
            setPayState(entity);
        } else if (Arrays.equals(mCurrState, Constant.STATE_SHIP)) {
            setShipState(entity);
        } else if (Arrays.equals(mCurrState, Constant.STATE_RECEIVING)) {
            setReceivingState(entity);
        } else if (Arrays.equals(mCurrState, Constant.STATE_EVALUATION)) {
            setEvaluationgState(entity);
        } else if (Arrays.equals(mCurrState, Constant.STATE_AFTERMARKET)) {
            setAftemarketState(entity);
        }
    }

    private void setBtnStateAll(OrderListBean.DataEntity entity) {
        if (Arrays.equals(entity.getState(), Constant.STATE_PAY)) {
            setPayState(entity);
        } else if (Arrays.equals(entity.getState(), Constant.STATE_SHIP) ||
                Arrays.equals(entity.getState(), Constant.STATE_SHIP_AP_180)) {
            setShipState(entity);
        } else if (Arrays.equals(entity.getState(), Constant.STATE_RECEIVING) ||
                Arrays.equals(entity.getState(), Constant.STATE_SHIP_AP_181)) {
            setReceivingState(entity);
        } else if ((Arrays.equals(entity.getState(), Constant.STATE_EVALUATION) && entity.getIs_comment() == 0) ||
                (Arrays.equals(entity.getState(), Constant.STATE_SHIP_AP_582) && entity.getIs_comment() == 0)) {
            setEvaluationgState(entity);
        } else if ((Arrays.equals(entity.getState(), Constant.STATE_EVALUATION) && entity.getIs_comment() == 1) ||
                (Arrays.equals(entity.getState(), Constant.STATE_SHIP_AP_582) && entity.getIs_comment() == 1)) {
            setAftemarketState(entity);
        } else if (Arrays.equals(entity.getState(), Constant.STATE_CANCELORDER)) {
            setCancelOrderState(entity);
        }
    }

    private void setBtnListener(final OrderListBean.DataEntity entity) {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complyForState(entity, v.getId());
            }
        };


        mBt_left.setOnClickListener(clickListener);
        mBt_right.setOnClickListener(clickListener);
    }

    /*-------------------  根据步骤设置界面（标题、左右按钮） ----------------------*/

    /**
     * 设置界面 -- 待付款
     *
     * @param entity
     */
    private void setPayState(OrderListBean.DataEntity entity) {
        mBt_left.setText(Utils.getString(R.string.cancel_order));
        setPayState(entity, false);
    }

    /**
     * 设置界面 -- 待发货
     *
     * @param entity
     */
    private void setShipState(OrderListBean.DataEntity entity) {
        String message = "";
        mBt_right.setEnabled(true);
        if (isOverseas(entity)) {
            message = Utils.getString(R.string.wait_openorder_delivery);
        } else {
            message = Utils.getString(R.string.wait_delivery);
        }

        if (entity.getLogistics_is_pay() == Constant.Logistics_Status_Yes) {       // 需要支付运费
            if (Double.valueOf(entity.getLogistics_price()) == 0) {       // 0元 -- 说明在审核中
                message = Utils.getString(R.string.freight_calculation);
                mBt_right.setText(Utils.getString(R.string.payment_of_freight));
                mBt_right.setEnabled(false);
            } else {
                message = Utils.getString(R.string.wait_pay_freight);
                mBt_right.setText(Utils.getString(R.string.payment_of_freight));
            }
        } else {
            mBt_right.setText(Utils.getString(R.string.remind_the_shipment));
        }

        mTv_state.setText(message);
        mBt_left.setVisibility(View.GONE);

    }

    /**
     * 设置界面 -- 待收货
     *
     * @param entity
     */
    private void setReceivingState(OrderListBean.DataEntity entity) {
        mTv_state.setText(Utils.getString(R.string.goods_in_transit));
        mBt_left.setText(Utils.getString(R.string.check_logistics));
        if (isOverseas(entity)) {
            if (entity.getPercentage() != Constant.Pay_Percentage_All) { /* 不是 -- 全部付款完 */
                setPayState(entity, true);
                return;
            }
        }
        mBt_right.setText(Utils.getString(R.string.confirm_receipt));

        if (entity.getIs_sh() == Constant.AP_True) {           // 账期

            if (entity.getShipping_status() == 1) {          // 未收货
                mTv_state.setText(Utils.getString(R.string.order_receiving_normal_state));
                mBt_right.setText(Utils.getString(R.string.confirm_receipt));
            } else if (entity.getShipping_status() == 2) {
                String closingTime = TimeUtils.transForDate(entity.getClosing_time(), TimeUtils.format11);
                mTv_state.setText(Utils.getString(R.string.final_settlement_day, closingTime));
                mBt_right.setText(Utils.getString(R.string.account_payment));                 // 支付
                mBt_left.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 设置界面 -- 已评价
     *
     * @param entity
     */
    private void setEvaluationgState(OrderListBean.DataEntity entity) {
        mTv_state.setText(Utils.getString(R.string.order_completed));
        if (!TextUtils.isEmpty(entity.getInvoice_img())) {
            mBt_left.setText(Utils.getString(R.string.download_invoice));
        } else {
            mBt_left.setVisibility(View.GONE);
        }
        mBt_right.setText(Utils.getString(R.string.go_to_evaluation));
    }

    /**
     * 设置界面 -- 售后
     *
     * @param entity
     */
    private void setAftemarketState(OrderListBean.DataEntity entity) {
        if (!TextUtils.isEmpty(entity.getInvoice_img())) {
            mBt_left.setText(Utils.getString(R.string.download_invoice));
        } else {
            mBt_left.setVisibility(View.GONE);
        }
        String buttonName = "";
        switch (entity.getAfter_sold_status()) {     // 0:申请售后;1:待受理;2:受理中;3:售后完成
            case Constant.AfterSold_Status_Apply:                             /* 申请售后 */
                buttonName = Utils.getString(R.string.apply_for_after_sales);
                break;
            case Constant.AfterSold_Status_Admissible:                        /* 待受理 */
                buttonName = Utils.getString(R.string.to_accept);
                break;
            case Constant.AfterSold_Status_Admissibility:                     /* 受理中 */
                buttonName = Utils.getString(R.string.in_the_processing);
                break;
            case Constant.AfterSold_Status_Completion:                        /* 售后完成 */
                buttonName = Utils.getString(R.string.after_complete);
                break;
            default:
                buttonName = Utils.getString(R.string.apply_for_after_sales);
                break;
        }

        mTv_state.setText(Utils.getString(R.string.order_completed));
        mBt_right.setText(buttonName);
    }


    /**
     * 设置界面 -- 订单已取消
     *
     * @param entity
     */
    private void setCancelOrderState(OrderListBean.DataEntity entity) {
        mBt_left.setVisibility(View.GONE);
        mBt_right.setVisibility(View.GONE);
        mTv_state.setText(Utils.getString(R.string.order_cancel));
    }

    /**
     * 支付 -- 设置界面
     *
     * @param entity
     * @param isOverseas
     */
    public void setPayState(OrderListBean.DataEntity entity, boolean isOverseas) {

        mBt_right.setEnabled(true);

        // 1可以支付 2不可以支付,主要用于期货判断
        if (entity.getIs_pay() != 1) {                          /* 不可以支付 */
            mTv_state.setText("");
            mBt_right.setEnabled(false);        /* 不可支付 -- 右边按钮不可交互 */
            mBt_right.setText(Utils.getString(R.string.wait_change));

        } else {                                                /* 可以支付 */
            // 0未支付 1微信 2支付宝 3线下 4余额(支付方式)
            if (entity.getPay_type() == Constant.Pay_Type_LineDown) {                    /* 3线下 */
                setProofPay(entity, isOverseas);
            } else {                                                             /* 1微信 2支付宝 4余额(支付方式) */
                if (isOverseas) {
                    mTv_state.setText(Utils.getString(R.string.wait_pay_balance));


                    if (StringUtils.isEmpty(entity.getSeventy_percent()) ||
                            Double.valueOf(entity.getSeventy_percent()) == 0) {
                        mBt_right.setText(Utils.getString(R.string.wait_change));
                        mBt_right.setEnabled(false);
                    } else {
                        mBt_right.setText(Utils.getString(R.string.pay_balance));
                    }

                } else {
                    mTv_state.setText(Utils.getString(R.string.wait_pay));
                    mBt_right.setText(Utils.getString(R.string.immediate_payment));
                }
            }
        }
    }

    /**
     * 线下汇款 -- 设置界面
     *
     * @param entity
     * @param isOverseas
     */
    private void setProofPay(OrderListBean.DataEntity entity, boolean isOverseas) {
        // 线下 -- 0:不是线下支付  线下汇款（1待提交凭证,2审核中，3重新提交凭证，4审核成功）
        if (entity.getProof() == Constant.Pay_Proof_WaitSubmit) {          /* 待提交凭证 */
            if (StringUtils.isEmpty(entity.getSeventy_percent()) ||
                    Double.valueOf(entity.getSeventy_percent()) == 0) {
                mBt_right.setEnabled(false);
            }

            if (isOverseas) {
                mTv_state.setText(Utils.getString(R.string.wait_pay_balance));
                mBt_right.setText(Utils.getString(R.string.pay_balance));

            } else {
                mTv_state.setText(Utils.getString(R.string.wait_user_upload));
                mBt_right.setText(Utils.getString(R.string.immediate_payment));
            }

        } else if (entity.getProof() == Constant.Pay_Proof_Audit) {          /* 审核中 */
            mTv_state.setText(Utils.getString(R.string.under_review));
            mBt_right.setText(Utils.getString(R.string.under_review));
            mBt_right.setEnabled(false);

        } else if (entity.getProof() == Constant.Pay_Proof_AgainSubmit) {    /* 重新提交凭证 -- 审核失败,请重新提交 */
            mTv_state.setText(Utils.getString(R.string.documentfaild));
            mBt_right.setText(Utils.getString(R.string.re_payment));
        }
    }
    /*--------------------------------  设置按钮点击事件 ----------------------------------*/

    /**
     * 根据"订单的当前状态"处理
     *
     * @param entity
     * @param id
     */
    private void complyForState(OrderListBean.DataEntity entity, int id) {
        if (entity != null && mClickListener != null) {
            if (Arrays.equals(entity.getState(), Constant.STATE_PAY)) {                  /* 待支付 */
                mClickListener.payState(entity, id);
            } else if (Arrays.equals(entity.getState(), Constant.STATE_SHIP) ||
                    Arrays.equals(entity.getState(), Constant.STATE_SHIP_AP_180)) {          /* 待发货 */
                mClickListener.shipState(entity);
            } else if (Arrays.equals(entity.getState(), Constant.STATE_RECEIVING) ||
                    Arrays.equals(entity.getState(), Constant.STATE_SHIP_AP_181)) {     /* 待收货 */
                mClickListener.receivingState(entity, id);
            } else if ((Arrays.equals(entity.getState(), Constant.STATE_EVALUATION) && entity.getIs_comment() == 0) ||
                    (Arrays.equals(entity.getState(), Constant.STATE_SHIP_AP_582) && entity.getIs_comment() == 0)) {                           /* 待评价 -- 未评论 */
                mClickListener.evaluationState(entity, id);
            } else if ((Arrays.equals(entity.getState(), Constant.STATE_EVALUATION) && entity.getIs_comment() == 1) ||
                    (Arrays.equals(entity.getState(), Constant.STATE_SHIP_AP_582) && entity.getIs_comment() == 1)) {                           /* 售后 -- 已评论 */
                mClickListener.aftrmarketState(entity, id);
            } else if (Arrays.equals(entity.getState(), Constant.STATE_AFTERMARKET)) {  /* 售后 */
                mClickListener.aftrmarketState(entity, id);
            }
        }
    }

    interface ClickListener {
        /**
         * 待支付
         */
        void payState(OrderListBean.DataEntity entity, int id);

        /**
         * 待支付
         */
        void shipState(OrderListBean.DataEntity entity);

        /**
         * 待收货
         */
        void receivingState(OrderListBean.DataEntity entity, int id);

        /**
         * 待评价 -- 未评论
         */
        void evaluationState(OrderListBean.DataEntity entity, int id);

        /**
         * 售后
         */
        void aftrmarketState(OrderListBean.DataEntity entity, int id);
    }
}
