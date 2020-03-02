package com.geli.m.mvp.home.index_fragment.main;

import com.geli.m.bean.base.IndexBaseBean;
import com.geli.m.mvp.base.BaseView;
import java.util.List;

/**
 * Created by Steam_l on 2017/12/21.
 */

public interface IndexView extends BaseView {
    void showIndexInfo(List<IndexBaseBean> data);
}
