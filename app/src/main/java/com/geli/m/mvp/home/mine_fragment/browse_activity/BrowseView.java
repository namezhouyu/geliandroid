package com.geli.m.mvp.home.mine_fragment.browse_activity;

import com.geli.m.mvp.base.BaseView;
import java.util.List;

/**
 * Created by Steam_l on 2018/3/26.
 */

public interface BrowseView extends BaseView {
    void showBrowse(List<Object> listEntity);
}
