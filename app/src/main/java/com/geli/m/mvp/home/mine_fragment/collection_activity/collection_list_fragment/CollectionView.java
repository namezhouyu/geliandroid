package com.geli.m.mvp.home.mine_fragment.collection_activity.collection_list_fragment;

import com.geli.m.bean.CollectionBean;
import com.geli.m.mvp.base.BaseView;
import java.util.List;

/**
 * Created by Steam_l on 2018/4/2.
 */

public interface CollectionView extends BaseView {
    void showList(List<CollectionBean.DataEntity> entityList);
}
