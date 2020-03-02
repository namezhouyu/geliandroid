package com.geli.m.mvp.home.index_fragment.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.geli.m.R;
import com.geli.m.bean.AreaBean;
import com.geli.m.bean.AtsBean;
import com.geli.m.bean.City;
import com.geli.m.bean.base.IndexBaseBean;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.base.BaseView;
import com.geli.m.mvp.base.MVPFragment;
import com.geli.m.mvp.home.index_fragment.location_activity.LocationActivity;
import com.geli.m.mvp.home.index_fragment.message_activity.MessageActivity;
import com.geli.m.mvp.home.index_fragment.scan_activity.ScanActivity;
import com.geli.m.mvp.home.main.HomeActivity;
import com.geli.m.mvp.home.other.WebViewActivity;
import com.geli.m.mvp.home.other.goodsdetails_activity.GoodsDetailsActivity;
import com.geli.m.mvp.home.other.shopdetails_activity.ShopDetailsActivity;
import com.geli.m.utils.LocationUtil;
import com.geli.m.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.ImmersionBar;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.geli.m.config.Constant.ACTION_MY_LOCATION;
import static com.geli.m.config.Constant.BROADCAST_DATA;
import static com.geli.m.config.Constant.INTENT_GOODS_ID;
import static com.geli.m.config.Constant.INTENT_LINKS;
import static com.geli.m.config.Constant.INTENT_LOCATION;
import static com.geli.m.config.Constant.INTENT_SHOP_ID;


/**
 * Created by Steam_l on 2017/12/21.
 */

