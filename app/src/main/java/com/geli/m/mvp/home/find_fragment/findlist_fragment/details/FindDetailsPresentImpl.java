package com.geli.m.mvp.home.find_fragment.findlist_fragment.details;

import com.geli.m.R;
import com.geli.m.bean.BaseBean;
import com.geli.m.bean.FindDetailsBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.utils.Utils;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/3/21.
 */

public class FindDetailsPresentImpl extends BasePresenter<FindDetailsView, FindDetailsModelImpl> {
    public FindDetailsPresentImpl(FindDetailsView mvpView) {
        super(mvpView);
    }

    public void getDetails(String user_id, String find_id) {
        mModel.getDetails(user_id, find_id, new BaseObserver<FindDetailsBean>(this, mvpView) {
            @Override
            protected void onSuccess(FindDetailsBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.showData(data.getData());
                } else {
                    mvpView.onError(data.getMessage());
                }
            }
        });
    }

    public boolean isColl;

    public void collection(Map<String, String> data) {
        isColl = true;
        if (BaseActivity.gotoLogin(mvpView)) {
            mvpView.onError(Utils.getString(R.string.please_login));
            return;
        }
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

    public boolean isLike;

    public void like(String user_id, String find_id) {
        isLike = true;
        if (BaseActivity.gotoLogin(mvpView)) {
            mvpView.onError(Utils.getString(R.string.please_login));
            return;
        }
        mModel.like(user_id, find_id, new BaseObserver<ResponseBody>(this, mvpView, false) {
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
    protected FindDetailsModelImpl createModel() {
        return new FindDetailsModelImpl();
    }
}
