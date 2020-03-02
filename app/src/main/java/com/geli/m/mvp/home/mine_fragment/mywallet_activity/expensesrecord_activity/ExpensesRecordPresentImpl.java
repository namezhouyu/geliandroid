package com.geli.m.mvp.home.mine_fragment.mywallet_activity.expensesrecord_activity;

import com.geli.m.bean.ExpensesBean;
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
 * Created by Steam_l on 2018/3/27.
 */

public class ExpensesRecordPresentImpl extends BasePresenter<ExpensesRecordView, ExpensesRecordModelImpl> {
    public ExpensesRecordPresentImpl(ExpensesRecordView mvpView) {
        super(mvpView);
    }

    List mEntityList = new ArrayList<>();

    public void getExpenses(String user_id, final String page) {
        mModel.getExpenses(user_id, page,
                new Function<ExpensesBean, ExpensesBean>() {
                    @Override
                    public ExpensesBean apply(@NonNull ExpensesBean expensesBean) throws Exception {
                        if (page.equals("1")) {
                            mEntityList.clear();
                        }
                        long preTime = 0;
                        if (mEntityList.size() > 0) {
                            try {
                                preTime = ((ExpensesBean.DataEntity.ConsumptionEntity) mEntityList.get(mEntityList.size() - 1)).getAdd_time();
                            } catch (Exception e) {
                            } finally {
                                mEntityList.clear();
                            }
                        }
                        if (expensesBean.getCode() == Constant.REQUEST_OK) {
                            List<ExpensesBean.DataEntity.ConsumptionEntity> consumption = expensesBean.getData().getConsumption();
                            for (ExpensesBean.DataEntity.ConsumptionEntity entity : consumption) {
                                boolean year = DateFormatUtil.isSameDateForYear(new Date(preTime * 1000), new Date(entity.getAdd_time() * 1000));
                                String title = "";
                                if (year) {
                                    boolean month = DateFormatUtil.isSameDateForMonth(new Date(preTime * 1000), new Date(entity.getAdd_time() * 1000));
                                    if (!month) {
                                        title = DateFormatUtil.transForDate((int) entity.getAdd_time(), "MM月");
                                        mEntityList.add(title);
                                        preTime = entity.getAdd_time();
                                    }
                                } else {
                                    title = DateFormatUtil.transForDate((int) entity.getAdd_time(), "yyyy年MM月");
                                    mEntityList.add(title);
                                    preTime = entity.getAdd_time();
                                }
                                mEntityList.add(entity);
                            }
                            expensesBean.getData().setDataList(mEntityList);
                        }
                        return expensesBean;
                    }
                }, new BaseObserver<ExpensesBean>(this, mvpView) {
                    @Override
                    protected void onSuccess(ExpensesBean data) {
                        if (data.getCode() == Constant.REQUEST_OK) {
                            mvpView.showData(data.getData().getDataList());
                        } else {
                            mvpView.onError(data.getMessage());
                        }

                    }
                });
    }

    @Override
    protected ExpensesRecordModelImpl createModel() {
        return new ExpensesRecordModelImpl();
    }
}
