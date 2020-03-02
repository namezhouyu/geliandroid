package com.geli.m.mvp.home.mine_fragment.coupon_activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.geli.m.mvp.home.mine_fragment.coupon_activity.coupon_fragment.CouponFragment;
import java.util.ArrayList;
import java.util.List;

import static com.geli.m.config.Constant.VIEWTYPE_CANUSE;
import static com.geli.m.config.Constant.VIEWTYPE_EXPIRED;
import static com.geli.m.config.Constant.VIEWTYPE_NOUSE;
import static com.geli.m.config.Constant.VIEWTYPE_USEHISTORY;

/**
 * Created by Steam_l on 2018/1/3.
 */

public class CouponAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments;

    public CouponAdapter(FragmentManager fm, int mode, String goodsId) {
        super(fm);
        mFragments = new ArrayList<>();

        if (mode == CouponManagerActivity.COUPONMODE_SELECT) {                      /* 选择优惠券 */
            mFragments.add(CouponFragment.newInstanct(VIEWTYPE_CANUSE, goodsId));

        } else {                                                                    /* 我的优惠券 */
            mFragments.add(CouponFragment.newInstanct(VIEWTYPE_NOUSE));
            mFragments.add(CouponFragment.newInstanct(VIEWTYPE_USEHISTORY));
            mFragments.add(CouponFragment.newInstanct(VIEWTYPE_EXPIRED));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
