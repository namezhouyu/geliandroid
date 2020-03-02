package com.geli.m.mvp.home.find_fragment.findlist_fragment;

import com.geli.m.bean.FindListBean;
import com.geli.m.mvp.base.BaseView;
import java.util.List;

/**
 * Created by Steam_l on 2018/3/21.
 */

public interface FindListView extends BaseView {
    void showList(List<FindListBean.DataEntity> dataEntity);
}
