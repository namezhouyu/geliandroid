package com.geli.m.mvp.home.mine_fragment.address_activity.addaddress_activity;

import com.geli.m.bean.AlterAddressBean;
import com.geli.m.bean.NewAddressBean;
import com.geli.m.bean.StreetList;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/11.
 */

public class AddEditAddressPresenterImpl extends BasePresenter<AddEditAddressView, AddEditAddressModelImpl> {
    public AddEditAddressPresenterImpl(AddEditAddressView mvpView) {
        super(mvpView);
    }

    public void getAddressInfo(String user_id, String address_id) {
        mModel.getAddressInfo(user_id, address_id, new BaseObserver<AlterAddressBean>(this, mvpView) {
            @Override
            protected void onSuccess(AlterAddressBean data) {
                if (data.getCode() == 100) {
                    mvpView.showAddressInfo(data);
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }


    public void saveAddress(Map data) {
        // 用户里没有地址的时候,添加的地址自动设置成默认
        mModel.saveAddress(data, new BaseObserver<NewAddressBean>(this, mvpView) {

            @Override
            protected void onSuccess(NewAddressBean data) {
                if (data.getCode() == 100) {
                    mvpView.onSuccess(data.getMessage());
                    mvpView.saveSuccess(data);
                } else {
                    mvpView.onError();
                }
            }

        });
    }

    // 获取街道
    public void getStree(String area_id) {
        mModel.getStreet(area_id, new BaseObserver<StreetList>(this, mvpView) {

            @Override
            protected void onSuccess(StreetList data) {
                if (data.getCode() == 100) {
                    mvpView.getStreetSuccess(data);
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

    @Override
    protected AddEditAddressModelImpl createModel() {
        return new AddEditAddressModelImpl();
    }
}
