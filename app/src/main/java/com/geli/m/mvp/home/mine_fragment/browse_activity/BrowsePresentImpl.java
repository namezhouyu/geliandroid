package com.geli.m.mvp.home.mine_fragment.browse_activity;

import com.geli.m.bean.BrowseBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.utils.DateFormatUtil;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Steam_l on 2018/3/26.
 */

public class BrowsePresentImpl extends BasePresenter<BrowseView, BrowseModelImpl> {

    public BrowsePresentImpl(BrowseView mvpView) {
        super(mvpView);
    }

    private List mDataList = new ArrayList();

    public void getBrowse(String user_id, final String page) {
        mModel.getBrowse(user_id, page,
                new Function<BrowseBean, BrowseBean>() {
                    @Override
                    public BrowseBean apply(@NonNull BrowseBean browseBean) throws Exception {
                        if (browseBean.getCode() == Constant.REQUEST_OK) {
                            //处理下一页衔接上一页
                            List<BrowseBean.DataEntity> list = browseBean.getData();
                            if (page.equals("1")) {
                                mDataList.clear();
                            }
                            long preTime = 0;
                            if (mDataList.size() > 0) {
                                preTime = ((BrowseBean.DataEntity) mDataList.get(mDataList.size() - 1)).getAdd_time() * 1000;
                                mDataList.clear();
                            }
                            for (BrowseBean.DataEntity childe : list) {
                                boolean sameDate = DateFormatUtil.isSameDate(new Date(preTime), new Date(childe.getAdd_time() * 1000));
                                if (!sameDate) {
                                    BrowseBean.BrowseTime browseTime = new BrowseBean.BrowseTime();
                                    browseTime.setTime(DateFormatUtil.transForBrowseDate(childe.getAdd_time()));
                                    mDataList.add(browseTime);
                                    preTime = childe.getAdd_time() * 1000;
                                }
                                mDataList.add(childe);
                            }
                            browseBean.setDataList(mDataList);
                        }
                        return browseBean;
                    }
                }, new BaseObserver<BrowseBean>(this, mvpView) {
                    @Override
                    protected void onSuccess(BrowseBean data) {
                        if (data.getCode() == Constant.REQUEST_OK) {
                            mvpView.showBrowse(data.getDataList());
                        } else {
                            mvpView.onError(data.getMessage());
                        }
                    }
                });
    }

    @Override
    protected BrowseModelImpl createModel() {
        return new BrowseModelImpl();
    }
}
