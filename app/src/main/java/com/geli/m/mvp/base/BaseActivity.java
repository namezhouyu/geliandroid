package com.geli.m.mvp.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.mvp.home.index_fragment.message_activity.MessActivity;
import com.geli.m.mvp.home.mine_fragment.accountmanagement_activity.AccountManagementActivity;
import com.geli.m.mvp.home.mine_fragment.accountorder_activity.AccountOrderActivity;
import com.geli.m.mvp.home.mine_fragment.address_activity.AddressActivity;
import com.geli.m.mvp.home.mine_fragment.browse_activity.BrowseActivity;
import com.geli.m.mvp.home.mine_fragment.collection_activity.CollectionActivity;
import com.geli.m.mvp.home.mine_fragment.coupon_activity.CouponManagerActivity;
import com.geli.m.mvp.home.mine_fragment.invoice_activity.InvoiceActivity;
import com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicetype_activity.InvoiceTypeActivity;
import com.geli.m.mvp.home.mine_fragment.invoicemerge_activity.InvoiceMergeActivity;
import com.geli.m.mvp.home.mine_fragment.member_activity.MemberCenterActivity;
import com.geli.m.mvp.home.mine_fragment.myorder_activity.MyOrderActivity;
import com.geli.m.mvp.home.mine_fragment.mywallet_activity.main.MyWalletActivity;
import com.geli.m.mvp.home.mine_fragment.setting_activity.getcode_activity.GetCodeActivity;
import com.geli.m.mvp.home.other.accountperiod_activity.AccountPeriodActivity;
import com.geli.m.mvp.home.other.accountperiod_opened_activity.AccountPeriodOpenedActivity;
import com.geli.m.mvp.home.other.login_register_activity.LoginActivity;
import com.geli.m.utils.KeyBoardUtils;
import com.geli.m.utils.LoginInformtaionSpUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.gyf.barlibrary.ImmersionBar;
import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public abstract class BaseActivity extends AppCompatActivity {

    Unbinder unbinder;
    protected Context mContext;
    protected ImmersionBar mImmersionBar;

    KeyBoardUtils.KeyBoardListener mBoardListener;

    private View mChildOfContent;

    private int usableHeightPrevious;

    /** 需要登录后才可以操作的 */
    private List<Class> loginList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Android setRequestedOrientation用法
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // 通过程序改变屏幕显示的方向
        // 1.landscape：     横屏(风景照) ， 显示时宽度大于高度；
        // 2.portrait：      竖屏(肖像照) ， 显示时高度大于宽度；
        // 3.user：          用户当前的首选方向；
        // 4.behind：        继承Activity堆栈中当前Activity下面的那个Activity的方向；
        // 5.sensor：        由物理感应器决定显示方向，它取决于用户如何持有设备，当设备被旋转时方向会随之变化——在横屏与竖屏之间；
        // 6.nosensor：      忽略物理感应器——即显示方向与物理感应器无关，不管用户如何旋转设备显示方向都不会随着改变("unspecified"设置除外)；
        // 7.unspecified：   未指定，此为默认值，由Android系统自己选择适当的方向，选择策略视具体设备的配置情况而定，因此不同的设备会有不同的方向选择；
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getResId());
        mContext = this;
        unbinder = ButterKnife.bind(this);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .fitsSystemWindows(true)
                .keyboardEnable(true)
                // 原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .statusBarDarkFont(true, 0.2f)
                .init();
        initList();
        init();
        initData();
        initEvent();
    }


    /* ------------设置字体为默认大小，不随系统字体大小改而改变 start---------------- */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }


    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }
    /* ------------设置字体为默认大小，不随系统字体大小改而改变 end---------------- */

    /**
     * 添加"需要登录后才可以操作的.class"
     */
    protected void initList() {
        loginList.add(MyOrderActivity.class);
        loginList.add(MyWalletActivity.class);
        loginList.add(MemberCenterActivity.class);
        loginList.add(CouponManagerActivity.class);
        loginList.add(InvoiceActivity.class);
        loginList.add(InvoiceTypeActivity.class);
        loginList.add(InvoiceMergeActivity.class);
        loginList.add(AddressActivity.class);
        loginList.add(BrowseActivity.class);
        loginList.add(GetCodeActivity.class);
        loginList.add(MessActivity.class);
        loginList.add(CollectionActivity.class);
        loginList.add(AccountPeriodActivity.class);
        loginList.add(AccountPeriodOpenedActivity.class);
        loginList.add(AccountManagementActivity.class);
        loginList.add(AccountOrderActivity.class);
    }


    protected void initKeyBoardListener() {
        if (mChildOfContent == null) {
            mChildOfContent = ((ViewGroup) getWindow().getDecorView()).getChildAt(0);
            mChildOfContent.getViewTreeObserver()
                    .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        public void onGlobalLayout() {
                            possiblyResizeChildOfContent();
                        }
                    });
        }
    }

    public static boolean gotoLogin(Object view) {
        Context context = GlobalData.mContext;
        if (view instanceof BaseFragment) {
            context = ((BaseFragment) view).getContext();
        } else if (view instanceof BaseActivity) {
            context = (Context) view;
        }
        boolean noLogin = !LoginInformtaionSpUtils.getLoginInfString(context, LoginInformtaionSpUtils.login_login).equals("1");
        if (noLogin) {
            context.startActivity(new Intent(context, LoginActivity.class));
        }
        return noLogin;
    }


    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // 键盘弹出
                mBoardListener.showKeyBoard();
            } else {
                // 键盘收起
                mBoardListener.hideKeyBoard();
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }


    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }


    public void startActivity(Class clazz, Intent intent) {
        if (!LoginInformtaionSpUtils.getLoginInfString(mContext, LoginInformtaionSpUtils.login_login).equals("1")) {
            for (Class childe : loginList) {
                if (childe.equals(clazz)) {
                    ShowSingleToast.showToast(mContext, Utils.getString(R.string.please_login));
                    startActivity(LoginActivity.class, new Intent());
                    return;
                }
            }
        }
        if(intent == null){
            intent = new Intent();
            intent.setClass(mContext, clazz);
        }else {
            intent.setClass(mContext, clazz);
        }
        startActivity(intent);
    }

    public void startActivity(Class clazz, Bundle bundle) {
        if (!LoginInformtaionSpUtils.getLoginInfString(mContext, LoginInformtaionSpUtils.login_login).equals("1")) {
            for (Class childe : loginList) {
                if (childe.equals(clazz)) {
                    ShowSingleToast.showToast(mContext, Utils.getString(R.string.please_login));
                    startActivity(LoginActivity.class, new Intent());
                    return;
                }
            }
        }
        Intent intent = new Intent(mContext, clazz);
        if(bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }



    public void startActivityForResult(Class clazz, Intent intent, int requestCode) {
        if (!LoginInformtaionSpUtils.getLoginInfString(mContext, LoginInformtaionSpUtils.login_login).equals("1")) {
            for (Class childe : loginList) {
                if (childe.equals(clazz)) {
                    ShowSingleToast.showToast(mContext, Utils.getString(R.string.please_login));
                    startActivity(LoginActivity.class, new Intent());
                    return;
                }
            }
        }
        if(intent == null){
            intent = new Intent();
            intent.setClass(mContext, clazz);
        }else {
            intent.setClass(mContext, clazz);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 监听键盘的显示和隐藏
     *
     * @return
     */
    public void setKeyBoardListener(KeyBoardUtils.KeyBoardListener listener) {
        this.mBoardListener = listener;
        initKeyBoardListener();
    }

    //    @Override
    //    protected void onResume() {
    //        super.onResume();
    //        System.out.println("=============" + getActivitiesByApplication(getApplication()) + "===============");
    //    }

    //    private static List<Activity> getActivitiesByApplication(Application application) {
    //        List<Activity> list = new ArrayList<>();
    //        try {
    //            Class<Application> applicationClass = Application.class;
    //            Field mLoadedApkField = applicationClass.getDeclaredField("mLoadedApk");
    //            mLoadedApkField.setAccessible(true);
    //            Object mLoadedApk = mLoadedApkField.get(application);
    //            Class<?> mLoadedApkClass = mLoadedApk.getClass();
    //            Field mActivityThreadField = mLoadedApkClass.getDeclaredField("mActivityThread");
    //            mActivityThreadField.setAccessible(true);
    //            Object mActivityThread = mActivityThreadField.get(mLoadedApk);
    //            Class<?> mActivityThreadClass = mActivityThread.getClass();
    //            Field mActivitiesField = mActivityThreadClass.getDeclaredField("mActivities");
    //            mActivitiesField.setAccessible(true);
    //            Object mActivities = mActivitiesField.get(mActivityThread);
    //            // 注意这里一定写成Map，低版本这里用的是HashMap，高版本用的是ArrayMap
    //            if (mActivities instanceof Map) {
    //                @SuppressWarnings("unchecked")
    //                Map<Object, Object> arrayMap = (Map<Object, Object>) mActivities;
    //                for (Map.Entry<Object, Object> entry : arrayMap.entrySet()) {
    //                    Object value = entry.getValue();
    //                    Class<?> activityClientRecordClass = value.getClass();
    //                    Field activityField = activityClientRecordClass.getDeclaredField("activity");
    //                    activityField.setAccessible(true);
    //                    Object o = activityField.get(value);
    //                    list.add((Activity) o);
    //                }
    //            }
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //            list = null;
    //        }
    //        return list;
    //    }

    protected abstract int getResId();

    protected void init() {
    }

    protected abstract void initData();

    protected abstract void initEvent();


    protected void showToast(String message){
        ToastUtils.showToast(mContext, message);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
    }

}
