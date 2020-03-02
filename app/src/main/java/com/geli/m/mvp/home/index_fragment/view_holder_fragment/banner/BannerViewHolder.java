package com.geli.m.mvp.home.index_fragment.view_holder_fragment.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.AtsBean;
import com.geli.m.bean.base.IndexBaseBean;
import com.geli.m.mvp.home.index_fragment.main.IndexFragment;
import com.geli.m.orther.GlideImageLoader;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by l on 2018/4/9.
 *
 * 轮播图 -- 使用"第三方框架Banner" 轮询播放
 */
public class BannerViewHolder extends BaseViewHolder<IndexBaseBean<List<AtsBean>>> implements
        IndexFragment.BannerListener {

    Context mContext;

    /** 轮播控件 */
    Banner mBanner;
    /** 显示页码的(文本指示器) */
    TextView tv_indicator;

    IndexFragment mFragment;

    public BannerViewHolder(ViewGroup parent, Context context, IndexFragment fragment) {
        super(parent, R.layout.fragment_banner1);
        mContext = context;
        mFragment = fragment;

        mBanner = $(R.id.home_banner1);
        tv_indicator = $(R.id.tv_banner1_indicator);

        mBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        mBanner.setImageLoader(new GlideImageLoader());
        mFragment.setBannerListener(this);                          // 设置监听事件
    }

    @Override
    public void setData(final IndexBaseBean<List<AtsBean>> data) {

        final List<String> imgs = new ArrayList<>();
        for (AtsBean child : data.getData()) {
            imgs.add(child.getAts_pic());
        }

        mBanner.setImages(imgs);
        mBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {              // 滑动的时候，修改"文本指示器"的文本
                if (tv_indicator != null) {
                    if (position > imgs.size()) {
                        tv_indicator.setText("1/" + imgs.size());
                    } else {
                        tv_indicator.setText(position + "/" + imgs.size());  /* ?什么下标从1开始的，这个要看看 */
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 点击事件
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                IndexFragment.bannerOnClicker(mContext, data.getData().get(position));
            }
        });

        mBanner.start();
    }

    @Override
    public void start() {
        if (mBanner != null) {
            mBanner.start();
        }
    }

    @Override
    public void stop() {
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }
    }

}
