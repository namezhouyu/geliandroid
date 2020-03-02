package com.geli.m.mvp.home.mine_fragment.coupon_activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.bean.CouponBean;
import com.geli.m.coustomview.MyTabLayout;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.utils.Utils;

import static com.geli.m.config.Constant.BROADCAST_COUPON;
import static com.geli.m.config.Constant.BROADCAST_DATA;
import static com.geli.m.config.Constant.INTENT_GOODS_ID;
import static com.geli.m.config.Constant.INTENT_MODE;
import static com.geli.m.config.Constant.INTENT_SHOP_ID;

/**
 * Created by Steam_l on 2018/1/3.
 *
 * 优惠券管理
 */
public class CouponManagerActivity extends BaseActivity implements View.OnClickListener {

    /** 标题名称 */
    @BindView(R.id.tv_title_name)
    TextView mTvTitle;
    /** 已使用 */
    @BindView(R.id.tv_coupon_tab_cannotuse)
    TextView mTvCannotuse;
    /** 未使用 */
    @BindView(R.id.tv_coupon_tab_canuse)
    TextView mTvCanuse;
    /** 已过期 */
    @BindView(R.id.tv_coupon_tab_expired)
    TextView mTvExpired;

    @BindView(R.id.vp_coupon_content)
    ViewPager mVpContent;
    /** 标签列表布局 */
    @BindView(R.id.mtl_tablayout)
    MyTabLayout mMtlLayout;
    /** 点击将不使用优惠价 */
    @BindView(R.id.tv_coupon_clicknouse)
    TextView mTvCouponNouse;

    /** 布局被复用 -- 我的优惠券 */
    public static final int COUPONMODE_MANAGER = 1 << 0;
    /** 布局被复用 -- 选择优惠券 */
    public static final int COUPONMODE_SELECT = 1 << 1;

    String mGoodsId;

    /** 当前的功能是 -- 我的优惠券/选择优惠券 */
    int mCurrMode = COUPONMODE_MANAGER;

    int mShopId = -1;

    @Override
    protected int getResId() {
        return R.layout.activity_coupon;
    }

    @Override
    protected void init() {
        super.init();
        Intent intent = getIntent();
        if (intent != null) {
            mCurrMode = intent.getIntExtra(INTENT_MODE, mCurrMode);
            mGoodsId = intent.getStringExtra(INTENT_GOODS_ID);
            mShopId = intent.getIntExtra(INTENT_SHOP_ID, -1);
        }
    }

    @Override
    protected void initData() {
        CouponAdapter adapter = new CouponAdapter(getSupportFragmentManager(), mCurrMode, mGoodsId);
        mVpContent.setAdapter(adapter);

        if (mCurrMode == COUPONMODE_SELECT) {                           /* 选择优惠券 */
            mTvTitle.setText(Utils.getString(R.string.title_selectcoupon));
            mTvCouponNouse.setVisibility(View.VISIBLE);
            mMtlLayout.setVisibility(View.GONE);

        } else {                                                        /* 我的优惠券 */
            mTvTitle.setText(Utils.getString(R.string.title_mycoupon));
            mTvCouponNouse.setVisibility(View.GONE);                    // 点击将不使用优惠卷
            mMtlLayout.setVisibility(View.VISIBLE);
            mMtlLayout.setViewPager(mVpContent);
            mVpContent.setOffscreenPageLimit(adapter.getCount());
        }
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.iv_title_back, R.id.tv_coupon_clicknouse, R.id.tv_coupon_tab_expired, R.id.tv_coupon_tab_cannotuse, R.id.tv_coupon_tab_canuse})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_title_back:
                finish();
                break;

            case R.id.tv_coupon_clicknouse:                     /* 点击 -- "点击将不使用优惠价" */
                notUseCoupon();
                break;

            case R.id.tv_coupon_tab_cannotuse:                  /* 未使用 */
                mVpContent.setCurrentItem(1);
                break;

            case R.id.tv_coupon_tab_canuse:                     /* 已使用 */
                mVpContent.setCurrentItem(0);
                break;

            case R.id.tv_coupon_tab_expired:                    /* 已过期 */
                mVpContent.setCurrentItem(2);
                break;

            default:
                break;
        }
    }


    /**
     * 不使用
     */
    private void notUseCoupon() {
        Intent intent = new Intent(BROADCAST_COUPON);   // 广播告诉SubmitOrderActivity界面，然后关闭Activity
        CouponBean couponBean = new CouponBean();
        couponBean.setShop_id(mShopId);
        couponBean.setMoney("");
        couponBean.setCpl_id(-1);
        intent.putExtra(BROADCAST_DATA, couponBean);
        mContext.sendBroadcast(intent);
        finish();
    }
}
