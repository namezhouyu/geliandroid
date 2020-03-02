package com.geli.m.mvp.home.find_fragment.main;

import com.geli.m.bean.FindCatBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import io.reactivex.functions.Function;

/**
 * Created by l on 2018/4/4.
 */

public class FindCatModelImpl extends BaseModel {
    public void getCatList(Function<FindCatBean, FindCatBean> function, BaseObserver<FindCatBean> observer) {
        universal(mApiService.findCatList().map(function), observer,false);
    }
}
