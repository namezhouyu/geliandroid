package com.geli.m.drawer.menudrawer;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import com.geli.m.R;
import com.geli.m.bean.RestaurantGoodsShopScreen;
import com.geli.m.coustomview.MyEasyRecyclerView;
import com.geli.m.utils.StringUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:  shen
 * date:    2019/3/15
 *
 * 菜单控件
 */
public class MenuLayout extends LinearLayout implements View.OnClickListener {

    MyEasyRecyclerView mErv;
    /** 重置 */
    Button mBtnReset;
    /** 确认 */
    Button mBtnConfirm;

    RecyclerArrayAdapter mAdapter;
    MenuLayoutListener mListener;


    String mTypeSort = "";

    public MenuLayout(Context context) {
        super(context);
        inflateView();
    }

    private void inflateView() {
        View.inflate(getContext(), R.layout.layout_menu, this);
        initView();
        initDataEvent();
        initErv();
    }

    private void initView(){

        mErv = (MyEasyRecyclerView) findViewById(R.id.erv_LayoutMenu);
        mBtnReset = (Button) findViewById(R.id.btn_reset_LayoutMenu);
        mBtnConfirm = (Button) findViewById(R.id.btn_confirm_LayoutMenu);
    }

    private void initDataEvent(){
        mBtnReset.setOnClickListener(this);
        mBtnConfirm.setOnClickListener(this);
    }


    private void initErv(){
        mErv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mErv.setAdapter(initAdapter());

        mErv.setVerticalScrollBarEnabled(false);
        mErv.setHorizontalScrollBarEnabled(false);

        mErv.setOrientation(MyEasyRecyclerView.VERTICAL);
    }

    private RecyclerArrayAdapter initAdapter(){
        mAdapter = new RecyclerArrayAdapter(getContext()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                if(viewType == 1)
                    return new MenuSortVH(parent, getContext(), new FirstSortListener() {
                        @Override
                        public void firstSortListener(RestaurantGoodsShopScreen.DataBeanX.DataBean bean) {
                            changeFirstSortData(bean);
                        }
                    });
                else
                    return new MenuVH(parent, getContext());
            }

            @Override
            public int getViewType(int position) {
                return position == 0 ? 1 : 2;
            }
        };

        return mAdapter;
    }

    /**
     * 第一个筛选 -- 根据点击改变 数据
     * @param bean
     */
    private void changeFirstSortData(RestaurantGoodsShopScreen.DataBeanX.DataBean bean) {
        if(mAdapter != null && mAdapter.getAllData() != null && mAdapter.getAllData().size() > 0){
            int i = 0;
            for (RestaurantGoodsShopScreen.DataBeanX.DataBean temp :
                    ((RestaurantGoodsShopScreen.DataBeanX) mAdapter.getAllData().get(0)).getData()) {
                if (bean.getValue() == temp.getValue()) {
                    ((RestaurantGoodsShopScreen.DataBeanX) mAdapter.getAllData().get(0))
                            .getData().get(i).setSelected(true);
                    mTypeSort = bean.getName();
                } else {
                    ((RestaurantGoodsShopScreen.DataBeanX) mAdapter.getAllData().get(0))
                            .getData().get(i).setSelected(false);
                }
                i++;
            }
        }
    }

    public MenuLayoutListener getListener() {
        return mListener;
    }

    public void setListener(MenuLayoutListener listener) {
        mListener = listener;
    }


    public void setAdapterData(List list){
        if(mAdapter != null){
            mAdapter.clear();
            mAdapter.addAll(list);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_reset_LayoutMenu:             /* 重置 */
                reset();
                break;

            case R.id.btn_confirm_LayoutMenu:           /* 确认 */
                confirm();
                break;
        }
    }

    private void reset() {
        if(mAdapter.getAllData() != null) {
            int i = 0;
            boolean isChange;
            for(Object o : mAdapter.getAllData()){
                isChange = false;
                RestaurantGoodsShopScreen.DataBeanX dataBeanX = (RestaurantGoodsShopScreen.DataBeanX) o;
                if(dataBeanX.getData() != null){
                    for(RestaurantGoodsShopScreen.DataBeanX.DataBean dataBean : dataBeanX.getData()){
                        if(i == 0){
                            dataBean.setSelected(false);
                            isChange = true;
                            continue;
                        }

                        if(dataBean.isSelected()) {
                            dataBean.setSelected(false);
                            isChange = true;
                        }
                    }
                }
                if(isChange){
                    mAdapter.notifyItemChanged(i);
                }

                i++;
            }
        }
    }

    private void confirm() {
        if(mListener != null){
            mListener.confirm(mTypeSort, getMapData());
        }
    }


    private Map getMapData(){
        Map map = new HashMap();
        if(mAdapter.getAllData() != null && mAdapter.getAllData().size() > 0) {
            for(Object o : mAdapter.getAllData()){
                RestaurantGoodsShopScreen.DataBeanX dataBeanX = (RestaurantGoodsShopScreen.DataBeanX) o;
                String key = dataBeanX.getScreen_key();
                String value = "";
                if(dataBeanX != null && dataBeanX.getData() != null){
                    for(Object o1 : dataBeanX.getData()){
                        RestaurantGoodsShopScreen.DataBeanX.DataBean dataBean =
                                (RestaurantGoodsShopScreen.DataBeanX.DataBean) o1;
                        if(dataBean.isSelected()){
                            value += dataBean.getValue() + ",";
                        }
                    }
                }
                if(StringUtils.isNotEmpty(value)){
                    value = value.substring(0, value.length() -1);
                    map.put(key, value);
                }
            }
        }
        return map;
    }

}
