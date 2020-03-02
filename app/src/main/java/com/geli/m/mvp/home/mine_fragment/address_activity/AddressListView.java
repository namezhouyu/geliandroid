package com.geli.m.mvp.home.mine_fragment.address_activity;

import com.geli.m.bean.AddressListBean;
import com.geli.m.mvp.base.BaseView;

/**
 * Created by Steam_l on 2018/1/9.
 */

public interface AddressListView extends BaseView {
    void showList(AddressListBean bean);

    void deleteSuccess(String add_id);
}
