package com.geli.m.mvp.home.cart_fragment.main;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.geli.m.R;
import com.geli.m.coustomview.MyEasyRecyclerView;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.may_you_like.view_holder.MayYouLikeVH;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * author:  shen
 * date:    2018/10/30
 */
public class CartBottomVH implements RecyclerArrayAdapter.ItemView{

    private MyEasyRecyclerView mErv_bottom_list;
    private RecyclerArrayAdapter mBottomAdapter;
    private RecyclerView.ItemDecoration bottomItemDecoration;

    Context mContext;
    public CartBottomVH(Context context){
        mContext = context;
    }

    @Override
    public View onCreateView(ViewGroup parent) {
        return LayoutInflater.from(mContext).inflate(R.layout.itemview_cart_bootom_mayyoulike, parent, false);
    }

    @Override
    public void onBindView(View headerView) {
        List data = new ArrayList();
        for (int i = 0; i < 11; i++) {
            data.add(i);
        }
        List data1 = new ArrayList();
        List<List> info = new ArrayList();
        for (int i = 1; i <= data.size(); i++) {
            data1.add(i - 1);
            if (i % 2 == 0 || i - 1 == data.size() - 1) {
                info.add(data1);
                data1 = new ArrayList();
            }
        }
        mErv_bottom_list = (MyEasyRecyclerView) headerView.findViewById(R.id.erv_cart_bottom_list);
        mErv_bottom_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        if (bottomItemDecoration != null) {
            mErv_bottom_list.removeItemDecoration(bottomItemDecoration);
        } else {
            bottomItemDecoration = new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    if (parent.getChildAdapterPosition(view) == 0) {
                        outRect.left = Utils.dip2px(mContext, 15);
                    } else {
                        outRect.left = Utils.dip2px(mContext, 10);
                    }
                    if (parent.getChildAdapterPosition(view) == mBottomAdapter.getAllData().size() - 1) {
                        outRect.right = Utils.dip2px(mContext, 15);
                    } else {
                        outRect.right = 0;
                    }
                }
            };
        }
        mErv_bottom_list.addItemDecoration(bottomItemDecoration);
        mErv_bottom_list.setAdapterWithProgress(mBottomAdapter = new RecyclerArrayAdapter(mContext, info) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new MayYouLikeVH(parent, mContext);
            }
        });
        ViewCompat.setNestedScrollingEnabled(mErv_bottom_list.getRecyclerView(), false);
    }
}
