package com.geli.m.mvp.home.mine_fragment.setting_activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.api.ApiEngine;
import com.geli.m.bean.AbousUsBean;
import com.geli.m.bean.AppVersion;
import com.geli.m.config.Constant;
import com.geli.m.dialog.TipDialog;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.mine_fragment.setting_activity.getcode_activity.GetCodeActivity;
import com.geli.m.mvp.home.other.WebViewActivity;
import com.geli.m.utils.DataCleanManager;
import com.geli.m.utils.LoginInformtaionSpUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.UpdateddVersionUtils;
import com.geli.m.utils.Utils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.geli.m.config.Constant.ACTION_MODIFY;
import static com.geli.m.config.Constant.INTENT_LINKS;
import static com.geli.m.config.Constant.INTENT_TYPE;

/**
 * Created by Steam_l on 2018/1/8.
 *
 * 设置中心
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    /** 标题 */
    @BindView(R.id.tv_title_name)
    TextView mTvTitle;
    /** 缓存大小 */
    @BindView(R.id.tv_setting_cachesize)
    TextView mTvCacheSize;
    /** 退出登录 */
    @BindView(R.id.tv_setting_sign_out)
    TextView mTvSignOut;
    /** 当前版本xxx  /  有更新版本 */
    @BindView(R.id.tv_setting_newversion)
    TextView mTvNewverion;
    /** 从网络中获取最新版本的信息 -- 没有最新版本，这个就是 null */
    AppVersion.DataBean mUpdatedData;

    @Override
    protected int getResId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {
        mTvTitle.setText(Utils.getString(R.string.title_personal_setting));
        setCacheSize();
        setVersion();
        checkVersion();
    }

    @Override
    protected void initEvent() {

    }

    /**
     * 目前安装的版本
     */
    private void setVersion() {
        mTvNewverion.setTextColor(Utils.getColor(R.color.text_color));
        mTvNewverion.setText(Utils.getString(R.string.current_version, Utils.getVersionName(mContext) + ""));
    }

    /**
     * 网络获取版本信息
     */
    private void checkVersion() {
        // 网络请求下，获取最新版本的信息
        UpdateddVersionUtils.checkVersionUpdated(mContext, new UpdateddVersionUtils.UpdatedVerionListener() {
            @Override
            public void update(AppVersion.DataBean versionData) {
                mUpdatedData = versionData;
                mTvNewverion.setTextColor(Utils.getColor(R.color.zhusediao));
                mTvNewverion.setText(Utils.getString(R.string.have_newversion));
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        // 根据是否登录设置"退出登录"是否显示
        if (LoginInformtaionSpUtils.getLoginInfString(mContext, LoginInformtaionSpUtils.login_login).equals("1")) {
            mTvSignOut.setVisibility(View.VISIBLE);
        } else {
            mTvSignOut.setVisibility(View.GONE);
        }
    }

    /**
     * 获取缓存数据--显示
     */
    private void setCacheSize() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext(DataCleanManager.getTotalCacheSize(mContext));
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        mTvCacheSize.setText(s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }



    @OnClick({R.id.tv_setting_newversion, R.id.tv_setting_aboutus, R.id.tv_setting_loginpasssetting, R.id.tv_setting_paypasssetting, R.id.iv_title_back, R.id.tv_setting_sign_out, R.id.ll_setting_clearcachelayout})
    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()) {
            case R.id.tv_setting_newversion:                                    /* 当前版本xxx / 有最新版本 */
                downloadNewVersion();
                break;

            case R.id.tv_setting_aboutus:                                      /* 关于我们 */
                aboutUs();
                break;

            case R.id.tv_setting_loginpasssetting:                          /* 登录密码设置(忘记密码) */
                intent = new Intent().putExtra(INTENT_TYPE, GetCodeActivity.TYPE_LOGINPASS);
                startActivity(GetCodeActivity.class, intent);
                break;

            case R.id.tv_setting_paypasssetting:                            /* 支付密码设置 */
                intent = new Intent().putExtra(INTENT_TYPE, GetCodeActivity.TYPE_PAYPASS);
                startActivity(GetCodeActivity.class, intent);
                break;

            case R.id.iv_title_back:
                finish();
                break;

            case R.id.tv_setting_sign_out:                                  /* 退出登录 */
                signOut();
                break;

            case R.id.ll_setting_clearcachelayout:                          /* 清除缓存 */
                clearCacheDialog();
                break;

            default:
                break;
        }
    }




    /**
     * 关于我们 -- 获取连接 -- 到WebViewActivity打开
     */
    private void aboutUs() {
        ApiEngine.getInstance().getApiService().getAbousUs("aboutus")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AbousUsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AbousUsBean abousUsBean) {
                        if (abousUsBean.getCode() == Constant.REQUEST_OK) {
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra(INTENT_LINKS, abousUsBean.getData().getContent());
                            intent.putExtra(WebViewActivity.EXTRAS_IS_SHARE, true);
                            startActivity(intent);
                        } else {
                            ToastUtils.showToast(Utils.getString(R.string.error_again));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showToast(Utils.getString(R.string.error_again));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 下载新版本
     */
    private void downloadNewVersion() {
        if (mUpdatedData == null) {
            ToastUtils.showToast(Utils.getString(R.string.already_the_latest_version));
            return;
        }
        UpdateddVersionUtils.requestPermissions(mContext,
                mUpdatedData.getUrl(),
                mUpdatedData.getVersions() + "",
                mUpdatedData.getDesc(),
                mUpdatedData.getIs_update());
    }

    /**
     * 退出登录
     */
    private void signOut() {
        Intent intent;
        LoginInformtaionSpUtils.clearSP(mContext);
        ShowSingleToast.showToast(mContext, Utils.getString(R.string.message_signout_success));
        intent = new Intent(ACTION_MODIFY); // 发送广播  MineFragment 接收
        sendBroadcast(intent);
        finish();
    }

    /**
     * 显示清除缓存的窗口
     */
    private void clearCacheDialog() {
        final TipDialog tipDialog = TipDialog.newInstance(Utils.getString(R.string.dialog_cleantext));
        tipDialog.setOnclickListener(new TipDialog.ClickListenerInterface() {
            @Override
            public void doConfirm(TipDialog tipDialog) {
                tipDialog.getDialog().dismiss();
                clearCache();
            }

            @Override
            public void doCancel() {
                tipDialog.getDialog().dismiss();
            }
        });
        tipDialog.show(getSupportFragmentManager(), "");
    }

    /**
     * 清除缓存
     */
    private void clearCache() {
        mTvCacheSize.setText(Utils.getString(R.string.clearcacheing));

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                DataCleanManager.clearAllCache(mContext);
                e.onNext(DataCleanManager.getTotalCacheSize(mContext));
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        mTvCacheSize.setText(s);
                        ShowSingleToast.showToast(mContext, Utils.getString(R.string.cacheclearedsuccess));
                    }

                    @Override
                    public void onError(Throwable e) {
                        setCacheSize();
                        ShowSingleToast.showToast(mContext, Utils.getString(R.string.clearcachefailed));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if(resultCode == Activity.RESULT_OK){
            if(requestCode == UpdateddVersionUtils.REQUEST_CODE_APP_INSTALL){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if(UpdateddVersionUtils.isHasInstallPermissionWithO(mContext)){

                        if(mUpdatedData != null) {
                            UpdateddVersionUtils.requestPermissions(mContext,
                                    mUpdatedData.getUrl(),
                                    mUpdatedData.getVersions() + "",
                                    mUpdatedData.getDesc(),
                                    mUpdatedData.getIs_update());
                        }
                    }
                }
         //   }
        }
    }

}
