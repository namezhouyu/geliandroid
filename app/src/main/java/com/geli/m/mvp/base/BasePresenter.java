package com.geli.m.mvp.base;

import com.geli.m.bean.BaseBean;
import com.geli.m.config.Constant;
import com.google.gson.Gson;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import org.json.JSONObject;


/**
 * @param <V>具体的View
 * @param <M>具体的model
 */
public abstract class BasePresenter<V, M extends BaseModel> {
    protected final Gson mGson;
    protected V mvpView;
    protected CompositeDisposable mDisposable;
    protected M mModel;

    public BasePresenter(V mvpView) {
        this.mvpView = mvpView;
        mModel = createModel();
        mGson = new Gson();
    }


    public static BaseBean parseData(String string) throws Exception {

        JSONObject jsonObject  = new JSONObject(string);
        String message = jsonObject.getString("message");
        int code = jsonObject.getInt("code");
        BaseBean baseBean = new BaseBean();
        baseBean.code = code;
        baseBean.message = message;
        return baseBean;
    }

    protected abstract M createModel();

    public void detachView() {
        this.mvpView = null;
        clearDisposable();
    }

    /**
     * Rxjava取消注册(取消订阅)，以避免内存泄露
     */
    public void clearDisposable() {
        if (mDisposable != null && mDisposable.size() != 0) {
            mDisposable.clear();
        }
    }


    public void addDisposable(Disposable disposable) {
        if (mDisposable == null) {
            mDisposable = new CompositeDisposable();
        }
        mDisposable.add(disposable);
    }

    public boolean isGetDataSuccess(int code){
        if(code == Constant.REQUEST_OK ||
                code == Constant.REQUEST_NODATA ||
                code == Constant.REQUEST_NODATA_201){
            return true;
        }else {
            return false;
        }
    }
}
