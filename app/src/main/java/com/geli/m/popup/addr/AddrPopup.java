package com.geli.m.popup.addr;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.geli.m.R;
import com.geli.m.bean.RestaurantAddrArrangeBean;
import com.geli.m.bean.RestaurantAddrBean;
import com.geli.m.popup.BasePopupWindow;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

import java.util.List;

import static android.widget.ListPopupWindow.MATCH_PARENT;

/**
 * author:  shen
 * date:    2019/7/24
 */
public abstract class AddrPopup extends BasePopupWindow {

    int mLastLeftPosition = -1;

    EasyRecyclerView mErvLeft;
    EasyRecyclerView mErvRight;

    LinearLayout mLinearLayout;
    RecyclerArrayAdapter mAdapterLeft;
    RecyclerArrayAdapter mAdapterRight;

    View mView;
    RestaurantAddrArrangeBean mData;

    RestaurantAddrBean.DataBean mSelectProvince;
    RestaurantAddrBean.DataBean mSelectCity;

    protected abstract void selectAddr(PopupWindow popupWindow, RestaurantAddrBean.DataBean selectProvince,
                                       RestaurantAddrBean.DataBean selectCity);

    /**
     * @param a
     * @param bgColor   0~1
     */
    public AddrPopup(Activity a, float bgColor) {
        super(a, R.layout.addr_popup, MATCH_PARENT, MATCH_PARENT, bgColor);
    }

    @Override
    protected void initView() {
        View view = getContentView();
        mLinearLayout = view.findViewById(R.id.lLayout_AddrPopup);
        mErvLeft = view.findViewById(R.id.erv_left_AddrPopup);
        mErvRight = view.findViewById(R.id.erv_right_AddrPopup);
        mView = view.findViewById(R.id.v_transparent_AddrPopup);


    }

    @Override
    protected void initEvent() {
        initErvLeft();
        initErvRight();

        setViewHide();
        getPopupWindow().setFocusable(false);
    }

    private void setViewHide() {
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPopupWindow().dismiss();
            }
        });
    }

    private void initErvLeft(){
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        mErvLeft.setLayoutManager(manager);
        mErvLeft.setAdapter(initAdapterLeft());
    }


    private void initErvRight(){
        GridLayoutManager manager = new GridLayoutManager(mActivity, 2);
        SpaceDecoration decoration = new SpaceDecoration(Utils.dip2px(mActivity, 25));
        decoration.setPaddingEdgeSide(true);
        decoration.setPaddingStart(true);
        decoration.setPaddingHeaderFooter(true);
        mErvRight.setLayoutManager(manager);
        mErvRight.addItemDecoration(decoration);
        mErvRight.setAdapter(initAdapterRight());

    }

    private RecyclerView.Adapter initAdapterLeft() {
        mAdapterLeft = new RecyclerArrayAdapter(mActivity) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new AddrLeftVH(parent, mActivity);
            }
        };

        mAdapterLeft.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                List list = ((RestaurantAddrArrangeBean.province)mAdapterLeft.getItem(position)).getCityList();
                setErvRightData(list);


                if(mLastLeftPosition != -1){
                    // 设置为未点击状态
                    ((RestaurantAddrArrangeBean.province)mAdapterLeft.getItem(mLastLeftPosition)).getPro().setSelect(false);
                    mAdapterLeft.notifyItemChanged(mLastLeftPosition);
                }
                ((RestaurantAddrArrangeBean.province)mAdapterLeft.getItem(position)).getPro().setSelect(true);
                mAdapterLeft.notifyItemChanged(position);
                mLastLeftPosition = position;

                mSelectProvince = ((RestaurantAddrArrangeBean.province)mAdapterLeft.getItem(position)).getPro();
            }
        });
        return mAdapterLeft;
    }


    private RecyclerView.Adapter initAdapterRight() {
        mAdapterRight = new RecyclerArrayAdapter(mActivity) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new AddrRightVH(parent, mActivity);
            }
        };

        mAdapterRight.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mSelectCity = (RestaurantAddrBean.DataBean)mAdapterRight.getItem(position);
                selectAddr(getPopupWindow(), mSelectProvince, mSelectCity);
            }
        });
        return mAdapterRight;
    }



    public void setData(RestaurantAddrArrangeBean bean){
        if(bean == null){
            return;
        }
        setErvLeftData(bean.getProvinceList());
        setErvRightData(bean.getProvinceList().get(0).getCityList());
    }

    boolean isFirst = true;
    private void setErvLeftData(List<RestaurantAddrArrangeBean.province> list){
        if(mAdapterLeft != null){
            mAdapterLeft.clear();
            mAdapterLeft.addAll(list);

            if(list != null && list.size() > 0){
                mSelectProvince = list.get(0).getPro();
            }

            if(isFirst) {
                // 当左边的数据(好像是"市")少于4个，控件高度减半
                if (mAdapterLeft.getCount() <= 4) {
                    ViewGroup.LayoutParams lp = mLinearLayout.getLayoutParams();
                    lp.height = lp.height / 2;
                    mLinearLayout.setLayoutParams(lp);
                }
                isFirst = false;
            }
        }

    }

    private void setErvRightData(List<RestaurantAddrBean.DataBean> list){
        if(mAdapterRight != null){
            mAdapterRight.clear();
            mAdapterRight.addAll(list);
        }
    }

}
