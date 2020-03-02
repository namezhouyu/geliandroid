package com.geli.m.coustomview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import com.geli.m.R;

/**
 * Created by Steam_l on 2018/1/10.
 */

public class ErrorView extends RelativeLayout {
    public ErrorView(Context context) {
        this(context, null);
    }

    public ErrorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(R.id.bt_errorview_refresh).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickRefreshListener != null) {
                    mClickRefreshListener.clickRefresh();
                }
            }
        });
    }

    ClickRefreshListener mClickRefreshListener;

    public interface ClickRefreshListener {
        void clickRefresh();
    }

    public void setClickRefreshListener(ClickRefreshListener listener) {
        mClickRefreshListener = listener;
    }
}
