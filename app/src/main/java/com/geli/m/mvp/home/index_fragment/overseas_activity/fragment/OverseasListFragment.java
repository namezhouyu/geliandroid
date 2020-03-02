package com.geli.m.mvp.home.index_fragment.overseas_activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import com.geli.m.R;
import com.geli.m.bean.OverseasGoodsOuterBean;
import com.geli.m.coustomview.ErrorView;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.base.BaseFragment;
import com.geli.m.mvp.home.index_fragment.overseas_activity.OverseasActivity;
import com.geli.m.mvp.home.other.goodsdetails_activity.GoodsDetailsActivity;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import java.util.List;

import static com.geli.m.config.Constant.INTENT_GOODS_ID;

/**
 * Created by Steam_l on 2017/12/29.
 */

public class OverseasListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.erv_overseas_list)
    public EasyRecyclerView mErvList;

    public static String ARG_GOODSTYPE = "arg_goodstype";
    public int mCurrGoodsType;
    private RecyclerArrayAdapter mAdapter;
    public int page = 1;
    public boolean mIsShow;
    int mSelectCountryId = 0;
    int mSelectSortId = 0;

    @Override
    public int getResId() {
        return R.layout.fragment_overseas_list;
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mIsShow && isVisibleToUser
                && (((OverseasActivity) mContext).selectCountryId != mSelectCountryId
                || ((OverseasActivity) mContext).selectSortId != mSelectSortId)) {
            //显示--加载数据
            ((OverseasActivity) mContext).getGoodsList(page, mCurrGoodsType);
        } else if (mIsShow && !isVisibleToUser) {
            //隐藏--保存数据
            mSelectCountryId = ((OverseasActivity) mContext).selectCountryId;
            mSelectSortId = ((OverseasActivity) mContext).selectSortId;
        }
    }


    /**
     * @param goodsType 4 团购  3 期货  2 现货
     * @return
     */
    public static OverseasListFragment newInstance(int goodsType) {
        OverseasListFragment overseasListFragment = new OverseasListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_GOODSTYPE, goodsType);
        overseasListFragment.setArguments(args);
        return overseasListFragment;
    }

    @Override
    protected void init() {
        super.init();
        Bundle arguments = getArguments();
        mCurrGoodsType = arguments.getInt(ARG_GOODSTYPE);
    }

    @Override
    protected void initData() {
        //        if (isShowSmallCart) {
        //            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mErvList.getLayoutParams();
        //            layoutParams.bottomMargin = (int) mContext.getResources().getDimension(R.dimen.bottom_height);
        //            mErvList.setLayoutParams(layoutParams);
        //        }
        mErvList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mErvList.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.line_color), Utils.dip2px(mContext, 1), Utils.dip2px(mContext, 15), 0));
        mErvList.setAdapterWithProgress(mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new OverseasListViewHolder(parent, mContext);
            }
        });
        EasyRecyclerViewUtils.initEasyRecyclerView(mErvList, new ErrorView.ClickRefreshListener() {

            @Override
            public void clickRefresh() {
                page = 1;
                ((OverseasActivity) mContext).initListData(mCurrGoodsType);
            }
        });
        EasyRecyclerViewUtils.initAdapter(mAdapter, new EasyRecyclerViewUtils.AdapterListener() {
            @Override
            public void onMoreShow() {
                page++;
                ((OverseasActivity) mContext).getGoodsList(page, mCurrGoodsType);
            }

            @Override
            public void onErrorClick() {
                mAdapter.resumeMore();
            }
        });
        mErvList.setRefreshListener(this);
    }

    @Override
    protected void initEvent() {
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                OverseasGoodsOuterBean.OverseasGoodsBean goodsBean = (OverseasGoodsOuterBean.OverseasGoodsBean) mAdapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra(INTENT_GOODS_ID, goodsBean.getGoods_id() + "");
                ((BaseActivity) mContext).startActivity(GoodsDetailsActivity.class, intent);
            }
        });
        mErvList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (((OverseasActivity) mContext).isOpen) {
                    ((OverseasActivity) mContext).opencloseSelectLayout(0);
                }
                return false;
            }
        });
    }

    public void onError() {
        if (page != 1) {
            mAdapter.pauseMore();
            //因为点击重新加载更多后page会+1;出错所以现在先-1
            page = page - 1;
        } else {
            mErvList.showError();
        }
    }

    public void setGoodsData(List<OverseasGoodsOuterBean.OverseasGoodsBean> overseasGoodsBean) {
        if (page == 1 && (overseasGoodsBean == null || overseasGoodsBean.size() == 0)) {
            mErvList.showEmpty();
            return;
        }
        if (page == 1) {
            mAdapter.clear();
        }
        mAdapter.addAll(overseasGoodsBean);
        if (overseasGoodsBean.size() < 20) {
            mAdapter.stopMore();
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        ((OverseasActivity) mContext).getGoodsList(page, mCurrGoodsType);
    }
}
