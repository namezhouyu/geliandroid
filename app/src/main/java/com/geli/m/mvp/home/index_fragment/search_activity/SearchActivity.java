package com.geli.m.mvp.home.index_fragment.search_activity;

import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.bean.KeyWordsBean;
import com.geli.m.bean.OverseasGoodsOuterBean;
import com.geli.m.bean.RetailCenterBean;
import com.geli.m.bean.SearchEntity;
import com.geli.m.coustomview.FluidLayout;
import com.geli.m.databases.SearchHistoryDBManger;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.index_fragment.search_activity.fragment.SearchBaseFragment;
import com.geli.m.utils.KeyBoardUtils;
import com.geli.m.utils.RxUtils;
import com.geli.m.utils.ShowSingleToast;
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

import static com.geli.m.config.Constant.INTENT_SHOP_ID;
import static com.geli.m.config.Constant.search_goods;
import static com.geli.m.config.Constant.search_shop;

/**
 * Created by Steam_l on 2017/12/29.
 *
 * 搜索界面
 */
public class SearchActivity extends MVPActivity<SearchPresentImpl> implements View.OnClickListener, SearchView {

    /** viewPager -- 排列类型: 宫格/列表 */
    @BindView(R.id.vp_search_content)
    ViewPager mVpContent;
    /** 内容显示方式 -- true->方块 false->列表 */
    @BindView(R.id.cb_search_list_state)
    CheckBox mCbState;
    /** 指示器(商品/店铺) */
    @BindView(R.id.mi_search)
    MagicIndicator mIndicator;

    /** 包裹"搜索编辑框" 和 "取消按钮" 的 布局 */
    @BindView(R.id.ll_search_root)
    LinearLayout mLlSearchRoot;
    /** 搜索 -- 编辑框 */
    @BindView(R.id.et_search_content)
    EditText mEtSeatch;

    /** 包裹"热门搜索"和"历史搜索"的 布局 */
    @BindView(R.id.ll_search_hosthistserch)
    LinearLayout mLlHisthistSearch;
    /** 包裹 "历史搜索" 的 布局  */
    @BindView(R.id.ll_search_historysearch)
    LinearLayout mLlHistorySearch;
    /** 包裹 "热门搜索" 的 布局  */
    @BindView(R.id.ll_search_hostsearch)
    LinearLayout mLlHostSearch;
    /** "历史搜索"  */
    @BindView(R.id.fl_historysearch_layout)
    FluidLayout mFlHistorySearch;
    /** "热门搜索"  */
    @BindView(R.id.fl_hostsearch_layout)
    FluidLayout mFlHostSearch;


    /** 标签控件的"头选项" */
    private List<String> mTitleList;

    public static final String INTENT_SEARCHTYPE = "intent_searchtype";

    /** 批零 */
    public static final int SEARCH_TYPE_P = 1;
    /** 厂家 */
    public static final int SEARCH_TYPE_C = 2;
    /** 海外 */
    public static final int SEARCH_TYPE_H = 3;
    /** 商家 */
    public static final int SEARCH_TYPE_S = 4;

    /** 当前要查询的类型 -- 1、批零; 2、厂家; 3、海外; 4、商家 */
    private int currSearchType = SEARCH_TYPE_P;
    private String currShopId;
    private SearchAdapter mSearchAdapter;

    /** 上一次搜索的内容 */
    private String tempSearch;
    public static final String SEARCH_TRANSITIONNAME = "search_transitionname";
    private SearchHistoryDBManger mSearchHistoryDBManger;

    /** 搜索类型 1、商品; 2、商店 */
    private String hostType = search_goods;

    private List<ListChangeListener> mListenerList = new ArrayList<>();

    @Override
    protected int getResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void init() {
        super.init();
        mImmersionBar
                .keyboardEnable(false)
                .statusBarColor(R.color.white)
                .init();
        currSearchType = getIntent().getIntExtra(INTENT_SEARCHTYPE, currSearchType);
    }

