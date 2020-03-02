package com.geli.m.mvp.home.mine_fragment.accountmanagement_activity;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.mvp.base.MVPActivity;
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

/**
 * author:  shen
 * date:    2018/11/13
 */
public class AccountManagementActivity extends MVPActivity<AccountManagementPresentImpl>
        implements AccountManagementView {

    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title_name)
    TextView mTvTitleName;
    @BindView(R.id.tv_title_right)
    TextView mTvTitleRight;
    @BindView(R.id.rl_title_rootlayout)
    RelativeLayout mRlTitleRootlayout;


    AccountManagementAdapter mAdapter = null;
    @BindView(R.id.mi_am)
    MagicIndicator mIndicator;
    @BindView(R.id.vp_ap_content_am)
    ViewPager mVpApContent;

    /**
     * 标签控件的"头选项"
     */
    private List<String> mTitleList;

    @Override
    protected int getResId() {
        return R.layout.activity_am;
    }

    @Override
    protected void initData() {
        mTvTitleName.setText(Utils.getString(R.string.account_management));
    }

    @Override
    protected void initEvent() {
        initTitleList();
        setViewPager();

    }

    private void initTitleList() {
        mTitleList = new ArrayList<>();
        mTitleList.add(getString(R.string.ap_already_opened));
        mTitleList.add(getString(R.string.ap_not_opened));

    }

    /**
     * 设置 -- ViewPager -- 还绑定指示器
     */
    private void setViewPager() {
        mAdapter = new AccountManagementAdapter(getSupportFragmentManager());
        mVpApContent.setAdapter(mAdapter);
        mVpApContent.setOffscreenPageLimit(mTitleList.size());
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(Utils.getColor(R.color.text_color));    // 未选中的颜色
                simplePagerTitleView.setSelectedColor(Utils.getColor(R.color.zhusediao));   // 选中的颜色
                simplePagerTitleView.setText(mTitleList.get(index));
                // simplePagerTitleView.setPadding(Utils.dip2px(mContext, 15), Utils.dip2px(mContext, 10), Utils.dip2px(mContext, 15), Utils.dip2px(mContext, 5));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mVpApContent.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {                      // 指示器的，样式
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setRoundRadius(Utils.dip2px(mContext, 2));
                linePagerIndicator.setColors(Utils.getColor(R.color.zhusediao));
                return linePagerIndicator;
            }
        });
        commonNavigator.setAdjustMode(true);
        mIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mIndicator, mVpApContent);
    }

    @Override
    protected AccountManagementPresentImpl createPresenter() {
        return new AccountManagementPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {

    }


    @OnClick(R.id.iv_title_back)
    public void onViewClicked() {
        finish();
    }
}
