package com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.bean.AreaBean;
import com.geli.m.bean.RestaurantAddrArrangeBean;
import com.geli.m.bean.RestaurantAddrBean;
import com.geli.m.bean.RestaurantBean;
import com.geli.m.bean.RestaurantSortBean;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.index_fragment.location_activity.LocationActivity;
import com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.restaurant_activity.RestaurantActivity;
import com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.restaurant_search_activity.RestaurantSearchActivity;
import com.geli.m.popup.addr.AddrPopup;
import com.geli.m.popup.sort.SortPopup;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.ResourceUtil;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.geli.m.config.Constant.INTENT_BEAN;
import static com.geli.m.config.Constant.INTENT_LOCATION;
import static com.geli.m.config.Constant.type_market;

/**
 * author:  shen
 * date:    2019/1/10
 * <p>
 * 新批发列表
 */
public class RestaurantListActivity extends MVPActivity<RestaurantListPresentImpl> implements RestaurantListView {

    /** 定位"请求码" */
    public static final int LOCATION_REQUESTCODE_RESTAURANT = 101;
    /** 限定多少个才可以显示"搜索" */
    public static final int COUNT = 50;

    @BindView(R.id.toolbar_restaurant_restaurantListActivity)
    Toolbar mToolbarRestaurant;
    @BindView(R.id.iv_title_back_restaurantListActivity)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title_name_restaurantListActivity)
    TextView mTvTitleName;
    @BindView(R.id.tv_title_right_restaurantListActivity)
    TextView mTvTitleRight;

    @BindView(R.id.tv_location_restaurantListActivity)
    TextView mTvLocation;

    @BindView(R.id.erv_list_restaurantListActivity)
    EasyRecyclerView mErvList;
    RecyclerArrayAdapter mAdapter;

    /** 包裹选择地址的布局 */
    @BindView(R.id.lLayout_addr_restaurantListSelect)
    LinearLayout mLLayoutAddrSelect;
    /** 选择地址--文本 */
    @BindView(R.id.tv_addr_restaurantListSelect)
    TextView mTvAddrSelect;
    /** 选择地址--上下箭头 */
    @BindView(R.id.iv_addr_restaurantListSelect)
    ImageView mIvAddrSelect;

    /** 包裹选择种类的布局 */
    @BindView(R.id.lLayout_sort_restaurantListSelect)
    LinearLayout mLLayoutSortSelect;
    /** 选择种类--文本 */
    @BindView(R.id.tv_sort_restaurantListSelect)
    TextView mTvSortSelect;
    /** 选择种类--上下箭头 */
    @BindView(R.id.iv_sort_restaurantListSelect)
    ImageView mIvSortSelect;

    /** 位置的信息 */
    AreaBean mAreaBean;

    private int mPage = 1;

    private int mLastOffset;
    private int mLastPosition;
    private boolean mIsShow;


    /** 选择地址窗口 */
    AddrPopup mAddrPopup;
    /** 选择种类窗口(新批发类型) */
    SortPopup mSortPopup;

    long mAddrPopupCloseTime = -1;
    long mSortPopupCloseTime = -1;

    RestaurantAddrArrangeBean mAddrBean;
    List<RestaurantSortBean.DataBean> mSortBeanList;


    int mSelectCityId = -1;
    int mSelectSortId = -1;

    @Override
    protected RestaurantListPresentImpl createPresenter() {
        return new RestaurantListPresentImpl(this);
    }


    @Override
    public int getResId() {
        return R.layout.activity_restaurantlist;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void initData() {
        getIntentData();
        setView();
        initErv();
        // initReceiver();
        mErvList.setRefreshing(true);
        getData(mPage);
        getPopupData();

        initLocation();
    }



    @Override
    protected void initEvent() {

    }



    private void getIntentData() {
        AreaBean areaBean = getIntent().getParcelableExtra(INTENT_LOCATION);
        if (areaBean != null && StringUtils.isNotEmpty(areaBean.getAddress())) {
            mAreaBean = areaBean;
        }
    }

    private void setView() {
        mTvTitleName.setText(getString(R.string.restaurant));

        if (mAreaBean != null && StringUtils.isNotEmpty(mAreaBean.getAddress())) {
            mTvLocation.setText(mAreaBean.getAddress());
        } else {
            mTvLocation.setText(getString(R.string.locationloading));
        }

        // mTvTitleRight.setVisibility(View.GONE);
    }

    private void initErv() {
        mErvList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mErvList.setAdapterWithProgress(initAdapter());

        EasyRecyclerViewUtils.initEasyRecyclerView(mErvList, -1, -1);

        EasyRecyclerViewUtils.initAdapter(mAdapter, new EasyRecyclerViewUtils.AdapterListener() {
            @Override
            public void onMoreShow() {
                mPage++;
                getData(mPage);
            }

            @Override
            public void onErrorClick() {
                mAdapter.resumeMore();
            }
        });

        mErvList.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                getData(mPage);
            }
        });

        mErvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (recyclerView.getLayoutManager() != null) {
                    getPositionAndOffset(recyclerView.getLayoutManager());
                }
            }
        });

    }

    private RecyclerArrayAdapter initAdapter() {
        mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new RestaurantListViewHolder(parent, mContext);
            }
        };

        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                RestaurantBean.DataBean.LocalFoodResBean bean = (RestaurantBean.DataBean.LocalFoodResBean) mAdapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra(INTENT_BEAN, bean);
                startActivity(RestaurantActivity.class, intent);
            }
        });

        return mAdapter;
    }

    private void initLocation() {
        mTvLocation.setVisibility(View.GONE);

        if (mAreaBean != null &&
                StringUtils.isNotEmpty(mAreaBean.getLo()) &&
                StringUtils.isNotEmpty(mAreaBean.getLa()) &&
                Double.valueOf(mAreaBean.getLo()) > 0 &&
                Double.valueOf(mAreaBean.getLa()) > 0) {
            mTvAddrSelect.setText(mAreaBean.getCityName());
        }else {
            mTvAddrSelect.setText(ResourceUtil.getStringFromResources(R.string.no_positioning));
        }
    }

