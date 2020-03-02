package com.geli.m.mvp.home.mine_fragment.address_activity.addaddress_activity;

import com.geli.m.bean.AlterAddressBean;
import com.geli.m.bean.NewAddressBean;
import com.geli.m.bean.StreetList;
import com.geli.m.mvp.base.BaseView;

/**
 * Created by Administrator on 2017/11/11.
 */

public interface AddEditAddressView extends BaseView {
    void getStreetSuccess(StreetList data);

    void onError();

    void saveSuccess(NewAddressBean data);

    void showAddressInfo(AlterAddressBean data);
}
