package com.geli.m.mvp.home.mine_fragment.collection_activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.mine_fragment.collection_activity.collection_list_fragment.CollectionListFragment;
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

import static com.geli.m.config.Constant.TYPE_ARTICLE;
import static com.geli.m.config.Constant.TYPE_GOODS;
import static com.geli.m.config.Constant.TYPE_SHOP;
import static com.geli.m.config.Constant.TYPE_VIDEO;

/**
 * Created by Steam_l on 2018/4/2.
 *
 * 我的收藏
 */

public class CollectionActivity extends BaseActivity implements View.OnClickListener {

    /** 标题 */
    @BindView(R.id.tv_title_name)
    TextView mTvTitle;

    /** ViewPager */
    @BindView(R.id.vp_collection_content)
    ViewPager mVpContent;

    /** 指示器 */
    @BindView(R.id.mi_collection)
    MagicIndicator mIndicator;


    private List<CollectionListFragment> mFragments;
    private List<String> mTitleList;

    @Override
    protected int getResId() {
        return R.layout.activity_collection;
    }

    @Override
    protected void initData() {
        mTvTitle.setText(Utils.getString(R.string.my_collection));
        initFragment();
        initNavigation();
    }

    @Override
    protected void initEvent() {

    }

    private void initFragment(){
        mTitleList = new ArrayList<>();
        mTitleList.add(Utils.getString(R.string.shop));
        mTitleList.add(Utils.getString(R.string.goods));
        mTitleList.add(Utils.getString(R.string.article));
        mTitleList.add(Utils.getString(R.string.video));

        mFragments = new ArrayList<>();
        mFragments.add(CollectionListFragment.newInstance(TYPE_SHOP));
        mFragments.add(CollectionListFragment.newInstance(TYPE_GOODS));
        mFragments.add(CollectionListFragment.newInstance(TYPE_ARTICLE));
        mFragments.add(CollectionListFragment.newInstance(TYPE_VIDEO));

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
     * 初始化导航
     */
    private void initNavigation() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {     /* 指示器上面的文字 */
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(Utils.getColor(R.color.text_color));
                simplePagerTitleView.setSelectedColor(Utils.getColor(R.color.zhusediao));
                simplePagerTitleView.setText(mTitleList.get(index));
                // simplePagerTitleView.setPadding(Utils.dip2px(mContext, 15), Utils.dip2px(mContext, 10), Utils.dip2px(mContext, 15), Utils.dip2px(mContext, 5));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mVpContent.setCurrentItem(index);           /* 切换Fragment */
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {                      /* 指示器 */
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setRoundRadius(Utils.dip2px(mContext, 2));
                linePagerIndicator.setColors(Utils.getColor(R.color.zhusediao));
                return linePagerIndicator;
            }
        });
        commonNavigator.setAdjustMode(true);
        mIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mIndicator, mVpContent);
    }

    @OnClick({R.id.iv_title_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;

            default:
                break;
        }
    }
}
