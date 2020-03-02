package com.geli.m.mvp.home.index_fragment.message_activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.MessBean;
import com.geli.m.config.Constant;
import com.geli.m.coustomview.ErrorView;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.logisticsdetails_activity.LogisticsDetailsActivity;
import com.geli.m.mvp.home.other.WebViewActivity;
import com.geli.m.mvp.home.other.goodsdetails_activity.GoodsDetailsActivity;
import com.geli.m.mvp.home.other.shopdetails_activity.ShopDetailsActivity;
import com.geli.m.mvp.present.MessPresentImpl;
import com.geli.m.mvp.view.MessView;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.viewholder.MessViewHolder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.geli.m.config.Constant.INTENT_GOODS_ID;
import static com.geli.m.config.Constant.INTENT_IS_OPEN;
import static com.geli.m.config.Constant.INTENT_LINKS;
import static com.geli.m.config.Constant.INTENT_ORDER_ID;
import static com.geli.m.config.Constant.INTENT_SHOP_ID;
import static com.geli.m.config.Constant.MessageType;
import static com.geli.m.config.Constant.Message_Title;

/**
 * Created by Steam_l on 2018/3/28.
 *
 *
 * 1:系统公告 2:通知中心 3:交易物流
 */
public class MessActivity extends MVPActivity<MessPresentImpl> implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, MessView {

    @BindView(R.id.tv_title_name)
    TextView mTvTitle;

    @BindView(R.id.erv_list_mess)
    EasyRecyclerView mErvList;

    private RecyclerArrayAdapter<MessBean.DataEntity> mAdapter;
    private int page = 1;

    /** 消息 -- 1:系统公告 2:通知中心 3:交易物流 */
    private Constant.MessageType mMessageType = MessageType.system_notify;

    @Override
    protected MessPresentImpl createPresenter() {
        return new MessPresentImpl(this);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_mess;
    }

    @Override
    protected void init() {
        super.init();

        getIntentExtra();
    }

    private void getIntentExtra(){
        mMessageType = (MessageType) getIntent().getExtras().get(Message_Title);
    }

    @Override
    protected void initData() {
        mTvTitle.setText(mMessageType.getName());

        initErv();
        onRefresh();
    }

    @Override
    protected void initEvent() {
        setErvAdapter();
    }

    private void initErv(){
        mErvList.setLayoutManager(new LinearLayoutManager(mContext));
        mErvList.setAdapterWithProgress(mAdapter = new RecyclerArrayAdapter<MessBean.DataEntity>(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new MessViewHolder(parent, mContext);
            }
        });

        EasyRecyclerViewUtils.initAdapter(mAdapter, new EasyRecyclerViewUtils.AdapterListener() {
            @Override
            public void onMoreShow() {
                super.onMoreShow();
                page++;
                getList();
            }

            @Override
            public void onErrorClick() {
                super.onErrorClick();
                mAdapter.resumeMore();
            }
        });

        EasyRecyclerViewUtils.initEasyRecyclerView(mErvList, new ErrorView.ClickRefreshListener() {
            @Override
            public void clickRefresh() {

            }
        });

        mErvList.setRefreshListener(this);

    }

    // 1系统消息 2通知中心 3物流消息 4商品详情 5商家首页 6商家优惠劵  8支付提醒 9活动
    private void setErvAdapter() {
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                MessBean.DataEntity item = mAdapter.getItem(position);
                int cat_id = item.getCat_id();
                Intent intent;

                switch (cat_id){
                    case 1:                            /* 1系统消息 */
                        intent = new Intent().putExtra(INTENT_LINKS, item.getPush_id() + "");
                        startActivity(WebViewActivity.class, intent);
                        break;

                    case 3:                            /* 3物流消息 */
                        intent = new Intent().putExtra(INTENT_ORDER_ID, item.getTarget_id() + "");
                        startActivity(LogisticsDetailsActivity.class, intent);
                        break;

                    case 4:                            /* 4商品详情 */
                        intent = new Intent().putExtra(INTENT_GOODS_ID, item.getTarget_id() + "");
                        startActivity(GoodsDetailsActivity.class, intent);
                        break;

                    case 5:                            /* 5商家首页 */
                        intent = new Intent().putExtra(INTENT_SHOP_ID, item.getTarget_id() + "");
                        startActivity(ShopDetailsActivity.class, intent);
                        break;

                    case 6:                            /* 6商家优惠劵 */
                        intent = new Intent().putExtra(INTENT_SHOP_ID, item.getTarget_id() + "")
                                .putExtra(INTENT_IS_OPEN, false);
                        startActivity(ShopDetailsActivity.class, intent);
                        break;

                    case 9:                            /* 9活动 */
                        intent = new Intent().putExtra(INTENT_LINKS, item.getTarget_id() + "");
                        startActivity(WebViewActivity.class, intent);
                        break;
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        page = 1;
        getList();
    }

    private void getList() {
        Map data = new HashMap();
        data.put("user_id", GlobalData.getUser_id());
        data.put("page", page + "");
        data.put("cat_id", mMessageType.getValue() + "");
        mPresenter.getMess(data);
    }


    @Override
    public void onError(String msg) {
        ToastUtils.showToast(msg);
        if (page != 1) {
            page--;
            mAdapter.pauseMore();
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
    public void showList(List<MessBean.DataEntity> dataEntities) {
        if (page == 1) {
            mAdapter.clear();
            if (null == dataEntities || dataEntities.size() == 0) {
                mErvList.showEmpty();
                return;
            }
        }
        mAdapter.addAll(dataEntities);
        if (dataEntities.size() < 20) {
            mAdapter.stopMore();
        }
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
