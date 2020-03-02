package com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.bean.AreaBean;
import com.geli.m.bean.RestaurantBean;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity.LocalRestaurantActivity;
import com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_search_activity.LocalRestaurantSearchActivity;
import com.geli.m.mvp.home.index_fragment.location_activity.LocationActivity;
import com.geli.m.utils.EasyRecyclerViewUtils;
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
 * 地方食品 -- 列表
 */
public class LocalRestaurantListActivity extends MVPActivity<LocalRestaurantListPresentImpl> implements LocalRestaurantListView {

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


    /** 包裹选择种类的布局 */
    @BindView(R.id.lLayout_select_restaurantListSelect)
    LinearLayout mLLayoutSelect;


    /** 位置的信息 */
    AreaBean mAreaBean;
    private int mPage = 1;

    private int mLastOffset;
    private int mLastPosition;
    private boolean mIsShow;

//    @Override
//    protected LocalRestaurantListPresentImpl createPresent() {
//        return new LocalRestaurantListPresentImpl(this);
//    }
    @Override
    protected LocalRestaurantListPresentImpl createPresenter() {
        return new LocalRestaurantListPresentImpl(this);
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
    }

    @Override
    protected void initEvent() {
        mLLayoutSelect.setVisibility(View.GONE);
    }

    private void getIntentData() {
        AreaBean areaBean = getIntent().getParcelableExtra(INTENT_LOCATION);
        if(areaBean != null && StringUtils.isNotEmpty(areaBean.getAddress())){
            mAreaBean = areaBean;
        }
    }

    private void setView() {
        mTvTitleName.setText(getString(R.string.local_food));

        if(mAreaBean != null && StringUtils.isNotEmpty(mAreaBean.getAddress())) {
            mTvLocation.setText(mAreaBean.getAddress());
        }else {
            mTvLocation.setText(getString(R.string.locationloading));
        }

        // mTvTitleRight.setVisibility(View.GONE);
    }

    private void initErv() {
        mErvList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mErvList.setAdapterWithProgress(initAdapter());

        EasyRecyclerViewUtils.initEasyRecyclerView(mErvList, R.layout.layout_erv_empty, -1);
        setEmptyMessage();

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
                return new LocalRestaurantListViewHolder(parent, mContext);
            }
        };

        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                RestaurantBean.DataBean.LocalFoodResBean bean = (RestaurantBean.DataBean.LocalFoodResBean) mAdapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra(INTENT_BEAN, bean);
                startActivity(LocalRestaurantActivity.class, intent);
            }
        });

        return mAdapter;
    }

    /**
     * 设置列表空数据的时候的显示
     */
    private void setEmptyMessage() {
        View emptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_erv_empty,
                new LinearLayout(mContext), false);     // 为了点击事件，所以这里要填充出来获取控件

        TextView message = (TextView) emptyView.findViewById(R.id.tv_message_empty);
        message.setText(getString(R.string.empty_restaurant));
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
            R.id.tv_location_restaurantListActivity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back_restaurantListActivity:             /* 重新定位 */
                finish();
                break;

            case R.id.tv_title_right_restaurantListActivity:            /* 查询 -- 搜索食品馆 */
                startActivity(LocalRestaurantSearchActivity.class, new Intent());
                break;

            case R.id.tv_location_restaurantListActivity:               /* 重新定位 */
                Intent intent = new Intent(mContext, LocationActivity.class);
                startActivityForResult(intent, LOCATION_REQUESTCODE_RESTAURANT);
                break;
        }
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
        mErvList.setRefreshing(false);

    }

    @Override
    public void onError(String msg) {
        super.onError(msg);
        mErvList.setRefreshing(false);
    }

    @Override
    public void setRestaurantShop(RestaurantBean.DataBean bean) {
        if(bean == null){
            return;
        }

        // mTvTitleRight.setVisibility(bean.getCount() > COUNT ? View.VISIBLE : View.GONE);

        List<RestaurantBean.DataBean.LocalFoodResBean> data = bean.getLocal_food_res();
        if (mPage == 1) {
            mAdapter.clear();
            if (data != null && data.size() == 0) {
                mErvList.showEmpty();
                return;
            }
        }

        if(data != null){
//            if(mAreaBean != null && Double.valueOf(mAreaBean.getLo()) > 0 && Double.valueOf(mAreaBean.getLa()) > 0) {
//                List<RestaurantBean.DataBean.LocalFoodResBean> list = mAdapter.getAllData();
//                list.addAll(data);
//                Collections.sort(list, new RestaurantBean.DataBean.LocalFoodResBean.RestaurantComparator(mAreaBean.getLo(), mAreaBean.getLa()));
//                mAdapter.clear();
//                mAdapter.addAll(list);
//            }else {
//                mAdapter.addAll(data);
//            }
            mAdapter.addAll(data);
            if (mIsShow) {
                scrollToPosition();             // 让RecyclerView滚动到指定位置
            }

            if (data.size() < 20) {       // 本次回的数据小余 20 说明后面没有数据了
                mAdapter.stopMore();
            }
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


    private void getData(int page){

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

        mPresenter.localFoodList(map);
    }
}
