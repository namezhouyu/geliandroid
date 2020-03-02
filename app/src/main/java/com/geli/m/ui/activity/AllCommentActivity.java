package com.geli.m.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geli.m.R;
import com.geli.m.api.UrlSet;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.GoodsDetailsBean;
import com.geli.m.bean.ShopInfoBean;
import com.geli.m.coustomview.ErrorView;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.present.CommentListPresentImpl;
import com.geli.m.mvp.view.CommentListView;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;
import com.geli.m.viewholder.AllCommentViewHolder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Steam_l on 2017/12/26.
 *
 * 全部评论
 */
public class AllCommentActivity extends MVPActivity<CommentListPresentImpl> implements RecyclerArrayAdapter.ItemView, CommentListView, ErrorView.ClickRefreshListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    @BindView(R.id.tv_title_name)
    TextView tv_title;

    @BindView(R.id.erv_allcomment_list)
    EasyRecyclerView erv_list;


    private RecyclerArrayAdapter mAdapter;

    /** 谁的评论 -- 商店 */
    public static final String TYPE_SHOP = "type_shop";
    /** 谁的评论 -- 食品 */
    public static final String TYPE_GOODS = "TYPE_GOODS";

    public static final String INTENT_TYPE = "intent_type";
    public static final String INTENT_ID = "intent_id";
    public static final String INTENT_GRADE = "intent_grade";

    /** 当前的"评论类型" */
    private String curr_type = TYPE_SHOP;
    private int page = 1;
    private String id;
    ShopInfoBean.DataEntity.ShopInfoEntity.GradeEntity mGradeEntity;

    @Override
    protected int getResId() {
        return R.layout.activity_allcomment;
    }

    @Override
    protected void init() {
        super.init();
        curr_type = getIntent().getStringExtra(INTENT_TYPE);
        id = getIntent().getStringExtra(INTENT_ID);
        mGradeEntity = getIntent().getParcelableExtra(INTENT_GRADE);
    }

    @Override
    protected void initData() {
        tv_title.setText(Utils.getString(R.string.title_goodscomment));
        erv_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        DividerDecoration decoration = new DividerDecoration(Utils.getColor(R.color.line_color), Utils.dip2px(mContext, 1), Utils.dip2px(mContext, 15), 0);
        decoration.setDrawLastItem(true);
        decoration.setDrawHeaderFooter(true);
        erv_list.addItemDecoration(decoration);
        erv_list.setAdapterWithProgress(mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new AllCommentViewHolder(parent, mContext);
            }
        });


        EasyRecyclerViewUtils.initEasyRecyclerView(erv_list, this);
        EasyRecyclerViewUtils.initAdapter(mAdapter, new EasyRecyclerViewUtils.AdapterListener() {
            @Override
            public void onMoreShow() {                              // 加载更多
                page++;
                onRefresh();
            }

            @Override
            public void onErrorClick() {
                mAdapter.resumeMore();
            }
        });
        if (curr_type.equals(TYPE_SHOP)) {
            mAdapter.addHeader(this);
        }
        erv_list.setRefreshListener(this);
        onRefresh();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public View onCreateView(ViewGroup parent) {
        return LayoutInflater.from(mContext).inflate(R.layout.include_allcomment_score, parent, false);
    }

    @Override
    public void onBindView(View headerView) {
        double chilled = 5.0;
        double logistics = 5.0;
        double service = 5.0;
        if (mGradeEntity != null) {
            chilled = mGradeEntity.getCom_grade();
            logistics = mGradeEntity.getLogistics_grade();
            service = mGradeEntity.getServe_grade();
        }

        /** 冰鲜等级 */
        TextView tv_chilled = (TextView) headerView.findViewById(R.id.tv_comment_chilled_level);
        /** 物流速度 */
        TextView tv_logistics = (TextView) headerView.findViewById(R.id.tv_comment_logistics_speed);
        /** 客服态度 */
        TextView tv_service = (TextView) headerView.findViewById(R.id.tv_comment_service_attitude);
        tv_chilled.setText(chilled + "");
        tv_logistics.setText(logistics + "");
        tv_service.setText(service + "");
    }

    @Override
    protected CommentListPresentImpl createPresenter() {
        return new CommentListPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
        if (mPresenter.isToLike) {
            mPresenter.isToLike = !mPresenter.isToLike;
            List<GoodsDetailsBean.DataBean.GoodsCommentBean> allData = mAdapter.getAllData();
            int i = 0;
            for (GoodsDetailsBean.DataBean.GoodsCommentBean bean : allData) {
                if (curr_comid.equals(bean.getCom_id() + "")) {
                    int tag = bean.getIs_like();
                    if (tag == 0) {
                        tag = 1;
                    } else {
                        tag = 0;
                    }

                    bean.setIs_like(tag);
                    int com_like = bean.getCom_like();
                    if (tag == 1) {
                        com_like++;
                    } else {
                        com_like--;
                    }
                    bean.setCom_like(com_like);
                    //去头
                    mAdapter.notifyItemChanged(i + mAdapter.getHeaderCount(), bean);
                    break;
                }
                i++;
            }
        }
    }

    @Override
    public void onError(String msg) {
        ShowSingleToast.showToast(mContext, msg);
        if (mPresenter.isToLike) {
            mPresenter.isToLike = !mPresenter.isToLike;
            return;
        }
        if (page != 1) {
            page = page - 1;
            mAdapter.pauseMore();
        } else {
            erv_list.showError();
        }
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
    public void showCommentList(List<GoodsDetailsBean.DataBean.GoodsCommentBean> commentBeanList) {
        if (page == 1) {
            mAdapter.clear();
        }
        if (commentBeanList == null || commentBeanList.size() == 0) {
            erv_list.showEmpty();
            return;
        }
        mAdapter.addAll(commentBeanList);
        if (commentBeanList.size() < 20) {
            mAdapter.stopMore();
        }
    }

    @Override
    public void clickRefresh() {
        page = 1;
        onRefresh();
    }

    @Override
    public void onRefresh() {
        Map data = new HashMap();
        data.put("user_id", GlobalData.getUser_id());
        data.put("page", page + "");
        String url;
        if (curr_type.equals(TYPE_SHOP)) {
            data.put("shop_id", id + "");
            url = UrlSet.shopCommentList;
        } else {
            data.put("goods_id", id + "");
            url = UrlSet.goodsCommentList;
        }
        mPresenter.getCommentList(url, data);
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

    /**
     * 当前点赞的是哪个"评论id"
     */
    private String curr_comid;

    /**
     * 点赞
     * @param com_id 评论id
     */
    public void toLike(String com_id) {
        curr_comid = com_id;
        mPresenter.toLike(GlobalData.getUser_id(), com_id);
    }
}
