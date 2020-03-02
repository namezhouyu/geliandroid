package com.geli.m.mvp.home.mine_fragment.setting_activity.getcode_activity.loginpass_activity;

import com.geli.m.R;
import com.geli.m.bean.BaseBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.mvp.base.BaseView;
import com.geli.m.utils.Utils;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/2/6.
 */
public class ChangeLoginPassPresentImpl extends BasePresenter<BaseView, ChangeLoginPassModelImpl> {
    public ChangeLoginPassPresentImpl(BaseView mvpView) {
        super(mvpView);
    }

    public void changeLoginPass(String url, Map data) {
        mModel.changePass(url, data, new BaseObserver<ResponseBody>(this, mvpView) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    BaseBean baseBean = parseData(data.string());
                    if (baseBean.code == Constant.REQUEST_OK) {
                        mvpView.onSuccess(Utils.getString(R.string.modify_success));
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
    protected ChangeLoginPassModelImpl createModel() {
        return new ChangeLoginPassModelImpl();
    }
}
