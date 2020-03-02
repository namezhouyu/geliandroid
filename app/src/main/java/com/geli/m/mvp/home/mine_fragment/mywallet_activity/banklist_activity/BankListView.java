package com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity;

import com.geli.m.bean.BankListBean;
import com.geli.m.mvp.base.BaseView;
import java.util.List;

/**
 * Created by Steam_l on 2018/3/24.
 */

public interface BankListView extends BaseView {
    void showList(List<BankListBean.DataEntity> dataEntities);
}
