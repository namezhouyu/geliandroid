package com.geli.m.mvp.home.other.transferaccounts_activity;

import com.geli.m.mvp.base.BasePresenter;

/**
 * shen
 */
public class TransferAccountsPresentImpl extends BasePresenter<TransferAccountsView, TransferAccountsModelImpl> {


    @Override
    protected TransferAccountsModelImpl createModel() {
        return new TransferAccountsModelImpl();
    }

    public TransferAccountsPresentImpl(TransferAccountsView mvpView) {
        super(mvpView);
    }


//    public void transfer(String user_id, String order_sn) {
//       mModel.transfer(user_id, order_sn, new BaseObserver<TransferBean>(this, mvpView, true) {
//            @Override
//            protected void onSuccess(TransferBean data) {
//                if(data.getCode() == Constant.REQUEST_OK){
//                    mvpView.getTransferData(data);
//                }else {
//                    mvpView.onError(data.getMessage());
//                }
//            }
//        });
//    }
}
