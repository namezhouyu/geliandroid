package com.geli.m.mvp.home.mine_fragment.myorder_activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.main.HomeActivity;
import com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.main.MyOrderFragment;
import com.geli.m.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import static com.geli.m.config.Constant.INTENT_POSITION;
import static com.geli.m.config.Constant.STATE_AFTERMARKET;
import static com.geli.m.config.Constant.STATE_ALL;
import static com.geli.m.config.Constant.STATE_EVALUATION;
import static com.geli.m.config.Constant.STATE_PAY;
import static com.geli.m.config.Constant.STATE_RECEIVING;
import static com.geli.m.config.Constant.STATE_SHIP;

/**
 * Created by Steam_l on 2018/1/8.
 */

public class MyOrderActivity extends BaseActivity implements View.OnClickListener {

    /** 指示器 */
    @BindView(R.id.mi_myorder)
    MagicIndicator mIndicator;
    /** ViewPager */
    @BindView(R.id.vp_myorder_content)
    ViewPager mVpContent;
    /** 标题 */
    @BindView(R.id.tv_title_name)
    TextView mTvTitle;

    private List<String> mTitleList;
    private List<Fragment> mFragments;

    /** 当前的"下标" */
    private int mCurrPosition = -1;


    @Override
    protected int getResId() {
        return R.layout.activity_myorder;
    }

    @Override
    protected void init() {
        super.init();
        mCurrPosition = getIntent().getIntExtra(INTENT_POSITION, mCurrPosition);
    }

    @Override
    protected void initData() {

        mTvTitle.setText(Utils.getString(R.string.title_myorder));

        setTitleList();
        setFragmentList();
        setVpAdapter();
        setIndicator();

        ViewPagerHelper.bind(mIndicator, mVpContent);               // 指示器和vp绑定
        if (mCurrPosition != -1) {
            mVpContent.setCurrentItem(mCurrPosition);
        }

        mVpContent.setOffscreenPageLimit(mFragments.size());        // 预加载fragment数量
    }




    @Override
    protected void initEvent() {

    }

    /**
     * 设置fragment标题列表
     */
    private void setTitleList() {
        mTitleList = new ArrayList<>();
        mTitleList.add(Utils.getString(R.string.all));
        mTitleList.add(Utils.getString(R.string.pre_payment));
        mTitleList.add(Utils.getString(R.string.to_be_delivered));
        mTitleList.add(Utils.getString(R.string.to_be_received));
        mTitleList.add(Utils.getString(R.string.be_evaluated));
        mTitleList.add(Utils.getString(R.string.aftermarket));
    }

    private void setFragmentList() {
        mFragments = new ArrayList<>();
        mFragments.add(MyOrderFragment.newInstance(STATE_ALL));
        mFragments.add(MyOrderFragment.newInstance(STATE_PAY));
        mFragments.add(MyOrderFragment.newInstance(STATE_SHIP));
        mFragments.add(MyOrderFragment.newInstance(STATE_RECEIVING));
        mFragments.add(MyOrderFragment.newInstance(STATE_EVALUATION));
        mFragments.add(MyOrderFragment.newInstance(STATE_AFTERMARKET));
    }

    /**
     * 设置ViewPager的适配器
     */
    private void setVpAdapter() {
        mVpContent.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });
    }

    /**
     * 设置"指示器"
     */
    private void setIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitleList.size();
            }

            /* 指示器上的文字 */
            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(Utils.getColor(R.color.text_color));    // 未选中
                simplePagerTitleView.setSelectedColor(Utils.getColor(R.color.zhusediao));   // 选中
                simplePagerTitleView.setText(mTitleList.get(index));
                simplePagerTitleView.setPadding(Utils.dip2px(mContext, 15), Utils.dip2px(mContext, 10), Utils.dip2px(mContext, 15), Utils.dip2px(mContext, 5));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mVpContent.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            /* 下面的一横 */
            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setRoundRadius(Utils.dip2px(mContext, 2));
                linePagerIndicator.setColors(Utils.getColor(R.color.zhusediao));
                return linePagerIndicator;
            }
        });
        mIndicator.setNavigator(commonNavigator);
    }

    @OnClick({R.id.iv_title_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                startActivity(HomeActivity.class, new Intent());
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(HomeActivity.class, new Intent());
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}
