package com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.restaurant_activity.r_search_activity.fragment;

import android.content.Intent;
import android.view.ViewGroup;
import butterknife.BindView;
import com.geli.m.R;
import com.geli.m.bean.RestaurantShopBean;
import com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.restaurant_activity.ShopRestaurantVH;
import com.geli.m.mvp.home.other.shopdetails_activity.ShopDetailsActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.List;

import static com.geli.m.config.Constant.INTENT_SHOP_ID;

/**
 * author:  shen
 * date:    2019/3/25
 * 新批发 -- 搜索的商店
 */
public class RSearchShopFragment extends RSearchBaseFragment<List<RestaurantShopBean>> {

    @BindView(R.id.erv_shopsearch_list)
    EasyRecyclerView erv_list;

    @Override
    public int getResId() {
        return R.layout.fragment_shopsearch;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initEvent() {
        setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                RestaurantShopBean.DataBean bean = (RestaurantShopBean.DataBean) mAdapter.getItem(position);
                Intent intent = new Intent(mContext, ShopDetailsActivity.class);
                intent.putExtra(INTENT_SHOP_ID, bean.getShop_id() + "");
                startActivity(intent);
            }
        });
    }

    @Override
    protected EasyRecyclerView getEasyRecyclerView() {
        return erv_list;
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent) {
        return new ShopRestaurantVH(parent, mContext);
    }


    @Override
    public void setData(List<RestaurantShopBean> data) {
        if (mPage == 1) {
            clearData();
        }
        if(data != null){
            addAll(data);
            if (data.size() < 20) {
                stopMore();
            }
        }else {
            clearData();
        }
        if(erv_list.getSwipeToRefresh().isRefreshing()){
            erv_list.getSwipeToRefresh().setRefreshing(false);
        }
    }
}
