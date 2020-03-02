package com.geli.m.mvp.home.mine_fragment.mywallet_activity.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.api.UrlSet;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.FwqTimeBean;
import com.geli.m.bean.WalletBalanceBean;
import com.geli.m.bean.WalletBean;
import com.geli.m.coustomview.ErrorView;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity.BankListActivity;
import com.geli.m.mvp.home.mine_fragment.mywallet_activity.expensesrecord_activity.ExpensesRecordActivity;
import com.geli.m.mvp.home.mine_fragment.mywallet_activity.main.fargment.ExpensesRecordAllFragment;
import com.geli.m.mvp.home.other.WebViewActivity;
import com.geli.m.mvp.home.other.pay_activity.PayActivity;
import com.geli.m.utils.LogUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.StringUtils;
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

import static com.geli.m.BuildConfig.GL_DOMAIN;
import static com.geli.m.BuildConfig.GL_PAY_URL;
import static com.geli.m.config.Constant.INTENT_IS_SHOW_TITLE;
import static com.geli.m.config.Constant.INTENT_LINKS;
import static com.geli.m.config.Constant.jm_recharge;
import static com.geli.m.config.Constant.jm_withdraw;

/**
 * Created by Steam_l on 2018/1/18.
 *
 *
 * 我的钱包
 */

public class MyWalletActivity extends MVPActivity<WalletPresentImpl> implements View.OnClickListener, MyWalletView {

    public static final String WALLET_BALANCE_DETAIL_TYPE = "WalletBalanceDetailType";

    /** 全部"记录" */
    public static final String ALL = "全部";
    /** 支出 */
    public static final String EXPENDITURE = "支出";
    /** 转入 */
    public static final String SHIFT_TO = "转入";


    /** 包裹"整个标题"的布局 */
    @BindView(R.id.rl_title_rootlayout)
    RelativeLayout mRlTitleLayout;
    /** "返回" */
    @BindView(R.id.iv_title_back)
    ImageView mIvBack;
    /** 标题名称 */
    @BindView(R.id.tv_title_name)
    TextView mTvTitle;


    /** 显示余额的文本 */
    @BindView(R.id.tv_mywallet_money)
    TextView mTvMoney;
    /** 消费记录 -- 显示页面 */
    @BindView(R.id.vp_expenses_record)
    ViewPager mVpExpensesRecord;
    /** 消费记录 -- 指示器 */
    @BindView(R.id.mi_expenses_record)
    MagicIndicator mIndicator;
    /** 错误页面 的 "根布局" */
    @BindView(R.id.layout_error_rootlayout)
    ErrorView mErrorView;


    public List<String> mTitleList = new ArrayList<>();
    private CommonNavigatorAdapter mNavigatorAdapter;
    public ArrayList<ExpensesRecordAllFragment> mFragments = new ArrayList<>();
    private FragmentStatePagerAdapter mAdapter;

    @Override
    protected int getResId() {
        return R.layout.activity_mywallet;
    }

    @Override
    protected void initData() {
        setTitleView();
        mImmersionBar.statusBarColor(R.color.zhusediao).init();
    }

    @Override
    protected void initEvent() {
        initTitleList();
        initErrorView();
        initViewPage();
        initFragment();
    }

