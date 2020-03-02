package com.geli.m.mvp.home.mine_fragment.address_activity;

import com.geli.m.R;
import com.geli.m.bean.AddressListBean;
import com.geli.m.bean.BaseBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.utils.Utils;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/1/9.
 */

public class AddressListPresentImpl extends BasePresenter<AddressListView, AddressListModelImpl> {
    public AddressListPresentImpl(AddressListView mvpView) {
        super(mvpView);
    }

    public void getAddressList(String user_id) {
        mModel.getAddressList(user_id, new BaseObserver<AddressListBean>(this, mvpView) {
            @Override
            protected void onSuccess(AddressListBean data) {
                if (data.getCode() == Constant.REQUEST_OK) {
                    mvpView.showList(data);
                } else {
                    mvpView.onError(data.getMessage());
                }
            }

        });
    }

    public void deleteAddress(String user_id, final String add_id) {
        mModel.deleteAddress(user_id, add_id, new BaseObserver<ResponseBody>(this, mvpView) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    BaseBean baseBean = parseData(data.string());
                    if (baseBean.code == Constant.REQUEST_OK) {
                        mvpView.onSuccess(Utils.getString(R.string.delete_success));
                        mvpView.deleteSuccess(add_id);
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
    protected AddressListModelImpl createModel() {
        return new AddressListModelImpl();
    }
}
