package com.geli.m.mvp.home.find_fragment.findlist_fragment.details;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.http.SslError;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.FindDetailsBean;
import com.geli.m.coustomview.MyVideoView;
import com.geli.m.dialog.CommentDialog;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.other.goodsdetails_activity.GoodsDetailsActivity;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.PriceUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.NetworkUtils;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.geli.m.config.Constant.INTENT_FIND_ID;
import static com.geli.m.config.Constant.INTENT_GOODS_ID;

/**
 * Created by Steam_l on 2018/2/27.
 *
 * 视频
 */
public class VideoDetailsActivity extends MVPActivity<FindDetailsPresentImpl> implements View.OnClickListener, FindDetailsView, MyVideoView.TitleOnclickListener {

    /** 最顶部视频 */
    @BindView(R.id.mvv_videodetails)
    MyVideoView mVideoView;
    /** 文章的详情 -- 网页的数据 */
    @BindView(R.id.wv_video_details)
    WebView mWvLayout;

    /** 包裹"底部相关文章"的布局 */
    @BindView(R.id.ll_video_related_root)
    LinearLayout mLlRelatedRoot;
    /** 底部相关文章下的列表 */
    @BindView(R.id.erv_video_related_list)
    EasyRecyclerView mErvRelatedList;


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

    public ImageView mIv_centerstart;
    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;
    private CommentDialog mCommentDialog;


    private String find_id;
    private RecyclerArrayAdapter<FindDetailsBean.DataEntity.AboutResEntity> mAdapter;
    public FindDetailsBean.DataEntity.GoodsResEntity mGoods_res;
    public int mIs_collection;
    private FindDetailsBean.DataEntity.FindResEntity mFind_res;

    @Override
    protected int getResId() {
        return R.layout.activity_videodetails;
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

        setErv();
        setWeb();
        mPresenter.getDetails(GlobalData.getUser_id(), find_id);
    }

    @Override
    protected void initEvent() {
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

        mVideoView.setStateChangeListener(new MyVideoView.StateChangeListener() {
            @Override
            public void hide() {
                hideStateBar();
            }

            @Override
            public void show() {
                showStateBar();
            }
        });
    }

