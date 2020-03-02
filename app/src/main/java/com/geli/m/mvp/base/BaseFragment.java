package com.geli.m.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.geli.m.utils.ToastUtils;

/**
 * Created by Reims
 * init:获得数据new对象等
 * initData:初始化数据
 * initEvent:设置监听事件
 */

public abstract class BaseFragment extends Fragment {
    protected View mRootView;
    Unbinder unbinder;
    protected Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getResId(), container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        mContext = getActivity();
        return mRootView;
    }

    public abstract int getResId();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        initData();
        initEvent();
    }

    protected void init() {
    }

    protected abstract void initData();

    protected abstract void initEvent();

    protected void showToast(String message){
        ToastUtils.showToast(mContext, message);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
