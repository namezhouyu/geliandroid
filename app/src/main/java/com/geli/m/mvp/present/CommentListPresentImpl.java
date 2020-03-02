package com.geli.m.mvp.present;

import com.geli.m.bean.BaseBean;
import com.geli.m.bean.CommentListBean;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.model.CommentListModelImpl;
import com.geli.m.mvp.view.CommentListView;
import com.geli.m.config.Constant;

import java.util.Map;

import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/2/1.
 */

public class CommentListPresentImpl extends CommentLikePresentImpl<CommentListView, CommentListModelImpl> {
    public CommentListPresentImpl(CommentListView mvpView) {
        super(mvpView);
    }

    public void getCommentList(String url, Map data) {
        ((CommentListModelImpl) mModel).getCommentList(url, data, new BaseObserver<ResponseBody>(this, mvpView) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    String string = data.string();
                    BaseBean baseBean = parseData(string);

                    if (baseBean.code == Constant.REQUEST_OK) {
                        CommentListBean commentListBean = mGson.fromJson(string, CommentListBean.class);
                        ((CommentListView) mvpView).showCommentList(commentListBean.getData());

                    } else if (baseBean.code == Constant.REQUEST_NODATA) {
                        ((CommentListView) mvpView).showCommentList(null);

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
    protected CommentListModelImpl createModel() {
        return new CommentListModelImpl();
    }
}
