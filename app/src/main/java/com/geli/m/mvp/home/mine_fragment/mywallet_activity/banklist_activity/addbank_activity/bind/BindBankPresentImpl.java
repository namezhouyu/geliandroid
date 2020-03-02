package com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity.addbank_activity.bind;

import com.geli.m.bean.BaseBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.home.mine_fragment.setting_activity.getcode_activity.GetCodePresentImpl;
import com.geli.m.mvp.home.mine_fragment.setting_activity.getcode_activity.GetCodeView;
import java.util.Map;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/3/24.
 */

public class BindBankPresentImpl extends GetCodePresentImpl<GetCodeView, BindBankModelImpl> {
    public BindBankPresentImpl(GetCodeView mvpView) {
        super(mvpView);
    }

    public void bindBank(Map data) {
        ((BindBankModelImpl) mModel).bindBank(data, new BaseObserver<ResponseBody>(this, mvpView) {
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
    protected BindBankModelImpl createModel() {
        return new BindBankModelImpl();
    }
}
