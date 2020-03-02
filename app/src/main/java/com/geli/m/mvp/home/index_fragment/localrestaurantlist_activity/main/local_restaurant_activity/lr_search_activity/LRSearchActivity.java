package com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity.lr_search_activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.bean.KeyWordsBean;
import com.geli.m.bean.RestaurantGoodsBean;
import com.geli.m.bean.RestaurantShopBean;
import com.geli.m.bean.SearchEntity;
import com.geli.m.coustomview.FluidLayout;
import com.geli.m.databases.SearchHistoryDBManger;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity.lr_search_activity.fragment.LRSearchBaseFragment;
import com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity.lr_search_activity.fragment.LRSearchGoodsFragment;
import com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity.lr_search_activity.fragment.LRSearchShopFragment;
import com.geli.m.utils.KeyBoardUtils;
import com.geli.m.utils.RxUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import static com.geli.m.config.Constant.INTENT_RESTAURANT_ID;
import static com.geli.m.config.Constant.INTENT_SEARCH_TYPE;
import static com.geli.m.config.Constant.Sort_Comprehensive;
import static com.geli.m.config.Constant.search_goods;
import static com.geli.m.config.Constant.search_shop;

/**
 * author:  shen
 * date:    2019/3/25
 * <p>
 * 搜索"本地食品"中的"商品/商店"
 */
public class LRSearchActivity extends MVPActivity<LRSearchPresentImpl> implements LRSearchView {


    /** 关键字输入框 */
    @BindView(R.id.et_search_content_rSearchActivity)
    EditText mEtSearch;
    @BindView(R.id.ll_search_root_rSearchActivity)
    LinearLayout mLlSearchRoot;
    /** 取消按钮 */
    @BindView(R.id.tv_search_cancel_rSearchActivity)
    TextView mTvSearchCancel;
    /** 指示器 -- 商店/商品 */
    @BindView(R.id.mi_search_rSearchActivity)
    MagicIndicator mMiSearch;
    @BindView(R.id.vp_search_content_rSearchActivity)
    ViewPager mVpSearchContent;

    /** 包裹"热门搜索"和"历史搜索"的 布局 */
    @BindView(R.id.ll_search_hosthistserch)
    LinearLayout mLlSearchHosthistserch;
    /** 热门搜索 */
    @BindView(R.id.ll_search_hostsearch)
    LinearLayout mLlHostSearch;
    /** 热门搜索 */
    @BindView(R.id.fl_hostsearch_layout)
    FluidLayout mFlHostSearch;
    /** 历史搜索 */
    @BindView(R.id.fl_historysearch_layout)
    FluidLayout mFlHistorySearch;
    /** 历史搜索 */
    @BindView(R.id.ll_search_historysearch)
    LinearLayout mLlHistorySearch;

    /** 标签控件的"头选项" */
    private List<String> mTitleList;
    private LRSearchAdapter mLRSearchAdapter;

    /** 搜索类型 1、商品; 2、商店 */
    private String mSearchType = search_goods;

    private SearchHistoryDBManger mSearchHistoryDBManger;
    /** 上一次搜索的内容 */
    private String mTempSearch;

    private int mLfId = -1;
    private int mPage = 1;

    @Override
    protected LRSearchPresentImpl createPresenter() {
        return new LRSearchPresentImpl(this);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_r_search;
    }

    @Override
    protected void init() {
        super.init();
        mImmersionBar
                .keyboardEnable(false)
                .statusBarColor(R.color.white)
                .init();

        getIntentData();
    }



    @Override
    protected void initData() {
        mSearchHistoryDBManger = new SearchHistoryDBManger(mContext);

        setSearchTitle();                   // 设置 -- simplePagerTitleView -- 搜索标签
        setViewPager();                     // 设置 -- ViewPager -- 还绑定指示器

        setHistorySearch();                 // 从本地数据库中，获取最近10条的搜索记录!添加到"流式布局控件中"
        getHostSearch();                    // 从后台获取"热门搜索" -- 根据"搜索类型 1、商品; 2、商店"
    }

    @Override
    protected void initEvent() {
        setFluidLayoutListener();       // 设置"流式布局控件"的"控件的点击监听事件" -- 搜索
        setViewPagerListener();         // 设置 -- ViewPager的监听事件 -- 切换的时候--根据"类型"重新搜索"热词"
        setEtSearchOnEditorActionListener();  // "搜索编辑框"的监听事件
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mLfId = intent.getIntExtra(INTENT_RESTAURANT_ID, mLfId);
        mSearchType = intent.getStringExtra(INTENT_SEARCH_TYPE);
    }

