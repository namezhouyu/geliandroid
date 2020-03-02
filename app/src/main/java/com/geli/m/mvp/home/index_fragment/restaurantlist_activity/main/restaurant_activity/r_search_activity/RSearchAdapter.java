package com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.restaurant_activity.r_search_activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.restaurant_activity.r_search_activity.fragment.RSearchBaseFragment;
import com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.restaurant_activity.r_search_activity.fragment.RSearchGoodsFragment;
import com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.restaurant_activity.r_search_activity.fragment.RSearchShopFragment;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * "搜索下显示内容的Fragment适配器"根据类型初始化，Fragment列表
 */
public class RSearchAdapter extends FragmentPagerAdapter {

    private final List<RSearchBaseFragment> mFragments;

    public RSearchAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();

        mFragments.add(new RSearchGoodsFragment());
        mFragments.add(new RSearchShopFragment());

    }

    public List<RSearchBaseFragment> getFragments() {
        return mFragments;
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
