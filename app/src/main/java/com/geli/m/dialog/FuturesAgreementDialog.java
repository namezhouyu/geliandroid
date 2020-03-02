package com.geli.m.dialog;

import android.net.http.SslError;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.dialog.base.BaseDialogFragment;
import com.geli.m.utils.Utils;

/**
 * Created by Steam_l on 2018/1/5.
 *
 * 期货协议弹窗
 */
public class FuturesAgreementDialog extends BaseDialogFragment implements View.OnClickListener {
    @BindView(R.id.wv_dialog_futures_content)
    WebView mWebView;
    @BindView(R.id.tv_dialog_futures_title)
    TextView tv_title;
    String content;
    int type;

    private FuturesAgreementDialog(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public static FuturesAgreementDialog newInstance(String content, int type) {
        return new FuturesAgreementDialog(content, type);
    }

    private Listener listener;

    public interface Listener {
        void agree();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void initData() {
        if (type == 4) {
            tv_title.setText(Utils.getString(R.string.group_buy_agreement));
        } else {
            tv_title.setText(Utils.getString(R.string.futures_agreement));
        }
        initWeb();
    }

    private void initWeb() {
        WebSettings webSettings = mWebView.getSettings();
        mWebView.loadData(content, "text/html; charset=UTF-8", null);
        //        WebChromeClient wvcc = new WebChromeClient() {
        //            @Override
        //            public void onReceivedTitle(WebView view, String title) {
        //                super.onReceivedTitle(view, title);
        //                mTvTitle.setText(title);
        //            }
        //        };
        //        mWv_layout.setWebChromeClient(wvcc);
        //自适应屏幕
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setTextZoom(360);
        //便页面支持缩放：
        //        webSettings.setSupportZoom(true);
        //        webSettings.setBuiltInZoomControls(true);
        //        webSettings.setDisplayZoomControls(false);
        mWebView.requestFocusFromTouch();
        // 允许调用js
        webSettings.setJavaScriptEnabled(true);
        //webSettings.setPluginState(true);  //支持插件
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // webview等比例大小
        if (GlobalData.hasNetWork) {
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webSettings.setNeedInitialFocus(true); // 当webview调用requestFocus时为webview设置节点

        //设置不用系统浏览器打开,直接显示在当前Webview
        mWebView.setWebViewClient(new WebViewClient() {

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
    protected void initEvent() {

    }

    @Override
    protected EditText getEt() {
        return null;
    }

    @Override
    protected int getResId() {
        return R.layout.dialog_futures_agreement;
    }

    @OnClick({R.id.tv_dialog_futures_agree, R.id.tv_dialog_futures_refuse, R.id.iv_dialog_futures_close})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_dialog_futures_agree:
                if (listener != null) {
                    listener.agree();
                }
                break;

            case R.id.tv_dialog_futures_refuse:
                dismiss();
                break;

            case R.id.iv_dialog_futures_close:
                dismiss();
                break;

            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf_8", null);
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
    }
}