    /**
     * 设置 -- simplePagerTitleView -- 搜索标签
     */
    private void setSearchTitle(){
        mTitleList = new ArrayList<>();
        mTitleList.add(Utils.getString(R.string.goods));
        mTitleList.add(Utils.getString(R.string.shop));
    }

    /**
     * 设置 -- ViewPager -- 还绑定指示器
     */
    private void setViewPager(){
        mLRSearchAdapter = new LRSearchAdapter(getSupportFragmentManager());
        mVpSearchContent.setAdapter(mLRSearchAdapter);
        mVpSearchContent.setOffscreenPageLimit(mTitleList.size());
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
                        mVpSearchContent.setCurrentItem(index);
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
        mMiSearch.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMiSearch, mVpSearchContent);
    }

    /**
     * 从后台获取"热门搜索" -- 根据"搜索类型 1、商品; 2、商店"
     */
    private void getHostSearch() {
        mPresenter.hotKeywords(mSearchType);
    }


    /**
     * 从本地数据库中，获取最近10条的搜索记录!添加到"流式布局控件中"
     */
    private void setHistorySearch() {
        Observable.create(new ObservableOnSubscribe<CheckBox>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<CheckBox> e) throws Exception {
                List<SearchEntity> historyList = mSearchHistoryDBManger.getHistoryList();
                if (null == historyList || historyList.size() == 0) {
                    mLlHistorySearch.setVisibility(View.GONE);
                    return;
                }

                List<String> historySearch = new ArrayList<>();
                for (int j = 0; j < historyList.size(); j++) {
                    historySearch.add(historyList.get(j).getInputContent());
                }

                List<TextView> view = mFlHistorySearch.getView(historySearch);
                for (int j = 0; j < view.size(); j++) {
                    CheckBox checkBox = (CheckBox) view.get(j);
                    checkBox.setTag(historyList.get(j).getInputContent());
                    e.onNext(checkBox);
                }
            }
        }).compose(RxUtils.<CheckBox>rxSchedulerHelper())
                .subscribe(new Observer<CheckBox>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CheckBox checkBox) {
                        mFlHistorySearch.addView(checkBox);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



    /**
     * 设置"流式布局控件"的"控件的点击监听事件" -- 搜索
     */
    private void setFluidLayoutListener(){
        FluidLayout.CheckBoxClickListener listener = new FluidLayout.CheckBoxClickListener() {
            @Override
            public void onCheckBoxClick(View v) {
                String content = (String) v.getTag();
                ((CheckBox) v).setChecked(false);
                mEtSearch.setText(content.trim());
                mEtSearch.setSelection(content.length());   // 光标移动到最后
                searchData(content.trim());                 // 开始搜索
                // KeyBoardUtils.openKeybord(mEtSeatch, mContext);
            }
        };
        mFlHostSearch.setCheckBoxClickListener(listener);
        mFlHistorySearch.setCheckBoxClickListener(listener);
    }

    /**
     * 设置 -- ViewPager的监听事件 -- 切换的时候--根据"类型"重新搜索"热词" -- 点击的时候，隐藏"软键盘"
     *
     */
    private void setViewPagerListener(){
        mVpSearchContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mLlHistorySearch.getVisibility() == View.VISIBLE) {
                    if (mTitleList.get(position).equals(Utils.getString(R.string.shop))) {
                        mSearchType = search_shop;
                    } else {
                        mSearchType = search_goods;
                    }
                    getHostSearch();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 点击的时候，隐藏"软键盘"
        mVpSearchContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                KeyBoardUtils.closeKeybord(mEtSearch, mContext);
                return false;
            }
        });

        if(mSearchType.equals(search_goods)){
            mVpSearchContent.setCurrentItem(0);
        }else if(mSearchType.equals(search_shop)){
            mVpSearchContent.setCurrentItem(1);
        }
    }


    /**
     * 需要注意的是
     * setOnEditorActionListener这个方法，并不是在我们点击EditText的时候触发，
     * 也不是在我们对EditText进行编辑时触发，而是在我们编辑完之后点击软键盘上的各种键才会触发。
     * 因为通过布局文件中的imeOptions可以控制软件盘右下角的按钮显示为不同按钮。
     * 所以和EditorInfo搭配起来可以实现各种软键盘的功能。
     *
     * imeOptions=”actionSearch” (xml布局中的设置) –> EditorInfo.IME_ACTION_SEARCH    这是对应的!!
     *
     */
    private void setEtSearchOnEditorActionListener(){
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String search = mEtSearch.getText().toString().trim();
                    if (TextUtils.isEmpty(search)) {
                        ToastUtils.showToast(Utils.getString(R.string.please_input_yousearch));
                        return false;
                    }
                    searchData(search);
                    return true;
                }
                return false;       //  返回false表示点击后，隐藏软键盘。返回true表示保留软键盘。
            }
        });
    }

    /**
     * 搜索
     * @param search 要搜索的"字符串"
     */
    private void searchData(String search) {
        if (mLlSearchHosthistserch.getVisibility() == View.VISIBLE) {
            mLlSearchHosthistserch.setVisibility(View.GONE);
        }

        if (!search.equals(mTempSearch)) {           /* 和上次搜索的不同 */
            saveHistory(search);
            mTempSearch = search;
            initPage();
        }

        Map<String, String> map = new HashMap<>();
        map.put("lf_id", mLfId + "");                   // 新批发id
        map.put("page", mPage + "");
        map.put("sort", Sort_Comprehensive + "");       // 1默认排序2销量
        map.put("keywords", search);                    // 搜索关键词

        if(mSearchType.equals(search_shop)){
            mPresenter.localFoodShops(map);
        }else {
            mPresenter.localFoodGoods(map);
        }
    }

    /**
     * 如果最后10次了历史输入数据中没有 ，就添加到数据库中
     * @param searchContent
     */
    private synchronized void saveHistory(String searchContent) {
        SearchEntity entity = new SearchEntity();
        if (!TextUtils.isEmpty(searchContent)) {
            entity.setInputContent(searchContent);
            entity.setCreateTime(System.currentTimeMillis());
            for (SearchEntity entity1 : mSearchHistoryDBManger.getHistoryList()) {
                if (entity1.getInputContent().equals(searchContent)) {
                    return;
                }
            }
            mSearchHistoryDBManger.add(entity);
        }
    }

    public void initPage() {
        List<LRSearchBaseFragment> fragments = mLRSearchAdapter.getFragments();
        for (LRSearchBaseFragment childe : fragments) {
            childe.mPage = 1;
        }
    }

    @OnClick({R.id.tv_search_cancel_rSearchActivity})
    public void onClickView(View v) {
        switch (v.getId()) {
            case R.id.tv_search_cancel_rSearchActivity:
                KeyBoardUtils.closeKeybord(mEtSearch, mContext);
                finish();
                break;

            default:
                break;
        }
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
    public void showHotKeywords(List<KeyWordsBean.DataEntity> data) {
        if (null == data || data.size() == 0) {
            mLlHostSearch.setVisibility(View.GONE);
            return;
        } else {
            mLlHostSearch.setVisibility(View.VISIBLE);
        }

        List<String> historySearch = new ArrayList<>();
        for (int j = 0; j < data.size(); j++) {
            historySearch.add(data.get(j).getKeyword());
        }
        mFlHostSearch.removeAllViews();
        List<TextView> view = mFlHostSearch.getView(historySearch);
        for (int j = 0; j < view.size(); j++) {
            CheckBox checkBox = (CheckBox) view.get(j);
            checkBox.setTag(data.get(j).getKeyword());
            mFlHostSearch.addView(checkBox);
        }
    }

    /**
     * 设置数据给Fragment
     * @param data
     */
    @Override
    public void setRestaurantGoods(List<RestaurantGoodsBean.DataBean> data) {
        KeyBoardUtils.closeKeybord(mEtSearch, mContext);
        List<LRSearchBaseFragment> fragments = mLRSearchAdapter.getFragments();
        for (LRSearchBaseFragment childe : fragments) {
            if (childe instanceof LRSearchGoodsFragment) {
                childe.setData(data);
            }
        }
    }

    /**
     * 设置数据给Fragment
     * @param data
     */
    @Override
    public void setRestaurantShop(List<RestaurantShopBean.DataBean> data) {
        KeyBoardUtils.closeKeybord(mEtSearch, mContext);
        List<LRSearchBaseFragment> fragments = mLRSearchAdapter.getFragments();
        for (LRSearchBaseFragment childe : fragments) {
            if(childe instanceof LRSearchShopFragment) {
                childe.setData(data);
            }
        }
    }

    public void getData() {
        String search = mEtSearch.getText().toString().trim();
        if (TextUtils.isEmpty(search)) {
            ToastUtils.showToast(Utils.getString(R.string.please_input_yousearch));
            return;
        }else {
            searchData(search);
        }
    }
}