    private void setErv() {
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
        //自适应屏幕
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        //便页面支持缩放：
        //        webSettings.setSupportZoom(true);
        //        webSettings.setBuiltInZoomControls(true);
        //        webSettings.setDisplayZoomControls(false);
        mWvLayout.requestFocusFromTouch();
        //允许调用js
        webSettings.setJavaScriptEnabled(true);
        //webSettings.setPluginState(true);  //支持插件
        //        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        if (GlobalData.hasNetWork) {
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点

        //设置不用系统浏览器打开,直接显示在当前Webview
        mWvLayout.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
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



    private void setVideoData(final String url, String imgUrl) {
        // String url = "http://v6-nh.ixigua.com/video/m/220a3e86a265f1848348b272cbbd981faa31154b7060000242af35bb54c/?Expires=1519785307&AWSAccessKeyId=qh0h9TdcEMoS2oPj7aKX&Signature=hMmwSkkzC9jNUuLqQWF24CcPkQQ%3D";

        //断网自动重新链接，url前接上ijkhttphook:
        //String url = "ijkhttphook:https://res.exexm.com/cw_145225549855002";

        //String url = "http://7xjmzj.com1.z0.glb.clouddn.com/20171026175005_JObCxCE2.mp4";

        //        String url = "http://video.7k.cn/app_video/20171202/6c8cf3ea/v.m3u8.mp4";
        //        String url =  "http://devimages.apple.com.edgekey.net/streaming/examples/bipbop_4x3/bipbop_4x3_variant.m3u8";
        //String url =  "rtsp://cloud.easydarwin.org:554/stream0.sdp";
        //        String url = "http://s.swao.cn/o_1c4gm8o1nniu1had13bk1t0l1rq64m.mov";
        //        String url = "http://api.ciguang.tv/avideo/play?num=02-041-0491&type=flv&v=1&client=android";
        //String url = "http://video.7k.cn/app_video/20171213/276d8195/v.m3u8.mp4";
        //        String url = "http://103.233.191.21/riak/riak-bucket/6469ac502e813a4c1df7c99f364e70c1.mp4";
        //String url = "http://7xjmzj.com1.z0.glb.clouddn.com/20171026175005_JObCxCE2.mp4";
        //String url = "https://media6.smartstudy.com/ae/07/3997/2/dest.m3u8";
        //String url = "http://cdn.tiaobatiaoba.com/Upload/square/2017-11-02/1509585140_1279.mp4";

        //String url = "http://hcjs2ra2rytd8v8np1q.exp.bcevod.com/mda-hegtjx8n5e8jt9zv/mda-hegtjx8n5e8jt9zv.m3u8";
        //String url = "http://7xse1z.com1.z0.glb.clouddn.com/1491813192";
        //String url = "http://ocgk7i2aj.bkt.clouddn.com/17651ac2-693c-47e9-b2d2-b731571bad37";
        //String url = "http://111.198.24.133:83/yyy_login_server/pic/YB059284/97778276040859/1.mp4";
        //String url = "http://vr.tudou.com/v2proxy/v?sid=95001&id=496378919&st=3&pw=";
        //String url = "http://pl-ali.youku.com/playlist/m3u8?type=mp4&ts=1490185963&keyframe=0&vid=XMjYxOTQ1Mzg2MA==&ep=ciadGkiFU8cF4SvajD8bYyuwJiYHXJZ3rHbN%2FrYDAcZuH%2BrC6DPcqJ21TPs%3D&sid=04901859548541247bba8&token=0524&ctype=12&ev=1&oip=976319194";
        //String url = "http://hls.ciguang.tv/hdtv/video.m3u8";
        //String url = "https://res.exexm.com/cw_145225549855002";
        //String url = "http://storage.gzstv.net/uploads/media/huangmeiyan/jr05-09.mp4";//mepg

        //detailPlayer.setUp(url, false, null, "测试视频");
        //detailPlayer.setLooping(true);
        //detailPlayer.setShowPauseCover(false);

        //如果视频帧数太高导致卡画面不同步
        //VideoOptionModel videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 5);
        //如果视频seek之后从头播放
        //VideoOptionModel videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "enable-accurate-seek", 1);
        //List<VideoOptionModel> list = new ArrayList<>();
        //list.add(videoOptionModel);
        //GSYVideoManager.instance().setOptionModelList(list);

        //GSYVideoManager.instance().setTimeOut(4000, true);


        TextView textView = new TextView(mContext);
        textView.setText(Utils.getString(R.string.wifi_toast));
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        textView.setPadding(0, 0, 0, Utils.dip2px(mContext, 15));
        textView.setBackgroundColor(Color.BLACK);
        initVideoData(url, textView, imgUrl);
    }

    private void initVideoData(String url, View thumbview, String imgUrl) {
        // detailPlayer.setThumbImageView(imageView);
        // 增加封面
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.img_loading)
                .centerCrop()
                .error(R.drawable.img_jiazaishibei);
        final ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(mContext).load(imgUrl).apply(options).into(imageView);

        // 外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, mVideoView);
        // 初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        mVideoView.setEnlargeImageRes(R.drawable.btn_quanping);
        mVideoView.setShrinkImageRes(R.drawable.btn_banping);
        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption.setThumbImageView(thumbview)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setSeekRatio(1)
                .setUrl(url)
                .setCacheWithPlay(false)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        Debuger.printfError("***** onPrepared **** " + objects[0]);
                        Debuger.printfError("***** onPrepared **** " + objects[1]);
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        orientationUtils.setEnable(true);
                        isPlay = true;
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        Debuger.printfError("***** onEnterFullscreen **** " + objects[0]);//title
                        Debuger.printfError("***** onEnterFullscreen **** " + objects[1]);//当前全屏player
                    }

