package com.geli.m.mvp.home.find_fragment.main;

import com.geli.m.bean.FindCatBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.mvp.home.find_fragment.findlist_fragment.FindListFragment;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by l on 2018/4/4.
 */

public class FindCatPresentImpl extends BasePresenter<FindView, FindCatModelImpl> {

    List<SoftReference<FindListFragment>> mSoftReferences = new ArrayList<>();

    public FindCatPresentImpl(FindView mvpView) {
        super(mvpView);
    }

    @Override
    protected FindCatModelImpl createModel() {
        return new FindCatModelImpl();
    }

    public void getCatList() {
        mModel.getCatList(new Function<FindCatBean, FindCatBean>() {
            @Override
            public FindCatBean apply(@NonNull FindCatBean findCatBean) throws Exception {
//                loop:
//                if (findCatBean.getCode() == Constant.REQUEST_OK) {
//                    LinkedHashMap<String, String> titleList = mvpView.getTitles();
//                    if (titleList.size() == 0) {
//                        break loop;
//                    }
//                    ArrayList<FindListFragment> fragments = mvpView.getFragments();
//                    LinkedHashMap<String, String> tempMap = new LinkedHashMap<>();
//                    tempMap.putAll(titleList);
//                    titleList.clear();
//
//                    // 重置title
//                    for (FindCatBean.DataEntity entity : findCatBean.getData()) {
//                        titleList.put(entity.getCat_name(), entity.getCat_id() + "");
//                    }
//                    if (tempMap.equals(titleList)) {
//                        break loop;
//                    }
//
//                    // 处理数据
//                    if (titleList.size() < fragments.size()) {         /* fragment太多去掉后面的? */
//                        for (int i = titleList.size() - 1; i < fragments.size() - 1; i++) {
//                            mSoftReferences.add(new SoftReference(fragments.get(i)));
//                            fragments.remove(i);
//                        }
//                    } else {
//                        for (int i = fragments.size(); i < titleList.size(); i++) {
//                            FindListFragment fragment = null;
//                            if (mSoftReferences.size() > 0) {
//                                Iterator<SoftReference<FindListFragment>> iterator = mSoftReferences.iterator();
//                                while (iterator.hasNext()) {
//                                    SoftReference<FindListFragment> reference = iterator.next();
//                                    if (null != reference) {
//                                        fragment = reference.get();
//                                        iterator.remove();
//                                        break;
//                                    }
//                                }
//                                if (null == fragment) {
//                                    fragment = FindListFragment.newInstance("1");
//                                }
//                            } else {
//                                fragment = FindListFragment.newInstance("1");
//                            }
//                            fragments.add(fragment);
//                        }
//                    }
//                }
                return findCatBean;
            }
        }, new BaseObserver<FindCatBean>(this, mvpView) {
            @Override
            protected void onSuccess(FindCatBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.showCatList(data.getData());
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

}
