package com.geli.m.mvp.home.mine_fragment.helpcenter_activity;

import com.geli.m.bean.HelpCenterBean;
import com.geli.m.mvp.base.BaseView;
import java.util.List;

/**
 * Created by Steam_l on 2018/3/8.
 */

public interface HelpCenterView extends BaseView {
    void showAllData(HelpCenterBean.DataEntity dataEntity);

    void showCatData(List<HelpCenterBean.DataEntity.HelpListEntity> catListEntity);
}