    @Override
    protected void initData() {
        ViewCompat.setTransitionName(mLlSearchRoot, SEARCH_TRANSITIONNAME);
        mSearchHistoryDBManger = new SearchHistoryDBManger(mContext);

        setSearchTitle();                   // 设置 -- simplePagerTitleView -- 搜索标签
        setViewPager();                     // 设置 -- ViewPager -- 还绑定指示器

        setEnterSharedElementCallback();    // 更新共享元素?

        setHistorySearch();                 // 从本地数据库中，获取最近10条的搜索记录!添加到"流式布局控件中"
        getHostSearch();                    // 从后台获取"热门搜索" -- 根据"搜索类型 1、商品; 2、商店"
    }


    @Override
    protected void initEvent() {
        setFluidLayoutListener();       // 设置"流式布局控件"的"控件的点击监听事件" -- 搜索
        setViewPagerListener();         // 设置 -- ViewPager的监听事件 -- 切换的时候--根据"类型"重新搜索"热词"
        setCbStateListener();           // 设置，"搜索结果的排列方式" -- "宫格"或是"列表" -- 如果还"没有搜索"，就不处理
        setEtSearchOnEditorActionListener();  // "搜索编辑框"的监听事件
    }


    /**
     * 设置 -- simplePagerTitleView -- 搜索标签
     */
    private void setSearchTitle(){
        mTitleList = new ArrayList<>();

        if (currSearchType == SEARCH_TYPE_H) {          /* 海外 -- 团购、期货、现货 */
            mTitleList.add(Utils.getString(R.string.group_buy));
            mTitleList.add(Utils.getString(R.string.futures));
            mTitleList.add(Utils.getString(R.string.spot));

        } else if (currSearchType == SEARCH_TYPE_S) {    /* 商家 --  商品 */
            mTitleList.add(Utils.getString(R.string.goods));
            currShopId = getIntent().getStringExtra(INTENT_SHOP_ID);

        } else {                                         /* 1、批零; 2、厂家  --  商品、店铺 */
            mTitleList.add(Utils.getString(R.string.goods));
            mTitleList.add(Utils.getString(R.string.shop));
        }
    }

    /**
     * 设置 -- ViewPager -- 还绑定指示器
     */
    private void setViewPager(){
        mSearchAdapter = new SearchAdapter(getSupportFragmentManager(), currSearchType);
        mVpContent.setAdapter(mSearchAdapter);
        mVpContent.setOffscreenPageLimit(mTitleList.size());
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
                        mVpContent.setCurrentItem(index);
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
        ViewPagerHelper.bind(mIndicator, mVpContent);
    }