//    /**
//     * 广播过滤 -- 定位
//     */
//    private void initReceiver() {
//        IntentFilter filter = new IntentFilter(ACTION_MY_LOCATION);      // 过滤自定义广播 -- "定位成功"
//        mContext.registerReceiver(mReceiver, filter);
//    }


//    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            if (intent.getAction().equals(ACTION_MY_LOCATION)) {                         /* 定位成功 */
//                setLocationData(intent);
//            }
//        }
//
//        public void setLocationData(Intent locationData) {
//            mAreaBean = locationData.getParcelableExtra(BROADCAST_DATA);
//            mTvLocation.setText(mAreaBean.getAddress());
//            mPage = 1;
//            getData(mPage);
//        }
//    };

    @OnClick({R.id.iv_title_back_restaurantListActivity,
            R.id.tv_title_right_restaurantListActivity,
            R.id.tv_location_restaurantListActivity,
            R.id.lLayout_addr_restaurantListSelect,
            R.id.lLayout_sort_restaurantListSelect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back_restaurantListActivity:             /* 重新定位 */
                finish();
                break;

            case R.id.tv_title_right_restaurantListActivity:            /* 查询 -- 搜索食品馆 */
                startActivity(RestaurantSearchActivity.class, new Intent());
                break;

            case R.id.tv_location_restaurantListActivity:               /* 重新定位 */
                Intent intent = new Intent(mContext, LocationActivity.class);
                startActivityForResult(intent, LOCATION_REQUESTCODE_RESTAURANT);
                break;

            case R.id.lLayout_addr_restaurantListSelect:               /* 选择地址 */
                showAddrDialog();
                break;

            case R.id.lLayout_sort_restaurantListSelect:               /* 选择种类 */
                showSortDialog();
                break;
        }
    }


    /**
     * 显示选择地址窗口
     */
    private void showAddrDialog() {

        if(mAddrPopup == null){
            mAddrPopup = new AddrPopup(this, 1) {

                @Override
                protected void selectAddr(PopupWindow popupWindow,
                                          RestaurantAddrBean.DataBean selectProvince,
                                          RestaurantAddrBean.DataBean selectCity) {
                    if(selectCity.getArea_name().contains("北京")){
                        mTvAddrSelect.setText(selectCity.getArea_name());
                    }else {
                        mTvAddrSelect.setText(selectProvince.getArea_name() + selectCity.getArea_name());
                    }
                    mSelectCityId = selectCity.getArea_id();
                    popupWindow.dismiss();
                    getData(mPage = 1);
                }

                @Override
                protected void setDismissListenerI() {
                    mIvAddrSelect.setImageResource(R.drawable.icon_zhankai);
                    mAddrPopupCloseTime = System.currentTimeMillis();
                }
            };
            mAddrPopup.setData(mAddrBean);

        }else {
            if(mAddrPopup.getPopupWindow().isShowing()){
                mAddrPopup.getPopupWindow().dismiss();
                return;
            }else {
                if (System.currentTimeMillis() - mAddrPopupCloseTime < 200) {
                    return ;
                }
            }
        }

        mIvAddrSelect.setImageResource(R.drawable.icon_shouhui);
        mAddrPopup.showAsDropDown(mLLayoutAddrSelect, 0,  Utils.dip2px(mContext, 7));
    }

    /**
     * 显示选择种类窗口
     */
    private void showSortDialog() {

        if(mSortPopup == null){
            mSortPopup = new SortPopup(this, 1) {
                @Override
                protected void selectItemText(PopupWindow popupWindow, RestaurantSortBean.DataBean sortBean) {
                    mTvSortSelect.setText(sortBean.getType_name());
                    mSelectSortId = sortBean.getType_id();
                    popupWindow.dismiss();
                    getData(mPage = 1);
                }

                @Override
                protected void setDismissListenerI() {
                    mIvSortSelect.setImageResource(R.drawable.icon_zhankai);
                    mSortPopupCloseTime = System.currentTimeMillis();
                }
            };
            mSortPopup.setData(mSortBeanList);
        }else {
            if(mSortPopup.getPopupWindow().isShowing()){
                mSortPopup.getPopupWindow().dismiss();
                return;
            }else {
                if (System.currentTimeMillis() - mSortPopupCloseTime < 200) {
                    return ;
                }
            }
        }
        mIvSortSelect.setImageResource(R.drawable.icon_shouhui);
        mSortPopup.showAsDropDown(mLLayoutSortSelect, 0,  Utils.dip2px(mContext, 7));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOCATION_REQUESTCODE_RESTAURANT && resultCode == Activity.RESULT_OK) {

            mAreaBean = data.getParcelableExtra(INTENT_LOCATION);

            if (TextUtils.isEmpty(mAreaBean.getCityName())) {
                mTvLocation.setText(Utils.getString(R.string.location_failed));
            } else {
                mTvLocation.setText(mAreaBean.getAddress());
                mPage = 1;
                mErvList.setRefreshing(true);
                getData(mPage);
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
    public void onDestroy() {
        super.onDestroy();
//        if(mReceiver != null){
//            mContext.unregisterReceiver(mReceiver);
//        }
    }

    @Override
    public void onSuccess(String msg) {
        super.onSuccess(msg);

    }

    @Override
    public void onError(String msg) {
        super.onError(msg);
        if(mErvList.getSwipeToRefresh().isRefreshing()){
            mErvList.getSwipeToRefresh().setRefreshing(false);
        }
    }

    @Override
    public void setRestaurantShop(RestaurantBean.DataBean bean) {
        if(mErvList.getSwipeToRefresh().isRefreshing()){
            mErvList.getSwipeToRefresh().setRefreshing(false);
        }

        if (bean == null ) {
            mAdapter.clear();
            return;
        }else {
            List<RestaurantBean.DataBean.LocalFoodResBean> data = bean.getLocal_food_res();
            if (mPage == 1) {
                mAdapter.clear();
                if (data != null && data.size() == 0) {
                    mErvList.showEmpty();
                    return;
                }
            }
            if(data != null){
                mAdapter.addAll(data);
                if(data != null){
                    if (mIsShow) {
                        scrollToPosition();             // 让RecyclerView滚动到指定位置
                    }

                    if (data.size() < 20) {             // 本次回的数据小余 20 说明后面没有数据了
                        mAdapter.stopMore();
                    }
                }
            }
        }

    }

    @Override
    public void setAddrPopup(RestaurantAddrArrangeBean data) {
        mAddrBean = data;
    }

    @Override
    public void setSortBean(List<RestaurantSortBean.DataBean> data) {
        mSortBeanList = data;
        if(data != null && data.size() == 0){
            mLLayoutSortSelect.setVisibility(View.GONE);
        }
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
    private void scrollToPosition() {
        if (mErvList.getRecyclerView().getLayoutManager() != null && mLastPosition >= 0) {
            ((LinearLayoutManager) mErvList.getRecyclerView().getLayoutManager())
                    .scrollToPositionWithOffset(mLastPosition, mLastOffset);
        }
    }


    private void getData(int page) {
        if(!mErvList.getSwipeToRefresh().isRefreshing()){
            mErvList.getSwipeToRefresh().setRefreshing(true);
        }

        Map<String, String> map = new HashMap<>();
        map.put("keywords", "");
        map.put("page", page + "");
        map.put("type", type_market);

        if (mAreaBean != null &&
                StringUtils.isNotEmpty(mAreaBean.getLo()) &&
                StringUtils.isNotEmpty(mAreaBean.getLa()) &&
                Double.valueOf(mAreaBean.getLo()) > 0 &&
                Double.valueOf(mAreaBean.getLa()) > 0) {
            map.put("lnt", mAreaBean.getLo());
            map.put("lat", mAreaBean.getLa());
        }

        if(mSelectCityId != -1){
            map.put("city_id", mSelectCityId + "");
        }
        if(mSelectSortId != -1){
            map.put("market_type", mSelectSortId + "");
        }

        mPresenter.localFoodList(map);
    }

    private void getPopupData() {
        mPresenter.marketZone();
        mPresenter.marketType();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(mAddrPopup != null && mAddrPopup.getPopupWindow().isShowing()){
                mAddrPopup.getPopupWindow().dismiss();
                return true;
            }

            if(mSortPopup != null && mSortPopup.getPopupWindow().isShowing()){
                mSortPopup.getPopupWindow().dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
