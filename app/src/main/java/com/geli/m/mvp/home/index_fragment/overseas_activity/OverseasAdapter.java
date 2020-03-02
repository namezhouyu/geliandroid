package com.geli.m.mvp.home.index_fragment.overseas_activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.geli.m.mvp.home.index_fragment.overseas_activity.fragment.OverseasListFragment;
import java.util.ArrayList;
import java.util.List;

import static com.geli.m.config.Constant.Goods_Type_Futures;
import static com.geli.m.config.Constant.Goods_Type_GroupBuy;
import static com.geli.m.config.Constant.Goods_Type_Spot;

/**
 * Created by Steam_l on 2017/12/29.
 */

public class OverseasAdapter extends FragmentPagerAdapter {

    private final List<OverseasListFragment> mFragments;

    public OverseasAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();
        mFragments.add(OverseasListFragment.newInstance(Goods_Type_Spot));
        mFragments.add(OverseasListFragment.newInstance(Goods_Type_GroupBuy));
        mFragments.add(OverseasListFragment.newInstance(Goods_Type_Futures));

    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public List<OverseasListFragment> getFragments() {
        return mFragments;
    }
}
