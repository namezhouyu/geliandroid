package com.geli.m.mvp.present;

import com.geli.m.R;
import com.geli.m.bean.BaseBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.mvp.base.BaseView;
import com.geli.m.mvp.model.CommentLikeModelImpl;
import com.geli.m.utils.Utils;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/2/1.
 */

public class CommentLikePresentImpl<V extends BaseView, M extends CommentLikeModelImpl> extends BasePresenter<BaseView, CommentLikeModelImpl> {
    public boolean isToLike = false;

    public CommentLikePresentImpl(BaseView mvpView) {
        super(mvpView);
    }

    public void toLike(String user_id, String com_id) {
        isToLike = true;
        if (BaseActivity.gotoLogin(mvpView)) {
            mvpView.onError(Utils.getString(R.string.please_login));
            return;
        }
        mModel.toLike(user_id, com_id, new BaseObserver<ResponseBody>(this, mvpView, false) {
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
    protected CommentLikeModelImpl createModel() {
        return new CommentLikeModelImpl();
    }
}
