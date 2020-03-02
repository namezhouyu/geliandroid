package com.geli.m.mvp.home.find_fragment.findlist_fragment.details;

/**
 * Created by Steam_l on 2018/3/22.
 */

import android.content.Intent;
import android.graphics.Rect;
import android.net.http.SslError;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.FindDetailsBean;
import com.geli.m.dialog.CommentDialog;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.other.goodsdetails_activity.GoodsDetailsActivity;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.PriceUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.geli.m.config.Constant.INTENT_FIND_ID;
import static com.geli.m.config.Constant.INTENT_GOODS_ID;

/**
 * Created by Steam_l on 2018/2/27.
 *
 *
 * 文章
 */
public class ArticleDetailsActivity extends MVPActivity<FindDetailsPresentImpl> implements View.OnClickListener, FindDetailsView {

    /** 收藏 */
    @BindView(R.id.cb_video_coll)
    CheckBox mCbColl;

    /** 最顶部的图片 */
    @BindView(R.id.iv_atricledetails_img)
    ImageView mIvImg;

    /** 标题 */
    @BindView(R.id.tv_video_title)
    TextView mTvTitle;
    /** 头像 */
    @BindView(R.id.riv_video_avatar)
    RoundedImageView mRivAvatar;
    /** 昵称 */
    @BindView(R.id.tv_video_name)
    TextView mTvName;
    /** 浏览次数 */
    @BindView(R.id.tv_video_viewnumber)
    TextView mTvViewNumber;
    /** 点赞数 */
    @BindView(R.id.cb_video_like)
    CheckBox mCbLike;

    /** 文章的详情 -- 网页的数据 */
    @BindView(R.id.wv_video_details)
    WebView mWvLayout;

    /** 商品图片 */
    @BindView(R.id.iv_video_goods_img)
    ImageView mIvGoodsImg;
    /** 商品标题规格 */
    @BindView(R.id.tv_video_goods_title)
    TextView mTvGoodsTitle;
    /** 商品的一些提示消息 -- 如：请询价后下单 */
    @BindView(R.id.tv_video_goods_mess)
    TextView mTvGoodsMess;
    /** 商品价格 */
    @BindView(R.id.tv_video_goods_price)
    TextView mTvGoodsPrice;
    /** 查看详情按钮 */
    @BindView(R.id.bt_video_goods_checkdetails)
    Button mBtnGoodsCheck;


    /** 包裹"底部相关文章"的布局 */
    @BindView(R.id.ll_video_related_root)
    LinearLayout mLlRelatedRoot;
    /** 底部相关文章下的-- 相关文章 这几个字 */
    @BindView(R.id.tv_video_related_title)
    TextView mTvRelatedTitle;
    /** 底部相关文章下的列表 */
    @BindView(R.id.erv_video_related_list)
    EasyRecyclerView mErvRelatedList;


    private CommentDialog mCommentDialog;

    private String find_id;
    private RecyclerArrayAdapter<FindDetailsBean.DataEntity.AboutResEntity> mAdapter;
    private FindDetailsBean.DataEntity.GoodsResEntity mGoods_res;
    private FindDetailsBean.DataEntity.FindResEntity mFind_res;

    @Override
    protected int getResId() {
        return R.layout.activity_artcledetails;
    }

    @Override
    protected void init() {
        super.init();
        mImmersionBar
                .reset()
                .statusBarDarkFont(true)
                .statusBarAlpha(0.0f)
                .statusBarView(R.id.view_videodetails_top)
                .init();

        find_id = getIntent().getStringExtra(INTENT_FIND_ID);
    }

    @Override
    protected void initData() {

        if (mCommentDialog == null) {
            mCommentDialog = CommentDialog.newInstance();
        }

        mTvRelatedTitle.setText(Utils.getString(R.string.title_related_article));
        setErvAndAdapter();                                         // 底部"相关文章"的列表
        setWeb();
        mPresenter.getDetails(GlobalData.getUser_id(), find_id);    // 获取所有的数据
    }

    @Override
    protected void initEvent() {

    }