    private void setTitleView() {
        mRlTitleLayout.setBackgroundColor(Utils.getColor(R.color.zhusediao));
        mTvTitle.setTextColor(Color.WHITE);
        mTvTitle.setText(Utils.getString(R.string.title_mywallet));
        mIvBack.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.nav_btn_white_fanhui));
    }

    /**
     * 初始化"标题"
     */
    private void initTitleList(){
        if(mTitleList != null){
            mTitleList.clear();
        }else {
            mTitleList = new ArrayList<>();
        }

        mTitleList.add(ALL);
        mTitleList.add(EXPENDITURE);
        mTitleList.add(SHIFT_TO);
    }

    private void initErrorView(){
        mErrorView.setVisibility(View.GONE);
        mErrorView.setClickRefreshListener(new ErrorView.ClickRefreshListener() {
            @Override
            public void clickRefresh() {
                getWalletBalance();
            }
        });
    }

    private void initViewPage(){
        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        mNavigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {     /* 设置"指示器文本" */
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(Utils.getColor(R.color.text_color));
                simplePagerTitleView.setSelectedColor(Utils.getColor(R.color.zhusediao));
                int i = 0;
                for (String title : mTitleList) {
                    if (i == index) {
                        simplePagerTitleView.setText(title);
                        break;
                    }
                    i++;
                }
                simplePagerTitleView.setPadding(Utils.dip2px(mContext, 15), Utils.dip2px(mContext, 10),
                        Utils.dip2px(mContext, 15), Utils.dip2px(mContext, 5));

                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mVpExpensesRecord.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {              /* 设置"指示器" */
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setRoundRadius(Utils.dip2px(mContext, 2));
                linePagerIndicator.setColors(Utils.getColor(R.color.zhusediao));
                return linePagerIndicator;
            }
        };

        commonNavigator.setAdjustMode(true); //ture 即标题平分屏幕宽度的模式
        commonNavigator.setAdapter(mNavigatorAdapter);
        mIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mIndicator, mVpExpensesRecord);
    }

    /**
     * 初始化 Fragment
     */
    private void initFragment(){

        mFragments.add(ExpensesRecordAllFragment.newInstance(ALL));             // 全部"记录"
        mFragments.add(ExpensesRecordAllFragment.newInstance(EXPENDITURE));     // 支出
        mFragments.add(ExpensesRecordAllFragment.newInstance(SHIFT_TO));        // 转入


        mAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {

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
                if (object instanceof ExpensesRecordAllFragment) {
                    if (!mTitleList.contains(((ExpensesRecordAllFragment) object).getDetailType())) {
                        return POSITION_NONE;
                    }
                    return mFragments.indexOf(object) == -1 ? POSITION_NONE : mFragments.indexOf(object);
                }
                return POSITION_UNCHANGED;
            }

        };
        mNavigatorAdapter.notifyDataSetChanged();
        mVpExpensesRecord.setAdapter(mAdapter);

        mVpExpensesRecord.setOffscreenPageLimit(3);
    }

    private void getWalletBalance(){

         // mPresenter.getWallet(GlobalData.getUser_id());                  // 之前的没有独立出钱包的接口
         mPresenter.getWalletBalance(GlobalData.getUser_id());

        // 金融列表测试账号的 user_id=6115b26b227bf8aa70345756aae81cdd
        // mPresenter.getWallet("6115b26b227bf8aa70345756aae81cdd");        // 之前的没有独立出钱包的接口
        // mPresenter.getWalletBalance("6115b26b227bf8aa70345756aae81cdd");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWalletBalance();
    }


    @OnClick({R.id.iv_title_back, R.id.tv_mywallet_bank, R.id.tv_mywallet_expenses_record, R.id.tv_mywallet_recharge, R.id.tv_mywallet_withdraw})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;

            case R.id.tv_mywallet_bank:                                            /* 银行卡  */
                startActivity(BankListActivity.class, new Intent().putExtra(BankListActivity.INTENT_TYPE, BankListActivity.TYPE_VIEW));
                break;

            case R.id.tv_mywallet_expenses_record:                                 /* 消费记录 */
                startActivity(ExpensesRecordActivity.class, new Intent());
                break;

            case R.id.tv_mywallet_recharge:                                         /* 充值 */
                recharge();
                break;

            case R.id.tv_mywallet_withdraw:                                         /* 提现 */
                withdraw();
                break;

            default:
                break;
        }
    }


    @Override
    protected WalletPresentImpl createPresenter() {
        return new WalletPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
    }

    @Override
    public void onError(String msg) {
        ShowSingleToast.showToast(mContext, msg);
        mErrorView.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        Utils.uProgressDialog(mContext);
    }

    @Override
    public void hideLoading() {
        Utils.dismissProgress();
    }

    @Override
    public void showWallet(WalletBean.DataEntity dataEntity) {
        mTvMoney.setText(dataEntity.getUser_money());           // 这是之前的接口
    }

    @Override
    public void showWalletBalance(WalletBalanceBean bean) {
        //mTvMoney.setText("￥" + bean.getData());
        if(StringUtils.isNotEmpty(bean.getData()))
            mTvMoney.setText(Utils.getString(R.string.overseas_list_money, bean.getData()));
        else
            mTvMoney.setText(Utils.getString(R.string.the_balance_is_temporarily_unavailable));
    }

    /**
     * 充值
     */
    private void recharge() {
        mPresenter.getFwqTime(jm_recharge);
    }

    /**
     * 提现
     */
    private void withdraw() {
        mPresenter.getFwqTime(jm_withdraw);
    }

    @Override
    public void getFwqTimeSuccess(String jm, FwqTimeBean data) {
        int time = data.getTime();

        String sp = "m";
        // String jm = jm;            // 1格利钱包 2修改支付密码
        String dm = GL_DOMAIN;
        String un = "";
        String tm = "";

        un = PayActivity.encode(GlobalData.getPhone());
        tm = PayActivity.encode(time + "");

        // 组合 链接
        String url = GL_PAY_URL + UrlSet.specialLogin + "?" +
                "sp=" + sp + "&" +
                "jm=" + jm + "&" +
                "dm=" + dm + "&" +
                "un=" + un + "&" +
                "tm=" + tm;

        LogUtils.i("url:" + url);
        // url = "http://www.jianshu.com/p/16713361bbd3";
        // url = "http://m.gelifood.com/goods/4806";
        Intent intent = new Intent(mContext, WebViewActivity.class);
        // Intent intent = new Intent(mContext, MyWebViewActivity.class);
        intent.putExtra(INTENT_LINKS, url);
        intent.putExtra(INTENT_IS_SHOW_TITLE, false);
        startActivity(intent);
    }
// if (mErrorView.getVisibility() == View.VISIBLE) {
//            mErrorView.setVisibility(View.GONE);
//}
}
