package com.geli.m.popup.listpopup;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.geli.m.R;
import com.geli.m.popup.BasePopupWindow;
import com.geli.m.utils.ResourceUtil;
import com.geli.m.utils.ScreenUtils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import java.util.List;

import static android.widget.ListPopupWindow.MATCH_PARENT;
import static android.widget.ListPopupWindow.WRAP_CONTENT;

/**
 * author:  shen
 * date:    2019/2/22
 */
public abstract class MyListPopupWindow extends BasePopupWindow {

    EasyRecyclerView mErv;
    RecyclerArrayAdapter mAdapter;

    protected abstract void adapterItemClick(Object o);

    /**
     *
     * @param context
     * @param bgColor       0~1   1:没有灰度
     */
    public MyListPopupWindow(Activity context, float bgColor) {
//        super(context, R.layout.list_popup,
//                ScreenUtils.dp2PxInt(context, 90f),
//                ScreenUtils.dp2PxInt(context, 80f), bgColor);

        super(context, R.layout.list_popup, MATCH_PARENT, WRAP_CONTENT, bgColor);
    }

    @Override
    protected void initView() {
        View view = getContentView();
        mErv = (EasyRecyclerView) view.findViewById(R.id.erv_ListPopup);
    }

    @Override
    protected void initEvent() {
        initErv();
    }


    private void initErv() {
        mErv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mErv.addItemDecoration(new DividerDecoration(ResourceUtil.getColor(R.color.line_color),
                ScreenUtils.dp2PxInt(mActivity, 1f), 0, 0));

        mErv.setAdapterWithProgress(initAdapter());
    }

    private RecyclerArrayAdapter initAdapter() {
        mAdapter = new RecyclerArrayAdapter(mActivity) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new PopupViewHolder(parent, mActivity);
            }
        };

        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Object o = mAdapter.getItem(position);
                MyListPopupWindow.this.getPopupWindow().dismiss();
                adapterItemClick(o);
            }
        });

        return mAdapter;
    }

    public void setList(List list) {
        mAdapter.clear();
        mAdapter.addAll(list);
    }
}
