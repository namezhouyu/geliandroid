package com.geli.m.coustomview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.ConsoleMessage;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.utils.StringUtils;

/**
 *
 * 有一个水平进度条的 WebView  -- 可复制内容
 */

public class ProgressWebView extends WebView {
    /* 长按的时候显示的 -- Item */
    // private String[] mTitleList = new String[]{"复制", "分享", "搜索"};
    private String[] mTitleList = new String[]{"复制"};
    private ActionMode mActionMode;

    private ProgressBar mProgressBar;
    private TextView mTitleView;
    private String mTitle;

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal); // 水平进度条
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 8);
        mProgressBar.setLayoutParams(layoutParams);

        Drawable drawable = context.getResources().getDrawable(
                R.drawable.web_progress_bar_states);
        mProgressBar.setProgressDrawable(drawable);
        addView(mProgressBar);
        setWebChromeClient(new WebChromeClient());
    }

    public void setTitleView(TextView titleView, String title) {
        mTitleView = titleView;
        mTitle = title;
        if(mTitleView != null && StringUtils.isNotEmpty(mTitle)){
            mTitleView.setText(title);
        }
    }



    public class WebChromeClient extends android.webkit.WebChromeClient {

        // web 加载数据的改变 -- 用进度框显示
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressBar.setVisibility(GONE);
            } else {
                if (mProgressBar.getVisibility() == GONE)
                    mProgressBar.setVisibility(VISIBLE);
                mProgressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (mTitleView != null && StringUtils.isEmpty(mTitle)) {
                mTitleView.setText(title);
            }
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            return super.onConsoleMessage(consoleMessage);
        }
    }


    // 进度框的位置
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        LayoutParams lp = (LayoutParams) mProgressBar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        mProgressBar.setLayoutParams(lp);
    }




    /*----------------------------------- 长按显示选择文本 -----------------------------------------*/
    // 如果不出现 问下做网页的有没有设置 --
    //    *{
    //        -webkit-touch-callout:none;   /*系统默认菜单被禁用*/
    //        -webkit-user-select:none;     /*webkit浏览器*/
    //        -khtml-user-select:none;      /*早期浏览器*/
    //        -moz-user-select:none;        /*火狐*/
    //        -ms-user-select:none;         /*IE10*/
    //        user-select:none;
    //    }
    //
    //    none 改为 text

    @Override
    public ActionMode startActionMode(ActionMode.Callback callback) {
        ActionMode actionMode = super.startActionMode(callback);
        return resolveMode(actionMode);
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback callback, int type) {
        ActionMode actionMode = super.startActionMode(callback, type);
        return resolveMode(actionMode);
    }

    public ActionMode resolveMode(ActionMode actionMode) {

        if(actionMode!=null){

            final Menu menu = actionMode.getMenu();
            menu.clear();
            for (int i = 0; i < mTitleList.length; i++) {
                menu.add(mTitleList[i]);
            }
            for (int i = 0; i < mTitleList.length; i++) {
                MenuItem item = menu.getItem(i);
                item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        String title = menuItem.getTitle().toString();
                        getSelectedData(title);     // 获取选中的h5页面的文本
                        releaseActionMode();
                        return true;
                    }
                });
            }
            this.mActionMode = actionMode;
        }
        return actionMode;
    }

    /**
     * 当点击ActionMode的item的之后，将我们的actionMode finish掉
     */
    public void releaseActionMode() {
        if (mActionMode != null) {
            mActionMode.finish();
            mActionMode = null;
        }
    }

    /**
     * 点击的时候，获取网页中选择的文本，回掉到原生中的js接口
     * @param title 传入点击的item文本，一起通过js返回给原生接口
     */
    private void getSelectedData(String title) {

        String js = "(function getSelectedText() {" +
                "var txt;" +
                "var title = \"" + title + "\";" +
                "if (window.getSelection) {" +
                "txt = window.getSelection().toString();" +
                "} else if (window.document.getSelection) {" +
                "txt = window.document.getSelection().toString();" +
                "} else if (window.document.selection) {" +
                "txt = window.document.selection.createRange().text;" +
                "}" +
                "ActionModeJavaScript.callback(txt,title);" +           //回调java方法将js获取的结果传递过去
                "})()";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {  //android系统4.4以上的时候调用js方法用这个
            evaluateJavascript("javascript:" + js, null);
        } else {
            loadUrl("javascript:" + js);
        }
    }

}
