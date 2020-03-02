package com.geli.m.mvp.home.mine_fragment.address_activity;

import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;

/**
 * Created by Steam_l on 2018/1/9.
 */

public class AddressListModelImpl extends BaseModel {
    public void getAddressList(String user_id, BaseObserver observer) {
        universal(mApiService.getAddressList(user_id), observer);
    }

    public void deleteAddress(String user_id, String add_id, BaseObserver observer) {
        universal(mApiService.deleteAddress(user_id, add_id), observer);
    }
}
