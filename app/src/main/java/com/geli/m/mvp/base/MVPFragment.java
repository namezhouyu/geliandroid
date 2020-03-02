package com.geli.m.mvp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.utils.ToastUtils;

/**
 * Created by Administrator on 2017/9/21.
 *
 * @param <P> 具体的presenter
 */

public abstract class MVPFragment<P extends BasePresenter> extends BaseFragment implements BaseView{
    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresent();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }


    protected abstract P createPresent();


    public void onSuccess(String msg){
        ToastUtils.showToast(msg);
    }

    public void onError(String msg){
        ToastUtils.showToast(msg);
    }

    public void showLoading(){}

    public void hideLoading(){}


    public void startActivity(Class clazz, Intent intent){
        if(getActivity() instanceof BaseActivity){
            ((BaseActivity) mContext).startActivity(clazz, intent);
        }
    }

    protected void finish(){
        ((BaseActivity) mContext).finish();
    }
}
