package com.geli.m.mvp.home.mine_fragment.helpcenter_activity;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.bean.HelpCenterBean;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.other.WebViewActivity;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.List;

import static com.geli.m.config.Constant.INTENT_LINKS;

/**
 * Created by Steam_l on 2018/3/8.
 *
 *
 * 帮助中心
 */

public class HelpCenterActivity extends MVPActivity<HelpCenterPresentImpl> implements HelpCenterView, View.OnClickListener {

    @BindView(R.id.tv_title_name)
    TextView mTvTitle;

    /* 大的分类 */
    @BindView(R.id.erv_helpcenter_top)
    EasyRecyclerView mErvTop;

    @BindView(R.id.erv_helpcenter_bottom)
    EasyRecyclerView mErvBottom;

    private RecyclerArrayAdapter mTopAdapter;
    private RecyclerArrayAdapter mBottomAdapter;


    @Override
    protected int getResId() {
        return R.layout.activity_helpcenter;
    }


    @Override
    protected void initData() {
        final Paint mPatin = new Paint();
        mPatin.setColor(Utils.getColor(R.color.line_color));
        mTvTitle.setText(Utils.getString(R.string.helper_center));

        initErvTop();
        initErvBottom();
        mPresenter.getAllData();
    }

    @Override
    protected void initEvent() {

    }


    private void initErvTop(){
        mErvTop.setLayoutManager(new LinearLayoutManager(mContext));
        mErvTop.setAdapterWithProgress(mTopAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new HelpCenterCatViewHolder(parent, mContext);
            }
        });
        ViewCompat.setNestedScrollingEnabled(mErvTop.getRecyclerView(), false);
        mTopAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                HelpCenterBean.DataEntity.CatListEntity entity = (HelpCenterBean.DataEntity.CatListEntity) mTopAdapter.getItem(position);
                mPresenter.getDataForCat(entity.getCat_id() + "");      // 获取这个类型帮助的信息
            }
        });
    }


    private void initErvBottom(){
        mErvBottom.setLayoutManager(new LinearLayoutManager(mContext));
        mErvBottom.setAdapterWithProgress(mBottomAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new HelpCenterCatListViewHolder(parent, mContext);
            }
        });


        ViewCompat.setNestedScrollingEnabled(mErvBottom.getRecyclerView(), false);
        EasyRecyclerViewUtils.initEasyRecyclerView(mErvBottom);

        mBottomAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {                 // 具体的使用 WebView 显示
                HelpCenterBean.DataEntity.HelpListEntity entity = (HelpCenterBean.DataEntity.HelpListEntity) mBottomAdapter.getItem(position);
                startActivity(WebViewActivity.class, new Intent().putExtra(INTENT_LINKS, entity.getArt_url()));
            }
        });
    }

    @Override
    protected HelpCenterPresentImpl createPresenter() {
        return new HelpCenterPresentImpl(this);
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
    public void showAllData(HelpCenterBean.DataEntity dataEntity) {
        mTopAdapter.clear();
        mTopAdapter.addAll(dataEntity.getCat_list());
        setBottomList(dataEntity.getHelp_list());
    }

    @Override
    public void showCatData(List<HelpCenterBean.DataEntity.HelpListEntity> catListEntity) {
        setBottomList(catListEntity);
    }

    public void setBottomList(List<HelpCenterBean.DataEntity.HelpListEntity> helpListEntity) {
        mBottomAdapter.clear();
        if (null == helpListEntity || helpListEntity.size() == 0) {
            mErvBottom.showEmpty();
        } else {
            mBottomAdapter.addAll(helpListEntity);
        }
    }

    @OnClick({R.id.iv_title_back})
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_title_back) {
            finish();
        }
    }
}
