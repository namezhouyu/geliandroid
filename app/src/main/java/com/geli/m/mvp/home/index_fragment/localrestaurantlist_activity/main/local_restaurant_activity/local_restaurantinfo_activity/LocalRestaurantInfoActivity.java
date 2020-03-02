package com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity.local_restaurantinfo_activity;

import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.RestaurantInfoBean;
import com.geli.m.config.Constant;
import com.geli.m.coustomview.MyEasyRecyclerView;
import com.geli.m.coustomview.webview.MJavascriptInterface;
import com.geli.m.coustomview.webview.MyWebViewClient;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.popup.listpopup.MyListPopupWindow;
import com.geli.m.ui.activity.ShowImageActivity;
import com.geli.m.utils.OpenMapUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import java.util.ArrayList;
import java.util.List;

import static com.geli.m.config.Constant.MAP_BaiDu;
import static com.geli.m.config.Constant.MAP_GaoDe;
import static com.geli.m.config.Constant.MAP_Tencent;

/**
 * author:  shen
 * date:    2019/3/12
 * <p>
 * 本地食品--详情
 */
public class LocalRestaurantInfoActivity extends MVPActivity<LocalRestaurantInfoPresentImpl> implements LocalRestaurantInfoView {


    @BindView(R.id.rl_title_rootlayout)
    RelativeLayout mRlTitleRootlayout;
    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title_name)
    TextView mTvTitleName;
    @BindView(R.id.tv_title_right)
    TextView mTvTitleRight;

    /**
     * 批发名字
     */
    @BindView(R.id.tv_restaurantName_RestaurantInfoActivity)
    TextView mTvRestaurantName;
    /**
     * 批发介绍
     */
    @BindView(R.id.tv_restaurantInfo_RestaurantInfoActivity)
    TextView mTvRestaurantInfo;
    /**
     * 包裹地址的布局
     */
    @BindView(R.id.llayout_location_RestaurantInfoActivity)
    LinearLayout mLLayoutLoction;
    /**
     * 批发地址
     */
    @BindView(R.id.tv_address_RestaurantInfoActivity)
    TextView mTvAddress;
    /**
     * 市场环境
     */
    @BindView(R.id.erv_marketEnvironment_RestaurantInfoActivity)
    MyEasyRecyclerView mErvMarketEnvironment;
    /**
     * 资质中心
     */
    @BindView(R.id.erv_qualificationCenter_RestaurantInfoActivity)
    MyEasyRecyclerView mErvQualificationCenter;

    /**
     *
     */
    @BindView(R.id.wv_RestaurantInfoActivity)
    WebView mWv;

    /**
     * 新批发id
     */
    int mRestaurantId = -1;

    /**
     * 市场环境 -- 适配器
     */
    RecyclerArrayAdapter mMEAdapter;
    /**
     * 资质中心 -- 适配器
     */
    RecyclerArrayAdapter mQCAdapter;

    @Override
    protected LocalRestaurantInfoPresentImpl createPresenter() {
        return new LocalRestaurantInfoPresentImpl(this);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_restaurant_info;
    }

    @Override
    protected void initData() {
        getIntentData();
        initErv();
        initWeb();

        if (mRestaurantId != -1)
            mPresenter.marketDetail(mRestaurantId + "");
    }


    @Override
    protected void initEvent() {

    }

    private void getIntentData() {
        Intent intent = getIntent();
        mRestaurantId = intent.getIntExtra(Constant.INTENT_RESTAURANT_ID, mRestaurantId);
    }


    private void initErv() {
        initAdapter();

        mErvMarketEnvironment.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mErvMarketEnvironment.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.transparent),
                Utils.dip2px(mContext, 10f), 0, 0));
        mErvMarketEnvironment.setAdapterWithProgress(mMEAdapter);
        // mErvMarketEnvironment.setNestedScrollingEnabled(false);
        ViewCompat.setNestedScrollingEnabled(mErvMarketEnvironment.getRecyclerView(), false);

        mErvQualificationCenter.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mErvQualificationCenter.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.transparent),
                Utils.dip2px(mContext, 10f), 0, 0));
        mErvQualificationCenter.setAdapterWithProgress(mQCAdapter);
        ViewCompat.setNestedScrollingEnabled(mErvQualificationCenter.getRecyclerView(), false);
    }


    private void initAdapter() {
        mMEAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new LocalRestaurantInfoVH(parent, mContext);
            }
        };

        mQCAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new LocalRestaurantInfoVH(parent, mContext);
            }
        };


        mMEAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ArrayList<String> imgList = (ArrayList<String>) mMEAdapter.getAllData();
                Intent intent = new Intent();
                intent.putExtra(ShowImageActivity.EXTRA_IMAGE_URLS, imgList);
                intent.putExtra(ShowImageActivity.EXTRA_IMAGE_INDEX, position);
                startActivity(ShowImageActivity.class, intent);
            }
        });

        mQCAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ArrayList<String> imgList = (ArrayList<String>) mQCAdapter.getAllData();
                Intent intent = new Intent();
                intent.putExtra(ShowImageActivity.EXTRA_IMAGE_URLS, imgList);
                intent.putExtra(ShowImageActivity.EXTRA_IMAGE_INDEX, position);
                startActivity(ShowImageActivity.class, intent);
            }
        });
    }


    /**
     * 设置web
     */
    private void initWeb() {
        WebSettings webSettings = mWv.getSettings();
        webSettings.setUseWideViewPort(true);                       // 设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setTextZoom(350);
        // webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        if (GlobalData.hasNetWork) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);     //根据cache-control决定是否从网络上取数据。
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        }
        mWv.addJavascriptInterface(new MJavascriptInterface(this), "imagelistener");
        mWv.setWebViewClient(new MyWebViewClient());
    }

    private void setView(RestaurantInfoBean.DataBean data) {
        mTvTitleName.setText(data.getTitle());

        mTvRestaurantName.setText(data.getTitle());
        mTvRestaurantInfo.setText(data.getNote());
        mTvAddress.setText(data.getAddress_detail());

        setWebViewData(data.getMarket_desc());

        mMEAdapter.clear();
        mMEAdapter.addAll(data.getEnvironment());

        mQCAdapter.clear();
        mQCAdapter.addAll(data.getQualification());
    }

    /**
     * 设置"WebView"数据
     *
     * @param webViewData 要加载的路径
     */
    public void setWebViewData(String webViewData) {
        mWv.loadDataWithBaseURL("", webViewData, "text/html", "utf-8", null);
    }

    @OnClick({R.id.iv_title_back, R.id.llayout_location_RestaurantInfoActivity})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:                                /* 返回 */
                finish();
                break;

            case R.id.llayout_location_RestaurantInfoActivity:      /* 地址 */

                mPopupWindow = new MyListPopupWindow(LocalRestaurantInfoActivity.this, 0.5f) {
                    @Override
                    protected void setDismissListenerI() {

                    }

                    @Override
                    protected void adapterItemClick(Object o) {
                        if (o instanceof String && mBean != null) {
                            String itemName = (String) o;
                            if (itemName.equals(MAP_Tencent)) {
                                OpenMapUtils.openTencent(mContext, 0, 0, null,
                                        Double.valueOf(mBean.getLatitude()),
                                        Double.valueOf(mBean.getLongitude()), mBean.getTitle(), false);
                            } else if (itemName.equals(MAP_GaoDe)) {
                                OpenMapUtils.openGaoDeMap(mContext, 0, 0, null,
                                        Double.valueOf(mBean.getLatitude()),
                                        Double.valueOf(mBean.getLongitude()), mBean.getTitle(), false);
                            } else if (itemName.equals(MAP_BaiDu)) {
                                OpenMapUtils.openBaiduMap(mContext, 0, 0, null,
                                        Double.valueOf(mBean.getLatitude()),
                                        Double.valueOf(mBean.getLongitude()), mBean.getTitle(), false);
                            }

                        }
                    }
                };

                List list = new ArrayList();
                list.add(MAP_Tencent);
                list.add(MAP_GaoDe);
                list.add(MAP_BaiDu);
                mPopupWindow.setList(list);
                mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
        }
    }

    MyListPopupWindow mPopupWindow;

    @Override
    public void onResume() {
        super.onResume();
        mWv.onResume();
        mWv.resumeTimers();
    }

    @Override
    public void onPause() {
        super.onPause();
        mWv.onPause();
        mWv.pauseTimers(); //暂停整个 WebView 所有布局、解析、JS。
    }


    @Override
    protected void onDestroy() {
        if (mWv != null) {
            mWv.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWv.clearHistory();

            ((ViewGroup) mWv.getParent()).removeView(mWv);
            mWv.destroy();
            mWv = null;
        }
        super.onDestroy();
    }

    RestaurantInfoBean.DataBean mBean;

    @Override
    public void marketDetailSuccess(RestaurantInfoBean data) {
        if (data != null && data.getData() != null) {
            mBean = data.getData();
            setView(data.getData());
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
}
