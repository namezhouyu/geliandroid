package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import com.geli.m.R;
import com.geli.m.api.UrlSet;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.OrderListBean;
import com.geli.m.bean.SubmitOrderNewBean;
import com.geli.m.config.Constant;
import com.geli.m.coustomview.ErrorView;
import com.geli.m.dialog.DownloadInvoiceDialog;
import com.geli.m.dialog.TipDialog;
import com.geli.m.mvp.base.MVPFragment;
import com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.aftersold_activity.AfterSoldActivity;
import com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.aftersolddetails_activity.AfterSoldDetailsActivity;
import com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.goodscomment_activity.GoodsCommentActivity;
import com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.logisticsdetails_activity.LogisticsDetailsActivity;
import com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.orderdetails_activity.OrderDetailsActivity;
import com.geli.m.mvp.home.other.pay_activity.PayActivity;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Steam_l on 2018/1/8.
 */
public class MyOrderFragment extends MVPFragment<OrderListPresentImpl> implements OrderListView,
        ErrorView.ClickRefreshListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.erv_itemview_myorder_shoplist)
    EasyRecyclerView mErvList;

    public static final String ARG_STATE = "arg_state";
    public String[] mCurrState;
    private int mLastOffset;
    private int mLastPosition;

    private int page = 1;
    private RecyclerArrayAdapter mAdapter;

    boolean mIsShow = false;

    public boolean mReceiving;
    public TipDialog mTipDialog;
    public boolean mUniversal;

    public static MyOrderFragment newInstance(String[] state) {
        MyOrderFragment myOrderFragment = new MyOrderFragment();
        Bundle args = new Bundle();
        args.putStringArray(ARG_STATE, state);
        myOrderFragment.setArguments(args);
        return myOrderFragment;
    }

    @Override
    protected OrderListPresentImpl createPresent() {
        return new OrderListPresentImpl(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsShow = true;
        onRefresh();
    }

    @Override
    public void onPause() {
        super.onPause();
        mIsShow = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        onRefresh();
    }

    @Override
    public int getResId() {
        return R.layout.fragment_myorder;
    }

    @Override
    protected void init() {
        Bundle arguments = getArguments();
        mCurrState = arguments.getStringArray(ARG_STATE);
    }

    @Override
    protected void initData() {
        initAdapter();                      // 设置适配器
        initErv();                          // 设置erv的布局和适配器，刷新处理
        setErvOtherView();                  // 设置Erv的"错误、没数据"的界面，加载处理
        page = 1;
    }

    @Override
    protected void initEvent() {

    }

    /**
     * 设置适配器
     */
    private void initAdapter() {
        mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyOrderShopViewHolder(parent, mContext, MyOrderFragment.this, mCurrState,
                        new MyOrderShopViewHolder.ClickListener() {
                            @Override
                            public void payState(OrderListBean.DataEntity entity, int id) {
                                complyPayState(entity, id);
                            }

                            @Override
                            public void shipState(OrderListBean.DataEntity entity) {
                                complyShipState(entity);
                            }

                            @Override
                            public void receivingState(OrderListBean.DataEntity entity, int id) {
                                complyReceivingState(entity, id);
                            }

                            @Override
                            public void evaluationState(OrderListBean.DataEntity entity, int id) {
                                complyEvaluationState(entity, id);
                            }

                            @Override
                            public void aftrmarketState(OrderListBean.DataEntity entity, int id) {
                                complyAftrmarketState(entity, id);
                            }
                        });
            }
        };

        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                OrderListBean.DataEntity dataEntity = (OrderListBean.DataEntity) mAdapter.getItem(position);
                intent.putExtra(Constant.INTENT_ORDER_ID, dataEntity.getOrder_id() + "");
                intent.putExtra(Constant.INTENT_AP_CLOSING_TIME, dataEntity.getClosing_time());
                startActivity(OrderDetailsActivity.class, intent);
            }
        });
    }

    /**
     * 设置erv的布局和适配器，刷新的处理
     */
    private void initErv() {
        mErvList.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.line_color), Utils.dip2px(mContext, 0.5f), Utils.dip2px(mContext, 15), 0));
        mErvList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mErvList.setAdapterWithProgress(mAdapter);

        mErvList.setRefreshListener(this);                                  // 刷新
        mErvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (recyclerView.getLayoutManager() != null) {
                    getPositionAndOffset(recyclerView.getLayoutManager());
                }
            }
        });
    }

    /**
     * 设置Erv的"错误、没数据"的界面，加载处理
     */
    private void setErvOtherView() {
        EasyRecyclerViewUtils.initEasyRecyclerView(mErvList, this);
        EasyRecyclerViewUtils.initAdapter(mAdapter, new EasyRecyclerViewUtils.AdapterListener() {
            @Override
            public void onMoreShow() {
                page++;
                getData();
            }

            @Override
            public void onErrorClick() {
                mAdapter.resumeMore();
            }
        });
    }


    /**
     * 记录RecyclerView当前位置
     *
     * @param layoutManager
     */
    private void getPositionAndOffset(RecyclerView.LayoutManager layoutManager) {

        View topView = layoutManager.getChildAt(0);         // 获取可视的第一个view
        if (topView != null) {
            mLastOffset = topView.getTop();                       // 获取与该view的顶部的偏移量
            mLastPosition = layoutManager.getPosition(topView);   // 得到该View的数组位置
        }
    }


    /**
     * 让RecyclerView滚动到指定位置
     */
    private void scrollToPosition() {
        if (mErvList.getRecyclerView().getLayoutManager() != null && mLastPosition >= 0) {
            ((LinearLayoutManager) mErvList.getRecyclerView().getLayoutManager())
                    .scrollToPositionWithOffset(mLastPosition, mLastOffset);
        }
    }


    @Override
    public void onSuccess(String msg) {
        ToastUtils.showToast(msg);
        if (mReceiving) {
            mReceiving = false;
            if (mTipDialog != null) {
                mTipDialog.dismiss();
            }
        }
        if (mUniversal) {
            mUniversal = false;
            onRefresh();
        }
    }

    @Override
    public void onError(String msg) {
        if (mUniversal) {
            mUniversal = false;
        }
        if (page != 1) {
            mAdapter.pauseMore();
            page = page - 1;        // 因为点击重新加载更多后page会+1;出错所以现在先-1
        } else {
            mErvList.showError();
        }
        ToastUtils.showToast(msg);
    }

    @Override
    public void showLoading() {
        if (page == 1) {
            if (mErvList != null) {
                mErvList.setRefreshing(true);
            }
        }
    }

    @Override
    public void hideLoading() {
        if (page == 1) {
            if (mErvList != null) {
                mErvList.setRefreshing(false);
            }
        }
    }

    @Override
    public void showOrderList(List<OrderListBean.DataEntity> entityList) {
        if (page == 1) {
            mAdapter.clear();
            if (entityList == null || entityList.size() == 0) {
                mErvList.showEmpty();
                return;
            }
        }
        mAdapter.addAll(entityList);
        if (mIsShow && getUserVisibleHint()) {
            scrollToPosition();             // 让RecyclerView滚动到指定位置
        }
        if (entityList.size() < 20) {       // 本次回的数据小余 20 说明后面没有数据了
            mAdapter.stopMore();
        }
    }


    /* 错误页面点击的 */
    @Override
    public void clickRefresh() {
        onRefresh();
    }

    /* 重新加载数据 */
    @Override
    public void onRefresh() {
        page = 1;
        getData();
    }

    /**
     * 网络加载数据
     */
    private void getData() {
        if (mIsShow && getUserVisibleHint()) {
            Map data = new HashMap();
            data.put("page", page + "");
            data.put("user_id", GlobalData.getUser_id());
            if (mCurrState != null && !Arrays.equals(mCurrState, Constant.STATE_ALL)) {
                data.put("order_status", mCurrState[0]);
                data.put("pay_status", mCurrState[1]);
                data.put("shipping_status", mCurrState[2]);

                // 1,2,0
                // "order_status":1, "pay_status":8 "shipping_type": 0,

                //
            }
            mPresenter.getOrderList(data, true);
        }

        //        //避免用户快速滑动请求网络
        //        Observable
        //                .timer(BaseModel.delayTime, TimeUnit.MILLISECONDS)
        //                .observeOn(AndroidSchedulers.mainThread())
        //                .subscribe(new Consumer<Long>() {
        //                    @Override
        //                    public void accept(Long aLong) throws Exception {
        //
        //                    }
        //                });
    }


    /*-------------------------------- 根据状态设置点击事件 ---------------------------------------*/

    /**
     * 待支付
     *
     * @param entity
     * @param id
     */
    private void complyPayState(final OrderListBean.DataEntity entity, int id) {
        if (id == R.id.bt_itemview_myorder_right) {
            Intent intent = new Intent();
            SubmitOrderNewBean.DataBean dataBean = new SubmitOrderNewBean.DataBean();
            dataBean.setOrder_sn(entity.getOrder_sn());
            dataBean.setAmount(Double.valueOf(getPrice(entity)));
            intent.putExtra(Constant.INTENT_BEAN, dataBean);
            startActivity(PayActivity.class, intent);
        } else {
            cancelOrder(entity.getOrder_id() + "");
        }
    }


    /**
     * 获取支付价格
     *
     * @return
     */
    private String getPrice(OrderListBean.DataEntity entity) {
        if (entity == null) {
            return "";
        }
        String price = "";
        if (isOverseas(entity)) {                                           // 海外的
            if (entity.getPercentage() == Constant.Pay_Percentage_70) {
                price = entity.getSeventy_percent();                        // 定金30%
            } else {
                price = entity.getPer_cent();                               // 70%
            }
        } else {
            price = entity.getSum_amount();
        }
        return price;
    }

    /**
     * 待发货
     *
     * @param entity
     */
    private void complyShipState(OrderListBean.DataEntity entity) {
        if (entity.getLogistics_is_pay() == Constant.Logistics_Status_Yes) {       // 需要支付运费
            if (Double.valueOf(entity.getLogistics_price()) > 0) {       // 0元 -- 说明在审核中
                Intent intent = new Intent();
                SubmitOrderNewBean.DataBean dataBean = new SubmitOrderNewBean.DataBean();
                dataBean.setOrder_sn(entity.getOrder_sn());
                dataBean.setAmount(Double.valueOf(entity.getLogistics_price()));
                intent.putExtra(Constant.INTENT_IS_LOGISTICS, true);
                intent.putExtra(Constant.INTENT_BEAN, dataBean);
                startActivity(PayActivity.class, intent);
            }
        } else {
            universal(UrlSet.remind, entity.getOrder_id() + ""); // 提醒发货
        }

    }

    /**
     * 待收货
     *
     * @param entity
     * @param id
     */
    private void complyReceivingState(final OrderListBean.DataEntity entity, int id) {
        if (id == R.id.bt_itemview_myorder_right) {
            if (isOverseas(entity)) {
                if (entity.getPercentage() != Constant.Pay_Percentage_All) {     /* 海外支付 -- 不是全款 */
                    complyPayState(entity, id);                 // 支付
                    return;
                }
            }

            if (entity.getIs_sh() == Constant.AP_True) {                   // 账期
                if (entity.getShipping_status() == 1) {      //已发货
                    confirmReceipt(entity);
                } else if (entity.getShipping_status() == 2) { //已收货
                    complyPayState(entity, id);                     // 支付
                }
            } else {
                confirmReceipt(entity);
            }
        } else {            // 查看物流
            Intent intent = new Intent().putExtra(Constant.INTENT_ORDER_ID, entity.getOrder_id() + "");
            startActivity(LogisticsDetailsActivity.class, intent);
        }
    }

    /**
     * 确认收货 -- 窗口
     *
     * @param entity
     */
    private void confirmReceipt(final OrderListBean.DataEntity entity) {
        // 确认收货
        mTipDialog = TipDialog.newInstance(Utils.getString(R.string.order_confirmreceipt));
        mTipDialog.setOnclickListener(new TipDialog.ClickListenerInterface() {
            @Override
            public void doConfirm(TipDialog tipDialog) {
                mReceiving = true;
                universal(UrlSet.confirmReceipt, entity.getOrder_id() + "");
            }

            @Override
            public void doCancel() {
                mTipDialog.dismiss();
            }
        });
        mTipDialog.show(getChildFragmentManager(), "");
    }


    /**
     * 待评价 -- 未评论
     *
     * @param entity
     * @param id
     */
    private void complyEvaluationState(OrderListBean.DataEntity entity, int id) {
        if (Arrays.equals(mCurrState, Constant.STATE_AFTERMARKET)) {        // 如果在"售后中"，就不评论直接"售后"
            complyAftrmarketState(entity, id);

        } else {
            if (id == R.id.bt_itemview_myorder_right) {                 /* 底部 -- 右边按钮 -- 评价 */
                Intent intent = new Intent().putParcelableArrayListExtra(Constant.INTENT_COMMENT_GOODS, entity.getGoods_list());
                startActivity(GoodsCommentActivity.class, intent);
            } else {                                                    /* 左边按钮 */
                downloadInvoice(entity);            // 下载发票
            }
        }
    }

    /**
     * 售后!
     *
     * @param entityL:
     * @param id
     */
    private void complyAftrmarketState(OrderListBean.DataEntity entity, int id) {
        if (id == R.id.bt_itemview_myorder_right) {                 /* 底部 -- 右边按钮 -- 售后 */
            if (entity.getAfter_sold_status() == Constant.AfterSold_Status_Apply) {           // 0：申请售后
                Intent intent = new Intent().putExtra(Constant.INTENT_ORDER_ID, entity.getOrder_id() + "");
                startActivity(AfterSoldActivity.class, intent);
            } else {
                Intent intent = new Intent().putExtra(Constant.INTENT_ORDER_ID, entity.getOrder_id() + "");
                startActivity(AfterSoldDetailsActivity.class, intent);
            }

        } else {
            downloadInvoice(entity);                // 下载发票
        }
    }


    /**
     * 下载发票
     *
     * @param entity
     */
    private void downloadInvoice(OrderListBean.DataEntity entity) {
        DownloadInvoiceDialog.newInstance(entity.getInvoice_img()).show(getChildFragmentManager(), "");
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
        return entity.getOrder_type().equals(Constant.Goods_Type_Spot + "") ||
                entity.getOrder_type().equals(Constant.Goods_Type_Futures + "");
    }

    /**
     * 提醒发货 / 确认收货  -- 看url
     *
     * @param url
     * @param order_id
     */
    public void universal(String url, String order_id) {
        mUniversal = true;
        mPresenter.universal(url, order_id);
    }

    /**
     * 取消订单
     *
     * @param order_id
     */
    public void cancelOrder(final String order_id) {
        final TipDialog tipDialog = TipDialog.newInstance(Utils.getString(R.string.is_cancel_order));
        tipDialog.setOnclickListener(new TipDialog.ClickListenerInterface() {
            @Override
            public void doConfirm(TipDialog tipDialog) {
                tipDialog.dismiss();
                mPresenter.universal(UrlSet.cancelOrder, order_id);
                clickRefresh();
            }

            @Override
            public void doCancel() {
                tipDialog.dismiss();
            }
        });
        tipDialog.show(getChildFragmentManager(), "");
    }
}
