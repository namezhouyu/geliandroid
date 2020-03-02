package com.geli.m.mvp.base;

import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.utils.LogUtils;
import com.geli.m.utils.Utils;
import com.google.gson.JsonSyntaxException;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import retrofit2.HttpException;

/**
 * Created by Administrator on 2017/9/21.
 *
 */
public abstract class BaseObserver<T> implements Observer {

    /* 对应HTTP的状态码 */
    private static final int NOT_FOUND = 404;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int UNSATISFIABLE_REQUEST = 504;
    private static final int SERVICE_TEMPORARILY_UNAVAILABLE = 503;
    private static final int BAD_GATEWAY = 502;

    private final BasePresenter mPresenter;
    private final BaseView mvpView;

    /** 是否显示加载框 */
    private final boolean isShowLoading;

    public BaseObserver(BasePresenter presenter, BaseView mvpView) {
        this(presenter, mvpView, true);
    }

    public BaseObserver(BasePresenter presenter, BaseView mvpView, boolean isShowLoading) {
        mPresenter = presenter;
        this.mvpView = mvpView;
        this.isShowLoading = isShowLoading;
    }


    protected abstract void onSuccess(T data);

    protected void onError(String msg) {
        mvpView.onError(msg);
    }



    @Override
    public void onSubscribe(@NonNull Disposable d) {
        mPresenter.addDisposable(d);
        onStart();
    }

    @Override
    public void onNext(@NonNull Object o) {
        onSuccess((T) o);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onError(parseError(e));
        onEnd();
    }

    @Override
    public void onComplete() {
        onEnd();
    }


    /**
     * 开始,显示加载框,不需要的话重写
     */
    protected void onStart() {
        if (isShowLoading) {
            mvpView.showLoading();
        }
    }


    /**
     * 结束,隐藏加载框,不需要的话重写
     */
    protected void onEnd() {
        if (isShowLoading) {
            mvpView.hideLoading();
        }
    }

    /**
     * 解析异常
     *
     * @param e 异常
     * @return
     */
    public static String parseError(Throwable e) {
        String msg = "";
        if (e instanceof HttpException) {                               /* 网络错误 */
            int code = ((HttpException) e).code();
            switch (code) {
                case NOT_FOUND:                       /* 404 */
                    msg = Utils.getString(R.string.message_not_found);
                    break;

                case INTERNAL_SERVER_ERROR:           /* 500 */
                    msg = Utils.getString(R.string.message_internal_server_error);
                    break;

                case UNSATISFIABLE_REQUEST:           /* 504 */
                    msg = Utils.getString(R.string.message_unsatisfiable_request);
                    break;

                case SERVICE_TEMPORARILY_UNAVAILABLE: /* 503 */
                    msg = Utils.getString(R.string.message_server_error);
                    break;

                case BAD_GATEWAY:                     /* 502 */
                    msg = Utils.getString(R.string.bad_gateway);
                    break;

                default:
                    break;
            }

        } else if (e instanceof UnknownHostException) {                 /* 没有网络 */
            GlobalData.hasNetWork = false;
            msg = Utils.getString(R.string.message_unknownhost);

        } else if (e instanceof SocketTimeoutException) {               /* 连接超时 */
            GlobalData.hasNetWork = false;
            msg = Utils.getString(R.string.message_sockettimeout);

        } else if (e instanceof ConnectException) {                     /* 连接异常 */
            GlobalData.hasNetWork = false;
            msg = Utils.getString(R.string.message_connectexception);

        } else if (e instanceof ParseException) {                       /* 数据解析失败 */
            msg = Utils.getString(R.string.message_data_parsing_filed);

        } else if (e instanceof JsonSyntaxException) {                  /* 数据解析失败 */
            msg = Utils.getString(R.string.message_data_parsing_filed);

        } else if (e instanceof IOException) {                          /* 数据读取失败 -- IO异常 */
            msg = Utils.getString(R.string.message_data_read_filed);

        } else {                                                        /* 未知错误 */
            msg = Utils.getString(R.string.message_unknown_mistake);
        }

        if (e != null) {
            e.printStackTrace();
            // Log.e("BaseObserver", "onError: " + e.getCause() + "  " + e.getMessage() + "  " + e.toString());
            LogUtils.i("错误类型:" + msg);
            LogUtils.i("BaseObserver<T> -- parseError:" , e);

        }

        return msg;
    }



}
