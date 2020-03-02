package com.geli.m.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.geli.m.R;
import com.geli.m.mvp.base.BaseFragment;
import com.luck.picture.lib.widget.longimage.ImageSource;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;

import butterknife.BindView;

import static com.geli.m.BuildConfig.GL_IMAGE_URL;

/**
 * Created by Steam_l on 2018/3/29.
 */

public class PreviewFragment extends BaseFragment {
    public static final String ARG_URL = "arg_url";
    private String curr_url;
    @BindView(R.id.ssiv_preview_img)
    SubsamplingScaleImageView mImageView;

    @Override
    public int getResId() {
        return R.layout.fragment_preview;
    }

    @Override
    protected void init() {
        super.init();
        curr_url = getArguments().getString(ARG_URL);
    }

    public static PreviewFragment newInstance(String url) {
        PreviewFragment previewFragment = new PreviewFragment();
        Bundle arg = new Bundle();
        arg.putString(ARG_URL, url);
        previewFragment.setArguments(arg);
        return previewFragment;
    }

    @Override
    protected void initData() {
        if (!curr_url.startsWith(GL_IMAGE_URL)) {
            curr_url = GL_IMAGE_URL + curr_url;
        }
        //        curr_url = "http://img.zcool.cn/community/012e9f57fc973ba84a0d304f5ad5e0.jpg";
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.img_loading)
                .error(R.drawable.img_jiazaishibei)
                .centerCrop();
        Glide.with(mContext)
                .asBitmap()
                .load(curr_url)
                .apply(options)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        ImageSource source = ImageSource.bitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_jiazaishibei));
                        mImageView.setImage(source);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        ImageSource source = ImageSource.bitmap(resource);
                        mImageView.setImage(source);
                    }
                });
    }

    @Override
    protected void initEvent() {

    }
}
