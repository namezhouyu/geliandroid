package com.geli.m.mvp.home.main;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import butterknife.BindView;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.AppVersion;
import com.geli.m.config.Constant;
import com.geli.m.coustomview.NoScrollViewpager;
import com.geli.m.dialog.UpdatedVersionDialog;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.other.goodsdetails_activity.GoodsDetailsActivity;
import com.geli.m.mvp.home.other.shopdetails_activity.ShopDetailsActivity;
import com.geli.m.utils.FilesUtils;
import com.geli.m.utils.LogUtils;
import com.geli.m.utils.UpdateddVersionUtils;
import com.geli.m.utils.Utils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.io.File;

import static com.geli.m.app.GlobalData.initQb;
import static com.geli.m.config.Constant.CartFragment;
import static com.geli.m.config.Constant.FindFragment;
import static com.geli.m.config.Constant.INTENT_CAT_ID;
import static com.geli.m.config.Constant.INTENT_GOODS_ID;
import static com.geli.m.config.Constant.INTENT_ITEM;
import static com.geli.m.config.Constant.INTENT_SHOP_ID;
import static com.geli.m.config.Constant.IndexFragment;
import static com.geli.m.config.Constant.MineFragment;

/**
 * Created by Steam_l on 2017/12/15.
 *
 * 主界面
 */
public class HomeActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,
        android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener {

    private long mLastTime = 0;

    @BindView(R.id.vp_content_home)
    NoScrollViewpager mVpContent;

    /** 底部4大按钮 */
    @BindView(R.id.rg_home_bottom)
    RadioGroup mRbCheck;

    int mCurrItem = 0;

    /** "版本更新"广播接收者 */
    private UpdatedBroadcastReceiver mReceiver;

    /** 从网络中获取最新版本的信息 -- 没有最新版本，这个就是 null */
    AppVersion.DataBean mUpdatedData;

    @Override
    protected int getResId() {
        //getWindow().setBackgroundDrawable(null);    // 设置背景为空
        return R.layout.activity_home;
    }

    @Override
    protected void init() {
        super.init();
        mImmersionBar.fitsSystemWindows(false).init();

        // CrashReport.testJavaCrash();        // 腾讯Bugly -- 测试bug
    }

    /* singleTask
     * 如果要启动的Activity在当前栈内启动，activity只会在任务栈里面存在一个实例。
     * 如果要激活的activity，在任务栈里面已经存在，就不会创建新的activity，
     * 而是复用这个已经存在的activity，调用 onNewIntent() 方法，并且清空这个activity任务栈上面所有的activity。
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        getUriOpenApp(intent);

        mCurrItem = -1;
        mCurrItem = intent.getIntExtra(INTENT_ITEM, mCurrItem);
        if (mCurrItem == -1) {
            return;
        }

        if (mCurrItem == IndexFragment) {                   /* 首页 */
            mRbCheck.check(R.id.rb_home_index);
        } else if (mCurrItem == FindFragment) {             /* 发现 */
            mRbCheck.check(R.id.rb_home_find);
        }
