package com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicetype_activity.addoreditinvoice_activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.geli.m.bean.InvoiceBean;
import com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicetype_activity.addoreditinvoice_activity.personal_fragment.PersonalFragment;
import com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicetype_activity.addoreditinvoice_activity.unit_fragment.UnitFragment;
import java.util.ArrayList;
import java.util.List;

import static com.geli.m.config.Constant.INTENT_INVOICE_DATA;

/**
 * Created by Steam_l on 2018/1/8.
 */

public class InvoicePagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments;

    public InvoicePagerAdapter(FragmentManager fm, InvoiceBean.DataEntity dataEntity) {
        super(fm);

        Bundle bundle = new Bundle();
        bundle.putParcelable(INTENT_INVOICE_DATA, dataEntity);

        mFragments = new ArrayList<>();
        mFragments.add(UnitFragment.newInstance(bundle));
        mFragments.add(PersonalFragment.newInstance(bundle));
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
