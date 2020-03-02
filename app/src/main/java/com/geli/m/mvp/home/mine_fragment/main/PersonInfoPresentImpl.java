package com.geli.m.mvp.home.mine_fragment.main;

import com.geli.m.app.GlobalData;
import com.geli.m.bean.PersonInfoBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.utils.ACache;
import com.geli.m.utils.LogUtils;
import com.google.gson.Gson;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Steam_l on 2018/2/5.
 */

public class PersonInfoPresentImpl extends BasePresenter<MineView, PersonInfoModelImpl> {
    public PersonInfoPresentImpl(MineView mvpView) {
        super(mvpView);
    }

    public void getPersonInfo(String user_id) {
        //if(NetWorkUtils.isNetworkConnected(GlobalData.getInstance())) {
            mModel.getPersonInfo(user_id, new BaseObserver<PersonInfoBean>(this, mvpView, false) {
                @Override
                protected void onSuccess(PersonInfoBean data) {
                    if (data.getCode() == Constant.REQUEST_OK) {
                        String s = new Gson().toJson(data);
                        setCache(s);
                        mvpView.showPersonInfo(data.getData());
                    } else {
                        mvpView.getPersonInfoError();
                    }
                }
            });
//        }else {
//            String string = ACache.get(GlobalData.getInstance()).getAsString(mKey_cache);  // 获取缓存数据
//            if (StringUtils.isNotEmpty(string)) {              /* 有缓存数据 */
//                PersonInfoBean p = parsePersonInfoBeanData(string);
//                mvpView.showPersonInfo(p.getData());
//            }
//        }
    }


    @Override
    protected PersonInfoModelImpl createModel() {
        return new PersonInfoModelImpl();
    }

    private String mKey_cache = "userinfo_cachedata";

    /**
     * 观察者，等人家订阅 -- 获取数据成功了，解析也成功了
     *
     * @return
     */
    private BaseObserver<PersonInfoBean> personInfoBeanObserver(){
        return new BaseObserver<PersonInfoBean>(this, mvpView, false) {
            @Override
            protected void onEnd() {
                super.onEnd();
                //mvpView.hideLoading();     // 避免第一次刷新缓存拿到后隐藏refresh控件
            }

            @Override
            protected void onSuccess(final PersonInfoBean data) {
                //mvpView.onSuccess(Utils.getString(R.string.refresh_success));
                mvpView.showPersonInfo(data.getData());

            }

        };
    }


    /**
     * 缓存数据
     *
     * @param string    要缓存的数据
     */
    // @SuppressLint("CheckResult")
    private void setCache(String string){
        Observable.just(string)                             // 设置下"缓存"
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onNext(String s) {
                                   if (GlobalData.getInstance() != null) {
                                       LogUtils.i("setCache");
                                       ACache.get(GlobalData.getInstance())
                                               .put(mKey_cache, s, 60 * 60 * 24 * 28);
                                   }
                               }

                               @Override
                               public void onError(Throwable e) {

                               }

                               @Override
                               public void onComplete() {

                               }
                           }
                );
    }

    private PersonInfoBean parsePersonInfoBeanData(String s){
        return  new Gson().fromJson(s, PersonInfoBean.class);
    }

}
