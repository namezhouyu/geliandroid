package com.geli.m.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.geli.m.R;
import com.geli.m.mvp.base.BaseFragment;
import com.luck.picture.lib.widget.longimage.ImageSource;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;

import butterknife.BindView;


public class ImageFragment extends BaseFragment {
    private String mImageUrl;
    static String URL = "url";

    public static ImageFragment newInstance(String imageUrl) {
        final ImageFragment m = new ImageFragment();
        final Bundle args = new Bundle();
        args.putString(URL, imageUrl);
        m.setArguments(args);
        return m;
    }

    @BindView(R.id.ssiv_showimg)
    SubsamplingScaleImageView imageView;

    @Override
    public int getResId() {
        return R.layout.imageview_show_item;
    }

    @Override
    protected void init() {
        super.init();
        mImageUrl = getArguments() != null ? getArguments().getString(URL) : null;
    }

    @Override
    protected void initData() {
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.img_loading)
                .error(R.drawable.img_jiazaishibei)
                .centerCrop();
        Glide.with(mContext).asBitmap().load(mImageUrl).apply(options).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                ImageSource source = ImageSource.bitmap(resource);
                imageView.setImage(source);
            }
        });
    }

    @Override
    protected void initEvent() {

    }
}
