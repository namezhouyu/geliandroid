package com.geli.m.mvp.home.other;

import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.api.UrlSet;
import com.geli.m.app.GlobalData;
import com.geli.m.coustomview.ProgressWebView;
import com.geli.m.dialog.ShareDialog;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.utils.Utils;

import static com.geli.m.config.Constant.INTENT_CONTENT;
import static com.geli.m.config.Constant.INTENT_IS_SHOW_TITLE;
import static com.geli.m.config.Constant.INTENT_LINKS;
import static com.geli.m.config.Constant.INTENT_TITLE;

/**
 * Created by Administrator on 2017/10/31.
 */

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.wv_layout)
    ProgressWebView mWvLayout;

    @BindView(R.id.rl_title_rootlayout)
    RelativeLayout mRLayoutTitle;
    @BindView(R.id.tv_title_name)
    TextView mTvTitle;
    @BindView(R.id.iv_title_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title_right)
    TextView mTvRight;

//    @BindView(R.id.iv_share)    //关于我们的分享按钮，其他地方不显示
//    ImageView mIvShare;


    /** 链接 */
    public String mLinkeUrl = "";
    private String mContent;
    private String mTitle;
    /** 是否显示标题 */
    private boolean isShowTitle = true;
    private boolean showShare;//是否显示分享
    public static final String EXTRAS_IS_SHARE = "extras_is_share";


    @Override
    protected int getResId() {
        return R.layout.activity_webview;
    }

    @Override
    public void onResume() {
        super.onResume();
        mWvLayout.onResume();
        mWvLayout.resumeTimers();
    }

    @Override
    protected void init() {
        mLinkeUrl = getIntent().getStringExtra(INTENT_LINKS);
        isShowTitle = getIntent().getBooleanExtra(INTENT_IS_SHOW_TITLE, isShowTitle);
        showShare = getIntent().getBooleanExtra(EXTRAS_IS_SHARE, false);

        if (showShare){  //是关于我们的页面，显示分享的按钮
            mTvRight.setVisibility(View.VISIBLE);
            mTvRight.setText("分享");
        }

        if (!TextUtils.isEmpty(mLinkeUrl)
                && !(mLinkeUrl.startsWith(UrlSet.HTTP) || mLinkeUrl.startsWith(UrlSet.HTTPS))) { // 检查 链接头部
            mLinkeUrl = UrlSet.HTTP + mLinkeUrl;
        }
        mContent = getIntent().getStringExtra(INTENT_CONTENT);
        mTitle = getIntent().getStringExtra(INTENT_TITLE);

    }

    @Override
    protected void initData() {
        setView();
        setWvLayout();
    }


    @Override
    protected void initEvent() {

    }

    private void setView() {
        mTvTitle.setTextSize(16);

        if (!isShowTitle) {
            mRLayoutTitle.setVisibility(View.GONE);
        }

    }

    private void setWvLayout() {

        if (TextUtils.isEmpty(mContent)) {
            mWvLayout.loadUrl(mLinkeUrl);
            mWvLayout.setTitleView(mTvTitle, mTitle);
        } else {
            mWvLayout.loadData(mContent, "text/html; charset=UTF-8", null);
            mTvTitle.setText(mTitle);
        }

        mWvLayout.clearCache(true);
        mWvLayout.clearHistory();


        WebSettings webSettings = mWvLayout.getSettings();
        webSettings.setDatabaseEnabled(true);

        /* 缓存白屏 */
        String mAppCachePath = getApplicationContext().getCacheDir().getAbsolutePath() + "/webcache";

        /* 设置 Application Caches 缓存目录 */
        webSettings.setAppCachePath(mAppCachePath);
        webSettings.setDatabasePath(mAppCachePath);

        /* 自适应屏幕 */
        webSettings.setUseWideViewPort(true);       //设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);

        /* 便页面支持缩放 */
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        mWvLayout.requestFocusFromTouch();

        /* 允许调用js */
        webSettings.setJavaScriptEnabled(true);
        //webSettings.setPluginState(true);             // 支持插件
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 支持内容重新布局
        if (GlobalData.hasNetWork) {
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        webSettings.setNeedInitialFocus(true);          // 当webview调用requestFocus时为webview设置节点
        webSettings.setDomStorageEnabled(true);
        mWvLayout.requestFocus();

        // 长按弹出选择文本 -- 点击对应的项触发对应的事件
        mWvLayout.addJavascriptInterface(new ActionModeJavaScript(),"ActionModeJavaScript");

        mWvLayout.setWebViewClient(new TempWebViewClient());
    }

    @OnClick({R.id.iv_title_back, R.id.tv_title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.tv_title_right:   //分享
                ShareDialog.newInstance("格利食品网", "点击下载APP", "", mLinkeUrl)
                        .show(getSupportFragmentManager(), "");
                break;
        }
    }

    /**
     * 设置不用系统浏览器打开,直接显示在当前Webview
     */
    public class TempWebViewClient extends WebViewClient{

//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            view.getSettings().setJavaScriptEnabled(true);
//            super.onPageStarted(view, url, favicon);
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            view.getSettings().setJavaScriptEnabled(true);
//            super.onPageFinished(view, url);
//            // addImageClickListener(view);    // 待网页加载完全后设置图片点击的监听方法
//        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return shouldOverrideUrlLoading(view, request.getUrl().toString());
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("tel")) {
                String[] split = url.split(":");
                if (split.length >= 2) {
                    Utils.callPhone(WebViewActivity.this, split[1]);
                    return true;
                }
            }
            view.loadUrl(url);
            return true;
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
            handler.proceed();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWvLayout.canGoBack()) {
            mWvLayout.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mWvLayout != null) {
            mWvLayout.loadDataWithBaseURL(null, "", "text/html", "utf_8", null);
            mWvLayout.clearHistory();

            ((ViewGroup) mWvLayout.getParent()).removeView(mWvLayout);
            mWvLayout.destroy();
            mWvLayout = null;
        }
        super.onDestroy();
    }


    private class ActionModeJavaScript {

        @JavascriptInterface
        public void callback(String text,String menuItemTitle){
            // ToastUtils.showToast("标题："+menuItemTitle+" -- 内容："+text);

            if(menuItemTitle.equals("复制")){
                Utils.copy(WebViewActivity.this, text);
            }
        }
    }
}
