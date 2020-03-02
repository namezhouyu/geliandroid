package com.geli.m.mvp.home.mine_fragment.accountmanagement_activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.geli.m.mvp.home.mine_fragment.accountmanagement_activity.fragment.AMFragment;
import java.util.ArrayList;
import java.util.List;

import static com.geli.m.config.Constant.AP_Already_Opened;
import static com.geli.m.config.Constant.AP_Not_Opened;

/**
 * Created by Steam_l on 2017/12/29.
 *
 * "搜索下显示内容的Fragment适配器"根据类型初始化，Fragment列表
 */
public class AccountManagementAdapter extends FragmentPagerAdapter {

    private final List<AMFragment> mFragments;

    public AccountManagementAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();

        mFragments.add(AMFragment.newInstance(AP_Already_Opened));
        mFragments.add(AMFragment.newInstance(AP_Not_Opened));


    }

    public List<AMFragment> getFragments() {
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
