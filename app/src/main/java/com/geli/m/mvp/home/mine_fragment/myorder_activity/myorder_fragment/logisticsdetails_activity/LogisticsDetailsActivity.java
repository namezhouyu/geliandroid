package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.logisticsdetails_activity;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.LogisticsDetailsBean;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.List;

import static com.geli.m.config.Constant.INTENT_ORDER_ID;

/**
 * Created by Steam_l on 2018/3/5.
 */

public class LogisticsDetailsActivity extends MVPActivity<LogisticsDetailsPresentImpl> implements View.OnClickListener, LogisticsDetailsView {

    /** 标题 */
    @BindView(R.id.tv_title_name)
    TextView mTvTitle;
    /** 图片 */
    @BindView(R.id.iv_logisticsdetails_img)
    ImageView mIvImg;
    /** 商品数量 */
    @BindView(R.id.tv_logisticsdetails_goodsnumber)
    TextView mTvGoodsNumber;
    /** 物流热线 */
    @BindView(R.id.tv_logisticsdetails_phone)
    TextView mTvPhone;
    /** 订单号 */
    @BindView(R.id.tv_logisticsdetails_ordernumber)
    TextView mTvOrderNumber;

    @BindView(R.id.erv_logisticsdetails_list)
    EasyRecyclerView mErvList;

    private String order_id;
    public RecyclerArrayAdapter mAdapter;

    @Override
    protected int getResId() {
        return R.layout.activity_logisticsdetails;
    }

    @Override
    protected void init() {
        super.init();
        order_id = getIntent().getStringExtra(INTENT_ORDER_ID);
    }

    @Override
    protected void initData() {
        mTvTitle.setText(Utils.getString(R.string.title_logisticsdetails));

        initErvAndAdapter();

        mPresenter.getLogistics(GlobalData.getUser_id(), order_id);
        EasyRecyclerViewUtils.initEasyRecyclerView(mErvList);
    }

    @Override
    protected void initEvent() {

    }

    private void initErvAndAdapter() {
        mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new LogisticsDetailsViewHolder(parent, mContext);
            }
        };

        mErvList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, true));
        mErvList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = 0;
                outRect.bottom = 0;
                if (parent.getChildAdapterPosition(view) == mAdapter.getAllData().size() - 1) {
                    outRect.top = Utils.dip2px(mContext, 30);
                } else if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.bottom = Utils.dip2px(mContext, 30);
                }
            }
        });
        mErvList.setAdapterWithProgress(mAdapter);
    }


    @Override
    protected LogisticsDetailsPresentImpl createPresenter() {
        return new LogisticsDetailsPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
    }

    @Override
    public void onError(String msg) {
        ShowSingleToast.showToast(mContext, msg);
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
    public void showList(LogisticsDetailsBean.DataEntity dataEntities) {
        mAdapter.clear();
        mTvOrderNumber.setText(Utils.getString(R.string.order_number, dataEntities.getOrder_sn()));
        SpannableString spannableString = new SpannableString(Utils.getString(R.string.logistics_phone) + dataEntities.getTel());
        spannableString.setSpan(new ForegroundColorSpan(Utils.getColor(R.color.zhusediao)), Utils.getString(R.string.logistics_phone).length(), spannableString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTvPhone.setText(spannableString.toString().trim());
        mTvGoodsNumber.setText(Utils.getString(R.string.total_goods_number, dataEntities.getGoods_number() + "", Utils.getString(R.string.species)));
        GlideUtils.loadImg(mContext, dataEntities.getGoods_thumb(), mIvImg);

        if (setErvData(dataEntities))
            return;

        mErvList.scrollToPosition(mAdapter.getAllData().size() - 1);
    }

    private boolean setErvData(LogisticsDetailsBean.DataEntity dataEntities) {
        List<LogisticsDetailsBean.DataEntity.LogisticsEntity> logistics = dataEntities.getLogistics();
        if (logistics == null || logistics.size() == 0) {
            mErvList.showEmpty();
            return true;
        }
        mAdapter.addAll(logistics);

        // 测试用数据
//        List<LogisticsDetailsBean.DataEntity.LogisticsEntity> beanList = new ArrayList<>();
//        for(int i =0; i<5; i ++){
//            LogisticsDetailsBean.DataEntity.LogisticsEntity bean = new LogisticsDetailsBean.DataEntity.LogisticsEntity();
//            bean.setAdd_time("152030787" + i);
//            bean.setContent("152030787" + i);
//            beanList.add(bean);
//        }
//        mAdapter.addAll(beanList);
        return false;
    }

    @OnClick({R.id.iv_title_back})
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