public class IndexFragment extends MVPFragment<GetIndexInfoPresentImpl>
        implements BaseView, SwipeRefreshLayout.OnRefreshListener, IndexView, View.OnClickListener {

    /** 标题栏 */
    @BindView(R.id.toolbar_index)
    Toolbar mToolbar;
    /** 存放所有模块的列表 */
    @BindView(R.id.erv_indexf)
    EasyRecyclerView mErvContent;
    /** 标题栏中的 -- 定位详情(定位中.../xxx市)  */
    @BindView(R.id.tv_top_location_index)
    TextView mTvLocation;

    private LayoutInflater mInflater;

    /** 比较器/排序器 */
    private LocationActivity.ComparatorUser mComparatorUser;
    /** 城市列表 */
    private List<City> mCityList;
    private RecyclerArrayAdapter<IndexBaseBean> mAdapter;

    public static AreaBean mAreaBean = new AreaBean();

    @Override
    protected GetIndexInfoPresentImpl createPresent() {
        return new GetIndexInfoPresentImpl(this);
    }

    @Override
    public int getResId() {
        return R.layout.fragment_index;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImmersionBar.setTitleBar(getActivity(), mToolbar);
    }

    @Override
    protected void init() {
        super.init();

        /*  Fragment具有属性retainInstance，默认值为false。
            当设备旋转时，fragment会随托管activity一起销毁并重建。
            调用setRetainInstance(true)方法可保留fragment

            如果设置了，那么当一个活动被重新创建时，片段的生命周期将会略有不同：
            @link onDestroy() 将不会被调用（但是@link onDestroy() 仍然存在将会是，因为片段正在脱离它当前的活动）。
            @link onCreate(Bundle)将不会被调用，因为片段没有被重新创建。
            @link onAttach(活动) 和@link onActivityCreate(Bundle) 仍然被称为。*/
        setRetainInstance(true);
    }

    @Override
    protected void initData() {
        onRefresh();
        mInflater = LayoutInflater.from(mContext);
        setLocation();                              // 设置定位信息
        setErvLayoutAndItemDecoration();            // 设置"列表"的布局，分隔线
        setErvAdapter();                            // 设置"列表"的"适配器"
    }

    @Override
    protected void initEvent() {
        setErvListener();                           // 设置"列表"的监听事件
    }

    /**
     * 设置"列表"的监听事件
     */
    private void setErvListener(){
        mErvContent.setRefreshListener(this);       // 下拉刷新监听

        // 滑动监听
        mErvContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (mListener != null) {
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int firstCompletelyVisibleItemPosition = manager.findFirstCompletelyVisibleItemPosition();
                    int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();

                    // 就是"列表"当前最上面显示的"item"不是"第一个"  --  第一个隐蔽了，就停止动画(轮询播放图片)
                    if (firstCompletelyVisibleItemPosition > 1) {
                        mListener.stop();
                    } else {
                        if (firstVisibleItemPosition <= 1) {        // ???
                            mListener.start();
                        }
                    }
                }
            }
        });

    }


    /**
     * 设置定位信息
     */
    private void setLocation(){
        mComparatorUser = new LocationActivity.ComparatorUser();
        mTvLocation.setText(Utils.getString(R.string.locationloading));
        if (mCityList == null) {
            new MyAsyncTask().execute();
        }
    }


    /**
     * 设置"列表"的布局，分隔线
     */
    private void setErvLayoutAndItemDecoration(){
        mErvContent.setLayoutManager(new LinearLayoutManager(mContext));
        final Paint paint = new Paint();
        paint.setColor(Utils.getColor(R.color.line_color));
        paint.setAntiAlias(true);
        mErvContent.addItemDecoration(new RecyclerView.ItemDecoration() {
            int decoration = Utils.dip2px(mContext, 5);

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
                for (int i = 0; i < parent.getChildCount(); i++) {
                    View childAt = parent.getChildAt(i);
                    if (parent.getChildAdapterPosition(childAt) < 1
                            || parent.getChildAdapterPosition(childAt) >= mAdapter.getAllData().size() - 1) {
                        continue;
                    }
                    int top = childAt.getBottom();
                    int left = childAt.getLeft();
                    int right = childAt.getRight();
                    int bottom = childAt.getBottom() + decoration;
                    c.drawRect(left, top, right, bottom, paint);
                }
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if (position > 1 && position < mAdapter.getAllData().size() - 1) {
                    outRect.bottom = decoration;
                } else {
                    outRect.bottom = 0;
                }
            }
        });
    }


    /**
     * 设置"列表"的"适配器"
     */
    private void setErvAdapter(){
        mAdapter = new RecyclerArrayAdapter<IndexBaseBean>(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                BaseViewHolder viewHolder = new BaseViewHolder(parent, R.layout.itemview_bank) {};
                viewHolder = mPresenter.getViewHolder(parent, viewType,
                        viewHolder, mContext, IndexFragment.this);
                return viewHolder;
            }

            @Override
            public int getViewType(int position) {                          // 得到"item类型编号"
                IndexBaseBean indexBaseBean = getItem(position);
                Integer view_type = mPresenter.getViewType(indexBaseBean);
                if (view_type != null)
                    return view_type;
                return super.getViewType(position);
            }
        };

        mErvContent.setAdapterWithProgress(mAdapter);
    }


    /**
     * 有几个模块的点击事件是指向这里的
     *
     * @param context
     * @param data
     */
    public static void bannerOnClicker(Context context, AtsBean data) {
        if (data == null || data.getSkip_priority() == null) {
            return;
        }

        if (data.getSkip_priority().equals(AtsBean.URL)) {              /* 连接 */
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra(INTENT_LINKS, data.getAts_link());
            context.startActivity(intent);

        } else if (data.getSkip_priority().equals(AtsBean.GOODS)) {     /* 商品详情详情 */
            Intent intent = new Intent(context, GoodsDetailsActivity.class);
            intent.putExtra(INTENT_GOODS_ID, data.getGoods_id() + "");
            context.startActivity(intent);

        } else if (data.getSkip_priority().equals(AtsBean.SHOP)) {      /* 店铺 */
            Intent intent = new Intent(context, ShopDetailsActivity.class);
            intent.putExtra(INTENT_SHOP_ID, data.getShop_id() + "");
            context.startActivity(intent);
        }
    }

    BannerListener mListener;

    public interface BannerListener {
        void start();
        void stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mListener != null) {
            mListener.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mListener != null) {
            mListener.stop();
        }
    }

    public void setBannerListener(BannerListener listener) {
        this.mListener = listener;
    }

    /**
     * 初始化高德定位相关信息. -- 定位成功(回调)
     */
    private void initLocal() {
        LocationUtil.getInstance().startLocation(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == AMapLocation.LOCATION_SUCCESS) {//定位成功
                        mAreaBean = new AreaBean();

                        mAreaBean.setCityName(aMapLocation.getCity());
                        mAreaBean.setLa(aMapLocation.getLatitude() + "");
                        mAreaBean.setLo(aMapLocation.getLongitude() + "");
//                        mAreaBean.setAddress(aMapLocation.getProvince() + aMapLocation.getCity() +
//                                aMapLocation.getStreet() +
//                                aMapLocation.getStreetNum());

                        String address = aMapLocation.getCity() + aMapLocation.getDistrict();
                        mAreaBean.setAddress(address);
                        // mAreaBean.setAddress(aMapLocation.getCity());

                        // System.out.println("=====initLocal========" + mLa + "=====" + mLo + "==========");

                        mTvLocation.setText(address);
                        List<City> cityList = accurateSearch(mAreaBean.getCityName(), mCityList);
                        for (City city : cityList) {
                            if (city.getArea_name().equals(mAreaBean.getCityName())) {
                                mAreaBean.setId(city.getArea_id());

                                Intent intent = new Intent(ACTION_MY_LOCATION);
                                intent.putExtra(BROADCAST_DATA, mAreaBean);
                                mContext.sendBroadcast(intent);

                                onRefresh();
                                return;
                            }
                        }
                    } else {
                        mTvLocation.setText(Utils.getString(R.string.location_failed));
                    }
                }
            }
        });
    }

    /**
     * 精确查询,用于根据定位结果查找本地数据集中的城市
     * @param name
     * @param list
     * @return
     */
    public List<City> accurateSearch(String name, List<City> list) {
        List<City> results = new ArrayList<>();
        for (City city : list) {
            if (city.getArea_name().equals(name)) {
                results.add(city);
            }
        }
        return results;
    }


    /**
     * 解析"本地文件 -- 城市"
     */
    class MyAsyncTask extends AsyncTask<String, Integer, List<City>> {

        @Override
        protected List<City> doInBackground(String... params) {
            final Gson gson = new Gson();
            String s = getFromAssets("city.json");
            List<City> citys = new ArrayList<>();
            try {
                citys = gson.fromJson(s, new TypeToken<List<City>>() {}.getType());
            } catch (Exception e) {
            }
            return citys;
        }

        @Override
        protected void onPostExecute(List<City> cities) {
            super.onPostExecute(cities);
            Collections.sort(cities, mComparatorUser);              // 排序
            mCityList = cities;
            requestPermissions();
        }
    }

    /**
     * 拿权限 -- 获取定位信息
     */
    private void requestPermissions() {
        RxPermissions rxPermissions = new RxPermissions((BaseActivity) mContext);
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Observer<Boolean>() {
                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onNext(Boolean aBoolean) {
                                    if (aBoolean) {
                                        initLocal();        // 初始化定位信息
                                    } else {
                                        mTvLocation.setText(Utils.getString(R.string.location_openlocapermission));
                                        Utils.showMissPermissionDialog(mContext, Utils.getString(R.string.location), Utils.getString(R.string.message_location));
                                    }
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
     * 获取本地json数据 -- 从Assets中获取
     * @param fileName
     * @return
     */
    public String getFromAssets(String fileName) {  // 获取本地json数据
        String result = "";
        try {
            InputStream in = getResources().getAssets().open(fileName);
            int lenght = in.available();            // 获取文件的字节数
            byte[] buffer = new byte[lenght];       // 创建byte数组
            in.read(buffer);                        // 将文件中的数据读到byte数组中
            result = new String(buffer, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public void showLoading() {
        mErvContent.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mErvContent.setRefreshing(false);
    }

    /* 刷新 -- 获取首页的数据 */
    @Override
    public void onRefresh() {
        mPresenter.getIndexInfo(mAreaBean);
    }


    public View getView(int resId) {
        return mInflater.inflate(resId, new RelativeLayout(mContext), false);
    }

    public void addDividingLineView(int resId) {
        // ll_rootlayout.addView(getView(resId));
    }


    /**
     * String FACTORY = "factory";//厂家直供
     * String SELL = "sell";//批零中心
     * String ATS = "ats";//轮播图
     * String RECOMMEND_GOODS = "recommend_goods";//商品推荐
     * String INTEREST_GOODS = "interest_goods";//推荐商品
     * String BRAND = "brand";//品牌街
     * String ADV = "adv";//广告
     *
     * @param data
     */
    @Override
    public void showIndexInfo(final List<IndexBaseBean> data) {
        mAdapter.clear();
        mAdapter.addAll(data);
    }

    /** 定位"请求码" */
    public static final int LOCATION_REQUESTCODE_INDEX = 100;

    @OnClick({R.id.iv_index_top_mess, R.id.iv_index_top_scan, R.id.tv_top_location_index})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_index_top_mess:
                startActivity(MessageActivity.class, new Intent());
                break;

            case R.id.iv_index_top_scan:                   /* 二维码扫描 */
                gotoScan();
                break;

            case R.id.tv_top_location_index:               /* 标题中的 -- 定位 */
                Intent intent = new Intent(mContext, LocationActivity.class);
                intent.putExtra(INTENT_LOCATION, mAreaBean);
                startActivityForResult(intent, LOCATION_REQUESTCODE_INDEX);
                break;

            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOCATION_REQUESTCODE_INDEX && resultCode == Activity.RESULT_OK) {

            mAreaBean = data.getParcelableExtra(INTENT_LOCATION);

            if (TextUtils.isEmpty(mAreaBean.getCityName())) {
                mTvLocation.setText(Utils.getString(R.string.location_failed));
            } else {
                mTvLocation.setText(mAreaBean.getAddress());
                onRefresh();
            }
        }
    }

    /**
     * 二维码扫描
     */
    public void gotoScan() {
        RxPermissions rxPermissions = new RxPermissions((HomeActivity) mContext);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onNext(Boolean aBoolean) {
                                    if (aBoolean) {
                                        Utils.startActivity(mContext, ScanActivity.class);
                                    } else {
                                        Utils.showMissPermissionDialog(mContext,
                                                Utils.getString(R.string.carme)
                                                + Utils.getString(R.string.and)
                                                + Utils.getString(R.string.read), Utils.getString(R.string.scan_erweima));
                                    }
                               }

                               @Override
                               public void onError(Throwable e) {

                               }

                               @Override
                               public void onComplete() {

                               }
                           });
    }
}
