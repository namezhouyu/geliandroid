package com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity.lr_search_activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity.lr_search_activity.fragment.LRSearchBaseFragment;
import com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity.lr_search_activity.fragment.LRSearchGoodsFragment;
import com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity.lr_search_activity.fragment.LRSearchShopFragment;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * "搜索下显示内容的Fragment适配器"根据类型初始化，Fragment列表
 */
public class LRSearchAdapter extends FragmentPagerAdapter {

    private final List<LRSearchBaseFragment> mFragments;

    public LRSearchAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();

        mFragments.add(new LRSearchGoodsFragment());
        mFragments.add(new LRSearchShopFragment());

    }

    public List<LRSearchBaseFragment> getFragments() {
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
