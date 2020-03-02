package com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main;

import com.geli.m.bean.RestaurantAddrArrangeBean;
import com.geli.m.bean.RestaurantAddrBean;
import com.geli.m.bean.RestaurantBean;
import com.geli.m.bean.RestaurantSortBean;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * shen
 */
public class RestaurantListPresentImpl extends BasePresenter<RestaurantListView, RestaurantListModelImpl> {


    @Override
    protected RestaurantListModelImpl createModel() {
        return new RestaurantListModelImpl();
    }

    public RestaurantListPresentImpl(RestaurantListView mvpView) {
        super(mvpView);
    }


    public void localFoodList(Map<String, String> map) {
        mModel.localFoodList(map, new BaseObserver<RestaurantBean>(this, mvpView, true) {
            @Override
            protected void onSuccess(RestaurantBean data) {
                if(isGetDataSuccess(data.getCode())){
                    mvpView.setRestaurantShop(data.getData());
                }else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }


    // 有新批发省市区
    public void marketZone(){
        mModel.marketZone(new BaseObserver<RestaurantAddrBean>(this, mvpView, false) {
            @Override
            protected void onSuccess(RestaurantAddrBean data) {
                if(isGetDataSuccess(data.getCode())){
                    mvpView.setAddrPopup(arrangeAddr(data.getData()));
                }else {
                    //mvpView.onError(data.getMessage());
                }
            }
        });
    }

    // 种类筛选
    public void marketType(){
        mModel.marketType(new BaseObserver<RestaurantSortBean>(this, mvpView, false) {
            @Override
            protected void onSuccess(RestaurantSortBean data) {
                if(isGetDataSuccess(data.getCode())){
                    mvpView.setSortBean(data.getData());
                }else {
                    //mvpView.onError(data.getMessage());
                }
            }
        });
    }


    /**
     * 整理"省市区"数据
     * @param data
     */
    private RestaurantAddrArrangeBean arrangeAddr(List<RestaurantAddrBean.DataBean> data){
        RestaurantAddrArrangeBean list = new RestaurantAddrArrangeBean();
        List<RestaurantAddrBean.DataBean> cityList = new ArrayList<>();
        if(data != null) {
            for (RestaurantAddrBean.DataBean d : data) {
                if (d.getArea_type() == 1) {
                    list.getProvinceList().add(
                            new RestaurantAddrArrangeBean.province(d,
                                    new ArrayList<RestaurantAddrBean.DataBean>()));
                } else {
                    cityList.add(d);
                }
            }

            for (RestaurantAddrArrangeBean.province province : list.getProvinceList()) {
                ListIterator<RestaurantAddrBean.DataBean> city = cityList.listIterator();
                while (city.hasNext()) {
                    RestaurantAddrBean.DataBean cityBean = city.next();
                    if (province.getPro().getCode().equals(cityBean.getParent_id())) {
                        province.getCityList().add(cityBean);
                        cityList.remove(city);
                    }
                }
            }
            return list;
        }else {
            return null;
        }

        // LogUtils.i("地址整理--/n" + list.toString());

    }
}