//        else if (mCurrItem == RestaurantListActivity) {       /* 食品馆 */
//            mRbCheck.check(R.id.rb_home_restaurant);
//        }
        else if (mCurrItem == CartFragment) {             /* 购物车 */
            mRbCheck.check(R.id.rb_home_cart);
        } else {                                            /* 我的 */
            mRbCheck.check(R.id.rb_home_mine);
        }
    }


    /**
     * 使用外部链接 打开 app
     *
     *
     * 前端的 ：
     * http://m.gelifood.com/openApp?code=101&goods_id=5520
     * http://m.gelifood.com/openApp?code=102&shop_id=100558&cat_id=55
     * http://m.gelifood.com/openApp?code=102&shop_id=100558&cat_id=57
     *
     * 这个会链接到 下面的（我实际接受的是这些）
     * -- gelifood//goodsdetails?code=101&goods_id=5520
     * -- gelifood//shopdetails?code=102&shop_id=100558&cat_id=55
     * -- gelifood//shopdetails?code=102&shop_id=100558&cat_id=57
     *
     *
     * @param intent
     */
    private void getUriOpenApp(Intent intent){
        if (intent != null) {                                       /* 外部链接打开app */
            String intentAction = intent.getAction();
            if (Intent.ACTION_VIEW.equals(intentAction)) {
                Uri intentData = intent.getData();
                if(intentData.getHost().equals("goodsdetails")) {
                    String goods_id = intentData.getQueryParameter("goods_id");
                    startActivity(GoodsDetailsActivity.class, new Intent().putExtra(INTENT_GOODS_ID, goods_id));

                }else if(intentData.getHost().equals("shopdetails")){
                    String shop_id = intentData.getQueryParameter("shop_id");
                    String cat_id = intentData.getQueryParameter("cat_id");

                    startActivity(ShopDetailsActivity.class,
                            new Intent().putExtra(INTENT_SHOP_ID, shop_id)
                                    .putExtra(INTENT_CAT_ID, cat_id));
                }
//                LogUtils.i("intentData.getHost():" + intentData.getHost());
            }
        }
    }



    @Override
    protected void initData() {
        initVp();
        initBroadcastReceiver();

        initDir();
    }

    @Override
    protected void initEvent() {
        mRbCheck.setOnCheckedChangeListener(this);
        getUriOpenApp(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initDir() {

        //取消严格模式FileProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy( builder.build() );
        }

        requestPermissions(mContext);
    }

    /**
     *
     * 获取下权限 -- 再下载
     * @param context
     */
    public void requestPermissions(final Context context) {
        RxPermissions rxPermissions = new RxPermissions((BaseActivity) context);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            FilesUtils.createFile(Constant.DownAgreementDir, "temp.txt");
                            FilesUtils.createFile(Constant.UpLoadAgreementDir, "temp.txt");
                            FilesUtils.createFile(Constant.InvoiceDir, "temp.txt");

                            UpdateddVersionUtils.checkVersionUpdated(mContext, new UpdateddVersionUtils.UpdatedVersionAutoListener() {
                                @Override
                                public void update(AppVersion.DataBean versionData) {
                                    mUpdatedData = versionData;
                                }
                            });             // 检查版本
                        } else {
                            Utils.showMissPermissionDialog(context, Utils.getString(R.string.read_write),
                                    Utils.getString(R.string.create_dir));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
//                        FilesUtils.deleteFileNoBroadCreate(new File(Constant.DownAgreementDir, "temp.txt"));
//                        FilesUtils.deleteFileNoBroadCreate(new File(Constant.UpLoadAgreementDir, "temp.txt"));
//                        FilesUtils.deleteFileNoBroadCreate(new File(Constant.InvoiceDir, "temp.txt"));

                        initQb();
                    }
                });
    }

    public static void installApkFile(Context context, String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.yuneec.android.saleelfin.fileprovider", new File(filePath));
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }



    /**
     * 设置VP
     */
    private void initVp() {
        HomeAdapter adapter = new HomeAdapter(getSupportFragmentManager());
        mVpContent.setAdapter(adapter);
        mVpContent.setOffscreenPageLimit(adapter.getCount());           // 预加载页数
    }

    /**
     * 设置广播
     */
    private void initBroadcastReceiver() {
        // 广播拦截 -- 需要拦截的广播 -- 下载完成时，下载管理器发送的广播意图动作。
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        mReceiver = new UpdatedBroadcastReceiver();
        registerReceiver(mReceiver, filter);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mLastTime < 2000) {
                finish();
                //System.exit(0);
                return true;
            }
            showToast(Utils.getString(R.string.message_clickagainexittheapp));
            mLastTime = System.currentTimeMillis();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * "下载完成"广播接收者
     *
     * ACTION_DOWNLOAD_COMPLETE -- 下载完成时，下载管理器发送的广播意图动作。
     * EXTRA_DOWNLOAD_ID --意图额外包含@link actiondownloadcomplete意图，指示刚刚完成的下载的ID（作为一个长）。
     */
    class UpdatedBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                // 下载的id
                long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                // 下载的时候 在那里预处理保存了这个标志 -- 下载的id
                long versionDownloadIdpks = Long.parseLong(
                        Utils.getSP(context, UpdatedVersionDialog.downLoadIdSPName, UpdatedVersionDialog.spKey));

                if (versionDownloadIdpks == reference) {            // 相同，说明是处理的是同一个
                    GlobalData.setIsDowning(false);
                    DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri uri = downloadManager.getUriForDownloadedFile(versionDownloadIdpks);    // 拿到下载的文件的Uri
                    LogUtils.i("downloadManager.getUriForDownloadedFile:" + uri.getPath());
                    if (uri != null) {
                        UpdateddVersionUtils.installNewVersion(context, uri);      // 安装文件
                    }
                }
            }
        }
    }




    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int id) {
        switch (id) {
            case R.id.rb_home_index:                /* 首页 */
                mVpContent.setCurrentItem(IndexFragment);
                break;

            case R.id.rb_home_find:                 /* 发现 */
                mVpContent.setCurrentItem(FindFragment);
                break;

//            case R.id.rb_home_restaurant:           /* 食品馆 */
//                mVpContent.setCurrentItem(RestaurantListActivity);
//                break;

            case R.id.rb_home_cart:                 /* 购物车 */
                mVpContent.setCurrentItem(CartFragment);
                break;

            case R.id.rb_home_mine:                 /* 我的 */
                mVpContent.setCurrentItem(MineFragment);
                break;

            default:
                break;
        }
    }


    @Override
    public void onRefresh() {
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
            }
    }

}
