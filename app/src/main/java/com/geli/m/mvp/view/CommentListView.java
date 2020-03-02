package com.geli.m.mvp.view;

import com.geli.m.bean.GoodsDetailsBean;
import com.geli.m.mvp.base.BaseView;

import java.util.List;

/**
 * Created by Steam_l on 2018/2/1.
 */

public interface CommentListView extends BaseView {
    void showCommentList(List<GoodsDetailsBean.DataBean.GoodsCommentBean> commentBeanList);
}
