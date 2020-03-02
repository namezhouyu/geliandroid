package com.geli.m.mvp.home.mine_fragment.helpcenter_activity;

import com.geli.m.bean.HelpCenterBean;
import com.geli.m.bean.HelpCenterBottomBean;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.BaseObserver;

/**
 * Created by Steam_l on 2018/3/8.
 */

public class HelpCenterModelImpl extends BaseModel {
    public void getAllData(BaseObserver<HelpCenterBean> observer) {
        universal(mApiService.getHelpCenterData(), observer);
    }

    public void getDataForCat(String cat_id, BaseObserver<HelpCenterBottomBean> observer) {
        universal(mApiService.getHelpCenterForCat(cat_id), observer);
    }

}
