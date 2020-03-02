package com.geli.m.mvp.base;

/**
 * shen
 */
public class EmptyPresentImpl extends BasePresenter<EmptyView, EmptyModelImpl> {


    @Override
    protected EmptyModelImpl createModel() {
        return new EmptyModelImpl();
    }

    public EmptyPresentImpl(EmptyView mvpView) {
        super(mvpView);
    }


//    public void deleteCart(String user_id, String cart_id) {
//       mModel.delCart(user_id, cart_id, new BaseObserver<ResponseBody>(this, mvpView, true) {
//            @Override
//            protected void onSuccess(ResponseBody data) {
//                try {
//                    BaseBean baseBean = parseData(data.string());
//                    if (baseBean.code == Constant.REQUEST_OK) {
//                        mvpView.onSuccess(Utils.getString(R.string.delete_success));
//                    } else {
//                        mvpView.onError(baseBean.message);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    mvpView.onError(parseError(e));
//                }
//            }
//        });
//    }


}
