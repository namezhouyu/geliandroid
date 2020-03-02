package com.geli.m.mvp.home.mine_fragment.collection_activity.collection_list_fragment;

import com.geli.m.bean.BaseBean;
import com.geli.m.bean.CollectionBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/4/2.
 */

public class CollectionPresentImpl extends BasePresenter<CollectionView, CollectionModelImpl> {
    public CollectionPresentImpl(CollectionView mvpView) {
        super(mvpView);
    }

    public void getList(String user_id, String col_type) {
        mModel.getList(user_id, col_type, new BaseObserver<CollectionBean>(this, mvpView) {
            @Override
            protected void onSuccess(CollectionBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.showList(data.getData());
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

    /** 这次执行的操作是否是"取消收藏" -- 用于 mvpView.onSuccess 中的判断 */
    public boolean isColl;

    public void collection(Map<String, String> data) {
        isColl = true;
        mModel.collection(data, new BaseObserver<ResponseBody>(this, mvpView, false) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    BaseBean baseBean = parseData(data.string());
                    if (baseBean.code == Constant.REQUEST_OK) {
                        mvpView.onSuccess(baseBean.message);
                    } else {
                        mvpView.onError(baseBean.message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mvpView.onError(parseError(e));
                }
            }
        });
    }

    @Override
    protected CollectionModelImpl createModel() {
        return new CollectionModelImpl();
    }
}
