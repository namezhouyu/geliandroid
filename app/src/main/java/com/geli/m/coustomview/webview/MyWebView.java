package com.geli.m.coustomview.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.geli.m.app.GlobalData;

/**
 * author:  shen
 * date:    2019/8/6
 */
public class MyWebView extends WebView {

    View mErrorView;
    MyWebViewClient mClient;

    public MyWebView(Context context) {
        super(context);
        initDate();
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDate();
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDate();
    }


    private void initDate() {
        WebSettings webSettings = getSettings();
        webSettings.setUseWideViewPort(true);                       // 设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setTextZoom(350);
        // webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        if (GlobalData.hasNetWork) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);     //根据cache-control决定是否从网络上取数据。
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        }


//        webSettings.setAllowFileAccess(true);
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);//排版适应屏幕


        addJavascriptInterface(new MJavascriptInterface(getContext()), "imagelistener");
        setWebViewClient(mClient = new MyWebViewClient());
    }


    public void setErrorView(View errorView) {
        mErrorView = errorView;

        if(mErrorView != null && mClient != null){
            mClient.setErrorView(mErrorView);
        }
    }
}
