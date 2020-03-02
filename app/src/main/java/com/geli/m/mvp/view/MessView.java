package com.geli.m.mvp.view;

import com.geli.m.bean.MessBean;
import com.geli.m.mvp.base.BaseView;

import java.util.List;

/**
 * Created by Steam_l on 2018/3/28.
 */

public interface MessView extends BaseView {
    void showList(List<MessBean.DataEntity> dataEntities);
}
