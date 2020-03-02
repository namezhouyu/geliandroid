package com.geli.m.mvp.home.mine_fragment.coupon_activity.coupon_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import butterknife.BindView;
import com.geli.m.R;
import com.geli.m.api.UrlSet;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.CouponBean;
import com.geli.m.coustomview.ErrorView;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.base.BaseView;
import com.geli.m.mvp.base.MVPFragment;
import com.geli.m.mvp.home.other.login_register_activity.LoginActivity;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.LoginInformtaionSpUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.geli.m.config.Constant.BROADCAST_COUPON;
import static com.geli.m.config.Constant.BROADCAST_DATA;
import static com.geli.m.config.Constant.VIEWTYPE_CANUSE;
import static com.geli.m.config.Constant.VIEWTYPE_NOUSE;
import static com.geli.m.config.Constant.VIEWTYPE_SHOPALL;

/**
 * Created by Steam_l on 2018/1/3.
 */

public class CouponFragment extends MVPFragment<CouponPresentImpl> implements BaseView, ErrorView.ClickRefreshListener, CouponView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.erv_coupon_list)
    EasyRecyclerView mErvList;

    public static String ARG_VIEWTYPE = "arg_viewtype";
    public static String ARG_ENTITY = "arg_entity";
    public static String ARG_GOODSID = "arg_goodsid";

    /** 模式 */
    public int mCurrViewType = VIEWTYPE_NOUSE;
    /** 优惠券列表 */
    private List<CouponBean> mCurrEntity;
    /** 当前选中的优惠券id */
    private int mCurrEditCouponId;
    /** 食品id */
    private String mGoodsId;
    private int page = 1;
    private RecyclerArrayAdapter mAdapter;


    @Override
    public int getResId() {
        return R.layout.fragment_coupon;
    }

    public static Fragment newInstanct(int viewtype) {
        return newInstance(viewtype, null, "");
    }

    public static Fragment newInstanct(int viewtype, String goodsId) {
        return newInstance(viewtype, null, goodsId);
    }

    public static Fragment newInstance(int viewtype, ArrayList<CouponBean> entity, String goodsId) {
        CouponFragment couponFragment = new CouponFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_VIEWTYPE, viewtype);

        switch (viewtype){
            case VIEWTYPE_SHOPALL:                              /* 商家可用,当前不请求网络 */
                bundle.putParcelableArrayList(ARG_ENTITY, entity);
                break;

            case VIEWTYPE_CANUSE:                               /* 订单中可以使用 */
                bundle.putString(ARG_GOODSID, goodsId);
                break;
        }

        couponFragment.setArguments(bundle);
        return couponFragment;
    }

    @Override
    protected void init() {
        super.init();
        Bundle arguments = getArguments();
        mCurrViewType = arguments.getInt(ARG_VIEWTYPE, mCurrViewType);

        switch (mCurrViewType){
            case VIEWTYPE_SHOPALL:                              /* 商家可用,当前不请求网络 */
                mCurrEntity = arguments.getParcelableArrayList(ARG_ENTITY);
                break;

            case VIEWTYPE_CANUSE:                               /* 订单中可以使用 */
                mGoodsId = arguments.getString(ARG_GOODSID);
                break;
        }
    }
    @Override
    protected void initData() {
        initErvAndAdpater();
        setView();
    }

    @Override
    protected void initEvent() {

    }

    private void initErvAndAdpater() {
        mErvList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new CouponViewHolder(parent, mContext, CouponFragment.this);
            }
        };
        mErvList.setAdapterWithProgress(mAdapter);
        ViewCompat.setNestedScrollingEnabled(mErvList.getRecyclerView(), false);
    }

    private void setView() {
        if (mCurrViewType == VIEWTYPE_SHOPALL) {            /* 商家可用,当前不请求网络 */
            setShopAllView();
            return;                                         // 这里是没有下拉刷新的(因为没有做下拉刷新，就直接return)
        } else {                                            /* 其他模式 */
            if (mCurrViewType == VIEWTYPE_CANUSE) {         /* 订单中可以使用  */
                setCanuseView();
            }
            setErvAndAdpater();
        }
    }

    /**
     * 在ShopDetailsFragment中拿到优惠券列表"ShopDetailsFragment加载CouponFragment"
     *
     * 商家可用,当前不请求网络
     */
    private void setShopAllView() {
        getView().setBackgroundColor(Utils.getColor(R.color.transparent));
        mAdapter.addAll(mCurrEntity);
    }

    /**
     * "选择的优惠券"模式 -- CouponManagerActivity加载CouponFragment
     * 选择优惠券后，广播告诉SubmitOrderActivity界面，然后关闭Activity
     *
     * 订单中可以使用
     */
    private void setCanuseView() {
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(BROADCAST_COUPON);
                intent.putExtra(BROADCAST_DATA, (CouponBean) mAdapter.getItem(position));
                mContext.sendBroadcast(intent);
                finish();
            }
        });
    }

    /**
     * 除了"VIEWTYPE_SHOPALL"其他的模式都设置这个
     */
    private void setErvAndAdpater() {
        EasyRecyclerViewUtils.initEasyRecyclerView(mErvList, this);
        mAdapter.setMore(R.layout.layout_loadingmore, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                page += 1;
                onRefresh();
            }

            @Override
            public void onMoreClick() {

            }
        });

        mAdapter.setNoMore(R.layout.layout_nomore);
        mAdapter.setError(R.layout.layout_more_error, new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {

            }

            @Override
            public void onErrorClick() {
                mAdapter.resumeMore();
            }
        });

        mErvList.setRefreshListener(this);
        onRefresh();
    }




    public int getViewType() {
        return mCurrViewType;
    }


    /**
     * 在商家列表界面 -- 领取按钮点击事件 -- 领取优惠券
     * @param coupon_id
     */
    public void collectCoupons(int coupon_id) {
        if (!LoginInformtaionSpUtils.getLoginInfString(mContext, LoginInformtaionSpUtils.login_login).equals("1")) {
            ShowSingleToast.showToast(mContext, Utils.getString(R.string.please_login));
            ((BaseActivity) mContext).startActivity(LoginActivity.class, new Intent());
            return;
        }
        mCurrEditCouponId = coupon_id;
        // 将优惠券挂钩到用户中
        mPresenter.collectCoupons(GlobalData.getUser_id(), coupon_id + "");
    }

    @Override
    protected CouponPresentImpl createPresent() {
        return new CouponPresentImpl(this);
    }


    // 领取成功后，刷新界面
    @Override
    public void onSuccess(String msg) {
        ToastUtils.showToast(msg);
        if (mPresenter.isCollect) {
            mPresenter.isCollect = !mPresenter.isCollect;
            int index = 0;
            for (CouponBean entity : mCurrEntity) {
                if (entity.getCp_id() == mCurrEditCouponId) {
                    entity.setIs_use(1);
                    mAdapter.update(entity, index);
                    break;
                }
                index++;
            }
        }
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showToast(msg);
        if (page != 1) {
            mAdapter.pauseMore();
            // 因为点击重新加载更多后page会+1;出错所以现在先-1
            page = page - 1;
        } else {
            mErvList.showError();
        }
    }

    @Override
    public void showLoading() {
        mErvList.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mErvList.setRefreshing(false);
    }

    @Override
    public void clickRefresh() {
        page = 1;
        onRefresh();
    }

    @Override
    public void showCouponList(List<CouponBean> couponBeanList) {
        if (couponBeanList == null || couponBeanList.size() == 0) {
            mAdapter.clear();
            mErvList.showEmpty();
            return;
        }

        if (page == 1) {
            mAdapter.clear();
        }

        mAdapter.addAll(couponBeanList);
        if (couponBeanList.size() < 20) {
            mAdapter.stopMore();
        }
    }

    @Override
    public void onRefresh() {
        String url = "";
        Map data = new HashMap();
        data.put("user_id", GlobalData.getUser_id());
        data.put("type", mCurrViewType + "");

        switch (mCurrViewType){
            case VIEWTYPE_CANUSE:
                url = UrlSet.AvailableCoupons;             // 4可用 5不可用 6可使用总数
                data.put("goods_ids", mGoodsId);
                break;

            default:
                url = UrlSet.myCoupon;                     // 1未领取 2使用记录 3过期
                data.put("page", page + "");
                data.put("page_size", "20");
                break;
        }

        mPresenter.getCouponList(url, data);
    }
}
