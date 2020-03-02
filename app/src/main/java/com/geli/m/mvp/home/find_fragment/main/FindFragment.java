package com.geli.m.mvp.home.find_fragment.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.geli.m.R;
import com.geli.m.bean.FindCatBean;
import com.geli.m.coustomview.ErrorView;
import com.geli.m.mvp.base.MVPFragment;
import com.geli.m.mvp.home.find_fragment.findlist_fragment.FindListFragment;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;
import com.gyf.barlibrary.ImmersionBar;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

/**
 * Created by Steam_l on 2017/12/22.
 */

public class FindFragment extends MVPFragment<FindCatPresentImpl> implements FindView {

    public static final String RECEIVER_CAT = "receiver_cat";

    @BindView(R.id.toolbar_find)
    Toolbar mToolbar;
    @BindView(R.id.tv_title_name)
    TextView mTvName;
    @BindView(R.id.iv_title_back)
    ImageView mIvBack;

    @BindView(R.id.vp_find_content)
    ViewPager mVpContent;
    @BindView(R.id.mi_find)
    MagicIndicator mIndicator;

    @BindView(R.id.layout_error_rootlayout)
    ErrorView mErrorView;

    boolean isShow;

    public LinkedHashMap<String, String> mTitleList = new LinkedHashMap<>();
    public ArrayList<FindListFragment> mFragments = new ArrayList<>();
    private CommonNavigatorAdapter mNavigatorAdapter;
    private FragmentStatePagerAdapter mAdapter;


    BroadcastReceiver mCatReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(RECEIVER_CAT)) {
                getCatList();
            }
        }
    };


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImmersionBar.setTitleBar(getActivity(), mToolbar);
    }

    @Override
    public int getResId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void initData() {
        setReceicer();                      // 设置广播接受者
        setView();
        setVp();
    }

    @Override
    protected void initEvent() {
        mErrorView.setClickRefreshListener(new ErrorView.ClickRefreshListener() {
            @Override
            public void clickRefresh() {
                getCatList();
            }
        });
    }

    private void setReceicer() {
        IntentFilter filter = new IntentFilter(RECEIVER_CAT);
        mContext.registerReceiver(mCatReceiver, filter);
    }


    private void setView() {
        mErrorView.setVisibility(View.GONE);
        mIvBack.setVisibility(View.GONE);
        mTvName.setText(Utils.getString(R.string.find));
    }

    private void setVp() {
        mNavigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitleList.size();
            }

            // 指示器的名字
            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(Utils.getColor(R.color.text_color));
                simplePagerTitleView.setSelectedColor(Utils.getColor(R.color.zhusediao));
                int i = 0;
                for (String title : mTitleList.keySet()) {
                    if (i == index) {
                        simplePagerTitleView.setText(title);
                        break;
                    }
                    i++;
                }
                simplePagerTitleView.setPadding(Utils.dip2px(mContext, 15), Utils.dip2px(mContext, 10), Utils.dip2px(mContext, 15), Utils.dip2px(mContext, 5));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mVpContent.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            // 指示器
            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setRoundRadius(Utils.dip2px(mContext, 2));
                linePagerIndicator.setColors(Utils.getColor(R.color.zhusediao));
                return linePagerIndicator;
            }
        };
        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        commonNavigator.setAdapter(mNavigatorAdapter);
        mIndicator.setNavigator(commonNavigator);                   // 指示器
        ViewPagerHelper.bind(mIndicator, mVpContent);               // 指示器和Vp绑定
    }



    @Override
    public void onResume() {
        super.onResume();
        isShow = true;
        getCatList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mCatReceiver != null){
            mContext.unregisterReceiver(mCatReceiver);
        }
    }


    private void getCatList() {
        if (isShow && getUserVisibleHint()) {
            mPresenter.getCatList();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        getCatList();
    }


    @Override
    protected FindCatPresentImpl createPresent() {
        return new FindCatPresentImpl(this);
    }


    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
    }

    @Override
    public void onError(String msg) {
        ShowSingleToast.showToast(mContext, msg);
        if (mTitleList.size() == 0) {
            mErrorView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public LinkedHashMap<String, String> getTitles() {
        return mTitleList;
    }

    @Override
    public ArrayList<FindListFragment> getFragments() {
        return mFragments;
    }


    @Override
    public void showCatList(List<FindCatBean.DataEntity> entityList) {
        if (mErrorView.getVisibility() == View.VISIBLE) {
            mErrorView.setVisibility(View.GONE);
        }

        if (mTitleList.size() == 0) {                                   /* 第一次 */
            setVpWhenTitleListEmpty(entityList);
        } else {
            setVpWhenTitleListNotEmpty();
        }
    }


    /**
     * 当 -- mTitleList.size() == 0 -- 第一次
     * @param entityList
     */
    private void setVpWhenTitleListEmpty(List<FindCatBean.DataEntity> entityList) {
        for (FindCatBean.DataEntity entity : entityList) {
            mTitleList.put(entity.getCat_name(), entity.getCat_id() + "");
        }
        Set<String> strings = mTitleList.keySet();
        for (String title : strings) {
            String cat_id = mTitleList.get(title);
            mFragments.add(FindListFragment.newInstance(cat_id));
        }

        mAdapter = new FragmentStatePagerAdapter(getChildFragmentManager()) {


            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public int getItemPosition(Object object) {
                if (object instanceof FindListFragment) {
                    if (!mTitleList.values().contains(((FindListFragment) object).getCatId())) {
                        return POSITION_NONE;      // 不相同 -2
                    }
                    return mFragments.indexOf(object) == -1 ? POSITION_NONE : mFragments.indexOf(object);
                }
                return POSITION_UNCHANGED;
            }

        };
        mNavigatorAdapter.notifyDataSetChanged();
        mVpContent.setAdapter(mAdapter);
        mVpContent.setOffscreenPageLimit(mFragments.size());
    }


    /**
     * 不是第二次
     */
    private void setVpWhenTitleListNotEmpty() {
        int index = 0;
        for (String cat_id : mTitleList.values()) {
            FindListFragment fragment = mFragments.get(index);
            fragment.setCatId(cat_id);
            index++;
        }
        mAdapter.notifyDataSetChanged();
        mNavigatorAdapter.notifyDataSetChanged();

        /* 显示的都重新获取数据 */
        for (FindListFragment fragment : mFragments) {
            if (fragment.isShow && fragment.getUserVisibleHint()) {
                fragment.getData();
            }
        }
    }



}
