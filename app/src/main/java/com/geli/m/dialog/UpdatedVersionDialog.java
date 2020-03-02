package com.geli.m.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.dialog.base.BaseDialogFragment;
import com.geli.m.utils.LogUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.UpdateddVersionUtils;
import com.geli.m.utils.Utils;
import java.io.File;

/**
 * 更新版本的"对话框"
 *
 */
@SuppressLint("ValidFragment")
public class UpdatedVersionDialog extends BaseDialogFragment implements View.OnClickListener {

    /** 更新消息 */
    @BindView(R.id.tv_updatedversion_mess)
    TextView tv_mess;

    Context mContext;

//    /** 下载管理 */
//    private DownloadManager downloadManager;
    /** sp名 */
    public static String downLoadIdSPName = "downLoadIdSPName";
    /** "下载id"sp对应的"key" */
    public static String spKey = "downLoadId";
    private String versionDownloadIdpks;

    /** 新版本的下载路径 */
    private String downloadUrl;
    /** 新版本 */
    private String newVersion;
    /** 更新的消息 */
    private String updateMess;

    /** 是否强制更新 */
    private int isUpdate;


    private UpdatedVersionDialog(Context context, String updateMess, String downloadUrl,
                                 String newVersion, int isUpdate) {
        mContext = context;

        this.updateMess = updateMess;
        this.downloadUrl = downloadUrl;
        this.newVersion = newVersion;
        this.isUpdate = isUpdate;
    }


    public static UpdatedVersionDialog newInstance(Context context, String updateMess, String downloadUrl,
                                                   String newVersion, int isUpdate) {
        UpdatedVersionDialog updatedVersionDialog = new UpdatedVersionDialog(context, updateMess,
                downloadUrl, newVersion, isUpdate);

        return updatedVersionDialog;
    }

    @Override
    protected int getResId() {
        return R.layout.dialog_updatedversion;
    }

    @Override
    protected void initData() {
        tv_mess.setText(updateMess);
    }

    @Override
    protected void initEvent() {
        Dialog dialog = getDialog();
        if(dialog != null) {
            if (isUpdate == 1) {
                setCancelable(false);
                setMyCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
            }
        }
    }

    @Override
    protected EditText getEt() {        // 没有输入框
        return null;
    }


    /**
     * "下载" 或 "安装新的apk"
     *
     * @param context
     * @param downLoadUrl
     * @param newVerSon
     */
    public void downLoadOrInstallNewApk(final Context context, final String downLoadUrl, final String newVerSon) {
        String name = "gelishancheng_v" + newVerSon + ".apk";
        boolean alreadyLoad = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), name).exists();

        if (alreadyLoad) {      /* 已经下载过，直接安装 */

            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            versionDownloadIdpks = Utils.getSP(context, downLoadIdSPName, spKey);

            if (TextUtils.isEmpty(versionDownloadIdpks)) {          /* 没下载过，去下载 */
                UpdateddVersionUtils.downLoadNewApk(context, downLoadUrl, newVerSon);
                ShowSingleToast.showToast(context, Utils.getString(R.string.download_newversion_inback));
            }else if (GlobalData.isIsDowning()) {                   /* 没下载过，去下载 */
                ShowSingleToast.showToast(context, Utils.getString(R.string.download_newversion_inback));
            } else {
                try {
                    Uri uri = downloadManager.getUriForDownloadedFile(Long.parseLong(versionDownloadIdpks));
                    UpdateddVersionUtils.installNewVersion(GlobalData.getInstance(), uri);
                    LogUtils.i("downloadManager.getUriForDownloadedFile:" + uri.getPath());
                } catch (Exception e) {
                    ShowSingleToast.showToast(context, Utils.getString(R.string.app_installation_failed));
                    UpdateddVersionUtils.downLoadNewApk(context, downLoadUrl, newVerSon);        // 安装失败重新下载
                }
            }

        } else {                /* 没下载过，去下载 */
            UpdateddVersionUtils.downLoadNewApk(context, downLoadUrl, newVerSon);
            ShowSingleToast.showToast(context, Utils.getString(R.string.download_newversion_inback));
        }
    }




    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            DisplayMetrics metrics = new DisplayMetrics();
            window.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            window.setLayout((int) (metrics.widthPixels * 0.8), LinearLayout.LayoutParams.MATCH_PARENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @OnClick({R.id.tv_updatedversion_cancel, R.id.tv_updatedversion_download})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_updatedversion_cancel:                                 /* 取消 */
                dismiss();
                if(isUpdate == 1){
                    GlobalData.exit();
                }
                break;

            case R.id.tv_updatedversion_download:
                getDialog().hide();
                getDialog().dismiss();

                if (UpdateddVersionUtils.checkInstallPermission()) {
                    downLoadOrInstallNewApk(mContext, downloadUrl, newVersion);     /* "下载"或"安装" */
                }


                break;

            default:
                break;
        }
    }
}
