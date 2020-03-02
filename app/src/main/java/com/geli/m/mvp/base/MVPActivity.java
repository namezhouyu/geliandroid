package com.geli.m.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;


/**
 * @param <P>具体的presenter
 */
public abstract class MVPActivity<P extends BasePresenter> extends BaseActivity implements BaseView{

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.dismissProgress();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }


    /**
     * @return 返回具体的Persenter
     */
    protected abstract P createPresenter();

    public void onSuccess(String msg){
        ToastUtils.showToast(msg);
    }

    public void onError(String msg){
        ToastUtils.showToast(msg);
    }

    public void showLoading(){}

    public void hideLoading(){}
}
