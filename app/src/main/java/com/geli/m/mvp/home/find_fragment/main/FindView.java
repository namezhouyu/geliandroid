package com.geli.m.mvp.home.find_fragment.main;

import com.geli.m.bean.FindCatBean;
import com.geli.m.mvp.base.BaseView;
import com.geli.m.mvp.home.find_fragment.findlist_fragment.FindListFragment;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by l on 2018/4/4.
 */

public interface FindView extends BaseView {
    void showCatList(List<FindCatBean.DataEntity> entityList);
    LinkedHashMap<String,String> getTitles();
    ArrayList<FindListFragment> getFragments();
}
