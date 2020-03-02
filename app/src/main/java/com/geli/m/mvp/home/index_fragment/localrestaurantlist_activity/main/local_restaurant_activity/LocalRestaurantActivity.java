package com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.bean.RestaurantBean;
import com.geli.m.bean.RestaurantGoodsBean;
import com.geli.m.bean.RestaurantGoodsShopScreen;
import com.geli.m.bean.RestaurantShopBean;
import com.geli.m.config.Constant;
import com.geli.m.drawer.menudrawer.MenuLayout;
import com.geli.m.drawer.menudrawer.MenuLayoutListener;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity.local_restaurantinfo_activity.LocalRestaurantInfoActivity;
import com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity.lr_search_activity.LRSearchActivity;
import com.geli.m.mvp.home.other.goodsdetails_activity.GoodsDetailsActivity;
import com.geli.m.mvp.home.other.shopdetails_activity.ShopDetailsActivity;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.geli.m.config.Constant.INTENT_BEAN;
import static com.geli.m.config.Constant.INTENT_GOODS_ID;
import static com.geli.m.config.Constant.INTENT_RESTAURANT_ID;
import static com.geli.m.config.Constant.INTENT_SEARCH_TYPE;
import static com.geli.m.config.Constant.INTENT_SHOP_ID;
import static com.geli.m.config.Constant.Sort_Comprehensive;

/**
 * author:  shen
 * date:    2019/1/11
 *
 * 某个--本地食品
 */
public class LocalRestaurantActivity extends MVPActivity<LocalRestaurantPresentImpl> implements LocalRestaurantView {



    final int goods = 1;
    final int shop = 2;

    @BindView(R.id.dlayout_restaurantActivity)
    DrawerLayout mDrawerLayout;

    /** 用于存放抽屉(筛选)的布局 */
    @BindView(R.id.llayout_nav_restaurantActivity)
    LinearLayout mLayoutNav;

    @BindView(R.id.rlayout_head_restaurantActivity)
    RelativeLayout mRLayoutHead;

    @BindView(R.id.layout_title_restaurantActivity)
    LinearLayout mLayoutTitle;

