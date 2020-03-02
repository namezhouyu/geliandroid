package com.geli.m.mvp.home.mine_fragment.mywallet_activity.expensesrecord_activity;

import com.geli.m.mvp.base.BaseView;
import java.util.List;

/**
 * Created by Steam_l on 2018/3/27.
 */

public interface ExpensesRecordView extends BaseView {
    void showData(List entityList);
}
