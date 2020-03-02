package com.geli.m.coustomview.webview;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by pks on 2017/7/14.
 *
 */
public class MyWebViewClient extends WebViewClient {
    View errorView;

    public MyWebViewClient(View errorView) {
        this.errorView = errorView;
    }

    public MyWebViewClient() {

    }

    @Override
    public void onPageFinished(WebView view, String url) {
        view.getSettings().setJavaScriptEnabled(true);
        super.onPageFinished(view, url);
        addImageClickListener(view);    // 待网页加载完全后设置图片点击的监听方法
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
    }

    // HTTP请求异常的时候
    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
        view.setVisibility(View.GONE);
        if (errorView != null) {
            errorView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        view.getSettings().setJavaScriptEnabled(true);
        super.onPageStarted(view, url, favicon);
    }

    private void addImageClickListener(WebView webView) {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++) " +
                "{"
                + " objs[i].onclick=function() " +
                " { "
                /* 通过js代码找到标签为img的代码块，设置点击的监听方法与本地的openImage方法进行连接 */
                + "  window.imagelistener.openImage(this.src); " +
                " } " +
                "}" +
                "})()");
    }

    public void setErrorView(View errorView) {
        this.errorView = errorView;
    }
}