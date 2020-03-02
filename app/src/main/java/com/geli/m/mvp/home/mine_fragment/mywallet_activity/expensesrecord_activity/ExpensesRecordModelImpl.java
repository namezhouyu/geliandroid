package com.geli.m.mvp.home.mine_fragment.mywallet_activity.expensesrecord_activity;

import com.geli.m.bean.ExpensesBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import io.reactivex.functions.Function;

/**
 * Created by Steam_l on 2018/3/27.
 */

public class ExpensesRecordModelImpl extends BaseModel {
    public void getExpenses(String user_id, String page, Function<ExpensesBean,ExpensesBean> function, BaseObserver<ExpensesBean> observer) {
        universal(mApiService.getExpenses(user_id, page).map(function), observer);
    }

}
