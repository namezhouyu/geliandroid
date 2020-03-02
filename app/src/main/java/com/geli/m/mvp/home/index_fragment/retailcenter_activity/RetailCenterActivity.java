package com.geli.m.mvp.home.index_fragment.retailcenter_activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.api.UrlSet;
import com.geli.m.bean.FactoryBean;
import com.geli.m.bean.RetailCenterBean;
import com.geli.m.coustomview.ErrorView;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.index_fragment.main.IndexFragment;
import com.geli.m.mvp.home.index_fragment.search_activity.SearchActivity;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.factory_direct1or2.view_holder.FactoryDirectVH1or2;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.geli.m.config.Constant.INTENT_TYPE;
import static com.geli.m.config.Constant.Location_Key;

/**
 * Created by Steam_l on 2017/12/27.
 *
 * 批零列表
 */
public class RetailCenterActivity extends MVPActivity<RetailCenterPresentImpl> implements RetailCenterView, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, ErrorView.ClickRefreshListener {


    /** 标题的右边 */
    @BindView(R.id.tv_title_right)
    TextView tv_right;
    /** 标题的左边 */
    @BindView(R.id.tv_title_name)
    TextView tv_name;

    @BindView(R.id.erv_pilinlist_content)
    EasyRecyclerView erv_list;

    /** 批零中心 */
    public static String TYPE_PILING = "type_piling";
    /** 厂家直供 */
    public static String TYPE_CHANGJIA = "TYPE_changjia";

    /** 当前类型 -- 如"批零中心"、"厂家直供" */
    private String curr_type = TYPE_PILING;

    private int page = 1;
    private RecyclerArrayAdapter mAdapter;
    private RecyclerView.RecycledViewPool mRecycledViewPool;

    /** 厂家直供 -- 列表 */
    private List<FactoryBean> mShop_list;

    @Override
    protected int getResId() {
        return R.layout.activity_pilinglist;
    }

    @Override
    protected void init() {
        super.init();
        curr_type = getIntent().getStringExtra(INTENT_TYPE);
    }

    @Override
    protected void initData() {
        setView();
        initErv();
        getList();
    }

    @Override
    protected void initEvent() {
        erv_list.setRefreshListener(this);
    }

    private void setView() {
        /* 设置放大镜图片 */
        Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.nav_btn_sousuo); // "放大镜"图片
        // 为可绘制的矩形指定一个边界矩形。这是当它的draw（）方法被调用时，它将绘制的地方。
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        tv_right.setCompoundDrawables(drawable, null, null, null);  // 将图片放在"文本"的左边

        /* 设置标题名字 */
        if (curr_type.equals(TYPE_PILING)) {
            tv_name.setText(Utils.getString(R.string.piling_center));
        } else {
            tv_name.setText(Utils.getString(R.string.changjia_zhigong));
        }
    }

    private void initErv() {
        // 垂直、分隔线
        erv_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        DividerDecoration itemDecoration = new DividerDecoration(Utils.getColor(R.color.line_color),
                Utils.dip2px(mContext, 1), Utils.dip2px(mContext, 15), 0);
        itemDecoration.setDrawLastItem(true);
        EasyRecyclerViewUtils.initEasyRecyclerView(erv_list, this);         // 设置下"空数据、错误数据时显示的界面"
        erv_list.addItemDecoration(itemDecoration);

        erv_list.setAdapterWithProgress(mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                FactoryDirectVH1or2 direViewHolder = new FactoryDirectVH1or2(parent, mContext, mRecycledViewPool);
                direViewHolder.isShowGoods(true);
                //共享线程池数据混乱
                // if (mRecycledViewPool == null) {
                //      mRecycledViewPool = direViewHolder.getRecycledViewPool();
                // }
                return direViewHolder;
            }
        });

        EasyRecyclerViewUtils.initAdapter(mAdapter, new EasyRecyclerViewUtils.AdapterListener() {
            @Override
            public void onMoreShow() {
                page += 1;
                getList();
            }

            @Override
            public void onErrorClick() {
                mAdapter.resumeMore();
            }
        });
    }

    /**
     * 获取数据 -- 根据地理位置
     */
    private void getList() {
        Map data = new HashMap();
        if (curr_type.equals(TYPE_PILING)) {
            data.put("shop_type", 1 + "");
        } else {
            data.put("shop_type", 2 + "");
        }

        for (String value : Location_Key) {            // 当前位置
            String dataValue = "";
            if (value.equals("district")) {
                dataValue = IndexFragment.mAreaBean.getAddress();
            } else if (value.equals("latitude")) {
                dataValue = IndexFragment.mAreaBean.getLa();
            } else if (value.equals("longitude")) {
                dataValue = IndexFragment.mAreaBean.getLo();
            }
            if (!TextUtils.isEmpty(dataValue)) {
                data.put(value, dataValue);
            }
        }

        data.put("page", page);
        mPresenter.getList(UrlSet.searchshop, data);
    }



    @Override
    protected RetailCenterPresentImpl createPresenter() {
        return new RetailCenterPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
    }

    @Override
    public void onError(String msg) {
        if (page != 1) {
            mAdapter.pauseMore();
            // 因为点击重新加载更多后page会+1;出错所以现在先-1
            page = page - 1;
        } else {
            erv_list.showError();
        }
        ShowSingleToast.showToast(mContext, msg);
    }

    @Override
    public void showLoading() {
        if (!erv_list.getSwipeToRefresh().isRefreshing() && page == 1) {
            erv_list.setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        if (page == 1) {
            erv_list.setRefreshing(false);
        }
    }

    @Override
    public void showData(RetailCenterBean bean) {
        if (bean == null || bean.getData().size() <= 0) {
            erv_list.showEmpty();
            return;
        }
        mShop_list = bean.getData();
        if (page == 1) {
            mAdapter.clear();
        }
        mAdapter.addAll(mShop_list);
        if (mShop_list.size() < 20) {
            mAdapter.stopMore();
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        getList();
    }



    @OnClick({R.id.iv_title_back, R.id.tv_title_right})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;

            case R.id.tv_title_right:                       /* 搜索 -- 标题右边的文本 */
                goSearch();
                break;

            default:
                break;
        }
    }

    /**
     * 去搜索
     */
    private void goSearch() {
        Intent intent = new Intent();
        if (curr_type.equals(TYPE_PILING)) { /* 批零 */
            intent.putExtra(SearchActivity.INTENT_SEARCHTYPE, SearchActivity.SEARCH_TYPE_P);
        } else {                             /* 厂家 */
            intent.putExtra(SearchActivity.INTENT_SEARCHTYPE, SearchActivity.SEARCH_TYPE_C);
        }
        startActivity(SearchActivity.class, intent);
    }

    @Override
    public void clickRefresh() {
        getList();
    }
}
