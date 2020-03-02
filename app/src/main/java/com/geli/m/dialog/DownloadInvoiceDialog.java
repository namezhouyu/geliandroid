package com.geli.m.dialog;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.geli.m.R;
import com.geli.m.config.Constant;
import com.geli.m.dialog.base.BaseDialogFragment;
import com.geli.m.utils.RxUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.luck.picture.lib.widget.longimage.ImageSource;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.geli.m.BuildConfig.GL_IMAGE_URL;

/**
 * Created by Steam_l on 2018/3/28.
 */

@SuppressLint("ValidFragment")
public class DownloadInvoiceDialog extends BaseDialogFragment implements View.OnClickListener {
    @BindView(R.id.iv_dialog_downloadinvoice_img)
    ImageView iv_img;
    @BindView(R.id.ssiv_img)
    SubsamplingScaleImageView mImageView;
    String imgUrl;
    Bitmap mBitmap;

    private DownloadInvoiceDialog(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public static DownloadInvoiceDialog newInstance(String imgUrl) {
        return new DownloadInvoiceDialog(imgUrl);
    }

    @Override
    protected int getResId() {
        return R.layout.dialog_downloadinvoice;
    }

    @Override
    protected void initData() {
        if (!imgUrl.startsWith(GL_IMAGE_URL)) {
            imgUrl = GL_IMAGE_URL + imgUrl;
        }
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.img_loading)
                .error(R.drawable.img_jiazaishibei)
                .centerCrop();
        Glide.with(mContext).asBitmap().load(imgUrl).apply(options).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                mBitmap = resource;
                ImageSource source = ImageSource.bitmap(mBitmap);
                mImageView.setImage(source);
                iv_img.setImageBitmap(resource);
            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected EditText getEt() {
        return null;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
        }
    }

    @OnClick({R.id.bt_dialog_downloadinvoice_save})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bt_dialog_downloadinvoice_save:
                if (mBitmap == null) {
                    ToastUtils.showToast( Utils.getString(R.string.please_wait_loading));
                    return;
                }
                RxPermissions rxPermissions = new RxPermissions(mContext);
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    ToastUtils.showToast(Utils.getString(R.string.svae_imging));
                                    Observable.create(new ObservableOnSubscribe<String>() {
                                        @Override
                                        public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                                            saveImageToGallery(mContext, mBitmap);
                                            e.onComplete();
                                        }
                                    }).compose(RxUtils.<String>rxSchedulerHelper())
                                            .subscribe(new Observer<String>() {
                                                @Override
                                                public void onSubscribe(@NonNull Disposable d) {

                                                }

                                                @Override
                                                public void onNext(@NonNull String s) {

                                                }

                                                @Override
                                                public void onError(@NonNull Throwable e) {

                                                }

                                                @Override
                                                public void onComplete() {
                                                    ToastUtils.showToast( Utils.getString(R.string.svae_imgsuccess));
                                                }
                                            });
                                } else {
                                    Utils.showMissPermissionDialog(mContext, Utils.getString(R.string.read_write), Utils.getString(R.string.save_photo));
                                }
                            }
                        });


                break;

            default:
                break;
        }
    }

    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Constant.InvoiceDir);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        //        try {
        //            MediaStore.Images.Media.insertImage(context.getContentResolver(),
        //                    file.getAbsolutePath(), fileName, null);
        //        } catch (FileNotFoundException e) {
        //            e.printStackTrace();
        //        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
    }
}
