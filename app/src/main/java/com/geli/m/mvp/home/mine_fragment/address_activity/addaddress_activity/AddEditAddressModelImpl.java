package com.geli.m.mvp.home.mine_fragment.address_activity.addaddress_activity;

import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/11.
 */

public class AddEditAddressModelImpl extends BaseModel {
    public void getAddressInfo(String user_id, String address_id, BaseObserver observer) {
        universal(mApiService.getAddressInfo(user_id, address_id), observer);
    }

    public void saveAddress(Map data, BaseObserver observer) {
        universal(mApiService.saveAddress(data), observer);
    }

    public void getStreet(String area_id, BaseObserver observer) {
        universal(mApiService.getStreet(area_id), observer);
    }

}
