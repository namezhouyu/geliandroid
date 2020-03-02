package com.geli.m.mvp.home.other.shopdetails_activity;

import com.geli.m.R;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.other.shopdetails_activity.shopdetails_fragment.ShopDetailsFragment;

import static com.geli.m.config.Constant.INTENT_CAT_ID;
import static com.geli.m.config.Constant.INTENT_IS_OPEN;
import static com.geli.m.config.Constant.INTENT_SHOP_ID;

/**
 * Created by Steam_l on 2017/12/30.
 *
 * 商店
 */
public class ShopDetailsActivity extends BaseActivity {

    /** 当前商店id */
    private String mCurrShopId;
    private String mCatId;
    private boolean isOpen = true;

    @Override
    protected int getResId() {
        return R.layout.activity_shopdetails;
    }


    @Override
    protected void init() {
        super.init();
        mImmersionBar
                .keyboardEnable(false)
                .fitsSystemWindows(false)
                .transparentStatusBar().init();

        mCurrShopId = getIntent().getStringExtra(INTENT_SHOP_ID);
        isOpen = getIntent().getBooleanExtra(INTENT_IS_OPEN, isOpen);
        mCatId = getIntent().getStringExtra(INTENT_CAT_ID);
    }

    @Override
    protected void initData() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_shopdetails_content,
                ShopDetailsFragment.newInstance(mCurrShopId, mCatId, isOpen)).commit();
    }

    @Override
    protected void initEvent() {

    }


    /* 点击返回键干的事情 */
    @Override
    public void onBackPressed() {
        if (mOnBackListener != null) {
            if (mOnBackListener.onBack()) {
                return;
            }
        }
        super.onBackPressed();
    }

    OnBackListener mOnBackListener;

    public interface OnBackListener {

        /**
         * 返回值 -- true: 点击返回键什么都不干  false：按照返回键的功能
         * @return
         */
        boolean onBack();
    }

    public void setOnBackListener(OnBackListener listener) {
        mOnBackListener = listener;
    }
}
