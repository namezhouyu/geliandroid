package com.geli.m.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geli.m.R;
import com.geli.m.api.UrlSet;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.ui.fragment.ImageFragment;
import com.geli.m.utils.StringUtils;

import java.util.List;

import static com.geli.m.BuildConfig.GL_IMAGE_URL;


public class ShowImageActivity extends BaseActivity {
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index"; //当前点击的图片位置
    public static final String EXTRA_IMAGE_URLS = "image_urls";  //整个图片集合

    private ViewPager mViewPager;
    private int pagerPosition;
    private TextView indicator;
    private RelativeLayout indicatorRl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_show_image;
    }

    @Override
    protected void init() {
        super.init();
        mImmersionBar
                .reset()
                .init();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, mViewPager.getCurrentItem());
    }

    private void init(Bundle savedInstanceState) {
        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        List<String> urls = getIntent().getStringArrayListExtra(
                EXTRA_IMAGE_URLS);
        mViewPager = (ViewPager) findViewById(R.id.pager);

        InnerPagerAdapter mAdapter = new InnerPagerAdapter(
                getSupportFragmentManager(), urls);
        mViewPager.setAdapter(mAdapter);
        indicator = (TextView) findViewById(R.id.indicator);
        indicatorRl = (RelativeLayout) findViewById(R.id.indicator_rl);
        indicatorRl.setBackgroundColor(Color.argb(80, 0, 0, 0)); //背景透明度
        mViewPager.setOffscreenPageLimit(urls.size() + 1);
        if (urls.size() == 1 && pagerPosition == 0) {
            indicatorRl.setVisibility(View.GONE);
        } else {
            indicator.setText(1 + "/" + mViewPager.getAdapter().getCount());
        }
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                // 更新下标
                indicator.setText(arg0 + 1 + "/" + mViewPager.getAdapter().getCount());
            }

        });
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        mViewPager.setCurrentItem(pagerPosition);
    }


    public class InnerPagerAdapter extends FragmentPagerAdapter {
        public List<String> fileList;

        public InnerPagerAdapter(FragmentManager fm, List<String> fileList) {
            super(fm);
            // TODO Auto-generated constructor stub
            this.fileList = fileList;
        }

        @Override
        public Fragment getItem(int position) {
            String url = fileList.get(position);
            if (StringUtils.isNotEmpty(url)) {
                url = url.startsWith(UrlSet.HTTP)|| url.startsWith(UrlSet.HTTPS) ? url : GL_IMAGE_URL + url;
            }
            return ImageFragment.newInstance(url);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return fileList.size();
        }

    }
}