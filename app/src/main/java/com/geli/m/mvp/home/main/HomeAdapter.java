package com.geli.m.mvp.home.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.geli.m.mvp.home.cart_fragment.main.CartFragment;
import com.geli.m.mvp.home.find_fragment.main.FindFragment;
import com.geli.m.mvp.home.index_fragment.main.IndexFragment;
import com.geli.m.mvp.home.mine_fragment.main.MineFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steam_l on 2017/12/22.
 *
 * 主界面的"ViewPage"的"适配器"
 */
public class HomeAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments;

    public HomeAdapter(FragmentManager fm) {
        super(fm);

        IndexFragment indexFragment = new IndexFragment();
        FindFragment findFragment = new FindFragment();
        // LocalRestaurantListActivity restaurantFragment = new LocalRestaurantListActivity();
        CartFragment cartFragment = new CartFragment();
        MineFragment mineFragment = new MineFragment();

        mFragments = new ArrayList<>();
        mFragments.add(indexFragment);
        mFragments.add(findFragment);
        // mFragments.add(restaurantFragment);
        mFragments.add(cartFragment);
        mFragments.add(mineFragment);

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
