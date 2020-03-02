package com.geli.m.drawer.menudrawer;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.bean.RestaurantGoodsShopScreen;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * <p>
 * 食品馆列表
 */

public class MenuVH extends BaseViewHolder<RestaurantGoodsShopScreen.DataBeanX> {

    Context mContext;

    @BindView(R.id.layout_title_VHMenu)
    LinearLayout mLayoutTitle;
    /** 标题 */
    @BindView(R.id.tv_title_VHMenu)
    TextView mTvTitle;
    /** 所有选中的值 */
    @BindView(R.id.tv_selectItemValue_VHMenu)
    TextView mTvSelectItemValue;

    @BindView(R.id.erv_VHMenu)
    EasyRecyclerView mErv;

    RecyclerArrayAdapter mAdapter;

    public MenuVH(ViewGroup parent, Context context) {
        super(parent, R.layout.vh_menu);
        mContext = context;
        ButterKnife.bind(this, itemView);

        initErv();
    }

    private void initErv() {
        mErv.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mErv.setAdapter(initAdapter());
    }

    private RecyclerArrayAdapter initAdapter(){
        mAdapter = new RecyclerArrayAdapter(getContext()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new MenuChildVH(parent, getContext());
            }
        };
        return mAdapter;
    }

    @Override
    public void setData(RestaurantGoodsShopScreen.DataBeanX data) {
        super.setData(data);

        mTvTitle.setText(data.getScreen_name());
        mAdapter.clear();
        mAdapter.addAll(data.getData());
    }


    @OnClick({R.id.layout_title_VHMenu, R.id.tv_selectItemValue_VHMenu})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.layout_title_VHMenu:                  /** 整个标题布局 */
                break;

            case R.id.tv_selectItemValue_VHMenu:            /**  所有选中的项文本 */
                break;
        }

    }
}