                    @Override
                    public void onClickStopFullscreen(String url, Object... objects) {
                        super.onClickStopFullscreen(url, objects);
                        if (mIv_centerstart != null) {
                            mIv_centerstart.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onClickResumeFullscreen(String url, Object... objects) {
                        super.onClickResumeFullscreen(url, objects);
                        if (mIv_centerstart != null) {
                            mIv_centerstart.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[0]);//title
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[1]);//当前非全屏player
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                    }

                    @Override
                    public void onAutoComplete(String url, Object... objects) {
                        super.onAutoComplete(url, objects);
                        getCurPlay().setThumbImageView(imageView);
                    }
                })
                .setLockClickListener(new LockClickListener() {
                    @Override
                    public void onClick(View view, boolean lock) {
                        if (orientationUtils != null) {
                            //配合下方的onConfigurationChanged
                            orientationUtils.setEnable(!lock);
                        }
                    }
                })
                .setGSYVideoProgressListener(new GSYVideoProgressListener() {
                    @Override
                    public void onProgress(int progress, int secProgress, int currentPosition, int duration) {
                        Debuger.printfLog(" progress " + progress + " secProgress " + secProgress + " currentPosition " + currentPosition + " duration " + duration);
                    }
                })
                .build(mVideoView);

        mVideoView.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 直接横屏
                orientationUtils.resolveByClick();

                // 第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                mVideoView.startWindowFullscreen(VideoDetailsActivity.this, true, true);
            }
        });
        if (NetworkUtils.isWifiConnected(mContext)) {
            mVideoView.startPlayLogic();
            getCurPlay().setThumbImageView(imageView);
        }
    }




    public void hideStateBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
    }

    public void showStateBar() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
    }

    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }

        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        getCurPlay().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        getCurPlay().onVideoResume();
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay && getCurPlay() != null) {
            getCurPlay().release();
        }
        //GSYPreViewManager.instance().releaseMediaPlayer();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
        if (mWvLayout != null) {
            mWvLayout.loadDataWithBaseURL(null, "", "text/html", "utf_8", null);
            mWvLayout.clearHistory();
            ((ViewGroup) mWvLayout.getParent()).removeView(mWvLayout);
            mWvLayout.destroy();
            mWvLayout = null;
        }
    }

    @Override
    protected FindDetailsPresentImpl createPresenter() {
        return new FindDetailsPresentImpl(this);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 如果旋转了就全屏
        if (isPlay && !isPause) {
            mVideoView.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
        }
    }

    private GSYVideoPlayer getCurPlay() {
        if (null != mVideoView && null != mVideoView.getFullWindowPlayer()) {
            return mVideoView.getFullWindowPlayer();
        }
        return mVideoView;
    }

    @OnClick({R.id.bt_video_goods_checkdetails, R.id.cb_video_like, R.id.rl_video_goodsroot})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cb_video_like:
                if (mFind_res != null) {
                    if (mCbLike.isChecked()) {
                        mFind_res.setLike_num(mFind_res.getLike_num() + 1);
                    } else {
                        mFind_res.setLike_num(mFind_res.getLike_num() - 1);
                    }
                    mCbLike.setText("(" + mFind_res.getLike_num() + ")");
                    mPresenter.like(GlobalData.getUser_id(), find_id);
                }
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

    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
    }

    @Override
    public void onError(String msg) {
        ShowSingleToast.showToast(mContext, msg);
        if (mPresenter.isColl) {
            mPresenter.isColl = false;
            if (mIs_collection == 1) {
                mVideoView.setIsColl(false);
            } else {
                mVideoView.setIsColl(true);
            }
            return;
        }
        if (mPresenter.isLike) {
            mPresenter.isLike = false;
            mFind_res.setLike_num(mFind_res.getLike_num() - 1);
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
        FindDetailsBean.DataEntity.FindResEntity find_res = dataEntity.getFind_res();
        setVideoData(find_res.getVideo_url(), find_res.getCover_url().get(0));
        setOrtherData(dataEntity);
    }

    public void setOrtherData(FindDetailsBean.DataEntity ortherData) {
        mFind_res = ortherData.getFind_res();
        mWvLayout.loadUrl(mFind_res.getContent_url());
        mTvTitle.setText(mFind_res.getArticle_title());
        FindDetailsBean.DataEntity.FindResEntity.AuthorResEntity author_res = mFind_res.getAuthor_res();
        if (author_res != null) {
            GlideUtils.loadImg(mContext, author_res.getAuthor_icon(), mRivAvatar);
            mTvName.setText(author_res.getAuthor_name());
        }
        mIs_collection = mFind_res.getIs_collection();
        mVideoView.setIsColl(mIs_collection == 1);
        mCbLike.setChecked(mFind_res.getIs_like() == 1);
        mCbLike.setText("(" + mFind_res.getLike_num() + ")");
        SpannableString text = new SpannableString(Utils.getString(R.string.view_number) + "(" + mFind_res.getView_num() + ")");
        text.setSpan(new ForegroundColorSpan(Utils.getColor(R.color.zhusediao)), Utils.getString(R.string.view_number).length(), text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTvViewNumber.setText(text);

        FindDetailsBean.DataEntity.GoodsResEntity goods_res = ortherData.getGoods_res();
        if (goods_res != null) {
            GlideUtils.loadImg(mContext, goods_res.getGoods_thumb(), mIvGoodsImg);
            mTvGoodsMess.setText(goods_res.getGoods_intro());
            mTvGoodsPrice.setText(PriceUtils.getPrice(goods_res.getShop_price()));
            mTvGoodsTitle.setText(goods_res.getGoods_name());
        }

        List<FindDetailsBean.DataEntity.AboutResEntity> about_res = ortherData.getAbout_res();
        if (null == about_res || about_res.size() == 0) {
            mLlRelatedRoot.setVisibility(View.GONE);
        } else {
            mAdapter.clear();
            mAdapter.addAll(ortherData.getAbout_res());
        }
    }

    @Override
    public void shear() {

    }

    @Override
    public void coll(boolean isColl) {
        mVideoView.setIsColl(isColl);
        Map<String, String> data = new HashMap();
        data.put("col_type", "4");
        data.put("find_id", find_id);
        data.put("user_id", GlobalData.getUser_id());
        mPresenter.collection(data);
    }

}
