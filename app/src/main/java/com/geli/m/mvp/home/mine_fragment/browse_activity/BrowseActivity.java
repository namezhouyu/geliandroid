package com.geli.m.mvp.home.mine_fragment.browse_activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.BrowseBean;
import com.geli.m.coustomview.ErrorView;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.other.goodsdetails_activity.GoodsDetailsActivity;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import java.util.List;

import static com.geli.m.config.Constant.INTENT_GOODS_ID;

/**
 * Created by Steam_l on 2018/3/26.
 *
 * 浏览记录
 */

public class BrowseActivity extends MVPActivity<BrowsePresentImpl> implements View.OnClickListener, BrowseView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tv_title_name)
    TextView mTvTitle;

    @BindView(R.id.erv_browse_list)
    EasyRecyclerView mErvList;

    private RecyclerArrayAdapter mAdapter;
    private int page = 1;

    @Override
    protected int getResId() {
        return R.layout.activity_browse;
    }

    @Override
    protected void initData() {
        mTvTitle.setText(Utils.getString(R.string.title_browse));

        initErv();
        initAdapter();
        setErv();
        onRefresh();
    }

    @Override
    protected void initEvent() {
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {        // 项点击事件
            @Override
            public void onItemClick(int position) {
                Object item = mAdapter.getItem(position);
                if (item instanceof BrowseBean.DataEntity) {
                    startActivity(GoodsDetailsActivity.class, new Intent().putExtra(INTENT_GOODS_ID, ((BrowseBean.DataEntity) item).getGoods_id() + ""));
                }
            }
        });
    }


    private void initErv() {
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);     // 每行占3格
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                try {
                    if (mAdapter.getItem(position) instanceof BrowseBean.DataEntity) {      /* 某某商品 */
                        return 1;           //占1格
                    }
                } catch (Exception e) {

                }
                return 3;                   //占3格                                          /* 时间段 */
            }
        });
        mErvList.setLayoutManager(manager);

        SpaceDecoration itemDecoration = new SpaceDecoration(Utils.dip2px(mContext, 10));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(false);
        mErvList.addItemDecoration(itemDecoration);
    }

    private void initAdapter() {
        mAdapter = new RecyclerArrayAdapter(mContext) {
            private int type_time = 1 << 0;
            private int type_goods = 1 << 1;

            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                if (viewType == type_time) {
                    return new BrowseTimeViewHolder(parent, mContext);
                }
                return new BrowseViewHolder(parent, mContext);
            }

            @Override
            public int getViewType(int position) {
                Object item = getItem(position);
                if (item instanceof BrowseBean.BrowseTime) {
                    return type_time;
                }
                return type_goods;
            }
        };
    }

    private void setErv() {
        mErvList.setAdapterWithProgress(mAdapter);
        EasyRecyclerViewUtils.initEasyRecyclerView(mErvList, new ErrorView.ClickRefreshListener() {
            @Override
            public void clickRefresh() {
                onRefresh();
            }
        });
        EasyRecyclerViewUtils.initAdapter(mAdapter, new EasyRecyclerViewUtils.AdapterListener() {
            @Override
            public void onMoreShow() {
                super.onMoreShow();
                page++;
                getBrowse();
            }

            @Override
            public void onErrorClick() {
                mAdapter.resumeMore();
            }
        });
        mErvList.setRefreshListener(this);
    }


    @Override
    protected BrowsePresentImpl createPresenter() {
        return new BrowsePresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
    }

    @Override
    public void onError(String msg) {
        ShowSingleToast.showToast(mContext, msg);
        if (page != 1) {
            page = page - 1;
            mAdapter.pauseMore();
        }else{
            mErvList.showError();
        }
    }

    @Override
    public void showLoading() {
        if (page == 1) {
            mErvList.setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        if (page == 1) {
            mErvList.setRefreshing(false);
        }
    }

    @Override
    public void showBrowse(List<Object> listEntity) {
        if (page == 1) {
            mAdapter.clear();
        }
        mAdapter.addAll(listEntity);
        if (listEntity.size() < 30) {
            mAdapter.stopMore();
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        getBrowse();
    }

    /**
     * 获取"浏览记录"
     */
    private void getBrowse() {
        mPresenter.getBrowse(GlobalData.getUser_id(), page + "");
    }

    @OnClick(R.id.iv_title_back)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;

            default:
                break;
        }
    }
}