    /**
     * 底部"相关文章"的列表
     */
    private void setErvAndAdapter() {
        mErvRelatedList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mErvRelatedList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.left = Utils.dip2px(mContext, 15);
                } else {
                    outRect.left = Utils.dip2px(mContext, 10);
                }
                if (parent.getChildAdapterPosition(view) == mAdapter.getAllData().size() - 1) {
                    outRect.right = Utils.dip2px(mContext, 15);
                } else {
                    outRect.right = 0;
                }
            }
        });

        mAdapter = new RecyclerArrayAdapter<FindDetailsBean.DataEntity.AboutResEntity>(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new RelatedVideoViewHolder(parent, mContext);
            }
        };

        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FindDetailsBean.DataEntity.AboutResEntity item = mAdapter.getItem(position);
                if (item.getType() == 1) {
                    startActivity(VideoDetailsActivity.class, new Intent().putExtra(INTENT_FIND_ID, item.getFind_id() + ""));
                } else {
                    startActivity(ArticleDetailsActivity.class, new Intent().putExtra(INTENT_FIND_ID, item.getFind_id() + ""));
                }
            }
        });

        mErvRelatedList.setAdapterWithProgress(mAdapter);
        mErvRelatedList.getRecyclerView().setNestedScrollingEnabled(false);
    }

    private void setWeb() {
        WebSettings webSettings = mWvLayout.getSettings();
        //        WebChromeClient wvcc = new WebChromeClient() {
        //            @Override
        //            public void onReceivedTitle(WebView view, String title) {
        //                super.onReceivedTitle(view, title);
        //                mTvTitle.setText(title);
        //            }
        //        };
        //        mWvLayout.setWebChromeClient(wvcc);

        // 自适应屏幕
        webSettings.setUseWideViewPort(true);               //设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);

        // 便页面支持缩放：
        // webSettings.setSupportZoom(true);
        // webSettings.setBuiltInZoomControls(true);
        // webSettings.setDisplayZoomControls(false);
        mWvLayout.requestFocusFromTouch();

        // 允许调用js
        webSettings.setJavaScriptEnabled(true);
        // webSettings.setPluginState(true);  // 支持插件
        // webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 支持内容重新布局
        if (GlobalData.hasNetWork) {
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webSettings.setNeedInitialFocus(true); // 当webview调用requestFocus时为webview设置节点

        // 设置不用系统浏览器打开,直接显示在当前Webview
        mWvLayout.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }
        });
    }




    @Override
    protected FindDetailsPresentImpl createPresenter() {
        return new FindDetailsPresentImpl(this);
    }


    @OnClick({R.id.bt_video_goods_checkdetails,
            R.id.cb_video_like, R.id.cb_video_coll,
            R.id.back, R.id.rl_video_goodsroot})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cb_video_coll:                    /*  点击  收藏按钮 */
                clickColl();
                break;

            case R.id.cb_video_like:                    /* 点击 点赞按钮 */
                clickLike();
                break;

            case R.id.back:
                finish();
                break;

            case R.id.bt_video_goods_checkdetails:
            case R.id.rl_video_goodsroot:
                if (mGoods_res != null) {
                    startActivity(GoodsDetailsActivity.class, new Intent().putExtra(INTENT_GOODS_ID, mGoods_res.getGoods_id() + ""));
                }
                break;

            default:
                break;
        }
    }

    /**
     * 收藏按钮
     */
    private void clickColl() {
        Map data = new HashMap();
        data.put("col_type", "3");
        data.put("find_id", find_id);
        data.put("user_id", GlobalData.getUser_id());
        mPresenter.collection(data);
    }

    /**
     * 点赞按钮
     */
    private void clickLike() {
        if (mFind_res != null) {
            if (mCbLike.isChecked()) {
                mFind_res.setLike_num(mFind_res.getLike_num() + 1);
            } else {
                mFind_res.setLike_num(mFind_res.getLike_num() - 1);
            }
            mCbLike.setText("(" + mFind_res.getLike_num() + ")");
            mPresenter.like(GlobalData.getUser_id(), find_id);
        }
    }

    @Override
    public void onSuccess(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showToast(msg);

        if (mPresenter.isColl) {
            mPresenter.isColl = false;
            mCbColl.setChecked(!mCbColl.isChecked());
            return;
        }

        if (mPresenter.isLike) {
            mPresenter.isLike = false;

            if (mCbLike.isChecked()) {
                mFind_res.setLike_num(mFind_res.getLike_num() - 1);
            } else {
                mFind_res.setLike_num(mFind_res.getLike_num() + 1);
            }
            mCbLike.setText("(" + mFind_res.getLike_num() + ")");

            mCbLike.setChecked(!mCbLike.isChecked());
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

    @Override
    public void showData(FindDetailsBean.DataEntity dataEntity) {
        mGoods_res = dataEntity.getGoods_res();
        setOrtherData(dataEntity);
    }

    public void setOrtherData(FindDetailsBean.DataEntity ortherData) {
        mFind_res = ortherData.getFind_res();

        mCbColl.setChecked(mFind_res.getIs_collection() == 1);

        mTvTitle.setText(mFind_res.getArticle_title());
        GlideUtils.loadImg(mContext, mFind_res.getContent_cover_url(), mIvImg);
        mWvLayout.loadUrl(mFind_res.getContent_url());

        // 作者
        FindDetailsBean.DataEntity.FindResEntity.AuthorResEntity author_res = mFind_res.getAuthor_res();
        if (author_res != null) {
            GlideUtils.loadImg(mContext, author_res.getAuthor_icon(), mRivAvatar);
            mTvName.setText(author_res.getAuthor_name());
        }
        SpannableString text = new SpannableString(Utils.getString(R.string.view_number) + "(" + mFind_res.getView_num() + ")");
        text.setSpan(new ForegroundColorSpan(Utils.getColor(R.color.zhusediao)), Utils.getString(R.string.view_number).length(), text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTvViewNumber.setText(text);
        mCbLike.setChecked(mFind_res.getIs_like() == 1);
        mCbLike.setText("(" + mFind_res.getLike_num() + ")");

        // 推荐商品？
        FindDetailsBean.DataEntity.GoodsResEntity goods_res = ortherData.getGoods_res();
        if (goods_res != null) {
            GlideUtils.loadImg(mContext, goods_res.getGoods_thumb(), mIvGoodsImg);
            mTvGoodsMess.setText(goods_res.getGoods_intro());
            mTvGoodsPrice.setText(PriceUtils.getPrice(goods_res.getShop_price()));
            mTvGoodsTitle.setText(goods_res.getGoods_name());
        }

        // 相关文章
        List<FindDetailsBean.DataEntity.AboutResEntity> about_res = ortherData.getAbout_res();
        if (null == about_res || about_res.size() == 0) {
            mLlRelatedRoot.setVisibility(View.GONE);
        } else {
            mAdapter.clear();
            mAdapter.addAll(ortherData.getAbout_res());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWvLayout != null) {
            mWvLayout.loadDataWithBaseURL(null, "", "text/html", "utf_8", null);
            mWvLayout.clearHistory();
            ((ViewGroup) mWvLayout.getParent()).removeView(mWvLayout);
            mWvLayout.destroy();
            mWvLayout = null;
        }
    }
}