    /**
     * 更新共享元素?
     *
     * 1、setExitSharedElementCallback(SharedElementCallback)
     * 的SharedElementCallback里面的onMapSharedElements()函数在Activity exit和reenter时都会触发
     * 2、setEnterSharedElementCallback(SharedElementCallback)
     * 的SharedElementCallback里面的onMapSharedElements()函数在Activity enter和return时都会触发。
     */
    private void setEnterSharedElementCallback(){

        ActivityCompat.setEnterSharedElementCallback(SearchActivity.this, new SharedElementCallback() {
            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);

                KeyBoardUtils.openKeybord(mEtSeatch, mContext);             // 打开软键盘
                ActivityCompat.setEnterSharedElementCallback(SearchActivity.this, null);
            }
        });
    }

    /**
     * 从后台获取"热门搜索" -- 根据"搜索类型 1、商品; 2、商店"
     */
    private void getHostSearch() {
        mPresenter.hotKeywords(hostType);
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
                mEtSeatch.setText(content.trim());
                mEtSeatch.setSelection(content.length());   // 光标移动到最后
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
        mVpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mLlHisthistSearch.getVisibility() == View.VISIBLE) {
                    if (mTitleList.get(position).equals(Utils.getString(R.string.shop))) {
                        hostType = search_shop;
                    } else {
                        hostType = search_goods;
                    }
                    getHostSearch();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 点击的时候，隐藏"软键盘"
        mVpContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                KeyBoardUtils.closeKeybord(mEtSeatch, mContext);
                return false;
            }
        });
    }


    /**
     * 设置，"搜索结果的排列方式" -- "宫格"或是"列表" -- 如果还"没有搜索"，就不处理
     */
    private void setCbStateListener(){
        mCbState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 如果 -- 包裹"热门搜索"和"历史搜索"的 布局 -- 可见 -- 说明是没有"搜索到内容" -- "单选框"变回原来的"状态"
                if (mLlHisthistSearch.getVisibility() == View.VISIBLE) {
                    mCbState.setChecked(!isChecked);    // "单选框"变回原来的"状态"
                    return;
                }

                KeyBoardUtils.closeKeybord(mEtSeatch, mContext);
                notifyChange(isChecked);
            }
        });
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
        mEtSeatch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String search = mEtSeatch.getText().toString().trim();
                    if (TextUtils.isEmpty(search)) {
                        ShowSingleToast.showToast(mContext, Utils.getString(R.string.please_input_yousearch));
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
        if (mLlHisthistSearch.getVisibility() == View.VISIBLE) {
            mLlHisthistSearch.setVisibility(View.GONE);
        }

        //   if (mVpContent.getVisibility() == View.GONE) {
        //       mVpContent.setVisibility(View.VISIBLE);
        //
        //   }
        //   if (TextUtils.isEmpty(tempSearch)) {
        //       tempSearch = search;
        //       //第一次
        //       saveHistory(tempSearch);
        //   }

        if (!search.equals(tempSearch)) {           /* 和上次搜索的不同 */
            saveHistory(search);
            tempSearch = search;
            initPage();
        }

        if (currSearchType == SEARCH_TYPE_H) {
            Map<String, String> data = new HashMap();
            data.put("shop_type", currSearchType + "");
            data.put("keywords", mEtSeatch.getText().toString().trim());
            mPresenter.searchAllForOverseas(data);

        } else if (currSearchType == SEARCH_TYPE_S) {
            Map<String, String> data = new HashMap();
            data.put("shop_type", currSearchType + "");
            data.put("keywords", search);
            data.put("goods_type", "1");
            data.put("shop_id", currShopId);
            mPresenter.searchGoods(data);

        } else {
            Map<String, String> data = new HashMap();
            data.put("shop_type", currSearchType + "");
            mPresenter.searchAllForNormal(data, mEtSeatch.getText().toString().trim());
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
        List<SearchBaseFragment> fragments = mSearchAdapter.getFragments();
        for (SearchBaseFragment childe : fragments) {
            childe.page = 1;
        }
    }

    public void setData(SearchBaseFragment childe, Object object) {
        childe.setData(object);
    }


    @OnClick({R.id.tv_search_cancel})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search_cancel:
                KeyBoardUtils.closeKeybord(mEtSeatch, mContext);
                ActivityCompat.finishAfterTransition(this);
                break;

            default:
                break;
        }
    }

    @Override
    protected SearchPresentImpl createPresenter() {
        return new SearchPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
    }

    @Override
    public void onError(String msg) {
        ShowSingleToast.showToast(mContext, msg);
    }

    @Override
    public void showLoading() {
        Utils.uProgressDialog(mContext);
    }

    @Override
    public void hideLoading() {
        Utils.dismissProgress();
    }

    /* Fragment 加载更多 -- 根据标识来对号入座 */
    @Override
    public void showGoods(OverseasGoodsOuterBean dataEntity) {
        KeyBoardUtils.closeKeybord(mEtSeatch, mContext);
        List<SearchBaseFragment> fragments = mSearchAdapter.getFragments();
        for (SearchBaseFragment childe : fragments) {
            if (childe.getType() == SearchBaseFragment.TYPE_SEARCHGOODS) {
                setData(childe, dataEntity);
            }
        }
    }

    @Override
    public void showShops(RetailCenterBean dataEntity) {
        KeyBoardUtils.closeKeybord(mEtSeatch, mContext);
        List<SearchBaseFragment> fragments = mSearchAdapter.getFragments();
        for (SearchBaseFragment childe : fragments) {
            if (childe.getType() == SearchBaseFragment.TYPE_SEARCHSHOP) {
                setData(childe, dataEntity);
            }
        }
    }

    @Override
    public void showOverseas(Map<String, OverseasGoodsOuterBean> map) {
        KeyBoardUtils.closeKeybord(mEtSeatch, mContext);
        List<SearchBaseFragment> fragments = mSearchAdapter.getFragments();
        for (SearchBaseFragment childe : fragments) {
            if (childe.getGoodsType() == 4) {
                setData(childe, map.get(SearchPresentImpl.KEY_GROUP_BUY));
                childe.setData(map.get(SearchPresentImpl.KEY_GROUP_BUY));
            } else if (childe.getGoodsType() == 3) {
                setData(childe, map.get(SearchPresentImpl.KEY_FUTURES));
            } else {
                setData(childe, map.get(SearchPresentImpl.KEY_SPOT));
            }
        }
    }


    /* 热门布局中的 -- 流式布局 */
    @Override
    public void showHotKeywords(List<KeyWordsBean.DataEntity> entityList) {
        if (null == entityList || entityList.size() == 0) {
            mLlHostSearch.setVisibility(View.GONE);
            return;
        } else {
            mLlHostSearch.setVisibility(View.VISIBLE);
        }

        List<String> historySearch = new ArrayList<>();
        for (int j = 0; j < entityList.size(); j++) {
            historySearch.add(entityList.get(j).getKeyword());
        }
        mFlHostSearch.removeAllViews();
        List<TextView> view = mFlHostSearch.getView(historySearch);
        for (int j = 0; j < view.size(); j++) {
            CheckBox checkBox = (CheckBox) view.get(j);
            checkBox.setTag(entityList.get(j).getKeyword());
            mFlHostSearch.addView(checkBox);
        }
    }

    public void getData() {
        if (TextUtils.isEmpty(mEtSeatch.getText().toString().trim())) {
            ShowSingleToast.showToast(mContext, Utils.getString(R.string.please_input_yousearch));
            return;
        }
        List<SearchBaseFragment> fragments = mSearchAdapter.getFragments();
        for (SearchBaseFragment childe : fragments) {
            if (childe.isShow()) {
                Map<String, String> data = new HashMap();
                data.put("page", childe.page + "");
                data.put("shop_type", currSearchType + "");

                if (childe.getType() == SearchBaseFragment.TYPE_SEARCHSHOP) {
                    data.put("shop_name", mEtSeatch.getText().toString().trim());
                    mPresenter.searchShop(data);

                } else if (childe.getType() == SearchBaseFragment.TYPE_SEARCHGOODS) {
                    data.put("keywords", mEtSeatch.getText().toString().trim());
                    data.put("goods_type", childe.getGoodsType() + "");
                    if (!TextUtils.isEmpty(currShopId)) {
                        data.put("shop_id", currShopId);
                    }
                    mPresenter.searchGoods(data);

                } else if (childe.getType() == SearchBaseFragment.TYPE_SEARCHOVERSEAS) {
                    data.put("keywords", mEtSeatch.getText().toString().trim());
                    data.put("goods_type", childe.getGoodsType() + "");
                    mPresenter.searchGoods(data);
                }
            }
        }
    }


    public interface ListChangeListener {
        /**
         * true->方形 false->列表
         * @param state
         */
        void changeState(boolean state);
    }

    public void setListChangeObserver(ListChangeListener listener) {
        mListenerList.add(listener);
    }

    public void notifyChange(boolean state) {
        for (ListChangeListener listener : mListenerList) {
            listener.changeState(state);
        }
    }

    public void loadMore() {

    }
}
