package com.geli.m.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.geli.m.R;
import com.geli.m.coustomview.ErrorView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import static com.geli.m.app.GlobalData.mContext;

/**
 * author:  shen
 * date:    2018/10/19
 */
public class EasyRecyclerViewUtils {

    public abstract static class AdapterListener {
        public void onMoreShow() {
        }

        public void onMoreClick() {

        }

        public void onErrorShow() {

        }

        public void onErrorClick() {

        }
    }

    public static void initAdapter(RecyclerArrayAdapter adapter, AdapterListener listener) {
        initAdapter(adapter, -1, -1, -1, listener);
    }


    /**
     *
     *
     * @param adapter
     * @param loadingId     加载的时候的界面(一般是上部分)
     * @param nomoreId      加载了全部数据下面显示的界面
     * @param errorId       错误的时候显示的界面
     * @param listener      适配器里的点击事件
     */
    public static void initAdapter(RecyclerArrayAdapter adapter, int loadingId, int nomoreId, int errorId, final AdapterListener listener) {
        if (loadingId == -1) {
            loadingId = R.layout.layout_loadingmore;
        }

        adapter.setMore(loadingId, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                listener.onMoreShow();
            }

            @Override
            public void onMoreClick() {
                listener.onMoreClick();

            }
        });

        /* 加载了全部数据下面显示的界面 */
        if (nomoreId == -1) {
            nomoreId = R.layout.layout_nomore;
        }
        adapter.setNoMore(nomoreId);

        /* 错误的时候显示的界面 */
        if (errorId == -1) {
            errorId = R.layout.layout_more_error;
        }
        adapter.setError(errorId, new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {
                listener.onErrorShow();
            }

            @Override
            public void onErrorClick() {
                listener.onErrorClick();
            }
        });
    }

    /**
     * 默认设置 -- "空数据、错误数据时显示的界面"
     *
     * @param view
     */
    public static void initEasyRecyclerView(EasyRecyclerView view) {
        initEasyRecyclerView(view, -1, -1);
    }

    /**
     * "空数据、错误数据时显示的界面"
     * @param view
     * @param listener 错误界面的刷新监听事件
     */
    public static void initEasyRecyclerView(EasyRecyclerView view, ErrorView.ClickRefreshListener listener) {
        initEasyRecyclerView(view, -1, -1, listener);
    }

    /**
     * "空数据、错误数据时显示的界面"  -- 没有监听
     * @param view
     * @param emptyId
     * @param errorId
     */
    public static void initEasyRecyclerView(EasyRecyclerView view, Object emptyId, Object errorId) {
        initEasyRecyclerView(view, emptyId, errorId, null);
    }


    public static void initEasyRecyclerView(EasyRecyclerView view, Object emptyId, Object errorId,
                                            final ErrorView.ClickRefreshListener listener) {

        /* 错误界面 -- 数据出错之类 */
        if (errorId instanceof Integer) {
            if ((int) errorId == -1) {
                View errorview = LayoutInflater.from(mContext).inflate(R.layout.layout_error_data,
                        new LinearLayout(mContext), false);     // 为了点击事件，所以这里要填充出来获取控件
                if (listener != null) {
                    errorview.findViewById(R.id.bt_errorview_refresh).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.clickRefresh();            // 点击刷新按钮事件
                        }
                    });
                }
                view.setErrorView(errorview);                   // EasyRecyclerView 设置出错界面
            } else {
                view.setErrorView((int) errorId);
            }
        } else {
            view.setErrorView((View) errorId);
        }


        /* 无数据界面 -- 暂无数据 */
        if (emptyId instanceof Integer) {
            if ((int) emptyId == -1) {
                view.setEmptyView(R.layout.layout_empty_data);
            } else {
                view.setEmptyView((int) emptyId);
            }
        } else {
            view.setEmptyView((View) emptyId);
        }
    }

}
