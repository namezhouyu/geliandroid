package com.geli.m.mvp.home.find_fragment.findlist_fragment.details;

import com.geli.m.bean.FindDetailsBean;
import com.geli.m.mvp.base.BaseView;

/**
 * Created by Steam_l on 2018/3/21.
 */

public interface FindDetailsView extends BaseView {
    void showData(FindDetailsBean.DataEntity dataEntity);
}
