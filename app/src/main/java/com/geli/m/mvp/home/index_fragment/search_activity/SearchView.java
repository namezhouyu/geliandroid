package com.geli.m.mvp.home.index_fragment.search_activity;

import com.geli.m.bean.KeyWordsBean;
import com.geli.m.bean.OverseasGoodsOuterBean;
import com.geli.m.bean.RetailCenterBean;
import com.geli.m.mvp.base.BaseView;
import java.util.List;
import java.util.Map;

/**
 * Created by Steam_l on 2018/3/16.
 */

public interface SearchView extends BaseView {
    void showGoods(OverseasGoodsOuterBean dataEntity);

    void showShops(RetailCenterBean dataEntity);

    void showOverseas(Map<String, OverseasGoodsOuterBean> map);

    void showHotKeywords(List<KeyWordsBean.DataEntity> entityList);
}
