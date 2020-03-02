package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.aftersolddetails_activity;

import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.AfterSoldDetailsBean;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.List;

import static com.geli.m.config.Constant.INTENT_ORDER_ID;

/**
 * author:  shen
 * date:    2018/5/24
 *
 * 售后详情
 */
public class AfterSoldDetailsActivity extends MVPActivity<AfterSoldDetailsPresenterImpl>
        implements AfterSoldDetailsView, View.OnClickListener{

    /** 标题名称 */
    @BindView(R.id.tv_title_name)
    TextView mTvTitleName;
    /** 标题返回键 */
    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;

    /** 包裹 "售后反馈进度流程" 的布局 */
    @BindView(R.id.erv_after_sold_details_list)
    EasyRecyclerView mErvDetails;


    /** 订单id */
    String mOrderId = "";
    public RecyclerArrayAdapter mAdapter;

    /** 售后反馈流程"脚部" */
    AfterSoldDetailsHeaderVH mHeaderLayout;

    @Override
    protected AfterSoldDetailsPresenterImpl createPresenter() {
        return new AfterSoldDetailsPresenterImpl(this);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_after_sold_details;
    }

    @Override
    protected void initData() {
        mOrderId = getIntent().getStringExtra(INTENT_ORDER_ID);
        mTvTitleName.setText(R.string.after_sales_progress);
        mPresenter.getAfterSoldDetails(GlobalData.getUser_id(), mOrderId);
    }

    @Override
    protected void initEvent() {
        initERv();
    }

    private void initERv(){
        mErvDetails.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mErvDetails.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = 0;
                outRect.bottom = 0;

                //if (parent.getChildAdapterPosition(view) == 0 + 1) {    // 还有一个头布局 mAdapter.getCount 会有个小问题，最开始会拿到的是0
                //      outRect.top = Utils.dip2px(mContext, 10);
                //}else
                //if (parent.getChildAdapterPosition(view) == (mAdapter.getAllData().size() - 1 ) + 1) {  // 还有一个头布局
                //    outRect.bottom = Utils.dip2px(mContext, 30);
                //}
            }
        });
        mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new AfterSoldDetailsViewHolder(parent, mContext);
            }
        };

        mHeaderLayout = new AfterSoldDetailsHeaderVH(this, getSupportFragmentManager());
        mAdapter.addHeader(mHeaderLayout);                        // 添加头部局
        mErvDetails.setAdapterWithProgress(mAdapter);
        EasyRecyclerViewUtils.initEasyRecyclerView(mErvDetails);

        ViewCompat.setNestedScrollingEnabled(mErvDetails, false);
    }


    /**
     * 设置"售后进度"详情
     * @param bean
     */
    private void setDetails(AfterSoldDetailsBean bean){
        List<AfterSoldDetailsBean.DataEntity.Dispose_res> afterSoldDetails = bean.getData().getDispose_res();
        if (afterSoldDetails == null || afterSoldDetails.size() == 0) {
            mErvDetails.showEmpty();
            return;
        }
        mAdapter.addAll(afterSoldDetails);

        // 测试用数据
//        List<AfterSoldDetailsBean.DataEntity.Dispose_res> beanList = new ArrayList<>();
//        for(int i =0; i<3; i ++){
//            AfterSoldDetailsBean.DataEntity.Dispose_res beantemp = new AfterSoldDetailsBean.DataEntity.Dispose_res();
//            beantemp.setAdd_time(1520307870);
//            beantemp.setDispose_detail("152030787" + i);
//            beanList.add(beantemp);
//        }
//        mAdapter.addAll(beanList);


        //mErvDetails.scrollToPosition(mAdapter.getAllData().size() - 1);  // 调到最后一个
    }


    @Override
    public void onSuccess(String msg) {

    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getAfterSoldDetailsSuccess(AfterSoldDetailsBean bean) {
        mHeaderLayout.setDate(bean);
        mHeaderLayout.setImg(bean);
        setDetails(bean);
    }


    @OnClick(R.id.iv_title_back)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_title_back:
                finish();
                break;
        }
    }
}
