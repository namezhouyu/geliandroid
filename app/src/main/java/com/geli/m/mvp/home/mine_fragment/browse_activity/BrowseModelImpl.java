package com.geli.m.mvp.home.mine_fragment.browse_activity;

import com.geli.m.bean.BrowseBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;
import io.reactivex.functions.Function;

/**
 * Created by Steam_l on 2018/3/26.
 */

public class BrowseModelImpl extends BaseModel {
    public void getBrowse(String user_id, final String page,Function function, BaseObserver<BrowseBean> observer) {
        universal(mApiService.getBrowse(user_id, page)
                .map(function), observer);
    }

}