    @BindView(R.id.iv_title_back_restaurantActivity)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title_name_restaurantActivity)
    TextView mTvTitleName;
    @BindView(R.id.iv_title_right_restaurantActivity)
    ImageView mIvTitleRight;

    /** 商品介绍布局 */
    @BindView(R.id.llayout_shopInfo_restaurantActivity)
    LinearLayout mLLinearShopInfo;
    /** 商店背景 */
    @BindView(R.id.iv_shop_back_restaurantActivity)
    ImageView mIvShopBack;
    /** 商店背景 */
    @BindView(R.id.iv_shop_back_mask_restaurantActivity)
    ImageView mIvShopBackMask;
    /** 商店图片 */
    @BindView(R.id.iv_shop_restaurantActivity)
    ImageView mIvShop;
    /** 商店描述 */
    @BindView(R.id.iv_shop_describe_restaurantActivity)
    TextView mTvShopDescribe;

    /** 排序筛选后--显示 */
    @BindView(R.id.tv_sort_restaurantActivity)
    TextView mTvSort;
    /** 切换商品/商店 */
    @BindView(R.id.tv_goodsOrShop_restaurantActivity)
    TextView mTvChangeGoodsOrShop;
    /** 筛选排序 */
    @BindView(R.id.tv_screen_restaurantActivity)
    TextView mTvScreen;

    /** 商店列表 */
    @BindView(R.id.erv_shop_restaurantActivity)
    EasyRecyclerView mErvShop;
    /** 商品列表 */
    @BindView(R.id.erv_goods_restaurantActivity)
    EasyRecyclerView mErvGoods;

    RecyclerArrayAdapter mShopAdapter;
    RecyclerArrayAdapter mGoodsAdapter;

    RestaurantBean.DataBean.LocalFoodResBean mBean = null;

    int mPage = 1;
    int mListType = shop;

    private int mLastOffset;
    private int mLastPosition;

    private boolean mIsShow;

    /** 筛选抽屉 -- 对应的控件 */
    MenuLayout mMenuLayout;

    /** 排序的数据 */
    List<RestaurantGoodsShopScreen.DataBeanX> mSortData;

    @Override
    protected LocalRestaurantPresentImpl createPresenter() {
        return new LocalRestaurantPresentImpl(this);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_restaurant_old;
    }

    @Override
    protected void initData() {

        setHeadView();
        getIntentData();
        initErv();
        setDrawable();

        if(mBean != null){
            getLocalFoodShop();
            setView(mBean);
            mErvGoods.setVisibility(View.GONE);
            mErvShop.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void initEvent() {

    }

    private void setHeadView() {

        mImmersionBar
                .fitsSystemWindows(false)           // 在BaseActivity中设置了 true (整体回向下移动一个状态栏高度)
                .keyboardEnable(true)
                // 原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，
                // 如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度  0.2f
                .statusBarDarkFont(true, 0)
                .init();

        // 状态栏透明  21以上的可以设置"状态栏透明" -- 其他的不设置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

//        else {      // 19 以上
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  // 设置透明
//        }


        /* 为了压低 "自定义标题"  */
        int statusBarHeight = getStatusBarHeight();
        View statusView = new View(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT,
                statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(Color.TRANSPARENT);
        // 添加 statusView 到布局中
        mLayoutTitle.addView(statusView, 0);// addView(ViewGroup view, index);

        /* 增高背景图片 */
        mIvShopBack.setLayoutParams(new RelativeLayout.LayoutParams(MATCH_PARENT,
                Utils.dip2px(this, 140) + statusBarHeight));
        mIvShopBackMask.setLayoutParams(new RelativeLayout.LayoutParams(MATCH_PARENT,
                Utils.dip2px(this, 140) + statusBarHeight));
    }

    /**
     * 获取状态栏的高度
     * @return
     */
    private int getStatusBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 设置 控件向上移动
     * @param view
     */
    protected void setImmerseLayout(final View view) {// view为标题栏
        if (Build.VERSION.SDK_INT >= 19) {
            final ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp.height == -2) {
                view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        lp.height = view.getHeight() + getStatusBarHeight();
                        view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + getStatusBarHeight(),
                                view.getPaddingRight(), view.getPaddingBottom());
                    }
                });
            } else {
                lp.height += getStatusBarHeight();
                view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + getStatusBarHeight(),
                        view.getPaddingRight(), view.getPaddingBottom());
            }
        }
    }

    public void getIntentData() {
        Intent intent = getIntent();
        mBean = intent.getParcelableExtra(INTENT_BEAN);
    }


    private void setView(RestaurantBean.DataBean.LocalFoodResBean bean) {
        GlideUtils.loadImg(mContext, bean.getContent(), mIvShopBack);
        GlideUtils.loadImgRect(mContext, bean.getImg(), mIvShop);
        mTvTitleName.setText(bean.getTitle());
        mTvShopDescribe.setText(bean.getNote());
    }

    private void initErv() {
        /* 商店 */
        mErvShop.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mErvShop.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.line_color),
                Utils.dip2px(mContext, 1f), 0, 0));
        mErvShop.setAdapterWithProgress(initShopAdapter());
        EasyRecyclerViewUtils.initAdapter(mShopAdapter, new EasyRecyclerViewUtils.AdapterListener() {
            @Override
            public void onMoreShow() {
                mPage++;
                getLocalFoodShop();
            }

            @Override
            public void onErrorClick() {
                mShopAdapter.resumeMore();
            }
        });
        mErvShop.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                getLocalFoodShop();
            }
        });

        mErvShop.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (recyclerView.getLayoutManager() != null) {
                    getPositionAndOffset(recyclerView.getLayoutManager());
                }
            }
        });



        /* 商品 */
        mErvGoods.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL , false));
        mErvGoods.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.line_color),
                Utils.dip2px(mContext, 1f), 0, 0));

        mErvGoods.setAdapterWithProgress(initGoodsAdapter());
        EasyRecyclerViewUtils.initAdapter(mGoodsAdapter, new EasyRecyclerViewUtils.AdapterListener() {
            @Override
            public void onMoreShow() {
                mPage++;
                getLocalFoodGoods();
            }

            @Override
            public void onErrorClick() {
                mGoodsAdapter.resumeMore();
            }
        });
        mErvGoods.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                getLocalFoodGoods();
            }
        });

        mErvGoods.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (recyclerView.getLayoutManager() != null) {
                    getPositionAndOffset(recyclerView.getLayoutManager());
                }
            }
        });


    }


    private RecyclerArrayAdapter initShopAdapter() {
        mShopAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new ShopLocalRestaurantVH(parent, mContext);
            }
        };

        mShopAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                RestaurantShopBean.DataBean bean = (RestaurantShopBean.DataBean) mShopAdapter.getItem(position);
                Intent intent = new Intent(mContext, ShopDetailsActivity.class);
                intent.putExtra(INTENT_SHOP_ID, bean.getShop_id() + "");
                mContext.startActivity(intent);
            }
        });

        return mShopAdapter;
    }

    private RecyclerArrayAdapter initGoodsAdapter() {
        mGoodsAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new GoodsLocalRestaurantVH(parent, mContext);
            }
        };

        mGoodsAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                RestaurantGoodsBean.DataBean bean = (RestaurantGoodsBean.DataBean) mGoodsAdapter.getItem(position);
                Intent intent = new Intent(mContext, GoodsDetailsActivity.class);
                intent.putExtra(INTENT_GOODS_ID, bean.getGoods_id() + "");
                mContext.startActivity(intent);
            }
        });

        return mGoodsAdapter;
    }

    /**
     * 记录RecyclerView当前位置
     *
     * @param layoutManager
     */
    private void getPositionAndOffset(RecyclerView.LayoutManager layoutManager) {

        View topView = layoutManager.getChildAt(0);         // 获取可视的第一个view
        if (topView != null) {
            mLastOffset = topView.getTop();                       // 获取与该view的顶部的偏移量
            mLastPosition = layoutManager.getPosition(topView);   // 得到该View的数组位置
        }
    }

    /**
     * 让RecyclerView滚动到指定位置
     */
    private void scrollToPosition(EasyRecyclerView view) {
        if (view.getRecyclerView().getLayoutManager() != null && mLastPosition >= 0) {
            ((LinearLayoutManager) view.getRecyclerView().getLayoutManager())
                    .scrollToPositionWithOffset(mLastPosition, mLastOffset);
        }
    }

    /**
     * 设置"抽屉" -- 筛选项
     */
    private void setDrawable() {
        mMenuLayout = new MenuLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        mMenuLayout.setLayoutParams(params);

//     * @param lf_id         食品馆id
//     * @param page
//     * @param sort	        1默认排序2销量
//     * @param keywords	    搜索关键词
//     * @param brand	        品牌id
//     * @param brands	    品牌id
        mMenuLayout.setListener(new MenuLayoutListener() {
            @Override
            public void confirm(String typeSort, Map map) {
                if(StringUtils.isNotEmpty(typeSort)){
                    mTvSort.setText(typeSort);
                }
                closeMenu();

                if(mListType == shop){
                    getLocalFoodShop(map);
                }else {
                    getLocalFoodGoods(map);
                }

            }
        });

        mLayoutNav.addView(mMenuLayout);
    }


    @Override
    public void showLoading() {
        Utils.uProgressDialog(mContext);
    }

    @Override
    public void hideLoading() {
        Utils.dismissProgress();
    }

    @OnClick({R.id.iv_title_back_restaurantActivity, R.id.tv_title_name_restaurantActivity,
            R.id.tv_screen_restaurantActivity, R.id.iv_title_right_restaurantActivity,
            R.id.tv_goodsOrShop_restaurantActivity, R.id.llayout_shopInfo_restaurantActivity})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.iv_title_back_restaurantActivity:                     /* 返回 */
                finish();
                break;

            case R.id.iv_title_right_restaurantActivity:                    /* 搜索 */
                goSearch();
                break;

            case R.id.tv_title_name_restaurantActivity:                    /* 标题 */
            case R.id.llayout_shopInfo_restaurantActivity:                 /* 商店信息布局 */
                goInfo();
                break;


            case R.id.tv_goodsOrShop_restaurantActivity:                    /* 切换商品/商店 */
                change();
                break;

            case R.id.tv_screen_restaurantActivity:                         /* 筛选 */
                screen();
                break;
        }
    }

    /**
     * 筛选
     */
    private void screen() {
        if(mSortData != null){
            openMenu();
        }else {
            if (mListType == shop) {
                mPresenter.getShopScreen(mBean.getLf_id() + "");
            } else {
                mPresenter.getGoodsScreen(mBean.getLf_id() + "");
            }
        }
    }

    /**
     * 去搜索界面
     */
    private void goSearch() {
        if(mBean != null && mBean.getLf_id() != -1){
            Intent intent = new Intent();
            intent.putExtra(INTENT_RESTAURANT_ID, mBean.getLf_id());

            if(mListType == goods){
                intent.putExtra(INTENT_SEARCH_TYPE, goods + "");
            }else {
                intent.putExtra(INTENT_SEARCH_TYPE, shop + "");
            }

            startActivity(LRSearchActivity.class, intent);
        }
    }

    /**
     * 去新批发详情
     */
    private void goInfo() {
        if(mBean != null) {
            Intent intent = new Intent();
            intent.putExtra(Constant.INTENT_RESTAURANT_ID, mBean.getLf_id());
            startActivity(LocalRestaurantInfoActivity.class, intent);
        }
    }


    /**
     * 获取新批发商店列表
     */
    private void getLocalFoodShop() {
        Map<String, String> map = new HashMap<>();
        map.put("lf_id", mBean.getLf_id() + "");
        map.put("page", mPage + "");
        map.put("sort", Sort_Comprehensive + "");
        mPresenter.localFoodShops(map);
    }

    /**
     * 获取新批发商品列表
     */
    private void getLocalFoodGoods() {
        Map<String, String> map = new HashMap<>();
        map.put("lf_id", mBean.getLf_id() + "");
        map.put("page", mPage + "");
        map.put("sort", Sort_Comprehensive + "");
        mPresenter.localFoodGoods(map);
    }

    /**
     * 获取新批发商店列表
     */
    private void getLocalFoodShop(Map<String, String> map) {
        map.put("lf_id", mBean.getLf_id() + "");
        map.put("page", mPage + "");
        if(!map.containsKey("sort")){
            map.put("sort", Sort_Comprehensive + "");
        }
        mPresenter.localFoodShops(map);
    }

    /**
     * 获取新批发商品列表
     */
    private void getLocalFoodGoods(Map<String, String> map) {
        map.put("lf_id", mBean.getLf_id() + "");
        map.put("page", mPage + "");
        if(!map.containsKey("sort")){
            map.put("sort", Sort_Comprehensive + "");
        }
        mPresenter.localFoodGoods(map);
    }

    private void change() {

        if(mBean != null){
            mPage = 1;
            mSortData = null;
            if(mListType == goods){
                getLocalFoodShop();
                mListType = shop;
                mErvGoods.setVisibility(View.GONE);
                mErvShop.setVisibility(View.VISIBLE);
                mTvChangeGoodsOrShop.setText(Utils.getString(R.string.change_goods));
            }else {
                getLocalFoodGoods();
                mListType = goods;
                mErvShop.setVisibility(View.GONE);
                mErvGoods.setVisibility(View.VISIBLE);
                mTvChangeGoodsOrShop.setText(Utils.getString(R.string.change_shop));
            }
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        mIsShow = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        mIsShow = false;
    }

    @Override
    public void setRestaurantShop(List<RestaurantShopBean.DataBean> data) {
        if(data == null){
            return;
        }

        if (mPage == 1) {
            mShopAdapter.clear();
        }

        if(data != null){
            mShopAdapter.addAll(data);
            if (mIsShow) {
                scrollToPosition(mErvShop);             // 让RecyclerView滚动到指定位置
            }

            if (data.size() < 20) {       // 本次回的数据小余 20 说明后面没有数据了
                mShopAdapter.stopMore();
            }
        }

    }

    @Override
    public void setRestaurantGoods(List<RestaurantGoodsBean.DataBean> data) {
        if(data == null){
            return;
        }

        if (mPage == 1) {
            mGoodsAdapter.clear();
        }

        if(data != null){
            mGoodsAdapter.addAll(data);
            if (mIsShow) {
                scrollToPosition(mErvGoods);             // 让RecyclerView滚动到指定位置
            }

            if (data.size() < 20) {       // 本次回的数据小余 20 说明后面没有数据了
                mGoodsAdapter.stopMore();
            }
        }

    }

    @Override
    public void setGoodsShopScreen(List<RestaurantGoodsShopScreen.DataBeanX> data) {
        mMenuLayout.setAdapterData(mSortData = data);
        openMenu();
    }


    public void closeMenu() {
        if(mDrawerLayout != null){
            mDrawerLayout.closeDrawer(GravityCompat.END);
        }
    }
    public void openMenu() {
        if(mDrawerLayout != null){
            mDrawerLayout.openDrawer(GravityCompat.END);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mLayoutNav)) {
                closeMenu();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
