package com.geli.m.mvp.home.index_fragment.search_activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.geli.m.mvp.home.index_fragment.search_activity.fragment.SearchBaseFragment;
import com.geli.m.mvp.home.index_fragment.search_activity.fragment.SearchGoodsFragment;
import com.geli.m.mvp.home.index_fragment.search_activity.fragment.SearchOverseasFragment;
import com.geli.m.mvp.home.index_fragment.search_activity.fragment.SearchShopFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steam_l on 2017/12/29.
 *
 * "搜索下显示内容的Fragment适配器"根据类型初始化，Fragment列表
 */
public class SearchAdapter extends FragmentPagerAdapter {

    private final List<SearchBaseFragment> mFragments;

    public SearchAdapter(FragmentManager fm, int currSearchMode) {
        super(fm);
        mFragments = new ArrayList<>();

        if (currSearchMode == SearchActivity.SEARCH_TYPE_H) {            /* 海外 */
            mFragments.add(SearchOverseasFragment.newInstance(4));
            mFragments.add(SearchOverseasFragment.newInstance(3));
            mFragments.add(SearchOverseasFragment.newInstance(2, true));

        } else if (currSearchMode == SearchActivity.SEARCH_TYPE_S) {     /* 商家 */
            mFragments.add(new SearchGoodsFragment());

        } else {                                                        /* 1、批零; 2、厂家; */
            mFragments.add(new SearchGoodsFragment());
            mFragments.add(new SearchShopFragment());
        }
    }

    public List<SearchBaseFragment> getFragments() {
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
